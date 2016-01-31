package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class GraphToText extends BasicAbstractAction {


    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

        if (editor != null) {
//            CustomGraphComponent curGraphComponent = editor.getCurGraphComponent();
//            CustomGraphComponent graphComponent;
//            TextPane textPane;
//            TextUndoManager textUndoManager;
//            HashMap<Integer, List<Attribute>> attrMap;
//
//            if (curGraphComponent == editor.getEnGraphComponent()) {
//                graphComponent = editor.getEnGraphComponent();
//                textPane = editor.getTextScrollPane().getEnTextPane();
//                textUndoManager = textPane.getTextUndoManager();
//                attrMap = editor.getDataTree().getEnAttrMap();
//            } else {
//                graphComponent = editor.getChGraphComponent();
//                textPane = editor.getTextScrollPane().getChTextPane();
//                textUndoManager = textPane.getTextUndoManager();
//                attrMap = editor.getDataTree().getChAttrMap();
//            }
//
//            //设置默认文字样式
//            SimpleAttributeSet attrDefault = new SimpleAttributeSet();
//            StyleConstants.setBold(attrDefault, false);
//            StyleConstants.setItalic(attrDefault, false);
//            StyleConstants.setUnderline(attrDefault, false);
//            StyleConstants.setForeground(attrDefault, Color.BLACK);
//            textPane.setCharacterAttributes(attrDefault, true);
//
//            String graphString = TreeOnXML.getSaveString(graphComponent, attrMap);
//            if (graphString == null) {
//                JOptionPane.showMessageDialog(null,
//                        mxResources.get("getGraphTextError"));
//                return;
//            }
//            String textString = textPane.getText();
//
//            if (!graphString.equals(textString) &&
//                    JOptionPane.showConfirmDialog(null,
//                            mxResources.get("ifOverWriteText")) ==
//                            JOptionPane.YES_OPTION) {
//                textPane.setText(graphString);
//                textUndoManager.pushOperate(textUndoManager.SETTEXT);
//                editor.setModified(true);
//            }
        }
    }
}
