package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.LichSuCT_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.LichSuCT_Item;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;

public class CashTransferHistory extends AbstractFragment {
	final String CASHTRANSFERSTATEMENT = "CashTransferStatementService";
	LichSuCT_Adapter adapter_LichSuCT;
	Button btn_Search;
	private DatePickerDialog dateEndPic;
	private DatePickerDialog dateStartPic;
	EditText edt_dateend;
	EditText edt_datestart;
	List<LichSuCT_Item> listLichSuCT;
	ListView lv_LichSuCT;
	TextView tv_loading;

	public static CashTransferHistory newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		CashTransferHistory self = new CashTransferHistory();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.CashHistory);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(
				R.layout.lichsu_chuyentien, paramViewGroup, false);
		this.edt_datestart = ((EditText) localView
				.findViewById(R.id.edt_LichSuCT_TuNgay));
		this.edt_dateend = ((EditText) localView
				.findViewById(R.id.edt_LichSuCT_DenNgay));
		this.btn_Search = ((Button) localView
				.findViewById(R.id.btn_LichSuCT_Search));
		this.lv_LichSuCT = ((ListView) localView
				.findViewById(R.id.listview_LichSuCT));
		this.tv_loading = ((TextView) localView
				.findViewById(R.id.text_lichsuct_loading));
		init();

		this.edt_datestart.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				dateStartPic
						.setTitle(getStringResource(R.string.TuNgay));
				dateStartPic.show();
				return false;
			}
		});
		this.edt_dateend.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				dateEndPic
						.setTitle(getStringResource(R.string.DenNgay));
				dateEndPic.show();
				return false;
			}
		});
		this.btn_Search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CallCashTransferStatement();
			}
		});
		return localView;
	}

	private void init() {
		this.listLichSuCT = new ArrayList<LichSuCT_Item>();
		this.dateStartPic = Common.createDialogChonDate(getActivity(),
				this.edt_datestart);
		this.dateEndPic = Common.createDialogChonDate(getActivity(),
				this.edt_dateend);
		this.edt_datestart.setKeyListener(null);
		this.edt_dateend.setKeyListener(null);
		this.adapter_LichSuCT = new LichSuCT_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listLichSuCT);
		this.lv_LichSuCT.setAdapter(this.adapter_LichSuCT);
	}

	private void isLoaded() {
		this.tv_loading.setVisibility(TextView.GONE);
	}

	private void isLoading() {
		this.tv_loading.setVisibility(TextView.VISIBLE);
	}

	protected void CallCashTransferStatement() {
		isLoading();

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_CashTransferStatement));
		}
		{
			list_key.add("Afacctno");
			list_value.add(StaticObjectManager.acctnoItem.ACCTNO);
		}
		{
			list_key.add("fromdate");
			list_value.add(edt_datestart.getText().toString());
		}
		{
			list_key.add("todate");
			list_value.add(edt_dateend.getText().toString());
		}

		StaticObjectManager.connectServer.callHttpPostService(
				CASHTRANSFERSTATEMENT, this, list_key, list_value);

	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		switch (key) {
		case CASHTRANSFERSTATEMENT:
			if (rObj.obj != null) {
				this.listLichSuCT.clear();
				this.listLichSuCT.addAll((List<LichSuCT_Item>) rObj.obj);
				this.adapter_LichSuCT.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
		isLoaded();
	}

	@Override
	protected void processNull(String key) {
		// TODO Auto-generated method stub
		super.processNull(key);
		isLoaded();
	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.LichSuCT JD-Core Version: 0.6.0
 */