/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.tableeditor.source;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.ui.PlatformUI;
import org.rf.ide.core.testdata.model.RobotFileOutput;
import org.robotframework.ide.eclipse.main.plugin.model.RobotModelEvents;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSuiteFile;
import org.robotframework.ide.eclipse.main.plugin.project.build.RobotArtifactsValidator;

public class SuiteSourceReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {

    private RobotDocument document;

    private final SuiteSourceEditor editor;

    public SuiteSourceReconcilingStrategy(final SuiteSourceEditor editor) {
        this.editor = editor;
    }

    @Override
    public void setProgressMonitor(final IProgressMonitor monitor) {
        // we don't need monitor
    }

    @Override
    public void setDocument(final IDocument document) {
        this.document = (RobotDocument) document;
    }

    @Override
    public void initialReconcile() {
        final RobotSuiteFile suiteModel = editor.getFileModel();
        reparseModel(suiteModel);
        getFoldingSupport().reset();
        getFoldingSupport().updateFoldingStructure(suiteModel, document);
    }

    @Override
    public void reconcile(final DirtyRegion dirtyRegion, final IRegion subRegion) {
        reconcile();
    }

    @Override
    public void reconcile(final IRegion partition) {
        reconcile();
    }

    private void reconcile() {
        final RobotSuiteFile suiteModel = editor.getFileModel();
        reparseModel(suiteModel);
        getFoldingSupport().updateFoldingStructure(suiteModel, document);
        RobotArtifactsValidator.revalidate(suiteModel);
    }

    private void reparseModel(final RobotSuiteFile suiteModel) {
        // to make sure the model is in sync with this, which is hold reparsed by document
        try {
            final RobotFileOutput fileOutput = document.getNewestFileOutput();
            suiteModel.link(fileOutput);

            final IEventBroker eventBroker = PlatformUI.getWorkbench().getService(IEventBroker.class);
            eventBroker.post(RobotModelEvents.REPARSING_DONE, suiteModel);
        } catch (final InterruptedException e) {
            // ok so the model will be reparsed later
        }
    }

    private SuiteSourceEditorFoldingSupport getFoldingSupport() {
        // to make sure folding support is initialized
        SuiteSourceEditorFoldingSupport foldingSupport = editor.getFoldingSupport();
        while (foldingSupport == null) {
            foldingSupport = editor.getFoldingSupport();
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
                // retry
            }
        }
        return foldingSupport;
    }
}
