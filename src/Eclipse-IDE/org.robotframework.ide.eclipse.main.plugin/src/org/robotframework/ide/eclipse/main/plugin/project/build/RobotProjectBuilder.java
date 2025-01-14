/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.project.build;

import static java.util.stream.Collectors.toSet;

import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.rf.ide.core.libraries.LibraryDescriptor;
import org.robotframework.ide.eclipse.main.plugin.RedPlugin;
import org.robotframework.ide.eclipse.main.plugin.model.LibspecsFolder;
import org.robotframework.ide.eclipse.main.plugin.model.RobotProject;
import org.robotframework.ide.eclipse.main.plugin.project.build.RobotArtifactsValidator.ModelUnitValidatorConfig;
import org.robotframework.ide.eclipse.main.plugin.project.build.RobotArtifactsValidator.ModelUnitValidatorConfigFactory;

public class RobotProjectBuilder extends IncrementalProjectBuilder {

    private final BuildLogger logger;
    private final ValidationReportingStrategy reporter;
    private final ValidationReportingStrategy fatalReporter;

    public RobotProjectBuilder() {
        this(ValidationReportingStrategy.reportOnly(), ValidationReportingStrategy.reportAndPanic(), new BuildLogger());
    }

    public RobotProjectBuilder(final ValidationReportingStrategy reporter, final ValidationReportingStrategy fatalReporter,
            final BuildLogger logger) {
        this.reporter = reporter;
        this.fatalReporter = fatalReporter;
        this.logger = logger;
    }

    @Override
    protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
            throws CoreException {
        final RobotProject robotProject = RedPlugin.getModelManager().getModel().createRobotProject(getProject());
        build(kind, robotProject, monitor);
        return null;
    }

    public void build(final int kind, final RobotProject robotProject, final IProgressMonitor monitor)
            throws CoreException {
        final boolean isValidationTurnedOff = RedPlugin.getDefault().getPreferences().isValidationTurnedOff();
        try {
            final IProject project = robotProject.getProject();
            final LibspecsFolder libspecsFolder = LibspecsFolder.createIfNeeded(project);
            final IResourceDelta delta = getDelta(project);
            final boolean rebuildNeeded = libspecsFolder.shouldRegenerateLibspecs(delta, kind);

            final Job buildJob = new RobotArtifactsBuilder(project, logger).createBuildJob(rebuildNeeded, reporter,
                    fatalReporter);
            Job validationJob = null;
            if (!isValidationTurnedOff) {
                final ModelUnitValidatorConfig validatorConfig = ModelUnitValidatorConfigFactory.create(project, delta,
                        kind, reporter);
                validationJob = new RobotArtifactsValidator(project, logger).createValidationJob(buildJob,
                        validatorConfig);
            }
            try {
                final String projectPath = project.getFullPath().toString();

                monitor.subTask("waiting for project " + projectPath + " build end");
                buildJob.schedule();
                if (!isValidationTurnedOff) {
                    validationJob.schedule();
                }
                buildJob.join();

                robotProject.clearConfiguration();
                if (buildJob.getResult().getSeverity() == IStatus.CANCEL
                        || buildJob.getResult().getSeverity() == IStatus.ERROR) {
                    if (libspecsFolder.exists()) {
                        libspecsFolder.remove();
                        if (!isValidationTurnedOff) {
                            validationJob.cancel();
                        }
                        return;
                    }
                }
                project.refreshLocal(IResource.DEPTH_INFINITE, null);

                if (!monitor.isCanceled()) {
                    monitor.subTask("waiting for project " + projectPath + " validation end");
                    if (!isValidationTurnedOff) {
                        validationJob.join();
                    }
                }
            } catch (final InterruptedException e) {
                throw new CoreException(Status.CANCEL_STATUS);
            }
        } finally {
            monitor.worked(1);
        }
    }


    @Override
    protected void clean(final IProgressMonitor monitor) throws CoreException {
        final IProject project = getProject();
        project.deleteMarkers(RobotProblem.TYPE_ID, true, IResource.DEPTH_INFINITE);
        project.deleteMarkers(RobotTask.TYPE_ID, true, IResource.DEPTH_INFINITE);
        clean(RedPlugin.getModelManager().createProject(project));
    }

    public static void clean(final RobotProject project) throws CoreException {
        project.clearConfiguration();
        project.clearKwSources();

        removeAllUnusedLibspecsFiles(project);
    }

    public static void removeAllUnusedLibspecsFiles(final RobotProject project) {
        final LibspecsFolder libspecsFolder = project.getLibspecsFolder();
        final Set<IFile> filesToPreserve = project.getLibraryDescriptorsStream()
                .map(LibraryDescriptor::generateLibspecFileName)
                .map(libspecsFolder::getXmlSpecFile)
                .collect(toSet());
        filesToPreserve.addAll(libspecsFolder.getNewestHtmlSpecFiles());
        try {
            libspecsFolder.preserveOnly(filesToPreserve);
        } catch (final CoreException e) {
            // ok, so the unused files are still there
        }
    }
}
