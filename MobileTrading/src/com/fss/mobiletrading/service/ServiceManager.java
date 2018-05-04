package com.fss.mobiletrading.service;

import java.util.HashMap;

public class ServiceManager {
	private HashMap<String, MTradingService> mapServices;

	public ServiceManager() {
		super();
		mapServices = new HashMap<String, MTradingService>();
	}

	public void addService(MTradingService service) {
		if (service != null && !mapServices.containsKey(service.getId())) {
			mapServices.put(service.getId(), service);
		}
	}

	public MTradingService getService(String serviceId) {
		if (serviceId == null || !mapServices.containsKey(serviceId)) {
			return null;
		}
		return mapServices.get(serviceId);
	}
}
