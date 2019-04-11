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

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.LichSuDKQM_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.LichSuDKQM_Item;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;

public class RightOffRegisterHistory extends AbstractFragment {
	public final String ADDSHARESSTATEMENT = "AddSharesStatementService";
	LichSuDKQM_Adapter adapterLichSuDKQM;
	Button btn_Search;
	int currentController;
	DatePickerDialog dateEndPic;
	DatePickerDialog dateStartPic;
	EditText edt_dateend;
	EditText edt_datestart;
	List<LichSuDKQM_Item> listLichSuDKQM;
	ListView lv_LichSuDKQM;
	TextView tv_loading;

	public static RightOffRegisterHistory newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		RightOffRegisterHistory self = new RightOffRegisterHistory();
		self.mainActivity = mActivity;
		self.TITLE = mActivity
				.getStringResource(R.string.RightOffRegisterHistory);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.lichsu_dkqm,
				paramViewGroup, false);
		this.edt_datestart = ((EditText) localView
				.findViewById(R.id.edt_lichsudkqm_TuNgay));
		this.edt_dateend = ((EditText) localView
				.findViewById(R.id.edt_lichsudkqm_DenNgay));
		this.btn_Search = ((Button) localView
				.findViewById(R.id.btn_lichsudkqm_Search));
		this.lv_LichSuDKQM = ((ListView) localView
				.findViewById(R.id.listview_lichsudkqm));
		this.tv_loading = ((TextView) localView
				.findViewById(R.id.text_lichsudkqm_loading));
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
				CallAddSharesStatement();

			}
		});
		return localView;
	}

	private void init() {
		this.listLichSuDKQM = new ArrayList<LichSuDKQM_Item>();
		this.dateStartPic = Common.createDialogChonDate(getActivity(),
				this.edt_datestart);
		this.dateEndPic = Common.createDialogChonDate(getActivity(),
				this.edt_dateend);
		this.edt_datestart.setKeyListener(null);
		this.edt_dateend.setKeyListener(null);
		this.adapterLichSuDKQM = new LichSuDKQM_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listLichSuDKQM);
		this.lv_LichSuDKQM.setAdapter(this.adapterLichSuDKQM);
	}

	private void CallAddSharesStatement() {
		isLoading();
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_AddSharesStatement));
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
				ADDSHARESSTATEMENT, this, list_key, list_value);
	}

	private void isLoaded() {
		this.tv_loading.setVisibility(TextView.GONE);
	}

	private void isLoading() {
		this.tv_loading.setVisibility(TextView.VISIBLE);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		switch (key) {
		case ADDSHARESSTATEMENT:
			if (rObj.obj != null) {
				this.listLichSuDKQM.clear();
				this.listLichSuDKQM.addAll((List<LichSuDKQM_Item>) rObj.obj);
				this.adapterLichSuDKQM.notifyDataSetChanged();
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
 * com.fss.fragment.LichSuDKQM JD-Core Version: 0.6.0
 */