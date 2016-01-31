package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class HistoryAction extends BasicAbstractAction {


    public static final boolean UNDO = true;

    public static final boolean REDO = false;

    /**
     *
     */
    protected boolean undo;

    /**
     *
     */
    public HistoryAction(boolean undo) {
        this.undo = undo;
    }

    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

        if (editor != null) {
//            mxUndoManager undoManager = editor.getEnGraphComponent().getGraph().getUndoManager();
//
//            if (editor.getCurGraphComponent() == editor.getChGraphComponent()) {
//                undoManager = editor.getChGraphComponent().getGraph().getUndoManager();
//            }
//
//            if (undo) {
//                if (undoManager.canUndo()) undoManager.undo();
//            } else {
//                if (undoManager.canRedo()) undoManager.redo();
//            }
        }
    }
}
