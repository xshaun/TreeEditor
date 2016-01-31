package org.treeEditor.ui;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;

import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;
import org.treeEditor.action.*;

import static org.treeEditor.assist.LoadFile.loadImage;

public class EditorPopupMenu extends JPopupMenu {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private final String url =("/cn/edu/zzu/nlp/images/");

	/**
	 *
	 */
	public static final int TEXTMENU = 1 ;

	public static final int GRAPHMENU = 2 ;


	public EditorPopupMenu(final BasicGraphEditor editor, int type){

		JMenu subMenu;

		switch(type){
			/**
			 * textPopupMenu
			 */
			case TEXTMENU:

//          /*
//           * Creates the text menu
//           */
//          subMenu = (JMenu) this.add(new JMenu(mxResources.get("text")));
//          subMenu.setIcon(new ImageIcon(EditorPopupMenu.class.loadResource("text-text.png")));
//
//          subMenu.add(ui.bind(mxResources.get("addText"), new TextAddAction(TextAddAction.TEXT), "text-addtext.png"));
//
//          subMenu.add(ui.bind(mxResources.get("replaceText"), new TextReplaceAction(TextReplaceAction.TEXT), "text-replacetext.png"));
//
//          this.addSeparator();

            /*
             * Creates the add menu
             */
				subMenu = (JMenu) this.add(new JMenu(mxResources.get("add")));
				subMenu.setIcon(new ImageIcon(loadImage("add.png")));

				subMenu.add(editor.bind(mxResources.get("addTag"), new TextAddAction(TextAddAction.TAG), "text-addtag.png"));

				subMenu.add(editor.bind(mxResources.get("addAttr"), new TextAddAction(TextAddAction.ATTR), "text-addattr.png"));

				subMenu.add(editor.bind(mxResources.get("addBlank"), new TextAddAction(TextAddAction.BLANK), "text-addblank.png"));


				this.addSeparator();

            /*
             * Creates the replace menu
             */
				subMenu = (JMenu) this.add(new JMenu(mxResources.get("replace")));

				subMenu.setIcon(new ImageIcon(loadImage("replace.png")));

				subMenu.add(editor.bind(mxResources.get("replaceTag"), new TextReplaceAction(TextReplaceAction.TAG), "text-replacetag.png"));

				subMenu.add(editor.bind(mxResources.get("replaceAttr"), new TextReplaceAction(TextReplaceAction.ATTR), "text-replaceattr.png"));

				this.addSeparator();

            /*
             * Creates the delete menuItem
             */
				this.add(editor.bind(mxResources.get("delete"), new TextDeleteAction(), "text-delete.png"));

				this.addSeparator();

            /*
             * Creates the undo menuItem
             */
				this.add(editor.bind(mxResources.get("undo"), new TextHistoryAction(TextHistoryAction.UNDO), "text-undo.png"));

            /*
             * Creates the redo menuItem
             */
				this.add(editor.bind(mxResources.get("redo"), new TextHistoryAction(TextHistoryAction.REDO), "text-redo.png"));

				this.addSeparator();

            /*
             * Creates the match menuItem
             */
				this.add(editor.bind(mxResources.get("match"), new MatchAction(), "text-match.gif"));

				this.addSeparator();

            /*
             * Creates the ShowThisTags menuItem
             */
				this.add(editor.bind(mxResources.get("showThisTags"), new ShowThisTagsAction(), "text-showthistags.png"));

				this.addSeparator();

            /*
             * Creates the RemoveOtherTags menuItem
             */
				this.add(editor.bind(mxResources.get("removeOtherTags"), new RemoveOtherTagsAction(), "text-removeothertags.png"));

				this.addSeparator();

            /*
             * Creates the repaint menuItem
             */
				this.add(editor.bind(mxResources.get("repaint"), new RepaintAction(), "text-repaint.gif"));


				break;

			/**
			 * graphPopupMenu
			 */
			case GRAPHMENU:

            /*
             * Creates the undo/redo menuItem
             */

				this.add(editor.bind(mxResources.get("undo"), new HistoryAction(HistoryAction.UNDO), "undo.gif"));
				this.add(editor.bind(mxResources.get("redo"), new HistoryAction(HistoryAction.REDO), "redo.gif"));

				this.addSeparator();

				this.add(editor.bind(mxResources.get("cut"), TransferHandler.getCutAction(), "cut.gif"));
				this.add(editor.bind(mxResources.get("copy"), TransferHandler.getCopyAction(), "copy.gif"));
				this.add(editor.bind(mxResources.get("paste"), TransferHandler.getPasteAction(), "paste.gif"));

				this.addSeparator();

				this.add(editor.bind(mxResources.get("selectAll"), mxGraphActions.getSelectAllAction(), "selectall.gif"));
				this.add(editor.bind(mxResources.get("selectNone"), mxGraphActions.getSelectNoneAction(),  "selectnone.gif"));
				this.add(editor.bind(mxResources.get("delete"), mxGraphActions.getDeleteAction(), "delete.gif"));

				this.addSeparator();

				this.add(editor.bind(mxResources.get("edit"), mxGraphActions.getEditAction(), "ui.gif"));

				this.addSeparator();

				this.add(editor.bind(mxResources.get("graphToText"), new GraphToText(), "graphtotext.gif"));

				break;

		}

	}

}
