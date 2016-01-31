package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class TextDeleteAction extends BasicAbstractAction {


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
//
//            TextUndoManager textUndoManager = textPane.getTextUndoManager();
//            textPane.setEditable(true);
//
//            //设置默认文字样式
//            SimpleAttributeSet attrDefault = new SimpleAttributeSet();
//            StyleConstants.setBold(attrDefault, false);
//            StyleConstants.setItalic(attrDefault, false);
//            StyleConstants.setUnderline(attrDefault, false);
//            StyleConstants.setForeground(attrDefault, Color.BLACK);
//            //textPane.setCharacterAttributes(attrDefault, true);
//
//            try {
//
//                String selected = textPane.getSelectedText();
//
//                //获取当前光标的位置
//                int caretPosition = textPane.getCaretPosition();
//
//                //获取当前文本的长度
//                int textLength = textPane.getText().length();
//
//                //自动匹配选择标签
//                int startPosition = 0;
//                int endPosition = textLength;
//
//                String startString = textPane.getText(startPosition, caretPosition - startPosition);
//                String endString = textPane.getText(caretPosition, endPosition - caretPosition);
//
//                if (selected != null) {
//                        /*
//						 * 如果为标签
//						 */
//                    if (selected.startsWith("<") && selected.endsWith(">")) {
//                        //结束标签
//                        if (selected.charAt(1) == '/') {
//                            String node = selected.substring(2, selected.length() - 1);
//
//                            String str = textPane.getText(0, textPane.getCaretPosition());
//                            int strLen = str.length();
//                            int nodeLen = node.length();
//                            int nodeCount = 0;
//                            while (true) {
//                                String sub = str.substring(strLen - nodeLen - 2, strLen - 1);
//                                if (sub.equals("/" + node)) {
//                                    nodeCount++;
//                                } else if (sub.equals("<" + node)) {
//                                    nodeCount--;
//                                    if (nodeCount == 0) break;
//                                }
//                                strLen--;
//                            }
//
//                            int start = strLen - nodeLen - 2;
//                            int end = textPane.getCaretPosition();
//
//                            String rep = textPane.getText(start, end - start);
//                            rep = rep.substring(rep.indexOf(">") + 1, rep.lastIndexOf("<"));
//                            textPane.select(start, end);
//                            textPane.replaceSelection(null);
//                            textPane.getDocument().insertString(
//                                    textPane.getCaretPosition(),
//                                    rep,
//                                    attrDefault);
//
//                            textUndoManager.pushOperate(textUndoManager.DELECTTAG);
//                            editor.setModified(true);
//
//                            //开始标签
//                        } else {
//                            String node = selected.substring(1,
//                                    selected.indexOf(" ") != -1 ? selected.indexOf(" ") : selected.indexOf(">"));
//
//                            String str = textPane.getText(textPane.getSelectionStart(), textPane.getText().length() - textPane.getSelectionStart());
//                            int nodeLen = node.length();
//                            int nodeCount = 0, nodePos = 0;
//                            while (true) {
//                                String sub = str.substring(nodePos + 0, nodePos + nodeLen + 1);
//                                if (sub.equals("<" + node)) {
//                                    nodeCount++;
//                                } else if (sub.equals("/" + node)) {
//                                    nodeCount--;
//                                    if (nodeCount == 0) break;
//                                }
//                                nodePos++;
//                            }
//
//                            int start = textPane.getSelectionStart();
//                            int end = textPane.getSelectionStart() + nodePos + nodeLen + 2;
//
//                            String rep = textPane.getText(start, end - start);
//                            rep = rep.substring(rep.indexOf(">") + 1, rep.lastIndexOf("<"));
//                            textPane.select(start, end);
//                            textPane.replaceSelection(null);
//                            textPane.getDocument().insertString(
//                                    textPane.getCaretPosition(),
//                                    rep,
//                                    attrDefault);
//
//                            textUndoManager.pushOperate(textUndoManager.DELECTTAG);
//                            editor.setModified(true);
//
//                        }
//
//						/*
//						 * 如果是属性
//						 */
//                    } else if (selected.matches("[a-zA-Z0-9_]*=\".*\"") &&
//                            startString.lastIndexOf("<") > startString.lastIndexOf(">") &&
//                            endString.indexOf(">") < endString.indexOf("<") &&
//                            endString.indexOf(">") != -1) {
//
//                        if (textPane.getText(caretPosition - selected.length() - 1, 1).equals(" ")) {
//                            textPane.select(caretPosition - selected.length() - 1, caretPosition);
//                        }
//
//                        textPane.replaceSelection(null);
//
//                        textUndoManager.pushOperate(textUndoManager.DELECTATTR);
//                        editor.setModified(true);
//
//						/*
//						 *  如果是空格
//						 */
//                    } else if (selected.matches("[ ]*")) {
//
//                        textPane.replaceSelection(null);
//
//                        textUndoManager.pushOperate(textUndoManager.DELECTBLANK);
//                        editor.setModified(true);
//
//
//						/*
//						 * 如果不是标签
//						 */
//                    } else {
//                        JOptionPane.showMessageDialog(null,
//                                mxResources.get("cannotDeleted"), mxResources.get("error"),
//                                JOptionPane.INFORMATION_MESSAGE);
//                    }
//                }
//
//            } catch (Exception e1) {
//                //e1.printStackTrace();
//                JOptionPane.showMessageDialog(null,
//                        e1.toString(), mxResources.get("error"),
//                        JOptionPane.ERROR_MESSAGE);
//            }
//
//            textPane.setEditable(false);
//            textPane.getCaret().setVisible(true);
//        }
    }
}
