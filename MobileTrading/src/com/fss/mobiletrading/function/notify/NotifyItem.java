package com.fss.mobiletrading.function.notify;

import java.util.HashMap;

public class NotifyItem {
	public String ID;
	public String IDdtl;
	public String Date;
	public String Short;
	public String Content;
	public String hasRead;
	public String Type;

	public NotifyItem() {

	}

	public NotifyItem(String title, String content) {
		this.Short = title;
		this.Content = content;
	}

	public NotifyItem(HashMap<String, String> hm) {
		ID = hm.get("ID");
		IDdtl = hm.get("IDdtl");
		Date = hm.get("Date");
		Short = hm.get("Short");
		Content = hm.get("Content");
		hasRead = hm.get("hasRead");
		Type = hm.get("Type");
	}

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getIDdtl() {
		return IDdtl;
	}
	public void setIDdtl(String iDdtl) {
		IDdtl = iDdtl;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getShort() {
		return Short;
	}

	public void setShort(String s) {
		Short = s;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String isHasRead() {
		return hasRead;
	}

	public void setHasRead(String hasRead) {
		this.hasRead = hasRead;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

}
