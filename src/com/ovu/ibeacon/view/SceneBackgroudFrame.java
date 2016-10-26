package com.ovu.ibeacon.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public class SceneBackgroudFrame extends JFrame{
	
	private int width;
	private int height; 
	
	/**
	 * �޲ι��캯����Ĭ�Ͽ��Ϊ��ʾ���Ŀ��
	 */
	public SceneBackgroudFrame(){
		//�õ���ʾ���Ŀ��
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		width = screenSize.width;
		height = screenSize.height;
		initFrame();
	}
	
	/**
	 * ���ι��캯�����������ô��ڵĿ��
	 * @param width
	 * @param height
	 */
	public SceneBackgroudFrame(int width, int height){
		this.width = width;
		this.height = height;
		initFrame();
	}
	
	/**
	 * Frame��ʼ������
	 */
	private void initFrame(){
		setSize(width,height);
		setTitle("IbeaconDemo1");
		setBackground(Color.BLUE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		isResizable();
	}

}
