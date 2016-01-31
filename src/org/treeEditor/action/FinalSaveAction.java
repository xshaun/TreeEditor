package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class FinalSaveAction extends BasicAbstractAction {


    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

//        if (editor != null) {
//            if (editor.getCurrentFile() == null) {
//                JOptionPane.showMessageDialog(editor, "无XML文件");
//                return;
//            }
//
//            String filename = editor.getCurrentFile().getAbsolutePath();
//            int choose = JOptionPane.showConfirmDialog(editor, "确定要进行最终保存？");
//            try {
//                if (choose == JOptionPane.YES_OPTION) {
//                    DataTree dataTree = editor.getDataTree();
//                    HashMap<Integer, String> enMap = dataTree.getEnMap();
//                    HashMap<Integer, String> chMap = dataTree.getChMap();
//
//                    int pageComboIndex = editor.toolBarComboBoxIndex;
//
//                    //检测图形面板数据与文本信息是否一致
//                    String graphString = TreeOnXML.getSaveString(editor.getEnGraphComponent(), dataTree.getEnAttrMap());
//                    if (graphString == null) {
//                        JOptionPane.showMessageDialog(null,
//                                mxResources.get("english") + "==>" + mxResources.get("getGraphTextError"));
//                        return;
//                    }
//                    TextPane textPane = editor.getTextScrollPane().getEnTextPane();
//                    TextUndoManager textUndoManager = textPane.getTextUndoManager();
//                    String textString = textPane.getText();
//                    if (!graphString.equals(textString) &&
//                            JOptionPane.showConfirmDialog(null,
//                                    mxResources.get("english") + " => " + mxResources.get("ifOverWriteText")) ==
//                                    JOptionPane.YES_OPTION) {
//                        textPane.setText(graphString);
//                        textUndoManager.pushOperate(textUndoManager.SETTEXT);
//                        editor.setModified(true);
//                    }
//
//                    graphString = TreeOnXML.getSaveString(editor.getChGraphComponent(), dataTree.getChAttrMap());
//                    if (graphString == null) {
//                        JOptionPane.showMessageDialog(null,
//                                mxResources.get("chinese") + "==>" + mxResources.get("getGraphTextError"));
//                        return;
//                    }
//                    textPane = editor.getTextScrollPane().getChTextPane();
//                    textUndoManager = textPane.getTextUndoManager();
//                    textString = textPane.getText();
//                    if (!graphString.equals(textString) &&
//                            JOptionPane.showConfirmDialog(null,
//                                    mxResources.get("chinese") + " => " + mxResources.get("ifOverWriteText")) ==
//                                    JOptionPane.YES_OPTION) {
//                        textPane.setText(graphString);
//                        textUndoManager.pushOperate(textUndoManager.SETTEXT);
//                        editor.setModified(true);
//                    }
//
//                    //从文字面板获取需要保存的数据
//                    String chStr = editor.getTextScrollPane().getChTextPane().getText();
//                    String enStr = editor.getTextScrollPane().getEnTextPane().getText();
//
//                    //将信息保存至HashMap中
//                    chMap.put(pageComboIndex, chStr);
//                    enMap.put(pageComboIndex, enStr);
//
//                    //去除添加的修改标记
//                    for (int i = 0; i < chMap.size(); i++) {
//                        String temp = chMap.get(i);
//                        if (temp != null) {
//                            temp = temp.replaceAll("<a m=\"t\"", "<a");
//                        }
//                        chMap.put(i, temp);
//                    }
//                    for (int i = 0; i < enMap.size(); i++) {
//                        String temp = enMap.get(i);
//                        if (temp != null) {
//                            temp = temp.replaceAll("<a m=\"t\"", "<a");
//                        }
//                        enMap.put(i, temp);
//                    }
//
//                    //将HashMap中的信息写入文件
//                    boolean saveSuccess = TreeOnXML.saveToFile(dataTree, filename);
//
//                    //保存成功，设置新状态
//                    if (saveSuccess) {
//                        editor.setModified(false);
//                        editor.setCurrentFile(new File(filename));
//                    }
//                }
//            } catch (Throwable ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(editor,
//                        ex.toString(), mxResources.get("error"),
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        }
    }
}
