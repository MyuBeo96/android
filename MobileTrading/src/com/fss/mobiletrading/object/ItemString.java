package com.fss.mobiletrading.object;

public class ItemString {
	private String text;
	private String value;

	public ItemString(String text, String value) {
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return this.text;
	}

	public String getValue() {
		return this.value;
	}

	public void setText(String paramString) {
		this.text = paramString;
	}

	public void setValue(String paramString) {
		this.value = paramString;
	}

	public String toString() {
		return this.text;
	}
}
