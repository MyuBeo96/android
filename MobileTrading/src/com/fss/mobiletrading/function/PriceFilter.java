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

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.DotBienGia_Adapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.DotBienGiaItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;

public class PriceFilter extends AbstractFragment {
	int DotBienTrong;
	final String GETARRANGELISTPRICE = "GetArrangeListPriceService#GETARRANGELISTPRICE";
	int KLDotBien;
	ArrayAdapter<String> adapterBoLocCP;
	ArrayAdapter<String> adapterDotBienTrong;
	ArrayAdapter<String> adapterKLDotBien;
	DotBienGia_Adapter adapterListView;
	Button btn_CapNhat;
	Button btn_giacaonhat;
	Button btn_giathapnhat;
	Dialog dialog;
	int gia;
	String giaBtn;
	String[] listBoLocCP;
	String[] listDotBienTrong;
	String[] listKLDotBien;
	List<DotBienGiaItem> listListViewItem;
	ListView lv_bolocgia;
	Spinner spn_boloccp;
	Spinner spn_dotbientrong;
	Spinner spn_kldotbien;
	TextView tv_Desc;
	TextView tv_chitiet_Change;
	TextView tv_chitiet_ClosePrice;
	TextView tv_chitiet_EPS;
	TextView tv_chitiet_HighInMonth;
	TextView tv_chitiet_LowInMonth;
	TextView tv_chitiet_PB;
	TextView tv_chitiet_PE;
	TextView tv_chitiet_PercentChange;
	TextView tv_chitiet_Symbol;
	TextView tv_chitiet_TongGTGD;
	TextView tv_chitiet_TongKLGD;

