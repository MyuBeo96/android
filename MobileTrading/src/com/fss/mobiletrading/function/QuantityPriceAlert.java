package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.DanhSachCanhBao_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.DanhSachCanhBaoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;

public class QuantityPriceAlert extends AbstractFragment {
	final String ADDALERTPRICEQUANTITYSUBMIT = "SuccessService#1";
	final String GETALLALERT_PRICE_QUANTITY = "GetAllAlertPriceQuantityService";
	final String REMOVEALERT_PRICE_QUANTITY = "SuccessService#2";

	int DotBienTrong;
	int GiaDotBien;
	int KLDotBien;
	int TyLeDotBien;
	ArrayAdapter<String> adapterBoLocCP;
	ArrayAdapter<String> adapterDotBienTrong;
	ArrayAdapter<String> adapterGiaDotBien;
	ArrayAdapter<String> adapterKLDotBien;
	DanhSachCanhBao_Adapter adapterListView;
	ArrayAdapter<String> adapterTyLeDotBien;
	Button btn_CapNhat;
	CheckBox checkSendEmail;
	EditText edt_Symbols;
	String[] listBoLocCP;
	String[] listDotBienTrong;
	String[] listGiaDotBien;
	String[] listKLDotBien;
	List<DanhSachCanhBaoItem> listListView;
	String[] listTyLeDotBien;
	ListView lv_AlertGiaKhoiLuong;
	Spinner spn_alert;
	Spinner spn_dotbientrong;
	Spinner spn_giadotbien;
	Spinner spn_kldotbien;
	Spinner spn_tyledotbien;
	TextView tv_Desc;

