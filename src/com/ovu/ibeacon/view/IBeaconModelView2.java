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

/**
 * �Խڵ�SNΪ���յ㲻������ÿ��Beacon����ڽڵ�ľ���
 * @author zz
 *
 */
public class IBeaconModelView2 extends JPanel {
	
	private boolean timeOut = false;
	private String lastUpdateTime = null;
	private String name;

	Color modelColor = Color.WHITE;
	TraiangleView traiView;
//	private List<IBeaconModel> ibeaconList = null;
	private JLabel distanceLabel = null;

	public IBeaconModelView2(String uuid) {
//		ibeaconList = new ArrayList<IBeaconModel>();
		this.name = uuid;
		setLayout(null);
		setSize(200, 600);
		// ���������ͼƬ
		traiView = new TraiangleView();
		traiView.setBounds(0, 0, traiView.getWidth(), traiView.getHeight());
		add(traiView);
		// ������ֱ�ǩ
		JLabel nameLabel = new JLabel(getName(), JLabel.CENTER);
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
//		distanceLabel.setBounds(0, traiView.getHeight(), traiView.getWidth(),
//				180);
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
		if(!timeOut){
			if(null != ibeaconList && ibeaconList.size() != 0){
				distanceLabel.setVisible(true);
				distanceLabel.setBounds(0, traiView.getHeight()-18, traiView.getWidth(), ibeaconList.size() * 25);
				StringBuilder s = new StringBuilder();
				s.append("<html>");
				for (IBeaconModel iBeaconModel : ibeaconList) {
					s.append(iBeaconModel.getUuid() + " ");
					s.append("Distance: " + String.format("%.2f", iBeaconModel.getDistance()) + "<br> ");
				}
				s.append("</html>");
				distanceLabel.setText(s.toString());
				setTrigger();
			}else{
				distanceLabel.setVisible(false);
				clearTrigger();
			}
		}else{
			distanceLabel.setVisible(true);
			distanceLabel.setBounds(0, traiView.getHeight()-18, traiView.getWidth(), 70);
			StringBuilder s = new StringBuilder();
			s.append("<html>");
			s.append("�ڵ㳬ʱ<br> ");
			s.append("�ϴθ���ʱ��: <br>" + formatTime(lastUpdateTime).substring(4));
			s.append("</html>");
			distanceLabel.setText(s.toString());
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
	
	/**
	 * ���ýڵ㳬ʱ
	 * @param flag
	 * @param time
	 */
	public void setTimeOut(boolean flag, String time){
		timeOut = flag;
		this.lastUpdateTime = time;
	}
	
	/**
	 * �Է�����������ʱ�������ʽ���Ա����
	 * @param time
	 * @return
	 */
	public String formatTime(String time){
		String result = time.substring(0,6)+"��"+time.substring(6,8)+"��"+time.substring(8,10)+"ʱ"+time.substring(10,12)+"��"+time.substring(12,14)+"��";
		return result;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

}
