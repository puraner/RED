package org.robotframework.ide.eclipse.main.plugin.tableeditor.settings;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.tools.services.IDirtyProviderService;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnAddingEditingSupport;
import org.eclipse.jface.viewers.ColumnAddingEditingSupport.ColumnProviders;
import org.eclipse.jface.viewers.ColumnAddingLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.RowExposingTableViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerColumnsFactory;
import org.eclipse.jface.viewers.ViewersConfigurator;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.robotframework.forms.RedFormToolkit;
import org.robotframework.forms.Sections;
import org.robotframework.ide.eclipse.main.plugin.RobotElement;
import org.robotframework.ide.eclipse.main.plugin.RobotElementChange;
import org.robotframework.ide.eclipse.main.plugin.RobotElementChange.Kind;
import org.robotframework.ide.eclipse.main.plugin.RobotImages;
import org.robotframework.ide.eclipse.main.plugin.RobotModelEvents;
import org.robotframework.ide.eclipse.main.plugin.RobotSetting;
import org.robotframework.ide.eclipse.main.plugin.RobotSuiteFile;
import org.robotframework.ide.eclipse.main.plugin.RobotSuiteFileSection;
import org.robotframework.ide.eclipse.main.plugin.RobotSuiteSettingsSection;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.CellsAcivationStrategy;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.CellsAcivationStrategy.RowTabbingStrategy;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.ISectionFormFragment;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.RobotEditorCommandsStack;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.RobotEditorSources;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.RobotElementEditingSupport.NewElementsCreator;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.cases.KeywordCallNameLabelProvider;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.settings.popup.ImportLibraryPopup;

public class ImportSettingsFormFragment implements ISectionFormFragment {

    @Inject
    private IEditorSite site;

    @Inject
    @Named(RobotEditorSources.SUITE_FILE_MODEL)
    private RobotSuiteFile fileModel;

    @Inject
    private RobotEditorCommandsStack commandsStack;

    @Inject
    private IDirtyProviderService dirtyProviderService;

    @Inject
    private RedFormToolkit toolkit;

    private RowExposingTableViewer viewer;

    private Section section;

    TableViewer getViewer() {
        return viewer;
    }

