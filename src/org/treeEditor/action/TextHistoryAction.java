package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class TextHistoryAction extends BasicAbstractAction {


    public static final boolean UNDO = true;

    public static final boolean REDO = false;

    /**
     *
     */
    protected boolean undo;

    /**
     *
     */
    public TextHistoryAction(boolean undo) {
        this.undo = undo;
    }

    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

//        if (editor != null) {
//            UndoManager textUndoManager;
//            if (editor.curGraphComponent == editor.chGraphComponent) {
//                textUndoManager = editor.getTextScrollPane().getChTextPane().getTextUndoManager();
//            } else {
//                textUndoManager = editor.getTextScrollPane().getEnTextPane().getTextUndoManager();
//            }
//
//            if (undo) {
//                if (textUndoManager.canUndo()) {
//                    textUndoManager.undo();
//                    editor.setModified(true);
//                }
//            } else {
//                if (textUndoManager.canRedo()) {
//                    textUndoManager.redo();
//                    editor.setModified(true);
//                }
//            }
//        }
    }
}
