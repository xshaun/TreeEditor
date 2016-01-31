package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Created by boy on 16-1-31.
 */
public class ShowThisTagsAction extends BasicAbstractAction {


    /**
     * 返回字符串中不可比配XML开始标记
     */
    private ArrayList<String> notMatchStartTag(String str) {
        ArrayList<String> startTag = new ArrayList<String>();

        while (str.contains("<") && str.contains(">")) {
            String tag = str.substring(str.indexOf('<'), str.indexOf('>') + 1);

            if (tag.startsWith("</")) {
                startTag.remove(startTag.size() - 1);
            } else {
                startTag.add(tag);
            }
            str = str.substring(0, str.indexOf('<')) + str.substring(str.indexOf('>') + 1, str.length());
        }
        if (startTag.size() > 0) {
            return startTag;
        }
        return null;
    }

    /**
     * 返回字符串中不可比配XML结束标记
     */
    private ArrayList<String> notMatchEndTag(String str) {
        ArrayList<String> endTag = new ArrayList<String>();

        while (str.contains("<") && str.contains(">")) {
            String tag = str.substring(str.lastIndexOf('<'), str.lastIndexOf('>') + 1);

            if (tag.startsWith("</")) {
                endTag.add(tag);
            } else {
                endTag.remove(endTag.size() - 1);
            }
            str = str.substring(0, str.lastIndexOf('<')) + str.substring(str.lastIndexOf('>') + 1, str.length());

        }
        if (endTag.size() > 0) {
            return endTag;
        }
        return null;
    }

