package org.treeEditor;

import java.awt.Font;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.treeEditor.ui.BasicGraphEditor;
import org.treeEditor.ui.EditorMenuBar;

public class TreeEditor extends BasicGraphEditor {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public TreeEditor() {
        super(" -welcome TreeEditor- ");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        /**
         * BeautyEye 风格
         */
        try {
            // 普通不透明边框
            BeautyEyeLNFHelper.frameBorderStyle =
                    BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;

            // 使用BeautyEye L&F
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper
                    .launchBeautyEyeLNF();

            // 隐藏“设置”按钮
            UIManager.put("RootPane.setupButtonVisible", false);

            // 解决 关于win7平台下某些java版本上的文本字体发虚的问题
            initGlobalFontSetting(new Font("微软雅黑", 5, 14));

            // 解决遇到输入法 输入框白屏的问题
            System.setProperty("sun.java2d.noddraw", "true");

        } catch (Exception e) {
            //* should add log */
        }

        TreeEditor editor = new TreeEditor();
        editor.createFrame(new EditorMenuBar(editor)).setVisible(true);

    }

}
