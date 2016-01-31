package org.treeEditor.action;

import org.treeEditor.ui.BasicGraphEditor;

import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by boy on 16-1-31.
 */
public class OpenAction extends BasicAbstractAction {


    /**
     *
     */
    protected void resetEditor(BasicGraphEditor editor) {
        editor.setModified(false);
        editor.clearUndoManager();
    }

    /*
     * Read XML format
     */
    protected void openXml(BasicGraphEditor editor, File file) {
//        DataTree dataTree = editor.getDataTree();
//        if (!TreeOnXML.readData(file, dataTree)) return;
//
//        //设置页号
//        editor.setToolBarComboBoxIndex(0);
//        EditorToolBar.setPageCombo(editor);
//
//        //更新页码选项框
//        EditorToolBar.update(editor);
//
//        //绘制树形结构
//        TreeOnXML.creatTree(editor);
//
//        editor.setCurrentFile(file);
//        resetEditor(editor);
    }


    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

//        if (editor != null) {
//            mxGraph graph = editor.getCurGraphComponent().getGraph();
//
//            if (!editor.isModified()
//                    || JOptionPane.showConfirmDialog(editor,
//                    mxResources.get("loseChanges")) == JOptionPane.YES_OPTION) {
//
//                JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "/data");
//
//                // Adds file filter for supported file format
//                fc.addChoosableFileFilter(new DefaultFileFilter(".xml",
//                        "XML Tree " + mxResources.get("file")
//                                + " (.xml)"));
//                fc.addChoosableFileFilter(new DefaultFileFilter(".mxe",
//                        "mxGraph Editor " + mxResources.get("file")
//                                + " (.mxe)"));
//
//                int rc = fc.showDialog(null, mxResources.get("openFile"));
//
//                if (rc == JFileChooser.APPROVE_OPTION) {
//                    try {
//                        //打开XML文件
//                        if (fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".xml")) {
//                            openXml(editor, fc.getSelectedFile());
//
//                        } else if (fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".mxe")) {
//                            Document document = mxXmlUtils
//                                    .parseXml(mxUtils.readFile(fc
//                                            .getSelectedFile()
//                                            .getAbsolutePath()));
//
//                            mxCodec codec = new mxCodec(document);
//                            codec.decode(
//                                    document.getDocumentElement(),
//                                    graph.getModel());
//                            editor.setCurrentFile(fc
//                                    .getSelectedFile());
//
//                            resetEditor(editor);
//
//                        }//打开不支持类型的文件
//                        else {
//                            JOptionPane.showMessageDialog(editor, mxResources.get("notSupport"));
//                        }
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                        JOptionPane.showMessageDialog(
//                                editor,
//                                ex.toString(),
//                                mxResources.get("error"),
//                                JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//        }
    }
}
