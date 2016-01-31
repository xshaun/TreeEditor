package org.treeEditor.ui;

import com.mxgraph.io.mxCodec;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import org.w3c.dom.Document;

import java.awt.*;

import static org.treeEditor.assist.LoadFile.loadResource;

/**
 * Created by boy on 16-1-31.
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

        // Sets switches typically used in an ui
        //setPageVisible(true); // 设置画板页边是否可见
        setGridVisible(true);
        setToolTips(true);
        getConnectionHandler().setCreateTarget(true);

        // Loads the defalt stylesheet from an external file
        mxCodec codec = new mxCodec();
//        Document doc = mxUtils.loadDocument(loadResource("default-style.xml").getPath().toString());
        Document doc = mxUtils.loadDocument(loadResource("default-style.xml").toString());
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
    public BasicGraphEditor.CustomGraph getGraph() {
        return (BasicGraphEditor.CustomGraph) super.getGraph();
    }
}