	public static QuantityPriceAlert newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		QuantityPriceAlert self = new QuantityPriceAlert();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.SmartAlert);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(
				R.layout.alert_dotbiengiakhoiluong, paramViewGroup, false);

		this.lv_AlertGiaKhoiLuong = ((ListView) localView
				.findViewById(R.id.listview_alertgiakhoiluong));
		this.spn_alert = ((Spinner) localView
				.findViewById(R.id.spn_alertgiakhoiluong));
		this.spn_dotbientrong = ((Spinner) localView
				.findViewById(R.id.spn_alertgiakhoiluong_DotBienTrong));
		this.spn_kldotbien = ((Spinner) localView
				.findViewById(R.id.spn_alertgiakhoiluong_KLDotBien));
		this.spn_giadotbien = ((Spinner) localView
				.findViewById(R.id.spn_alertgiakhoiluong_GiaDotBien));
		this.spn_tyledotbien = ((Spinner) localView
				.findViewById(R.id.spn_alertgiakhoiluong_TyLeDB));
		this.btn_CapNhat = ((Button) localView
				.findViewById(R.id.btn_alertgiakhoiluong_CapNhat));
		this.tv_Desc = ((TextView) localView
				.findViewById(R.id.text_alertgiakhoiluong_Desc));
		edt_Symbols = (EditText) localView
				.findViewById(R.id.edt_alertgiakhoiluong_symbols);
		checkSendEmail = (CheckBox) localView
				.findViewById(R.id.checkBox_alertgiakhoiluong_SendEmail);
		Common.setupUI(localView.findViewById(R.id.alert_dotbiengiakhoiluong),
				getActivity());
		initialise();
		initialiseListener();
		return localView;
	}

	private void initialise() {
		this.listBoLocCP = getResources().getStringArray(R.array.listAlert);
		this.adapterBoLocCP = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listBoLocCP);
		this.adapterBoLocCP.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_alert.setAdapter(this.adapterBoLocCP);
		this.listDotBienTrong = getResources().getStringArray(
				R.array.DBKhoiLuong_DotBienTrong);
		this.adapterDotBienTrong = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listDotBienTrong);
		this.adapterDotBienTrong
				.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_dotbientrong.setAdapter(this.adapterDotBienTrong);
		this.listKLDotBien = getResources().getStringArray(
				R.array.DBKhoiLuong_KhoiLuongDB);
		this.adapterKLDotBien = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listKLDotBien);
		this.adapterKLDotBien
				.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_kldotbien.setAdapter(this.adapterKLDotBien);
		this.listTyLeDotBien = getResources().getStringArray(
				R.array.DBGiaGiaKhoiLuong_TyLeDotBien);
		this.adapterTyLeDotBien = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listTyLeDotBien);
		this.adapterTyLeDotBien
				.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_tyledotbien.setAdapter(this.adapterTyLeDotBien);
		this.listGiaDotBien = getResources().getStringArray(
				R.array.DBGiaGiaKhoiLuong_GiaDotBien);
		this.adapterGiaDotBien = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listGiaDotBien);
		this.adapterGiaDotBien
				.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_giadotbien.setAdapter(this.adapterGiaDotBien);
		this.listListView = new ArrayList<DanhSachCanhBaoItem>();
		this.adapterListView = new DanhSachCanhBao_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listListView);
		this.lv_AlertGiaKhoiLuong.setAdapter(this.adapterListView);
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
		this.spn_tyledotbien
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						TyLeDotBien = position;
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		this.spn_giadotbien
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						GiaDotBien = position;
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		this.btn_CapNhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDescription();
				CallAddAlertPriceQuantitySubmit();
				;
			}
		});
		this.lv_AlertGiaKhoiLuong.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});

		this.lv_AlertGiaKhoiLuong
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						CallRemoveAlert_Price_Quantity(listListView
								.get(position).id);
						return false;
					}
				});
	}

	public void onResume() {
		super.onResume();
		CallGetALLAlert_Price_Quantity();
	}

	private void CallAddAlertPriceQuantitySubmit() {

		if ((this.edt_Symbols.getText() != null)
				&& (this.edt_Symbols.getText().toString().length() > 0)) {
			ArrayList<String> list_key = new ArrayList<String>();
			ArrayList<String> list_value = new ArrayList<String>();
			{
				list_key.add("giang");
				list_value
						.add(getStringResource(R.string.controller_AddAlertPriceQuantitySubmit));
			}
			{
				list_key.add("pv_QTTY");
				list_value.add(String.valueOf(this.spn_kldotbien
						.getSelectedItemPosition()));
			}
			{
				list_key.add("pv_PERIODTIME");
				list_value.add(String.valueOf(this.spn_dotbientrong
						.getSelectedItemPosition()));
			}
			{
				list_key.add("pv_RATE");
				list_value.add(String.valueOf(this.spn_tyledotbien
						.getSelectedItemPosition()));
			}
			{
				list_key.add("pv_PRICE");
				list_value.add(String.valueOf(this.spn_giadotbien
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
			StaticObjectManager.connectServer.callHttpPostService(
					ADDALERTPRICEQUANTITYSUBMIT, this, list_key, list_value);
		} else {
			showDialogMessage(getStringResource(R.string.thong_bao),
					getStringResource(R.string.alert_taocanhbaothatbai));
		}
	}

	private void CallGetALLAlert_Price_Quantity() {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("giang");
			list_value
					.add(new String(
							getStringResource(R.string.controller_GetALLAlert_Price_Quantity)));
		}
		StaticObjectManager.connectServer.callHttpPostService(
				GETALLALERT_PRICE_QUANTITY, this, list_key, list_value);
	}

	private void CallRemoveAlert_Price_Quantity(String paramString) {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("giang");
			list_value
					.add(getStringResource(R.string.controller_RemoveAlert_Price_Quantity));
		}
		{
			list_key.add("id");
			list_value.add(paramString);
		}
		StaticObjectManager.connectServer.callHttpPostService(
				REMOVEALERT_PRICE_QUANTITY, this, list_key, list_value);
	}

	private void setDescription() {
		String str = getResources()
				.getString(R.string.dotbiengiakhoiluong_lbl_Notice)
				.replace("ss2", this.spn_kldotbien.getSelectedItem().toString())
				.replace("ss3",
						this.spn_tyledotbien.getSelectedItem().toString())
				.replace("ss4",
						this.spn_dotbientrong.getSelectedItem().toString())
				.replace("ss1",
						this.spn_giadotbien.getSelectedItem().toString());
		this.tv_Desc.setText(str);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		switch (key) {
		case ADDALERTPRICEQUANTITYSUBMIT:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			CallGetALLAlert_Price_Quantity();
			break;
		case GETALLALERT_PRICE_QUANTITY:
			if (rObj.obj != null) {
				this.listListView.clear();
				this.listListView.addAll((List<DanhSachCanhBaoItem>) rObj.obj);
				this.adapterListView.notifyDataSetChanged();
			}
			break;
		case REMOVEALERT_PRICE_QUANTITY:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			CallGetALLAlert_Price_Quantity();
			break;

		default:
			break;
		}
	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.AlertDBKhoiLuongGia JD-Core Version: 0.6.0
 */