package cn.edu.zzu.nlp.editor;

import cn.edu.zzu.nlp.GraphEditor;
import cn.edu.zzu.nlp.editor.EditorTextScrollPane.TextPane;
import cn.edu.zzu.nlp.tree.DataTree;
import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.util.*;
import com.mxgraph.util.mxUndoableEdit.mxUndoableChange;
import com.mxgraph.view.mxGraph;
import org.dom4j.Attribute;
import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;


public class BasicGraphEditor extends JPanel {

    /**
     *
     */
    public static final String LINE_TAB = System.getProperty("line.separator") + "\t";
    /**
     * Holds the shared number formatter.
     *
     * @see NumberFormat#getInstance()
     */
    public static final NumberFormat numberFormat = NumberFormat.getInstance();
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Adds required resources for i18n
     */
    static {
        try {
            mxResources.add("cn/edu/zzu/nlp/resources/editor");
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * 存储从文件中读入的信息
     */
    protected DataTree dataTree = new DataTree();

    /**
     * 工具栏
     */
    protected EditorToolBar toolBar = new EditorToolBar(this, JToolBar.HORIZONTAL);
    ;
    protected int toolBarComboBoxIndex = 0;

    /**
     * 原树结构显示面板
     */
    protected CustomGraphComponent chGraphComponent = new CustomGraphComponent(new CustomGraph());

    /**
     * 修改树显示面板
     */
    protected CustomGraphComponent enGraphComponent = new CustomGraphComponent(new CustomGraph());

    /**
     * 当前显示面板
     */
    protected CustomGraphComponent curGraphComponent = chGraphComponent;

    /**
     *
     */
    protected EditorTabbedPane tabbedPane;
    protected boolean alreadyAddTabbedPane = false;

    /**
     *
     */
    protected EditorSplitPane splitPane;
    protected boolean alreadyAddSplitdPane = false;

    /**
     *
     */
    protected EditorTextScrollPane textScrollPane = new EditorTextScrollPane(this);

    /**
     *
     */
    protected String appTitle;

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
    protected mxRubberband enRubberband;
    protected mxRubberband chRubberband;

    /**
     *
     */
    protected EditorKeyboardHandler enKeyboardHandler;
    protected EditorKeyboardHandler chKeyboardHandler;

    /**
     *
     */
    public BasicGraphEditor(String appTitle) {
        // Stores and updates the frame title
        this.appTitle = appTitle;

        // Creates the graph outline component
//		graphOutline = new mxGraphOutline(graphComponent);
//
//		// Creates the library pane that contains the tabs with the palettes
//		libraryPane = new JTabbedPane();
//
//		// Creates the inner split pane that contains the library with the
//		// palettes and the graph outline on the left side of the window
//		JSplitPane inner = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
//				libraryPane, graphOutline);
//		inner.setDividerLocation(320);
//		inner.setResizeWeight(1);
//		inner.setDividerSize(6);
//		inner.setBorder(null);
//
//		// Creates the outer split pane that contains the inner split pane and
//		// the graph component on the right side of the window
//		JSplitPane outer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inner,
//				graphComponent);
//		outer.setOneTouchExpandable(true);
//		outer.setDividerLocation(200);
//		outer.setDividerSize(6);
//		outer.setBorder(null);

        // Creates the status bar
//		statusBar = createStatusBar();

        // Display some useful information about repaint events
//		installRepaintListener();

        // Puts everything together
//		setLayout(new BorderLayout());
//		add(outer, BorderLayout.CENTER);
//		add(statusBar, BorderLayout.SOUTH);
//		installToolBar();
        installToolBarAndTabbedPane();
//		installToolBarAndSplitdPane();


        // Installs rubberband selection and handling for some special
        // keystrokes such as F2, Control-C, -V, X, A etc.
        installHandlers();
        installListeners();
        updateTitle();

    }

    /**
     * 设置全局字体
     *
     * @param fnt
     */
    public static void initGlobalFontSetting(Font fnt) {
        FontUIResource fontRes = new FontUIResource(fnt);
        for (Enumeration<?> keys = UIManager.getDefaults().keys(); keys
                .hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }

    /**
     *
     */
    protected void clearUndoManager() {
        chGraphComponent.getGraph().getUndoManager().clear();
        enGraphComponent.getGraph().getUndoManager().clear();
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
            curGraphComponent.zoomIn();
        } else {
            curGraphComponent.zoomOut();
        }
    }

    /**
     *
     */
    protected void installHandlers() {
        enRubberband = new mxRubberband(enGraphComponent);
        chRubberband = new mxRubberband(chGraphComponent);
        enKeyboardHandler = new EditorKeyboardHandler(enGraphComponent);
        chKeyboardHandler = new EditorKeyboardHandler(chGraphComponent);
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
//		graphOutline.addMouseWheelListener(wheelTracker);
        enGraphComponent.addMouseWheelListener(wheelTracker);
        chGraphComponent.addMouseWheelListener(wheelTracker);

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
        enGraphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

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
                    showGraphPopupMenu(e, enGraphComponent);
                }
            }
        });
        chGraphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

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
                    showGraphPopupMenu(e, chGraphComponent);
                }
            }
        });

