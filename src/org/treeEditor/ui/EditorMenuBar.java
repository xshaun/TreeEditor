package org.treeEditor.ui;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.TransferHandler;

import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;
import org.treeEditor.action.*;

import static org.treeEditor.assist.LoadFile.loadImage;


public class EditorMenuBar extends JMenuBar {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public EditorMenuBar(final BasicGraphEditor editor) {
        JMenu menu = null;
        JMenu subMenu = null;

        /*
         *  Creates the file menu
         */
        menu = add(new JMenu(mxResources.get("file")));

        menu.add(editor.bind(mxResources.get("open"), new OpenAction(), "open.gif"));

        menu.addSeparator();

        menu.add(editor.bind(mxResources.get("save"), new SaveAction(SaveAction.NOTSHOWDIALOG), "save.gif"));
        menu.add(editor.bind(mxResources.get("saveAs"), new SaveAction(SaveAction.SHOWDIALOG), "saveas.gif"));

        menu.addSeparator();

        menu.add(editor.bind(mxResources.get("pageSetup"), new PageSetupAction(), "pagesetup.gif"));
        menu.add(editor.bind(mxResources.get("print"), new PrintAction(), "print.gif"));

        menu.addSeparator();

        menu.add(editor.bind(mxResources.get("exit"), new ExitAction()));

        /*
         *  Creates the edit menu
         */
        menu = add(new JMenu(mxResources.get("edit")));

        // Creates the graphPane subMenu
        subMenu = (JMenu) menu.add(new JMenu(mxResources.get("graphPane")));
        subMenu.setIcon(new ImageIcon(loadImage("graphPane.gif")));

        subMenu.add(editor.bind(mxResources.get("undo"), new HistoryAction(HistoryAction.UNDO), "undo.gif"));
        subMenu.add(editor.bind(mxResources.get("redo"), new HistoryAction(HistoryAction.REDO), "redo.gif"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("cut"), TransferHandler.getCutAction(), "cut.gif"));
        subMenu.add(editor.bind(mxResources.get("copy"), TransferHandler.getCopyAction(), "copy.gif"));
        subMenu.add(editor.bind(mxResources.get("paste"), TransferHandler.getPasteAction(), "paste.gif"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("selectAll"), mxGraphActions.getSelectAllAction(), "selectall.gif"));
        subMenu.add(editor.bind(mxResources.get("selectNone"), mxGraphActions.getSelectNoneAction(), "selectnone.gif"));
        subMenu.add(editor.bind(mxResources.get("delete"), mxGraphActions.getDeleteAction(), "delete.gif"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("edit"), mxGraphActions.getEditAction(), "editor.gif"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("graphToText"), new GraphToText(), "graphtotext.gif"));


        // Creates the textPane subMenu
        subMenu = (JMenu) menu.add(new JMenu(mxResources.get("textPane")));
        subMenu.setIcon(new ImageIcon(loadImage("textPane.gif")));

        subMenu.add(editor.bind(mxResources.get("addTag"), new TextAddAction(TextAddAction.TAG), "text-addtag.png"));
        subMenu.add(editor.bind(mxResources.get("replaceTag"), new TextReplaceAction(TextReplaceAction.TAG), "text-replacetag.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("addAttr"), new TextAddAction(TextAddAction.ATTR), "text-addattr.png"));
        subMenu.add(editor.bind(mxResources.get("replaceAttr"), new TextReplaceAction(TextReplaceAction.ATTR), "text-replaceattr.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("addBlank"), new TextAddAction(TextAddAction.BLANK), "text-addblank.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("delete"), new TextDeleteAction(), "text-delete.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("undo"), new TextHistoryAction(TextHistoryAction.UNDO), "text-undo.png"));
        subMenu.add(editor.bind(mxResources.get("redo"), new TextHistoryAction(TextHistoryAction.REDO), "text-redo.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("match"), new MatchAction(), "text-match.gif"));

        subMenu.add(editor.bind(mxResources.get("showThisTags"), new ShowThisTagsAction(), "text-showthistags.png"));

        subMenu.add(editor.bind(mxResources.get("removeOtherTags"), new RemoveOtherTagsAction(), "text-removeothertags.png"));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("repaint"), new RepaintAction(), "text-repaint.gif"));

        subMenu.addSeparator();

        //Creates the fontSetup subMenu
        subMenu = (JMenu) subMenu.add(new JMenu(mxResources.get("fontSetup")));
        subMenu.setIcon(new ImageIcon(loadImage("font.gif")));

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

        menu.add(editor.bind(mxResources.get("pre"), new PreAction(), "pre.gif"));
        menu.add(editor.bind(mxResources.get("next"), new NextAction(), "next.gif"));

        menu.addSeparator();

        //Creates the zoom subMenu
        subMenu = (JMenu) menu.add(new JMenu(mxResources.get("zoom")));
        subMenu.setIcon(new ImageIcon(loadImage("zoom.gif")));

        subMenu.add(editor.bind("140%", new ScaleAction(1.4)));
        subMenu.add(editor.bind("130%", new ScaleAction(1.3)));
        subMenu.add(editor.bind("120%", new ScaleAction(1.2)));
        subMenu.add(editor.bind("100%", new ScaleAction(1)));
        subMenu.add(editor.bind("80%", new ScaleAction(0.8)));
        subMenu.add(editor.bind("70%", new ScaleAction(0.7)));

        subMenu.addSeparator();

        subMenu.add(editor.bind(mxResources.get("custom"), new ScaleAction(0)));

        menu.addSeparator();

        menu.add(editor.bind(mxResources.get("zoomIn"), mxGraphActions.getZoomInAction(), "zoomin.gif"));
        menu.add(editor.bind(mxResources.get("zoomOut"), mxGraphActions.getZoomOutAction(), "zoomout.gif"));

        menu.addSeparator();

        menu.add(editor.bind(mxResources.get("zoomActual"), mxGraphActions.getZoomActualAction(), "zoomactual.gif"));

        /*
         * Creates the help menu
         */
        menu = add(new JMenu(mxResources.get("help")));

        menu.add(editor.bind(mxResources.get("JGraphOnline"), new HelpOnlineAction(HelpOnlineAction.JGRAPH), "helponline.gif"));
        menu.add(editor.bind(mxResources.get("about"), new AboutAction(), "about.gif"));

    }
}