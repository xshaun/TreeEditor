package cn.edu.zzu.nlp.editor;

import cn.edu.zzu.nlp.editor.BasicGraphEditor.CustomGraphComponent;

import javax.swing.*;
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
    //原树结构显示面板
    private CustomGraphComponent chGraphComponent;
    //修改树显示面板
    private CustomGraphComponent enGraphComponent;

    public EditorTabbedPane(final BasicGraphEditor editor) {
        //获取显示面板
        chGraphComponent = editor.getChGraphComponent();
        enGraphComponent = editor.getEnGraphComponent();

        //加入选项面板
        this.add(chGraphComponent, 0);
        this.addTab("中文", chGraphComponent);

        this.add(enGraphComponent, 1);
        this.addTab("英文", enGraphComponent);

        //设置初始显示面板为可修改面板
        this.setSelectedIndex(0);
        editor.setCurGraphComponent(chGraphComponent);

        this.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                if (getSelectedIndex() == 0) {
                    editor.setCurGraphComponent(chGraphComponent);
                    editor.textScrollPane.setViewportView(editor.textScrollPane.getChTextPane());
                } else if (getSelectedIndex() == 1) {
                    editor.setCurGraphComponent(enGraphComponent);
                    editor.textScrollPane.setViewportView(editor.textScrollPane.getEnTextPane());
                }
            }
        });

    }
}
