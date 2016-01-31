package org.treeEditor.ui;

import javax.swing.JSplitPane;

public class EditorSplitPane extends JSplitPane {

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

    public EditorSplitPane(final BasicGraphEditor editor) {
//        //获取显示面板
//        chGraphComponent = editor.getChGraphComponent();
//        enGraphComponent = editor.getEnGraphComponent();
//
//        //设置显示面板的位置
//        this.setLeftComponent(chGraphComponent);
//        this.setRightComponent(enGraphComponent);
//
//        //设置此时状态当前面板为EnGraphComponent
//        editor.setCurGraphComponent(enGraphComponent);

//		//设置监听事件
//		chGraphComponent.addFocusListener(new FocusListener(){
//			@Override
//			public void focusLost(FocusEvent e) {
//				//
//			}
//			
//			@Override
//			public void focusGained(FocusEvent e) {
//				//当获取焦点时设置当前面板
//				curGraphComponent = chGraphComponent ;
//			}
//		});
//		
//		//设置监听事件
//		enGraphComponent.addFocusListener(new FocusListener(){
//			@Override
//			public void focusLost(FocusEvent e) {
//				//
//			}
//			
//			@Override
//			public void focusGained(FocusEvent e) {
//				//当获取焦点时设置当前面板
//				curGraphComponent = enGraphComponent ;
//			}
//		});

    }

}