    /**
     * 返回带有完整XML标记的选择文本
     */
    private String getSelectedTextWithXML(ArrayList<String> startTag, String selected, ArrayList<String> endTag) {

        if (startTag == null && endTag == null) {
            return selected;
        }

        if (startTag != null && endTag == null) {
            String temp = "";
            for (int i = 0; i < startTag.size(); i++) {
                temp += startTag.get(i);
            }
            return temp + selected;
        }

        if (startTag == null && endTag != null) {
            String temp = "";
            for (int i = endTag.size() - 1; i >= 0; i--) {
                temp += endTag.get(i);
            }
            return selected + temp;
        }

        String lastRemoveStartTag = "", lastRemoveEndTag = "";
        while (true) {
            if (!(startTag.size() > 0 && endTag.size() > 0)) break;

            String start = startTag.get(0);
            String end = endTag.get(0);
            end = end.replaceAll("[/>]", "");

            if (start.startsWith(end + " ") ||
                    start.startsWith(end + ">")) {
                lastRemoveStartTag = startTag.remove(0);
                lastRemoveEndTag = endTag.remove(0);
            } else {
                break;
            }
        }

        String startTemp = lastRemoveStartTag, endTemp = "";
        for (int i = 0; i < startTag.size(); i++) {
            startTemp += startTag.get(i);
        }
        for (int i = endTag.size() - 1; i >= 0; i--) {
            endTemp += endTag.get(i);
        }
        endTemp += lastRemoveEndTag;

        return startTemp + selected + endTemp;
    }

    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

//        if (editor != null) {
//            DataTree dataTree = editor.getDataTree();
//            int toolBarComboBoxIndex = editor.getToolBarComboBoxIndex();
//
//            //获取原始文本数据
//            String text;
//            TextPane textPane;
//            if (editor.curGraphComponent == editor.chGraphComponent) {
//                text = dataTree.getChMap().get(toolBarComboBoxIndex);
//                textPane = editor.getTextScrollPane().getChTextPane();
//            } else {
//                text = dataTree.getEnMap().get(toolBarComboBoxIndex);
//                textPane = editor.getTextScrollPane().getEnTextPane();
//            }
//
//            TextUndoManager textUndoManager = textPane.getTextUndoManager();
//            textPane.setEditable(true);
//
//            //获取文字面板中的数据
//            String string = textPane.getText();
//            String startString = string.substring(0, textPane.getSelectionStart()).replaceAll("<[^>]*>", "").replaceAll(" +", " ");
//            String selectedString = textPane.getSelectedText().replaceAll("<[^>]*>", "").replaceAll(" +", " ");
//            String endString = string.substring(textPane.getSelectionEnd()).replaceAll("<[^>]*>", "").replaceAll(" +", " ");
////
////				System.out.println("startString:"+startString);
////				System.out.println("selectedString:"+selectedString);
////				System.out.println("endString:"+endString);
//
//            int startIndex = -1, endIndex = -1;
//            if (selectedString != null) {
//                for (int i = text.length() - 1; i >= 0; i--) {
//                    String temp = text.substring(i).replaceAll("[" + BasicGraphEditor.LINE_TAB + "]", "").replaceAll("(<[^>]*>)+", " ").replaceAll(" +", " ").trim();
//                    if (temp.equals((selectedString + endString).trim())) {
//                        startIndex = i;
//                        break;
//                    }
////						System.out.println("temp   :"+temp);
////						System.out.println("sel+end:"+(selectedString + endString));
//                }
////					System.out.println("startIndex:"+startIndex);
//
//                for (int i = 0; i <= text.length(); i++) {
//                    String temp = text.substring(0, i).replaceAll("[" + BasicGraphEditor.LINE_TAB + "]", "").replaceAll("(<[^>]*>)+", " ").replaceAll(" +", " ").trim();
//                    if (temp.equals((startString + selectedString).trim())) {
//                        endIndex = i;
//                        break;
//                    }
//                }
////					System.out.println("endIndex:"+endIndex);
//            }
//
//            if (startIndex != -1 && endIndex != -1) {
//                String startText = text.substring(0, startIndex);
//                String selectedText = text.substring(startIndex, endIndex);
//                String endText = text.substring(endIndex, text.length());
//                String newStartText = startText.replaceAll("(</?[^(a|s|>)]+>)+", " ");
//                String newEndText = endText.replaceAll("(</?[^(a|s|>)]+>)+", " ");
//
////					System.out.println("startText:"+startText);
////					System.out.println("selectedText:"+selectedText);
////					System.out.println("endText:"+endText);
//
//                //处理选中区标记
//                String newSelectedText = getSelectedTextWithXML(
//                        notMatchStartTag(startText.replaceAll("<[as][^>]*>", "").replaceAll("</[as]>", "")),
//                        selectedText,
//                        notMatchEndTag(endText.replaceAll("<[as][^>]*>", "").replaceAll("</[as]>", ""))
//                );
//
//                //添加修改标记符
//                if (!newStartText.startsWith("<a m=\"t\"")) {
//                    newStartText = newStartText.replaceFirst("<a", "<a m=\"t\"");
//                }
//
////					System.out.println(newStartText);
////					System.out.println(newSelectedText);
////					System.out.println(newEndText);
//
//                // 显示文本数据
//                String newText = newStartText + newSelectedText + newEndText;
//                newText = newText.replaceAll("[" + BasicGraphEditor.LINE_TAB + "]", "").replaceAll(" +", " ")
//                        .replaceAll("> ", ">").replaceAll(" <", "<");
//
//                //获得显示面板
//                CustomGraphComponent curGraphComponent = editor.getCurGraphComponent();
//                CustomGraph curGraph = curGraphComponent.getGraph();
//
//                // 判断中英文
//                boolean curIsCh = false;
//                if (curGraphComponent == editor.getChGraphComponent()) {
//                    curIsCh = true;
//                } else if (curGraphComponent == editor.getEnGraphComponent()) {
//                    curIsCh = false;
//                } else {
//                    return;
//                }
//
//                //获得显示的数据
//                String curStr = newText;
//                org.dom4j.Document curXML = TreeOnXML.convertXML(curStr.replaceAll("[" + BasicGraphEditor.LINE_TAB + "]", "").trim());
//                if (curXML == null) return;
//
//                //设置内容超出后自动剪裁
//                curGraph.setLabelsClipped(true);
//
//                //清空面板
//                curGraph.selectAll();
//                curGraph.removeCells();
//
//                //绘制树形图
//                TreeOnXML.creatTree(
//                        curGraphComponent,
//                        curXML,
//                        TreeOnXML.VERTEX_X = 50,
//                        10,
//                        curIsCh ? dataTree.getChAttrMap() : dataTree.getEnAttrMap()
//                );
//
//                //设置文本
//                textPane.setText(newText);
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
