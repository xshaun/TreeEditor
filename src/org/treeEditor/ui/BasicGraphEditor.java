package org.treeEditor.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.*;
import java.io.File;
import java.util.Enumeration;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUndoManager;
import com.mxgraph.util.mxUndoableEdit;
import com.mxgraph.util.mxUndoableEdit.mxUndoableChange;
import com.mxgraph.view.mxGraph;

import static org.treeEditor.assist.LoadFile.loadImage;


public class BasicGraphEditor extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final String LINE_TAB = System.getProperty("line.separator") + "\t";

    /**
     * Adds required resources for i18n
     */
    static {
        try {
            mxResources.add("org/treeEditor/resources/editor");
        } catch (Exception e) {
            //* should add log */
        }
    }

//    /**
//     * 存储从文件中读入的信息
//     */
//    protected DataTree dataTree = new DataTree();

    /**
     * 工具栏
     */
    protected EditorToolBar toolBar;

    protected int toolBarComboBoxIndex = 0;

    /**
     *
     */
    protected mxGraphOutline graphOutline;

    /**
     *
     */
    protected JPanel libraryPane;

    /**
     * 当前显示面板
     */
    protected CustomGraphComponent currentGraphComponent = new CustomGraphComponent(new CustomGraph());

    /**
     *
     */
    protected EditorTabbedPane tabbedPane;

    /**
     *
     */
    protected EditorTextScrollPane textScrollPane;

    /**
     *
     */
    protected String appTitle;

    /**
     *
     */
    protected JLabel statusBar;

    /**
     *
     */
    protected File currentFile;

    /**
     * Flag indicating whether the current graph has been modified
     */
    protected boolean modified = false;

    /**
     *
     */
    protected mxRubberband currentRubberband;

    /**
     *
     */
    protected EditorKeyboardHandler currentKeyboardHandler;

    /**
     *
     */
    public BasicGraphEditor(String appTitle) {
        // Stores and updates the frame title
        this.appTitle = appTitle;

        // Creates the graph outline component
        graphOutline = new mxGraphOutline(currentGraphComponent);

        // Creates the library pane that contains the tabs with the palettes
        libraryPane = new JPanel();

        // Creates the textScroll pane
        textScrollPane = new EditorTextScrollPane(this);

        // Creates the lefter split pane that contains the library with the
        // palettes and the graph outline on the left side of the window
        JSplitPane lefter = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                libraryPane, graphOutline);
        lefter.setDividerLocation(320);
        lefter.setResizeWeight(1);
        lefter.setDividerSize(6);
        lefter.setBorder(null);

        // Creates the righter split pane that contains the graphComponent and
        // textScrollPane on the right side of the window
        JSplitPane righter = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                currentGraphComponent, textScrollPane);
        righter.setDividerLocation(450);
        righter.setResizeWeight(1);
        righter.setDividerSize(6);
        righter.setBorder(null);

        // Creates the outer split pane that contains the lefter split pane and
        // the righter component on the right side of the window
        JSplitPane outer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lefter, righter);
        outer.setOneTouchExpandable(true);
        outer.setDividerLocation(200);
        outer.setDividerSize(6);
        outer.setBorder(null);

        // Creates the status bar
        statusBar = createStatusBar();

        // Display some useful information about repaint events
