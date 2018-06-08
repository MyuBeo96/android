package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.DanhSachCanhBao_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.DanhSachCanhBaoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;

public class PriceAlert extends AbstractFragment {
	final String ADDALERTPRICESUBMIT = "SuccessService#1";
	final String GETALLALERT_PRICE = "GetAllAlertPriceService";
	final String REMOVEALERT_PRICE = "SuccessService#2";

	int DotBienTrong;
	int KLDotBien;
	ArrayAdapter<String> adapterAlert;
	ArrayAdapter<String> adapterDotBienTrong;
	ArrayAdapter<String> adapterKLDotBien;
	DanhSachCanhBao_Adapter adapterListView;
	Button btn_CapNhat;
	Button btn_giacaonhat;
	Button btn_giathapnhat;
	CheckBox checkSendEmail;
	EditText edt_Symbols;
	int gia;
	String giaBtn;
	String[] listAlert;
	String[] listDotBienTrong;
	String[] listKLDotBien;
	List<DanhSachCanhBaoItem> listListView;
	ListView lv_AlertGia;
	Spinner spn_alert;
	Spinner spn_dotbientrong;
	Spinner spn_kldotbien;
	TextView tv_Desc;

	public static PriceAlert newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		PriceAlert self = new PriceAlert();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.SmartAlert);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.alert_dotbiengia,
				paramViewGroup, false);
		this.spn_alert = ((Spinner) localView.findViewById(R.id.spn_alertgia));
		this.spn_dotbientrong = ((Spinner) localView
				.findViewById(R.id.spn_alertgia_DotBienTrong));
		this.spn_kldotbien = ((Spinner) localView
				.findViewById(R.id.spn_alertgia_KLDotBien));
		this.btn_giacaonhat = ((Button) localView
				.findViewById(R.id.btn_alertgia_GiaCaoNhat));
		this.btn_giathapnhat = ((Button) localView
				.findViewById(R.id.btn_alertgia_GiaThapNhat));
		this.tv_Desc = ((TextView) localView
				.findViewById(R.id.text_alertgia_Desc));
		this.btn_CapNhat = ((Button) localView
				.findViewById(R.id.btn_alertgia_CapNhat));
		this.edt_Symbols = ((EditText) localView
				.findViewById(R.id.edt_alertgia_symbols));
		this.checkSendEmail = ((CheckBox) localView
				.findViewById(R.id.checkBox_alertgia_SendEmail));
		this.lv_AlertGia = ((ListView) localView
				.findViewById(R.id.listview_alertgia));
		Common.setupUI(localView.findViewById(R.id.alert_dotbiengia),
				getActivity());
		initialise();
		initialiseListener();
		return localView;
	}

	private void initialise() {
		this.listAlert = getResources().getStringArray(R.array.listAlert);
		this.adapterAlert = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listAlert);
		this.adapterAlert.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_alert.setAdapter(this.adapterAlert);
		this.listDotBienTrong = getResources().getStringArray(
				R.array.DBGia_DotBienTrong);
		this.adapterDotBienTrong = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listDotBienTrong);
		this.adapterDotBienTrong
				.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_dotbientrong.setAdapter(this.adapterDotBienTrong);
		String[] arrayOfString = getResources().getStringArray(
				R.array.DBGia_KhoiLuongDB);
		this.listDotBienTrong = arrayOfString;
		this.listKLDotBien = arrayOfString;
		this.adapterKLDotBien = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listKLDotBien);
		this.adapterKLDotBien
				.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_kldotbien.setAdapter(this.adapterKLDotBien);
		this.listListView = new ArrayList<DanhSachCanhBaoItem>();
		this.adapterListView = new DanhSachCanhBao_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listListView);
		this.lv_AlertGia.setAdapter(this.adapterListView);
		this.spn_alert.setEnabled(false);
	}

	private void initialiseListener() {
		this.spn_dotbientrong
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						DotBienTrong = position;
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		this.spn_kldotbien
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						KLDotBien = position;

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		this.btn_giacaonhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gia = 0;
			}
		});
		this.btn_giathapnhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gia = 1;
			}
		});
		this.btn_CapNhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDescription();
				CallAddAlertPriceSubmit();
			}
		});
		this.lv_AlertGia.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		this.lv_AlertGia
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						CallRemoveAlert_Price(listListView.get(position).id);
						return false;
					}
				});
	}

	public void onResume() {
		super.onResume();
		this.btn_giacaonhat.setActivated(true);
		this.giaBtn = this.btn_giacaonhat.getText().toString();
		CallGetALLAlert_Price();
	}

	private void CallAddAlertPriceSubmit() {
		if ((this.edt_Symbols.getText() != null)
				&& (this.edt_Symbols.getText().toString().length() > 0)) {
			ArrayList<String> list_key = new ArrayList<String>();
			ArrayList<String> list_value = new ArrayList<String>();
			{
				list_key.add("giang");
				list_value
						.add(getStringResource(R.string.controller_AddAlertPriceSubmit));
			}
			{
				list_key.add("pv_PERIODTIME");
				list_value.add(String.valueOf(this.spn_dotbientrong
						.getSelectedItemPosition()));
			}
			{
				list_key.add("pv_QTTY");
				list_value.add(String.valueOf(this.spn_kldotbien
						.getSelectedItemPosition()));
			}
			{
				list_key.add("pv_SYMBOLS");
				list_value.add(this.edt_Symbols.getText().toString());
			}
			{
				list_key.add("pv_SYMBOLSINDAY");
				list_value.add(this.edt_Symbols.getText().toString());
			}
			{
				list_key.add("pv_ISEMAIL");
				if (this.checkSendEmail.isChecked()) {
					list_value.add("Y");

				} else {
					list_value.add("N");
				}
			}
			{
				list_key.add("pv_TREND");
				list_value.add(String.valueOf(this.gia));
			}
			StaticObjectManager.connectServer.callHttpPostService(
					ADDALERTPRICESUBMIT, this, list_key, list_value);
		} else {
			showDialogMessage(getStringResource(R.string.thong_bao),
					getStringResource(R.string.alert_taocanhbaothatbai));
		}
	}

	private void CallGetALLAlert_Price() {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		list_key.add("giang");
		list_value.add(new String(
				getStringResource(R.string.controller_GetALLAlert_Price)));
		StaticObjectManager.connectServer.callHttpPostService(
				GETALLALERT_PRICE, this, list_key, list_value);
	}

	private void CallRemoveAlert_Price(String paramString) {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("giang");
			list_value
					.add(getStringResource(R.string.controller_RemoveAlert_Price));
		}
		{
			list_key.add("id");
			list_value.add(paramString);
		}
		StaticObjectManager.connectServer.callHttpPostService(
				REMOVEALERT_PRICE, this, list_key, list_value);
	}

	private void setDescription() {
		String str = getResources()
				.getString(R.string.dotbienkhoiluong_lbl_Notice)
				.replace("ss1", this.spn_kldotbien.getSelectedItem().toString())
				.replace("ss3",
						this.spn_dotbientrong.getSelectedItem().toString())
				.replace("ss2", this.giaBtn);
		this.tv_Desc.setText(str);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		switch (key) {
		case ADDALERTPRICESUBMIT:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			CallGetALLAlert_Price();
			break;
		case GETALLALERT_PRICE:
			if (rObj.obj != null) {
				this.listListView.clear();
				this.listListView.addAll((List<DanhSachCanhBaoItem>) rObj.obj);
				this.adapterListView.notifyDataSetChanged();
			}
			break;
		case REMOVEALERT_PRICE:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			CallGetALLAlert_Price();
			break;

		default:
			break;
		}
	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.AlertDBGia JD-Core Version: 0.6.0
 */