package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class RemoveOtherTagsAction extends BasicAbstractAction {


    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

//        if (editor != null) {
//            TextPane textPane;
//            if (editor.curGraphComponent == editor.chGraphComponent) {
//                textPane = editor.getTextScrollPane().getChTextPane();
//            } else {
//                textPane = editor.getTextScrollPane().getEnTextPane();
//            }
//            TextUndoManager textUndoManager = textPane.getTextUndoManager();
//            textPane.setEditable(true);
//
//            String selected = textPane.getSelectedText();
//            if (selected != null) {
//                //设置默认文字样式
//                SimpleAttributeSet attrDefault = new SimpleAttributeSet();
//                StyleConstants.setBold(attrDefault, false);
//                StyleConstants.setItalic(attrDefault, false);
//                StyleConstants.setUnderline(attrDefault, false);
//                StyleConstants.setForeground(attrDefault, Color.BLACK);
//
//                String text = textPane.getText();
//                String selectedText = textPane.getSelectedText();
//                int start = 0;
//                int selectedStart = textPane.getSelectionStart();
//                int end = text.length();
//                int selectedEnd = textPane.getSelectionEnd();
//
//                String startString = text.substring(start, selectedStart);
//                String endString = text.substring(selectedEnd, end);
//
//                startString = startString.replaceAll("</?[^(a|s|>)]+>", "");
//                endString = endString.replaceAll("</?[^(a|s|>)]+>", "");
//
//                textPane.setText(startString + selectedText + endString);
//
//                textUndoManager.pushOperate(textUndoManager.SETTEXT);
//                editor.setModified(true);
//
//            }
//
//            textPane.setEditable(false);
//            textPane.getCaret().setVisible(true);
//        }
    }
}
