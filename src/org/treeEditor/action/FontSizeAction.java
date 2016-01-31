package org.treeEditor.action;

import com.mxgraph.util.mxResources;
import org.treeEditor.ui.BasicGraphEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class FontSizeAction extends BasicAbstractAction {


    protected int size;

    public FontSizeAction(int value) {
        this.size = value;
    }

    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

        if (editor != null) {
            JTextPane textPane = editor.getTextScrollPane().getChTextPane();
            Font textFont = textPane.getFont();

            int valueInt = 0;

            if (this.size == 0) {
                String value = (String) JOptionPane.showInputDialog(
                        editor, mxResources.get("size"),
                        mxResources.get("fontSetup"), JOptionPane.PLAIN_MESSAGE, null, null, textFont.getSize());

                if (value != null) {
                    try {
                        valueInt = Integer.parseInt(value);
                        if (valueInt < 1) valueInt = textFont.getSize();
                    } catch (NumberFormatException ex) {
                        valueInt = textFont.getSize();
                        JOptionPane.showMessageDialog(editor, mxResources.get("pleaseInputInteger"));
                    }
                } else {
                    valueInt = textFont.getSize();
                }
            } else {
                valueInt = this.size;
            }

            textPane.setFont(new Font(textFont.getName(), textFont.getStyle(), valueInt));
            textPane = editor.getTextScrollPane().getEnTextPane();
            textPane.setFont(new Font(textFont.getName(), textFont.getStyle(), valueInt));

        }
    }
}
