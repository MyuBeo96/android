package com.fss.mobiletrading.function;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fss.mobiletrading.adapter.StockStatementAdapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.StockStatementItem;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.InputDate;
import com.fscuat.mobiletrading.design.InputDate.OnChangeTextDateListener;

import java.util.ArrayList;
import java.util.List;

public class StockStatement extends AbstractFragment {
	final String STOCKSTATEMENT = "StockStatementService";
	StockStatementAdapter adapter_SaoKeCK;
	Button btn_Search;
	InputDate edt_dateend;
	InputDate edt_datestart;
	List<StockStatementItem> listSaoKeCK;
	ListView lv_SaoKeCK;
	TextView tv_loading;

	public static StockStatement newInstance(MainActivity mActivity) {

		StockStatement self = new StockStatement();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.StockStatement);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View localView = inflater.inflate(R.layout.stockstatement, viewGroup,
				false);
		edt_datestart = ((InputDate) localView
				.findViewById(R.id.date_stocckstatement_fromdate));
		edt_dateend = ((InputDate) localView
				.findViewById(R.id.date_stockstatement_todate));
		btn_Search = ((Button) localView.findViewById(R.id.btn_SaoKeCK_Search));
		lv_SaoKeCK = ((ListView) localView.findViewById(R.id.listview_SaoKeCK));
		tv_loading = ((TextView) localView
				.findViewById(R.id.text_saokeck_loading));
		init();
		initListener();
		edt_datestart
				.setOnChangeTextDateListener(new OnChangeTextDateListener() {

					@Override
					public void onChange(String date) {
						CallStockStatement();
					}
				});
		edt_dateend.setOnChangeTextDateListener(new OnChangeTextDateListener() {

			@Override
			public void onChange(String date) {
				CallStockStatement();
			}
		});

		btn_Search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				CallStockStatement();
			}
		});
		return localView;
	}

	private void init() {

		if (listSaoKeCK == null) {
			listSaoKeCK = new ArrayList<StockStatementItem>();
		} else {
			listSaoKeCK.clear();
		}
		if (adapter_SaoKeCK == null) {
			adapter_SaoKeCK = new StockStatementAdapter(getActivity(),
					android.R.layout.simple_list_item_1, this.listSaoKeCK);
		} else {
			adapter_SaoKeCK.notifyDataSetChanged();
		}
		lv_SaoKeCK.setAdapter(this.adapter_SaoKeCK);
		edt_datestart.setLabel(getStringResource(R.string.TuNgay));
		edt_dateend.setLabel(getStringResource(R.string.DenNgay));
	}

	private void initListener() {
		if (DeviceProperties.isTablet) {

		} else {
			lv_SaoKeCK.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					adapter_SaoKeCK.setExpandPosition(position);
					adapter_SaoKeCK.notifyDataSetChanged();
					if (position == listSaoKeCK.size() - 1) {
						lv_SaoKeCK.setSelection(adapter_SaoKeCK.getCount() - 1);
					}
				}
			});
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		edt_datestart.resetDate();
		edt_dateend.resetDate();
	}

	private void isLoaded() {
		this.tv_loading.setVisibility(TextView.GONE);
	}

	private void isLoading() {
		this.tv_loading.setVisibility(TextView.VISIBLE);
	}

	protected void CallStockStatement() {
		isLoading();
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_StockStatement));
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

		StaticObjectManager.connectServer.callHttpPostService(STOCKSTATEMENT,
				this, list_key, list_value);
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		if (edt_datestart != null) {
			edt_datestart.resetDate();
		}
		if (edt_dateend != null) {
			edt_dateend.resetDate();
		}
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case STOCKSTATEMENT:
			if (rObj.obj != null) {
				this.listSaoKeCK.clear();
				this.listSaoKeCK.addAll((List<StockStatementItem>) rObj.obj);
				this.adapter_SaoKeCK.notifyDataSetChanged();
			}
			break;

		default:

			break;
		}
		isLoaded();
	}

	@Override
	protected void processNull(String key) {

		super.processNull(key);
		isLoaded();
	}

	@Override
	protected void processErrorOther(String key, ResultObj rObj) {
		super.processErrorOther(key, rObj);
		switch (key) {
		case STOCKSTATEMENT:
			listSaoKeCK.clear();
			adapter_SaoKeCK.notifyDataSetChanged();
			break;

		default:
			break;
		}
		isLoaded();
	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.SaoKeChungKhoan JD-Core Version: 0.6.0
 */