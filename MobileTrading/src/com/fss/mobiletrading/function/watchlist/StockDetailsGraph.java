package com.fss.mobiletrading.function.watchlist;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONException;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.object.StockDetailsItem;
import com.fss.mobiletrading.view.MarketIndexGraphView;
import com.fscuat.mobiletrading.R;
import com.mtrading.mobile.graph.IndexVolumeChartPoint;
import com.mtrading.mobile.graph.IndexVolumeChartView;
import com.mtrading.mobile.graph.TradeLog;

public class StockDetailsGraph extends MarketIndexGraphView {
	TradeLog tradeLog;
	DecimalFormat fullvolFormat = new DecimalFormat("#,###");

	public void setTradeLog(TradeLog tradeLog) {
		this.tradeLog = tradeLog;
	}

	public void trigger(List<IndexVolumeChartPoint> list, boolean isNewData,
			double average) {
		if (list != null) {
			if (isNewData) {
				Log.e("watchlog", "bat dau ve chart");
				indexVolumeChartView.temp_AverageIndex = average;
				createChart(list);
			} else {
				Log.e("watchlog", "bat dau ve chart");
				indexVolumeChartView.temp_AverageIndex = average;
				updateChart(list);
			}
		} else {
			Log.e("watchlog", "bat dau ve chart");
			createChart(null);
		}
	}

	public void trigger2(List<IndexVolumeChartPoint> list, boolean isNewData,
			double average) {
		if (list != null) {
			if (isNewData) {
				Log.e("watchlog", "bat dau ve chart");
				indexVolumeChartView.temp_AverageIndex = average;
				createChart2(list);
			} else {
				Log.e("watchlog", "bat dau ve chart");
				indexVolumeChartView.temp_AverageIndex = average;
				updateChart2(list);
			}
		} else {
			Log.e("watchlog", "bat dau ve chart");
			createChart2(null);
		}
	}

	@Override
	protected String genDetail(String time, String index, String volume,
			int color) {
		String detail = getStringResource(R.string.ThoiGian) + ": " + time
				+ "  " + getStringResource(R.string.Gia) + ": " + index + "  "
				+ getStringResource(R.string.KhoiLuong) + ": " + volume + "  ";
		return detail;
	}

	public void setAutoClickChart(IndexVolumeChartPoint item) {
		indexVolumeChartView.setAutoClickChart(item);
		// double averageIndex = IndexVolumeChartView.averageIndex;
		// int color;
		// if (item.getIndex() > averageIndex) {
		// color = Color.GREEN;
		// } else if (item.getIndex() < averageIndex) {
		// color = Color.RED;
		// } else {
		// color = Color.YELLOW;
		// }
		// setDetail(item.getStrTime(), String.valueOf(item.getIndex()),
		// fullvolFormat.format(item.getFullvolume()), color);
	}

	@Override
	public void onClickChartListener(IndexVolumeChartPoint item) {
		super.onClickChartListener(item);
		tradeLog.setSelection(item);
	}
}
