package com.fss.mobiletrading.function.watchlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.json.JSONException;

import android.util.Log;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.StockDetailsItem;
import com.mtrading.mobile.graph.IndexVolumeChartPoint;

public class StockDetailObservable extends Observable {
	private StockDetailsItem stockDetailsItem;
	private boolean isNewData = true;

	public boolean isNewData() {
		return isNewData;
	}

	public void setNewData(boolean isNewData) {
		this.isNewData = isNewData;
	}

	public StockDetailsItem getStockDetailsItem() {
		return stockDetailsItem;
	}

	public void setStockDetailsItem(StockDetailsItem stockDetailsItem) {
		this.stockDetailsItem = stockDetailsItem;
		setChanged();
		notifyObservers();
	}
}
