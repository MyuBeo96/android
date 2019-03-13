package com.fss.mobiletrading.function.orderlist;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fscuat.mobiletrading.MainActivity_Mobile;
import com.fscuat.mobiletrading.design.CustomPassLayout;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.function.watchlist.StockIndex;
import com.fss.mobiletrading.function.watchlist.StockIndexView;
import com.fss.mobiletrading.keyboard.KBoardPrice;
import com.fss.mobiletrading.keyboard.KBoardQuantity;
import com.fss.mobiletrading.object.FindStock;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.StockDetailsItem;
import com.fss.mobiletrading.object.StockItem;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MSTradeAppConfig;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.Edittext_Gia;
import com.fscuat.mobiletrading.design.Edittext_LoaiLenh;
import com.fscuat.mobiletrading.design.Edittext_SoLuong;
import com.fscuat.mobiletrading.design.LabelContentLayout;

import java.util.ArrayList;
import java.util.List;

import static com.fss.mobiletrading.object.StockItem.HOSE;

public class AmendOrder extends AbstractFragment {

	static final String FINDSTOCK = "FindStockService#FINDSTOCK";
	static final String AMENDORDER = "CheckOrderService#AMENDORDER";
	String findstockKey;

	protected Button btn_Ban;
	protected Button btn_DatLenh;
	protected Button btn_Mua;
	protected EditText edt_MaCK;

	protected Edittext_SoLuong edt_SplitQtty;
	protected Edittext_Gia edttg_Gia;
	protected Edittext_LoaiLenh edttg_LoaiLenh;
	protected Edittext_SoLuong edttg_SoLuong;
	protected CustomPassLayout edt_TradingPw;
	protected FindStock findStock;

	protected TextView tv_GiaKhopCuoi;
	protected TextView tv_San;
	protected TextView tv_Tran;
	protected TextView tv_RefPrice;

	protected TextView lbl_PPSE;
	protected TextView lbl_Rttbuy;
	protected TextView lbl_TyLeVay;
	protected TextView lbl_KLduocmua;

	protected TextView tv_PPSE;
	protected TextView tv_Rttbuy;
	protected TextView tv_TyLeVay;
	protected TextView tv_KLduocmua;

	protected TextView lbl_KLduocban;
	protected TextView lbl_ChoVe;
	protected TextView lbl_PP0;
	protected TextView lbl_Rttsell;
	protected TextView lbl_NNBan;
	protected TextView lbl_NNBanSell;

	protected TextView tv_KLduocban;
	protected TextView tv_ChoVe;
	protected TextView tv_PP0;
	protected TextView tv_Rttsell;
	protected TextView tv_Company;
	protected TextView tv_RoomNN;
	protected TextView tv_NNMua;
	protected TextView tv_NNBan;
	protected TextView tv_NNBanSell;

	protected LinearLayout stockIndex;
	protected ImageButton checkboxTradingpass;

	AmendOrderModel orderSetParams;
	boolean changedAfacctno = false;
	StockIndexView stockIndexView;

	/**
	 * only tablet
	 */
	protected TextView tv_Afacctno;
	/**
	 * only tablet
	 */
	KBoardPrice kBoardPrice;
	/**
	 * only tablet
	 */
	KBoardQuantity kBoardQuantity;

