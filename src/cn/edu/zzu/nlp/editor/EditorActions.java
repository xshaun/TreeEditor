package cn.edu.zzu.nlp.editor;

import cn.edu.zzu.nlp.editor.BasicGraphEditor.CustomGraph;
import cn.edu.zzu.nlp.editor.BasicGraphEditor.CustomGraphComponent;
import cn.edu.zzu.nlp.editor.EditorTextScrollPane.TextPane;
import cn.edu.zzu.nlp.editor.EditorTextScrollPane.TextUndoManager;
import cn.edu.zzu.nlp.tree.DataTree;
import cn.edu.zzu.nlp.tree.DefaultFileFilter;
import cn.edu.zzu.nlp.tree.TreeOnXML;
import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.canvas.mxSvgCanvas;
import com.mxgraph.io.mxCodec;
import com.mxgraph.io.mxGdCodec;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.*;
import com.mxgraph.util.mxCellRenderer.CanvasFactory;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.view.mxGraph;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.w3c.dom.Document;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

public class EditorActions {

    /**
     * @param e
     * @return Returns the graph for the given action event.
     */
    public static final BasicGraphEditor getEditor(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component component = (Component) e.getSource();

            while (component != null
                    && !(component instanceof BasicGraphEditor)) {
                component = component.getParent();
            }

            return (BasicGraphEditor) component;
        }

        return null;
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class NewAction extends AbstractAction {

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);

