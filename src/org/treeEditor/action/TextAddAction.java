package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class TextAddAction extends BasicAbstractAction {


    public static final int TEXT = 1;

    public static final int TAG = 2;

    public static final int ATTR = 3;

    public static final int BLANK = 4;

    protected int type;

    public TextAddAction(int type) {
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
//            //设置修改文字样式
//            SimpleAttributeSet attrSet = new SimpleAttributeSet();
//            StyleConstants.setItalic(attrSet, false);
//            StyleConstants.setBold(attrSet, true);
//            StyleConstants.setUnderline(attrSet, true);
//            StyleConstants.setForeground(attrSet, new Color(0xFF, 0x5F, 0x24));
//            //textPane.setCharacterAttributes(attrDefault, true);
//
//            switch (this.type) {
//                case TEXT:
//                    //获取文本
//                    String addText = JOptionPane.showInputDialog(mxResources.get("pleaseInputText"));
//
//                    //增加文本
//                    try {
//                        textPane.getDocument().insertString(
//                                textPane.getCaretPosition(),
//                                addText,
//                                attrSet);
//                    } catch (BadLocationException e1) {
//                        //e1.printStackTrace();
//                        JOptionPane.showMessageDialog(null,
//                                e1.toString(), mxResources.get("error"),
//                                JOptionPane.ERROR_MESSAGE);
//                    }
//                    break;
//
//                case TAG:
//                    //若未选中则结束
//                    if (textPane.getSelectedText() == null) break;
//
//                    //获取所有标签名
//                    List<String> tagList = new ArrayList<String>();
//                    for (Map.Entry<String, List<Element>> entry : tagMap.entrySet()) {
//                        tagList.add(entry.getKey().toString());
//                    }
//
//                    //将标签排序
//                    Collections.sort(tagList);
//
//                    //选择标签
//                    Object selectedTag = JOptionPane.showInputDialog(editor,
//                            "Choose one", "Input",
//                            JOptionPane.INFORMATION_MESSAGE, null,
//                            tagList.toArray(), tagList.get(0));
//
//                    if (selectedTag != null) {
//                        //增加文本
//                        try {
//                            textPane.getDocument().insertString(
//                                    textPane.getSelectionStart(),
//                                    "<" + selectedTag + ">",
//                                    attrSet);
//                            textPane.getDocument().insertString(
//                                    textPane.getSelectionEnd(),
//                                    "</" + selectedTag + ">",
//                                    attrSet);
//
//                            textUndoManager.pushOperate(textUndoManager.ADDTAG);
//                            editor.setModified(true);
//
//                        } catch (BadLocationException e1) {
//                            //e1.printStackTrace();
//                            JOptionPane.showMessageDialog(null,
//                                    e1.toString(), mxResources.get("error"),
//                                    JOptionPane.ERROR_MESSAGE);
//                        }
//                    }
//                    break;
//
//                case ATTR:
//                    //若未选中则结束
//                    if (textPane.getSelectedText() == null) break;
//
//                    //换取选中的文本
//                    String selected = textPane.getSelectedText();
//
//                    if (!selected.contains("/") && selected.startsWith("<") && selected.endsWith(">")) {
//
//                        String node = selected.split(" ")[0].replaceAll("<|>", "");
//
//                        //获取属性列表
//                        List<String> attrList = new ArrayList<String>();
//                        HashMap<String, String> attrMap = new HashMap<String, String>();
//                        List<Element> tagAttr = tagMap.get(node);
//                        if (tagAttr == null || tagAttr.isEmpty()) {
//                            break;
//                        }
//                        for (Element i : tagAttr) {
//                            attrList.add(i.attributeValue("name"));
//                            attrMap.put(i.attributeValue("name"), i.attributeValue("value"));
//                        }
//
//                        //将属性排序
//                        Collections.sort(attrList);
//
//                        //选择属性
//                        Object selectedAttr = JOptionPane.showInputDialog(editor,
//                                "Choose one", "Input",
//                                JOptionPane.INFORMATION_MESSAGE, null,
//                                attrList.toArray(), attrList.get(0));
//
//                        if (selectedAttr != null) {
//                            //获取属性数组
//                            String[] attrValueArray = attrMap.get(selectedAttr).split(" ");
//
//                            //获取属性值
//                            Object attrValue = null;
//                            if (attrValueArray[0].isEmpty()) {
//                                attrValue = JOptionPane.showInputDialog(
//                                        mxResources.get("pleaseInputText"));
//                            } else {
//                                //将属性值排序
//                                Arrays.sort(attrValueArray);
//                                attrValue = JOptionPane.showInputDialog(editor,
//                                        "Choose one", "Input",
//                                        JOptionPane.INFORMATION_MESSAGE, null,
//                                        attrValueArray, attrValueArray[0]);
//                            }
//
//                            if (attrValue != null) {
//                                try {
//                                    textPane.getDocument().insertString(
//                                            textPane.getSelectionEnd() - 1,
//                                            " " + selectedAttr + "=\"" + attrValue.toString() + "\"",
//                                            attrSet);
//
//                                    textUndoManager.pushOperate(textUndoManager.ADDATTR);
//                                    editor.setModified(true);
//
//                                } catch (BadLocationException e1) {
//                                    e1.printStackTrace();
//                                }
//
//                            }
//                        }
//                    }
//                    break;
//
//                case BLANK:
//                    try {
//                        textPane.getDocument().insertString(
//                                textPane.getCaretPosition(),
//                                " ",
//                                attrSet);
//
//                        textUndoManager.pushOperate(textUndoManager.ADDBLANK);
//                        editor.setModified(true);
//
//                    } catch (BadLocationException e1) {
//                        e1.printStackTrace();
//                    }
//                    break;
//
//            }
//
//            textPane.setEditable(false);
//            textPane.getCaret().setVisible(true);
//        }
    }
}
