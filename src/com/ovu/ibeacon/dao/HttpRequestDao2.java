package com.ovu.ibeacon.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ovu.ibeacon.model.IBeaconModel;
import com.ovu.ibeacon.utils.HttpRequest;
import com.ovu.ibeacon.utils.Utils;

/**
 * @author zz 2016/10/31
 */
public class HttpRequestDao2 {

	private boolean timeOutFlag = false;
	private List<IBeaconModel> iBeaconList = new ArrayList<IBeaconModel>();
	private List<IBeaconModel> transformList2 = new ArrayList<IBeaconModel>();
	
	private int sysTime;

	public interface DataOutOfRangeListener {
		public void getClose(List<IBeaconModel> ib);

		public void getFar();

		public void timeOut(boolean flag, String lastUpdateTime);
	}

	private DataOutOfRangeListener outOfRangeListener;

	public void setDataOutOfRangeListener(
			DataOutOfRangeListener outOfRangeListener) {
		this.outOfRangeListener = outOfRangeListener;
	}

	public HttpRequestDao2(final String url, final String param) {
		// ����ʱ���̣߳����������������
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				String data = null;
				HttpRequest hr = new HttpRequest();
				String s = hr.sendGet(url, param);
				// System.out.println(s);
				// s = formatString(s);
				System.out.println("--------------��ʼ��ӡһ������-----------------");
				System.out.println(s);
				try {
					JSONObject jsonObj = JSONObject.fromObject(s);
					// ��ȡϵͳ��ǰʱ��
					Calendar c = Calendar.getInstance();
					int day=c.get(Calendar.DATE);
					int hour = c.get(Calendar.HOUR_OF_DAY);
					int minute = c.get(Calendar.MINUTE);
					int seconds = c.get(Calendar.SECOND);
					// ȡ��data����
					data = jsonObj.getString("data");
					// ȡ���ϴθ�������ʱ��
					String last_update_time = jsonObj
							.getString("last_update_time");
					//����ȡ�õ�ϵͳʱ�䣬��λs
					sysTime = hour * 60 * 60 + minute * 60 + seconds;
					//�����ȡ�Ľڵ�����ʱ�䣬��λs
					int readTime = Integer.parseInt(last_update_time.substring(8, 10)) * 60 * 60 + Integer.parseInt(last_update_time.substring(10, 12)) * 60 + Integer.parseInt(last_update_time.substring(12, 14));
					if (timeOutFlag == false) {
						//ʱ������80s, �ж�Ϊ��ʱ
						if (day > Integer.parseInt(last_update_time.substring(6, 8)) ||(sysTime - readTime) >= 80) {
							timeOutFlag = true;
							System.out.println(jsonObj.getString("mac") + "��ʱ");
							outOfRangeListener.timeOut(timeOutFlag, last_update_time);
						}
					} else {
						//ʱ���С��80s������ͬһ��, δ��ʱ��������ʾ
						if (sysTime - readTime < 80 && day == Integer.parseInt(last_update_time.substring(6, 8))) {
							timeOutFlag = false;
							outOfRangeListener.timeOut(timeOutFlag, last_update_time);
						}
					}
					if(data.length()==0){
						if (outOfRangeListener != null)
							outOfRangeListener.getFar();
					}else if (data.equals("0000000000ff")) { // �жϽڵ��Ƿ��Ѿ���������ȷ��������
						System.out.println("�ڵ㻹û����");
					} else if (!timeOutFlag && data.length() != 0) {
						// dataCount��������
						String dataCount = data.substring(4, 6);
						// �õ���ȥ����ͷ����Ч�����ַ���dataWithoutHead
						String dataWithoutHead = data.substring(8,
								data.length());
						for (int i = 0; i < dataWithoutHead.length(); i = i + 10) {
							String data10 = dataWithoutHead
									.substring(i, i + 10);
							IBeaconModel ib = new IBeaconModel();
							ib.setUuid(data10.substring(0, 8));
							ib.setRssi(data10.substring(8));
							ib.setDistance(calDistance(Integer.parseInt(
									ib.getRssi(), 16)));
							ib.setUpdateTime(readTime);
							iBeaconList.add(ib);
						}
						List<IBeaconModel> transformList = new ArrayList<IBeaconModel>();
						for (IBeaconModel iBeaconModel : iBeaconList) {
							System.out.println("iBeaconList UUID=" + iBeaconModel.getUuid()
											+ " RSSI=" + iBeaconModel.getRssi()
											+ " Distance="
											+ iBeaconModel.getDistance());
							// ����iBeacon������ֵ
							if (iBeaconModel.getDistance() < 10 && Utils.hasThisUUID(iBeaconModel.getUuid())) {
								transformList.add(iBeaconModel);
								//�ж��Ƿ��޸�transformList2�е�����
								if(transformList2!=null && transformList2.size()!=0){
									if(transformList2.contains(iBeaconModel)){
										transformList2.set(transformList2.indexOf(iBeaconModel), iBeaconModel);
									}else{
										transformList2.add(iBeaconModel);
									}
								}else{
									transformList2.add(iBeaconModel);
								}
							}else{
								transformList2.remove(iBeaconModel);
							}
						}
						//��ӡtransformList2��Ϣ
						for (IBeaconModel iBeaconModel2 : transformList2) {
							System.out.println("iBeaconModel2 UUID=" + iBeaconModel2.getUuid()
									+ " RSSI=" + iBeaconModel2.getRssi()
									+ " Distance="
									+ iBeaconModel2.getDistance() + " updateTime=" + iBeaconModel2.getUpdateTime());
						}
						if (transformList != null && transformList.size() != 0) {
							if (outOfRangeListener != null)
								outOfRangeListener.getClose(transformList);
						} else {
							if (outOfRangeListener != null)
								outOfRangeListener.getFar();
						}
						iBeaconList.clear();
						System.out
								.println("--------------��ӡ�������ݽ���-----------------");
						System.out.println();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		// 3sִ��һ��
		timer.schedule(task, 0, 5000);
	}
	
	/**
	 * �ڵ�������������ζ��������ݵĽڵ���˵�
	 * @param list
	 */
	public void modelFilter(List<IBeaconModel> list){
		for (IBeaconModel iBeaconModel : list) {
			if(sysTime - iBeaconModel.getUpdateTime() < 60){
				list.remove(iBeaconModel);
			}
		}
	}

	/**
	 * ������뺯�� rrsi�ķ�Χ�� 21-53 �� 55~77 ֮����Ч
	 * 
	 * @param rrsi
	 * @return
	 */
	public double calDistance(int rrsi) {
		double distance = 0;
		int A = rrsi - 54;
		double n = 0;
		if (A <= 4) {
			n = 1.3;
		} else if (A > 4 && A <= 7) {
			n = 1.5;
		} else if (A > 7 && A <= 12) {
			n = 1.75;
		} else if (A > 12 && A <= 15) {
			n = 1.9;
		} else if (A > 15 && A <= 18) {
			n = 2.3;
		} else if (A > 18 && A <= 23) {
			n = 2.5;
		} else {
			n = 2.7;
		}
		distance = Math.pow(10, (double) A / (10 * n));
		return distance;
	}

	/**
	 * ���ӷ������õ������ݸ�ʽ��̿ɽ�����Json�ַ�����ȥ��ǰ���""���м�ĸ���\
	 * 
	 * @param s
	 * @return
	 */
	public String formatString(String s) {
		return s.substring(1, s.length() - 1).replace("\\", "");
	}

}
