package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class NextAction extends BasicAbstractAction {

    /**
     *
     */
    protected void resetEditor(BasicGraphEditor editor) {
        editor.setModified(false);
        editor.clearUndoManager();
    }

    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

        if (editor != null) {
//            //如果未修改或者放弃保存
//            if (!editor.isModified()
//                    || JOptionPane.showConfirmDialog(editor,
//                    mxResources.get("loseChanges")) == JOptionPane.YES_OPTION) {
//                //将状态设置为未修改、清空操作记录
//                resetEditor(editor);
//
//                //检测页号并进行跳转
//                int itemSelectIndex = editor.getToolBarComboBoxIndex() + 1;
//                if (itemSelectIndex >= editor.getDataTree().getTreeCount()) {
//                    JOptionPane.showMessageDialog(editor, mxResources.get("lastPage"));
//                } else {
//                    //设置页号
//                    editor.setToolBarComboBoxIndex(itemSelectIndex);
//                    EditorToolBar.setPageCombo(editor);
//                    //绘制树形结构
//                    String fileName = editor.getCurrentFile().getName();
//                    if (fileName.endsWith(".xml")) {
//                        TreeOnXML.creatTree(editor);
//                    }
//
//                    resetEditor(editor);
//                }
//            }
        }
    }
}