    @Override
    public void initialize(final Composite parent) {
        section = toolkit.createSection(parent, ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
        section.setText("Imports");
        section.setExpanded(false);
        GridDataFactory.fillDefaults().grab(true, false).minSize(1, 22).applyTo(section);
        Sections.switchGridCellGrabbingOnExpansion(section);
        Sections.installMaximazingPossibility(section);

        viewer = new RowExposingTableViewer(section, SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getTable());
        section.setClient(viewer.getTable());
        viewer.getTable().setLinesVisible(true);
        viewer.getTable().setHeaderVisible(true);
        viewer.getTable().addListener(SWT.MeasureItem, new Listener() {

            @Override
            public void handleEvent(final Event event) {
                event.height = Double.valueOf(event.gc.getFontMetrics().getHeight() * 1.5).intValue();
            }
        });
        viewer.setContentProvider(new ImportSettingsContentProvider(fileModel.isEditable()));
        CellsAcivationStrategy.addActivationStrategy(viewer, RowTabbingStrategy.MOVE_TO_NEXT);
        ColumnViewerToolTipSupport.enableFor(viewer, ToolTip.NO_RECREATE);

        ViewersConfigurator.disableContextMenuOnHeader(viewer);
        createContextMenu();

        viewer.setInput(getImportElements());
        createColumns(true);
        viewer.refresh();
        viewer.packFirstColumn();
    }

    private void createContextMenu() {
        final String menuId = "org.robotframework.ide.eclipse.editor.page.settings.imports.contextMenu";

        final MenuManager manager = new MenuManager("Robot suite editor imports settings context menu", menuId);
        final Table control = viewer.getTable();
        final Menu menu = manager.createContextMenu(control);
        control.setMenu(menu);
        site.registerContextMenu(menuId, manager, site.getSelectionProvider(), false);
    }

    private List<RobotElement> getImportElements() {
        final RobotSuiteSettingsSection section = getSettingsSection();
        return section != null ? section.getImportSettings() : null;
    }

    private RobotSuiteSettingsSection getSettingsSection() {
        final com.google.common.base.Optional<RobotElement> settingsSection = fileModel
                .findSection(RobotSuiteSettingsSection.class);
        return (RobotSuiteSettingsSection) settingsSection.orNull();
    }

    private void createColumns(final boolean createFirst) {
        final NewElementsCreator creator = newElementsCreator();
        if (createFirst) {
            ViewerColumnsFactory.newColumn("Import").withWidth(100)
                .shouldGrabAllTheSpaceLeft(viewer.getInput() == null).withMinWidth(100)
                .labelsProvidedBy(new KeywordCallNameLabelProvider())
                .editingSupportedBy(new ImportSettingsEditingSupport(viewer, commandsStack, creator))
                .editingEnabledOnlyWhen(fileModel.isEditable())
                .createFor(viewer);
        }
        if (viewer.getInput() == null) {
            return;
        }

        final int max = calcualateLongestArgumentsLength();
        createArgumentColumn("Name / Path", 0, creator);
        for (int i = 1; i < max; i++) {
            createArgumentColumn("", i, creator);
        }
        ViewerColumnsFactory.newColumn("Comment").withWidth(200)
            .shouldGrabAllTheSpaceLeft(true).withMinWidth(100)
            .labelsProvidedBy(new SettingsCommentsLabelProvider())
            .editingSupportedBy(new SettingsCommentsEditingSupport(viewer, commandsStack, creator))
            .editingEnabledOnlyWhen(fileModel.isEditable())
            .createFor(viewer);

        final int newColumnsStartingPosition = max + 1;

        final ImageDescriptor addImage = fileModel.isEditable() ? RobotImages.getAddImage() : RobotImages
                .getGreyedImage(RobotImages.getAddImage());
        ViewerColumnsFactory.newColumn("").withWidth(28)
                .resizable(false)
                .withTooltip("Activate cell in this column to add new arguments columns")
                .withImage(addImage.createImage())
                .labelsProvidedBy(new ColumnAddingLabelProvider())
                .editingSupportedBy(
                        new ColumnAddingEditingSupport(viewer, newColumnsStartingPosition, new ColumnProviders() {
                            @Override
                            public void createColumn(final int index) {
                                createArgumentColumn("", index - 1, creator);
                            }
                        })).editingEnabledOnlyWhen(fileModel.isEditable()).createFor(viewer);
    }

    private NewElementsCreator newElementsCreator() {
        return new NewElementsCreator() {
            @Override
            public RobotElement createNew() {
                new ImportLibraryPopup(viewer.getControl().getShell(), commandsStack, fileModel).open();
                return null;
            }
        };
    }

    private int calcualateLongestArgumentsLength() {
        int max = 1;
        final List<?> elements = (List<?>) viewer.getInput();
        if (elements != null) {
            for (final Object element : elements) {
                final RobotSetting setting = (RobotSetting) element;
                if (setting != null) {
                    max = Math.max(max, setting.getArguments().size());
                }
            }
        }
        return max;
    }

    private void createArgumentColumn(final String name, final int index, final NewElementsCreator creator) {
        ViewerColumnsFactory.newColumn(name).withWidth(120).labelsProvidedBy(new SettingsArgsLabelProvider(index))
                .editingSupportedBy(new SettingsArgsEditingSupport(viewer, index, commandsStack, creator))
                .editingEnabledOnlyWhen(fileModel.isEditable()).createFor(viewer);
    }

    @Override
    public void setFocus() {
        viewer.getTable().setFocus();
    }

    public void revealSetting(final RobotSetting setting) {
        Sections.maximizeChosenSectionAndMinimalizeOthers(section);
        viewer.setSelection(new StructuredSelection(setting));
        setFocus();
    }

    public void clearSettingsSelection() {
        viewer.setSelection(StructuredSelection.EMPTY);
    }

    private void refreshInput() {
        viewer.setInput(getImportElements());
        viewer.removeColumns(1);
        createColumns(false);
        viewer.refresh();
        viewer.packFirstColumn();
    }

    @Inject
    @Optional
    private void whenSectionIsCreated(
            @UIEventTopic(RobotModelEvents.ROBOT_SUITE_SECTION_ADDED) final RobotSuiteFile file) {
        if (file == fileModel) {
            refreshInput();
            dirtyProviderService.setDirtyState(true);
        }
    }

    @Inject
    @Optional
    private void whenSectionIsRemoved(
            @UIEventTopic(RobotModelEvents.ROBOT_SUITE_SECTION_REMOVED) final RobotSuiteFile file) {
        if (file == fileModel) {
            refreshInput();
            dirtyProviderService.setDirtyState(true);
        }
    }

    @Inject
    @Optional
    private void whenSettingDetailsChanges(
            @UIEventTopic(RobotModelEvents.ROBOT_KEYWORD_CALL_DETAIL_CHANGE_ALL) final RobotSetting setting) {
        final List<?> input = (List<?>) viewer.getInput();
        if (setting.getSuiteFile() == fileModel && input != null && input.contains(setting)) {
            refreshInput();
            dirtyProviderService.setDirtyState(true);
        }
    }

    @Inject
    @Optional
    private void whenSettingIsAddedOrRemoved(
            @UIEventTopic(RobotModelEvents.ROBOT_SETTINGS_STRUCTURAL_ALL) final RobotSuiteFileSection section) {
        if (section.getSuiteFile() == fileModel) {
            refreshInput();
            dirtyProviderService.setDirtyState(true);
        }
    }

    @Inject
    @Optional
    private void whenFileChangedExternally(
            @UIEventTopic(RobotModelEvents.EXTERNAL_MODEL_CHANGE) final RobotElementChange change) {
        if (change.getKind() == Kind.CHANGED) {
            refreshInput();
        }
    }

}
