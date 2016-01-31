package org.treeEditor.ui;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.util.mxGraphActions;
import org.treeEditor.action.HistoryAction;
import org.treeEditor.action.OpenAction;
import org.treeEditor.action.SaveAction;


/**
 * @author Administrator
 * 
 */
public class EditorKeyboardHandler  extends mxKeyboardHandler
{

	/**
	 * 
	 * @param graphComponent
	 */
	public EditorKeyboardHandler(mxGraphComponent graphComponent)
	{
		super(graphComponent);
	}

	/**
	 * Return JTree's input map.
	 */
	protected InputMap getInputMap(int condition)
	{
		InputMap map = super.getInputMap(condition);

		if (condition == JComponent.WHEN_FOCUSED && map != null)
		{
			map.put(KeyStroke.getKeyStroke("control S"), "save");
			map.put(KeyStroke.getKeyStroke("control shift S"), "saveAs");
//			map.put(KeyStroke.getKeyStroke("control N"), "new");
			map.put(KeyStroke.getKeyStroke("control O"), "open");

			map.put(KeyStroke.getKeyStroke("control Z"), "undo");
			map.put(KeyStroke.getKeyStroke("control Y"), "redo");
//			map.put(KeyStroke.getKeyStroke("control shift V"), "selectVertices");
//			map.put(KeyStroke.getKeyStroke("control shift E"), "selectEdges");
		

			map.put(KeyStroke.getKeyStroke("delect"), "delect");
			
		}

		return map;
	}

	/**
	 * Return the mapping between JTree's input map and JGraph's actions.
	 */
	protected ActionMap createActionMap()
	{
		ActionMap map = super.createActionMap();

		map.put("save", new SaveAction(false));
		map.put("saveAs", new SaveAction(true));
//		map.put("new", new EditorActions.NewAction());
		map.put("open", new OpenAction());
		map.put("undo", new HistoryAction(true));
		map.put("redo", new HistoryAction(false));
		
//		map.put("selectVertices", mxGraphActions.getSelectVerticesAction());
//		map.put("selectEdges", mxGraphActions.getSelectEdgesAction());

		map.put("delect", mxGraphActions.getDeleteAction());
		
		
		return map;
	}


}
