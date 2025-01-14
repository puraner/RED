/*
 * Copyright 2016 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.tableeditor.source.assist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.robotframework.ide.eclipse.main.plugin.tableeditor.source.assist.Assistant.createAssistant;
import static org.robotframework.ide.eclipse.main.plugin.tableeditor.source.assist.Proposals.applyToDocument;
import static org.robotframework.ide.eclipse.main.plugin.tableeditor.source.assist.Proposals.proposalWithImage;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.robotframework.ide.eclipse.main.plugin.mockdocument.Document;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.source.SuiteSourcePartitionScanner;
import org.robotframework.red.junit.ProjectProvider;

public class CodeReservedWordsAssistProcessorTest {

    @ClassRule
    public static ProjectProvider projectProvider = new ProjectProvider(CodeReservedWordsAssistProcessorTest.class);

    private static IFile suite;

    private static IFile suiteWithFor;

    @BeforeClass
    public static void beforeSuite() throws Exception {
        suite = projectProvider.createFile("suite.robot",
                "*** Keywords ***",
                "keyword",
                "  ",
                "  cell1  cell2",
                "  Giv");
        suiteWithFor = projectProvider.createFile("suite_for.robot",
                "*** Keywords ***",
                "keyword",
                "  :FOR  ${x}  ",
                "  :FOR  ${x}  IN cell");
    }

    @AfterClass
    public static void afterSuite() {
        suite = null;
    }

    @Test
    public void codeReservedWordsProcessorIsValidOnlyForKeywordsOrCasesSections() {
        final CodeReservedWordsAssistProcessor processor = new CodeReservedWordsAssistProcessor(createAssistant(suite));
        assertThat(processor.getApplicableContentTypes()).containsOnly(SuiteSourcePartitionScanner.KEYWORDS_SECTION,
                SuiteSourcePartitionScanner.TEST_CASES_SECTION, SuiteSourcePartitionScanner.TASKS_SECTION);
    }

    @Test
    public void codeReservedWordsProcessorHasTitleDefined() {
        final CodeReservedWordsAssistProcessor processor = new CodeReservedWordsAssistProcessor(createAssistant(suite));
        assertThat(processor.getProposalsTitle()).isNotNull().isNotEmpty();
    }

    @Test
    public void noProposalsAreProvided_whenIsNotInTheFirstCell() throws Exception {
        final ITextViewer viewer = mock(ITextViewer.class);
        final IDocument document = spy(new Document(projectProvider.getFileContent(suite)));

        when(viewer.getDocument()).thenReturn(document);
        when(document.getContentType(37)).thenReturn(SuiteSourcePartitionScanner.KEYWORDS_SECTION);

        final CodeReservedWordsAssistProcessor processor = new CodeReservedWordsAssistProcessor(createAssistant(suite));
        final List<? extends ICompletionProposal> proposals = processor.computeProposals(viewer, 37);

        assertThat(proposals).isEmpty();
    }

    @Test
    public void noProposalsAreProvided_whenNotInApplicableContentType() throws Exception {
        final ITextViewer viewer = mock(ITextViewer.class);
        final IDocument document = spy(new Document(projectProvider.getFileContent(suite)));

        when(viewer.getDocument()).thenReturn(document);
        when(document.getContentType(27)).thenReturn(SuiteSourcePartitionScanner.SETTINGS_SECTION);

        final CodeReservedWordsAssistProcessor processor = new CodeReservedWordsAssistProcessor(createAssistant(suite));
        final List<? extends ICompletionProposal> proposals = processor.computeProposals(viewer, 27);

        assertThat(proposals).isNull();
    }

    @Test
    public void gherkinAndForProposalsAreProvided_whenAtTheEndOfFirstCellOfKeywordsSection() throws Exception {
        final ITextViewer viewer = mock(ITextViewer.class);
        final IDocument document = spy(new Document(projectProvider.getFileContent(suite)));

        when(viewer.getDocument()).thenReturn(document);
        when(document.getContentType(27)).thenReturn(SuiteSourcePartitionScanner.KEYWORDS_SECTION);

        final CodeReservedWordsAssistProcessor processor = new CodeReservedWordsAssistProcessor(createAssistant(suite));
        final List<? extends ICompletionProposal> proposals = processor.computeProposals(viewer, 27);

        assertThat(proposals).hasSize(8).are(proposalWithImage(null));

        assertThat(proposals).extracting(proposal -> applyToDocument(document, proposal)).containsOnly(
                new Document("*** Keywords ***", "keyword", "  :FOR  ", "  cell1  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  END  ", "  cell1  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  FOR  ", "  cell1  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  Given ", "  cell1  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  When ", "  cell1  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  Then ", "  cell1  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  And ", "  cell1  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  But ", "  cell1  cell2", "  Giv"));
    }

    @Test
    public void gherkinAndForProposalsAreProvided_whenInsideTheCellAndProposalIsMatchingToInput() throws Exception {
        final ITextViewer viewer = mock(ITextViewer.class);
        final IDocument document = spy(new Document(projectProvider.getFileContent(suite)));

        when(viewer.getDocument()).thenReturn(document);
        when(document.getContentType(48)).thenReturn(SuiteSourcePartitionScanner.KEYWORDS_SECTION);

        final CodeReservedWordsAssistProcessor processor = new CodeReservedWordsAssistProcessor(createAssistant(suite));
        final List<? extends ICompletionProposal> proposals = processor.computeProposals(viewer, 48);

        assertThat(proposals).hasSize(1).are(proposalWithImage(null));

        assertThat(proposals).extracting(proposal -> applyToDocument(document, proposal)).containsOnly(
                new Document("*** Keywords ***", "keyword", "  ", "  cell1  cell2", "  Given "));
    }

    @Test
    public void gherkinAndForProposalsAreProvided_whenAtTheBeginningEndOfFirstCellOfKeywordsSection() throws Exception {
        final ITextViewer viewer = mock(ITextViewer.class);
        final IDocument document = spy(new Document(projectProvider.getFileContent(suite)));

        when(viewer.getDocument()).thenReturn(document);
        when(document.getContentType(30)).thenReturn(SuiteSourcePartitionScanner.KEYWORDS_SECTION);

        final CodeReservedWordsAssistProcessor processor = new CodeReservedWordsAssistProcessor(createAssistant(suite));
        final List<? extends ICompletionProposal> proposals = processor.computeProposals(viewer, 30);

        assertThat(proposals).hasSize(8).are(proposalWithImage(null));

        assertThat(proposals).extracting(proposal -> applyToDocument(document, proposal)).containsOnly(
                new Document("*** Keywords ***", "keyword", "  ", "  :FOR  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  ", "  END  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  ", "  FOR  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  ", "  Given  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  ", "  When  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  ", "  Then  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  ", "  And  cell2", "  Giv"),
                new Document("*** Keywords ***", "keyword", "  ", "  But  cell2", "  Giv"));
    }

    @Test
    public void forIteratorProposalsAreProvided_whenAtTheEndOfForLine() throws Exception {
        final ITextViewer viewer = mock(ITextViewer.class);
        final IDocument document = spy(new Document(projectProvider.getFileContent(suiteWithFor)));

        when(viewer.getDocument()).thenReturn(document);
        when(document.getContentType(39)).thenReturn(SuiteSourcePartitionScanner.KEYWORDS_SECTION);

        final CodeReservedWordsAssistProcessor processor = new CodeReservedWordsAssistProcessor(
                createAssistant(suiteWithFor));
        final List<? extends ICompletionProposal> proposals = processor.computeProposals(viewer, 39);

        assertThat(proposals).hasSize(4).are(proposalWithImage(null));

        assertThat(proposals).extracting(proposal -> applyToDocument(document, proposal)).containsOnly(
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  IN  ", "  :FOR  ${x}  IN cell"),
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  IN RANGE  ", "  :FOR  ${x}  IN cell"),
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  IN ENUMERATE  ", "  :FOR  ${x}  IN cell"),
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  IN ZIP  ", "  :FOR  ${x}  IN cell"));
    }

    @Test
    public void forIteratorProposalsAreProvided_whenWhenInsideTheCellOfForLine() throws Exception {
        final ITextViewer viewer = mock(ITextViewer.class);
        final IDocument document = spy(new Document(projectProvider.getFileContent(suiteWithFor)));

        when(viewer.getDocument()).thenReturn(document);
        when(document.getContentType(56)).thenReturn(SuiteSourcePartitionScanner.KEYWORDS_SECTION);

        final CodeReservedWordsAssistProcessor processor = new CodeReservedWordsAssistProcessor(
                createAssistant(suiteWithFor));
        final List<? extends ICompletionProposal> proposals = processor.computeProposals(viewer, 56);

        assertThat(proposals).hasSize(4).are(proposalWithImage(null));

        assertThat(proposals).extracting(proposal -> applyToDocument(document, proposal)).containsOnly(
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  ", "  :FOR  ${x}  IN"),
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  ", "  :FOR  ${x}  IN RANGE"),
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  ", "  :FOR  ${x}  IN ENUMERATE"),
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  ", "  :FOR  ${x}  IN ZIP"));
    }

    @Test
    public void forIteratorProposalsAreProvided_whenAtTheBeginningOfTheCellOfForLine() throws Exception {
        final ITextViewer viewer = mock(ITextViewer.class);
        final IDocument document = spy(new Document(projectProvider.getFileContent(suiteWithFor)));

        when(viewer.getDocument()).thenReturn(document);
        when(document.getContentType(54)).thenReturn(SuiteSourcePartitionScanner.KEYWORDS_SECTION);

        final CodeReservedWordsAssistProcessor processor = new CodeReservedWordsAssistProcessor(
                createAssistant(suiteWithFor));
        final List<? extends ICompletionProposal> proposals = processor.computeProposals(viewer, 54);

        assertThat(proposals).hasSize(4).are(proposalWithImage(null));

        assertThat(proposals).extracting(proposal -> applyToDocument(document, proposal)).containsOnly(
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  ", "  :FOR  ${x}  IN"),
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  ", "  :FOR  ${x}  IN RANGE"),
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  ", "  :FOR  ${x}  IN ENUMERATE"),
                new Document("*** Keywords ***", "keyword", "  :FOR  ${x}  ", "  :FOR  ${x}  IN ZIP"));
    }
}
