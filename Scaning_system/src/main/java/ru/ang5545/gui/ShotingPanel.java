package ru.ang5545.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ru.ang5545.image_processing.ImageHelper;

public class ShotingPanel extends JPanel {

	private JLabel image;
	
	private int imgWidth;
	private int imgHeight;
	private int panWidth;
	private int panHeight;
	
	public ShotingPanel(String panelName, int width, int height) {
		super();
		this.panWidth = width;
		this.panHeight = height;
		this.imgWidth = width -10;
		this.imgHeight = height - 45;
		this.setBorder(BorderFactory.createTitledBorder(panelName));
		this.setDimension(panWidth, panHeight);
		this.setLayout(new BorderLayout());
		this.image = new JLabel();
		this.setImage(  ImageHelper.getEmptyImage(imgWidth, imgHeight) );
		this.add(image, BorderLayout.PAGE_START);
	}

	public void setDimension(int width, int height){
		Dimension dim = new Dimension(width, height);
		this.setMaximumSize(dim);
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		this.setSize(dim);
	}
	
	public void setImage(BufferedImage img){
		ImageIcon icon = new ImageIcon( resize(img) );
		image.setIcon(icon);
	}
	
	public BufferedImage resize(BufferedImage image) {
	    BufferedImage bi = new BufferedImage(imgWidth, imgHeight, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(image, 0, 0, imgWidth, imgHeight, null);
	    g2d.dispose();
	    return bi;
	}
}
