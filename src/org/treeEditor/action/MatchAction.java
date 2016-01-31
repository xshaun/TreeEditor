package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class MatchAction extends BasicAbstractAction {


    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

        if (editor != null) {
//            TextPane textPane;
//            if (editor.curGraphComponent == editor.chGraphComponent) {
//                textPane = editor.getTextScrollPane().getChTextPane();
//            } else {
//                textPane = editor.getTextScrollPane().getEnTextPane();
//            }
//            textPane.setEditable(true);
//
//            String selected = textPane.getSelectedText();
//            if (selected != null) {
//
//					/*
//					 * 如果为标签
//					 */
//                if (selected.startsWith("<") && selected.endsWith(">")) {
//                    try {
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
//                            textPane.select(start, end);
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
//                            textPane.select(start, end);
//
//                        }
//                    } catch (Exception e1) {
//                        //e1.printStackTrace();
//                        JOptionPane.showMessageDialog(null,
//                                e1.toString(), mxResources.get("error"),
//                                JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//
//            textPane.setEditable(false);
//            textPane.getCaret().setVisible(true);
        }
    }
}