//		installRepaintListener();

        // Puts everything together
        setLayout(new BorderLayout());
        add(outer, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
//        installToolBar();
//        installTabbedPane();
//        installTextPane();


        // Installs rubberband selection and handling for some special
        // keystrokes such as F2, Control-C, -V, X, A etc.
        installHandlers();
        installListeners();
        updateTitle();

    }

    public class CustomGraph extends mxGraph {

        /**
         * Stores a reference to the graph and creates the command history
         */
        protected mxUndoManager undoManager = new mxUndoManager();

        /**
         *
         */
        protected mxIEventListener undoHandler = (source, evt) -> undoManager.undoableEditHappened((mxUndoableEdit) evt.getProperty("edit"));

        /**
         *
         */
        protected mxIEventListener changeTracker = (source, evt) -> setModified(true);

        /**
         * Custom graph that defines the alternate edge style to be used when
         * the middle control point of edges is double clicked (flipped).
         */
        public CustomGraph() {
            // Do not change the scale and translation after files have been loaded
            setResetViewOnRootChange(false);

            // Updates the modified flag if the graph model changes
            getModel().addListener(mxEvent.CHANGE, changeTracker);

            // Adds the command history to the model and view
            getModel().addListener(mxEvent.UNDO, undoHandler);
            getView().addListener(mxEvent.UNDO, undoHandler);

            // Keeps the selection in sync with the command history
            mxIEventListener undoHandler = (source, evt) -> {
                List<mxUndoableChange> changes = ((mxUndoableEdit) evt
                        .getProperty("edit")).getChanges();
                setSelectionCells(getSelectionCellsForChanges(changes));
            };

            undoManager.addListener(mxEvent.UNDO, undoHandler);
            undoManager.addListener(mxEvent.REDO, undoHandler);

            setAlternateEdgeStyle("edgeStyle=mxEdgeStyle.ElbowConnector;elbow=vertical");

        }

        /**
         * 插入边
         *
         * @param parent
         * @param source
         * @param target
         * @return
         */
        public Object insertEdge(Object parent, Object source, Object target) {
            return super.insertEdge(parent, null, null, source, target);
        }

        /**
         * 插入叶子节点
         *
         * @param parent
         * @param value
         * @param x
         * @param y
         * @return
         */
        public Object insertLeafNode(Object parent, Object value, double x, double y, boolean alignLeft) {
            StringBuilder style = new StringBuilder();
            style.append("fillColor=#91D100;gradientColor=#78BA00;strokeColor=#78BA00;fontColor=#151515;");
            if (alignLeft) {
                style.append("align=left");
            }
            return super.insertVertex(
                    parent,  //parent - Cell that specifies the parent of the new vertex.
                    null,  //id - Optional string that defines the Id of the new vertex.
                    value,  //value - Object to be used as the user object.
                    x,  //x - Integer that defines the x coordinate of the vertex.
                    y,  //y - Integer that defines the y coordinate of the vertex.
                    75,  //width - Integer that defines the width of the vertex.
                    38,  //height - Integer that defines the height of the vertex.
                    style.toString()  //style - Optional string that defines the cell style.
            );
        }

        /**
         * 插入节点
         *
         * @param parent
         * @param value
         * @param x
         * @param y
         * @return
         */
        public Object insertNode(Object parent, Object value, double x, double y) {
            return super.insertVertex(
                    parent,    //parent - Cell that specifies the parent of the new vertex.
                    null,    //id - Optional string that defines the Id of the new vertex.
                    value,    //value - Object to be used as the user object.
                    x,    //x - Integer that defines the x coordinate of the vertex.
                    y,    //y - Integer that defines the y coordinate of the vertex.
                    75,    //width - Integer that defines the width of the vertex.
                    38    //height - Integer that defines the height of the vertex.
            );
        }

        /**
         * @return the undoManager
         */
        public mxUndoManager getUndoManager() {
            return undoManager;
        }

        /**
         * @return the changeTracker
         */
        public mxIEventListener getChangeTracker() {
            return changeTracker;
        }

    }

    /**
     *
     */
    public void clearUndoManager() {
        currentGraphComponent.getGraph().getUndoManager().clear();
    }

    /**
     *
     */
    protected void showGraphPopupMenu(MouseEvent e, CustomGraphComponent graphComponent) {
        Point pt = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(),
                graphComponent);
        EditorPopupMenu menu = new EditorPopupMenu(this, EditorPopupMenu.GRAPHMENU);
        menu.show(graphComponent, pt.x, pt.y);

        e.consume();
    }

    /**
     *
     */
    protected void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            currentGraphComponent.zoomIn();
        } else {
            currentGraphComponent.zoomOut();
        }
    }

    /**
     *
     */
    protected void installHandlers() {
        currentRubberband = new mxRubberband(currentGraphComponent);
    }

    /**
     *
     */
    protected void installListeners() {
        // Installs mouse wheel listener for zooming
        MouseWheelListener wheelTracker = new MouseWheelListener() {
            /**
             *
             */
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getSource() instanceof mxGraphOutline
                        || e.isControlDown()) {
                    BasicGraphEditor.this.mouseWheelMoved(e);
                }
            }
        };

        // Handles mouse wheel events in the outline and graph component
        graphOutline.addMouseWheelListener(wheelTracker);
        currentGraphComponent.addMouseWheelListener(wheelTracker);

