package org.treeEditor.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditorWaitBar extends JFrame implements ChangeListener, ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final String CANCEL = "Cancel";

    public final String OK = "OK";

    JButton button;
    //	JProgressBar bar;
    JLabel show;
    JPanel pane;
    //	Timer timer;
    boolean finish = false;

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new EditorWaitBar("uninstall").setVisible(true);
//	}

    public EditorWaitBar(String title) {
        super(title);
        this.init();
        this.pack();
        this.setVisible(true);
    }

    public void init() {
        //setBounds(300, 100, 500, 100);
        this.setLocation(500, 300);
        pane = new JPanel();
        pane.setLayout(new BorderLayout());
        pane.setBorder(new EmptyBorder(10, 15, 20, 15));
        getContentPane().add(pane);

        button = new JButton(this.CANCEL);
        button.setBorder(new EmptyBorder(2, 2, 2, 2));
        button.addActionListener(this);

//		bar = new JProgressBar();
//		bar.setMinimum(0);
//		bar.setMaximum(100);
//		bar.setValue(0);
//		bar.setStringPainted(false);
//		bar.setBorderPainted(true);
//		bar.addChangeListener(this);
//		bar.setPreferredSize(new Dimension(500, 30));

        show = new JLabel("Please wait for a moment", JLabel.CENTER);
        show.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        show.setBorder(new EmptyBorder(10, 10, 10, 10));

//		pane.add(bar, BorderLayout.NORTH);
        pane.add(button, BorderLayout.CENTER);
        pane.add(show, BorderLayout.NORTH);

//		timer = new Timer(60, this);
//		timer.start();
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        // TODO Auto-generated method stub
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == button) {
            if (button.getText() == this.CANCEL) {
                this.dispose();
            } else if (button.getText() == this.OK) {
                this.dispose();
            }
        }
//		if(e.getSource() == timer) {
//			int value = bar.getValue();
//			if(value < 100) {
//				value++;
//				bar.setValue(value);
//			}else{
//				bar.setValue(0);
//			}
//			
//			if(finish){
//				show.setText("Finished!");
//				timer.stop();
//				bar.setValue(100);
//			}
//		}
    }
}