	public static AmendOrder newInstance(MainActivity mActivity) {
		AmendOrder self = new AmendOrder();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.AmendOrder);
		return self;
	}

	protected int getLayoutId() {
		return R.layout.placeorder;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {

		View view = inflater.inflate(getLayoutId(), viewGroup, false);
		btn_Ban = (Button) view.findViewById(R.id.btn_DatLenh_Ban);
		btn_Mua = (Button) view.findViewById(R.id.btn_DatLenh_Mua);
		btn_DatLenh = (Button) view.findViewById(R.id.btn_DatLenh_DatLenh);
		edt_MaCK = ((EditText) view.findViewById(R.id.autoEdt_DatLenh_Symbol));
		edttg_SoLuong = (Edittext_SoLuong) view
				.findViewById(R.id.cus_edt_DatLenh_SoLuong);
		edttg_LoaiLenh = (Edittext_LoaiLenh) view
				.findViewById(R.id.cus_edt_DatLenh_LoaiLenh);
		edttg_Gia = (Edittext_Gia) view.findViewById(R.id.cus_edt_DatLenh_Gia);
		edt_TradingPw = (CustomPassLayout) view.findViewById(R.id.edt_amendorder_TradingCode);
		checkboxTradingpass = edt_TradingPw.getcheckbox();
		kBoardPrice = (KBoardPrice) view
				.findViewById(R.id.t_placeorder_kboardsymbol_price);
		kBoardQuantity = (KBoardQuantity) view
				.findViewById(R.id.t_placeorder_kboardsymbol_quantity);
		tv_GiaKhopCuoi = (TextView) view
				.findViewById(R.id.text_DatLenh_GiaKhopCuoi);
		tv_Afacctno = (TextView) view
				.findViewById(R.id.autoEdt_DatLenh_Afacctno);
		tv_Tran = (TextView) view.findViewById(R.id.text_DatLenh_Tran);
		tv_San = (TextView) view.findViewById(R.id.text_DatLenh_San);
		tv_RefPrice = (TextView) view.findViewById(R.id.text_DatLenh_refprice);

		lbl_PPSE = (TextView) view.findViewById(R.id.lbl_datlenh_SucMuaBuy);
		tv_PPSE = (TextView) view.findViewById(R.id.text_datlenh_SucMuaBuy);

		lbl_Rttbuy = (TextView) view.findViewById(R.id.lbl_datlenh_rttbuy);
		tv_Rttbuy = (TextView) view.findViewById(R.id.text_datlenh_rttbuy);

		lbl_TyLeVay = (TextView) view.findViewById(R.id.lbl_datlenh_tylevay);
		tv_TyLeVay = (TextView) view.findViewById(R.id.text_datlenh_tylevay);

		lbl_KLduocmua = (TextView) view
				.findViewById(R.id.lbl_datlenh_KLduocmua);
		tv_KLduocmua = (TextView) view
				.findViewById(R.id.text_datlenh_KLduocmua);

		lbl_Rttsell = (TextView) view.findViewById(R.id.lbl_datlenh_rttsell);
		tv_Rttsell = (TextView) view.findViewById(R.id.text_datlenh_rttsell);

		lbl_KLduocban = (TextView) view
				.findViewById(R.id.lbl_datlenh_KLduocban);
		tv_KLduocban = (TextView) view
				.findViewById(R.id.text_datlenh_KLduocban);

		lbl_ChoVe = (TextView) view.findViewById(R.id.lbl_datlenh_ChoVe);
		tv_ChoVe = (TextView) view.findViewById(R.id.text_datlenh_ChoVe);

		lbl_PP0 = (TextView) view.findViewById(R.id.lbl_datlenh_SucMuaSell);
		tv_PP0 = (TextView) view.findViewById(R.id.text_datlenh_SucMuaSell);

		tv_Company = (TextView) view.findViewById(R.id.text_DatLenh_Company);
		tv_RoomNN = (TextView) view.findViewById(R.id.text_DatLenh_RoomNN);
		tv_NNMua = (TextView) view.findViewById(R.id.text_DatLenh_NNMua);

		lbl_NNBan = (TextView) view.findViewById(R.id.lbl_datlenh_nnban);
		tv_NNBan = (TextView) view.findViewById(R.id.text_DatLenh_NNBan);

		lbl_NNBanSell = (TextView) view.findViewById(R.id.lbl_datlenh_nnbansell);
		tv_NNBanSell = (TextView) view.findViewById(R.id.text_DatLenh_NNBanSell);

		edt_SplitQtty = ((Edittext_SoLuong) view
				.findViewById(R.id.edttg_DatLenh_SplitQtty));

		if (DeviceProperties.isTablet) {
			view.findViewById(R.id.selector_placeordertype).setVisibility(
					View.GONE);
		}
		stockIndex = (LinearLayout) view
				.findViewById(R.id.container_placeorder_stockindex);
		// keyboardQuantity = (KeyboardQuantity) view
		// .findViewById(R.id.kb_datlenh_quantity);
		// keyboardPrice = (KeyboardPrice) view
		// .findViewById(R.id.kb_datlenh_price);
		btn_DatLenh.setText(R.string.AmendOrder);
		Common.setupUI(view.findViewById(R.id.datlenh), mainActivity);
		initialise();
		initialiseListener();

		return view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog d = super.onCreateDialog(savedInstanceState);
		Window w = d.getWindow();
		w.setBackgroundDrawableResource(R.color.transparent);
		w.setGravity(Gravity.LEFT);
		WindowManager.LayoutParams l = w.getAttributes();
		l.x = getDimenResource(R.dimen.t_framewatchlist_width);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return d;
	}

	@Override
	public void onStart() {
		super.onStart();
		int width = getDimenResource(R.dimen.t_placeorder_dialog_width);
		int height = LayoutParams.WRAP_CONTENT;
		if (getDialog() != null) {
			getDialog().getWindow().setLayout(width, height);
		}
	}

	private void initialise() {
		btn_Mua.setEnabled(false);
		btn_Ban.setEnabled(false);
		edt_MaCK.setEnabled(false);
		edttg_LoaiLenh.setEnabled(false);
		edt_SplitQtty.setEnabled(false);
		if (DeviceProperties.isTablet) {
			if (stockIndexView == null) {
				stockIndexView = new StockIndexView(getActivity(), null);
			}
			stockIndex.addView(stockIndexView);
		}

	}

	TextWatcher edt_gia_textwatcher;

	private void initialiseListener() {
		checkboxTradingpass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				checkboxTradingpass.setSelected(!checkboxTradingpass.isSelected());
			}
		});
		edttg_SoLuong.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if (DeviceProperties.isTablet) {
						showKBQuantity(true, edttg_SoLuong.toEditText());
					} else {
						mainActivity.showKBoardQuantity(true,
								edttg_SoLuong.toEditText());
					}
				} else {
					if (DeviceProperties.isTablet) {
						showKBQuantity(false, null);
					} else {
						mainActivity.showKBoardQuantity(false,
								edttg_SoLuong.toEditText());
					}
					if (edttg_SoLuong.getText().length() == 0) {
						edttg_SoLuong.setText(orderSetParams.quantityOrder);
					}
				}

			}
		});
		edttg_Gia.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if (DeviceProperties.isTablet) {
						showKBPrice(true, edttg_Gia.toEditText());
					} else {
						mainActivity.showKBoardPrice(true,
								edttg_Gia.toEditText());
					}

				} else {
					if (DeviceProperties.isTablet) {
						showKBPrice(false, null);
					} else {
						mainActivity.showKBoardPrice(false, null);
					}
					if (edttg_Gia.getText().length() == 0) {
						edttg_Gia.setText(orderSetParams.priceOrder);
					} else if (edttg_Gia.getText().length() > 0
							&& !edttg_Gia.getText().toString().contains(".")) {
						edttg_Gia
								.setText(edttg_Gia.getText().toString() + ".0");
					}
				}
			}
		});
		if (edt_gia_textwatcher == null) {
			edt_gia_textwatcher = new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					if (s != null) {
						CallFindStock();
					}
				}
			};
		}

		edttg_Gia.addTextChangedListener(edt_gia_textwatcher);
		btn_DatLenh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				CallAmendOrder(orderSetParams);
			}
		});

	}

	@Override
	public void onPause() {
		super.onPause();
		if (DeviceProperties.isTablet) {
			stockIndex.removeAllViews();
			stockIndexView.clearView();
		}
	}
	private void customDisplay(){
		if(StaticObjectManager.saveTradingPass){
			edt_TradingPw.setText(StaticObjectManager.tradingPass);
			checkboxTradingpass.setSelected(StaticObjectManager.saveTradingPass);
			edt_TradingPw.setVisibility(View.GONE);
		}
		else
			edt_TradingPw.setText(StringConst.EMPTY);
	}
	@Override
	public void onResume() {
		super.onResume();
		customDisplay();
		if (orderSetParams != null) {
			if (DeviceProperties.isTablet) {
				StockIndex.CallStockDetails(StringConst.EMPTY,
						orderSetParams.symbolOrder, AmendOrder.this,
						StockIndex.STOCKDETAILS);
				tv_Afacctno.setText(StaticObjectManager.acctnoItem.toString());

			}
			if (orderSetParams.sideOrder.equals(PlaceOrder.SIDE_NS)) {
				functionBtnBanClick();
			} else {
				functionBtnMuaClick();
			}
			edt_MaCK.setText(orderSetParams.symbolOrder);
			StockItem stockItemCurrent = StaticObjectManager
					.getStockItem(orderSetParams.symbolOrder);
			if (stockItemCurrent != null) {
				edttg_SoLuong.tradeplace = stockItemCurrent.getTradePlace();
				edttg_Gia.tradeplace = stockItemCurrent.getTradePlace();
			} else {
				edttg_SoLuong.tradeplace = StringConst.EMPTY;
				edttg_Gia.tradeplace = StringConst.EMPTY;
			}
			edttg_SoLuong.setText(orderSetParams.quantityOrder);
			edttg_LoaiLenh.setTextWhenDisable(orderSetParams.priceType);
			edttg_Gia.setText(String.valueOf(orderSetParams.priceOrder));
			String priceNew;
			Double price;
			if(orderSetParams.priceOrder.contains(",")){
			priceNew = orderSetParams.priceOrder.replaceAll(",", "");
				price = (Double.parseDouble(priceNew)/1000);
				Log.i("sualenh",orderSetParams.priceOrder +"\n"+ price);
				edttg_Gia.setText(String.valueOf(price));
			}
			// edt_SplitQtty.setText(orderSetParams.splitOrder);
			if (orderSetParams.isQuantityAmend) {
				edttg_SoLuong.setEnabled(true);
			} else {
				edttg_SoLuong.setEnabled(false);
			}
			if (orderSetParams.isPriceAmend) {
				edttg_Gia.setEnabled(true);
			} else {
				edttg_Gia.setEnabled(false);
			}
		}

		//Check không được sửa field chữ mờ hơn
		edttg_LoaiLenh.getEditContext().setTextColor(getColorResource(R.color.color_background_edittext));

		if(edttg_SoLuong.tradeplace== StockItem.HOSE) {
			edttg_SoLuong.toEditText().setTextColor(getColorResource(R.color.color_background_edittext));
		}
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		 setBackLogoAction();
	}

	protected void functionBtnBanClick() {

		// Ä‘Ã´Ì‰i maÌ€u button mua baÌ�n
		btn_Mua.setSelected(false);
		btn_Ban.setSelected(true);

		// hiÃªÌ‰n thiÌ£ vaÌ€ Ã¢Ì‰n caÌ�c trÆ°Æ¡Ì€ng tÆ°Æ¡ng Æ°Ì�ng
		lbl_KLduocban.setVisibility(TextView.VISIBLE);
		lbl_ChoVe.setVisibility(TextView.VISIBLE);
		lbl_PP0.setVisibility(TextView.VISIBLE);
		lbl_Rttsell.setVisibility(TextView.VISIBLE);

		tv_KLduocban.setVisibility(TextView.VISIBLE);
		tv_ChoVe.setVisibility(TextView.VISIBLE);
		tv_PP0.setVisibility(TextView.VISIBLE);
		tv_Rttsell.setVisibility(TextView.VISIBLE);

		lbl_PPSE.setVisibility(TextView.GONE);
		lbl_TyLeVay.setVisibility(TextView.GONE);
		lbl_KLduocmua.setVisibility(TextView.GONE);
		lbl_Rttbuy.setVisibility(TextView.GONE);

		tv_PPSE.setVisibility(TextView.GONE);
		tv_TyLeVay.setVisibility(TextView.GONE);
		tv_KLduocmua.setVisibility(TextView.GONE);
		tv_Rttbuy.setVisibility(TextView.GONE);
		if (!DeviceProperties.isTablet) {
			lbl_NNBanSell.setVisibility(TextView.VISIBLE);
			tv_NNBanSell.setVisibility(TextView.VISIBLE);
			lbl_NNBan.setVisibility(TextView.GONE);
			tv_NNBan.setVisibility(TextView.GONE);
			((MainActivity_Mobile) getActivity()).setActionbarPlaceOrder(R.color.placeorder_sell_color);
		}
		btn_DatLenh.setBackgroundResource(R.drawable.background_sellbutton);
	}

	protected void functionBtnMuaClick() {

		// Ä‘Ã´Ì‰i maÌ€u button mua baÌ�n
		btn_Mua.setSelected(true);
		btn_Ban.setSelected(false);

		// hiÃªÌ‰n thiÌ£ vaÌ€ Ã¢Ì‰n caÌ�c trÆ°Æ¡Ì€ng tÆ°Æ¡ng Æ°Ì�ng
		lbl_KLduocban.setVisibility(TextView.GONE);
		lbl_ChoVe.setVisibility(TextView.GONE);
		lbl_PP0.setVisibility(TextView.GONE);
		lbl_Rttsell.setVisibility(TextView.GONE);

		tv_KLduocban.setVisibility(TextView.GONE);
		tv_ChoVe.setVisibility(TextView.GONE);
		tv_PP0.setVisibility(TextView.GONE);
		tv_Rttsell.setVisibility(TextView.GONE);

		lbl_PPSE.setVisibility(TextView.VISIBLE);
		lbl_TyLeVay.setVisibility(TextView.VISIBLE);
		lbl_KLduocmua.setVisibility(TextView.VISIBLE);
		lbl_Rttbuy.setVisibility(TextView.VISIBLE);

		tv_PPSE.setVisibility(TextView.VISIBLE);
		tv_TyLeVay.setVisibility(TextView.VISIBLE);
		tv_KLduocmua.setVisibility(TextView.VISIBLE);
		tv_Rttbuy.setVisibility(TextView.VISIBLE);
		if (!DeviceProperties.isTablet) {
			lbl_NNBan.setVisibility(TextView.VISIBLE);
			tv_NNBan.setVisibility(TextView.VISIBLE);
			lbl_NNBanSell.setVisibility(TextView.GONE);
			tv_NNBanSell.setVisibility(TextView.GONE);
			((MainActivity_Mobile) getActivity()).setActionbarPlaceOrder(R.color.placeorder_buy_color);
		}
		btn_DatLenh.setBackgroundResource(R.drawable.backgroundbutton);
	}

	protected void CallAmendOrder(AmendOrderModel item) {

		if (edttg_Gia.getText().length() == 0
				&& edttg_SoLuong.getText().length() == 0) {
			showDialogMessage(getStringResource(R.string.thong_bao),
					getStringResource(R.string.ChuaNhapDL));
			return;
		}
		if (edt_TradingPw.getText().length() == 0) {
			if (edt_TradingPw.getVisibility() != View.VISIBLE) {
				edt_TradingPw.setVisibility(View.VISIBLE);
			}
			showDialogMessage(getStringResource(R.string.thong_bao), getStringResource(R.string.NhapPin));
			edt_TradingPw.requestFocus();
			return;
		}
			List<String> list_key = new ArrayList<String>();
			List<String> list_value = new ArrayList<String>();
			{
				list_key.add("link");
				list_value.add(getStringResource(R.string.controller_AmendOrder));
			}
			{
				list_key.add("OrderId");
				list_value.add(item.orderID);
			}
			{
				list_key.add("CustodyCd");
				list_value.add(item.custodycd);
			}
			{
				list_key.add("AfAcctno");
				list_value.add(item.afacctno);
			}
			{
				list_key.add("Symbol");
				list_value.add(item.symbolOrder);
			}
			{
				list_key.add("Side");
				list_value.add(item.sideOrder);
			}
			{
				list_key.add("Qtty");
				list_value.add(edttg_SoLuong.getText().toString());
			}
			{
				list_key.add("PriceType");
				list_value.add(item.priceType);
			}
			{
				list_key.add("Price");
				list_value.add(edttg_Gia.getText().toString());
			}
			{
				list_key.add("TradingPassword");
				list_value.add(edt_TradingPw.getText().toString());
			}

			StaticObjectManager.connectServer.callHttpPostService(AMENDORDER, this,
					list_key, list_value);
			btn_DatLenh.setEnabled(false);
		}


	protected void CallFindStock() {
		if (orderSetParams == null) {
			clearFormStockInfo();
			return;
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_FindStock);
		}
		{
			list_key.add("symbol");
			list_value.add(orderSetParams.symbolOrder);
		}
		{
			list_key.add("side");
			list_value.add(orderSetParams.sideOrder);
		}
		{
			list_key.add("price");
			list_value.add(edttg_Gia.getText().toString());
		}
		{
			list_key.add("qty");
			list_value.add(orderSetParams.quantityOrder);
		}
		{
			list_key.add("ordertype");
			list_value.add(orderSetParams.priceType);
		}
		{
			list_key.add("afacctno");
			list_value.add(orderSetParams.afacctno);
		}
		findstockKey = FINDSTOCK + StringConst.SEMI
				+ String.valueOf(list_value.hashCode());
		StaticObjectManager.connectServer.callHttpPostService(findstockKey,
				this, list_key, list_value);

	}

	protected void clearFormStockInfo() {

		tv_PPSE.setText(StringConst.EMPTY);
		tv_Rttbuy.setText(StringConst.EMPTY);
		tv_TyLeVay.setText(StringConst.EMPTY);
		tv_KLduocmua.setText(StringConst.EMPTY);

		tv_KLduocban.setText(StringConst.EMPTY);
		tv_ChoVe.setText(StringConst.EMPTY);
		tv_PP0.setText(StringConst.EMPTY);
		tv_Rttsell.setText(StringConst.EMPTY);

		tv_Tran.setText(StringConst.EMPTY);
		tv_San.setText(StringConst.EMPTY);
		tv_GiaKhopCuoi.setText(StringConst.EMPTY);
		tv_RefPrice.setText(StringConst.EMPTY);
		edt_TradingPw.setText(StringConst.EMPTY);
		tv_NNBan.setText(StringConst.EMPTY);
		tv_NNBanSell.setText(StringConst.EMPTY);
		tv_NNMua.setText(StringConst.EMPTY);
		tv_Company.setText(StringConst.EMPTY);
		tv_RoomNN.setText(StringConst.EMPTY);
	}

	protected void displayStockInfo() {

		if (findStock != null) {
			tv_Company.setText(findStock.stockInfo.stockname);
			tv_RoomNN.setText(Common
					.formatAmount(findStock.stockInfo.foreignRemain));
			tv_NNMua.setText(Common
					.formatAmount(findStock.stockInfo.foreignBuy));
			tv_NNBan.setText(Common
					.formatAmount(findStock.stockInfo.foreignSell));
			tv_NNBanSell.setText(Common
					.formatAmount(findStock.stockInfo.foreignSell));
			tv_PPSE.setText(Common
					.formatAmount(findStock.stockInfo.CashAvaiable));
			tv_PP0.setText(Common
					.formatAmount(findStock.stockInfo.CashAvaiable));
			tv_KLduocmua.setText(Common
					.formatAmount(findStock.orderInfo.MaxQty));
			tv_KLduocban.setText(Common
					.formatAmount(findStock.orderInfo.MaxQty));
			tv_ChoVe.setText(Common.formatAmount(findStock.stockInfo.Receiving));
			tv_TyLeVay.setText(Common
					.formatAmount(findStock.orderInfo.MRRATIOLOAN));

			tv_Rttbuy.setText(Common.formatAmount(findStock.orderInfo.Rtt));

			tv_Rttsell.setText(Common.formatAmount(findStock.orderInfo.Rtt));

			tv_Tran.setText(findStock.stockInfo.CeilPrice);
			tv_San.setText(findStock.stockInfo.FloorPrice);
			tv_RefPrice.setText(findStock.stockInfo.RefPrice);
			tv_GiaKhopCuoi.setText(findStock.stockInfo.ClosePrice);
			tv_GiaKhopCuoi.setTextColor(getColorResource(Common.getColor(
					findStock.stockInfo.ClosePrice,
					findStock.stockInfo.CeilPrice,
					findStock.stockInfo.FloorPrice,
					findStock.stockInfo.RefPrice)));

		}
	}

	@Override
	public void onShowed() {
		super.onShowed();
		if (changedAfacctno) {
			mainActivity.displayFragment(PlaceOrder.class.getName());
			changedAfacctno = false;
		}

	}

	@Override
	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		changedAfacctno = true;
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		String[] keys = key.split(StringConst.SEMI);
		switch (keys[0]) {
		case FINDSTOCK:
			if (rObj.obj != null && key.equals(findstockKey)) {
				findStock = ((FindStock) rObj.obj);
				displayStockInfo();
				edttg_Gia.setCeilPrice(findStock.stockInfo.CeilPrice);
				edttg_Gia.setFloorPrice(findStock.stockInfo.FloorPrice);
				edttg_Gia.setClosePrice(findStock.stockInfo.ClosePrice);
			}
			break;
		case StockIndex.STOCKDETAILS:
			if (rObj.obj != null) {
				StockDetailsItem stockDetailsItem = ((StockDetailsItem) rObj.obj);
				stockIndexView.displayView(stockDetailsItem);
			}
			break;
		case AMENDORDER:
			StaticObjectManager.saveTradingPass= checkboxTradingpass.isSelected();
			if(StaticObjectManager.saveTradingPass)
				StaticObjectManager.tradingPass = edt_TradingPw.getText().toString();
			else
				StaticObjectManager.tradingPass = StringConst.EMPTY;
			showDialogMessage(getStringResource(R.string.thong_bao),
					getStringResource(R.string.Giaodichthanhcong),
					new SimpleAction() {

						@Override
						public void performAction(Object obj) {
							if (mainActivity != null) {
								onDismiss(getDialog());
								mainActivity
										.displayFragment(NormalOrderList.class
												.getName());
							}
						}
					});
		default:
			break;
		}
	}

	public final static int ERR_AT_SYMBOL = 1;
	public final static int ERR_AT_SIDE = 2;
	public final static int ERR_AT_QTTY = 4;
	public final static int ERR_AT_PRICE = 3;
	public final static int ERR_AT_PIN = 5;
	public final static int ERR_AT_QUOTEQTTY = 6;
	public final static int ERR_AT_LIMITPRICE = 7;
	public final static int ERR_AT_AFACCTNO = 8;
	public final static int ERR_AT_PRICETYPE = 9;
	public final static int ERR_AT_FROMDATE = 10;
	public final static int ERR_AT_TODATE = 11;
	public final static int ERR_AT_SPLITQTTY = 12;

	@Override
	protected void processErrorOther(String key, ResultObj rObj) {
		super.processErrorOther(key, rObj);
		switch (key) {
		case AMENDORDER:
			int focusTo = (int) rObj.obj;
			switch (focusTo) {
			case ERR_AT_QTTY:
				if (edttg_SoLuong.isEnabled()) {
					edttg_SoLuong.requestFocus(View.FOCUS_RIGHT);
				}
				break;
			case ERR_AT_PRICE:
				if (edttg_Gia.isEnabled()) {
					edttg_Gia.requestFocus(View.FOCUS_RIGHT);
				}
				break;
			case ERR_AT_PRICETYPE:
				// edttg_LoaiLenh.requestFocus(View.FOCUS_RIGHT);
				break;
			case ERR_AT_SYMBOL:
				// edt_MaCK.requestFocus(View.FOCUS_RIGHT);
				break;
			case ERR_AT_SPLITQTTY:
				// edt_SplitQtty.requestFocus(View.FOCUS_RIGHT);
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void isReceivedResponse(String key) {
		super.isReceivedResponse(key);
		switch (key) {
		case AMENDORDER:
			btn_DatLenh.setEnabled(true);
			break;
		default:
			break;
		}
	}

	@Override
	public void getArgument(Object obj) {
		super.getArgument(obj);
		if (obj instanceof AmendOrderModel) {
			orderSetParams = (AmendOrderModel) obj;
		}
	}

	private void showKBPrice(boolean isShow, View v) {
		if (isShow) {
			kBoardPrice.show(v);
			kBoardQuantity.setVisibility(View.GONE);
		} else {
			kBoardPrice.hide();
			kBoardQuantity.setVisibility(View.VISIBLE);
		}

	}

	private void showKBQuantity(boolean isShow, View v) {
		if (isShow) {
			kBoardQuantity.show(v);
			kBoardPrice.setVisibility(View.GONE);
		} else {
			kBoardQuantity.hide();
			kBoardPrice.setVisibility(View.VISIBLE);
		}
	}

}