//		// Installs the popup menu in the outline
//		graphOutline.addMouseListener(new MouseAdapter()
//		{
//
//			/**
//			 * 
//			 */
//			public void mousePressed(MouseEvent e)
//			{
//				// Handles context menu on the Mac where the trigger is on mousepressed
//				mouseReleased(e);
//			}
//
//			/**
//			 * 
//			 */
//			public void mouseReleased(MouseEvent e)
//			{
//				if (e.isPopupTrigger())
//				{
//					showOutlinePopupMenu(e);
//				}
//			}
//
//		});
//
        // Installs the popup menu in the graph component
        currentGraphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

            /**
             *
             */
            public void mousePressed(MouseEvent e) {
                // Handles context menu on the Mac where the trigger is on mousepressed
                mouseReleased(e);
            }

            /**
             *
             */
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showGraphPopupMenu(e, currentGraphComponent);
                }
            }
        });
    }

    /**
     *
     */
    protected JLabel createStatusBar() {
        JLabel statusBar = new JLabel(mxResources.get("ready"));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));

        return statusBar;
    }

    /**
     *
     */
    protected void installToolBar() {
        this.toolBar = new EditorToolBar(this, JToolBar.HORIZONTAL);
        add(toolBar, BorderLayout.NORTH);
    }

    /**
     *
     */
    protected void installTabbedPane() {
        this.tabbedPane = new EditorTabbedPane(this);
        add(this.tabbedPane, BorderLayout.CENTER);
    }

//    /**
//     * dataTree 的 setter() 和 getter()
//     */
//    public void setDataTree(DataTree dataTree) {
//        this.dataTree = dataTree;
//    }
//
//    public DataTree getDataTree() {
//        return this.dataTree;
//    }

//    /**
//     * toolBarComboBoxIndex 的 setter() 和 getter()
//     */
//    public void setToolBarComboBoxIndex(int toolBarComboBoxIndex) {
//        this.toolBarComboBoxIndex = toolBarComboBoxIndex;
//    }
//
//    public int getToolBarComboBoxIndex() {
//        return this.toolBarComboBoxIndex;
//    }

    /**
     * currentGraphComponent 的 setter() 和 getter()
     */
    public void setcurrentGraphComponent(CustomGraphComponent currentGraphComponent) {
        this.currentGraphComponent = currentGraphComponent;
    }

    public CustomGraphComponent getcurrentGraphComponent() {
        return this.currentGraphComponent;
    }

    /**
     * @return the tabbedPane
     */
    public EditorTabbedPane getTabbedPane() {
        return this.tabbedPane;
    }

    /**
     * textPane 的 setter() 和 getter()
     */
    public void setTextScrollPane(EditorTextScrollPane textScrollPane) {
        this.textScrollPane = textScrollPane;
    }

    public EditorTextScrollPane getTextScrollPane() {
        return textScrollPane;
    }

