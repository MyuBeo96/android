package com.mtrading.mobile.graph;

public class IndexVolumeChartPoint {
	double index;
	double volume;
	double fullvolume;
	int time;
	double change;
	String strTime;

	public IndexVolumeChartPoint(String time, double index, double volume,
			double change) {
		super();
		this.index = index;
		this.fullvolume = volume;
		this.volume = volume / 1000000;
		this.change = change;
		this.strTime = time;

		this.time = IndexVolumeChartView.parseTimeStringToInteger(time);

	}

	public String getStrTime() {
		return strTime;
	}

	public double getChange() {
		return change;
	}

	public double getIndex() {
		return index;
	}

	public double getVolume() {
		return volume;
	}

	public double getFullvolume() {
		return fullvolume;
	}

	public int getTime() {
		return time;
	}
}
