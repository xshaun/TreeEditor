package org.treeEditor.action;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxResources;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class ScaleAction extends BasicAbstractAction {

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
