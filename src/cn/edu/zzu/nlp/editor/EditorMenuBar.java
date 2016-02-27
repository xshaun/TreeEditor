package cn.edu.zzu.nlp.editor;

import cn.edu.zzu.nlp.editor.EditorActions.*;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;

import javax.swing.*;

public class EditorMenuBar extends JMenuBar {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private final String url = ("/cn/edu/zzu/nlp/images/");

    public EditorMenuBar(final BasicGraphEditor editor) {
        JMenu menu = null;
        JMenu subMenu = null;

		/*
         *  Creates the file menu
		 */
        menu = add(new JMenu(mxResources.get("file")));

        menu.add(editor.bind(mxResources.get("open"), new OpenAction(), url + "open.gif"));

        menu.addSeparator();

        menu.add(editor.bind(mxResources.get("save"), new SaveAction(SaveAction.NOTSHOWDIALOG), url + "save.gif"));
        menu.add(editor.bind(mxResources.get("saveAs"), new SaveAction(SaveAction.SHOWDIALOG), url + "saveas.gif"));
        menu.add(editor.bind(mxResources.get("finalSave"), new FinalSaveAction(), url + "finalsave.png"));

        menu.addSeparator();

        menu.add(editor.bind(mxResources.get("pageSetup"), new PageSetupAction(), url + "pagesetup.gif"));
        menu.add(editor.bind(mxResources.get("print"), new PrintAction(), url + "print.gif"));

        menu.addSeparator();

        menu.add(editor.bind(mxResources.get("exit"), new ExitAction()));

		/*
         *  Creates the edit menu
		 */
        menu = add(new JMenu(mxResources.get("edit")));

        // Creates the graphPane subMenu
        subMenu = (JMenu) menu.add(new JMenu(mxResources.get("graphPane")));
        subMenu.setIcon(new ImageIcon(EditorMenuBar.class.getResource(url + "graphPane.gif")));

        subMenu.add(editor.bind(mxResources.get("undo"), new HistoryAction(HistoryAction.UNDO), url + "undo.gif"));
        subMenu.add(editor.bind(mxResources.get("redo"), new HistoryAction(HistoryAction.REDO), url + "redo.gif"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("cut"), TransferHandler.getCutAction(), url + "cut.gif"));
        subMenu.add(editor.bind(mxResources.get("copy"), TransferHandler.getCopyAction(), url + "copy.gif"));
        subMenu.add(editor.bind(mxResources.get("paste"), TransferHandler.getPasteAction(), url + "paste.gif"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("selectAll"), mxGraphActions.getSelectAllAction(), url + "selectall.gif"));
        subMenu.add(editor.bind(mxResources.get("selectNone"), mxGraphActions.getSelectNoneAction(), url + "selectnone.gif"));
        subMenu.add(editor.bind(mxResources.get("delete"), mxGraphActions.getDeleteAction(), url + "delete.gif"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("edit"), mxGraphActions.getEditAction(), url + "editor.gif"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("graphToText"), new GraphToText(), url + "graphtotext.gif"));


        // Creates the textPane subMenu
        subMenu = (JMenu) menu.add(new JMenu(mxResources.get("textPane")));
        subMenu.setIcon(new ImageIcon(EditorMenuBar.class.getResource(url + "textPane.gif")));

        subMenu.add(editor.bind(mxResources.get("addTag"), new TextAddAction(TextAddAction.TAG), url + "text-addtag.png"));
        subMenu.add(editor.bind(mxResources.get("replaceTag"), new TextReplaceAction(TextReplaceAction.TAG), url + "text-replacetag.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("addAttr"), new TextAddAction(TextAddAction.ATTR), url + "text-addattr.png"));
        subMenu.add(editor.bind(mxResources.get("replaceAttr"), new TextReplaceAction(TextReplaceAction.ATTR), url + "text-replaceattr.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("addBlank"), new TextAddAction(TextAddAction.BLANK), url + "text-addblank.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("delete"), new TextDeleteAction(), url + "text-delete.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("undo"), new TextHistoryAction(TextHistoryAction.UNDO), url + "text-undo.png"));
        subMenu.add(editor.bind(mxResources.get("redo"), new TextHistoryAction(TextHistoryAction.REDO), url + "text-redo.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("match"), new MatchAction(), url + "text-match.gif"));

        subMenu.add(editor.bind(mxResources.get("showThisTags"), new ShowThisTagsAction(), url + "text-showthistags.png"));

        subMenu.add(editor.bind(mxResources.get("removeOtherTags"), new RemoveOtherTagsAction(), url + "text-removeothertags.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("repaint"), new RepaintAction(), url + "text-repaint.gif"));

        subMenu.addSeparator();

        //Creates the fontSetup subMenu
        subMenu = (JMenu) subMenu.add(new JMenu(mxResources.get("fontSetup")));
        subMenu.setIcon(new ImageIcon(EditorMenuBar.class.getResource(url + "font.gif")));

        subMenu.add(editor.bind(mxResources.get("name"), new FontNameAction()));

        //Creates the fontSetup-size subMenu
        subMenu = (JMenu) subMenu.add(new JMenu(mxResources.get("size")));

        subMenu.add(editor.bind("8", new FontSizeAction(8)));
        subMenu.add(editor.bind("12", new FontSizeAction(12)));
        subMenu.add(editor.bind("14", new FontSizeAction(14)));
        subMenu.add(editor.bind("16", new FontSizeAction(16)));
        subMenu.add(editor.bind("18", new FontSizeAction(18)));
        subMenu.add(editor.bind("20", new FontSizeAction(20)));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("custom"), new FontSizeAction(0)));
		
		/*
		 *  Creates the view menu
		 */
        menu = add(new JMenu(mxResources.get("view")));

        menu.add(editor.bind(mxResources.get("pre"), new PreAction(), url + "pre.gif"));
        menu.add(editor.bind(mxResources.get("next"), new NextAction(), url + "next.gif"));

        menu.addSeparator();

        //Creates the zoom subMenu
        subMenu = (JMenu) menu.add(new JMenu(mxResources.get("zoom")));
        subMenu.setIcon(new ImageIcon(EditorMenuBar.class.getResource(url + "zoom.gif")));

        subMenu.add(editor.bind("140%", new ScaleAction(1.4)));
        subMenu.add(editor.bind("130%", new ScaleAction(1.3)));
        subMenu.add(editor.bind("120%", new ScaleAction(1.2)));
        subMenu.add(editor.bind("100%", new ScaleAction(1)));
        subMenu.add(editor.bind("80%", new ScaleAction(0.8)));
        subMenu.add(editor.bind("70%", new ScaleAction(0.7)));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("custom"), new ScaleAction(0)));

        menu.addSeparator();

        menu.add(editor.bind(mxResources.get("zoomIn"), mxGraphActions.getZoomInAction(), url + "zoomin.gif"));
        menu.add(editor.bind(mxResources.get("zoomOut"), mxGraphActions.getZoomOutAction(), url + "zoomout.gif"));

        menu.addSeparator();

        menu.add(editor.bind(mxResources.get("zoomActual"), mxGraphActions.getZoomActualAction(), url + "zoomactual.gif"));
		
		/*
		 *  Creates the window menu
		 */
        menu = add(new JMenu(mxResources.get("window")));

        menu.add(editor.bind(mxResources.get("split"), new WindowAction(WindowAction.SPLIT), url + "split.gif"));
        menu.add(editor.bind(mxResources.get("tabbed"), new WindowAction(WindowAction.TABBED), url + "tabbed.gif"));

        menu.addSeparator();

		/*
		 * Creates the help menu
		 */
        menu = add(new JMenu(mxResources.get("help")));

        menu.add(editor.bind(mxResources.get("JGraphOnline"), new HelpOnlineAction(HelpOnlineAction.JGRAPH), url + "helponline.gif"));
        menu.add(editor.bind(mxResources.get("about"), new AboutAction(), url + "about.gif"));

    }

    ;
};