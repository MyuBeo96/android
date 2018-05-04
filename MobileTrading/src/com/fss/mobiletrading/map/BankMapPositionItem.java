package com.fss.mobiletrading.map;

import java.util.HashMap;

public class BankMapPositionItem {
	private String BrID;
	private String BrCode;
	private String BrName;
	private String BrAddr;
	private String latitude;
	private String longitude;

	public BankMapPositionItem() {

	}

	public void setBrID(String brID) {
		BrID = brID;
	}

	public void setBrCode(String brCode) {
		BrCode = brCode;
	}

	public void setBrName(String brName) {
		BrName = brName;
	}

	public void setBrAddr(String brAddr) {
		BrAddr = brAddr;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public BankMapPositionItem(HashMap<String, String> hm) {
		super();
		BrID = hm.get("BrID");
		BrCode = hm.get("BrCode");
		BrName = hm.get("BrName");
		BrAddr = hm.get("BrAddr");
		latitude = hm.get("latitude");
		longitude = hm.get("longitude");
	}

	public String getBrID() {
		return BrID;
	}

	public String getBrCode() {
		return BrCode;
	}

	public String getBrName() {
		return BrName;
	}

	public String getBrAddr() {
		return BrAddr;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

}
