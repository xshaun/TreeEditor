package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class FontNameAction extends BasicAbstractAction {
    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

//        if (editor != null) {
//            //获取字体列表
//            List<String> fontNameCombo = new ArrayList<String>();
//            Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
//            for (Font f : fonts) {
//                fontNameCombo.add(f.getName());
//            }
//
//            //选择字体
//            Object selectedValue = JOptionPane.showInputDialog(editor,
//                    "Choose one", "Input",
//                    JOptionPane.INFORMATION_MESSAGE, null,
//                    fontNameCombo.toArray(), fontNameCombo.get(0));
//            if (selectedValue == null) return;
//
//            //设置字体
//            JTextPane textPane = editor.getTextScrollPane().getChTextPane();
//            Font textFont = textPane.getFont();
//            textPane.setFont(new Font(selectedValue.toString(), textFont.getStyle(), textFont.getSize()));
//            textPane = editor.getTextScrollPane().getEnTextPane();
//            textFont = textPane.getFont();
//            textPane.setFont(new Font(selectedValue.toString(), textFont.getStyle(), textFont.getSize()));
//
//        }
    }
}
