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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.DanhSachCanhBao_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.DanhSachCanhBaoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.design.RangeSeekBar;
import com.fscuat.mobiletrading.design.RangeSeekBarLayout;
import com.fscuat.mobiletrading.design.RangeSeekBar.OnRangeSeekBarChangeListener;

public class BasicAlert extends AbstractFragment {
	final String ADDALERTBASICSUBMIT = "SuccessService#1";
	final String GETALLALERT_BASIC = "GetAllAlertBasicService";
	final String REMOVEALERT_BASIC = "SuccessService#2";

	float Beta_from;
	float Beta_to;
	int EPS_from;
	int EPS_to;
	int GiaHT_from;
	int GiaHT_to;
	int PB_from;
	int PB_to;
	int PE_from;
	int PE_to;
	int ROA_from;
	int ROA_to;
	int ROE_from;
	int ROE_to;
	DanhSachCanhBao_Adapter adapterListView;
	Button btn_CapNhat;
	Button btn_CoBan;
	Button btn_HieuQua;
	Button btn_LuotSong;
	Button btn_MacDinh;
	CheckBox checkSendEmail;
	EditText edt_Symbols;
	List<DanhSachCanhBaoItem> listListView;
	ListView lv_BoLocCoBan;
	RangeSeekBarLayout rangeSeekBar_Beta;
	RangeSeekBarLayout rangeSeekBar_EPS;
	RangeSeekBarLayout rangeSeekBar_GiaHT;
	RangeSeekBarLayout rangeSeekBar_PB;
	RangeSeekBarLayout rangeSeekBar_PE;
	RangeSeekBarLayout rangeSeekBar_ROA;
	RangeSeekBarLayout rangeSeekBar_ROE;
	TextView tv_DescBeta;
	TextView tv_DescEPS;
	TextView tv_DescGiaHT;
	TextView tv_DescPB;
	TextView tv_DescPE;
	TextView tv_DescROA;
	TextView tv_DescROE;

