package com.fss.mobiletrading.function.watchlist;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.StockDetailsItem;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;
import com.msbuat.mobiletrading.design.TextViewHightLight;

public class StockIndexView extends LinearLayout {
	ImageView imgview_kyhieu;
	TextView tv_symbol;
	TextView tv_Bid1;
	TextView tv_Bid2;
	TextView tv_Bid3;
	TextView tv_BidPrice1;
	TextView tv_BidPrice2;
	TextView tv_BidPrice3;
	TextView tv_Change;
	TextView tv_ClosePrice;
	TextView tv_Percent;
	TextView tv_Company;
	TextView tv_Offer1;
	TextView tv_Offer2;
	TextView tv_Offer3;
	TextView tv_OfferPrice1;
	TextView tv_OfferPrice2;
	TextView tv_OfferPrice3;
	TextView tv_market;
	RelativeLayout lay_bid1;
	RelativeLayout lay_bid2;
	RelativeLayout lay_bid3;
	RelativeLayout lay_offer1;
	RelativeLayout lay_offer2;
	RelativeLayout lay_offer3;

	TextViewHightLight tv_Ceil;
	TextViewHightLight tv_Ref;
	TextViewHightLight tv_Floor;
	TextViewHightLight tv_Highest;
	TextViewHightLight tv_Lowest;
	TextViewHightLight tv_KhoiLuong;
	TextViewHightLight tv_TongKhoiLuong;
	TextViewHightLight tv_foreignRemain;

