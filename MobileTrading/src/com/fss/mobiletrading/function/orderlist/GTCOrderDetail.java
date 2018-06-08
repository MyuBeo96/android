package com.fss.mobiletrading.function.orderlist;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fss.mobiletrading.consts.StringConst;
import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.SolenhCT_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.object.OrderDetailsItem;
import com.fss.mobiletrading.object.SolenhItem;
import com.fscuat.mobiletrading.Login;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.MyActionBar.Action;
import com.fscuat.mobiletrading.design.LabelContentLayout;
import com.fscuat.mobiletrading.design.NumberEditText;
import com.fscuat.mobiletrading.design.VerticalListview;

public class GTCOrderDetail extends OrderDetail {

	LabelContentLayout tv_chitiet_fromDate;
	LabelContentLayout tv_chitiet_toDate;
	LabelContentLayout edt_TradingPw;

	public static GTCOrderDetail newInstance(MainActivity mActivity) {
		GTCOrderDetail self = new GTCOrderDetail();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.OrderDetail);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.orderdetail, container, false);

		tv_chitiet_CustodyCd = ((LabelContentLayout) view
				.findViewById(R.id.text_solenh_chitiet_custodycd));
		tv_chitiet_TieuKhoan = ((LabelContentLayout) view
				.findViewById(R.id.text_solenh_chitiet_afacctno));
		tv_chitiet_MaCK = ((LabelContentLayout) view
				.findViewById(R.id.text_solenh_chitiet_symbol));
		tv_chitiet_OrderSide = ((LabelContentLayout) view
				.findViewById(R.id.text_solenh_chitiet_side));
		tv_chitiet_Gia = ((LabelContentLayout) view
				.findViewById(R.id.edt_solenh_chitiet_orderprice));
		tv_chitiet_SoLuong = ((LabelContentLayout) view
				.findViewById(R.id.edt_solenh_chitiet_orderquantity));
		tv_chitiet_KLKhop = ((LabelContentLayout) view
				.findViewById(R.id.text_solenh_chitiet_matchquantity));
		tv_chitiet_TrangThai = ((LabelContentLayout) view
				.findViewById(R.id.text_solenh_chitiet_status));
		tv_chitiet_PriceType = (LabelContentLayout) view
				.findViewById(R.id.text_solenh_chitiet_pricetype);
		tv_chitiet_fromDate = ((LabelContentLayout) view
				.findViewById(R.id.text_solenh_chitiet_fromDate));
		tv_chitiet_toDate = ((LabelContentLayout) view
				.findViewById(R.id.text_solenh_chitiet_toDate));
		edt_TradingPw = (LabelContentLayout) view.findViewById(R.id.edt_orderdetail_TradingCode);
		view.findViewById(R.id.layout_solenh_chitiet_ChiTietKhop)
				.setVisibility(View.GONE);

		tv_chitiet_fromDate.setVisibility(View.VISIBLE);
		tv_chitiet_toDate.setVisibility(View.VISIBLE);

		tv_chitiet_Gia.setEnabled(false);
		tv_chitiet_SoLuong.setEnabled(false);

		lv_SolenhCT = ((VerticalListview) view
				.findViewById(R.id.listview_solenhthuongCT));
		btn_chitiet_HuyLenh = ((Button) view
				.findViewById(R.id.btn_solenh_chitiet_HuyLenh));
		btn_chitiet_SuaLenh = ((Button) view
				.findViewById(R.id.btn_solenh_chitiet_SuaLenh));
		btn_chitiet_SuaLenh.setVisibility(Button.GONE);

		tv_chitiet_OrderSide.getContent().setAllCaps(true);
		initData();
		initListener();
		return view;
	}

	@Override
	protected void initData() {
		if (listOrderDetails == null) {
			listOrderDetails = new ArrayList<OrderDetailsItem>();
		} else {
			listOrderDetails.clear();
		}
		if (adapterSolenhCT == null) {
			adapterSolenhCT = new SolenhCT_Adapter(mainActivity,
					android.R.layout.simple_list_item_1, listOrderDetails);
		}
		lv_SolenhCT.setAdapter(adapterSolenhCT);
	}

	protected void displayOrderDetail(SolenhItem item) {

		if (item.isCancellable.matches("true")) {
			btn_chitiet_HuyLenh.setEnabled(true);
		} else {
			btn_chitiet_HuyLenh.setEnabled(false);
		}
		tv_chitiet_CustodyCd.setText(item.CustodyCd);
		tv_chitiet_TieuKhoan.setText(item.AfAcctno);
		tv_chitiet_MaCK.setText(item.Symbol);
		tv_chitiet_PriceType.setText(item.PriceType);
		tv_chitiet_Gia.setText(item.Price);
		tv_chitiet_SoLuong.setText(Common.formatAmount(item.Qtty));
		tv_chitiet_KLKhop.setText(Common.formatAmount(item.Matched));
		tv_chitiet_TrangThai.setText(item.Status);
		tv_chitiet_fromDate.setText(item.fromDate);
		tv_chitiet_toDate.setText(item.toDate);
		edt_TradingPw.setText(StringConst.EMPTY);

		if (item.Side.equals(PlaceOrder.SIDE_NB)) {
			tv_chitiet_OrderSide.setText(getStringResource(R.string.Mua));
			tv_chitiet_OrderSide.setActivated(true);
			tv_chitiet_PriceType.setActivated(true);
			tv_chitiet_SoLuong.setActivated(true);
			tv_chitiet_KLKhop.setActivated(true);
			tv_chitiet_Gia.setActivated(true);

		} else {
			tv_chitiet_OrderSide.setText(getStringResource(R.string.Ban));
			tv_chitiet_OrderSide.setActivated(false);
			tv_chitiet_PriceType.setActivated(false);
			tv_chitiet_SoLuong.setActivated(false);
			tv_chitiet_KLKhop.setActivated(false);
			tv_chitiet_Gia.setActivated(false);
		}

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		mainActivity.removeAllAction();
	}

	@Override
	protected void CallOrderDetails(String orderId, String pv_custodycd) {
	}

	protected void CallCancelOrder(SolenhItem item) {
		if(edt_TradingPw.getText().length()!=0){
		if (item != null) {
			List<String> list_key = new ArrayList<String>();
			List<String> list_value = new ArrayList<String>();
			{
				list_key.add("link");
				list_value
						.add(getStringResource(R.string.controller_CancelOrderGTC));
			}
			{
				list_key.add("OrderId");
				list_value.add(item.OrderId);
			}
			{
				list_key.add("CustodyCd");
				list_value.add(item.CustodyCd);
			}
			{
				list_key.add("AfAcctno");
				list_value.add(item.AfAcctno);
			}
			{
				list_key.add("Symbol");
				list_value.add(item.Symbol);
			}
			{
				list_key.add("Side");
				list_value.add(item.Side);
			}
			{
				list_key.add("Qtty");
				list_value.add(item.Qtty);
			}
			{
				list_key.add("PriceType");
				list_value.add(item.PriceType);
			}
			{
				list_key.add("Price");
				list_value.add(item.Price);
			}
			{
				list_key.add("TradingPassword");
				list_value.add(edt_TradingPw.getText().toString());
			}

			StaticObjectManager.connectServer.callHttpPostService(CANCELORDER,
					this, list_key, list_value);
			btn_chitiet_HuyLenh.setEnabled(false);
		}
	}else {
		showDialogMessage(getStringResource(R.string.thong_bao),getStringResource(R.string.NhapPin));
			edt_TradingPw.setVisibility(View.VISIBLE);
		edt_TradingPw.requestFocus();
		}
	}

}