	public static BasicAlert newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		BasicAlert self = new BasicAlert();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.SmartAlert);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {

		View localView = paramLayoutInflater.inflate(R.layout.alert_coban,
				paramViewGroup, false);
		this.rangeSeekBar_EPS = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_alert_coban_EPS));
		this.tv_DescEPS = ((TextView) localView
				.findViewById(R.id.text_alert_coban_DescEPS));
		this.rangeSeekBar_PE = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_alert_coban_PE));
		this.tv_DescPE = ((TextView) localView
				.findViewById(R.id.text_alert_coban_DescPE));
		this.rangeSeekBar_ROE = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_alert_coban_ROE));
		this.tv_DescROE = ((TextView) localView
				.findViewById(R.id.text_alert_coban_DescROE));
		this.rangeSeekBar_ROA = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_alert_coban_ROA));
		this.tv_DescROA = ((TextView) localView
				.findViewById(R.id.text_alert_coban_DescROA));
		this.rangeSeekBar_PB = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_alert_coban_PB));
		this.tv_DescPB = ((TextView) localView
				.findViewById(R.id.text_alert_coban_DescPB));
		this.rangeSeekBar_Beta = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_alert_coban_Beta));
		this.tv_DescBeta = ((TextView) localView
				.findViewById(R.id.text_alert_coban_DescBeta));
		this.rangeSeekBar_GiaHT = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_alert_coban_GiaHT));
		this.tv_DescGiaHT = ((TextView) localView
				.findViewById(R.id.text_alert_coban_DescGiaHT));

		this.btn_CapNhat = ((Button) localView
				.findViewById(R.id.btn_alert_coban_CapNhat));
		this.btn_MacDinh = ((Button) localView
				.findViewById(R.id.btn_alert_coban_Default));
		this.lv_BoLocCoBan = ((ListView) localView
				.findViewById(R.id.listview_alert_coban));
		this.btn_CoBan = ((Button) localView
				.findViewById(R.id.btn_alert_coban_dautucoban));
		this.btn_HieuQua = ((Button) localView
				.findViewById(R.id.btn_alert_coban_dautuhieuqua));
		this.btn_LuotSong = ((Button) localView
				.findViewById(R.id.btn_alert_coban_dautuluotsong));

		this.edt_Symbols = ((EditText) localView
				.findViewById(R.id.edt_alert_coban_symbols));
		this.checkSendEmail = ((CheckBox) localView
				.findViewById(R.id.checkBox_alert_coban_SendEmail));

		Common.setupUI(localView.findViewById(R.id.alert_coban), getActivity());
		initialise();
		initialiseListener();
		return localView;
	}

	private void initialise() {
		setRangeSeekBarsDefault();
		this.listListView = new ArrayList<DanhSachCanhBaoItem>();
		this.adapterListView = new DanhSachCanhBao_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listListView);
		this.lv_BoLocCoBan.setAdapter(this.adapterListView);
	}

	private void initialiseListener() {
		this.rangeSeekBar_EPS
				.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

					@Override
					public void onRangeSeekBarValuesChanged(
							RangeSeekBar<?> bar, Integer minValue,
							Integer maxValue) {
						// TODO Auto-generated method stub
						setDescription(tv_DescEPS, 1000 * minValue,
								1000 * maxValue);
						EPS_from = (1000 * minValue.intValue());
						EPS_to = (1000 * maxValue.intValue());
					}
				});
		this.rangeSeekBar_PE
				.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

					@Override
					public void onRangeSeekBarValuesChanged(
							RangeSeekBar<?> bar, Integer minValue,
							Integer maxValue) {
						// TODO Auto-generated method stub
						setDescription(tv_DescPE, 1000 * minValue,
								1000 * maxValue);
						PE_from = (1000 * minValue.intValue());
						PE_to = (1000 * maxValue.intValue());
					}
				});
		this.rangeSeekBar_ROE
				.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

					@Override
					public void onRangeSeekBarValuesChanged(
							RangeSeekBar<?> bar, Integer minValue,
							Integer maxValue) {
						// TODO Auto-generated method stub
						setDescription(tv_DescROE, 1000 * minValue,
								1000 * maxValue);
						ROE_from = (1000 * minValue.intValue());
						ROE_to = (1000 * maxValue.intValue());
					}
				});
		this.rangeSeekBar_ROA
				.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

					@Override
					public void onRangeSeekBarValuesChanged(
							RangeSeekBar<?> bar, Integer minValue,
							Integer maxValue) {
						// TODO Auto-generated method stub
						setDescription(tv_DescROA, 1000 * minValue,
								1000 * maxValue);
						ROA_from = (1000 * minValue.intValue());
						ROA_to = (1000 * maxValue.intValue());
					}
				});
		this.rangeSeekBar_PB
				.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

					@Override
					public void onRangeSeekBarValuesChanged(
							RangeSeekBar<?> bar, Integer minValue,
							Integer maxValue) {
						// TODO Auto-generated method stub
						setDescription(tv_DescPB, 1000 * minValue,
								1000 * maxValue);
						PB_from = (1000 * minValue.intValue());
						PB_to = (1000 * maxValue.intValue());
					}
				});
		this.rangeSeekBar_Beta
				.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

					@Override
					public void onRangeSeekBarValuesChanged(
							RangeSeekBar<?> bar, Integer minValue,
							Integer maxValue) {
						// TODO Auto-generated method stub
						setDescription(tv_DescBeta, (float) minValue / 10,
								(float) maxValue / 10);
						Beta_from = ((float) minValue / 10);
						Beta_to = ((float) maxValue / 10);
					}
				});
		this.rangeSeekBar_GiaHT
				.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

					@Override
					public void onRangeSeekBarValuesChanged(
							RangeSeekBar<?> bar, Integer minValue,
							Integer maxValue) {
						// TODO Auto-generated method stub
						setDescription(tv_DescGiaHT, 1000 * minValue,
								1000 * maxValue);
						GiaHT_from = (1000 * minValue.intValue());
						GiaHT_to = (1000 * maxValue.intValue());
					}
				});
		this.btn_CapNhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CallAddAlertBasicSubmit();
			}
		});
		this.btn_MacDinh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		this.btn_CoBan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setRangeSeekBars(5, 20, 0, 10, 4, 20, 3, 20, 0, 20, 0, 30, 0,
						40);
			}
		});
		this.btn_HieuQua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setRangeSeekBars(5, 20, 0, 10, 6, 20, 4, 20, 0, 40, 0, 30, 0,
						40);
			}
		});
		this.btn_LuotSong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setRangeSeekBars(0, 20, 0, 50, 0, 20, 0, 20, 0, 40, 15, 30, 0,
						40);
			}
		});
		this.lv_BoLocCoBan.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});

		this.lv_BoLocCoBan
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						CallRemoveAlert_Basic(listListView.get(position).id);
						return false;
					}
				});
	}

	public void onPause() {
		super.onPause();
	}

	public void onResume() {
		super.onResume();
		CallGetALLAlert_Basic();
	}

	private void CallAddAlertBasicSubmit() {
		if ((this.edt_Symbols.getText() != null)
				&& (this.edt_Symbols.getText().toString().length() > 0)) {
			ArrayList<String> list_key = new ArrayList<String>();
			ArrayList<String> list_value = new ArrayList<String>();
			list_key.add("giang");
			list_value
					.add(new String(
							getStringResource(R.string.controller_AddAlertBasicSubmit)));
			list_key.add("pv_EPSMIN");
			list_value.add(String.valueOf(this.EPS_from));
			list_key.add("pv_EPSMAX");
			list_value.add(String.valueOf(this.EPS_to));
			list_key.add("pv_PEMIN");
			list_value.add(String.valueOf(this.PE_from));
			list_key.add("pv_PEMAX");
			list_value.add(String.valueOf(this.PE_to));
			list_key.add("pv_ROEMIN");
			list_value.add(String.valueOf(this.ROE_from));
			list_key.add("pv_ROEMAX");
			list_value.add(String.valueOf(this.ROE_to));
			list_key.add("pv_ROAMIN");
			list_value.add(String.valueOf(this.ROA_from));
			list_key.add("pv_ROAMAX");
			list_value.add(String.valueOf(this.ROA_to));
			list_key.add("pv_PBMIN");
			list_value.add(String.valueOf(this.PB_from));
			list_key.add("pv_PBMAX");
			list_value.add(String.valueOf(this.PB_to));
			list_key.add("pv_BETAMIN");
			list_value.add(String.valueOf(this.Beta_from));
			list_key.add("pv_BETAMAX");
			list_value.add(String.valueOf(this.Beta_to));
			list_key.add("pv_PRICEMIN");
			list_value.add(String.valueOf(this.GiaHT_from));
			list_key.add("pv_PRICEMAX");
			list_value.add(String.valueOf(this.GiaHT_to));
			list_key.add("pv_SYMBOLS");
			list_value.add(this.edt_Symbols.getText().toString());
			list_key.add("pv_SYMBOLSINDAY");
			list_value.add(this.edt_Symbols.getText().toString());
			list_key.add("pv_ISEMAIL");
			if (this.checkSendEmail.isChecked()) {
				list_value.add("Y");

			} else {
				list_value.add("N");
			}
			StaticObjectManager.connectServer.callHttpPostService(
					ADDALERTBASICSUBMIT, this, list_key, list_value);
		} else {
			showDialogMessage(R.string.thong_bao,
					R.string.alert_taocanhbaothatbai);
		}
	}

	private void CallGetALLAlert_Basic() {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		list_key.add("giang");
		list_value.add(new String(
				getStringResource(R.string.controller_GetALLAlert_Basic)));
		StaticObjectManager.connectServer.callHttpPostService(
				GETALLALERT_BASIC, this, list_key, list_value);
	}

	private void CallRemoveAlert_Basic(String paramString) {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		list_key.add("giang");
		list_value
				.add(getStringResource(R.string.controller_RemoveAlert_Basic));
		list_key.add("id");
		list_value.add(paramString);
		StaticObjectManager.connectServer.callHttpPostService(
				REMOVEALERT_BASIC, this, list_key, list_value);
	}

	private void setDescription(TextView paramTextView, float paramFloat1,
			float paramFloat2) {
		paramTextView.setText(new String(
				getStringResource(R.string.boloccoban_desc)).replace("ss1",
				String.valueOf(paramFloat1)).replace("ss2",
				String.valueOf(paramFloat2)));
	}

	private void setDescription(TextView paramTextView, int paramInt1,
			int paramInt2) {
		paramTextView.setText(new String(
				getStringResource(R.string.boloccoban_desc)).replace("ss1",
				String.valueOf(paramInt1)).replace("ss2",
				String.valueOf(paramInt2)));
	}

	private void setRangeSeekBars(int paramInt1, int paramInt2, int paramInt3,
			int paramInt4, int paramInt5, int paramInt6, int paramInt7,
			int paramInt8, int paramInt9, int paramInt10, int paramInt11,
			int paramInt12, int paramInt13, int paramInt14) {
		this.EPS_from = (paramInt1 * 1000);
		this.EPS_to = (paramInt2 * 1000);
		this.PE_from = paramInt3;
		this.PE_to = paramInt4;
		this.ROE_from = (paramInt5 * 5);
		this.ROE_to = (paramInt6 * 5);
		this.ROA_from = (paramInt7 * 5);
		this.ROA_to = (paramInt8 * 5);
		this.PB_from = (paramInt9 * 10);
		this.PB_to = (paramInt10 * 10);
		this.Beta_from = (paramInt11 / 10.0F);
		this.Beta_to = (paramInt12 / 10.0F);
		this.GiaHT_from = (paramInt13 * 5);
		this.GiaHT_to = (paramInt14 * 5);
		setDescription(this.tv_DescEPS, paramInt1 * 1000, paramInt2 * 1000);
		setDescription(this.tv_DescPE, paramInt3, paramInt4);
		setDescription(this.tv_DescROE, paramInt5 * 5, paramInt6 * 5);
		setDescription(this.tv_DescROA, paramInt7 * 5, paramInt8 * 5);
		setDescription(this.tv_DescPB, paramInt9 * 10, paramInt10 * 10);
		setDescription(this.tv_DescBeta, paramInt11 / 10.0F, paramInt12 / 10.0F);
		setDescription(this.tv_DescGiaHT, paramInt13 * 5, paramInt14 * 5);
		this.rangeSeekBar_EPS.setMinValue(paramInt1);
		this.rangeSeekBar_EPS.setMaxValue(paramInt2);
		this.rangeSeekBar_PE.setMinValue(paramInt3);
		this.rangeSeekBar_PE.setMaxValue(paramInt4);
		this.rangeSeekBar_ROE.setMinValue(paramInt5);
		this.rangeSeekBar_ROE.setMaxValue(paramInt6);
		this.rangeSeekBar_ROA.setMinValue(paramInt7);
		this.rangeSeekBar_ROA.setMaxValue(paramInt8);
		this.rangeSeekBar_PB.setMinValue(paramInt9);
		this.rangeSeekBar_PB.setMaxValue(paramInt10);
		this.rangeSeekBar_Beta.setMinValue(paramInt11);
		this.rangeSeekBar_Beta.setMaxValue(paramInt12);
		this.rangeSeekBar_GiaHT.setMinValue(paramInt13);
		this.rangeSeekBar_GiaHT.setMaxValue(paramInt14);
	}

	private void setRangeSeekBarsDefault() {
		setDescription(this.tv_DescEPS,
				1000 * this.rangeSeekBar_EPS.getDefaultMinValue(),
				1000 * this.rangeSeekBar_EPS.getDefaultMaxValue());
		setDescription(this.tv_DescPE,
				this.rangeSeekBar_PE.getDefaultMinValue(),
				this.rangeSeekBar_PE.getDefaultMaxValue());
		setDescription(this.tv_DescROE,
				5 * this.rangeSeekBar_ROE.getDefaultMinValue(),
				5 * this.rangeSeekBar_ROE.getDefaultMaxValue());
		setDescription(this.tv_DescROA,
				5 * this.rangeSeekBar_ROA.getDefaultMinValue(),
				5 * this.rangeSeekBar_ROA.getDefaultMaxValue());
		setDescription(this.tv_DescPB,
				10 * this.rangeSeekBar_PB.getDefaultMinValue(),
				10 * this.rangeSeekBar_PB.getDefaultMaxValue());
		setDescription(this.tv_DescBeta,
				this.rangeSeekBar_Beta.getDefaultMinValue() / 10.0F,
				this.rangeSeekBar_Beta.getDefaultMaxValue() / 10.0F);
		setDescription(this.tv_DescGiaHT,
				5 * this.rangeSeekBar_GiaHT.getDefaultMinValue(),
				5 * this.rangeSeekBar_GiaHT.getDefaultMaxValue());
		this.rangeSeekBar_EPS.setMinValue(this.rangeSeekBar_EPS
				.getDefaultMinValue());
		this.rangeSeekBar_EPS.setMaxValue(this.rangeSeekBar_EPS
				.getDefaultMaxValue());
		this.rangeSeekBar_PE.setMinValue(this.rangeSeekBar_PE
				.getDefaultMinValue());
		this.rangeSeekBar_PE.setMaxValue(this.rangeSeekBar_PE
				.getDefaultMaxValue());
		this.rangeSeekBar_ROE.setMinValue(this.rangeSeekBar_ROE
				.getDefaultMinValue());
		this.rangeSeekBar_ROE.setMaxValue(this.rangeSeekBar_ROE
				.getDefaultMaxValue());
		this.rangeSeekBar_ROA.setMinValue(this.rangeSeekBar_ROA
				.getDefaultMinValue());
		this.rangeSeekBar_ROA.setMaxValue(this.rangeSeekBar_ROA
				.getDefaultMaxValue());
		this.rangeSeekBar_PB.setMinValue(this.rangeSeekBar_PB
				.getDefaultMinValue());
		this.rangeSeekBar_PB.setMaxValue(this.rangeSeekBar_PB
				.getDefaultMaxValue());
		this.rangeSeekBar_Beta.setMinValue(this.rangeSeekBar_Beta
				.getDefaultMinValue());
		this.rangeSeekBar_Beta.setMaxValue(this.rangeSeekBar_Beta
				.getDefaultMaxValue());
		this.rangeSeekBar_GiaHT.setMinValue(this.rangeSeekBar_GiaHT
				.getDefaultMinValue());
		this.rangeSeekBar_GiaHT.setMaxValue(this.rangeSeekBar_GiaHT
				.getDefaultMaxValue());
		this.EPS_from = 0;
		this.EPS_to = (1000 * this.rangeSeekBar_EPS.getDefaultMaxValue());
		this.PE_from = 0;
		this.PE_to = this.rangeSeekBar_PE.getDefaultMaxValue();
		this.ROE_from = 0;
		this.ROE_to = (5 * this.rangeSeekBar_ROE.getDefaultMaxValue());
		this.ROA_from = 0;
		this.ROA_to = (5 * this.rangeSeekBar_ROA.getDefaultMaxValue());
		this.PB_from = 0;
		this.PB_to = (10 * this.rangeSeekBar_PB.getDefaultMaxValue());
		this.Beta_from = 0.0F;
		this.Beta_to = (this.rangeSeekBar_Beta.getDefaultMaxValue() / 10.0F);
		this.GiaHT_from = 0;
		this.GiaHT_to = (5 * this.rangeSeekBar_GiaHT.getDefaultMaxValue());
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		switch (key) {
		case ADDALERTBASICSUBMIT:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			CallGetALLAlert_Basic();
			break;
		case GETALLALERT_BASIC:
			if (rObj.obj != null) {
				this.listListView.clear();
				this.listListView.addAll((List<DanhSachCanhBaoItem>) rObj.obj);
				this.adapterListView.notifyDataSetChanged();
			}
			break;
		case REMOVEALERT_BASIC:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			CallGetALLAlert_Basic();
			break;

		default:
			break;
		}
	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.alert_coban JD-Core Version: 0.6.0
 */