package org.treeEditor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import com.mxgraph.util.mxResources;

public class EditorAboutFrame extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public EditorAboutFrame(Frame owner)
	{
		super(owner);
		setTitle(mxResources.get("aboutThisEditor"));
		setLayout(new BorderLayout());

		// Creates the gradient panel
		JPanel panel = new JPanel(new BorderLayout())
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);

				// Paint gradient background
				Graphics2D g2d = (Graphics2D) g;
				g2d.setPaint(new GradientPaint(0, 0, Color.WHITE, getWidth(),
						0, getBackground()));
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}

		};

		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(0, 0, 1, 0, Color.GRAY), BorderFactory
				.createEmptyBorder(8, 8, 12, 8)));

		// Adds title
		JLabel titleLabel = new JLabel(mxResources.get("aboutThisEditor"));
		titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
		titleLabel.setOpaque(false);
		panel.add(titleLabel, BorderLayout.NORTH);

		// Adds optional subtitle
		JLabel subtitleLabel = new JLabel(
				"<html>此工具是基于JGraph进行开发<br />具有语法树的编辑、对比等功能</html>");
		subtitleLabel.setBorder(BorderFactory.createEmptyBorder(4, 18, 0, 0));
		subtitleLabel.setOpaque(false);
		panel.add(subtitleLabel, BorderLayout.CENTER);

		getContentPane().add(panel, BorderLayout.NORTH);

		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		content.add(new JLabel("版本号： VS20141106"));
		content.add(new JLabel("Copyright (C) 2014 郑州大学 孙晓阳"));
		content.add(new JLabel("版权所有"));
		content.add(new JLabel(" "));
		
		content.add(new JLabel("技术支持： 郑州大学自然语言处理实验室 梁军、冯晓波"));
		content.add(new JLabel("鸣谢：JGraph  Eclipse  Ubuntu"));
		content.add(new JLabel(" "));
		try
		{
			content.add(new JLabel("操作系统名称: "
					+ System.getProperty("os.name")));
			content.add(new JLabel("操作系统版本: "
					+ System.getProperty("os.version")));
			content.add(new JLabel(" "));

			content.add(new JLabel("Java 供应商: "
					+ System.getProperty("java.vendor", "undefined")));
			content.add(new JLabel("Java 版本: "
					+ System.getProperty("java.version", "undefined")));
			content.add(new JLabel(" "));

			content.add(new JLabel("全部内存: "
					+ Runtime.getRuntime().totalMemory()));
			content.add(new JLabel("剩余内存: "
					+ Runtime.getRuntime().freeMemory()));
		}
		catch (Exception e)
		{
			// ignore
		}
		
		getContentPane().add(content, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(1, 0, 0, 0, Color.GRAY), BorderFactory
				.createEmptyBorder(16, 8, 8, 8)));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// Adds OK button to close window
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});

		buttonPanel.add(closeButton);

		// Sets default button for enter key
		getRootPane().setDefaultButton(closeButton);
		
		setResizable(false);
		setSize(400, 480);
	}

	/**
	 * Overrides {@link JDialog#createRootPane()} to return a root pane that
	 * hides the window when the user presses the ESCAPE key.O
	 */
	protected JRootPane createRootPane()
	{
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		JRootPane rootPane = new JRootPane();
		rootPane.registerKeyboardAction(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{
				setVisible(false);
			}
		}, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		return rootPane;
	}

}
