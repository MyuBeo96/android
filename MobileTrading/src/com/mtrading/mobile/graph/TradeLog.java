package com.mtrading.mobile.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONException;

import com.fss.mobiletrading.adapter.TradeLog_Adapter;
import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.function.watchlist.StockDetailObservable;
import com.fss.mobiletrading.function.watchlist.StockDetailsGraph;
import com.fss.mobiletrading.object.StockDetailsItem;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TradeLog extends ListView implements Observer {
	Context context;
	List<IndexVolumeChartPoint> data;
	TradeLog_Adapter adapter;
	StockDetailsGraph graph;
	String average;

	public void setGraph(StockDetailsGraph graph) {
		this.graph = graph;
	}

	public TradeLog(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		data = new ArrayList<IndexVolumeChartPoint>();
		adapter = new TradeLog_Adapter(context,
				android.R.layout.simple_list_item_1, data);
		setAdapter(adapter);
		setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.setItemCheckedPosition(position);
				IndexVolumeChartPoint item = data.get(position);
				if (graph != null) {
					graph.setAutoClickChart(item);
				}
			}
		});
		initThread();
	}

	public void setSelection(IndexVolumeChartPoint item) {
		int index = data.indexOf(item);
		if (index != -1) {
			adapter.setItemCheckedPosition(index);
			if (!(index >= getFirstVisiblePosition() && index <= getLastVisiblePosition())) {
				setSelection(index);
			}

		}
	}

	public void clearData() {
		data.clear();
	}

	public void notifyDataSetChanged() {
		adapter.notifyDataSetChanged();
	}

	public void fillData(List<IndexVolumeChartPoint> list) {
		data.clear();
		data.addAll(list);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void update(Observable observable, Object data) {

		if (observable instanceof StockDetailObservable) {
			StockDetailObservable observable2 = (StockDetailObservable) observable;
			StockDetailsItem stockDetailsItem = observable2
					.getStockDetailsItem();
			if (stockDetailsItem != null) {
				average = stockDetailsItem.reference;
				MyJsonObject jObj = observable2.getStockDetailsItem().data;
				List<IndexVolumeChartPoint> list = parseData(jObj);
				if (list != null) {
					if (observable2.isNewData()) {
						this.data.clear();
						this.data.addAll(list);

					} else {
						Log.e("watchlog", "update chart");
						this.data.addAll(list);
					}
				} else {
					Log.e("watchlog", "clear chart");
					this.data.clear();
				}
				if (getVisibility() == VISIBLE) {
					drawChart();
					adapter.notifyDataSetChanged();
				}
			}
		}
	}

	private List<IndexVolumeChartPoint> parseData(MyJsonObject DT) {
		List<IndexVolumeChartPoint> list = new ArrayList<IndexVolumeChartPoint>();
		MyJsonArray TL = DT.getJSONArray("TL");
		int length = TL.length();
		for (int i = 0; i < length; i++) {
			MyJsonObject obj = TL.getJSONObject(i);
			try {
				list.add(new IndexVolumeChartPoint(obj
						.getString("formattedTime"), obj
						.getDouble("formattedMatchPrice"), obj
						.getDouble("formattedVol"), obj
						.getDouble("formattedChangeValue")));
			} catch (JSONException e) {
			}
		}
		return list;
	}

	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if (visibility == VISIBLE) {
			drawChart();
			adapter.notifyDataSetChanged();
		}
	}

	private void drawChart() {
		// if (graph != null) {
		// graph.trigger(list, observable2.isNewData());
		// }
		drawChartThread.run();
	}

	private void initThread() {
		Runnable drawChart = new Runnable() {

			@Override
			public void run() {
				if (graph != null) {
					double averageDouble;
					try {
						averageDouble = Double.parseDouble(average);
					} catch (NumberFormatException e) {
						averageDouble = Double.MIN_VALUE;
					} catch (NullPointerException e) {
						averageDouble = Double.MIN_VALUE;
					}

					graph.trigger2(data, true, averageDouble);
				}
			}
		};
		drawChartThread = new Thread(drawChart);
	}

	Thread drawChartThread;
}
