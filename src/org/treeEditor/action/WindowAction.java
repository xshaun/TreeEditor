package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class WindowAction extends BasicAbstractAction {


    public static final boolean SPLIT = true;

    public static final boolean TABBED = false;

    /**
     * 标志是否进行对比显示
     */
    protected boolean split;

    /**
     * @param
     */
    public WindowAction(boolean split) {
        this.split = split;
    }

    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

//        if (editor != null) {
//            if (this.split && !editor.alreadyAddSplitdPane) {
//                //删去tabbedPane，增加splitPane
//                editor.remove(editor.tabbedPane);
//                editor.alreadyAddTabbedPane = false;
//                editor.installToolBarAndSplitdPane();
//                editor.validate();
//                editor.repaint();
//
//            } else if (!this.split && !editor.alreadyAddTabbedPane) {
//                //删去splitPane，增加tabbedPane
//                editor.remove(editor.splitPane);
//                editor.alreadyAddSplitdPane = false;
//                editor.installToolBarAndTabbedPane();
//                editor.validate();
//                editor.repaint();
//
//            }
//        }
    }
}
