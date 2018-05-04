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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.DotBienGiaKhoiLuong_Adapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.DotBienGiaKhoiLuongItem;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;

public class QuantityPriceFilter extends AbstractFragment {
	int DotBienTrong;
	final String GETARRANGELISTPRICE_QUANTITY = "GetArrangeListPriceQuantityService";
	int GiaDotBien;
	int KLDotBien;
	int TyLeDotBien;
	ArrayAdapter<String> adapterBoLocCP;
	ArrayAdapter<String> adapterDotBienTrong;
	ArrayAdapter<String> adapterGiaDotBien;
	ArrayAdapter<String> adapterKLDotBien;
	DotBienGiaKhoiLuong_Adapter adapterListView;
	ArrayAdapter<String> adapterTyLeDotBien;
	Button btn_CapNhat;
	Dialog dialog;
	String[] listBoLocCP;
	String[] listDotBienTrong;
	String[] listGiaDotBien;
	String[] listKLDotBien;
	List<DotBienGiaKhoiLuongItem> listListViewItem;
	String[] listTyLeDotBien;
	ListView lv_bolocgiakhoiluong;
	Spinner spn_boloccp;
	Spinner spn_dotbientrong;
	Spinner spn_giadotbien;
	Spinner spn_kldotbien;
	Spinner spn_tyledotbien;
	TextView tv_Desc;
	TextView tv_chitiet_Cao;
	TextView tv_chitiet_Change;
	TextView tv_chitiet_ChangeKLGD;
	TextView tv_chitiet_ClosePrice;
	TextView tv_chitiet_KLGDTB;
	TextView tv_chitiet_MoCua;
	TextView tv_chitiet_PercentChange;
	TextView tv_chitiet_Symbol;
	TextView tv_chitiet_Thap;
	TextView tv_chitiet_TongGTGD;
	TextView tv_chitiet_TongKLGD;

