package org.treeEditor.action;

import org.treeEditor.ui.EditorBrowserLaunch;

import java.awt.event.ActionEvent;

/**
 * Created by boy on 16-1-31.
 */
public class HelpOnlineAction extends BasicAbstractAction {


    public static final String JGRAPH = "http://www.mxgraph.cn/jgraph.php";

    /**
     *
     */
    protected String url;

    /**
     * @param url
     */
    public HelpOnlineAction(String url) {
        this.url = url;
    }

    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        EditorBrowserLaunch.openURL(this.url);
    }
}
