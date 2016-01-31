package org.treeEditor.action;

import com.mxgraph.io.mxCodec;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.*;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.view.mxGraph;
import org.treeEditor.ui.BasicGraphEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by boy on 16-1-31.
 */
public class SaveAction extends BasicAbstractAction {


    public static final boolean SHOWDIALOG = true;

    public static final boolean NOTSHOWDIALOG = false;

    /**
     *
     */
    protected boolean showDialog;

    /**
     *
     */
    public SaveAction(boolean showDialog) {
        this.showDialog = showDialog;
    }

    /**
     * @param editor
     * @param filename
     * @throws Exception
     */
    protected void saveXML(BasicGraphEditor editor, String filename) throws Exception {
//        DataTree dataTree = editor.getDataTree();
//        HashMap<Integer, String> enMap = dataTree.getEnMap();
//        HashMap<Integer, String> chMap = dataTree.getChMap();
//
//        int pageComboIndex = editor.toolBarComboBoxIndex;
//
//
//        /**
//         * 检测图形面板数据与文本信息是否一致
//         */
//        //英文
//        String graphString = TreeOnXML.getSaveString(editor.getEnGraphComponent(), dataTree.getEnAttrMap());
//        TextPane textPane = editor.getTextScrollPane().getEnTextPane();
//        TextUndoManager textUndoManager = textPane.getTextUndoManager();
//        String textString = textPane.getText();
//
//        if (graphString == null) {
//            JOptionPane.showMessageDialog(null,
//                    mxResources.get("english") + "==>" + mxResources.get("getGraphTextError"));
//            return;
//        }
//
//        if (textString.startsWith("<a m=\"t\"")) {
//            if (!graphString.equals(textString)) {
//                int choose = JOptionPane.showConfirmDialog(null,
//                        mxResources.get("english") + " => " + mxResources.get("ifOverWriteText"));
//                if (choose == JOptionPane.YES_OPTION) {
//                    textPane.setText(graphString);
//                    textUndoManager.pushOperate(textUndoManager.SETTEXT);
//                    editor.setModified(true);
//                    enMap.put(pageComboIndex, graphString);
//                } else if (choose == JOptionPane.NO_OPTION) {
//                    String enStr = editor.getTextScrollPane().getEnTextPane().getText();
//                    enMap.put(pageComboIndex, enStr);
//                } else {
//                    return;
//                }
//            } else {
//                enMap.put(pageComboIndex, graphString);
//            }
//        }
//
//        //中文
//        graphString = TreeOnXML.getSaveString(editor.getChGraphComponent(), dataTree.getChAttrMap());
//        textPane = editor.getTextScrollPane().getChTextPane();
//        textUndoManager = textPane.getTextUndoManager();
//        textString = textPane.getText();
//
//        if (graphString == null) {
//            JOptionPane.showMessageDialog(null,
//                    mxResources.get("chinese") + "==>" + mxResources.get("getGraphTextError"));
//            return;
//        }
//
//        if (textString.startsWith("<a m=\"t\"")) {
//            if (!graphString.equals(textString)) {
//                int choose = JOptionPane.showConfirmDialog(null,
//                        mxResources.get("chinese") + " => " + mxResources.get("ifOverWriteText"));
//
//                if (choose == JOptionPane.YES_OPTION) {
//                    textPane.setText(graphString);
//                    textUndoManager.pushOperate(textUndoManager.SETTEXT);
//                    editor.setModified(true);
//                    chMap.put(pageComboIndex, graphString);
//                } else if (choose == JOptionPane.NO_OPTION) {
//                    String chStr = editor.getTextScrollPane().getChTextPane().getText();
//                    chMap.put(pageComboIndex, chStr);
//                } else {
//                    return;
//                }
//            } else {
//                chMap.put(pageComboIndex, graphString);
//            }
//        }
//
//        //将HashMap中的信息写入文件
//        boolean saveSuccess = TreeOnXML.saveToFile(dataTree, filename);
//
//        //保存成功，设置新状态
//        if (saveSuccess) {
//            editor.setModified(false);
//            editor.setCurrentFile(new File(filename));
//        }

    }

