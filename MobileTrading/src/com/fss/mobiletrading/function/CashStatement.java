package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.CashStatementAdapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.CashStatementItem;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.design.InputDate;
import com.fscuat.mobiletrading.design.InputDate.OnChangeTextDateListener;

public class CashStatement extends AbstractFragment {
	final String CASHSTATEMENT = "CashStatementService#CASHSTATEMENT";
	CashStatementAdapter adapter_SaoKeTien;
	Button btn_Search;
	InputDate edt_dateend;
	InputDate edt_datestart;
	List<CashStatementItem> listSaoKeTien;
	ListView lv_SaoKeTien;
	TextView tv_loading;

	public static CashStatement newInstance(MainActivity mActivity) {

		CashStatement self = new CashStatement();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.CashStatement);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.cashstatement,
				paramViewGroup, false);
		edt_datestart = ((InputDate) localView
				.findViewById(R.id.date_cashstatement_fromdate));
		edt_dateend = ((InputDate) localView
				.findViewById(R.id.date_cashstatement_todate));
		btn_Search = ((Button) localView
				.findViewById(R.id.btn_SaoKeTien_Search));
		lv_SaoKeTien = ((ListView) localView
				.findViewById(R.id.listview_SaoKeTien));
		tv_loading = ((TextView) localView
				.findViewById(R.id.text_saoketien_loading));
		init();
		initListener();
		return localView;
	}

	private void init() {
		if (listSaoKeTien == null) {
			listSaoKeTien = new ArrayList<CashStatementItem>();
		} else {
			listSaoKeTien.clear();
		}
		if (adapter_SaoKeTien == null) {
			adapter_SaoKeTien = new CashStatementAdapter(getActivity(),
					android.R.layout.simple_list_item_1, this.listSaoKeTien);
		} else {
			adapter_SaoKeTien.notifyDataSetChanged();
		}
		lv_SaoKeTien.setAdapter(this.adapter_SaoKeTien);
		edt_datestart.setLabel(getStringResource(R.string.TuNgay));
		edt_dateend.setLabel(getStringResource(R.string.DenNgay));
	}

	private void initListener() {
		edt_datestart
				.setOnChangeTextDateListener(new OnChangeTextDateListener() {
					@Override
					public void onChange(String date) {
						CallCashStatement();
					}
				});
		edt_dateend.setOnChangeTextDateListener(new OnChangeTextDateListener() {

			@Override
			public void onChange(String date) {
				CallCashStatement();
			}
		});
		btn_Search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CallCashStatement();
			}
		});
		lv_SaoKeTien.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter_SaoKeTien.setExpandPosition(position);
				adapter_SaoKeTien.notifyDataSetChanged();
			}
		});
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

	protected void CallCashStatement() {
		isLoading();
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_CashStatement));
		}
		{
			list_key.add("AfAcctno");
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

		StaticObjectManager.connectServer.callHttpPostService(CASHSTATEMENT,
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
			case CASHSTATEMENT:
				if (rObj.obj != null) {
					listSaoKeTien.clear();
					listSaoKeTien.addAll((List<CashStatementItem>) rObj.obj);
					if (listSaoKeTien.size() > 0) {

						// thêm số dư đầu kỳ và số dư cuối kỳ vào list
						CashStatementItem beginBalance = new CashStatementItem();
						CashStatementItem endBalance = new CashStatementItem();
						if (listSaoKeTien.size() > 0) {
							beginBalance.setEndbalance(listSaoKeTien.get(0)
									.getBeginningbalance());
						}
						if (listSaoKeTien.size() > 0) {
							endBalance.setEndbalance(listSaoKeTien.get(
									listSaoKeTien.size() - 1).getEndbalance());
						}
						beginBalance.setDate(getStringResource(R.string.SoDuDauKy));
						endBalance.setDate(getStringResource(R.string.SoDuCuoiKy));

						listSaoKeTien.add(0, beginBalance);
						listSaoKeTien.add(endBalance);
					}

					// if (listSaoKeTien.size() > 0) {
					// tv_beginbalance.setText(Common.formatAmount(listSaoKeTien
					// .get(0).getBeginningbalance()));
					// }
					// if (listSaoKeTien.size() > 1) {
					// tv_endbalance.setText(Common.formatAmount(listSaoKeTien
					// .get(listSaoKeTien.size() - 1).getEndbalance()));
					// }

					adapter_SaoKeTien.notifyDataSetChanged();
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
		case CASHSTATEMENT:
			listSaoKeTien.clear();
			adapter_SaoKeTien.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}

	@Override
	protected void isReceivedResponse(String key) {
		super.isReceivedResponse(key);
		isLoaded();
	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.SaoKeTien JD-Core Version: 0.6.0
 */