            if (editor != null) {

            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class SaveAction extends AbstractAction {

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
            DataTree dataTree = editor.getDataTree();
            HashMap<Integer, String> enMap = dataTree.getEnMap();
            HashMap<Integer, String> chMap = dataTree.getChMap();

            int pageComboIndex = editor.toolBarComboBoxIndex;


            /**
             * 检测图形面板数据与文本信息是否一致
             */
            //英文
            String graphString = TreeOnXML.getSaveString(editor.getEnGraphComponent(), dataTree.getEnAttrMap());
            TextPane textPane = editor.getTextScrollPane().getEnTextPane();
            TextUndoManager textUndoManager = textPane.getTextUndoManager();
            String textString = textPane.getText();

            if (graphString == null) {
                JOptionPane.showMessageDialog(null,
                        mxResources.get("english") + "==>" + mxResources.get("getGraphTextError"));
                return;
            }

            if (textString.startsWith("<a m=\"t\"")) {
                if (!graphString.equals(textString)) {
                    int choose = JOptionPane.showConfirmDialog(null,
                            mxResources.get("english") + " => " + mxResources.get("ifOverWriteText"));
                    if (choose == JOptionPane.YES_OPTION) {
                        textPane.setText(graphString);
                        textUndoManager.pushOperate(textUndoManager.SETTEXT);
                        editor.setModified(true);
                        enMap.put(pageComboIndex, graphString);
                    } else if (choose == JOptionPane.NO_OPTION) {
                        String enStr = editor.getTextScrollPane().getEnTextPane().getText();
                        enMap.put(pageComboIndex, enStr);
                    } else {
                        return;
                    }
                } else {
                    enMap.put(pageComboIndex, graphString);
                }
            }

            //中文
            graphString = TreeOnXML.getSaveString(editor.getChGraphComponent(), dataTree.getChAttrMap());
            textPane = editor.getTextScrollPane().getChTextPane();
            textUndoManager = textPane.getTextUndoManager();
            textString = textPane.getText();

            if (graphString == null) {
                JOptionPane.showMessageDialog(null,
                        mxResources.get("chinese") + "==>" + mxResources.get("getGraphTextError"));
                return;
            }

            if (textString.startsWith("<a m=\"t\"")) {
                if (!graphString.equals(textString)) {
                    int choose = JOptionPane.showConfirmDialog(null,
                            mxResources.get("chinese") + " => " + mxResources.get("ifOverWriteText"));

                    if (choose == JOptionPane.YES_OPTION) {
                        textPane.setText(graphString);
                        textUndoManager.pushOperate(textUndoManager.SETTEXT);
                        editor.setModified(true);
                        chMap.put(pageComboIndex, graphString);
                    } else if (choose == JOptionPane.NO_OPTION) {
                        String chStr = editor.getTextScrollPane().getChTextPane().getText();
                        chMap.put(pageComboIndex, chStr);
                    } else {
                        return;
                    }
                } else {
                    chMap.put(pageComboIndex, graphString);
                }
            }

            //将HashMap中的信息写入文件
            boolean saveSuccess = TreeOnXML.saveToFile(dataTree, filename);

            //保存成功，设置新状态
            if (saveSuccess) {
                editor.setModified(false);
                editor.setCurrentFile(new File(filename));
            }

        }

        /**
         * /**
         * Saves XML+PNG format.
         */
        protected void saveXmlPng(BasicGraphEditor editor, String filename,
                                  Color bg) throws IOException {
            mxGraphComponent graphComponent = editor.getCurGraphComponent();
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
            if (editor != null) {
                mxGraphComponent graphComponent = editor.getCurGraphComponent();
                mxGraph graph = graphComponent.getGraph();
                FileFilter xmlPngFilter = new DefaultFileFilter(".png",
                        "PNG+XML " + mxResources.get("fileType") + " (.png)");
                FileFilter vmlFileFilter = new DefaultFileFilter(".html",
                        "VML " + mxResources.get("fileType") + " (.html)");
                FileFilter selectedFilter = null;
                String filename = null;
                boolean dialogShown = false;

                if (showDialog || editor.getCurrentFile() == null) {//另存为
                    String wd = System.getProperty("user.dir") + "/data";

                    JFileChooser fc = new JFileChooser(wd);

                    // Adds file filter for supported file format
                    fc.addChoosableFileFilter(new DefaultFileFilter(".xml",
                            "XML Tree " + mxResources.get("fileType") + " (.xml)"));
                    fc.addChoosableFileFilter(new DefaultFileFilter(".bra",
                            "Brackets Tree  " + mxResources.get("fileType") + " (.bra)"));
                    fc.addChoosableFileFilter(new DefaultFileFilter(".mxe",
                            "mxGraph Editor " + mxResources.get("file") + " (.mxe)"));
                    fc.addChoosableFileFilter(new DefaultFileFilter(".txt",
                            "Graph Drawing " + mxResources.get("fileType") + " (.txt)"));
                    fc.addChoosableFileFilter(vmlFileFilter);
                    fc.addChoosableFileFilter(new DefaultFileFilter(".html",
                            "HTML " + mxResources.get("fileType") + " (.html)"));
                    fc.addChoosableFileFilter(new DefaultFileFilter(".svg",
                            "SVG " + mxResources.get("fileType") + " (.svg)"));
                    fc.addChoosableFileFilter(xmlPngFilter);

                    // Adds a filter for each supported image format
                    Object[] imageFormats = ImageIO.getReaderFormatNames();

                    // Finds all distinct extensions
                    HashSet<String> formats = new HashSet<String>();

                    for (int i = 0; i < imageFormats.length; i++) {
                        String ext = imageFormats[i].toString().toLowerCase();
                        formats.add(ext);
                    }

                    imageFormats = formats.toArray();

                    for (int i = 0; i < imageFormats.length; i++) {
                        String ext = imageFormats[i].toString();
                        fc.addChoosableFileFilter(new DefaultFileFilter("."
                                + ext, ext.toUpperCase() + " "
                                + mxResources.get("fileType") + " (." + ext + ")"));
                    }

                    // Adds filter that accepts all supported image formats
                    fc.addChoosableFileFilter(new DefaultFileFilter.ImageFileFilter(
                            mxResources.get("allImages")));


                    int rc = fc.showDialog(null, mxResources.get("saveAs"));
                    dialogShown = true;

                    if (rc != JFileChooser.APPROVE_OPTION) {
                        return;
                    }

                    filename = fc.getSelectedFile().getAbsolutePath();
                    selectedFilter = fc.getFileFilter();

                    if (selectedFilter instanceof DefaultFileFilter) {
                        String ext = ((DefaultFileFilter) selectedFilter)
                                .getExtension();

                        if (!filename.toLowerCase().endsWith(ext)) {
                            filename += ext;
                        }
                    }

                    if (new File(filename).exists()
                            && JOptionPane.showConfirmDialog(graphComponent,
                            mxResources.get("overwriteExistingFile")) != JOptionPane.YES_OPTION) {
                        return;
                    }
                } else {
                    filename = editor.getCurrentFile().getAbsolutePath();
                }

                try {
                    String ext = filename.substring(filename.lastIndexOf('.') + 1);

                    if (ext.equalsIgnoreCase("xml")) {
                        saveXML(editor, filename);
                    } else if (ext.equalsIgnoreCase("mxe")) {
                        mxCodec codec = new mxCodec();
                        String xml = mxXmlUtils.getXml(codec.encode(graph
                                .getModel()));

                        mxUtils.writeFile(xml, filename);

                        editor.setModified(false);
                        editor.setCurrentFile(new File(filename));
                    } else if (ext.equalsIgnoreCase("txt")) {

                        String content = mxGdCodec.encode(graph);
                        mxUtils.writeFile(content, filename);

                    } else if (selectedFilter == vmlFileFilter) {

                        mxUtils.writeFile(mxXmlUtils.getXml(mxCellRenderer
                                .createVmlDocument(graph, null, 1, null, null)
                                .getDocumentElement()), filename);

                    } else if (ext.equalsIgnoreCase("html")) {

                        mxUtils.writeFile(mxXmlUtils.getXml(mxCellRenderer
                                .createHtmlDocument(graph, null, 1, null, null)
                                .getDocumentElement()), filename);

                    } else if (ext.equalsIgnoreCase("svg")) {

                        mxSvgCanvas canvas = (mxSvgCanvas) mxCellRenderer
                                .drawCells(graph, null, 1, null,
                                        new CanvasFactory() {
                                            public mxICanvas createCanvas(
                                                    int width, int height) {
                                                mxSvgCanvas canvas = new mxSvgCanvas(
                                                        mxDomUtils.createSvgDocument(
                                                                width, height));
                                                canvas.setEmbedded(true);

                                                return canvas;
                                            }

                                        });

                        mxUtils.writeFile(mxXmlUtils.getXml(canvas.getDocument()), filename);

                    } else {
                        Color bg = null;

                        if ((!ext.equalsIgnoreCase("gif") && !ext
                                .equalsIgnoreCase("png"))
                                || JOptionPane.showConfirmDialog(
                                graphComponent, mxResources
                                        .get("transparentBackground")) != JOptionPane.YES_OPTION) {
                            bg = graphComponent.getBackground();
                        }

                        if (selectedFilter == xmlPngFilter
                                || (editor.getCurrentFile() != null
                                && ext.equalsIgnoreCase("png") && !dialogShown)) {
                            saveXmlPng(editor, filename, bg);
                        } else {
                            BufferedImage image = mxCellRenderer
                                    .createBufferedImage(graph, null, 1, bg,
                                            graphComponent.isAntiAlias(), null,
                                            graphComponent.getCanvas());

                            if (image != null) {
                                ImageIO.write(image, ext, new File(filename));
                            } else {
                                JOptionPane.showMessageDialog(graphComponent,
                                        mxResources.get("noImageData"));
                            }
                        }
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(graphComponent,
                            ex.toString(), mxResources.get("error"),
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class FinalSaveAction extends AbstractAction {

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);

            if (editor != null) {
                if (editor.getCurrentFile() == null) {
                    JOptionPane.showMessageDialog(editor, "无XML文件");
                    return;
                }

                String filename = editor.getCurrentFile().getAbsolutePath();
                int choose = JOptionPane.showConfirmDialog(editor, "确定要进行最终保存？");
                try {
                    if (choose == JOptionPane.YES_OPTION) {
                        DataTree dataTree = editor.getDataTree();
                        HashMap<Integer, String> enMap = dataTree.getEnMap();
                        HashMap<Integer, String> chMap = dataTree.getChMap();

                        int pageComboIndex = editor.toolBarComboBoxIndex;

                        //检测图形面板数据与文本信息是否一致
                        String graphString = TreeOnXML.getSaveString(editor.getEnGraphComponent(), dataTree.getEnAttrMap());
                        if (graphString == null) {
                            JOptionPane.showMessageDialog(null,
                                    mxResources.get("english") + "==>" + mxResources.get("getGraphTextError"));
                            return;
                        }
                        TextPane textPane = editor.getTextScrollPane().getEnTextPane();
                        TextUndoManager textUndoManager = textPane.getTextUndoManager();
                        String textString = textPane.getText();
                        if (!graphString.equals(textString) &&
                                JOptionPane.showConfirmDialog(null,
                                        mxResources.get("english") + " => " + mxResources.get("ifOverWriteText")) ==
                                        JOptionPane.YES_OPTION) {
                            textPane.setText(graphString);
                            textUndoManager.pushOperate(textUndoManager.SETTEXT);
                            editor.setModified(true);
                        }

                        graphString = TreeOnXML.getSaveString(editor.getChGraphComponent(), dataTree.getChAttrMap());
                        if (graphString == null) {
                            JOptionPane.showMessageDialog(null,
                                    mxResources.get("chinese") + "==>" + mxResources.get("getGraphTextError"));
                            return;
                        }
                        textPane = editor.getTextScrollPane().getChTextPane();
                        textUndoManager = textPane.getTextUndoManager();
                        textString = textPane.getText();
                        if (!graphString.equals(textString) &&
                                JOptionPane.showConfirmDialog(null,
                                        mxResources.get("chinese") + " => " + mxResources.get("ifOverWriteText")) ==
                                        JOptionPane.YES_OPTION) {
                            textPane.setText(graphString);
                            textUndoManager.pushOperate(textUndoManager.SETTEXT);
                            editor.setModified(true);
                        }

                        //从文字面板获取需要保存的数据
                        String chStr = editor.getTextScrollPane().getChTextPane().getText();
                        String enStr = editor.getTextScrollPane().getEnTextPane().getText();

                        //将信息保存至HashMap中
                        chMap.put(pageComboIndex, chStr);
                        enMap.put(pageComboIndex, enStr);

                        //去除添加的修改标记
                        for (int i = 0; i < chMap.size(); i++) {
                            String temp = chMap.get(i);
                            if (temp != null) {
                                temp = temp.replaceAll("<a m=\"t\"", "<a");
                            }
                            chMap.put(i, temp);
                        }
                        for (int i = 0; i < enMap.size(); i++) {
                            String temp = enMap.get(i);
                            if (temp != null) {
                                temp = temp.replaceAll("<a m=\"t\"", "<a");
                            }
                            enMap.put(i, temp);
                        }

                        //将HashMap中的信息写入文件
                        boolean saveSuccess = TreeOnXML.saveToFile(dataTree, filename);

                        //保存成功，设置新状态
                        if (saveSuccess) {
                            editor.setModified(false);
                            editor.setCurrentFile(new File(filename));
                        }
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(editor,
                            ex.toString(), mxResources.get("error"),
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class OpenAction extends AbstractAction {

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
            DataTree dataTree = editor.getDataTree();
            if (!TreeOnXML.readData(file, dataTree)) return;

            //设置页号
            editor.setToolBarComboBoxIndex(0);
            EditorToolBar.setPageCombo(editor);

            //更新页码选项框
            EditorToolBar.update(editor);

            //绘制树形结构
            TreeOnXML.creatTree(editor);

            editor.setCurrentFile(file);
            resetEditor(editor);
        }


        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);

            if (editor != null) {
                mxGraph graph = editor.getCurGraphComponent().getGraph();

                if (!editor.isModified()
                        || JOptionPane.showConfirmDialog(editor,
                        mxResources.get("loseChanges")) == JOptionPane.YES_OPTION) {

                    JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "/data");

                    // Adds file filter for supported file format
                    fc.addChoosableFileFilter(new DefaultFileFilter(".xml",
                            "XML Tree " + mxResources.get("file")
                                    + " (.xml)"));
                    fc.addChoosableFileFilter(new DefaultFileFilter(".mxe",
                            "mxGraph Editor " + mxResources.get("file")
                                    + " (.mxe)"));

                    int rc = fc.showDialog(null, mxResources.get("openFile"));

                    if (rc == JFileChooser.APPROVE_OPTION) {
                        try {
                            //打开XML文件
                            if (fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".xml")) {
                                openXml(editor, fc.getSelectedFile());

                            } else if (fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".mxe")) {
                                Document document = mxXmlUtils
                                        .parseXml(mxUtils.readFile(fc
                                                .getSelectedFile()
                                                .getAbsolutePath()));

                                mxCodec codec = new mxCodec(document);
                                codec.decode(
                                        document.getDocumentElement(),
                                        graph.getModel());
                                editor.setCurrentFile(fc
                                        .getSelectedFile());

                                resetEditor(editor);

                            }//打开不支持类型的文件
                            else {
                                JOptionPane.showMessageDialog(editor, mxResources.get("notSupport"));
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(
                                    editor,
                                    ex.toString(),
                                    mxResources.get("error"),
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        }

    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class PageSetupAction extends AbstractAction {
        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof mxGraphComponent) {
                mxGraphComponent graphComponent = (mxGraphComponent) e
                        .getSource();
                PrinterJob pj = PrinterJob.getPrinterJob();
                PageFormat format = pj.pageDialog(graphComponent
                        .getPageFormat());

                if (format != null) {
                    graphComponent.setPageFormat(format);
                    graphComponent.zoomAndCenter();
                }
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class PrintAction extends AbstractAction {
        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof mxGraphComponent) {
                mxGraphComponent graphComponent = (mxGraphComponent) e
                        .getSource();
                PrinterJob pj = PrinterJob.getPrinterJob();

                if (pj.printDialog()) {
                    PageFormat pf = graphComponent.getPageFormat();
                    Paper paper = new Paper();
                    double margin = 36;
                    paper.setImageableArea(margin, margin, paper.getWidth()
                            - margin * 2, paper.getHeight() - margin * 2);
                    pf.setPaper(paper);
                    pj.setPrintable(graphComponent, pf);

                    try {
                        pj.print();
                    } catch (PrinterException e2) {
                        System.out.println(e2);
                    }
                }
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class ExitAction extends AbstractAction {

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);

            if (editor != null) {
                editor.exit();
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class HistoryAction extends AbstractAction {

        public static final boolean UNDO = true;

        public static final boolean REDO = false;

        /**
         *
         */
        protected boolean undo;

        /**
         *
         */
        public HistoryAction(boolean undo) {
            this.undo = undo;
        }

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);

            if (editor != null) {
                mxUndoManager undoManager = editor.getEnGraphComponent().getGraph().getUndoManager();

                if (editor.getCurGraphComponent() == editor.getChGraphComponent()) {
                    undoManager = editor.getChGraphComponent().getGraph().getUndoManager();
                }

                if (undo) {
                    if (undoManager.canUndo()) undoManager.undo();
                } else {
                    if (undoManager.canRedo()) undoManager.redo();
                }
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class PreAction extends AbstractAction {
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
                //如果未修改或者放弃保存
                if (!editor.isModified()
                        || JOptionPane.showConfirmDialog(editor,
                        mxResources.get("loseChanges")) == JOptionPane.YES_OPTION) {
                    //将状态设置为未修改、清空操作记录
                    resetEditor(editor);

                    //检测页号并进行跳转
                    int itemSelectIndex = editor.getToolBarComboBoxIndex() - 1;
                    if (itemSelectIndex < 0) {
                        JOptionPane.showMessageDialog(editor, mxResources.get("firstPage"));
                    } else {
                        //设置页号
                        editor.setToolBarComboBoxIndex(itemSelectIndex);
                        EditorToolBar.setPageCombo(editor);
                        //绘制树形结构
                        String fileName = editor.getCurrentFile().getName();
                        if (fileName.endsWith(".xml")) {
                            TreeOnXML.creatTree(editor);
                        }

                        resetEditor(editor);
                    }
                }
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class NextAction extends AbstractAction {
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
                //如果未修改或者放弃保存
                if (!editor.isModified()
                        || JOptionPane.showConfirmDialog(editor,
                        mxResources.get("loseChanges")) == JOptionPane.YES_OPTION) {
                    //将状态设置为未修改、清空操作记录
                    resetEditor(editor);

                    //检测页号并进行跳转
                    int itemSelectIndex = editor.getToolBarComboBoxIndex() + 1;
                    if (itemSelectIndex >= editor.getDataTree().getTreeCount()) {
                        JOptionPane.showMessageDialog(editor, mxResources.get("lastPage"));
                    } else {
                        //设置页号
                        editor.setToolBarComboBoxIndex(itemSelectIndex);
                        EditorToolBar.setPageCombo(editor);
                        //绘制树形结构
                        String fileName = editor.getCurrentFile().getName();
                        if (fileName.endsWith(".xml")) {
                            TreeOnXML.creatTree(editor);
                        }

                        resetEditor(editor);
                    }
                }
            }
        }
    }

    /**
     * itemStateChanged=true时 触发该事件 创建显示当前树形结构
     */
    public static class GoAction implements ItemListener {
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
            BasicGraphEditor editor = getEditor(ae);
            if (editor != null) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    //如果未修改或者放弃保存
                    if (!editor.isModified()
                            || JOptionPane.showConfirmDialog(editor,
                            mxResources.get("loseChanges")) == JOptionPane.YES_OPTION) {
                        String itemSelect = e.getItemSelectable().getSelectedObjects()[0].toString();

                        int itemSelectIndex = Integer.parseInt(itemSelect.substring(itemSelect.indexOf("Page: ") + 5, itemSelect.indexOf("/")).trim());
                        itemSelectIndex--; // 设置的索引号比当前页码号少1

                        if (itemSelectIndex < 0 || itemSelectIndex >= editor.getDataTree().getTreeCount()) {
                            JOptionPane.showMessageDialog(editor, mxResources.get("pageNumberError"));

                        } else {
                            //设置当前页号
                            editor.setToolBarComboBoxIndex(itemSelectIndex);
                            EditorToolBar.setPageCombo(editor);
                            //EditorToolBar.setPage(editor);
                            //绘制树形结构
                            String fileName = editor.getCurrentFile().getName();
                            if (fileName.endsWith(".xml")) {
                                TreeOnXML.creatTree(editor);
                            }

                            resetEditor(editor);
                        }
                    } else {
                        //恢复选择前的页号
                        EditorToolBar.setPageCombo(editor);
                    }
                }
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class WindowAction extends AbstractAction {

        public static final boolean SPLIT = true;

        public static final boolean TABBED = false;

        /**
         * 标志是否进行对比显示
         */
        protected boolean split;

        /**
         * @param split
         */
        public WindowAction(boolean split) {
            this.split = split;
        }

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);

            if (editor != null) {
                if (this.split && !editor.alreadyAddSplitdPane) {
                    //删去tabbedPane，增加splitPane
                    editor.remove(editor.tabbedPane);
                    editor.alreadyAddTabbedPane = false;
                    editor.installToolBarAndSplitdPane();
                    editor.validate();
                    editor.repaint();

                } else if (!this.split && !editor.alreadyAddTabbedPane) {
                    //删去splitPane，增加tabbedPane
                    editor.remove(editor.splitPane);
                    editor.alreadyAddSplitdPane = false;
                    editor.installToolBarAndTabbedPane();
                    editor.validate();
                    editor.repaint();

                }
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class ScaleAction extends AbstractAction {
        /**
         *
         */
        protected double scale;

        /**
         *
         */
        public ScaleAction(double scale) {
            this.scale = scale;
        }

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof mxGraphComponent) {
                mxGraphComponent graphComponent = (mxGraphComponent) e
                        .getSource();
                double scale = this.scale;

                if (scale == 0) {

                    String value = (String) JOptionPane.showInputDialog(
                            graphComponent, mxResources.get("value"),
                            mxResources.get("scale") + " (%)",
                            JOptionPane.PLAIN_MESSAGE, null, null, "");

                    if (value != null) {
                        try {
                            scale = Double.parseDouble(value.replace("%", "")) / 100;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(graphComponent, ex
                                    .getMessage());
                        }
                    }
                }

                if (scale > 0) {
                    graphComponent.zoomTo(scale, graphComponent.isCenterZoom());
                }
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class FontNameAction extends AbstractAction {

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);
            if (editor != null) {
                //获取字体列表
                List<String> fontNameCombo = new ArrayList<String>();
                Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
                for (Font f : fonts) {
                    fontNameCombo.add(f.getName());
                }

                //选择字体
                Object selectedValue = JOptionPane.showInputDialog(editor,
                        "Choose one", "Input",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        fontNameCombo.toArray(), fontNameCombo.get(0));
                if (selectedValue == null) return;

                //设置字体
                JTextPane textPane = editor.getTextScrollPane().getChTextPane();
                Font textFont = textPane.getFont();
                textPane.setFont(new Font(selectedValue.toString(), textFont.getStyle(), textFont.getSize()));
                textPane = editor.getTextScrollPane().getEnTextPane();
                textFont = textPane.getFont();
                textPane.setFont(new Font(selectedValue.toString(), textFont.getStyle(), textFont.getSize()));

            }
        }
    }

//	/**
//	 *
//	 */
//	@SuppressWarnings("serial")
//	public static class FontStyleAction extends AbstractAction {
//		
//		protected int style;
//		
//		public FontStyleAction(int value){
//			this.style = value;
//		}
//		
//		/**
//		 * 
//		 */
//		public void actionPerformed(ActionEvent e)
//		{
//			BasicGraphEditor editor = getEditor(e);
//			if(editor!=null){
//				JTextPane textPane = editor.getTextScrollPane().getTextPane();
//				Font textFont = textPane.getFont();
//				textPane.setFont(new Font(textFont.getName(), this.style ,  textFont.getSize()));
//			}
//		}
//	}

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class FontSizeAction extends AbstractAction {

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

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class HelpOnlineAction extends AbstractAction {

        public static final String JGRAPH = "http://www.mxgraph.cn/jgraph.php";

        /**
         *
         */
        protected String url;

        /**
         * @param url
         */
        public HelpOnlineAction(String url) {
            this.url = url;
        }

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            EditorBrowserLaunch.openURL(this.url);
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class AboutAction extends AbstractAction {

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);
            if (editor != null) {
                editor.about();
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class GraphToText extends AbstractAction {

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);

            if (editor != null) {
                CustomGraphComponent curGraphComponent = editor.getCurGraphComponent();
                CustomGraphComponent graphComponent;
                TextPane textPane;
                TextUndoManager textUndoManager;
                HashMap<Integer, List<Attribute>> attrMap;

                if (curGraphComponent == editor.getEnGraphComponent()) {
                    graphComponent = editor.getEnGraphComponent();
                    textPane = editor.getTextScrollPane().getEnTextPane();
                    textUndoManager = textPane.getTextUndoManager();
                    attrMap = editor.getDataTree().getEnAttrMap();
                } else {
                    graphComponent = editor.getChGraphComponent();
                    textPane = editor.getTextScrollPane().getChTextPane();
                    textUndoManager = textPane.getTextUndoManager();
                    attrMap = editor.getDataTree().getChAttrMap();
                }

                //设置默认文字样式
                SimpleAttributeSet attrDefault = new SimpleAttributeSet();
                StyleConstants.setBold(attrDefault, false);
                StyleConstants.setItalic(attrDefault, false);
                StyleConstants.setUnderline(attrDefault, false);
                StyleConstants.setForeground(attrDefault, Color.BLACK);
                textPane.setCharacterAttributes(attrDefault, true);

                String graphString = TreeOnXML.getSaveString(graphComponent, attrMap);
                if (graphString == null) {
                    JOptionPane.showMessageDialog(null,
                            mxResources.get("getGraphTextError"));
                    return;
                }
                String textString = textPane.getText();

                if (!graphString.equals(textString) &&
                        JOptionPane.showConfirmDialog(null,
                                mxResources.get("ifOverWriteText")) ==
                                JOptionPane.YES_OPTION) {
                    textPane.setText(graphString);
                    textUndoManager.pushOperate(textUndoManager.SETTEXT);
                    editor.setModified(true);
                }
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class TextAddAction extends AbstractAction {

        public static final int TEXT = 1;

        public static final int TAG = 2;

        public static final int ATTR = 3;

        public static final int BLANK = 4;

        protected int type;

        public TextAddAction(int type) {
            this.type = type;
        }

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);
            if (editor != null) {
                TextPane textPane;
                if (editor.curGraphComponent == editor.chGraphComponent) {
                    textPane = editor.getTextScrollPane().getChTextPane();
                } else {
                    textPane = editor.getTextScrollPane().getEnTextPane();
                }

                TextUndoManager textUndoManager = textPane.getTextUndoManager();
                textPane.setEditable(true);

                //获取标签数据
                boolean isCh = editor.curGraphComponent == editor.chGraphComponent;
                HashMap<String, List<Element>> tagMap =
                        isCh ? editor.dataTree.getTagChMap() : editor.dataTree.getTagEnMap();

                //设置修改文字样式
                SimpleAttributeSet attrSet = new SimpleAttributeSet();
                StyleConstants.setItalic(attrSet, false);
                StyleConstants.setBold(attrSet, true);
                StyleConstants.setUnderline(attrSet, true);
                StyleConstants.setForeground(attrSet, new Color(0xFF, 0x5F, 0x24));
                //textPane.setCharacterAttributes(attrDefault, true);

                switch (this.type) {
                    case TEXT:
                        //获取文本
                        String addText = JOptionPane.showInputDialog(mxResources.get("pleaseInputText"));

                        //增加文本
                        try {
                            textPane.getDocument().insertString(
                                    textPane.getCaretPosition(),
                                    addText,
                                    attrSet);
                        } catch (BadLocationException e1) {
                            //e1.printStackTrace();
                            JOptionPane.showMessageDialog(null,
                                    e1.toString(), mxResources.get("error"),
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    case TAG:
                        //若未选中则结束
                        if (textPane.getSelectedText() == null) break;

                        //获取所有标签名
                        List<String> tagList = new ArrayList<String>();
                        for (Map.Entry<String, List<Element>> entry : tagMap.entrySet()) {
                            tagList.add(entry.getKey().toString());
                        }

                        //将标签排序
                        Collections.sort(tagList);

                        //选择标签
                        Object selectedTag = JOptionPane.showInputDialog(editor,
                                "Choose one", "Input",
                                JOptionPane.INFORMATION_MESSAGE, null,
                                tagList.toArray(), tagList.get(0));

                        if (selectedTag != null) {
                            //增加文本
                            try {
                                textPane.getDocument().insertString(
                                        textPane.getSelectionStart(),
                                        "<" + selectedTag + ">",
                                        attrSet);
                                textPane.getDocument().insertString(
                                        textPane.getSelectionEnd(),
                                        "</" + selectedTag + ">",
                                        attrSet);

                                textUndoManager.pushOperate(textUndoManager.ADDTAG);
                                editor.setModified(true);

                            } catch (BadLocationException e1) {
                                //e1.printStackTrace();
                                JOptionPane.showMessageDialog(null,
                                        e1.toString(), mxResources.get("error"),
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;

                    case ATTR:
                        //若未选中则结束
                        if (textPane.getSelectedText() == null) break;

                        //换取选中的文本
                        String selected = textPane.getSelectedText();

                        if (!selected.contains("/") && selected.startsWith("<") && selected.endsWith(">")) {

                            String node = selected.split(" ")[0].replaceAll("<|>", "");

                            //获取属性列表
                            List<String> attrList = new ArrayList<String>();
                            HashMap<String, String> attrMap = new HashMap<String, String>();
                            List<Element> tagAttr = tagMap.get(node);
                            if (tagAttr == null || tagAttr.isEmpty()) {
                                break;
                            }
                            for (Element i : tagAttr) {
                                attrList.add(i.attributeValue("name"));
                                attrMap.put(i.attributeValue("name"), i.attributeValue("value"));
                            }

                            //将属性排序
                            Collections.sort(attrList);

                            //选择属性
                            Object selectedAttr = JOptionPane.showInputDialog(editor,
                                    "Choose one", "Input",
                                    JOptionPane.INFORMATION_MESSAGE, null,
                                    attrList.toArray(), attrList.get(0));

                            if (selectedAttr != null) {
                                //获取属性数组
                                String[] attrValueArray = attrMap.get(selectedAttr).split(" ");

                                //获取属性值
                                Object attrValue = null;
                                if (attrValueArray[0].isEmpty()) {
                                    attrValue = JOptionPane.showInputDialog(
                                            mxResources.get("pleaseInputText"));
                                } else {
                                    //将属性值排序
                                    Arrays.sort(attrValueArray);
                                    attrValue = JOptionPane.showInputDialog(editor,
                                            "Choose one", "Input",
                                            JOptionPane.INFORMATION_MESSAGE, null,
                                            attrValueArray, attrValueArray[0]);
                                }

                                if (attrValue != null) {
                                    try {
                                        textPane.getDocument().insertString(
                                                textPane.getSelectionEnd() - 1,
                                                " " + selectedAttr + "=\"" + attrValue.toString() + "\"",
                                                attrSet);

                                        textUndoManager.pushOperate(textUndoManager.ADDATTR);
                                        editor.setModified(true);

                                    } catch (BadLocationException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                            }
                        }
                        break;

                    case BLANK:
                        try {
                            textPane.getDocument().insertString(
                                    textPane.getCaretPosition(),
                                    " ",
                                    attrSet);

                            textUndoManager.pushOperate(textUndoManager.ADDBLANK);
                            editor.setModified(true);

                        } catch (BadLocationException e1) {
                            e1.printStackTrace();
                        }
                        break;

                }

                textPane.setEditable(false);
                textPane.getCaret().setVisible(true);
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class TextDeleteAction extends AbstractAction {

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);
            if (editor != null) {
                TextPane textPane;
                if (editor.curGraphComponent == editor.chGraphComponent) {
                    textPane = editor.getTextScrollPane().getChTextPane();
                } else {
                    textPane = editor.getTextScrollPane().getEnTextPane();
                }

                TextUndoManager textUndoManager = textPane.getTextUndoManager();
                textPane.setEditable(true);

                //设置默认文字样式
                SimpleAttributeSet attrDefault = new SimpleAttributeSet();
                StyleConstants.setBold(attrDefault, false);
                StyleConstants.setItalic(attrDefault, false);
                StyleConstants.setUnderline(attrDefault, false);
                StyleConstants.setForeground(attrDefault, Color.BLACK);
                //textPane.setCharacterAttributes(attrDefault, true);

                try {

                    String selected = textPane.getSelectedText();

                    //获取当前光标的位置
                    int caretPosition = textPane.getCaretPosition();

                    //获取当前文本的长度
                    int textLength = textPane.getText().length();

                    //自动匹配选择标签
                    int startPosition = 0;
                    int endPosition = textLength;

                    String startString = textPane.getText(startPosition, caretPosition - startPosition);
                    String endString = textPane.getText(caretPosition, endPosition - caretPosition);

                    if (selected != null) {
                        /*
                         * 如果为标签
						 */
                        if (selected.startsWith("<") && selected.endsWith(">")) {
                            //结束标签
                            if (selected.charAt(1) == '/') {
                                String node = selected.substring(2, selected.length() - 1);

                                String str = textPane.getText(0, textPane.getCaretPosition());
                                int strLen = str.length();
                                int nodeLen = node.length();
                                int nodeCount = 0;
                                while (true) {
                                    String sub = str.substring(strLen - nodeLen - 2, strLen - 1);
                                    if (sub.equals("/" + node)) {
                                        nodeCount++;
                                    } else if (sub.equals("<" + node)) {
                                        nodeCount--;
                                        if (nodeCount == 0) break;
                                    }
                                    strLen--;
                                }

                                int start = strLen - nodeLen - 2;
                                int end = textPane.getCaretPosition();

                                String rep = textPane.getText(start, end - start);
                                rep = rep.substring(rep.indexOf(">") + 1, rep.lastIndexOf("<"));
                                textPane.select(start, end);
                                textPane.replaceSelection(null);
                                textPane.getDocument().insertString(
                                        textPane.getCaretPosition(),
                                        rep,
                                        attrDefault);

                                textUndoManager.pushOperate(textUndoManager.DELECTTAG);
                                editor.setModified(true);

                                //开始标签
                            } else {
                                String node = selected.substring(1,
                                        selected.indexOf(" ") != -1 ? selected.indexOf(" ") : selected.indexOf(">"));

                                String str = textPane.getText(textPane.getSelectionStart(), textPane.getText().length() - textPane.getSelectionStart());
                                int nodeLen = node.length();
                                int nodeCount = 0, nodePos = 0;
                                while (true) {
                                    String sub = str.substring(nodePos + 0, nodePos + nodeLen + 1);
                                    if (sub.equals("<" + node)) {
                                        nodeCount++;
                                    } else if (sub.equals("/" + node)) {
                                        nodeCount--;
                                        if (nodeCount == 0) break;
                                    }
                                    nodePos++;
                                }

                                int start = textPane.getSelectionStart();
                                int end = textPane.getSelectionStart() + nodePos + nodeLen + 2;

                                String rep = textPane.getText(start, end - start);
                                rep = rep.substring(rep.indexOf(">") + 1, rep.lastIndexOf("<"));
                                textPane.select(start, end);
                                textPane.replaceSelection(null);
                                textPane.getDocument().insertString(
                                        textPane.getCaretPosition(),
                                        rep,
                                        attrDefault);

                                textUndoManager.pushOperate(textUndoManager.DELECTTAG);
                                editor.setModified(true);

                            }
						
						/*
						 * 如果是属性
						 */
                        } else if (selected.matches("[a-zA-Z0-9_]*=\".*\"") &&
                                startString.lastIndexOf("<") > startString.lastIndexOf(">") &&
                                endString.indexOf(">") < endString.indexOf("<") &&
                                endString.indexOf(">") != -1) {

                            if (textPane.getText(caretPosition - selected.length() - 1, 1).equals(" ")) {
                                textPane.select(caretPosition - selected.length() - 1, caretPosition);
                            }

                            textPane.replaceSelection(null);

                            textUndoManager.pushOperate(textUndoManager.DELECTATTR);
                            editor.setModified(true);
						
						/*
						 *  如果是空格
						 */
                        } else if (selected.matches("[ ]*")) {

                            textPane.replaceSelection(null);

                            textUndoManager.pushOperate(textUndoManager.DELECTBLANK);
                            editor.setModified(true);
							
							
						/*
						 * 如果不是标签
						 */
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    mxResources.get("cannotDeleted"), mxResources.get("error"),
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                } catch (Exception e1) {
                    //e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            e1.toString(), mxResources.get("error"),
                            JOptionPane.ERROR_MESSAGE);
                }

                textPane.setEditable(false);
                textPane.getCaret().setVisible(true);
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class TextReplaceAction extends AbstractAction {

        public static final int TEXT = 1;

        public static final int TAG = 2;

        public static final int ATTR = 3;

        protected int type;

        public TextReplaceAction(int type) {
            this.type = type;
        }

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);
            if (editor != null) {
                TextPane textPane;
                if (editor.curGraphComponent == editor.chGraphComponent) {
                    textPane = editor.getTextScrollPane().getChTextPane();
                } else {
                    textPane = editor.getTextScrollPane().getEnTextPane();
                }

                TextUndoManager textUndoManager = textPane.getTextUndoManager();
                textPane.setEditable(true);

                //获取标签数据
                boolean isCh = editor.curGraphComponent == editor.chGraphComponent;
                HashMap<String, List<Element>> tagMap =
                        isCh ? editor.dataTree.getTagChMap() : editor.dataTree.getTagEnMap();


                //设置默认文字样式
                SimpleAttributeSet attrDefault = new SimpleAttributeSet();
                StyleConstants.setBold(attrDefault, false);
                StyleConstants.setItalic(attrDefault, false);
                StyleConstants.setUnderline(attrDefault, false);
                StyleConstants.setForeground(attrDefault, Color.BLACK);
                //textPane.setCharacterAttributes(attrDefault, true);

                //设置修改文字样式
                SimpleAttributeSet attrSet = new SimpleAttributeSet();
                StyleConstants.setBold(attrSet, true);
                StyleConstants.setItalic(attrSet, true);
                StyleConstants.setUnderline(attrSet, false);
                StyleConstants.setForeground(attrSet, new Color(0x5F, 0x9E, 0xA0));

                try {
                    switch (this.type) {
                        case TEXT: {
                            //若未选中则结束
                            if (textPane.getSelectedText() == null) break;

                            //获取文本
                            String addText = JOptionPane.showInputDialog(mxResources.get("pleaseInputText"));

                            //如果 点击取消按钮 或 未输入文本 则结束
                            if (addText == null || addText.isEmpty()) break;

                            //替换文本
                            textPane.replaceSelection(null);
                            textPane.getDocument().insertString(
                                    textPane.getCaretPosition(),
                                    addText,
                                    attrSet);

                        }
                        break;

                        case TAG: {
                            //若未选中则结束
                            if (textPane.getSelectedText() == null) break;

                            String selected = textPane.getSelectedText();

                            //如果为标签,则进行替换
                            if (selected.startsWith("<") && selected.endsWith(">")) {

                                //获取所有标签名
                                List<String> tagList = new ArrayList<String>();
                                for (Map.Entry<String, List<Element>> entry : tagMap.entrySet()) {
                                    tagList.add(entry.getKey().toString());
                                }

                                //将标签排序
                                Collections.sort(tagList);

                                //选择标签
                                Object selectedTag = JOptionPane.showInputDialog(editor,
                                        "Choose one", "Input",
                                        JOptionPane.INFORMATION_MESSAGE, null,
                                        tagList.toArray(), tagList.get(0));

                                if (selectedTag != null) {
                                    //结束标签
                                    if (selected.charAt(1) == '/') {
                                        String node = selected.substring(2, selected.length() - 1);

                                        String str = textPane.getText(0, textPane.getCaretPosition());
                                        int strLen = str.length();
                                        int nodeLen = node.length();
                                        int nodeCount = 0;
                                        while (true) {
                                            String sub = str.substring(strLen - nodeLen - 2, strLen - 1);
                                            if (sub.equals("/" + node)) {
                                                nodeCount++;
                                            } else if (sub.equals("<" + node)) {
                                                nodeCount--;
                                                if (nodeCount == 0) break;
                                            }
                                            strLen--;
                                        }

                                        int start = strLen - nodeLen - 2;
                                        int end = textPane.getCaretPosition();

                                        String rep = textPane.getText(start, end - start);
                                        rep = rep.substring(rep.indexOf(">") + 1, rep.lastIndexOf("<"));


                                        textPane.select(start, end);
                                        textPane.replaceSelection(null);
                                        textPane.getDocument().insertString(
                                                textPane.getCaretPosition(),
                                                rep,
                                                attrDefault);
                                        textPane.getDocument().insertString(
                                                textPane.getCaretPosition() - rep.length(),
                                                "<" + selectedTag + ">",
                                                attrSet);
                                        textPane.getDocument().insertString(
                                                textPane.getCaretPosition(),
                                                "</" + selectedTag + ">",
                                                attrSet);

                                        textUndoManager.pushOperate(textUndoManager.REPLACETAG);
                                        editor.setModified(true);

                                        //开始标签
                                    } else {
                                        String node = selected.substring(1,
                                                selected.indexOf(" ") != -1 ? selected.indexOf(" ") : selected.indexOf(">"));

                                        String str = textPane.getText(textPane.getSelectionStart(), textPane.getText().length() - textPane.getSelectionStart());
                                        int nodeLen = node.length();
                                        int nodeCount = 0, nodePos = 0;
                                        while (true) {
                                            String sub = str.substring(nodePos + 0, nodePos + nodeLen + 1);
                                            if (sub.equals("<" + node)) {
                                                nodeCount++;
                                            } else if (sub.equals("/" + node)) {
                                                nodeCount--;
                                                if (nodeCount == 0) break;
                                            }
                                            nodePos++;
                                        }

                                        int start = textPane.getSelectionStart();
                                        int end = textPane.getSelectionStart() + nodePos + nodeLen + 2;

                                        String rep = textPane.getText(start, end - start);
                                        rep = rep.substring(rep.indexOf(">") + 1, rep.lastIndexOf("<"));

                                        textPane.select(start, end);
                                        textPane.replaceSelection(null);
                                        textPane.getDocument().insertString(
                                                textPane.getCaretPosition(),
                                                rep,
                                                attrDefault);
                                        textPane.getDocument().insertString(
                                                textPane.getCaretPosition() - rep.length(),
                                                "<" + selectedTag + ">",
                                                attrSet);
                                        textPane.getDocument().insertString(
                                                textPane.getCaretPosition(),
                                                "</" + selectedTag + ">",
                                                attrSet);

                                        textUndoManager.pushOperate(textUndoManager.REPLACETAG);
                                        editor.setModified(true);

                                    }
                                }
                            }
                        }
                        break;

                        case ATTR: {
                            //若未选中则结束
                            if (textPane.getSelectedText() == null) break;

                            String selected = textPane.getSelectedText();

                            //获取当前光标的位置
                            int caretPosition = textPane.getCaretPosition();

                            //获取当前文本的长度
                            int textLength = textPane.getText().length();

                            //自动匹配选择标签
                            int startPosition = 0;
                            int endPosition = textLength;

                            String startString = textPane.getText(startPosition, caretPosition - startPosition);
                            String endString = textPane.getText(caretPosition, endPosition - caretPosition);

                            if (selected.matches("[a-zA-Z0-9_]*=\".*\"") &&
                                    startString.lastIndexOf("<") > startString.lastIndexOf(">") &&
                                    endString.indexOf(">") < endString.indexOf("<") &&
                                    endString.indexOf(">") != -1) {

                                int start = startString.lastIndexOf("<");
                                int end = textPane.getCaretPosition() + endString.indexOf(">");

                                String locTag = textPane.getText(start, end - start + 1);

                                String node = locTag.split(" ")[0].replaceAll("<|>", "");

                                //获取属性列表
                                List<String> attrList = new ArrayList<String>();
                                HashMap<String, String> attrMap = new HashMap<String, String>();
                                List<Element> tagAttr = tagMap.get(node);
                                if (tagAttr == null) {
                                    break;
                                }
                                for (Element i : tagAttr) {
                                    attrList.add(i.attributeValue("name"));
                                    attrMap.put(i.attributeValue("name"), i.attributeValue("value"));
                                }

                                //将属性排序
                                Collections.sort(attrList);

                                //选择属性
                                Object selectedAttr = JOptionPane.showInputDialog(editor,
                                        "Choose one", "Input",
                                        JOptionPane.INFORMATION_MESSAGE, null,
                                        attrList.toArray(), attrList.get(0));

                                if (selectedAttr != null) {
                                    //获取属性数组
                                    String[] attrValueArray = attrMap.get(selectedAttr).split(" ");

                                    //获取属性值
                                    Object attrValue = null;
                                    if (attrValueArray[0].isEmpty()) {
                                        attrValue = JOptionPane.showInputDialog(
                                                mxResources.get("pleaseInputText"));
                                    } else {
                                        //将属性值排序
                                        Arrays.sort(attrValueArray);
                                        attrValue = JOptionPane.showInputDialog(editor,
                                                "Choose one", "Input",
                                                JOptionPane.INFORMATION_MESSAGE, null,
                                                attrValueArray, attrValueArray[0]);
                                    }

                                    if (attrValue != null) {
                                        textPane.replaceSelection(null);
                                        textPane.getDocument().insertString(
                                                textPane.getCaretPosition(),
                                                selectedAttr + "=\"" + attrValue + "\"",
                                                attrSet);

                                        textUndoManager.pushOperate(textUndoManager.REPLACEATTR);
                                        editor.setModified(true);

                                    }
                                }
                            }

                        }
                        break;

                    }
                } catch (Exception e1) {
                    //e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            e1.toString(), mxResources.get("error"),
                            JOptionPane.ERROR_MESSAGE);
                }

                textPane.setEditable(false);
                textPane.getCaret().setVisible(true);
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class TextHistoryAction extends AbstractAction {

        public static final boolean UNDO = true;

        public static final boolean REDO = false;

        /**
         *
         */
        protected boolean undo;

        /**
         *
         */
        public TextHistoryAction(boolean undo) {
            this.undo = undo;
        }

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);
            if (editor != null) {
                UndoManager textUndoManager;
                if (editor.curGraphComponent == editor.chGraphComponent) {
                    textUndoManager = editor.getTextScrollPane().getChTextPane().getTextUndoManager();
                } else {
                    textUndoManager = editor.getTextScrollPane().getEnTextPane().getTextUndoManager();
                }

                if (undo) {
                    if (textUndoManager.canUndo()) {
                        textUndoManager.undo();
                        editor.setModified(true);
                    }
                } else {
                    if (textUndoManager.canRedo()) {
                        textUndoManager.redo();
                        editor.setModified(true);
                    }
                }
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class MatchAction extends AbstractAction {

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);
            if (editor != null) {
                TextPane textPane;
                if (editor.curGraphComponent == editor.chGraphComponent) {
                    textPane = editor.getTextScrollPane().getChTextPane();
                } else {
                    textPane = editor.getTextScrollPane().getEnTextPane();
                }
                textPane.setEditable(true);

                String selected = textPane.getSelectedText();
                if (selected != null) {
					
					/*
					 * 如果为标签
					 */
                    if (selected.startsWith("<") && selected.endsWith(">")) {
                        try {
                            //结束标签
                            if (selected.charAt(1) == '/') {
                                String node = selected.substring(2, selected.length() - 1);

                                String str = textPane.getText(0, textPane.getCaretPosition());
                                int strLen = str.length();
                                int nodeLen = node.length();
                                int nodeCount = 0;
                                while (true) {
                                    String sub = str.substring(strLen - nodeLen - 2, strLen - 1);
                                    if (sub.equals("/" + node)) {
                                        nodeCount++;
                                    } else if (sub.equals("<" + node)) {
                                        nodeCount--;
                                        if (nodeCount == 0) break;
                                    }
                                    strLen--;
                                }

                                int start = strLen - nodeLen - 2;
                                int end = textPane.getCaretPosition();

                                textPane.select(start, end);

                                //开始标签
                            } else {
                                String node = selected.substring(1,
                                        selected.indexOf(" ") != -1 ? selected.indexOf(" ") : selected.indexOf(">"));

                                String str = textPane.getText(textPane.getSelectionStart(), textPane.getText().length() - textPane.getSelectionStart());
                                int nodeLen = node.length();
                                int nodeCount = 0, nodePos = 0;
                                while (true) {
                                    String sub = str.substring(nodePos + 0, nodePos + nodeLen + 1);
                                    if (sub.equals("<" + node)) {
                                        nodeCount++;
                                    } else if (sub.equals("/" + node)) {
                                        nodeCount--;
                                        if (nodeCount == 0) break;
                                    }
                                    nodePos++;
                                }

                                int start = textPane.getSelectionStart();
                                int end = textPane.getSelectionStart() + nodePos + nodeLen + 2;

                                textPane.select(start, end);

                            }
                        } catch (Exception e1) {
                            //e1.printStackTrace();
                            JOptionPane.showMessageDialog(null,
                                    e1.toString(), mxResources.get("error"),
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

                textPane.setEditable(false);
                textPane.getCaret().setVisible(true);
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class RepaintAction extends AbstractAction {

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);
            DataTree dataTree = editor.getDataTree();

            if (editor != null) {
                HashMap<Integer, String> curMap;
                String curStr;
                if (editor.curGraphComponent == editor.chGraphComponent) {
                    curMap = dataTree.getChMap();
                    curStr = editor.textScrollPane.getChTextPane().getText();
                } else {
                    curMap = dataTree.getEnMap();
                    curStr = editor.textScrollPane.getEnTextPane().getText();
                }

                int key = editor.toolBarComboBoxIndex;

                //将信息保存至HashMap中
                curMap.put(key, curStr);

                TreeOnXML.repaintTree(editor);

            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class ShowThisTagsAction extends AbstractAction {

        /**
         * 返回字符串中不可比配XML开始标记
         */
        private ArrayList<String> notMatchStartTag(String str) {
            ArrayList<String> startTag = new ArrayList<String>();

            while (str.contains("<") && str.contains(">")) {
                String tag = str.substring(str.indexOf('<'), str.indexOf('>') + 1);

                if (tag.startsWith("</")) {
                    startTag.remove(startTag.size() - 1);
                } else {
                    startTag.add(tag);
                }
                str = str.substring(0, str.indexOf('<')) + str.substring(str.indexOf('>') + 1, str.length());
            }
            if (startTag.size() > 0) {
                return startTag;
            }
            return null;
        }

        /**
         * 返回字符串中不可比配XML结束标记
         */
        private ArrayList<String> notMatchEndTag(String str) {
            ArrayList<String> endTag = new ArrayList<String>();

            while (str.contains("<") && str.contains(">")) {
                String tag = str.substring(str.lastIndexOf('<'), str.lastIndexOf('>') + 1);

                if (tag.startsWith("</")) {
                    endTag.add(tag);
                } else {
                    endTag.remove(endTag.size() - 1);
                }
                str = str.substring(0, str.lastIndexOf('<')) + str.substring(str.lastIndexOf('>') + 1, str.length());

            }
            if (endTag.size() > 0) {
                return endTag;
            }
            return null;
        }

        /**
         * 返回带有完整XML标记的选择文本
         */
        private String getSelectedTextWithXML(ArrayList<String> startTag, String selected, ArrayList<String> endTag) {

            if (startTag == null && endTag == null) {
                return selected;
            }

            if (startTag != null && endTag == null) {
                String temp = "";
                for (int i = 0; i < startTag.size(); i++) {
                    temp += startTag.get(i);
                }
                return temp + selected;
            }

            if (startTag == null && endTag != null) {
                String temp = "";
                for (int i = endTag.size() - 1; i >= 0; i--) {
                    temp += endTag.get(i);
                }
                return selected + temp;
            }

            String lastRemoveStartTag = "", lastRemoveEndTag = "";
            while (true) {
                if (!(startTag.size() > 0 && endTag.size() > 0)) break;

                String start = startTag.get(0);
                String end = endTag.get(0);
                end = end.replaceAll("[/>]", "");

                if (start.startsWith(end + " ") ||
                        start.startsWith(end + ">")) {
                    lastRemoveStartTag = startTag.remove(0);
                    lastRemoveEndTag = endTag.remove(0);
                } else {
                    break;
                }
            }

            String startTemp = lastRemoveStartTag, endTemp = "";
            for (int i = 0; i < startTag.size(); i++) {
                startTemp += startTag.get(i);
            }
            for (int i = endTag.size() - 1; i >= 0; i--) {
                endTemp += endTag.get(i);
            }
            endTemp += lastRemoveEndTag;

            return startTemp + selected + endTemp;
        }

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);

            if (editor != null) {
                DataTree dataTree = editor.getDataTree();
                int toolBarComboBoxIndex = editor.getToolBarComboBoxIndex();

                //获取原始文本数据
                String text;
                TextPane textPane;
                if (editor.curGraphComponent == editor.chGraphComponent) {
                    text = dataTree.getChMap().get(toolBarComboBoxIndex);
                    textPane = editor.getTextScrollPane().getChTextPane();
                } else {
                    text = dataTree.getEnMap().get(toolBarComboBoxIndex);
                    textPane = editor.getTextScrollPane().getEnTextPane();
                }

                TextUndoManager textUndoManager = textPane.getTextUndoManager();
                textPane.setEditable(true);

                //获取文字面板中的数据
                String string = textPane.getText();
                String startString = string.substring(0, textPane.getSelectionStart()).replaceAll("<[^>]*>", "").replaceAll(" +", " ");
                String selectedString = textPane.getSelectedText().replaceAll("<[^>]*>", "").replaceAll(" +", " ");
                String endString = string.substring(textPane.getSelectionEnd()).replaceAll("<[^>]*>", "").replaceAll(" +", " ");
//				
//				System.out.println("startString:"+startString);
//				System.out.println("selectedString:"+selectedString);
//				System.out.println("endString:"+endString);

                int startIndex = -1, endIndex = -1;
                if (selectedString != null) {
                    for (int i = text.length() - 1; i >= 0; i--) {
                        String temp = text.substring(i).replaceAll("[" + BasicGraphEditor.LINE_TAB + "]", "").replaceAll("(<[^>]*>)+", " ").replaceAll(" +", " ").trim();
                        if (temp.equals((selectedString + endString).trim())) {
                            startIndex = i;
                            break;
                        }
//						System.out.println("temp   :"+temp);
//						System.out.println("sel+end:"+(selectedString + endString));
                    }
//					System.out.println("startIndex:"+startIndex);

                    for (int i = 0; i <= text.length(); i++) {
                        String temp = text.substring(0, i).replaceAll("[" + BasicGraphEditor.LINE_TAB + "]", "").replaceAll("(<[^>]*>)+", " ").replaceAll(" +", " ").trim();
                        if (temp.equals((startString + selectedString).trim())) {
                            endIndex = i;
                            break;
                        }
                    }
//					System.out.println("endIndex:"+endIndex);
                }

                if (startIndex != -1 && endIndex != -1) {
                    String startText = text.substring(0, startIndex);
                    String selectedText = text.substring(startIndex, endIndex);
                    String endText = text.substring(endIndex, text.length());
                    String newStartText = startText.replaceAll("(</?[^(a|s|>)]+>)+", " ");
                    String newEndText = endText.replaceAll("(</?[^(a|s|>)]+>)+", " ");

//					System.out.println("startText:"+startText);
//					System.out.println("selectedText:"+selectedText);
//					System.out.println("endText:"+endText);

                    //处理选中区标记
                    String newSelectedText = getSelectedTextWithXML(
                            notMatchStartTag(startText.replaceAll("<[as][^>]*>", "").replaceAll("</[as]>", "")),
                            selectedText,
                            notMatchEndTag(endText.replaceAll("<[as][^>]*>", "").replaceAll("</[as]>", ""))
                    );

                    //添加修改标记符
                    if (!newStartText.startsWith("<a m=\"t\"")) {
                        newStartText = newStartText.replaceFirst("<a", "<a m=\"t\"");
                    }

//					System.out.println(newStartText);
//					System.out.println(newSelectedText);
//					System.out.println(newEndText);

                    // 显示文本数据
                    String newText = newStartText + newSelectedText + newEndText;
                    newText = newText.replaceAll("[" + BasicGraphEditor.LINE_TAB + "]", "").replaceAll(" +", " ")
                            .replaceAll("> ", ">").replaceAll(" <", "<");

                    //获得显示面板
                    CustomGraphComponent curGraphComponent = editor.getCurGraphComponent();
                    CustomGraph curGraph = curGraphComponent.getGraph();

                    // 判断中英文
                    boolean curIsCh = false;
                    if (curGraphComponent == editor.getChGraphComponent()) {
                        curIsCh = true;
                    } else if (curGraphComponent == editor.getEnGraphComponent()) {
                        curIsCh = false;
                    } else {
                        return;
                    }

                    //获得显示的数据
                    String curStr = newText;
                    org.dom4j.Document curXML = TreeOnXML.convertXML(curStr.replaceAll("[" + BasicGraphEditor.LINE_TAB + "]", "").trim());
                    if (curXML == null) return;

                    //设置内容超出后自动剪裁
                    curGraph.setLabelsClipped(true);

                    //清空面板
                    curGraph.selectAll();
                    curGraph.removeCells();

                    //绘制树形图
                    TreeOnXML.creatTree(
                            curGraphComponent,
                            curXML,
                            TreeOnXML.VERTEX_X = 50,
                            10,
                            curIsCh ? dataTree.getChAttrMap() : dataTree.getEnAttrMap()
                    );

                    //设置文本
                    textPane.setText(newText);
                    textUndoManager.pushOperate(textUndoManager.SETTEXT);
                    editor.setModified(true);

                }

                textPane.setEditable(false);
                textPane.getCaret().setVisible(true);
            }
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public static class RemoveOtherTagsAction extends AbstractAction {

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {
            BasicGraphEditor editor = getEditor(e);

            if (editor != null) {
                TextPane textPane;
                if (editor.curGraphComponent == editor.chGraphComponent) {
                    textPane = editor.getTextScrollPane().getChTextPane();
                } else {
                    textPane = editor.getTextScrollPane().getEnTextPane();
                }
                TextUndoManager textUndoManager = textPane.getTextUndoManager();
                textPane.setEditable(true);

                String selected = textPane.getSelectedText();
                if (selected != null) {
                    //设置默认文字样式
                    SimpleAttributeSet attrDefault = new SimpleAttributeSet();
                    StyleConstants.setBold(attrDefault, false);
                    StyleConstants.setItalic(attrDefault, false);
                    StyleConstants.setUnderline(attrDefault, false);
                    StyleConstants.setForeground(attrDefault, Color.BLACK);

                    String text = textPane.getText();
                    String selectedText = textPane.getSelectedText();
                    int start = 0;
                    int selectedStart = textPane.getSelectionStart();
                    int end = text.length();
                    int selectedEnd = textPane.getSelectionEnd();

                    String startString = text.substring(start, selectedStart);
                    String endString = text.substring(selectedEnd, end);

                    startString = startString.replaceAll("</?[^(a|s|>)]+>", "");
                    endString = endString.replaceAll("</?[^(a|s|>)]+>", "");

                    textPane.setText(startString + selectedText + endString);

                    textUndoManager.pushOperate(textUndoManager.SETTEXT);
                    editor.setModified(true);

                }

                textPane.setEditable(false);
                textPane.getCaret().setVisible(true);
            }
        }
    }
}
