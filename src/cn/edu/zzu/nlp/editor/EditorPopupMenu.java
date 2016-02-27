package cn.edu.zzu.nlp.editor;

import cn.edu.zzu.nlp.editor.EditorActions.*;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;

import javax.swing.*;

public class EditorPopupMenu extends JPopupMenu {

    /**
     *
     */
    public static final int TEXTMENU = 1;
    public static final int GRAPHMENU = 2;
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private final String url = ("/cn/edu/zzu/nlp/images/");


    public EditorPopupMenu(final BasicGraphEditor editor, int type) {

        JMenu subMenu;

        switch (type) {
            /**
             * textPopupMenu
             */
            case TEXTMENU:

//			/*
//			 * Creates the text menu 
//			 */
//			subMenu = (JMenu) this.add(new JMenu(mxResources.get("text")));
//			subMenu.setIcon(new ImageIcon(EditorPopupMenu.class.getResource(url+"text-text.png")));
//			
//			subMenu.add(editor.bind(mxResources.get("addText"), new TextAddAction(TextAddAction.TEXT), url+"text-addtext.png"));
//			
//			subMenu.add(editor.bind(mxResources.get("replaceText"), new TextReplaceAction(TextReplaceAction.TEXT), url+"text-replacetext.png"));
//			
//			this.addSeparator();

			/*
             * Creates the add menu
			 */
                subMenu = (JMenu) this.add(new JMenu(mxResources.get("add")));
                subMenu.setIcon(new ImageIcon(EditorPopupMenu.class.getResource(url + "add.png")));

                subMenu.add(editor.bind(mxResources.get("addTag"), new TextAddAction(TextAddAction.TAG), url + "text-addtag.png"));

                subMenu.add(editor.bind(mxResources.get("addAttr"), new TextAddAction(TextAddAction.ATTR), url + "text-addattr.png"));

                subMenu.add(editor.bind(mxResources.get("addBlank"), new TextAddAction(TextAddAction.BLANK), url + "text-addblank.png"));


                this.addSeparator();
			
			/*
			 * Creates the replace menu 
			 */
                subMenu = (JMenu) this.add(new JMenu(mxResources.get("replace")));

                subMenu.setIcon(new ImageIcon(EditorPopupMenu.class.getResource(url + "replace.png")));

                subMenu.add(editor.bind(mxResources.get("replaceTag"), new TextReplaceAction(TextReplaceAction.TAG), url + "text-replacetag.png"));

                subMenu.add(editor.bind(mxResources.get("replaceAttr"), new TextReplaceAction(TextReplaceAction.ATTR), url + "text-replaceattr.png"));

                this.addSeparator();
			
			/*
			 * Creates the delete menuItem 
			 */
                this.add(editor.bind(mxResources.get("delete"), new TextDeleteAction(), url + "text-delete.png"));

                this.addSeparator();
			
			/*
			 * Creates the undo menuItem  
			 */
                this.add(editor.bind(mxResources.get("undo"), new TextHistoryAction(TextHistoryAction.UNDO), url + "text-undo.png"));
			
			/*
			 * Creates the redo menuItem  
			 */
                this.add(editor.bind(mxResources.get("redo"), new TextHistoryAction(TextHistoryAction.REDO), url + "text-redo.png"));

                this.addSeparator();
			
			/*
			 * Creates the match menuItem  
			 */
                this.add(editor.bind(mxResources.get("match"), new MatchAction(), url + "text-match.gif"));

                this.addSeparator();
			
			/*
			 * Creates the ShowThisTags menuItem  
			 */
                this.add(editor.bind(mxResources.get("showThisTags"), new ShowThisTagsAction(), url + "text-showthistags.png"));

                this.addSeparator();
			
			/*
			 * Creates the RemoveOtherTags menuItem  
			 */
                this.add(editor.bind(mxResources.get("removeOtherTags"), new RemoveOtherTagsAction(), url + "text-removeothertags.png"));

                this.addSeparator();
			
			/*
			 * Creates the repaint menuItem  
			 */
                this.add(editor.bind(mxResources.get("repaint"), new RepaintAction(), url + "text-repaint.gif"));


                break;

            /**
             * graphPopupMenu
             */
            case GRAPHMENU:
			
			/*
			 * Creates the undo/redo menuItem 
			 */

                this.add(editor.bind(mxResources.get("undo"), new HistoryAction(HistoryAction.UNDO), url + "undo.gif"));
                this.add(editor.bind(mxResources.get("redo"), new HistoryAction(HistoryAction.REDO), url + "redo.gif"));

                this.addSeparator();

                this.add(editor.bind(mxResources.get("cut"), TransferHandler.getCutAction(), url + "cut.gif"));
                this.add(editor.bind(mxResources.get("copy"), TransferHandler.getCopyAction(), url + "copy.gif"));
                this.add(editor.bind(mxResources.get("paste"), TransferHandler.getPasteAction(), url + "paste.gif"));

                this.addSeparator();

                this.add(editor.bind(mxResources.get("selectAll"), mxGraphActions.getSelectAllAction(), url + "selectall.gif"));
                this.add(editor.bind(mxResources.get("selectNone"), mxGraphActions.getSelectNoneAction(), url + "selectnone.gif"));
                this.add(editor.bind(mxResources.get("delete"), mxGraphActions.getDeleteAction(), url + "delete.gif"));

                this.addSeparator();

                this.add(editor.bind(mxResources.get("edit"), mxGraphActions.getEditAction(), url + "editor.gif"));

                this.addSeparator();

                this.add(editor.bind(mxResources.get("graphToText"), new GraphToText(), url + "graphtotext.gif"));

                break;

        }

    }

}
