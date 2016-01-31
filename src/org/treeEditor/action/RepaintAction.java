package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class RepaintAction extends BasicAbstractAction {
    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);
//        DataTree dataTree = editor.getDataTree();

//        if (editor != null) {
//            HashMap<Integer, String> curMap;
//            String curStr;
//            if (editor.curGraphComponent == editor.chGraphComponent) {
//                curMap = dataTree.getChMap();
//                curStr = editor.textScrollPane.getChTextPane().getText();
//            } else {
//                curMap = dataTree.getEnMap();
//                curStr = editor.textScrollPane.getEnTextPane().getText();
//            }
//
//            int key = editor.toolBarComboBoxIndex;
//
//            //将信息保存至HashMap中
//            curMap.put(key, curStr);
//
//            TreeOnXML.repaintTree(editor);
//
//        }
    }
}
