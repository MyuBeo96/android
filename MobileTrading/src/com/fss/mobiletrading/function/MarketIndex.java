package com.fss.mobiletrading.function;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fss.mobiletrading.adapter.TabsPagerAdapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.market.MarketIndexTradeLog;
import com.fss.mobiletrading.function.market.MarketInfo;
import com.fss.mobiletrading.object.IndicesItem;
import com.fss.mobiletrading.object.MarketIndexItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.view.MarketIndexGraphView;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.design.DialogLoading;
import com.msbuat.mobiletrading.design.TextViewHightLight;
import com.mtrading.mobile.graph.IndexVolumeChartPoint;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class MarketIndex extends AbstractFragment {

	static final long UPDATE_INTERVAL = 5000;
	public static IndicesItem indicesItem;
	static final String MARKETINDEX = "MarketIndexService#MARKETINDEX";
	static final String MARKETCHART = "MarketChartService#MARKETCHART";
	ImageView img_KyHieu;
	String lastSeqMarketIndex = "0";
	String lastSeqMarketChart = "0";
	TabsPagerAdapter mAdapter;
	ArrayList<AbstractFragment> listFragment;
	MarketIndexItem marketIndexItem;
	Timer timer;
	TextViewHightLight tv_TotalShare;
	TextViewHightLight tv_TotalTrade;
	TextViewHightLight tv_TotalValue;
	TextView tv_advances;
	TextView tv_declines;
	TextViewHightLight tv_indexChange;
	TextViewHightLight tv_indexPercentChange;
	TextView tv_marketCode;
	TextViewHightLight tv_marketIndex;
	TextView tv_noChange;
	TextView tv_marketIndexStatus;
	ViewPager viewPager;
	int viewPaperSelection;
	private MarketIndexGraphView graphView;
	private MarketIndexTradeLog tradelog;
	List<IndexVolumeChartPoint> dataChart;
	Runnable updateRealtime;
	boolean isUpdating = false;
	DialogLoading dlg_loading;

	public static MarketIndex newInstance(MainActivity mActivity) {

		MarketIndex self = new MarketIndex();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.MarketIndex);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View localView = paramLayoutInflater.inflate(R.layout.marketindex,
				paramViewGroup, false);
		tv_marketCode = ((TextView) localView
				.findViewById(R.id.text_marketindex_MarketCode));
		tv_indexPercentChange = ((TextViewHightLight) localView
				.findViewById(R.id.text_marketindex_indexPercentChange));
		tv_indexChange = ((TextViewHightLight) localView
				.findViewById(R.id.text_marketindex_indexChange));
		tv_marketIndex = ((TextViewHightLight) localView
				.findViewById(R.id.text_marketindex_marketIndex));
		tv_TotalShare = ((TextViewHightLight) localView
				.findViewById(R.id.text_marketindex_TotalShare));
		tv_TotalValue = ((TextViewHightLight) localView
				.findViewById(R.id.text_marketindex_TotalValue));
		tv_TotalTrade = ((TextViewHightLight) localView
				.findViewById(R.id.text_marketindex_TotalTrare));
		tv_advances = ((TextView) localView
				.findViewById(R.id.text_marketindex_advances));
		tv_declines = ((TextView) localView
				.findViewById(R.id.text_marketindex_declines));
		tv_noChange = ((TextView) localView
				.findViewById(R.id.text_marketindex_noChange));
		img_KyHieu = ((ImageView) localView
				.findViewById(R.id.img_marketindex_KyHieu));
		viewPager = ((ViewPager) localView
				.findViewById(R.id.marketindex_viewpager));
		tv_marketIndexStatus = ((TextView) localView
				.findViewById(R.id.text_marketindex_Status));
		initialise();
		initialiseListener();
		return localView;
	}

	private void initialise() {
		dlg_loading = new DialogLoading(mainActivity);
		dataChart = new ArrayList<IndexVolumeChartPoint>();
		listFragment = new ArrayList<AbstractFragment>();
		graphView = new MarketIndexGraphView();
		tradelog = new MarketIndexTradeLog();
		listFragment.add(graphView);
		listFragment.add(tradelog);
		mAdapter = new TabsPagerAdapter(getChildFragmentManager(), listFragment);
		viewPager.setAdapter(mAdapter);
		viewPager.setCurrentItem(viewPaperSelection);
		if (updateRealtime == null) {
			updateRealtime = new Runnable() {

				@Override
				public void run() {
					CallMarketIndex();
					// CallMarketChart();
				}
			};
		}
	}

	private void initialiseListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

				viewPaperSelection = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void startUpdateMarket() {
		if (!isUpdating) {
			CallMarketIndex();
			// CallMarketChart();
			isUpdating = true;
		}
	}

	private void stopUpdateMarket() {
		isUpdating = false;
	}

	private void postDelayUpdateMarket() {
		if (isUpdating) {
			mainActivity.delay.postDelayed(updateRealtime, UPDATE_INTERVAL);
		}
	}

	public void onPause() {
		super.onPause();
		stopUpdateMarket();
		CancelTimer();
		if (marketIndexItem != null) {
			marketIndexItem.listPoint.clear();
		}
	}

	public void onResume() {
		super.onResume();
		startUpdateMarket();
		AsynchTaskTimer();
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoAction();
	}

	@Override
	protected void performBackAction() {
		super.performBackAction();
		mainActivity.displayFragment(MarketInfo.class.getName());
	}

	private void CallMarketIndex() {
		try {
			if (indicesItem != null && indicesItem.MarketName != null
					&& indicesItem.MarketId != null) {
				List<String> list_key = new ArrayList<String>();
				List<String> list_value = new ArrayList<String>();
				{
					list_key.add("link");
					list_value.add(MSTradeAppConfig.controller_MarketIndex);
				}
				{
					list_key.add("pv_MarketCode");
					list_value.add(indicesItem.MarketName);
				}
				{
					list_key.add("pv_lastSeq");
					list_value.add(lastSeqMarketIndex);
				}
				{
					list_key.add("pv_marketId");
					list_value.add(indicesItem.MarketId);
				}
				StaticObjectManager.connectServer.callHttpPostService(
						MARKETINDEX, this, list_key, list_value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void CallMarketChart() {
		try {
			if (indicesItem != null && indicesItem.MarketName != null
					&& indicesItem.MarketId != null) {
				List<String> list_key = new ArrayList<String>();
				List<String> list_value = new ArrayList<String>();
				{
					list_key.add("link");
					list_value.add(MSTradeAppConfig.controller_MarketChart);
				}
				{
					list_key.add("pv_MarketCode");
					list_value.add(indicesItem.MarketName);
				}
				{
					list_key.add("pv_lastSeq");
					list_value.add(lastSeqMarketChart);
				}
				{
					list_key.add("pv_marketId");
					list_value.add(indicesItem.MarketId);
				}
				StaticObjectManager.connectServer.callHttpPostService(
						MARKETCHART, this, list_key, list_value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showMarketIndex() {
		tv_marketCode.setText(marketIndexItem.marketCode);
		tv_indexPercentChange.setText(marketIndexItem.indexPercentChange + "%");
		tv_indexChange.setText(marketIndexItem.indexChange);
		tv_marketIndex.setText(marketIndexItem.marketIndex);
		tv_TotalShare.setText(Common.formatAmount(marketIndexItem.totalVolume));
		if (marketIndexItem.totalValue.length() > 9) {
			tv_TotalValue
					.setText(NumberFormat
							.getNumberInstance(Locale.US)
							.format(Double
									.parseDouble(marketIndexItem.totalValue) / 1000000000)
							+ " " + getStringResource(R.string.Ty));
		} else {
			tv_TotalValue.setText(Common
					.formatAmount(marketIndexItem.totalValue));
		}
		tv_TotalTrade.setText(Common.formatAmount(marketIndexItem.totalTrade));
		tv_advances.setText(marketIndexItem.advances);
		tv_declines.setText(marketIndexItem.declines);
		tv_noChange.setText(marketIndexItem.noChange);
		tv_marketIndexStatus.setText(indicesItem.MarketStatusText);
		setColor();
	}

	private void drawChartView(List<IndexVolumeChartPoint> listPoint) {
		dlg_loading.show();
		if (mAdapter != null) {
			if (MarketIndex.this.isResumed() && lastSeqMarketIndex.equals("0")) {
				graphView.createChart(listPoint);
			} else {
				graphView.updateChart(listPoint);
			}
			tradelog.setListPoints(listPoint);
		}
		dlg_loading.dismiss();
	}

	private void setColor() {
		try {
			Double indexChange = Double.valueOf(marketIndexItem.indexChange);

			if (indexChange > 0.0D) {
				tv_indexChange.setTextColor(getResources().getColor(
						R.color.color_Mua));
				tv_indexPercentChange.setTextColor(getResources().getColor(
						R.color.color_Mua));
				tv_marketIndex.setTextColor(getResources().getColor(
						R.color.color_Mua));
				tv_marketIndexStatus.setTextColor(getResources().getColor(
						R.color.color_Mua));
				img_KyHieu.setImageResource(R.drawable.ic_up);
			} else {
				if (indexChange == 0.0D) {
					tv_indexChange.setTextColor(getResources().getColor(
							R.color.color_ThamChieu));
					tv_indexPercentChange.setTextColor(getResources().getColor(
							R.color.color_ThamChieu));
					tv_marketIndex.setTextColor(getResources().getColor(
							R.color.color_ThamChieu));
					tv_marketIndexStatus.setTextColor(getResources().getColor(
							R.color.color_ThamChieu));
					img_KyHieu.setImageResource(R.drawable.ic_yellow);
				} else {
					tv_indexChange.setTextColor(getResources().getColor(
							R.color.color_Ban));
					tv_indexPercentChange.setTextColor(getResources().getColor(
							R.color.color_Ban));
					tv_marketIndex.setTextColor(getResources().getColor(
							R.color.color_Ban));
					tv_marketIndexStatus.setTextColor(getResources().getColor(
							R.color.color_Ban));
					img_KyHieu.setImageResource(R.drawable.ic_downred);
				}
			}
		} catch (Exception localException) {
		}
	}

	public void AsynchTaskTimer() {
		// final Handler handler = new Handler();
		//
		// TimerTask timertask = new TimerTask() {
		// @Override
		// public void run() {
		// handler.post(new Runnable() {
		// public void run() {
		// try {
		// CallMarketIndex();
		// CallMarketChart();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// });
		// }
		// };
		// timer = new Timer();
		// timer.schedule(timertask, 0, UPDATE_INTERVAL);
	}

	private void CancelTimer() {
		// timer.cancel();
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case MARKETINDEX:
			if (rObj.obj != null) {
				marketIndexItem = ((MarketIndexItem) rObj.obj);
				showMarketIndex();
				if (marketIndexItem != null) {
					lastSeqMarketIndex = marketIndexItem.lastSeq;
					if (marketIndexItem.listPoint != null) {
						if (marketIndexItem.listPoint.size() >= 600) {
							dataChart.addAll(marketIndexItem.listPoint);
							CallMarketIndex();
						} else {
							dataChart.addAll(marketIndexItem.listPoint);
							drawChartView(dataChart);
							dataChart.clear();
							postDelayUpdateMarket();
						}
					} else {
						postDelayUpdateMarket();
					}
				} else {
					postDelayUpdateMarket();
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void isReceivedResponse(String key) {
		super.isReceivedResponse(key);
		switch (key) {
		case MARKETINDEX:
			break;
		default:
			break;
		}
	}

	@Override
	protected void processNull(String key) {
		super.processNull(key);
		CancelTimer();
	}
}