//		// Installs a mouse motion listener to display the mouse location
//		graphComponent.getGraphControl().addMouseMotionListener(
//				new MouseMotionListener()
//				{
//
//					/*
//					 * (non-Javadoc)
//					 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
//					 */
//					public void mouseDragged(MouseEvent e)
//					{
//						mouseLocationChanged(e);
//					}
//
//					/*
//					 * (non-Javadoc)
//					 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
//					 */
//					public void mouseMoved(MouseEvent e)
//					{
//						mouseDragged(e);
//					}
//
//				});
    }

    /**
     *
     */
    protected void installToolBarAndTabbedPane() {
        setLayout(new BorderLayout());
        installToolBar();
        installTabbedPane();
        installTextPane();
    }

    /**
     *
     */
    protected void installToolBarAndSplitdPane() {
        setLayout(new BorderLayout());
        installToolBar();
        installSplitdPane();
        installTextPane();
    }

    /**
     *
     */
    protected void installToolBar() {
        add(toolBar, BorderLayout.NORTH);
    }

    /**
     *
     */
    protected void installTabbedPane() {
        this.tabbedPane = new EditorTabbedPane(this);
        this.alreadyAddTabbedPane = true;

        add(this.tabbedPane, BorderLayout.CENTER);
    }

    /**
     *
     */
    protected void installSplitdPane() {
        this.splitPane = new EditorSplitPane(this);
        this.alreadyAddSplitdPane = true;

        add(this.splitPane, BorderLayout.CENTER);

        //设置水平方向显示
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        //设置分隔符的位置
        splitPane.setResizeWeight(0.5);
    }

    /**
     *
     */
    protected void installTextPane() {
        add(textScrollPane, BorderLayout.SOUTH);
    }

    public DataTree getDataTree() {
        return this.dataTree;
    }

    /**
     * dataTree 的 setter() 和 getter()
     */
    public void setDataTree(DataTree dataTree) {
        this.dataTree = dataTree;
    }

    /**
     * toolBar 的 getter()
     */
    public EditorToolBar getToolBar() {
        return this.toolBar;
    }

    public int getToolBarComboBoxIndex() {
        return this.toolBarComboBoxIndex;
    }

    /**
     * toolBarComboBoxIndex 的 setter() 和 getter()
     */
    public void setToolBarComboBoxIndex(int toolBarComboBoxIndex) {
        this.toolBarComboBoxIndex = toolBarComboBoxIndex;
    }

    public CustomGraphComponent getCurGraphComponent() {
        return this.curGraphComponent;
    }

    /**
     * curGraphComponent 的 setter() 和 getter()
     */
    public void setCurGraphComponent(CustomGraphComponent newGraphComponent) {
        this.curGraphComponent = newGraphComponent;
    }

    public CustomGraphComponent getChGraphComponent() {
        return this.chGraphComponent;
    }

    /**
     * originalGraphComponent 的 setter() 和 getter()
     */
    public void setChGraphComponent(CustomGraphComponent newGraphComponent) {
        this.chGraphComponent = newGraphComponent;
    }

    public CustomGraphComponent getEnGraphComponent() {
        return this.enGraphComponent;
    }

    /**
     * modifyGraphComponent 的 setter() 和 getter()
     */
    public void setEnGraphComponent(CustomGraphComponent newGraphComponent) {
        this.enGraphComponent = newGraphComponent;
    }

    /**
     * @return the tabbedPane
     */
    public EditorTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /**
     * @return the splitPane
     */
    public EditorSplitPane getSplitPane() {
        return splitPane;
    }

    public EditorTextScrollPane getTextScrollPane() {
        return textScrollPane;
    }

    /**
     * textPane 的 setter() 和 getter()
     */
    public void setTextScrollPane(EditorTextScrollPane textScrollPane) {
        this.textScrollPane = textScrollPane;
    }

    public void updateTextScrollPane() {
        //获取文本框
        TextPane chTextPane = this.textScrollPane.getChTextPane();
        TextPane enTextPane = this.textScrollPane.getEnTextPane();

        //获取文本
        String chText = dataTree.getChMap().get(this.toolBarComboBoxIndex);
        String enText = dataTree.getEnMap().get(this.toolBarComboBoxIndex);

        //截取文本
        if (!chText.matches("<a m=\"t\".*>.*</a>")) {
            chText = chText.replaceAll("(</?[^(a|s|>)]+>)+", " ").replaceAll(" +", " ");
        }
        if (!enText.matches("<a m=\"t\".*>.*</a>")) {
            enText = enText.replaceAll("(</?[^(a|s|>)]+>)+", " ").replaceAll(" +", " ");
        }

        //设置样式
        SimpleAttributeSet attrSet = new SimpleAttributeSet();
        StyleConstants.setItalic(attrSet, false);
        StyleConstants.setBold(attrSet, false);
        StyleConstants.setUnderline(attrSet, false);
        StyleConstants.setForeground(attrSet, Color.BLACK);

        chTextPane.setCharacterAttributes(attrSet, true);
        enTextPane.setCharacterAttributes(attrSet, true);

        //增加文本
        chTextPane.setText(chText.replaceAll("[" + LINE_TAB + "]", ""));
        enTextPane.setText(enText.replaceAll("[" + LINE_TAB + "]", ""));

        //清空记录
        chTextPane.getTextUndoManager().discardAllEdits();
        enTextPane.getTextUndoManager().discardAllEdits();

    }

    /**
     *
     */
    public File getCurrentFile() {
        return currentFile;
    }

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
     * @return whether or not the current graph has been modified
     */
    public boolean isModified() {
        return modified;
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
    public Action bind(String name, final Action action, String iconUrl) {
        AbstractAction newAction = new AbstractAction(name, (iconUrl != null) ? new ImageIcon(
                BasicGraphEditor.class.getResource(iconUrl)) : null) {
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(new ActionEvent(getCurGraphComponent(), e
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
                //keyboardHandler = new EditorKeyboardHandler(this);
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
        frame.setLocation(0, 0);
        frame.setIconImage(frame.getToolkit().getImage(BasicGraphEditor.class.getResource("/cn/edu/zzu/nlp/images/logo.gif")));

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

    public class CustomGraph extends mxGraph {

        /**
         *
         */
        protected mxIEventListener changeTracker = new mxIEventListener() {
            public void invoke(Object source, mxEventObject evt) {
                setModified(true);
            }
        };
        // Stores a reference to the graph and creates the command history
        mxUndoManager undoManager = new mxUndoManager();
        /**
         *
         */
        mxIEventListener undoHandler = new mxIEventListener() {
            public void invoke(Object source, mxEventObject evt) {
                undoManager.undoableEditHappened((mxUndoableEdit) evt.getProperty("edit"));
            }
        };

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
            mxIEventListener undoHandler = new mxIEventListener() {
                public void invoke(Object source, mxEventObject evt) {
                    List<mxUndoableChange> changes = ((mxUndoableEdit) evt
                            .getProperty("edit")).getChanges();
                    setSelectionCells(getSelectionCellsForChanges(changes));
                }
            };

            undoManager.addListener(mxEvent.UNDO, undoHandler);
            undoManager.addListener(mxEvent.REDO, undoHandler);

            setAlternateEdgeStyle("edgeStyle=mxEdgeStyle.ElbowConnector;elbow=vertical");
        }

        /**
         * Prints out some useful information about the cell in the toolTip.
         */
        public String getToolTipForCell(Object cell) {
            String tip = "<html>";
            String caseName = this.getLabel(cell).replaceAll("\\{.*\\}|(" + System.getProperty("line.separator") + ")*", "");
            int hashCode = ((mxCell) cell).hashCode();
            tip += "CaseName:&nbsp;&nbsp;" + caseName + "<hr>";
            //tip +=  "<br>ID: " + id +"<hr>";

            if (!getModel().isEdge(cell)) {
                HashMap<Integer, List<Attribute>> attrMap = dataTree.getEnAttrMap();
                if (curGraphComponent == chGraphComponent) {
                    attrMap = dataTree.getChAttrMap();
                }

                List<Attribute> attrList = attrMap.get(hashCode);
                if (attrList != null) {
                    for (Attribute index : attrList) {
                        tip += (index.getName() + "=" + index.getValue() + "<br>");
                    }
                }

            } else {
                return null;
            }

            tip += "</html>";
            return tip;
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
            if (alignLeft) {
                return super.insertVertex(
                        parent,    //parent - Cell that specifies the parent of the new vertex.
                        null,    //id - Optional string that defines the Id of the new vertex.
                        value,    //value - Object to be used as the user object.
                        x,    //x - Integer that defines the x coordinate of the vertex.
                        y,    //y - Integer that defines the y coordinate of the vertex.
                        75,    //width - Integer that defines the width of the vertex.
                        38,    //height - Integer that defines the height of the vertex.
                        "fillColor=#91D100;gradientColor=#78BA00;strokeColor=#78BA00;fontColor=#151515;align=left"    //style - Optional string that defines the cell style.
                );
            }
            return super.insertVertex(
                    parent,    //parent - Cell that specifies the parent of the new vertex.
                    null,    //id - Optional string that defines the Id of the new vertex.
                    value,    //value - Object to be used as the user object.
                    x,    //x - Integer that defines the x coordinate of the vertex.
                    y,    //y - Integer that defines the y coordinate of the vertex.
                    75,    //width - Integer that defines the width of the vertex.
                    38,    //height - Integer that defines the height of the vertex.
                    "fillColor=#91D100;gradientColor=#78BA00;strokeColor=#78BA00;fontColor=#151515;"    //style - Optional string that defines the cell style.
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
    public class CustomGraphComponent extends mxGraphComponent {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         * @param graph
         */
        public CustomGraphComponent(mxGraph graph) {
            super(graph);

            // Sets switches typically used in an editor
//			setPageVisible(true);
            setGridVisible(true);
            setToolTips(true);
            getConnectionHandler().setCreateTarget(true);

            // Loads the defalt stylesheet from an external file
            mxCodec codec = new mxCodec();
            Document doc = mxUtils.loadDocument(GraphEditor.class.getResource(
                    "/cn/edu/zzu/nlp/resources/default-style.xml")
                    .toString());
            codec.decode(doc.getDocumentElement(), graph.getStylesheet());

            // Sets the background to white
            getViewport().setOpaque(true);
            getViewport().setBackground(Color.WHITE);

        }

        /**
         * Overrides getGraph
         *
         * @return
         */
        public CustomGraph getGraph() {
            return (CustomGraph) super.getGraph();
        }
    }
}
