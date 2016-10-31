package com.ovu.ibeacon.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.ovu.ibeacon.model.IBeaconModel;

public class IBeaconModelView2 extends JPanel {

	Color modelColor = Color.WHITE;
	TraiangleView traiView;
//	private List<IBeaconModel> ibeaconList = null;
	private JLabel distanceLabel = null;

	public IBeaconModelView2(String uuid) {
//		ibeaconList = new ArrayList<IBeaconModel>();
		setLayout(null);
		setSize(200, 400);
		// ���������ͼƬ
		traiView = new TraiangleView();
		traiView.setBounds(0, 0, traiView.getWidth(), traiView.getHeight());
		add(traiView);
		// ������ֱ�ǩ
		JLabel nameLabel = new JLabel("SN1", JLabel.CENTER);
		nameLabel.setBounds(0, traiView.getHeight() - 65, traiView.getWidth(),
				20);
		nameLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		add(nameLabel);
		
		setBackground(Color.RED);
		
		// ��Ӿ����ǩ
		distanceLabel = new JLabel();
		// �����ı����Ķ���
		distanceLabel.setHorizontalAlignment(JLabel.CENTER);
		// ����λ��
		distanceLabel.setBounds(0, traiView.getHeight(), traiView.getWidth(),
				120);
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
		//��ʾ�����ǩ
		//showDistanceLabel();
	}

	
	/**
	 * ��ʾ�����ǩ�����ibeaconListΪ������ʾ����Ͷ�����������ʾibeaconList�����Ӧ��Ϣ���򿪶���
	 * @param ibeaconList
	 */
	public void showDistanceLabel(List<IBeaconModel> ibeaconList) {
		if(null != ibeaconList && ibeaconList.size() != 0){
			StringBuilder s = new StringBuilder();
			s.append("<html>");
			for (IBeaconModel iBeaconModel : ibeaconList) {
				s.append(iBeaconModel.getUuid() + " ");
				s.append("Distance: " + String.format("%.2f", iBeaconModel.getDistance()) + "<br> ");
			}
			s.append("</html>");
			distanceLabel.setText(s.toString());
			distanceLabel.setVisible(true);
			setTrigger();
		}else{
			distanceLabel.setVisible(false);
			clearTrigger();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 400);
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
	
	public static void main(String[] args) {
		SceneBackgroudFrame sbg = new SceneBackgroudFrame();
		IBeaconModelView2 b2 = new IBeaconModelView2("momo");
		sbg.add(b2);
	}

}
