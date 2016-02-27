package org.treeEditor.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

import com.mxgraph.util.mxResources;

public class EditorTextScrollPane extends JScrollPane {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private static JPopupMenu popupMenu;

    /**
     *
     */
    private static TextPane chTextPane = new TextPane();

    /**
     *
     */
    private static TextPane enTextPane = new TextPane();


    /**
     * @param editor
     */
    public EditorTextScrollPane(BasicGraphEditor editor) {

        //弹出式菜单
        popupMenu = new EditorPopupMenu(editor, EditorPopupMenu.TEXTMENU);

        //JScrollPane设置
        setBorder(BorderFactory.createLineBorder(new Color(0xdf, 0xdd, 0xdb), 7));
        setViewportView(chTextPane);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        setPreferredSize(new Dimension(0, 200));
    }

    /**
     * @return
     */
    public TextPane getChTextPane() {
        return chTextPane;
    }

    /**
     * @return
     */
    public TextPane getEnTextPane() {
        return enTextPane;
    }


    /**
     * @author boy
     */
    public static class TextPane extends JTextPane {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */
        protected TextUndoManager textUndoManager = new TextUndoManager();


        public TextPane() {
            //文本框设置
            this.setBorder(new EmptyBorder(10, 15, 20, 15));
            //this.setSize(new Dimension(122, 200));


            this.setEditable(false); //设置不可编辑
            this.getCaret().setVisible(true); //设置光标可见
            this.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
            this.setBackground(new Color(240, 240, 240));

            // 增加撤销重做管理器
            this.getDocument().addUndoableEditListener(textUndoManager);

            // 增加鼠标点击事件
            this.addMouseListener(new TextPaneListener(this));
        }

        /**
         * @return
         */
        public TextUndoManager getTextUndoManager() {
            return textUndoManager;
        }


    }

    /**
     * @author boy
     */
    public static class TextPaneListener extends MouseAdapter {

        private JTextPane textPane;

        public TextPaneListener(JTextPane textPane) {
            this.textPane = textPane;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            /*
			 * 自动选择标签
			 */
            try {
                //显示当前光标
                textPane.getCaret().setVisible(true);

                //获取当前光标的位置
                int caretPosition = textPane.getCaretPosition();

                //获取当前文本的长度
                int textLength = textPane.getText().length();

                //自动匹配选择标签
                int startPosition = 0;
                int endPosition = textLength;

                String startString = textPane.getText(startPosition, caretPosition - startPosition);
                String endString = textPane.getText(caretPosition, endPosition - caretPosition);

                //根据点击次数进行不同的选中
                switch (e.getClickCount()) {

                    case 1: {//单击选中

                        int startIndex =
                                startString.lastIndexOf("<") > startString.lastIndexOf(">") ?
                                        startPosition + (startString.lastIndexOf(" ") > startString.lastIndexOf("<") ?
                                                startString.lastIndexOf(" ") : startString.lastIndexOf("<")) : -1;
                        if (startIndex != -1) {
                            int endIndex = caretPosition +
                                    (endString.indexOf(" ") < endString.indexOf(">") && endString.indexOf(" ") != -1 ?
                                            endString.indexOf(" ") : endString.indexOf(">"));
                            textPane.select(startIndex + 1, endIndex);
                        }

                    }
                    break;


                    case 2: {//双击选中
                        int startIndex =
                                startString.lastIndexOf("<") > startString.lastIndexOf(">") ?
                                        startPosition + startString.lastIndexOf("<") : -1;
                        if (startIndex != -1) {
                            int endIndex = caretPosition + endString.indexOf(">");

                            textPane.select(startIndex, endIndex + 1);
                        }

                    }
                    break;

                }

            } catch (BadLocationException e1) {
                //e1.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        e1.toString(), mxResources.get("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
			
			/*
			 * 右键弹出提示框
			 */
            if (e.getButton() == MouseEvent.BUTTON3) {
                popupMenu.show(textPane, e.getX(), e.getY());
            }

        }

    }

    /**
     * @author boy
     */
    public static class TextUndoManager extends UndoManager {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public final int ADDTAG = 2;

        public final int REPLACETAG = 4;

        public final int DELECTTAG = 2;

        public final int ADDATTR = 1;

        public final int REPLACEATTR = 2;

        public final int DELECTATTR = 1;

        public final int SETTEXT = 2;

        public final int ADDBLANK = 1;

        public final int DELECTBLANK = 1;

        int stepSumNumber;

        int curStepNumber;

        ArrayList<Integer> stack = new ArrayList<Integer>();

        public TextUndoManager() {
            this.stepSumNumber = 0;

            this.curStepNumber = -1;

            stack.clear();
        }

        public void pushOperate(int operateNumber) {

            for (int i = curStepNumber + 1; i < stepSumNumber; i++) {
                stack.remove(i);
            }

            this.curStepNumber++;
            this.stepSumNumber = this.curStepNumber + 1;

            stack.add(curStepNumber, operateNumber);

        }

        public boolean canUndo() {
            if (this.curStepNumber >= 0) {
                return true;
            }
            return false;
        }

        public boolean canRedo() {
            if (this.curStepNumber < this.stepSumNumber - 1) {
                return true;
            }
            return false;
        }

        public void undo() {
            int stepNum = stack.get(curStepNumber);

            this.curStepNumber--;

            for (int i = 0; i < stepNum; i++) {
                if (super.canUndo()) {
                    super.undo();
                }
            }

        }

        public void redo() {
            this.curStepNumber++;

            int stepNum = stack.get(curStepNumber);

            for (int i = 0; i < stepNum; i++) {
                if (super.canRedo()) {
                    super.redo();
                    ;
                }
            }
        }

    }

}
