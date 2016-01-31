package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by boy on 16-1-31.
 */

// itemStateChanged=true时 触发该事件 创建显示当前树形结构
public class GoAction implements ItemListener {

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
    public void itemStateChanged(ItemEvent e) {

        //将ItemEvent事件 转换为 ActionEvent
        ActionEvent ae = new ActionEvent(e.getSource(), e.getID(), e.paramString());
        BasicGraphEditor editor = BasicAbstractAction.getEditor(ae);

        if (editor != null) {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                //如果未修改或者放弃保存
//                if (!editor.isModified()
//                        || JOptionPane.showConfirmDialog(editor,
//                        mxResources.get("loseChanges")) == JOptionPane.YES_OPTION) {
//                    String itemSelect = e.getItemSelectable().getSelectedObjects()[0].toString();
//
//                    int itemSelectIndex = Integer.parseInt(itemSelect.substring(itemSelect.indexOf("Page: ") + 5, itemSelect.indexOf("/")).trim());
//                    itemSelectIndex--; // 设置的索引号比当前页码号少1
//
//                    if (itemSelectIndex < 0 || itemSelectIndex >= editor.getDataTree().getTreeCount()) {
//                        JOptionPane.showMessageDialog(editor, mxResources.get("pageNumberError"));
//
//                    } else {
//                        //设置当前页号
//                        editor.setToolBarComboBoxIndex(itemSelectIndex);
//                        EditorToolBar.setPageCombo(editor);
//                        //EditorToolBar.setPage(ui);
//                        //绘制树形结构
//                        String fileName = editor.getCurrentFile().getName();
//                        if (fileName.endsWith(".xml")) {
//                            TreeOnXML.creatTree(editor);
//                        }
//
//                        resetEditor(editor);
//                    }
//                } else {
//                    //恢复选择前的页号
//                    EditorToolBar.setPageCombo(editor);
//                }
//            }
        }
    }
}
