/*
 * Copyright 2016 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.tableeditor.variables;

import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.rf.ide.core.testdata.model.table.variables.AVariable.VariableType;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSuiteFile;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.RobotEditorCommandsStack;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.TableThemes.TableTheme;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.assist.CombinedProposalsProvider;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.assist.VariableProposalsProvider;
import org.robotframework.red.jface.assist.RedContentProposalProvider;
import org.robotframework.red.nattable.edit.DetailCellEditor;
import org.robotframework.red.nattable.edit.RedTextCellEditor;
import org.robotframework.red.nattable.edit.VariableNameRedCellEditorValidator;

class VariableValuesEditConfiguration extends AbstractRegistryConfiguration {

    private final TableTheme theme;

    private final VariablesDataProvider dataProvider;

    private final RobotEditorCommandsStack commandsStack;

    private final RobotSuiteFile suiteFile;

    private final boolean wrapCellContent;

    VariableValuesEditConfiguration(final TableTheme theme, final RobotSuiteFile suiteFile,
            final VariablesDataProvider dataProvider, final RobotEditorCommandsStack commandsStack,
            final boolean wrapCellContent) {
        this.theme = theme;
        this.suiteFile = suiteFile;
        this.dataProvider = dataProvider;
        this.commandsStack = commandsStack;
        this.wrapCellContent = wrapCellContent;
    }

    @Override
    public void configureRegistry(final IConfigRegistry configRegistry) {
        final CombinedProposalsProvider proposalProvider = new CombinedProposalsProvider(
                new VariableProposalsProvider(suiteFile, dataProvider));

        configureNamesCellEditors(configRegistry, proposalProvider);
        configureValuesCellEditors(configRegistry, proposalProvider);
        configureCommentCellEditors(configRegistry);
    }

    private void configureNamesCellEditors(final IConfigRegistry configRegistry,
            final RedContentProposalProvider proposalsProvider) {

        configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
                new RedTextCellEditor(2, 1, new VariableNameRedCellEditorValidator(), proposalsProvider,
                        wrapCellContent),
                DisplayMode.NORMAL,
                VariableTypesAndColumnsLabelAccumulator.getNameColumnLabel(VariableType.SCALAR));
        configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
                new RedTextCellEditor(2, 1, new VariableNameRedCellEditorValidator(), proposalsProvider,
                        wrapCellContent),
                DisplayMode.NORMAL, VariableTypesAndColumnsLabelAccumulator.getNameColumnLabel(VariableType.LIST));
        configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
                new RedTextCellEditor(2, 1, new VariableNameRedCellEditorValidator(), proposalsProvider,
                        wrapCellContent),
                DisplayMode.NORMAL,
                VariableTypesAndColumnsLabelAccumulator.getNameColumnLabel(VariableType.DICTIONARY));
    }

    private void configureValuesCellEditors(final IConfigRegistry configRegistry,
            final RedContentProposalProvider proposalsProvider) {

        configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
                new RedTextCellEditor(proposalsProvider, wrapCellContent), DisplayMode.NORMAL,
                VariableTypesAndColumnsLabelAccumulator.getValueColumnLabel(VariableType.SCALAR));

        configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
                new DetailCellEditor<>(new ListVariableDetailsEditingSupport(theme, dataProvider, commandsStack),
                        proposalsProvider),
                DisplayMode.NORMAL, VariableTypesAndColumnsLabelAccumulator.getValueColumnLabel(VariableType.LIST));
        configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
                new DetailCellEditor<>(new ListVariableDetailsEditingSupport(theme, dataProvider, commandsStack),
                        proposalsProvider),
                DisplayMode.NORMAL, VariableTypesAndColumnsLabelAccumulator.getValueColumnLabel(VariableType.INVALID));

        configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
                new DetailCellEditor<>(new DictVariableDetailsEditingSupport(theme, dataProvider, commandsStack),
                        proposalsProvider),
                DisplayMode.NORMAL,
                VariableTypesAndColumnsLabelAccumulator.getValueColumnLabel(VariableType.DICTIONARY));
    }

    private void configureCommentCellEditors(final IConfigRegistry configRegistry) {
        configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
                new RedTextCellEditor(0, 0, null, wrapCellContent), DisplayMode.NORMAL,
                VariableTypesAndColumnsLabelAccumulator.getCommentColumnLabel(VariableType.SCALAR));
        configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
                new RedTextCellEditor(0, 0, null, wrapCellContent), DisplayMode.NORMAL,
                VariableTypesAndColumnsLabelAccumulator.getCommentColumnLabel(VariableType.LIST));
        configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
                new RedTextCellEditor(0, 0, null, wrapCellContent), DisplayMode.NORMAL,
                VariableTypesAndColumnsLabelAccumulator.getCommentColumnLabel(VariableType.DICTIONARY));
        configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
                new RedTextCellEditor(0, 0, null, wrapCellContent), DisplayMode.NORMAL,
                VariableTypesAndColumnsLabelAccumulator.getCommentColumnLabel(VariableType.INVALID));
    }
}
