/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.tableeditor.source.assist;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.robotframework.ide.eclipse.main.plugin.assist.AssistProposal;
import org.robotframework.ide.eclipse.main.plugin.assist.RedFileLocationProposals;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSetting.SettingsGroup;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.source.DocumentUtilities;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.source.SuiteSourcePartitionScanner;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.source.assist.RedCompletionProposalAdapter.DocumentModification;

/**
 * @author Michal Anglart
 */
public class ResourcesImportAssistProcessor extends RedContentAssistProcessor {

    public ResourcesImportAssistProcessor(final SuiteSourceAssistantContext assist) {
        super(assist);
    }

    @Override
    public String getProposalsTitle() {
        return "Resource files";
    }

    @Override
    public List<String> getApplicableContentTypes() {
        return newArrayList(SuiteSourcePartitionScanner.SETTINGS_SECTION);
    }

    @Override
    protected boolean shouldShowProposals(final IDocument document, final int offset, final String lineContent)
            throws BadLocationException {
        return isInApplicableContentType(document, offset) && lineContent.toLowerCase().startsWith("resource")
                && DocumentUtilities.getNumberOfCellSeparators(lineContent, assist.isTsvFile()) == 1;
    }

    @Override
    protected List<? extends ICompletionProposal> computeProposals(final IDocument document, final int offset,
            final int cellLength, final String userContent, final boolean atTheEndOfLine) throws BadLocationException {

        final List<? extends AssistProposal> varFilesProposals = RedFileLocationProposals
                .create(SettingsGroup.RESOURCES, assist.getModel())
                .getFilesLocationsProposals(userContent);

        final List<ICompletionProposal> proposals = newArrayList();
        for (final AssistProposal proposal : varFilesProposals) {
            final DocumentModification modification = new DocumentModification("",
                    new Position(offset - userContent.length(), cellLength));

            proposals.add(new RedCompletionProposalAdapter(assist, proposal, modification));
        }
        return proposals;
    }
}
