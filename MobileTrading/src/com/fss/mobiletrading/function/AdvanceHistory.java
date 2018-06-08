package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
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

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.LichSuUT_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.LichSuUT_Item;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;

public class AdvanceHistory extends AbstractFragment {
	public final String ADVANCEHISTORY = "AdvanceHistoryService";
	LichSuUT_Adapter adapterLichSuUT;
	Button btn_Search;
	DatePickerDialog dateEndPic;
	DatePickerDialog dateStartPic;
	EditText edt_dateend;
	EditText edt_datestart;
	List<LichSuUT_Item> listLichSuUT;
	ListView lv_lichsuungtruoc;
	TextView tv_loading;

	public static AdvanceHistory newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		AdvanceHistory self = new AdvanceHistory();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.AdvancedHistory);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.lichsu_ungtruoc,
				paramViewGroup, false);
		this.edt_datestart = ((EditText) localView
				.findViewById(R.id.edt_lichsuungtruoc_TuNgay));
		this.edt_dateend = ((EditText) localView
				.findViewById(R.id.edt_lichsuungtruoc_DenNgay));
		this.btn_Search = ((Button) localView
				.findViewById(R.id.btn_lichsuungtruoc_Search));
		this.lv_lichsuungtruoc = ((ListView) localView
				.findViewById(R.id.listview_lichsuungtruoc));
		this.tv_loading = ((TextView) localView
				.findViewById(R.id.text_lichsuut_loading));
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
				CallAdvanceHistory();
			}
		});
		return localView;
	}

	private void init() {
		this.listLichSuUT = new ArrayList<LichSuUT_Item>();
		this.dateStartPic = Common.createDialogChonDate(getActivity(),
				this.edt_datestart);
		this.dateEndPic = Common.createDialogChonDate(getActivity(),
				this.edt_dateend);
		this.edt_datestart.setKeyListener(null);
		this.edt_dateend.setKeyListener(null);
		this.adapterLichSuUT = new LichSuUT_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listLichSuUT);
		this.lv_lichsuungtruoc.setAdapter(this.adapterLichSuUT);
	}

	private void CallAdvanceHistory() {
		isLoading();
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_AdvanceHistory));
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

		StaticObjectManager.connectServer.callHttpPostService(ADVANCEHISTORY,
				this, list_key, list_value);
	}

	private void isLoaded() {
		this.tv_loading.setVisibility(TextView.GONE);
	}

	private void isLoading() {
		this.tv_loading.setVisibility(TextView.VISIBLE);
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		switch (key) {
		case ADVANCEHISTORY:
			if (rObj.obj != null) {
				this.listLichSuUT.clear();
				this.listLichSuUT.addAll((List<LichSuUT_Item>) rObj.obj);
				this.adapterLichSuUT.notifyDataSetChanged();
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
 * com.fss.fragment.LichSuUT JD-Core Version: 0.6.0
 */