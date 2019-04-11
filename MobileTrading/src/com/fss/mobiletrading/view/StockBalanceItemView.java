package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.StockBalanceItem;
import com.fss.mobiletrading.object.StockItem;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;

public class StockBalanceItemView extends LinearLayout {
	Context context;
	TextView tv_receivingT0;
	TextView tv_receivingT1;
	TextView tv_receivingT2;
	TextView tv_receivingT3;
	TextView tv_availableamount;
	TextView tv_symbol;
	/**
	 * only mobile
	 */
	TextView tv_tradeplace;
	/**
	 * only mobile
	 */
	TextView tv_fullstock;
	/**
	 * only tablet
	 */
	TextView tv_total;
	/**
	 * only tablet
	 */
	TextView tv_chovequyen;
	/**
	 * only tablet
	 */
	TextView tv_pendingmatch;
	/**
	 * only tablet
	 */
	TextView tv_marketprice;
	/**
	 * only tablet
	 */
	TextView tv_marketvalue;
	/**
	 * only tablet
	 */
	Button btn_buy;
	/**
	 * only tablet
	 */
	Button btn_sell;
	StockBalanceItem item;
	SimpleAction onSellClickListener;
	SimpleAction onBuyClickListener;

	public void setOnSellClickListener(SimpleAction onSellClickListener) {
		this.onSellClickListener = onSellClickListener;
	}

	public void setOnBuyClickListener(SimpleAction onBuyClickListener) {
		this.onBuyClickListener = onBuyClickListener;
	}

	public StockBalanceItemView(Context context) {
		super(context);
		this.context = context;
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.stockbalance_item, this);
		tv_symbol = ((TextView) findViewById(R.id.text_symbol));
		tv_tradeplace = ((TextView) findViewById(R.id.text_tradeplace));
		tv_fullstock = ((TextView) findViewById(R.id.text_fullstock));
		tv_availableamount = ((TextView) findViewById(R.id.text_availableamount));
		tv_receivingT0 = ((TextView) findViewById(R.id.text_receivingT0));
		tv_receivingT1 = ((TextView) findViewById(R.id.text_receivingT1));
		tv_receivingT2 = ((TextView) findViewById(R.id.text_receivingT2));
		tv_receivingT3 = ((TextView) findViewById(R.id.text_receivingT3));

		tv_total = ((TextView) findViewById(R.id.text_total));
		tv_chovequyen = ((TextView) findViewById(R.id.text_chovequyen));
		tv_pendingmatch = ((TextView) findViewById(R.id.text_pendingmatch));
		tv_marketprice = ((TextView) findViewById(R.id.text_marketprice));
		tv_marketvalue = ((TextView) findViewById(R.id.text_marketvalue));
		btn_buy = ((Button) findViewById(R.id.btn_buy));
		btn_sell = ((Button) findViewById(R.id.btn_sell));

		if (DeviceProperties.isTablet) {
			btn_buy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (onBuyClickListener != null) {
						onBuyClickListener.performAction(item);
					}
				}
			});
			btn_sell.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (onSellClickListener != null) {
						onSellClickListener.performAction(item);
					}
				}
			});
		}

	}

	public void setListView(StockBalanceItem item) {
		this.item = item;
		tv_symbol.setText(item.getITEM());
		tv_receivingT0.setText(Common.formatAmount(item.getRECEIVING_T0()));
		tv_receivingT1.setText(Common.formatAmount(item.getRECEIVING_T1()));
		tv_receivingT2.setText(Common.formatAmount(item.getRECEIVING_T2()));
//		tv_receivingT3.setText(Common.formatAmount(item.getRECEIVING_T3()));
		if (DeviceProperties.isTablet) {
			tv_total.setText(Common.formatAmount(item.getTOTAL_QTTY()));
			tv_availableamount.setText(Common.formatAmount(item.getTRADE()));
			tv_chovequyen
					.setText(Common.formatAmount(item.getRECEIVING_RIGHT()));
			tv_pendingmatch.setText(Common.formatAmount(item.getSECURED()));
			tv_marketprice.setText(Common.formatAmount(item.getCURRPRICE()));
			tv_marketvalue.setText(Common.formatAmount(item.getMARKETAMT()));
		} else {
			tv_total.setText(Common.formatAmount(item.getTOTAL_QTTY()));
			StockItem stockItem = StaticObjectManager.getStockItem(item
					.getITEM());
			if (stockItem != null) {
				tv_tradeplace.setText(stockItem.getTradePlace());
				tv_fullstock.setText(stockItem.getFullStock());
			}
		}
	}
}
