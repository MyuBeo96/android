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

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.DotBienKhoiLuong_Adapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.DotBienKhoiLuongItem;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;

public class QuantityFilter extends AbstractFragment {
	int DotBienTrong;
	final String GETARRANGELISTQUANTITY = "GetArrangeListQuantityService";
	int KLDotBien;
	int TyLeDotBien;
	ArrayAdapter<String> adapterBoLocCP;
	ArrayAdapter<String> adapterDotBienTrong;
	ArrayAdapter<String> adapterKLDotBien;
	DotBienKhoiLuong_Adapter adapterListView;
	ArrayAdapter<String> adapterTyLeDotBien;
	Button btn_CapNhat;
	Dialog dialog;
	String[] listBoLocCP;
	String[] listDotBienTrong;
	String[] listKLDotBien;
	List<DotBienKhoiLuongItem> listListViewItem;
	String[] listTyLeDotBien;
	ListView lv_bolockhoiluong;
	Spinner spn_boloccp;
	Spinner spn_dotbientrong;
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

	public static QuantityFilter newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		QuantityFilter self = new QuantityFilter();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.StickerFilter);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(
				R.layout.boloc_dotbienkhoiluong, paramViewGroup, false);
		this.lv_bolockhoiluong = ((ListView) localView
				.findViewById(R.id.listview_bolockhoiluong));
		this.spn_boloccp = ((Spinner) localView
				.findViewById(R.id.spn_bolockhoiluong));
		this.spn_dotbientrong = ((Spinner) localView
				.findViewById(R.id.spn_bolockhoiluong_DotBienTrong));
		this.spn_kldotbien = ((Spinner) localView
				.findViewById(R.id.spn_bolockhoiluong_KLDotBien));
		this.spn_tyledotbien = ((Spinner) localView
				.findViewById(R.id.spn_bolockhoiluong_TyLeDB));
		this.btn_CapNhat = ((Button) localView
				.findViewById(R.id.btn_bolockhoiluong_CapNhat));
		this.tv_Desc = ((TextView) localView
				.findViewById(R.id.text_bolockhoiluong_Desc));
		initialise();
		initialiseListener();
		return localView;
	}

	private void initialise() {
		this.listListViewItem = new ArrayList<DotBienKhoiLuongItem>();
		this.adapterListView = new DotBienKhoiLuong_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listListViewItem);
		this.lv_bolockhoiluong.setAdapter(this.adapterListView);
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
				R.array.DBKhoiLuong_TyLeDotBien);
		this.adapterTyLeDotBien = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listTyLeDotBien);
		this.adapterTyLeDotBien
				.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_tyledotbien.setAdapter(this.adapterTyLeDotBien);
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
		this.btn_CapNhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDescription();
				CallGetArrangeListQuantity();

			}
		});
		this.lv_bolockhoiluong.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		this.lv_bolockhoiluong
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
		this.spn_boloccp.setSelection(BoLocCP_ViewPager.DOT_BIEN_KL);
	}

	private void CallGetArrangeListQuantity() {
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
		StaticObjectManager.connectServer.callHttpPostService(
				GETARRANGELISTQUANTITY, this, list_key, list_value);
	}

	private void CreateDialogChiTiet() {
		this.dialog = new Dialog(getActivity());
		this.dialog.requestWindowFeature(1);
		this.dialog.setCancelable(true);
		this.dialog.setContentView(R.layout.boloc_dotbienkhoiluong_chitiet);
		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
		localLayoutParams.copyFrom(this.dialog.getWindow().getAttributes());
		localLayoutParams.width = LayoutParams.MATCH_PARENT;
		localLayoutParams.height = LayoutParams.WRAP_CONTENT;
		this.dialog.getWindow().setAttributes(localLayoutParams);
		this.tv_chitiet_Symbol = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiensoluong_chitiet_Symbol));
		this.tv_chitiet_ClosePrice = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiensoluong_chitiet_ClosePrice));
		this.tv_chitiet_Change = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiensoluong_chitiet_Change));
		this.tv_chitiet_PercentChange = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiensoluong_chitiet_PercentChange));
		this.tv_chitiet_TongKLGD = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiensoluong_chitiet_TongKLGD));
		this.tv_chitiet_TongGTGD = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiensoluong_chitiet_TongGTGD));
		this.tv_chitiet_KLGDTB = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiensoluong_chitiet_KLGDTB));
		this.tv_chitiet_ChangeKLGD = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiensoluong_chitiet_ThayDoiKLGD));
		this.tv_chitiet_MoCua = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiensoluong_chitiet_MoCua));
		this.tv_chitiet_Cao = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiensoluong_chitiet_Cao));
		this.tv_chitiet_Thap = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiensoluong_chitiet_Thap));
	}

	private void DisplayDialogChiTiet(
			DotBienKhoiLuongItem paramDotBienKhoiLuongItem) {
		this.tv_chitiet_Symbol.setText(paramDotBienKhoiLuongItem.SYMBOL);
		this.tv_chitiet_ClosePrice.setText(paramDotBienKhoiLuongItem.REALPRICE);
		this.tv_chitiet_Change
				.setText(paramDotBienKhoiLuongItem.REALPRICE_CHANGE);
		this.tv_chitiet_PercentChange
				.setText(paramDotBienKhoiLuongItem.REALPRICE_PERCENT_CHANGE);
		this.tv_chitiet_TongKLGD.setText(paramDotBienKhoiLuongItem.KLGD);
		this.tv_chitiet_TongGTGD.setText(paramDotBienKhoiLuongItem.GTGD);
		this.tv_chitiet_KLGDTB.setText(paramDotBienKhoiLuongItem.KLGD_TB);
		this.tv_chitiet_ChangeKLGD
				.setText(paramDotBienKhoiLuongItem.KLGD_SV_TB);
		this.tv_chitiet_MoCua.setText(paramDotBienKhoiLuongItem.OPENPRICE);
		this.tv_chitiet_Cao.setText(paramDotBienKhoiLuongItem.HIGH);
		this.tv_chitiet_Thap.setText(paramDotBienKhoiLuongItem.LOW);
		this.dialog.show();
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
		case GETARRANGELISTQUANTITY:
			if (rObj.obj != null) {
				this.listListViewItem.clear();
				this.listListViewItem
						.addAll((List<DotBienKhoiLuongItem>) rObj.obj);
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
 * com.fss.fragment.BoLocDBKhoiLuong JD-Core Version: 0.6.0
 */