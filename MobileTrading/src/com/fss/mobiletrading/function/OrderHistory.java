package com.fss.mobiletrading.function;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.fss.designcontrol.SymbolEdittext;
import com.fss.mobiletrading.adapter.OrderHistoryAdapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.LichSuLenh_Item;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.design.FilterArrayAdapter.Condition;
import com.msbuat.mobiletrading.design.InputDate;
import com.msbuat.mobiletrading.design.InputDate.OnChangeTextDateListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OrderHistory extends AbstractFragment {

	public final String PASTORDER = "PastOrderService";

	OrderHistoryAdapter adapterLichSuLenh;
	Button btn_Search;
	InputDate edt_dateend;
	InputDate edt_datestart;
	List<LichSuLenh_Item> listLichSuLenh;
	ListView lv_LichSuLenh;
	int selectedItemPosition;
	SymbolEdittext edt_searchsymbol;
	String filterSymbol = StringConst.EMPTY;

	public static OrderHistory newInstance(MainActivity mActivity) {

		OrderHistory self = new OrderHistory();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.OrderHistory);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.orderhistory,
				paramViewGroup, false);
		edt_datestart = ((InputDate) localView
				.findViewById(R.id.date_cashstatement_fromdate));
		edt_dateend = ((InputDate) localView
				.findViewById(R.id.date_cashstatement_todate));
		btn_Search = ((Button) localView
				.findViewById(R.id.btn_LichSuLenh_Search));
		lv_LichSuLenh = ((ListView) localView
				.findViewById(R.id.listview_LichSuLenh));
		edt_searchsymbol = (SymbolEdittext) localView
				.findViewById(R.id.edt_searchstock_search);
		edt_searchsymbol.setSymbolEdittextType(SymbolEdittext.SymbolEdittextType.Single);
		initialise();
		initialiseListener();
		return localView;
	}

	private void initialise() {
		if (listLichSuLenh == null) {
			listLichSuLenh = new ArrayList<LichSuLenh_Item>();
		} else {
			listLichSuLenh.clear();
		}

		if (adapterLichSuLenh == null) {
			adapterLichSuLenh = new OrderHistoryAdapter(getActivity(),
					android.R.layout.simple_list_item_1, listLichSuLenh);
			adapterLichSuLenh.addCondition(new Condition<LichSuLenh_Item>() {

				@Override
				public boolean checkCondition(LichSuLenh_Item item) {
					if (item == null) {
						return false;
					}
					if (filterSymbol == null) {
						return true;
					}
					if (item.toString().contains(filterSymbol)) {
						return true;
					} else {
						return false;
					}
				}
			});
		} else {
			adapterLichSuLenh.notifyDataSetChanged();
		}
		lv_LichSuLenh.setAdapter(adapterLichSuLenh);
		edt_datestart.setLabel(getStringResource(R.string.TuNgay));
		edt_dateend.setLabel(getStringResource(R.string.DenNgay));
	}

	private void initialiseListener() {
		btn_Search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CallPastOrder();
			}
		});
		edt_datestart
				.setOnChangeTextDateListener(new OnChangeTextDateListener() {

					@Override
					public void onChange(String date) {
						CallPastOrder();
					}
				});
		edt_dateend.setOnChangeTextDateListener(new OnChangeTextDateListener() {

			@Override
			public void onChange(String date) {
				CallPastOrder();
			}
		});
		lv_LichSuLenh.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				selectedItemPosition = position;
				return false;
			}
		});
		edt_searchsymbol.setInputType(InputType.TYPE_NULL);
		edt_searchsymbol.setRawInputType(InputType.TYPE_CLASS_TEXT);
		edt_searchsymbol.setTextIsSelectable(true);
		edt_searchsymbol.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mainActivity.showKBoardHook(true, v, StaticObjectManager.getListAllStock());
					mainActivity.showKBoardSymbol(true, v);
				} else {
					mainActivity.showKBoardHook(false, null, null);
					mainActivity.showKBoardSymbol(false, null);
				}
			}
		});
		edt_searchsymbol.addTextChangedListener(new TextWatcher() {
			int DELAYTIME = 500;
			Timer timer = new Timer();

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				filterSymbol = Common.convertUTF8ToLatin(s.toString()
						.toLowerCase());
				timer.cancel();
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						mainActivity.delay.post(new Runnable() {
							@Override
							public void run() {
								adapterLichSuLenh.notifyDataSetChanged();
							}
						});
					}
				}, DELAYTIME);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		edt_datestart.resetDate();
		edt_dateend.resetDate();

	}

	@Override
	public void onPause() {
		super.onPause();
		edt_searchsymbol.getEditableText().clear();
	}

	// @Override
	// public void onCreateContextMenu(ContextMenu menu, View v,
	// ContextMenuInfo menuInfo) {
	// super.onCreateContextMenu(menu, v, menuInfo);
	// getActivity().getMenuInflater().inflate(R.menu.ctxt_menu_orderhistory,
	// menu);
	// }
	//
	// @Override
	// public boolean onContextItemSelected(MenuItem item) {
	//
	// switch (item.getItemId()) {
	// case R.id.contextmenu_item_orderhistory_mua:
	//
	// PlaceOrder.symbolOrder = listLichSuLenh.get(selectedItemPosition)
	// .getSymbol();
	// PlaceOrder.sideOrder = "NB";
	// PlaceOrder.priceOrder = StringConst.EMPTY;
	// PlaceOrder.optionOrder = 1;
	// ((MainActivity) getActivity()).displayFragment(PlaceOrder.class
	// .getName());
	// break;
	// case R.id.contextmenu_item_orderhistory_ban:
	// PlaceOrder.symbolOrder = listLichSuLenh.get(selectedItemPosition)
	// .getSymbol();
	// PlaceOrder.sideOrder = "NS";
	// PlaceOrder.priceOrder = StringConst.EMPTY;
	// PlaceOrder.optionOrder = 1;
	// ((MainActivity) getActivity()).displayFragment(PlaceOrder.class
	// .getName());
	// break;
	// default:
	// break;
	// }
	// return super.onContextItemSelected(item);
	// }

	private void CallPastOrder() {
		isLoading();

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(getStringResource(R.string.controller_PastOrders));
		}
		{
			list_key.add("Afacctno");
			list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
		}
		{
			list_key.add("fromdate");
			list_value.add(edt_datestart.toString());
		}
		{
			list_key.add("todate");
			list_value.add(edt_dateend.toString());
		}

		StaticObjectManager.connectServer.callHttpPostService(PASTORDER, this,
				list_key, list_value);

	}

	private void isLoaded() {
	}

	private void isLoading() {
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		edt_datestart.resetDate();
		edt_dateend.resetDate();
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case PASTORDER:
			if (rObj.obj != null) {
				listLichSuLenh.clear();
				listLichSuLenh.addAll((List<LichSuLenh_Item>) rObj.obj);
				adapterLichSuLenh.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
		isLoaded();
	}

	@Override
	protected void processErrorOther(String key, ResultObj rObj) {
		super.processErrorOther(key, rObj);
		switch (key) {
		case PASTORDER:
			listLichSuLenh.clear();
			adapterLichSuLenh.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}

	@Override
	protected void processNull(String key) {

		super.processNull(key);
		isLoaded();
	}

}
