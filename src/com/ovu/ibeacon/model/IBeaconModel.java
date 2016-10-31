package com.ovu.ibeacon.model;

public class IBeaconModel {
	
	String uuid;
	String rssi;
	String name;
	double distance;
	
	/**
	 * �޲������췽��
	 */
	public IBeaconModel(){
		
	}
	
	/**
	 * �� uuid ��  distance �Ĺ��췽��
	 * @param uuid
	 * @param distance
	 */
	public IBeaconModel(String uuid, double distance) {
		super();
		this.uuid = uuid;
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRssi() {
		return rssi;
	}

	public void setRssi(String rssi) {
		this.rssi = rssi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
