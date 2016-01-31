package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class AboutAction extends BasicAbstractAction {
    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);
        if (editor != null) {
            editor.about();
        }
    }
}
