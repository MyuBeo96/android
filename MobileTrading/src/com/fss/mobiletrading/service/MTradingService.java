package com.fss.mobiletrading.service;

public class MTradingService {
	String id;

	public MTradingService() {
		id = this.getClass().getName();
	}

	public String getId() {
		return id;
	}
}
