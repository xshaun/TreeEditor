package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class ConvertDERTreeAction extends BasicAbstractAction {
    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

        if (editor != null) {
//            try {
//                EditorWaitBar waitBar =
//                        new EditorWaitBar(mxResources.get("DERtree"));
//
//                if (JOptionPane.showConfirmDialog(null,
//                        mxResources.get("needTimePleaseWait")) ==
//                        JOptionPane.YES_OPTION
//                        ) {
//                    if (getNewXML.setResult()) {
//                        waitBar.show.setText("Finished!");
//                        waitBar.button.setText(waitBar.OK);
//                    }
//                } else {
//                    waitBar.dispose();
//                }
//
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
        }
    }
}
