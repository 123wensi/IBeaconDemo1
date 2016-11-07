package com.ovu.ibeacon.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ovu.ibeacon.utils.Utils;

public class TraiangleView extends JPanel {
		
	private static double theta = 0;
	private double radius = 0;
	private boolean triggerFlag = false;
	private int igwidth = 0;

	public TraiangleView() {
		setSize(Utils.SN_WIDTH,Utils.SN_WIDTH);
//		setBackground(Color.BLUE);
		theta = Math.toRadians(theta);
		createAnim();
		setOpaque(false);
	}
	
	public void createAnim(){
		Timer timer = new Timer();
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				setAngle(theta);
				theta = theta + Math.PI / (180 * 2);
				radius = radius + 0.4;
				if(radius > 50)
					radius = igwidth / 2;
			}		
		};
		timer.schedule(task, 0, 10);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		// �������
		RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHints(renderingHints);
		
		//������ת����
		AffineTransform affineTransform=new AffineTransform();
		//��ת�Ƕ�theta, ��ת����(100,100)
		affineTransform.setToRotation(theta,Utils.SN_WIDTH/2,Utils.SN_WIDTH/2);
		g2d.transform(affineTransform);

		if(!triggerFlag){
			//��ȡͼƬ
			ImageIcon imageIcon = new ImageIcon("images/triangle_small.png");
			Image image = imageIcon.getImage();
			radius = imageIcon.getIconWidth() / 2;
			//����ͼƬ�����Ϣ
			igwidth = imageIcon.getIconWidth();
			g2d.drawImage(image, Utils.SN_WIDTH/2-imageIcon.getIconWidth()/2, Utils.SN_WIDTH/2-imageIcon.getIconHeight()/2, null);
		}else{
			//��ȡͼƬ
			ImageIcon imageIcon = new ImageIcon("images/triangle2_small.png");
			Image image = imageIcon.getImage();
			//����ͼƬ�����Ϣ
			igwidth = imageIcon.getIconWidth();
			g2d.drawImage(image, Utils.SN_WIDTH/2-imageIcon.getIconWidth()/2, Utils.SN_WIDTH/2-imageIcon.getIconHeight()/2, null);
			//����Բ
			Rectangle2D rect = new Rectangle2D.Double(Utils.SN_WIDTH/2-radius, Utils.SN_WIDTH/2-radius, radius * 2, radius * 2);
			Ellipse2D ellipse = new Ellipse2D.Double();
			ellipse.setFrame(rect);
			g2d.setStroke(new BasicStroke(5f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
			g2d.setColor(Color.RED);
			g2d.draw(ellipse);
		}
	
	}
	
	public void setAngle(double theta){
		this.theta = theta;
		repaint();
	}

//	public static void main(String[] args) {
//		JFrame jf = new JFrame("������ת��ť");
//		jf.setSize(800, 800);
//		TraiangleView btn = new TraiangleView();
//		btn.createAnim();
//		btn.setBounds(100,100,200,200);
//		jf.setLayout(null);
//		jf.add(btn);
//		jf.setVisible(true);
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
	
	public boolean isTriggerFlag() {
		return triggerFlag;
	}

	public void setTriggerFlag(boolean triggerFlag) {
		this.triggerFlag = triggerFlag;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Utils.SN_WIDTH, Utils.SN_WIDTH);
	}
	
}
