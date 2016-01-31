package org.treeEditor.ui;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditorTabbedPane extends JTabbedPane {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 定义面板
     */

    private CustomGraphComponent currentGraphComponent;

//    //原树结构显示面板
//    private CustomGraphComponent chGraphComponent;
//
//    //修改树显示面板
//    private CustomGraphComponent enGraphComponent;

    public EditorTabbedPane(final BasicGraphEditor editor) {

        currentGraphComponent = editor.getcurrentGraphComponent();

//        //获取显示面板
//        chGraphComponent = editor.getChGraphComponent();
//        enGraphComponent = editor.getEnGraphComponent();

        this.add(currentGraphComponent, 0);
        this.addTab("当前面板", currentGraphComponent);

//        //加入选项面板
//        this.add(chGraphComponent, 0);
//        this.addTab("中文", chGraphComponent);
//
//        this.add(enGraphComponent, 1);
//        this.addTab("英文", enGraphComponent);

        editor.setcurrentGraphComponent(currentGraphComponent);

//        //设置初始显示面板为可修改面板
//        this.setSelectedIndex(0);
//        editor.setCurGraphComponent(chGraphComponent);

//        this.addChangeListener(e -> {
//
//            if (getSelectedIndex() == 0) {
//                editor.setCurGraphComponent(chGraphComponent);
//                editor.textScrollPane.setViewportView(editor.textScrollPane.getChTextPane());
//            } else if (getSelectedIndex() == 1) {
//                editor.setCurGraphComponent(enGraphComponent);
//                editor.textScrollPane.setViewportView(editor.textScrollPane.getEnTextPane());
//            }
//        });

    }
}