	public StockIndexView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.stockinfo_index, this);
		tv_symbol = (TextView) findViewById(R.id.text_BangGiaCT_Symbol);
		tv_Company = ((TextView) findViewById(R.id.text_BangGiaCT_TenCty));
		tv_ClosePrice = ((TextView) findViewById(R.id.text_BangGiaCT_GiaKhopCuoi));
		tv_Change = ((TextView) findViewById(R.id.text_BangGiaCT_Change));
		tv_Percent = ((TextView) findViewById(R.id.text_BangGiaCT_Percent));
		tv_KhoiLuong = ((TextViewHightLight) findViewById(R.id.text_BangGiaCT_khoiluong));
		tv_TongKhoiLuong = ((TextViewHightLight) findViewById(R.id.text_BangGiaCT_tongkhoiluong));
		tv_foreignRemain = ((TextViewHightLight) findViewById(R.id.text_BangGiaCT_foreignRemain));
		tv_Ceil = ((TextViewHightLight) findViewById(R.id.text_BangGiaCT_Tran));
		tv_Floor = ((TextViewHightLight) findViewById(R.id.text_BangGiaCT_San));
		tv_Ref = ((TextViewHightLight) findViewById(R.id.text_BangGiaCT_ThamChieu));
		tv_Highest = ((TextViewHightLight) findViewById(R.id.text_BangGiaCT_CaoNhat));
		tv_Lowest = ((TextViewHightLight) findViewById(R.id.text_BangGiaCT_ThapNhat));

		tv_market = ((TextView) findViewById(R.id.text_stockinfo_index_market));
		tv_Bid1 = ((TextView) findViewById(R.id.text_BangGiaCT_Bid1));
		tv_Bid2 = ((TextView) findViewById(R.id.text_BangGiaCT_Bid2));
		tv_Bid3 = ((TextView) findViewById(R.id.text_BangGiaCT_Bid3));
		tv_BidPrice1 = ((TextView) findViewById(R.id.text_BangGiaCT_BidPrice1));
		tv_BidPrice2 = ((TextView) findViewById(R.id.text_BangGiaCT_BidPrice2));
		tv_BidPrice3 = ((TextView) findViewById(R.id.text_BangGiaCT_BidPrice3));
		tv_Offer1 = ((TextView) findViewById(R.id.text_BangGiaCT_Offer1));
		tv_Offer2 = ((TextView) findViewById(R.id.text_BangGiaCT_Offer2));
		tv_Offer3 = ((TextView) findViewById(R.id.text_BangGiaCT_Offer3));
		tv_OfferPrice1 = ((TextView) findViewById(R.id.text_BangGiaCT_OfferPrice1));
		tv_OfferPrice2 = ((TextView) findViewById(R.id.text_BangGiaCT_OfferPrice2));
		tv_OfferPrice3 = ((TextView) findViewById(R.id.text_BangGiaCT_OfferPrice3));

		imgview_kyhieu = ((ImageView) findViewById(R.id.Img_BangGiaCT_KyHieu));

		lay_bid1 = (RelativeLayout) findViewById(R.id.lay_stockinfo_index_bid1);
		lay_bid2 = (RelativeLayout) findViewById(R.id.lay_stockinfo_index_bid2);
		lay_bid3 = (RelativeLayout) findViewById(R.id.lay_stockinfo_index_bid3);
		lay_offer1 = (RelativeLayout) findViewById(R.id.lay_stockinfo_index_offer1);
		lay_offer2 = (RelativeLayout) findViewById(R.id.lay_stockinfo_index_offer2);
		lay_offer3 = (RelativeLayout) findViewById(R.id.lay_stockinfo_index_offer3);
	}

	Context context;

	public void displayView(StockDetailsItem stockDetailsItem) {
		try {
			int i;
			String floor = null;
			if (stockDetailsItem != null) {
				if (WatchList.HNX.equals(stockDetailsItem.FloorCode)) {
					floor = getResources().getString(R.string.HNX);
				} else if (WatchList.HOSE.equals(stockDetailsItem.FloorCode)) {
					floor = getResources().getString(R.string.HOSE);
				} else if (WatchList.UPCOM.equals(stockDetailsItem.FloorCode)) {
					floor = getResources().getString(R.string.UPCOM);
				}

				if (DeviceProperties.isTablet) {
					tv_symbol.setText(stockDetailsItem.symbol);
					tv_market.setText(floor);
					tv_Company.setText(stockDetailsItem.FullName);
				} else {
					tv_Company.setText(floor + "/" + stockDetailsItem.FullName);
				}

				tv_ClosePrice.setText(stockDetailsItem.closePrice);
				tv_Change.setText(stockDetailsItem.change);
				tv_Percent.setText(stockDetailsItem.changePercent);
				tv_KhoiLuong.setText(stockDetailsItem.closeVol);
				tv_TongKhoiLuong.setText(stockDetailsItem.totalTrading);
				tv_foreignRemain.setText(stockDetailsItem.foreignRemain);
				tv_Ceil.setText(stockDetailsItem.ceiling);
				tv_Floor.setText(stockDetailsItem.floor);
				tv_Ref.setText(stockDetailsItem.reference);
				tv_Highest.setText(stockDetailsItem.high);
				tv_Lowest.setText(stockDetailsItem.low);
				tv_Highest.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.high,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_Lowest.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.low,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_Bid1.setText(stockDetailsItem.bidVol1);
				tv_Bid2.setText(stockDetailsItem.bidVol2);
				tv_Bid3.setText(stockDetailsItem.bidVol3);
				tv_BidPrice1.setText(stockDetailsItem.bidPrice1);
				tv_BidPrice2.setText(stockDetailsItem.bidPrice2);
				tv_BidPrice3.setText(stockDetailsItem.bidPrice3);
				tv_Offer1.setText(stockDetailsItem.offerVol1);
				tv_Offer2.setText(stockDetailsItem.offerVol2);
				tv_Offer3.setText(stockDetailsItem.offerVol3);
				tv_OfferPrice1.setText(stockDetailsItem.offerPrice1);
				tv_OfferPrice2.setText(stockDetailsItem.offerPrice2);
				tv_OfferPrice3.setText(stockDetailsItem.offerPrice3);
				tv_Bid1.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.bidPrice1,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_BidPrice1.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.bidPrice1,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_Bid2.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.bidPrice2,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_BidPrice2.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.bidPrice2,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_Bid3.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.bidPrice3,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_BidPrice3.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.bidPrice3,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_Offer1.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.offerPrice1,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_OfferPrice1.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.offerPrice1,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_Offer2.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.offerPrice2,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_OfferPrice2.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.offerPrice2,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_Offer3.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.offerPrice3,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));
				tv_OfferPrice3.setTextColor(getResources().getColor(
						Common.getColor(stockDetailsItem.offerPrice3,
								stockDetailsItem.ceiling,
								stockDetailsItem.floor,
								stockDetailsItem.reference)));

				i = Common.getColor(stockDetailsItem.closePrice,
						stockDetailsItem.ceiling, stockDetailsItem.floor,
						stockDetailsItem.reference);

				tv_Change.setTextColor(getResources().getColor(i));
				tv_Percent.setTextColor(getResources().getColor(i));
				tv_ClosePrice.setTextColor(getResources().getColor(i));
				tv_KhoiLuong.setTextColor(getResources().getColor(i));
				if (DeviceProperties.isTablet) {
					tv_symbol.setTextColor(getResources().getColor(i));
				}
				if ((i == R.color.color_Mua) || (i == R.color.color_Tran)) {
					imgview_kyhieu.setImageResource(R.drawable.ic_up);
				} else {
					if (i == R.color.color_ThamChieu) {
						imgview_kyhieu.setImageResource(R.drawable.ic_yellow);
					} else {
						imgview_kyhieu.setImageResource(R.drawable.ic_downred);
					}
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	public void clearView() {
		if (DeviceProperties.isTablet) {
			tv_symbol.setText(StringConst.EMPTY);
			tv_market.setText(StringConst.EMPTY);
			tv_Company.setText(StringConst.EMPTY);
		} else {
			tv_Company.setText(StringConst.EMPTY);
		}

		tv_ClosePrice.setText(StringConst.EMPTY);
		tv_Change.setText(StringConst.EMPTY);
		tv_Percent.setText(StringConst.EMPTY);
		tv_KhoiLuong.setText(StringConst.EMPTY);
		tv_TongKhoiLuong.setText(StringConst.EMPTY);
		tv_foreignRemain.setText(StringConst.EMPTY);
		tv_Ceil.setText(StringConst.EMPTY);
		tv_Floor.setText(StringConst.EMPTY);
		tv_Ref.setText(StringConst.EMPTY);
		tv_Highest.setText(StringConst.EMPTY);
		tv_Lowest.setText(StringConst.EMPTY);
		tv_Bid1.setText(StringConst.EMPTY);
		tv_Bid2.setText(StringConst.EMPTY);
		tv_Bid3.setText(StringConst.EMPTY);
		tv_BidPrice1.setText(StringConst.EMPTY);
		tv_BidPrice2.setText(StringConst.EMPTY);
		tv_BidPrice3.setText(StringConst.EMPTY);
		tv_Offer1.setText(StringConst.EMPTY);
		tv_Offer2.setText(StringConst.EMPTY);
		tv_Offer3.setText(StringConst.EMPTY);
		tv_OfferPrice1.setText(StringConst.EMPTY);
		tv_OfferPrice2.setText(StringConst.EMPTY);
		tv_OfferPrice3.setText(StringConst.EMPTY);
	}
}
