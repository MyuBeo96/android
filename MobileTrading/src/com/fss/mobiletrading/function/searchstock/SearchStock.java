package com.fss.mobiletrading.function.searchstock;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.fss.designcontrol.SymbolEdittext;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.design.InputDate;
import com.fscuat.mobiletrading.design.InputDate.OnChangeTextDateListener;

import java.util.ArrayList;
import java.util.List;

public class SearchStock extends AbstractFragment {
	final String STOCKINFO = "StockInfoService#STOCKINFO";

	SymbolEdittext edt_symbolinput;
	InputDate edt_datestart;
	InputDate edt_dateend;
	Button btn_search;
	ListView lv_searchstock;

	List<SearchStockItem> list_SearchStockItem;
	SearchStock_Adapter adapter_SearchStock;

	public static SearchStock newInstance(MainActivity mainActivity) {
		SearchStock searchStock = new SearchStock();
		searchStock.mainActivity = mainActivity;
		searchStock.TITLE = mainActivity
				.getStringResource(R.string.SearchStock);
		return searchStock;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.search_stock, container, false);
		edt_symbolinput = (SymbolEdittext) view
				.findViewById(R.id.edt_searchstock_search);
		edt_symbolinput.setSymbolEdittextType(SymbolEdittext.SymbolEdittextType.Single);
		edt_datestart = (InputDate) view
				.findViewById(R.id.edt_searchstock_fromdate);
		edt_dateend = (InputDate) view
				.findViewById(R.id.edt_searchstock_todate);
		btn_search = (Button) view.findViewById(R.id.btn_searchstock_search);
		lv_searchstock = (ListView) view
				.findViewById(R.id.listview_searchstock);
		Common.setupUI(view.findViewById(R.id.search_stock), mainActivity);
		initialise();
		return view;
	}

	private void initialise() {

		initialiseData();
		initialiseListener();
	}

	private void initialiseData() {

		if (list_SearchStockItem == null) {
			list_SearchStockItem = new ArrayList<SearchStockItem>();
		}
		list_SearchStockItem.clear();
		if (adapter_SearchStock == null) {
			adapter_SearchStock = new SearchStock_Adapter(mainActivity,
					android.R.layout.simple_list_item_1, list_SearchStockItem);
		}
		adapter_SearchStock.notifyDataSetChanged();
		lv_searchstock.setAdapter(adapter_SearchStock);
		edt_datestart.setLabel(getStringResource(R.string.TuNgay));
		edt_dateend.setLabel(getStringResource(R.string.DenNgay));
	}

	private void initialiseListener() {
		edt_symbolinput.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.onTouchEvent(event);
				mainActivity.imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				return true;
			}
		});
		edt_datestart
				.setOnChangeTextDateListener(new OnChangeTextDateListener() {

					@Override
					public void onChange(String date) {
						btn_search.performClick();
					}
				});
		edt_dateend.setOnChangeTextDateListener(new OnChangeTextDateListener() {

			@Override
			public void onChange(String date) {
				btn_search.performClick();
			}
		});
		edt_symbolinput.setInputType(InputType.TYPE_NULL);
		edt_symbolinput.setRawInputType(InputType.TYPE_CLASS_TEXT);
		edt_symbolinput.setTextIsSelectable(true);
		edt_symbolinput.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus) {
					mainActivity.showKBoardHook(true, edt_symbolinput, StaticObjectManager.getListAllStock());
					mainActivity.showKBoardSymbol(true, edt_symbolinput);
				} else {
					mainActivity.showKBoardSymbol(false, null);
					mainActivity.showKBoardHook(false, null, null);
					btn_search.performClick();
				}
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (edt_symbolinput != null && edt_datestart != null
						&& edt_dateend != null) {
					CallStockInfo(edt_symbolinput.getText().toString(),
							edt_datestart.toString(), edt_dateend.toString());
				}
			}
		});
	}
	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoActionMenu();
	}
	@Override
	public void onResume() {

		super.onResume();
		edt_symbolinput.setText(StringConst.EMPTY);
		edt_datestart.resetDate();
		edt_dateend.resetDate();
	}

	private void CallStockInfo(String pr_symbol, String pr_fromdate,
			String pr_todate) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(getStringResource(R.string.controller_StockInfo));
		}
		{
			list_key.add("Afacctno");
			list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
		}
		{
			list_key.add("symbol");
			list_value.add(pr_symbol);
		}
		{
			list_key.add("fromdate");
			list_value.add(pr_fromdate);
		}

		{
			list_key.add("todate");
			list_value.add(pr_todate);
		}
		StaticObjectManager.connectServer.callHttpPostService(STOCKINFO, this,
				list_key, list_value);
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case STOCKINFO:
			if (rObj.obj != null) {
				list_SearchStockItem.clear();
				list_SearchStockItem.addAll((List<SearchStockItem>) rObj.obj);
				adapter_SearchStock.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void processErrorOther(String key, ResultObj rObj) {
		super.processErrorOther(key, rObj);
		switch (key) {
		case STOCKINFO:
			list_SearchStockItem.clear();
			adapter_SearchStock.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}

}
