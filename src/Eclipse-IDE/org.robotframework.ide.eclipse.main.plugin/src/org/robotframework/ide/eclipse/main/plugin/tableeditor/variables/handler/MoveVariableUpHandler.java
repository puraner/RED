package org.robotframework.ide.eclipse.main.plugin.tableeditor.variables.handler;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.tools.compat.parts.DIHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.robotframework.ide.eclipse.main.plugin.model.RobotVariable;
import org.robotframework.ide.eclipse.main.plugin.model.cmd.MoveVariableUpCommand;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.RobotEditorCommandsStack;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.variables.handler.MoveVariableUpHandler.E4MoveVariableUpHandler;
import org.robotframework.red.viewers.Selections;

public class MoveVariableUpHandler extends DIHandler<E4MoveVariableUpHandler> {

    public MoveVariableUpHandler() {
        super(E4MoveVariableUpHandler.class);
    }

    public static class E4MoveVariableUpHandler {

        @Inject
        private RobotEditorCommandsStack stack;

        @Execute
        public Object moveVariableUp(@Named(Selections.SELECTION) final IStructuredSelection selection) {

            final RobotVariable selectedVariable = Selections.getSingleElement(selection, RobotVariable.class);
            stack.execute(new MoveVariableUpCommand(selectedVariable));

            return null;
        }
    }
}
