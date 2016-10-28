package com.ovu.ibeacon.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ovu.ibeacon.model.IBeaconModel;

public class IBeaconModelView extends JPanel {

	Color modelColor = Color.WHITE;
	TraiangleView traiView;
	private IBeaconModel ibeaconmodel = null;
	private JLabel distanceLabel = null;

	public IBeaconModelView(String modelName) {
		ibeaconmodel = new IBeaconModel();
		setIBeaconModelName(modelName);
		setLayout(null);
		setSize(200, 220);
		// ���������ͼƬ
		traiView = new TraiangleView();
		traiView.setBounds(0, 0, traiView.getWidth(), traiView.getHeight());
		add(traiView);
		// ������ֱ�ǩ
		JLabel nameLabel = new JLabel(ibeaconmodel.getName(), JLabel.CENTER);
		nameLabel.setBounds(0, traiView.getHeight() - 65, traiView.getWidth(),
				20);
		nameLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		add(nameLabel);
		// ��Ӿ����ǩ
		distanceLabel = new JLabel();
		// �����ı����Ķ���
		distanceLabel.setHorizontalAlignment(JLabel.CENTER);
		// ����λ��
		distanceLabel.setBounds(0, traiView.getHeight(), traiView.getWidth(),
				20);
		// ��������
		distanceLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		// ���ñ�����ɫ
		distanceLabel.setBackground(Color.WHITE);
		// ���ñ߿��ɫ
		distanceLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// ��Ҫ������Ϊtrue��ɫ����������ʾ
		distanceLabel.setOpaque(true);
		// ��ʼ״̬����ʾ
		distanceLabel.setVisible(false);
		add(distanceLabel);
		// ����͸��
		setOpaque(false);
	}

	/**
	 * ���þ����־ֵ
	 * @param distance
	 */
	public void setDistanceLabel(double distance) {
		ibeaconmodel.setDistance(distance);
		distanceLabel.setText("Distance: "
				+ String.valueOf(ibeaconmodel.getDistance()));
	}

	/**
	 * ���þ����ǩ�Ƿ�ɼ���true�ɼ���false���ɼ�
	 * @param flag
	 */
	public void setDistanceLabelVisible(boolean flag) {
		distanceLabel.setVisible(flag);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	/**
	 * ���ø�IBeaconView���IBeacon�����name
	 * @param name
	 */
	public void setIBeaconModelName(String name) {
		ibeaconmodel.setName(name);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 220);
	}

	/**
	 * �򿪿���ʱ�Ķ���Ч��
	 */
	public void setTrigger() {
		traiView.setTriggerFlag(true);
	}

	/**
	 * �رտ���ʱ�Ķ���Ч��
	 */
	public void clearTrigger() {
		traiView.setTriggerFlag(false);
	}

}
