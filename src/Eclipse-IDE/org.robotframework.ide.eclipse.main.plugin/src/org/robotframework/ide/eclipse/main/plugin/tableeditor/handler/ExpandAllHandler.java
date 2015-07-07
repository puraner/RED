package org.robotframework.ide.eclipse.main.plugin.tableeditor.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.tools.compat.parts.DIHandler;
import org.eclipse.jface.viewers.TreeViewer;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.FocusedViewerAccessor;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.handler.ExpandAllHandler.E4ExpandAllHandler;

public class ExpandAllHandler extends DIHandler<E4ExpandAllHandler> {

    public ExpandAllHandler() {
        super(E4ExpandAllHandler.class);
    }

    public static class E4ExpandAllHandler {
        @Execute
        public Object expandAll(final FocusedViewerAccessor viewerAccessor) {
            final TreeViewer viewer = (TreeViewer) viewerAccessor.getViewer();
            viewer.getTree().setRedraw(false);
            viewer.expandAll();
            viewer.getTree().setRedraw(true);
            return null;
        }
    }
}
