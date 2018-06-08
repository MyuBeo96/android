package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.BoLocCoBan_Adapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.BoLocCoBanItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.design.RangeSeekBar;
import com.fscuat.mobiletrading.design.RangeSeekBarLayout;
import com.fscuat.mobiletrading.design.RangeSeekBar.OnRangeSeekBarChangeListener;

public class BasicFilter extends AbstractFragment {

	final String GETARRANGEBASIC = "GetArrangeBasicService";

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
	BoLocCoBan_Adapter adapterListView;
	Button btn_CapNhat;
	Button btn_CoBan;
	Button btn_HieuQua;
	Button btn_LuotSong;
	Button btn_MacDinh;
	Dialog dialog;
	List<BoLocCoBanItem> listListView;
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
	TextView tv_chitiet_Beta;
	TextView tv_chitiet_Change;
	TextView tv_chitiet_ClosePrice;
	TextView tv_chitiet_EBIT;
	TextView tv_chitiet_EPS;
	TextView tv_chitiet_LaiGop;
	TextView tv_chitiet_LaiHDKD;
	TextView tv_chitiet_LaiRong;
	TextView tv_chitiet_PB;
	TextView tv_chitiet_PE;
	TextView tv_chitiet_PS;
	TextView tv_chitiet_ROA;
	TextView tv_chitiet_ROE;
	TextView tv_chitiet_Symbol;
	TextView tv_chitiet_ThanhToanHH;
	TextView tv_chitiet_ThanhToanNhanh;
	TextView tv_chitiet_VonVay_TongTS;
	TextView tv_chitiet_VonVay_VonCSH;

