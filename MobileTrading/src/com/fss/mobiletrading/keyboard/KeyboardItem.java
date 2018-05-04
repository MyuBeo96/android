package com.fss.mobiletrading.keyboard;

public class KeyboardItem {
	public String color;
	public String text;
	public Boolean isAppend = true;

	public KeyboardItem(String text, Boolean para_isAppend, String color) {
		super();
		this.color = color;
		this.text = text;
		isAppend = para_isAppend;
	}
}