    /**
     * /**
     * Saves XML+PNG format.
     */
    protected void saveXmlPng(BasicGraphEditor editor, String filename,
                              Color bg) throws IOException {
        mxGraphComponent graphComponent = editor.getcurrentGraphComponent();
        mxGraph graph = graphComponent.getGraph();

        // Creates the image for the PNG file
        BufferedImage image = mxCellRenderer.createBufferedImage(graph,
                null, 1, bg, graphComponent.isAntiAlias(), null,
                graphComponent.getCanvas());

        // Creates the URL-encoded XML data
        mxCodec codec = new mxCodec();
        String xml = URLEncoder.encode(
                mxXmlUtils.getXml(codec.encode(graph.getModel())), "UTF-8");
        mxPngEncodeParam param = mxPngEncodeParam
                .getDefaultEncodeParam(image);
        param.setCompressedText(new String[]{"mxGraphModel", xml});

        // Saves as a PNG file
        FileOutputStream outputStream = new FileOutputStream(new File(
                filename));
        try {
            mxPngImageEncoder encoder = new mxPngImageEncoder(outputStream,
                    param);

            if (image != null) {
                encoder.encode(image);

                editor.setModified(false);
                editor.setCurrentFile(new File(filename));
            } else {
                JOptionPane.showMessageDialog(graphComponent,
                        mxResources.get("noImageData"));
            }
        } finally {
            outputStream.close();
        }
    }

    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = getEditor(e);

//        if (editor != null) {
//            mxGraphComponent graphComponent = editor.getcurrentGraphComponent();
//            mxGraph graph = graphComponent.getGraph();
//            FileFilter xmlPngFilter = new DefaultFileFilter(".png",
//                    "PNG+XML " + mxResources.get("fileType") + " (.png)");
//            FileFilter vmlFileFilter = new DefaultFileFilter(".html",
//                    "VML " + mxResources.get("fileType") + " (.html)");
//            FileFilter selectedFilter = null;
//            String filename = null;
//            boolean dialogShown = false;
//
//            if (showDialog || editor.getCurrentFile() == null) {//另存为
//                String wd = System.getProperty("user.dir") + "/data";
//
//                JFileChooser fc = new JFileChooser(wd);
//
//                // Adds file filter for supported file format
//                fc.addChoosableFileFilter(new DefaultFileFilter(".xml",
//                        "XML Tree " + mxResources.get("fileType") + " (.xml)"));
//                fc.addChoosableFileFilter(new DefaultFileFilter(".bra",
//                        "Brackets Tree  " + mxResources.get("fileType") + " (.bra)"));
//                fc.addChoosableFileFilter(new DefaultFileFilter(".mxe",
//                        "mxGraph Editor " + mxResources.get("file") + " (.mxe)"));
//                fc.addChoosableFileFilter(new DefaultFileFilter(".txt",
//                        "Graph Drawing " + mxResources.get("fileType") + " (.txt)"));
//                fc.addChoosableFileFilter(vmlFileFilter);
//                fc.addChoosableFileFilter(new DefaultFileFilter(".html",
//                        "HTML " + mxResources.get("fileType") + " (.html)"));
//                fc.addChoosableFileFilter(new DefaultFileFilter(".svg",
//                        "SVG " + mxResources.get("fileType") + " (.svg)"));
//                fc.addChoosableFileFilter(xmlPngFilter);
//
//                // Adds a filter for each supported image format
//                Object[] imageFormats = ImageIO.getReaderFormatNames();
//
//                // Finds all distinct extensions
//                HashSet<String> formats = new HashSet<String>();
//
//                for (int i = 0; i < imageFormats.length; i++) {
//                    String ext = imageFormats[i].toString().toLowerCase();
//                    formats.add(ext);
//                }
//
//                imageFormats = formats.toArray();
//
//                for (int i = 0; i < imageFormats.length; i++) {
//                    String ext = imageFormats[i].toString();
//                    fc.addChoosableFileFilter(new DefaultFileFilter("."
//                            + ext, ext.toUpperCase() + " "
//                            + mxResources.get("fileType") + " (." + ext + ")"));
//                }
//
//                // Adds filter that accepts all supported image formats
//                fc.addChoosableFileFilter(new DefaultFileFilter.ImageFileFilter(
//                        mxResources.get("allImages")));
//
//
//                int rc = fc.showDialog(null, mxResources.get("saveAs"));
//                dialogShown = true;
//
//                if (rc != JFileChooser.APPROVE_OPTION) {
//                    return;
//                }
//
//                filename = fc.getSelectedFile().getAbsolutePath();
//                selectedFilter = fc.getFileFilter();
//
//                if (selectedFilter instanceof DefaultFileFilter) {
//                    String ext = ((DefaultFileFilter) selectedFilter)
//                            .getExtension();
//
//                    if (!filename.toLowerCase().endsWith(ext)) {
//                        filename += ext;
//                    }
//                }
//
//                if (new File(filename).exists()
//                        && JOptionPane.showConfirmDialog(graphComponent,
//                        mxResources.get("overwriteExistingFile")) != JOptionPane.YES_OPTION) {
//                    return;
//                }
//            } else {
//                filename = editor.getCurrentFile().getAbsolutePath();
//            }
//
//            try {
//                String ext = filename.substring(filename.lastIndexOf('.') + 1);
//
//                if (ext.equalsIgnoreCase("xml")) {
//                    saveXML(editor, filename);
//                } else if (ext.equalsIgnoreCase("mxe")) {
//                    mxCodec codec = new mxCodec();
//                    String xml = mxXmlUtils.getXml(codec.encode(graph
//                            .getModel()));
//
//                    mxUtils.writeFile(xml, filename);
//
//                    editor.setModified(false);
//                    editor.setCurrentFile(new File(filename));
//                } else if (ext.equalsIgnoreCase("txt")) {
//
//                    String content = mxGdCodec.encode(graph);
//                    mxUtils.writeFile(content, filename);
//
//                } else if (selectedFilter == vmlFileFilter) {
//
//                    mxUtils.writeFile(mxXmlUtils.getXml(mxCellRenderer
//                            .createVmlDocument(graph, null, 1, null, null)
//                            .getDocumentElement()), filename);
//
//                } else if (ext.equalsIgnoreCase("html")) {
//
//                    mxUtils.writeFile(mxXmlUtils.getXml(mxCellRenderer
//                            .createHtmlDocument(graph, null, 1, null, null)
//                            .getDocumentElement()), filename);
//
//                } else if (ext.equalsIgnoreCase("svg")) {
//
//                    mxSvgCanvas canvas = (mxSvgCanvas) mxCellRenderer
//                            .drawCells(graph, null, 1, null,
//                                    new mxCellRenderer.CanvasFactory() {
//                                        public mxICanvas createCanvas(
//                                                int width, int height) {
//                                            mxSvgCanvas canvas = new mxSvgCanvas(
//                                                    mxDomUtils.createSvgDocument(
//                                                            width, height));
//                                            canvas.setEmbedded(true);
//
//                                            return canvas;
//                                        }
//
//                                    });
//
//                    mxUtils.writeFile(mxXmlUtils.getXml(canvas.getDocument()), filename);
//
//                } else {
//                    Color bg = null;
//
//                    if ((!ext.equalsIgnoreCase("gif") && !ext
//                            .equalsIgnoreCase("png"))
//                            || JOptionPane.showConfirmDialog(
//                            graphComponent, mxResources
//                                    .get("transparentBackground")) != JOptionPane.YES_OPTION) {
//                        bg = graphComponent.getBackground();
//                    }
//
//                    if (selectedFilter == xmlPngFilter
//                            || (editor.getCurrentFile() != null
//                            && ext.equalsIgnoreCase("png") && !dialogShown)) {
//                        saveXmlPng(editor, filename, bg);
//                    } else {
//                        BufferedImage image = mxCellRenderer
//                                .createBufferedImage(graph, null, 1, bg,
//                                        graphComponent.isAntiAlias(), null,
//                                        graphComponent.getCanvas());
//
//                        if (image != null) {
//                            ImageIO.write(image, ext, new File(filename));
//                        } else {
//                            JOptionPane.showMessageDialog(graphComponent,
//                                    mxResources.get("noImageData"));
//                        }
//                    }
//                }
//            } catch (Throwable ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(graphComponent,
//                        ex.toString(), mxResources.get("error"),
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        }
    }
}