	public static BasicFilter newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		BasicFilter self = new BasicFilter();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.StickerFilter);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.boloc_coban,
				paramViewGroup, false);
		this.rangeSeekBar_EPS = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_boloccoban_EPS));
		this.tv_DescEPS = ((TextView) localView
				.findViewById(R.id.text_boloc_coban_DescEPS));
		this.rangeSeekBar_PE = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_boloccoban_PE));
		this.tv_DescPE = ((TextView) localView
				.findViewById(R.id.text_boloc_coban_DescPE));
		this.rangeSeekBar_ROE = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_boloccoban_ROE));
		this.tv_DescROE = ((TextView) localView
				.findViewById(R.id.text_boloc_coban_DescROE));
		this.rangeSeekBar_ROA = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_boloccoban_ROA));
		this.tv_DescROA = ((TextView) localView
				.findViewById(R.id.text_boloc_coban_DescROA));
		this.rangeSeekBar_PB = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_boloccoban_PB));
		this.tv_DescPB = ((TextView) localView
				.findViewById(R.id.text_boloc_coban_DescPB));
		this.rangeSeekBar_Beta = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_boloccoban_Beta));
		this.tv_DescBeta = ((TextView) localView
				.findViewById(R.id.text_boloc_coban_DescBeta));
		this.rangeSeekBar_GiaHT = ((RangeSeekBarLayout) localView
				.findViewById(R.id.seekbar_boloccoban_GiaHT));
		this.tv_DescGiaHT = ((TextView) localView
				.findViewById(R.id.text_boloc_coban_DescGiaHT));
		this.btn_CapNhat = ((Button) localView
				.findViewById(R.id.btn_boloccoban_CapNhat));
		this.btn_MacDinh = ((Button) localView
				.findViewById(R.id.btn_boloccoban_Default));
		this.lv_BoLocCoBan = ((ListView) localView
				.findViewById(R.id.listview_boloccoban));
		this.btn_CoBan = ((Button) localView
				.findViewById(R.id.btn_boloccoban_dautucoban));
		this.btn_HieuQua = ((Button) localView
				.findViewById(R.id.btn_boloccoban_dautuhieuqua));
		this.btn_LuotSong = ((Button) localView
				.findViewById(R.id.btn_boloccoban_dautuluotsong));
		initialise();
		initialiseListener();
		return localView;
	}

	private void initialise() {
		setRangeSeekBarsDefault();
		this.listListView = new ArrayList<BoLocCoBanItem>();
		this.adapterListView = new BoLocCoBan_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listListView);
		this.lv_BoLocCoBan.setAdapter(this.adapterListView);
		CreateDialogChiTiet();
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
				CallGetArrangeBasic();
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
		this.lv_BoLocCoBan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				DisplayDialogChiTiet(listListView.get(position));
			}
		});
	}

	public void onPause() {
		super.onPause();
	}

	public void onResume() {
		super.onResume();
	}

	private void CallGetArrangeBasic() {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("giang");
			list_value.add(new String(
					getStringResource(R.string.controller_GetArrangeBasic)));
		}
		{
			list_key.add("pv_eps_from");
			list_value.add(String.valueOf(this.EPS_from));
		}
		{
			list_key.add("pv_eps_to");
			list_value.add(String.valueOf(this.EPS_to));
		}
		{
			list_key.add("pv_pe_from");
			list_value.add(String.valueOf(this.PE_from));
		}
		{
			list_key.add("pv_pe_to");
			list_value.add(String.valueOf(this.PE_to));
		}
		{
			list_key.add("pv_roe_from");
			list_value.add(String.valueOf(this.ROE_from));
		}
		{
			list_key.add("pv_roe_to");
			list_value.add(String.valueOf(this.ROE_to));
		}
		{
			list_key.add("pv_roa_from");
			list_value.add(String.valueOf(this.ROA_from));
		}
		{
			list_key.add("pv_roa_to");
			list_value.add(String.valueOf(this.ROA_to));
		}
		{
			list_key.add("pv_pb_from");
			list_value.add(String.valueOf(this.PB_from));
		}
		{
			list_key.add("pv_pb_to");
			list_value.add(String.valueOf(this.PB_to));
		}
		{
			list_key.add("pv_beta_from");
			list_value.add(String.valueOf(this.Beta_from));
		}
		{
			list_key.add("pv_beta_to");
			list_value.add(String.valueOf(this.Beta_to));
		}
		{
			list_key.add("pv_price_from");
			list_value.add(String.valueOf(this.GiaHT_from));
		}
		{
			list_key.add("pv_price_to");
			list_value.add(String.valueOf(this.GiaHT_to));
		}

		StaticObjectManager.connectServer.callHttpPostService(GETARRANGEBASIC,
				this, list_key, list_value);

	}

	private void CreateDialogChiTiet() {
		this.dialog = new Dialog(getActivity());
		this.dialog.requestWindowFeature(1);
		this.dialog.setCancelable(true);
		this.dialog.setContentView(R.layout.boloc_coban_chitiet);
		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
		localLayoutParams.copyFrom(this.dialog.getWindow().getAttributes());
		localLayoutParams.width = LayoutParams.MATCH_PARENT;
		localLayoutParams.height = LayoutParams.WRAP_CONTENT;
		this.dialog.getWindow().setAttributes(localLayoutParams);
		this.tv_chitiet_Symbol = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_Symbol));
		this.tv_chitiet_ClosePrice = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_ClosePrice));
		this.tv_chitiet_Change = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_Change));
		this.tv_chitiet_PE = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_PE));
		this.tv_chitiet_PB = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_PB));
		this.tv_chitiet_EPS = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_EPS));
		this.tv_chitiet_PS = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_PS));
		this.tv_chitiet_ThanhToanNhanh = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_ThanhToanNhanh));
		this.tv_chitiet_ThanhToanHH = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_ThanhToanHienHanh));
		this.tv_chitiet_VonVay_VonCSH = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_VonVay_VonCSH));
		this.tv_chitiet_VonVay_TongTS = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_VonVay_TongTS));
		this.tv_chitiet_LaiGop = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_LaiGop_DT));
		this.tv_chitiet_EBIT = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_EBIT_DT));
		this.tv_chitiet_LaiHDKD = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_LaiHDKT_DT));
		this.tv_chitiet_LaiRong = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_LaiRong_DT));
		this.tv_chitiet_ROE = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_ROE));
		this.tv_chitiet_ROA = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_ROA));
		this.tv_chitiet_Beta = ((TextView) this.dialog
				.findViewById(R.id.text_coban_chitiet_Beta));
	}

	private void DisplayDialogChiTiet(BoLocCoBanItem paramBoLocCoBanItem) {
		this.tv_chitiet_Symbol.setText(paramBoLocCoBanItem.SYMBOL);
		this.tv_chitiet_ClosePrice.setText(paramBoLocCoBanItem.REALPRICE);
		this.tv_chitiet_Change.setText(paramBoLocCoBanItem.REALPRICE_CHANGE);
		this.tv_chitiet_PE.setText(paramBoLocCoBanItem.PE);
		this.tv_chitiet_PB.setText(paramBoLocCoBanItem.PB);
		this.tv_chitiet_EPS.setText(paramBoLocCoBanItem.EST_EPS);
		this.tv_chitiet_PS.setText(paramBoLocCoBanItem.PS);
		this.tv_chitiet_ThanhToanNhanh.setText(paramBoLocCoBanItem.QUICK_RATIO);
		this.tv_chitiet_ThanhToanHH.setText(paramBoLocCoBanItem.STASSET_STLIAB);
		this.tv_chitiet_VonVay_VonCSH
				.setText(paramBoLocCoBanItem.TOTAL_LOANS_EQUITY);
		this.tv_chitiet_VonVay_TongTS
				.setText(paramBoLocCoBanItem.TOTAL_LOANS_ASSETS);
		this.tv_chitiet_LaiGop.setText(paramBoLocCoBanItem.T_GP_SALES);
		this.tv_chitiet_EBIT.setText(paramBoLocCoBanItem.T_EBIT_DOANH_SO);
		this.tv_chitiet_LaiHDKD
				.setText(paramBoLocCoBanItem.T_LAI_HOAT_DONG_DOANH_SO);
		this.tv_chitiet_LaiRong
				.setText(paramBoLocCoBanItem.T_LAI_THUAN_DOANH_SO);
		this.tv_chitiet_ROE.setText(paramBoLocCoBanItem.T_ROE);
		this.tv_chitiet_ROA.setText(paramBoLocCoBanItem.T_ROA);
		this.tv_chitiet_Beta.setText(paramBoLocCoBanItem.BETA);
		this.dialog.show();
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
		paramTextView.setText(new String(getStringResource(2131034408))
				.replace("ss1", String.valueOf(paramInt1)).replace("ss2",
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
		case GETARRANGEBASIC:
			if (rObj.obj != null) {
				this.listListView.clear();
				this.listListView.addAll((List<BoLocCoBanItem>) rObj.obj);
				this.adapterListView.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.BoLocCoBan JD-Core Version: 0.6.0
 */