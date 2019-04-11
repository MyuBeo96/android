package com.fss.mobiletrading.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.design.DialogLoading;
import com.mtrading.mobile.graph.IndexVolumeChartPoint;
import com.mtrading.mobile.graph.IndexVolumeChartView;
import com.mtrading.mobile.graph.IndexVolumeChartView.OnChartClickListener;
import com.mtrading.mobile.graph.IndexVolumeChartView.OnFinishedDrawChart;

public class MarketIndexGraphView extends AbstractFragment {
	LinearLayout layout;
	TextView tv_detail;
	public IndexVolumeChartView indexVolumeChartView;
	DialogLoading dlg_loading;

	// public boolean trung = false;

	public MarketIndexGraphView() {
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.marketindex_graphview, viewGroup,
				false);
		this.layout = ((LinearLayout) view
				.findViewById(R.id.lay_marketindex_graphview));
		tv_detail = (TextView) view.findViewById(R.id.tv_details);
		dlg_loading = new DialogLoading(getActivity());
		if (indexVolumeChartView == null) {
			indexVolumeChartView = new IndexVolumeChartView(getActivity(), null);
			indexVolumeChartView
					.setOnChartClickListener(new OnChartClickListener() {

						@Override
						public void onClick(String time, String index,
								String volume, int color,
								IndexVolumeChartPoint point) {
							setDetail(time, index, volume, color);
							onClickChartListener(point);
						}

					});
			indexVolumeChartView
					.setOnFinishedDrawChart(new OnFinishedDrawChart() {

						@Override
						public void onFinishedDraw() {
							if (dlg_loading.isShowing()) {
								dlg_loading.dismiss();
							}

						}
					});
		}
		layout.addView(indexVolumeChartView);
		return view;
	}

	protected String genDetail(String time, String index, String volume,
			int color) {
		String detail = getStringResource(R.string.ThoiGian) + ": " + time
				+ "  " + getStringResource(R.string.Index) + ": " + index
				+ "  " + getStringResource(R.string.KhoiLuong) + ": " + volume
				+ "  ";
		return detail;
	}

	protected void setDetail(String time, String index, String volume, int color) {
		tv_detail.setText(genDetail(time, index, volume, color));
		tv_detail.setTextColor(color);
	}

	public void onClickChartListener(IndexVolumeChartPoint item) {
	}

	@Override
	public void onPause() {
		super.onPause();
		if (dlg_loading.isShowing()) {
			dlg_loading.dismiss();
		}
		layout.removeView(indexVolumeChartView);
	}

	public void createChart(List<IndexVolumeChartPoint> data) {
		if (data != null) {
			Log.e("createChart", "lenght " + data.size() + "");
		}
		// dlg_loading.show();
		indexVolumeChartView.createChart(data);
		tv_detail.setText(StringConst.EMPTY);
	}

	List<IndexVolumeChartPoint> dataUpdateChart = new ArrayList<IndexVolumeChartPoint>();

	public void updateChart(List<IndexVolumeChartPoint> data) {
		if (data != null && data.size() > 0) {
			Log.e("updateChart", "lenght " + data.size() + "");
			// dlg_loading.show();
			dataUpdateChart = data;
			indexVolumeChartView.updateChart2(dataUpdateChart);
		}
	}

	@Override
	protected void process(String key, ResultObj rObj) {

	}

	public boolean receiverparameter(String... para) {
		return false;
	}

	public void resetChart() {
		indexVolumeChartView.createChart(null);
		tv_detail.setText(StringConst.EMPTY);
	}

	public void createChart2(List<IndexVolumeChartPoint> data) {
		if (data != null) {
			Log.e("Length", data.size() + "");
		}
		indexVolumeChartView.createChart2(data);
		tv_detail.setText(StringConst.EMPTY);
	}

	public void updateChart2(List<IndexVolumeChartPoint> data) {
		if (data != null && data.size() > 0) {
			Log.e("Length", data.size() + "");
			indexVolumeChartView.updateChart2(data);
		}
	}
}
