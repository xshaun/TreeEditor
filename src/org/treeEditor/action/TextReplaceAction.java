package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class TextReplaceAction extends BasicAbstractAction {


    public static final int TEXT = 1;

    public static final int TAG = 2;

    public static final int ATTR = 3;

    protected int type;

    public TextReplaceAction(int type) {
        this.type = type;
    }

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
//            //获取标签数据
//            boolean isCh = editor.curGraphComponent == editor.chGraphComponent;
//            HashMap<String, List<Element>> tagMap =
//                    isCh ? editor.dataTree.getTagChMap() : editor.dataTree.getTagEnMap();
//
//
//            //设置默认文字样式
//            SimpleAttributeSet attrDefault = new SimpleAttributeSet();
//            StyleConstants.setBold(attrDefault, false);
//            StyleConstants.setItalic(attrDefault, false);
//            StyleConstants.setUnderline(attrDefault, false);
//            StyleConstants.setForeground(attrDefault, Color.BLACK);
//            //textPane.setCharacterAttributes(attrDefault, true);
//
//            //设置修改文字样式
//            SimpleAttributeSet attrSet = new SimpleAttributeSet();
//            StyleConstants.setBold(attrSet, true);
//            StyleConstants.setItalic(attrSet, true);
//            StyleConstants.setUnderline(attrSet, false);
//            StyleConstants.setForeground(attrSet, new Color(0x5F, 0x9E, 0xA0));
//
//            try {
//                switch (this.type) {
//                    case TEXT: {
//                        //若未选中则结束
//                        if (textPane.getSelectedText() == null) break;
//
//                        //获取文本
//                        String addText = JOptionPane.showInputDialog(mxResources.get("pleaseInputText"));
//
//                        //如果 点击取消按钮 或 未输入文本 则结束
//                        if (addText == null || addText.isEmpty()) break;
//
//                        //替换文本
//                        textPane.replaceSelection(null);
//                        textPane.getDocument().insertString(
//                                textPane.getCaretPosition(),
//                                addText,
//                                attrSet);
//
//                    }
//                    break;
//
//                    case TAG: {
//                        //若未选中则结束
//                        if (textPane.getSelectedText() == null) break;
//
//                        String selected = textPane.getSelectedText();
//
//                        //如果为标签,则进行替换
//                        if (selected.startsWith("<") && selected.endsWith(">")) {
//
//                            //获取所有标签名
//                            List<String> tagList = new ArrayList<String>();
//                            for (Map.Entry<String, List<Element>> entry : tagMap.entrySet()) {
//                                tagList.add(entry.getKey().toString());
//                            }
//
//                            //将标签排序
//                            Collections.sort(tagList);
//
//                            //选择标签
//                            Object selectedTag = JOptionPane.showInputDialog(editor,
//                                    "Choose one", "Input",
//                                    JOptionPane.INFORMATION_MESSAGE, null,
//                                    tagList.toArray(), tagList.get(0));
//
//                            if (selectedTag != null) {
//                                //结束标签
//                                if (selected.charAt(1) == '/') {
//                                    String node = selected.substring(2, selected.length() - 1);
//
//                                    String str = textPane.getText(0, textPane.getCaretPosition());
//                                    int strLen = str.length();
//                                    int nodeLen = node.length();
//                                    int nodeCount = 0;
//                                    while (true) {
//                                        String sub = str.substring(strLen - nodeLen - 2, strLen - 1);
//                                        if (sub.equals("/" + node)) {
//                                            nodeCount++;
//                                        } else if (sub.equals("<" + node)) {
//                                            nodeCount--;
//                                            if (nodeCount == 0) break;
//                                        }
//                                        strLen--;
//                                    }
//
//                                    int start = strLen - nodeLen - 2;
//                                    int end = textPane.getCaretPosition();
//
//                                    String rep = textPane.getText(start, end - start);
//                                    rep = rep.substring(rep.indexOf(">") + 1, rep.lastIndexOf("<"));
//
//
//                                    textPane.select(start, end);
//                                    textPane.replaceSelection(null);
//                                    textPane.getDocument().insertString(
//                                            textPane.getCaretPosition(),
//                                            rep,
//                                            attrDefault);
//                                    textPane.getDocument().insertString(
//                                            textPane.getCaretPosition() - rep.length(),
//                                            "<" + selectedTag + ">",
//                                            attrSet);
//                                    textPane.getDocument().insertString(
//                                            textPane.getCaretPosition(),
//                                            "</" + selectedTag + ">",
//                                            attrSet);
//
//                                    textUndoManager.pushOperate(textUndoManager.REPLACETAG);
//                                    editor.setModified(true);
//
//                                    //开始标签
//                                } else {
//                                    String node = selected.substring(1,
//                                            selected.indexOf(" ") != -1 ? selected.indexOf(" ") : selected.indexOf(">"));
//
//                                    String str = textPane.getText(textPane.getSelectionStart(), textPane.getText().length() - textPane.getSelectionStart());
//                                    int nodeLen = node.length();
//                                    int nodeCount = 0, nodePos = 0;
//                                    while (true) {
//                                        String sub = str.substring(nodePos + 0, nodePos + nodeLen + 1);
//                                        if (sub.equals("<" + node)) {
//                                            nodeCount++;
//                                        } else if (sub.equals("/" + node)) {
//                                            nodeCount--;
//                                            if (nodeCount == 0) break;
//                                        }
//                                        nodePos++;
//                                    }
//
//                                    int start = textPane.getSelectionStart();
//                                    int end = textPane.getSelectionStart() + nodePos + nodeLen + 2;
//
//                                    String rep = textPane.getText(start, end - start);
//                                    rep = rep.substring(rep.indexOf(">") + 1, rep.lastIndexOf("<"));
//
//                                    textPane.select(start, end);
//                                    textPane.replaceSelection(null);
//                                    textPane.getDocument().insertString(
//                                            textPane.getCaretPosition(),
//                                            rep,
//                                            attrDefault);
//                                    textPane.getDocument().insertString(
//                                            textPane.getCaretPosition() - rep.length(),
//                                            "<" + selectedTag + ">",
//                                            attrSet);
//                                    textPane.getDocument().insertString(
//                                            textPane.getCaretPosition(),
//                                            "</" + selectedTag + ">",
//                                            attrSet);
//
//                                    textUndoManager.pushOperate(textUndoManager.REPLACETAG);
//                                    editor.setModified(true);
//
//                                }
//                            }
//                        }
//                    }
//                    break;
//
//                    case ATTR: {
//                        //若未选中则结束
//                        if (textPane.getSelectedText() == null) break;
//
//                        String selected = textPane.getSelectedText();
//
//                        //获取当前光标的位置
//                        int caretPosition = textPane.getCaretPosition();
//
//                        //获取当前文本的长度
//                        int textLength = textPane.getText().length();
//
//                        //自动匹配选择标签
//                        int startPosition = 0;
//                        int endPosition = textLength;
//
//                        String startString = textPane.getText(startPosition, caretPosition - startPosition);
//                        String endString = textPane.getText(caretPosition, endPosition - caretPosition);
//
//                        if (selected.matches("[a-zA-Z0-9_]*=\".*\"") &&
//                                startString.lastIndexOf("<") > startString.lastIndexOf(">") &&
//                                endString.indexOf(">") < endString.indexOf("<") &&
//                                endString.indexOf(">") != -1) {
//
//                            int start = startString.lastIndexOf("<");
//                            int end = textPane.getCaretPosition() + endString.indexOf(">");
//
//                            String locTag = textPane.getText(start, end - start + 1);
//
//                            String node = locTag.split(" ")[0].replaceAll("<|>", "");
//
//                            //获取属性列表
//                            List<String> attrList = new ArrayList<String>();
//                            HashMap<String, String> attrMap = new HashMap<String, String>();
//                            List<Element> tagAttr = tagMap.get(node);
//                            if (tagAttr == null) {
//                                break;
//                            }
//                            for (Element i : tagAttr) {
//                                attrList.add(i.attributeValue("name"));
//                                attrMap.put(i.attributeValue("name"), i.attributeValue("value"));
//                            }
//
//                            //将属性排序
//                            Collections.sort(attrList);
//
//                            //选择属性
//                            Object selectedAttr = JOptionPane.showInputDialog(editor,
//                                    "Choose one", "Input",
//                                    JOptionPane.INFORMATION_MESSAGE, null,
//                                    attrList.toArray(), attrList.get(0));
//
//                            if (selectedAttr != null) {
//                                //获取属性数组
//                                String[] attrValueArray = attrMap.get(selectedAttr).split(" ");
//
//                                //获取属性值
//                                Object attrValue = null;
//                                if (attrValueArray[0].isEmpty()) {
//                                    attrValue = JOptionPane.showInputDialog(
//                                            mxResources.get("pleaseInputText"));
//                                } else {
//                                    //将属性值排序
//                                    Arrays.sort(attrValueArray);
//                                    attrValue = JOptionPane.showInputDialog(editor,
//                                            "Choose one", "Input",
//                                            JOptionPane.INFORMATION_MESSAGE, null,
//                                            attrValueArray, attrValueArray[0]);
//                                }
//
//                                if (attrValue != null) {
//                                    textPane.replaceSelection(null);
//                                    textPane.getDocument().insertString(
//                                            textPane.getCaretPosition(),
//                                            selectedAttr + "=\"" + attrValue + "\"",
//                                            attrSet);
//
//                                    textUndoManager.pushOperate(textUndoManager.REPLACEATTR);
//                                    editor.setModified(true);
//
//                                }
//                            }
//                        }
//
//                    }
//                    break;
//
//                }
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
