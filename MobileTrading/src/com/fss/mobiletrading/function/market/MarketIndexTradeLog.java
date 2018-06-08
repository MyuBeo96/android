package com.fss.mobiletrading.function.market;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fss.mobiletrading.adapter.TradeLog_Adapter;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.R;
import com.mtrading.mobile.graph.IndexVolumeChartPoint;

public class MarketIndexTradeLog extends AbstractFragment {
	ListView livTradelog;
	TradeLog_Adapter adapter;
	List<IndexVolumeChartPoint> listAPoint = new ArrayList<IndexVolumeChartPoint>();

	public MarketIndexTradeLog() {
	}

	@Override
	protected void process(String key, ResultObj rObj) {

	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(
				R.layout.marketindex_tradelog, paramViewGroup, false);
		livTradelog = (ListView) localView.findViewById(R.id.livTradelog);
		initialise();
		initListener();
		return localView;
	}

	private void initListener() {
		livTradelog.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.setItemCheckedPosition(position);
			}
		});
	}

	public void setListPoints(List<IndexVolumeChartPoint> paramList) {
		listAPoint.addAll(paramList);
		adapter.notifyDataSetChanged();
	}

	private void initialise() {
		if (listAPoint == null) {
			listAPoint = new ArrayList<IndexVolumeChartPoint>();
		} else {
			listAPoint.clear();
		}

		if (adapter == null) {
			adapter = new TradeLog_Adapter(getActivity(),
					android.R.layout.simple_list_item_1, listAPoint);
		} else {
			adapter.notifyDataSetChanged();
		}
		livTradelog.setAdapter(adapter);
	}

}
