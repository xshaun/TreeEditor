package cn.edu.zzu.nlp.editor;

import cn.edu.zzu.nlp.editor.EditorActions.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EditorToolBar extends JToolBar {

    /**
     *
     */
    public static final int pageComboIndex = 11;
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private final String url = ("/cn/edu/zzu/nlp/images/");
    /**
     * @param frame
     * @param orientation
     */
    private boolean ignoreZoomChange = false;

    /**
     *
     */
    public EditorToolBar(final BasicGraphEditor editor, int orientation) {
        super(orientation);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 3, 0, 3), getBorder()));
        setFloatable(false);
        Dimension separator = new Dimension(13, 0);

        this.add(editor.bind(
                "Open",
                new OpenAction(),
                url + "open.gif")).setToolTipText(mxResources.get("open")); //index = 0
        this.add(editor.bind(
                "Save",
                new SaveAction(SaveAction.NOTSHOWDIALOG),
                url + "save.gif")).setToolTipText(mxResources.get("save")); //index = 1

        this.addSeparator(separator); //index = 2

        this.add(editor.bind(
                "Print",
                new PrintAction(),
                url + "print.gif")).setToolTipText(mxResources.get("print")); //index = 3

        this.addSeparator(separator); //index = 4

		/*
         * 增加图像编辑栏
		 */
        JToolBar graphToolBar = new JToolBar();
        graphToolBar.setFloatable(false);
        graphToolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.ORANGE));

        this.add(graphToolBar); //index = 5

        graphToolBar.add(editor.bind(
                mxResources.get("cut"),
                TransferHandler.getCutAction(),
                url + "cut.gif")).setToolTipText(mxResources.get("cut"));
        graphToolBar.add(editor.bind(
                mxResources.get("copy"),
                TransferHandler.getCopyAction(),
                url + "copy.gif")).setToolTipText(mxResources.get("copy"));
        graphToolBar.add(editor.bind(
                mxResources.get("paste"),
                TransferHandler.getPasteAction(),
                url + "paste.gif")).setToolTipText(mxResources.get("paste"));

        graphToolBar.addSeparator(separator);

        graphToolBar.add(editor.bind(
                mxResources.get("selectAll"),
                mxGraphActions.getSelectAllAction(),
                url + "selectall.gif")).setToolTipText(mxResources.get("selectAll"));
        graphToolBar.add(editor.bind(
                mxResources.get("selectNone"),
                mxGraphActions.getSelectNoneAction(),
                url + "selectnone.gif")).setToolTipText(mxResources.get("selectNone"));
        graphToolBar.add(editor.bind(
                mxResources.get("delete"),
                mxGraphActions.getDeleteAction(),
                url + "delete.gif")).setToolTipText(mxResources.get("delete"));

        graphToolBar.addSeparator(separator);

        graphToolBar.add(editor.bind(
                mxResources.get("undo"),
                new HistoryAction(HistoryAction.UNDO),
                url + "undo.gif")).setToolTipText(mxResources.get("undo"));
        graphToolBar.add(editor.bind(
                mxResources.get("redo"),
                new HistoryAction(HistoryAction.REDO),
                url + "redo.gif")).setToolTipText(mxResources.get("redo"));

        graphToolBar.addSeparator(separator);

        graphToolBar.add(editor.bind(
                mxResources.get("graphToText"),
                new GraphToText(),
                url + "graphtotext.gif")).setToolTipText(mxResources.get("graphToText"));
		
		/*
		 * 增加文字编辑栏
		 */
        JToolBar textToolBar = new JToolBar();
        textToolBar.setFloatable(false);
        textToolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLUE));

        this.add(textToolBar); //index = 6

        textToolBar.add(editor.bind(
                mxResources.get("addTag"),
                new TextAddAction(TextAddAction.TAG),
                url + "text-addtag.png")).setToolTipText(mxResources.get("addTag"));
        textToolBar.add(editor.bind(
                mxResources.get("replaceTag"),
                new TextReplaceAction(TextReplaceAction.TAG),
                url + "text-replacetag.png")).setToolTipText(mxResources.get("replaceTag"));

        textToolBar.addSeparator(separator);

        textToolBar.add(editor.bind(
                mxResources.get("addAttr"),
                new TextAddAction(TextAddAction.ATTR),
                url + "text-addattr.png")).setToolTipText(mxResources.get("addAttr"));
        textToolBar.add(editor.bind(
                mxResources.get("replaceAttr"),
                new TextReplaceAction(TextReplaceAction.ATTR),
                url + "text-replaceattr.png")).setToolTipText(mxResources.get("replaceAttr"));

        textToolBar.addSeparator(separator);

        textToolBar.add(editor.bind(
                mxResources.get("addBlank"),
                new TextAddAction(TextAddAction.BLANK),
                url + "text-addblank.png")).setToolTipText(mxResources.get("addBlank"));

        textToolBar.add(editor.bind(
                mxResources.get("delete"),
                new TextDeleteAction(),
                url + "text-delete.png")).setToolTipText(mxResources.get("delete"));

        textToolBar.addSeparator(separator);

        textToolBar.add(editor.bind(
                mxResources.get("undo"),
                new TextHistoryAction(TextHistoryAction.UNDO),
                url + "text-undo.png")).setToolTipText(mxResources.get("undo"));
        textToolBar.add(editor.bind(
                mxResources.get("redo"),
                new TextHistoryAction(TextHistoryAction.REDO),
                url + "text-redo.png")).setToolTipText(mxResources.get("redo"));

        textToolBar.addSeparator(separator);

        textToolBar.add(editor.bind(
                mxResources.get("match"),
                new MatchAction(),
                url + "text-match.gif")).setToolTipText(mxResources.get("match"));

        textToolBar.add(editor.bind(
                mxResources.get("showThisTags"),
                new ShowThisTagsAction(),
                url + "text-showthistags.png")).setToolTipText(mxResources.get("showThisTags"));

        textToolBar.add(editor.bind(
                mxResources.get("removeOtherTags"),
                new RemoveOtherTagsAction(),
                url + "text-removeothertags.png")).setToolTipText(mxResources.get("removeOtherTags"));

        textToolBar.addSeparator(separator);

        textToolBar.add(editor.bind(
                mxResources.get("repaint"),
                new RepaintAction(),
                url + "text-repaint.gif")).setToolTipText(mxResources.get("repaint"));
		
		
		/*
		 * 增加换页工具栏
		 */
        this.addSeparator(new Dimension(20, 0)); //index = 7

        this.add(editor.bind(
                "pre",
                new PreAction(),
                url + "pre.gif")).setToolTipText(mxResources.get("pre")); //index = 8
        this.add(editor.bind(
                "next",
                new NextAction(),
                url + "next.gif")).setToolTipText(mxResources.get("next")); //index = 9

        this.addSeparator(separator); //index = 10

        final JComboBox<String> pageCombo = new JComboBox<String>();

        pageCombo.setEditable(false);
        pageCombo.setMinimumSize(new Dimension(150, 0));
        pageCombo.setMaximumSize(new Dimension(150, 100));

        //获得句子的个数
        int treeCount = editor.getDataTree().getTreeCount().intValue();
        //清空pageCombo
        pageCombo.removeAllItems();
        //未加载文件时
        if (treeCount == 0) {
            pageCombo.addItem("欢迎首次使用");
        } else {
            //增加pageCombo中的选项
            for (int i = 0; i < treeCount; i++) {
                pageCombo.addItem("Page: " + (i + 1) + " / " + treeCount);
            }
        }

        //设置页号
        int toolBarComboBoxIndex = editor.getToolBarComboBoxIndex();
        pageCombo.setSelectedIndex(toolBarComboBoxIndex);

        final GoAction itemListener = new GoAction();
        pageCombo.addItemListener(itemListener);

        this.add(pageCombo); //index = 11

        this.addSeparator(separator); //index = 12
		
		/*
		 *	增加缩放工具栏 
		 */
        this.add(editor.bind(
                "zoomIn",
                mxGraphActions.getZoomInAction(),
                url + "zoomin.gif")).setToolTipText(mxResources.get("zoomIn")); //index = 13

        this.add(editor.bind(
                "zoomOut",
                mxGraphActions.getZoomOutAction(),
                url + "zoomout.gif")).setToolTipText(mxResources.get("zoomOut")); //index = 14


        final JComboBox<Object> zoomCombo = new JComboBox<Object>(new Object[]{mxResources.get("zoomActual"),
                "140%", "130%", "120%", "110%", "100%", "90%", "80%", "70%"});
        zoomCombo.setEditable(true);
        zoomCombo.setMinimumSize(new Dimension(100, 0));
        zoomCombo.setMaximumSize(new Dimension(100, 100));

        zoomCombo.addActionListener(new ActionListener() {
            /**
             *
             */
            public void actionPerformed(ActionEvent e) {
                mxGraphComponent graphComponent = editor.getCurGraphComponent();

                // Zoomcombo is changed when the scale is changed in the diagram
                // but the change is ignored here
                if (!ignoreZoomChange) {
                    String zoom = zoomCombo.getSelectedItem().toString();

                    if (zoom.equals(mxResources.get("zoomActual"))) {
                        graphComponent.zoomActual();
                    } else {
                        try {
                            zoom = zoom.replace("%", "");
                            double scale = Math.min(16, Math.max(0.01,
                                    Double.parseDouble(zoom) / 100));
                            graphComponent.zoomTo(scale, graphComponent
                                    .isCenterZoom());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(editor, ex
                                    .getMessage());
                        }
                    }
                }
            }
        });

        this.add(zoomCombo); //index =15
    }

    /**
     * 将文件中句子个数加进去
     *
     * @param editor
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void update(final BasicGraphEditor editor) {
        // pageCombo的索引号是22
        JComboBox pageCombo = (JComboBox) editor.getToolBar().getComponentAtIndex(EditorToolBar.pageComboIndex);
        //itemListener是获取的对象数组中的第一个
        GoAction itemListener = (GoAction) pageCombo.getItemListeners()[0];
        //移去itemListener监听器
        pageCombo.removeItemListener(itemListener);

        //获得句子的个数
        int treeCount = editor.getDataTree().getTreeCount().intValue();
        //清空pageCombo
        pageCombo.removeAllItems();
        //增加pageCombo中的选项
        for (int i = 0; i < treeCount; i++) {
            pageCombo.addItem("Page: " + (i + 1) + " / " + treeCount);
        }

        //设置页号
        int toolBarComboBoxIndex = editor.getToolBarComboBoxIndex();
        pageCombo.setSelectedIndex(toolBarComboBoxIndex);

        //恢复itemListener监听器
        pageCombo.addItemListener(itemListener);
    }

    /**
     * 设置当前显示的页号
     * 同时itemStateChanged=true 触发该事件 创建显示当前树形结构
     *
     * @param editor
     */
    @SuppressWarnings("rawtypes")
    public static void setPage(final BasicGraphEditor editor) {
        // pageCombo的索引号是22
        JComboBox pageCombo = (JComboBox) editor.getToolBar().getComponentAtIndex(EditorToolBar.pageComboIndex);

        //设置页号
        int toolBarComboBoxIndex = editor.getToolBarComboBoxIndex();
        pageCombo.setSelectedIndex(toolBarComboBoxIndex);
    }

    /**
     * 设置当前页号
     * 仅设置页号，并不创建新树
     *
     * @param editor
     */
    @SuppressWarnings("rawtypes")
    public static void setPageCombo(final BasicGraphEditor editor) {
        // pageCombo的索引号是22
        JComboBox pageCombo = (JComboBox) editor.getToolBar().getComponentAtIndex(EditorToolBar.pageComboIndex);
        //itemListener是获取的对象数组中的第一个
        GoAction itemListener = (GoAction) pageCombo.getItemListeners()[0];
        //移去itemListener监听器
        pageCombo.removeItemListener(itemListener);

        //设置页号
        int toolBarComboBoxIndex = editor.getToolBarComboBoxIndex();
        pageCombo.setSelectedIndex(toolBarComboBoxIndex);

        //恢复itemListener监听器
        pageCombo.addItemListener(itemListener);

    }

}