	public static QuantityPriceFilter newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		QuantityPriceFilter self = new QuantityPriceFilter();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.StickerFilter);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(
				R.layout.boloc_dotbiengiakhoiluong, paramViewGroup, false);
		this.lv_bolocgiakhoiluong = ((ListView) localView
				.findViewById(R.id.listview_bolocgiakhoiluong));
		this.spn_boloccp = ((Spinner) localView
				.findViewById(R.id.spn_bolocgiakhoiluong));
		this.spn_dotbientrong = ((Spinner) localView
				.findViewById(R.id.spn_bolocgiakhoiluong_DotBienTrong));
		this.spn_kldotbien = ((Spinner) localView
				.findViewById(R.id.spn_bolocgiakhoiluong_KLDotBien));
		this.spn_giadotbien = ((Spinner) localView
				.findViewById(R.id.spn_bolocgiakhoiluong_GiaDotBien));
		this.spn_tyledotbien = ((Spinner) localView
				.findViewById(R.id.spn_bolocgiakhoiluong_TyLeDB));
		this.btn_CapNhat = ((Button) localView
				.findViewById(R.id.btn_bolocgiakhoiluong_CapNhat));
		this.tv_Desc = ((TextView) localView
				.findViewById(R.id.text_bolocgiakhoiluong_Desc));
		initialise();
		initialiseListener();
		return localView;
	}

	private void initialise() {
		this.listListViewItem = new ArrayList<DotBienGiaKhoiLuongItem>();
		this.adapterListView = new DotBienGiaKhoiLuong_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listListViewItem);
		this.lv_bolocgiakhoiluong.setAdapter(this.adapterListView);

		this.listBoLocCP = getResources().getStringArray(R.array.listBoLocCP);
		this.adapterBoLocCP = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listBoLocCP);
		this.adapterBoLocCP.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_boloccp.setAdapter(this.adapterBoLocCP);
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
		CreateDialogChiTiet();
		this.spn_boloccp.setEnabled(false);
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
				CallGetArrangeListPrice_Quantity();
			}
		});
		this.lv_bolocgiakhoiluong.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});

		this.lv_bolocgiakhoiluong
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						DisplayDialogChiTiet(listListViewItem.get(position));
					}
				});
	}

	public void onResume() {
		super.onResume();
		this.spn_boloccp.setSelection(BoLocCP_ViewPager.DOT_BIEN_GIA_KL);
	}

	private void CallGetArrangeListPrice_Quantity() {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("giang");
			list_value
					.add(getStringResource(R.string.controller_GetArrangeListQuantity));
		}
		{
			list_key.add("l_dkklgd");
			list_value.add(String.valueOf(this.spn_kldotbien
					.getSelectedItemPosition()));
		}
		{
			list_key.add("l_dktg");
			list_value.add(String.valueOf(this.spn_dotbientrong
					.getSelectedItemPosition()));
		}
		{
			list_key.add("l_dktyleklgd");
			list_value.add(String.valueOf(this.spn_tyledotbien
					.getSelectedItemPosition()));
		}
		{
			list_key.add("l_dkgia");
			list_value.add(String.valueOf(this.spn_giadotbien
					.getSelectedItemPosition()));
		}

		StaticObjectManager.connectServer.callHttpPostService(
				GETARRANGELISTPRICE_QUANTITY, this, list_key, list_value);
	}

	private void CreateDialogChiTiet() {
		this.dialog = new Dialog(getActivity());
		this.dialog.requestWindowFeature(1);
		this.dialog.setCancelable(true);
		this.dialog.setContentView(R.layout.boloc_dotbiengiakhoiluong_chitiet);
		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
		localLayoutParams.copyFrom(this.dialog.getWindow().getAttributes());
		localLayoutParams.width = LayoutParams.MATCH_PARENT;
		localLayoutParams.height = LayoutParams.WRAP_CONTENT;
		this.dialog.getWindow().setAttributes(localLayoutParams);
		this.tv_chitiet_Symbol = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengiakhoiluong_chitiet_Symbol));
		this.tv_chitiet_ClosePrice = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengiakhoiluong_chitiet_ClosePrice));
		this.tv_chitiet_Change = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengiakhoiluong_chitiet_Change));
		this.tv_chitiet_PercentChange = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengiakhoiluong_chitiet_PercentChange));
		this.tv_chitiet_TongKLGD = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengiakhoiluong_chitiet_TongKLGD));
		this.tv_chitiet_TongGTGD = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengiakhoiluong_chitiet_TongGTGD));
		this.tv_chitiet_KLGDTB = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengiakhoiluong_chitiet_KLGDTB));
		this.tv_chitiet_ChangeKLGD = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengiakhoiluong_chitiet_ThayDoiKLGD));
		this.tv_chitiet_MoCua = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengiakhoiluong_chitiet_MoCua));
		this.tv_chitiet_Cao = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengiakhoiluong_chitiet_Cao));
		this.tv_chitiet_Thap = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengiakhoiluong_chitiet_Thap));
	}

	private void DisplayDialogChiTiet(
			DotBienGiaKhoiLuongItem paramDotBienGiaKhoiLuongItem) {
		this.tv_chitiet_Symbol.setText(paramDotBienGiaKhoiLuongItem.SYMBOL);
		this.tv_chitiet_ClosePrice
				.setText(paramDotBienGiaKhoiLuongItem.REALPRICE);
		this.tv_chitiet_Change
				.setText(paramDotBienGiaKhoiLuongItem.REALPRICE_CHANGE);
		this.tv_chitiet_PercentChange
				.setText(paramDotBienGiaKhoiLuongItem.REALPRICE_PERCENT_CHANGE);
		this.tv_chitiet_TongKLGD.setText(paramDotBienGiaKhoiLuongItem.KLGD);
		this.tv_chitiet_TongGTGD.setText(paramDotBienGiaKhoiLuongItem.GTGD);
		this.tv_chitiet_KLGDTB.setText(paramDotBienGiaKhoiLuongItem.KLGD_TB);
		this.tv_chitiet_ChangeKLGD
				.setText(paramDotBienGiaKhoiLuongItem.KLGD_SV_TB);
		this.tv_chitiet_MoCua.setText(paramDotBienGiaKhoiLuongItem.OPENPRICE);
		this.tv_chitiet_Cao.setText(paramDotBienGiaKhoiLuongItem.HIGH);
		this.tv_chitiet_Thap.setText(paramDotBienGiaKhoiLuongItem.LOW);
		this.dialog.show();
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
		case GETARRANGELISTPRICE_QUANTITY:
			if (rObj.obj != null) {
				this.listListViewItem.clear();
				this.listListViewItem
						.addAll((List<DotBienGiaKhoiLuongItem>) rObj.obj);
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
 * com.fss.fragment.BoLocDBKhoiLuongGia JD-Core Version: 0.6.0
 */