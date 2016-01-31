package org.treeEditor.action;

import com.mxgraph.swing.mxGraphComponent;

import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;

/**
 * Created by boy on 16-1-31.
 */
public class PageSetupAction extends BasicAbstractAction {
    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof mxGraphComponent) {
            mxGraphComponent graphComponent = (mxGraphComponent) e
                    .getSource();
            PrinterJob pj = PrinterJob.getPrinterJob();
            PageFormat format = pj.pageDialog(graphComponent
                    .getPageFormat());

            if (format != null) {
                graphComponent.setPageFormat(format);
                graphComponent.zoomAndCenter();
            }
        }
    }
}