	public static PriceFilter newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		PriceFilter self = new PriceFilter();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.StickerFilter);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.boloc_dotbiengia,
				paramViewGroup, false);
		this.lv_bolocgia = ((ListView) localView
				.findViewById(R.id.listview_bolocgia));
		this.spn_boloccp = ((Spinner) localView.findViewById(R.id.spn_bolocgia));
		this.spn_dotbientrong = ((Spinner) localView
				.findViewById(R.id.spn_bolocgia_DotBienTrong));
		this.spn_kldotbien = ((Spinner) localView
				.findViewById(R.id.spn_bolocgia_KLDotBien));
		this.btn_giacaonhat = ((Button) localView
				.findViewById(R.id.btn_bolocgia_GiaCaoNhat));
		this.btn_giathapnhat = ((Button) localView
				.findViewById(R.id.btn_bolocgia_GiaThapNhat));
		this.tv_Desc = ((TextView) localView
				.findViewById(R.id.text_bolocgia_Desc));
		this.btn_CapNhat = ((Button) localView
				.findViewById(R.id.btn_bolocgia_CapNhat));
		initialise();
		initialiseListener();
		return localView;
	}

	private void initialise() {
		this.listListViewItem = new ArrayList<DotBienGiaItem>();
		this.adapterListView = new DotBienGia_Adapter(getActivity(),
				android.R.layout.simple_list_item_1, this.listListViewItem);
		this.lv_bolocgia.setAdapter(this.adapterListView);
		this.listBoLocCP = getResources().getStringArray(R.array.listBoLocCP);
		this.adapterBoLocCP = new ArrayAdapter<>(getActivity(),
				R.layout.spinner_text, this.listBoLocCP);
		this.adapterBoLocCP.setDropDownViewResource(R.layout.spinner_dropdown);
		this.spn_boloccp.setAdapter(this.adapterBoLocCP);
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
				CallGetArrangeListPrice();
			}
		});
		this.lv_bolocgia.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		this.lv_bolocgia.setOnItemClickListener(new OnItemClickListener() {

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
		this.btn_giacaonhat.setActivated(true);
		this.giaBtn = this.btn_giacaonhat.getText().toString();
		this.spn_boloccp.setSelection(BoLocCP_ViewPager.DOT_BIEN_GIA);
	}

	private void CallGetArrangeListPrice() {
		ArrayList<String> list_key = new ArrayList<String>();
		ArrayList<String> list_value = new ArrayList<String>();
		{
			list_key.add("giang");
			list_value
					.add(getStringResource(R.string.controller_GetArrangeListPrice));
		}
		{
			list_key.add("l_dkgia");
			list_value.add(String.valueOf(this.gia));
		}
		{
			list_key.add("l_dktg");
			list_value.add(String.valueOf(this.spn_dotbientrong
					.getSelectedItemPosition()));
		}
		{
			list_key.add("l_dkklgd");
			list_value.add(String.valueOf(this.spn_kldotbien
					.getSelectedItemPosition()));
		}
		StaticObjectManager.connectServer.callHttpPostService(
				GETARRANGELISTPRICE, this, list_key, list_value);
	}

	private void CreateDialogChiTiet() {
		this.dialog = new Dialog(getActivity());
		this.dialog.requestWindowFeature(1);
		this.dialog.setCancelable(true);
		this.dialog.setContentView(R.layout.boloc_dotbiengia_chitiet);
		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
		localLayoutParams.copyFrom(this.dialog.getWindow().getAttributes());
		localLayoutParams.width = LayoutParams.MATCH_PARENT;
		localLayoutParams.height = LayoutParams.WRAP_CONTENT;
		this.dialog.getWindow().setAttributes(localLayoutParams);
		this.tv_chitiet_Symbol = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengia_chitiet_Symbol));
		this.tv_chitiet_ClosePrice = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengia_chitiet_ClosePrice));
		this.tv_chitiet_Change = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengia_chitiet_Change));
		this.tv_chitiet_PercentChange = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengia_chitiet_PercentChange));
		this.tv_chitiet_TongKLGD = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengia_chitiet_TongKLGD));
		this.tv_chitiet_TongGTGD = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengia_chitiet_TongGTGD));
		this.tv_chitiet_HighInMonth = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengia_chitiet_HighInMonth));
		this.tv_chitiet_LowInMonth = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengia_chitiet_LowInMonth));
		this.tv_chitiet_PE = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengia_chitiet_PE));
		this.tv_chitiet_EPS = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengia_chitiet_EPS));
		this.tv_chitiet_PB = ((TextView) this.dialog
				.findViewById(R.id.text_dotbiengia_chitiet_PB));
	}

	private void DisplayDialogChiTiet(DotBienGiaItem paramDotBienGiaItem) {
		this.tv_chitiet_Symbol.setText(paramDotBienGiaItem.SYMBOL);
		this.tv_chitiet_ClosePrice.setText(paramDotBienGiaItem.REALPRICE);
		this.tv_chitiet_Change.setText(paramDotBienGiaItem.REALPRICE_CHANGE);
		this.tv_chitiet_PercentChange
				.setText(paramDotBienGiaItem.REALPRICE_PERCENT_CHANGE);
		this.tv_chitiet_TongKLGD.setText(paramDotBienGiaItem.KLGD);
		this.tv_chitiet_TongGTGD.setText(paramDotBienGiaItem.GTGD);
		this.tv_chitiet_HighInMonth.setText(paramDotBienGiaItem.HIGH);
		this.tv_chitiet_LowInMonth.setText(paramDotBienGiaItem.LOW);
		this.tv_chitiet_PE.setText(paramDotBienGiaItem.PE);
		this.tv_chitiet_EPS.setText(paramDotBienGiaItem.EPS);
		this.tv_chitiet_PB.setText(paramDotBienGiaItem.PB);
		this.dialog.show();
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
		case GETARRANGELISTPRICE:
			if (rObj.obj != null) {
				this.listListViewItem.clear();
				this.listListViewItem.addAll((List<DotBienGiaItem>) rObj.obj);
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
 * com.fss.fragment.BoLocDBGia JD-Core Version: 0.6.0
 */