package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public abstract class BasicAbstractAction extends AbstractAction {
    /**
     * @param e
     * @return Returns the graph for the given action event.
     */
    public static final BasicGraphEditor getEditor(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component component = (Component) e.getSource();

            while (component != null
                    && !(component instanceof BasicGraphEditor)) {
                component = component.getParent();
            }

            return (BasicGraphEditor) component;
        }

        return null;
    }
}
