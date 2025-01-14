/*
 * Copyright 2016 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.tableeditor.assist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.AbstractMap.SimpleEntry;

import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.rf.ide.core.project.RobotProjectConfig;
import org.rf.ide.core.project.RobotProjectConfig.ExecutionEnvironment;
import org.robotframework.ide.eclipse.main.plugin.model.RobotCasesSection;
import org.robotframework.ide.eclipse.main.plugin.model.RobotKeywordCall;
import org.robotframework.ide.eclipse.main.plugin.model.RobotModel;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSettingsSection;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSuiteFile;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.assist.VariableProposalsProvider.VariableTextModificationStrategy;
import org.robotframework.red.jface.assist.AssistantContext;
import org.robotframework.red.jface.assist.RedContentProposal;
import org.robotframework.red.jface.assist.RedContentProposal.ModificationStrategy;
import org.robotframework.red.jface.assist.RedTextContentAdapter.SubstituteTextModificationStrategy;
import org.robotframework.red.junit.ProjectProvider;
import org.robotframework.red.junit.ShellProvider;
import org.robotframework.red.nattable.edit.AssistanceSupport.NatTableAssistantContext;

public class VariableProposalsProviderTest {

        @ClassRule
        public static ProjectProvider projectProvider = new ProjectProvider(VariableProposalsProviderTest.class);

    @Rule
    public ShellProvider shellProvider = new ShellProvider();

    @BeforeClass
    public static void beforeSuite() throws Exception {
        projectProvider.createFile("suite.robot",
                "*** Variables ***",
                "${a_var}",
                "${b_var}",
                "@{c_list}",
                "*** Settings ***", "Suite Setup",
                "*** Test Cases ***",
                "case",
                "  call");

        // skipping global variables
        final RobotProjectConfig config = RobotProjectConfig.create();
        config.setExecutionEnvironment(ExecutionEnvironment.create("", null));
        projectProvider.configure(config);
    }

    @Test
    public void exceptionIsThrownWhenDataProviderReturnsOrdinaryObject() {
        final RobotSuiteFile suite = new RobotModel().createSuiteFile(projectProvider.getFile("suite.robot"));

        final IRowDataProvider<Object> dataProvider = createDataProvider(new Object());
        final VariableProposalsProvider provider = new VariableProposalsProvider(suite, dataProvider);
        final AssistantContext context = new NatTableAssistantContext(0, 0);

        assertThatIllegalStateException().isThrownBy(() -> provider.getProposals("${xyz}", 0, context))
                .withMessage("Unrecognized element in table")
                .withNoCause();
    }

    @Test
    public void thereAreNoVariablesProposalsProvided_whenThereIsNoVariableMatchingCurrentInput() {
        final RobotSuiteFile suite = new RobotModel().createSuiteFile(projectProvider.getFile("suite.robot"));
        final RobotKeywordCall callElement = suite.findSection(RobotCasesSection.class)
                .get().getChildren().get(0).getChildren().get(0);

        final IRowDataProvider<Object> dataProvider = createDataProvider(callElement);
        final VariableProposalsProvider provider = new VariableProposalsProvider(suite, dataProvider);
        final AssistantContext context = new NatTableAssistantContext(0, 0);
        final RedContentProposal[] proposals = provider.getProposals("${xyz}", 3, context);

        assertThat(proposals).isEmpty();
    }

    @Test
    public void thereAreVariablesProposalsProvided_whenThereAreProposalsMatchingCurrentContent() {
        final RobotSuiteFile suite = new RobotModel().createSuiteFile(projectProvider.getFile("suite.robot"));
        final RobotKeywordCall callElement = suite.findSection(RobotCasesSection.class)
                .get().getChildren().get(0).getChildren().get(0);

        final IRowDataProvider<Object> dataProvider = createDataProvider(callElement);
        final VariableProposalsProvider provider = new VariableProposalsProvider(suite, dataProvider);
        final AssistantContext context = new NatTableAssistantContext(0, 0);
        final RedContentProposal[] proposals = provider.getProposals("a${abc}b", 4, context);

        assertThat(proposals).extracting(RedContentProposal::getContent).containsExactly("${a_var}", "${b_var}");
        assertThat(proposals).extracting(RedContentProposal::getModificationStrategy)
                .allMatch(strategy -> strategy instanceof VariableTextModificationStrategy);
    }

    @Test
    public void thereAreVariablesProposalsProvided_alsoWhenCurrentContentDoesNotContainVariableRegion() {
        final RobotSuiteFile suite = new RobotModel().createSuiteFile(projectProvider.getFile("suite.robot"));
        final RobotKeywordCall callElement = suite.findSection(RobotCasesSection.class)
                .get().getChildren().get(0).getChildren().get(0);

        final IRowDataProvider<Object> dataProvider = createDataProvider(callElement);
        final VariableProposalsProvider provider = new VariableProposalsProvider(suite, dataProvider);
        final AssistantContext context = new NatTableAssistantContext(0, 0);
        final RedContentProposal[] proposals = provider.getProposals("abc", 1, context);

        assertThat(proposals).extracting(RedContentProposal::getContent).containsExactly("${a_var}", "${b_var}");
        assertThat(proposals).extracting(RedContentProposal::getModificationStrategy)
                .allMatch(strategy -> strategy instanceof SubstituteTextModificationStrategy);
    }

    @Test
    public void thereAreVariablesProposalsProvided_alsoWhenSettingIsWrappedAsEntry() {
        final RobotSuiteFile suite = new RobotModel().createSuiteFile(projectProvider.getFile("suite.robot"));
        final RobotKeywordCall settingElement = suite.findSection(RobotSettingsSection.class).get().getChildren().get(0);

        final IRowDataProvider<Object> dataProvider = createDataProvider(
                new SimpleEntry<String, RobotKeywordCall>("x", settingElement));
        final VariableProposalsProvider provider = new VariableProposalsProvider(suite, dataProvider);
        final AssistantContext context = new NatTableAssistantContext(0, 0);
        final RedContentProposal[] proposals = provider.getProposals("${blah}", 3, context);

        assertThat(proposals).extracting(RedContentProposal::getContent).containsExactly("${b_var}");
        assertThat(proposals).extracting(RedContentProposal::getModificationStrategy)
                .allMatch(strategy -> strategy instanceof VariableTextModificationStrategy);
    }

    @Test
    public void proposalIsInserted_whenInputContainsOnlyVariableIdentificator() {
        final Text text = new Text(shellProvider.getShell(), SWT.SINGLE);
        text.setText("$");
        text.setSelection(1);

        final ModificationStrategy strategy = new VariableTextModificationStrategy();

        strategy.insert(text, new ContentProposal("${some_var}"));
        assertThat(text.getText()).isEqualTo("${some_var}");
    }

    @Test
    public void proposalIsInserted_whenInputContainsOnlyVariableIdentificatorWrappedWithText() {
        final Text text = new Text(shellProvider.getShell(), SWT.SINGLE);
        text.setText("a@b");
        text.setSelection(2);

        final ModificationStrategy strategy = new VariableTextModificationStrategy();

        strategy.insert(text, new ContentProposal("@{some_list}"));
        assertThat(text.getText()).isEqualTo("a@{some_list}b");
    }

    @Test
    public void proposalIsInserted_whenInputContainsSingleCorrectVariable() {
        final Text text = new Text(shellProvider.getShell(), SWT.SINGLE);
        text.setText("&{dict}");
        text.setSelection(2);

        final ModificationStrategy strategy = new VariableTextModificationStrategy();

        strategy.insert(text, new ContentProposal("&{some_dict}"));
        assertThat(text.getText()).isEqualTo("&{some_dict}");
    }

    @Test
    public void proposalIsInserted_whenInputContainsSingleCorrectVariableWrappedByText() {
        final Text text = new Text(shellProvider.getShell(), SWT.SINGLE);
        text.setText("a${var}b");
        text.setSelection(5);

        final ModificationStrategy strategy = new VariableTextModificationStrategy();

        strategy.insert(text, new ContentProposal("${some_var}"));
        assertThat(text.getText()).isEqualTo("a${some_var}b");
    }

    @Test
    public void proposalIsInserted_whenInputContainsSeveralVarRegionsWithSameTypes() {
        final Text text = new Text(shellProvider.getShell(), SWT.SINGLE);
        text.setText("a${xyz}b$");
        text.setSelection(9);

        final ModificationStrategy strategy = new VariableTextModificationStrategy();

        strategy.insert(text, new ContentProposal("${some_var}"));
        assertThat(text.getText()).isEqualTo("a${xyz}b${some_var}");
    }

    @Test
    public void proposalIsInserted_whenInputContainsSeveralVarRegionsWithDifferentTypes() {
        final Text text = new Text(shellProvider.getShell(), SWT.SINGLE);
        text.setText("a@{xyz}b$");
        text.setSelection(9);

        final ModificationStrategy strategy = new VariableTextModificationStrategy();

        strategy.insert(text, new ContentProposal("${some_var}"));
        assertThat(text.getText()).isEqualTo("a@{xyz}b${some_var}");
    }

    private static IRowDataProvider<Object> createDataProvider(final Object rowObject) {
        @SuppressWarnings("unchecked")
        final IRowDataProvider<Object> dataProvider = mock(IRowDataProvider.class);
        when(dataProvider.getRowObject(0)).thenReturn(rowObject);
        return dataProvider;
    }
}
