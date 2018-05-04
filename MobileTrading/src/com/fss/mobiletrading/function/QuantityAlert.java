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

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.DanhSachCanhBao_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.DanhSachCanhBaoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;

public class QuantityAlert extends AbstractFragment {
	final String ADDALERTQUANTITYSUBMIT = "SuccessService#1";
	final String GETALLALERT_QUANTITY = "GetAllAlertQuantityService";
	final String REMOVEALERT_QUANTITY = "SuccessService#2";

	int DotBienTrong;
	int KLDotBien;
	int TyLeDotBien;
	ArrayAdapter<String> adapterBoLocCP;
	ArrayAdapter<String> adapterDotBienTrong;
	ArrayAdapter<String> adapterKLDotBien;
	DanhSachCanhBao_Adapter adapterListView;
	ArrayAdapter<String> adapterTyLeDotBien;
	Button btn_CapNhat;
	CheckBox checkSendEmail;
	EditText edt_Symbols;
	String[] listBoLocCP;
	String[] listDotBienTrong;
	String[] listKLDotBien;
	List<DanhSachCanhBaoItem> listListView;
	String[] listTyLeDotBien;
	ListView lv_AlertKhoiLuong;
	Spinner spn_alert;
	Spinner spn_dotbientrong;
	Spinner spn_kldotbien;
	Spinner spn_tyledotbien;
	TextView tv_Desc;

	public static QuantityAlert newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		QuantityAlert self = new QuantityAlert();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.SmartAlert);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(
				R.layout.alert_dotbienkhoiluong, paramViewGroup, false);
		this.spn_alert = ((Spinner) localView
				.findViewById(R.id.spn_alertkhoiluong));
		this.spn_dotbientrong = ((Spinner) localView
				.findViewById(R.id.spn_alertkhoiluong_DotBienTrong));
		this.spn_kldotbien = ((Spinner) localView
				.findViewById(R.id.spn_alertkhoiluong_KLDotBien));
		this.spn_tyledotbien = ((Spinner) localView
				.findViewById(R.id.spn_alertkhoiluong_TyLeDB));
		this.btn_CapNhat = ((Button) localView
				.findViewById(R.id.btn_alertkhoiluong_CapNhat));
		this.tv_Desc = ((TextView) localView
				.findViewById(R.id.text_alertkhoiluong_Desc));
		this.edt_Symbols = ((EditText) localView
				.findViewById(R.id.edt_alertkhoiluong_symbols));
		this.checkSendEmail = ((CheckBox) localView
				.findViewById(R.id.checkBox_alertkhoiluong_SendEmail));
		this.lv_AlertKhoiLuong = ((ListView) localView
				.findViewById(R.id.listview_alertkhoiluong));
		Common.setupUI(localView.findViewById(R.id.alert_dotbienkhoiluong),
				getActivity());
		initialise();
		initialiseListener();
		return localView;
	}

	private void initialise() {
		this.listBoLocCP = getResources().getStringArray(R.array.listBoLocCP);
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
				R.array.DBKhoiLuong_TyLeDotBien);
		this.adapterTyLeDotBien = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listTyLeDotBien);
		this.adapterTyLeDotBien
				.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_tyledotbien.setAdapter(this.adapterTyLeDotBien);
		this.listListView = new ArrayList<DanhSachCanhBaoItem>();
		this.adapterListView = new DanhSachCanhBao_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listListView);
		this.lv_AlertKhoiLuong.setAdapter(this.adapterListView);
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
		this.btn_CapNhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDescription();
				CallAddAlertQuantitySubmit();

			}
		});
		this.lv_AlertKhoiLuong.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		this.lv_AlertKhoiLuong
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						CallRemoveAlert_Quantity(listListView.get(position).id);
						return false;
					}
				});
	}

	public void onResume() {
		super.onResume();
		CallGetALLAlert_Quantity();
	}

	private void CallAddAlertQuantitySubmit() {

		if ((this.edt_Symbols.getText() != null)
				&& (this.edt_Symbols.getText().toString().length() > 0)) {
			ArrayList<String> list_key = new ArrayList<String>();
			ArrayList<String> list_value = new ArrayList<String>();

			{
				list_key.add("giang");
				list_value
						.add(getStringResource(R.string.controller_AddAlertQuantitySubmit));
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
					ADDALERTQUANTITYSUBMIT, this, list_key, list_value);
		} else {
			showDialogMessage(getStringResource(R.string.thong_bao),
					getStringResource(R.string.alert_taocanhbaothatbai));
		}
	}

	private void CallGetALLAlert_Quantity() {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("giang");
			list_value
					.add(new String(
							getStringResource(R.string.controller_GetALLAlert_Quantity)));
		}
		StaticObjectManager.connectServer.callHttpPostService(
				GETALLALERT_QUANTITY, this, list_key, list_value);
	}

	private void CallRemoveAlert_Quantity(String paramString) {

		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("giang");
			list_value
					.add(getStringResource(R.string.controller_RemoveAlert_Quantity));
		}
		{
			list_key.add("id");
			list_value.add(paramString);
		}
		StaticObjectManager.connectServer.callHttpPostService(
				REMOVEALERT_QUANTITY, this, list_key, list_value);
	}

	private void setDescription() {
		String str = getResources()
				.getString(R.string.dotbienkhoiluong_lbl_Notice)
				.replace("ss1", this.spn_kldotbien.getSelectedItem().toString())
				.replace("ss2",
						this.spn_tyledotbien.getSelectedItem().toString())
				.replace("ss3",
						this.spn_dotbientrong.getSelectedItem().toString());
		this.tv_Desc.setText(str);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		switch (key) {
		case GETALLALERT_QUANTITY:
			if (rObj.obj != null) {
				this.listListView.clear();
				this.listListView.addAll((List<DanhSachCanhBaoItem>) rObj.obj);
				this.adapterListView.notifyDataSetChanged();
			}
			break;
		case ADDALERTQUANTITYSUBMIT:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			CallGetALLAlert_Quantity();
			break;
		case REMOVEALERT_QUANTITY:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			CallGetALLAlert_Quantity();
			break;

		default:
			break;
		}
	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.AlertDBKhoiLuong JD-Core Version: 0.6.0
 */