//    public void updateTextScrollPane() {
//        //获取文本框
//        TextPane chTextPane = this.textScrollPane.getChTextPane();
//        TextPane enTextPane = this.textScrollPane.getEnTextPane();
//
//        //获取文本
//        String chText = dataTree.getChMap().get(this.toolBarComboBoxIndex);
//        String enText = dataTree.getEnMap().get(this.toolBarComboBoxIndex);
//
//        //截取文本
//        if (!chText.matches("<a m=\"t\".*>.*</a>")) {
//            chText = chText.replaceAll("(</?[^(a|s|>)]+>)+", " ").replaceAll(" +", " ");
//        }
//        if (!enText.matches("<a m=\"t\".*>.*</a>")) {
//            enText = enText.replaceAll("(</?[^(a|s|>)]+>)+", " ").replaceAll(" +", " ");
//        }
//
//        //设置样式
//        SimpleAttributeSet attrSet = new SimpleAttributeSet();
//        StyleConstants.setItalic(attrSet, false);
//        StyleConstants.setBold(attrSet, false);
//        StyleConstants.setUnderline(attrSet, false);
//        StyleConstants.setForeground(attrSet, Color.BLACK);
//
//        chTextPane.setCharacterAttributes(attrSet, true);
//        enTextPane.setCharacterAttributes(attrSet, true);
//
//        //增加文本
//        chTextPane.setText(chText.replaceAll("[" + LINE_TAB + "]", ""));
//        enTextPane.setText(enText.replaceAll("[" + LINE_TAB + "]", ""));
//
//        //清空记录
//        chTextPane.getTextUndoManager().discardAllEdits();
//        enTextPane.getTextUndoManager().discardAllEdits();
//
//    }

    /**
     *
     */
    public void setCurrentFile(File file) {
        File oldValue = currentFile;
        currentFile = file;

        firePropertyChange("currentFile", oldValue, file);

        if (oldValue != file) {
            updateTitle();
        }
    }

    /**
     *
     */
    public File getCurrentFile() {
        return this.currentFile;
    }

    /**
     * @param modified
     */
    public void setModified(boolean modified) {
        boolean oldValue = this.modified;
        this.modified = modified;

        firePropertyChange("modified", oldValue, modified);

        if (oldValue != modified) {
            updateTitle();
        }
    }

    /**
     * @return whether or not the current graph has been modified
     */
    public boolean isModified() {
        return this.modified;
    }

    /**
     *
     */
    public void updateTitle() {
        JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);

        if (frame != null) {
            String title = (currentFile != null) ? currentFile
                    .getAbsolutePath() : mxResources.get("welcome");

            if (modified) {
                title += "*";
            }

            frame.setTitle(title + " - " + appTitle);
        }
    }

    /**
     * @param name
     * @param action
     * @return a new Action bound to the specified string name
     */
    public Action bind(String name, final Action action) {
        return bind(name, action, null);
    }

    /**
     * @param name
     * @param action
     * @return a new Action bound to the specified string name and icon
     */
    @SuppressWarnings("serial")
    public Action bind(String name, final Action action, String iconName) {
        AbstractAction newAction = new AbstractAction(name, (iconName != null) ? new ImageIcon(
                loadImage(iconName)) : null) {
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(new ActionEvent(getcurrentGraphComponent(), e
                        .getID(), e.getActionCommand()));
            }
        };

        newAction.putValue(Action.SHORT_DESCRIPTION, action.getValue(Action.SHORT_DESCRIPTION));

        return newAction;
    }

    /**
     *
     */
    public void about() {
        JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);

        if (frame != null) {
            EditorAboutFrame about = new EditorAboutFrame(frame);
            about.setModal(true);

            // Centers inside the application frame
            int x = frame.getX() + (frame.getWidth() - about.getWidth()) / 2;
            int y = frame.getY() + (frame.getHeight() - about.getHeight()) / 2;
            about.setLocation(x, y);

            // Shows the modal dialog and waits
            about.setVisible(true);
        }
    }

    /**
     *
     */
    public boolean exit() {
        if (this.isModified()) {//文件已修改
            int choose = JOptionPane.showConfirmDialog(this, mxResources.get("loseChanges"));
            if (choose == JOptionPane.YES_OPTION) {//放弃保存
                JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);
                if (frame != null) {
                    frame.dispose();
                }
                return true;
            } else {//取消此操作
                return false;
            }
        } else {//文件未修改
            JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);
            if (frame != null) {
                frame.dispose();
            }
            return true;
        }
    }

    /**
     *
     */
    public void setLookAndFeel(String clazz) {
        JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);

        if (frame != null) {
            try {
                UIManager.setLookAndFeel(clazz);
                SwingUtilities.updateComponentTreeUI(frame);

                // Needs to assign the key bindings again
                currentKeyboardHandler = new EditorKeyboardHandler(currentGraphComponent);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     *
     */
    public JFrame createFrame(JMenuBar menuBar) {
        final JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.setSize(1250, 640);
        frame.setLocationRelativeTo(null); // 窗口居中
        frame.setIconImage(frame.getToolkit().getImage(loadImage("logo.gif")));

        //窗口关闭时进行检测
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame component = (JFrame) e.getSource();
                BasicGraphEditor editor = (BasicGraphEditor) component.getContentPane().getComponent(0);
                if (!editor.exit()) {
                    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  //阻止关闭窗口
                }
            }
        });

        // Updates the frame title
        updateTitle();

        return frame;
    }

    /**
     * 设置全局字体
     *
     * @param font
     */
    public static void initGlobalFontSetting(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<?> keys = UIManager.getDefaults().keys(); keys
                .hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }
}
