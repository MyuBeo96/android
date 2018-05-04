package com.fss.mobiletrading.function.orderlist;

import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

import com.fss.mobiletrading.adapter.SolenhGTC_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.SolenhItem;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;
import com.msbuat.mobiletrading.design.SearchTextUI;
import com.msbuat.mobiletrading.design.TabSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GTCOrderList extends AbstractFragment {
	private static final int UPDATE_INTERVAL = 3000;
	final String AMENDORDER = "SuccessService#AMENDORDER";
	final String CANCELORDERGTC = "SuccessService#CANCELORDER";
	final String DOCANCELORDER = "SuccessService#DOCANCELORDER";
	final String MGTCORDERREALTIME = "MorderService#MGTCORDERREALTIME";
	final String ORDERGTCDETAILS = "OrderDetailsService#ORDERGTCDETAILS";

	ListView lv_Solenh;
	TabSelector tabSelector;
	SearchTextUI searchTextUI;

	List<SolenhItem> listSolenhItem;
	SolenhGTC_Adapter adapterSolenh;

	int currentTextView;
	int SelectedItemPosition;
	String statusOrder1 = "All"; // status 1
	String statusOrder2 = "All"; // status 2
	String filterText = StringConst.EMPTY; // filter by text

	Point p;
	Timer timer;
	SolenhItem cancelItem;
	int filterDataHashcode;

	public static GTCOrderList newInstance(MainActivity mActivity) {

		GTCOrderList self = new GTCOrderList();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.GTCOrder);
		return self;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.gtcorderlist, viewGroup, false);
		initView(view);
		initData();
		initListener();
		Common.setupUI(view, mainActivity);
		return view;
	}

	private void initView(View view) {
		lv_Solenh = ((ListView) view.findViewById(R.id.listview_solenhgtc));
		tabSelector = (TabSelector) view.findViewById(R.id.tab_gtcorderlist);
		if (DeviceProperties.isTablet) {
			searchTextUI = (SearchTextUI) view
					.findViewById(R.id.searchtext_gtcorderlist_all);
			searchTextUI.setVisibleButton(false);
			searchTextUI.setVisibleClearField(true);
			searchTextUI.setTextColor(getColorResource(R.color.white_text));
		}
	}

	private void initData() {
		if (listSolenhItem == null) {
			listSolenhItem = new ArrayList<SolenhItem>();
		}
		if (adapterSolenh == null) {
			adapterSolenh = new SolenhGTC_Adapter(mainActivity,
					android.R.layout.simple_list_item_1, listSolenhItem);
			adapterSolenh.setItemClickAction(new SimpleAction() {

				@Override
				public void performAction(Object obj) {
					if (obj != null && obj instanceof SolenhItem) {
						mainActivity.sendArgumentToFragment(
								GTCOrderDetail.class.getName(), obj);
						mainActivity.navigateFragment(GTCOrderDetail.class
								.getName());
					}
				}
			});

			adapterSolenh.setmCancelClickAction(new SimpleAction() {

				@Override
				public void performAction(Object obj) {
					if (obj instanceof SolenhItem
							&& ((SolenhItem) obj).isCancellable
									.equals(StringConst.TRUE)) {
						cancelItem = (SolenhItem) obj;
						showDialogMessage(
								getStringResource(R.string.thong_bao),
								getStringResource(R.string.BanCoMuonHuyLenhKhong),
								new SimpleAction() {

									@Override
									public void performAction(Object obj) {
										// positive
										cancelOrder();
									}
								}, new SimpleAction() {

									@Override
									public void performAction(Object obj) {
										// negative
									}
								}, getStringResource(R.string.Yes),
								getStringResource(R.string.No));

					} else {
						showDialogMessage(R.string.thong_bao,
								R.string.solenh_lenhkhongduocphephuy);
					}
				}
			});
		}
		lv_Solenh.setAdapter(adapterSolenh);
	}

	private void initListener() {
		if (DeviceProperties.isTablet) {
			searchTextUI.getEditText().addTextChangedListener(
					new TextWatcher() {

						@Override
						public void onTextChanged(CharSequence s, int start,
								int before, int count) {

						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {

						}

						@Override
						public void afterTextChanged(Editable s) {
							filterText = s.toString().toLowerCase();
							filterOrder();
						}
					});
		}
		// lv_Solenh.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {

		// SolenhItem item = listSolenhItem.get(position);
		// mainActivity.sendArgumentToFragment(
		// GTCOrderDetail.class.getName(), item);
		// mainActivity.navigateFragment(GTCOrderDetail.class.getName());
		// CancelTimer();
		// }
		// });
		lv_Solenh.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				SelectedItemPosition = position;
				return false;
			}
		});
		tabSelector.setOnTabSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					// tất cả các lệnh
					statusOrder1 = "All";
					statusOrder2 = "All";
					adapterSolenh.isUpdate = true;
					filterOrder();
					break;
				case 1:
					// chờ gửi
					statusOrder1 = "P";
					statusOrder2 = "P";
					adapterSolenh.isUpdate = true;
					filterOrder();
					break;
				case 2:
					// hoạt động
					statusOrder1 = "A";
					statusOrder2 = "A";
					adapterSolenh.isUpdate = true;
					filterOrder();
					break;

				case 3:
					// hủy
					statusOrder1 = "R";
					statusOrder2 = "R";
					adapterSolenh.isUpdate = true;
					filterOrder();
					break;

				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	public void onPause() {
		super.onPause();
		if (DeviceProperties.isTablet) {
			searchTextUI.getEditText().getText().clear();
		}
		CancelTimer();
	}

	public void onResume() {
		super.onResume();
		tabSelector.setItemSelected(0);
		CallMGTCOrder(MGTCORDERREALTIME);

	}

	@Override
	public void onShowed() {
		super.onShowed();
		CallMGTCOrder(MGTCORDERREALTIME);
	}

	@Override
	public void onHide() {
		super.onHide();
		CancelTimer();
	}

	private void cancelOrder() {
		if (cancelItem != null) {
			CallCancelOrderGTC(cancelItem);
		}
	}

	private void CallCancelOrderGTC(SolenhItem item) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_CancelOrderGTC));
		}
		{
			list_key.add("OrderId");
			list_value.add(item.OrderId);
		}
		{
			list_key.add("Custodycd");
			list_value.add(item.CustodyCd);
		}
		StaticObjectManager.connectServer.callHttpPostService(CANCELORDERGTC,
				this, list_key, list_value);
	}

	private void CallMGTCOrder(String key) {
		try {
			StaticObjectManager.connectServer.callHttpGetService(key, this,
					MSTradeAppConfig.controller_GTCOrders);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void callRealtimeDelay() {
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		TimerTask timertask = new TimerTask() {
			@Override
			public void run() {
				CallMGTCOrder(MGTCORDERREALTIME);
			}
		};
		try {
			timer.schedule(timertask, UPDATE_INTERVAL);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	private void CancelTimer() {
		if (timer != null) {
			timer.cancel();
		}
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		switch (key) {
		case MGTCORDERREALTIME:
			if (rObj.obj != null) {
				listSolenhItem.clear();
				listSolenhItem.addAll((List<SolenhItem>) rObj.obj);
				filterOrder();
			}
			break;
		case CANCELORDERGTC:
			showDialogMessage(R.string.thong_bao, R.string.Giaodichthanhcong);

			break;
		case AMENDORDER:
			showDialogMessage(R.string.thong_bao, R.string.Giaodichthanhcong);
			break;
		default:
			break;
		}
	}

	@Override
	protected void isReceivedResponse(String key) {
		super.isReceivedResponse(key);
		switch (key) {
		case MGTCORDERREALTIME:
			callRealtimeDelay();
			break;
		case CANCELORDERGTC:
			break;
		case AMENDORDER:
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

	static String STATUS_ALL = "All";

	private void filterOrder() {
		FilterData filterData = new FilterData();
		filterData.list = listSolenhItem;
		filterData.adapterSolenh = adapterSolenh;
		filterData.statusOrder1 = statusOrder1;
		filterData.statusOrder2 = statusOrder2;
		filterData.filterText = filterText;
		filterData.hashCode = filterData.hashCode();
		filterDataHashcode = filterData.hashCode;
		filterData.execute(StringConst.EMPTY);
	}

	class FilterData extends AsyncTask<String, String, List<Integer>> {
		int hashCode;
		String statusOrder1;
		String statusOrder2;
		String filterText;
		SolenhGTC_Adapter adapterSolenh;
		List<SolenhItem> list;

		@Override
		protected List<Integer> doInBackground(String... params) {
			List<Integer> filterItems = new ArrayList<Integer>();
			for (int i = 0; i < list.size(); i++) {
				SolenhItem item = list.get(i);
				if (statusOrder1.equals(STATUS_ALL)) {
				} else {
					if (!(item.statusValue.equals(statusOrder1))
							&& !(item.statusValue.equals(statusOrder2))) {
						continue;
					}
				}
				if (filterText.length() > 0) {
					String latin = Common.convertUTF8ToLatin(filterText);
					if (latin != null
							&& !(list.get(i).toString().contains(latin))) {
						continue;
					}
				}
				filterItems.add(i);
			}
			return filterItems;
		}

		@Override
		protected void onPostExecute(List<Integer> result) {
			super.onPostExecute(result);
			if (hashCode == filterDataHashcode) {
				adapterSolenh.setFilterItemPosition(result);
				adapterSolenh.notifyDataSetChanged();
			}
		}
	}
	@Override
	public void refresh() {
		super.refresh();
		CallMGTCOrder(MGTCORDERREALTIME);
	}
}
