package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.adapter.FullWatchListAdapter;
import com.fss.mobiletrading.common.MTradingColor;
import com.fss.mobiletrading.common.MTradingColor.PriceColor;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.BangGia_Item;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;

public class FullWatchListItemView extends LinearLayout {
	ImageView img_Sign;
	TextView tv_BidP1;
	TextView tv_BidV1;
	TextView tv_Change;
	TextView tv_OfferP1;
	TextView tv_OfferV1;
	TextView tv_Price;
	TextView tv_Quantity;
	TextView tv_Symbol;
	TextView tv_totalTrade;
	/**
	 * only tablet
	 * 
	 * @param paramContext
	 */
	TextView tv_bidprice1;
	TextView tv_bidprice2;
	TextView tv_bidprice3;
	TextView tv_bidvol1;
	TextView tv_bidvol2;
	TextView tv_bidvol3;
	TextView tv_offerprice1;
	TextView tv_offerprice2;
	TextView tv_offerprice3;
	TextView tv_offervol1;
	TextView tv_offervol2;
	TextView tv_offervol3;
	static int transparent = Color.parseColor("#00ffffff");

	public FullWatchListItemView(Context paramContext) {
		super(paramContext);
		((LayoutInflater) paramContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.fullwatchlist_item, this);
		this.tv_Symbol = ((TextView) findViewById(R.id.text_banggiafull_item_Symbol));
		this.img_Sign = ((ImageView) findViewById(R.id.img_banggiafull_item_Sign));
		this.tv_Change = ((TextView) findViewById(R.id.text_banggiafull_item_Change));
		this.tv_Price = ((TextView) findViewById(R.id.text_banggiafull_item_Price));
		this.tv_Quantity = ((TextView) findViewById(R.id.text_banggiafull_item_Quantity));
		this.tv_totalTrade = ((TextView) findViewById(R.id.text_banggiafull_item_totalTrading));
		if (DeviceProperties.isTablet) {
			tv_bidprice1 = (TextView) findViewById(R.id.text_item_bidprice1);
			tv_bidprice2 = (TextView) findViewById(R.id.text_item_bidprice2);
			tv_bidprice3 = (TextView) findViewById(R.id.text_item_bidprice3);
			tv_bidvol1 = (TextView) findViewById(R.id.text_item_bidvol1);
			tv_bidvol2 = (TextView) findViewById(R.id.text_item_bidvol2);
			tv_bidvol3 = (TextView) findViewById(R.id.text_item_bidvol3);
			tv_offerprice1 = (TextView) findViewById(R.id.text_item_offerprice1);
			tv_offerprice2 = (TextView) findViewById(R.id.text_item_offerprice2);
			tv_offerprice3 = (TextView) findViewById(R.id.text_item_offerprice3);
			tv_offervol1 = (TextView) findViewById(R.id.text_item_offervol1);
			tv_offervol2 = (TextView) findViewById(R.id.text_item_offervol2);
			tv_offervol3 = (TextView) findViewById(R.id.text_item_offervol3);
		} else {
			this.tv_BidV1 = ((TextView) findViewById(R.id.text_banggiafull_item_BidV1));
			this.tv_OfferV1 = ((TextView) findViewById(R.id.text_banggiafull_item_OfferV1));
			this.tv_BidP1 = ((TextView) findViewById(R.id.text_banggiafull_item_BidP1));
			this.tv_OfferP1 = ((TextView) findViewById(R.id.text_banggiafull_item_OfferP1));
		}

	}

	public void setView(BangGia_Item newitem, int bidoffer) {
		if (newitem == null) {
			clearAllHighLight();
			return;
		}
		newitem.parse();
		if (DeviceProperties.isTablet) {
			tv_bidprice1.setText(newitem.BidP1);
			tv_bidprice2.setText(newitem.BidP2);
			tv_bidprice3.setText(newitem.BidP3);
			tv_bidvol1.setText(newitem.BidV1);
			tv_bidvol2.setText(newitem.BidV2);
			tv_bidvol3.setText(newitem.BidV3);
			tv_offerprice1.setText(newitem.OffP1);
			tv_offerprice2.setText(newitem.OffP2);
			tv_offerprice3.setText(newitem.OffP3);
			tv_offervol1.setText(newitem.OffV1);
			tv_offervol2.setText(newitem.OffV2);
			tv_offervol3.setText(newitem.OffV3);

			tv_bidprice1.setTextColor(newitem.BidP1Color);
			tv_bidprice2.setTextColor(newitem.BidP2Color);
			tv_bidprice3.setTextColor(newitem.BidP3Color);
			tv_bidvol1.setTextColor(newitem.BidV1Color);
			tv_bidvol2.setTextColor(newitem.BidV2Color);
			tv_bidvol3.setTextColor(newitem.BidV3Color);
			tv_offerprice1.setTextColor(newitem.OffP1Color);
			tv_offerprice2.setTextColor(newitem.OffP2Color);
			tv_offerprice3.setTextColor(newitem.OffP3Color);
			tv_offervol1.setTextColor(newitem.OffV1Color);
			tv_offervol2.setTextColor(newitem.OffV2Color);
			tv_offervol3.setTextColor(newitem.OffV3Color);
		} else {
			switch (bidoffer) {
			case FullWatchListAdapter.BidOffer1:
				tv_BidV1.setText(newitem.BidV1);
				tv_OfferV1.setText(newitem.OffV1);
				tv_BidP1.setText(newitem.BidP1);
				tv_OfferP1.setText(newitem.OffP1);
				tv_BidV1.setTextColor(newitem.BidV1Color);
				tv_OfferV1.setTextColor(newitem.OffV1Color);
				tv_BidP1.setTextColor(newitem.BidP1Color);
				tv_OfferP1.setTextColor(newitem.OffP1Color);
				break;
			case FullWatchListAdapter.BidOffer2:
				tv_BidV1.setText(newitem.BidV2);
				tv_OfferV1.setText(newitem.OffV2);
				tv_BidP1.setText(newitem.BidP2);
				tv_OfferP1.setText(newitem.OffP2);
				tv_BidV1.setTextColor(newitem.BidV2Color);
				tv_OfferV1.setTextColor(newitem.OffV2Color);
				tv_BidP1.setTextColor(newitem.BidP2Color);
				tv_OfferP1.setTextColor(newitem.OffP2Color);
				break;
			case FullWatchListAdapter.BidOffer3:
				tv_BidV1.setText(newitem.BidV3);
				tv_OfferV1.setText(newitem.OffV3);
				tv_BidP1.setText(newitem.BidP3);
				tv_OfferP1.setText(newitem.OffP3);
				tv_BidV1.setTextColor(newitem.BidV3Color);
				tv_OfferV1.setTextColor(newitem.OffV3Color);
				tv_BidP1.setTextColor(newitem.BidP3Color);
				tv_OfferP1.setTextColor(newitem.OffP3Color);
				break;

			default:
				break;
			}
		}

		tv_Symbol.setText(newitem.Symbol);
		tv_Price.setText(newitem.ClosePrice);
		tv_Change.setText(newitem.Change);
		tv_Quantity.setText(newitem.closeVol);
		tv_totalTrade.setText(newitem.totalTrading);

		tv_Symbol.setTextColor(newitem.SymbolColor);
		tv_Price.setTextColor(newitem.ClosePriceColor);
		tv_Change.setTextColor(newitem.ChangeColor);
		tv_Quantity.setTextColor(newitem.closeVolColor);
		tv_totalTrade.setTextColor(newitem.totalTradingColor);

		int closePriceColor = newitem.ClosePriceColor;
		if (closePriceColor == PriceColor.hitCeil.getColor()) {
			this.img_Sign.setImageResource(R.drawable.ic_upceil);
		} else if (closePriceColor == PriceColor.hitFloor.getColor()) {
			this.img_Sign.setImageResource(R.drawable.ic_downfloor);
		} else if (closePriceColor == PriceColor.gainer.getColor()) {
			this.img_Sign.setImageResource(R.drawable.ic_up);
		} else if (closePriceColor == PriceColor.loser.getColor()) {
			this.img_Sign.setImageResource(R.drawable.ic_downred);
		} else {
			this.img_Sign.setImageResource(R.drawable.ic_yellow);
		}

		BangGia_Item olditem = newitem.getOldItem();

		if (olditem == null) {
			clearAllHighLight();
		} else {

			if (DeviceProperties.isTablet) {
				setHighLight(tv_bidprice1, olditem.BidP1, newitem.BidP1);
				setHighLight(tv_bidprice2, olditem.BidP2, newitem.BidP2);
				setHighLight(tv_bidprice3, olditem.BidP3, newitem.BidP3);
				setHighLight(tv_bidvol1, olditem.BidV1, newitem.BidV1);
				setHighLight(tv_bidvol2, olditem.BidV2, newitem.BidV2);
				setHighLight(tv_bidvol3, olditem.BidV3, newitem.BidV3);
				setHighLight(tv_offerprice1, olditem.OffP1, newitem.OffP1);
				setHighLight(tv_offerprice2, olditem.OffP2, newitem.OffP2);
				setHighLight(tv_offerprice3, olditem.OffP3, newitem.OffP3);
				setHighLight(tv_offervol1, olditem.OffV1, newitem.OffV1);
				setHighLight(tv_offervol2, olditem.OffV2, newitem.OffV2);
				setHighLight(tv_offervol3, olditem.OffV3, newitem.OffV3);
			} else {
				switch (bidoffer) {
				case FullWatchListAdapter.BidOffer1:
					setHighLight(tv_BidV1, olditem.BidV1, newitem.BidV1);
					setHighLight(tv_OfferV1, olditem.OffV1, newitem.OffV1);
					setHighLight(tv_BidP1, olditem.BidP1, newitem.BidP1);
					setHighLight(tv_OfferP1, olditem.OffP1, newitem.OffP1);
					break;
				case FullWatchListAdapter.BidOffer2:
					setHighLight(tv_BidV1, olditem.BidV2, newitem.BidV2);
					setHighLight(tv_OfferV1, olditem.OffV2, newitem.OffV2);
					setHighLight(tv_BidP1, olditem.BidP2, newitem.BidP2);
					setHighLight(tv_OfferP1, olditem.OffP2, newitem.OffP2);
					break;
				case FullWatchListAdapter.BidOffer3:
					setHighLight(tv_BidV1, olditem.BidV3, newitem.BidV3);
					setHighLight(tv_OfferV1, olditem.OffV3, newitem.OffV3);
					setHighLight(tv_BidP1, olditem.BidP3, newitem.BidP3);
					setHighLight(tv_OfferP1, olditem.OffP3, newitem.OffP3);
					break;

				default:
					break;
				}
			}
			setHighLight(tv_Price, olditem.ClosePrice, newitem.ClosePrice);
			setHighLight(tv_Change, olditem.Change, newitem.Change);
			setHighLight(tv_Quantity, olditem.closeVol, newitem.closeVol);
			setHighLight(tv_totalTrade, olditem.totalTrading,
					newitem.totalTrading);
		}
	}

	private void clearAllHighLight() {
		if (DeviceProperties.isTablet) {
			tv_bidprice1.setBackgroundColor(transparent);
			tv_bidprice2.setBackgroundColor(transparent);
			tv_bidprice3.setBackgroundColor(transparent);
			tv_bidvol1.setBackgroundColor(transparent);
			tv_bidvol2.setBackgroundColor(transparent);
			tv_bidvol3.setBackgroundColor(transparent);
			tv_offerprice1.setBackgroundColor(transparent);
			tv_offerprice2.setBackgroundColor(transparent);
			tv_offerprice3.setBackgroundColor(transparent);
			tv_offervol1.setBackgroundColor(transparent);
			tv_offervol2.setBackgroundColor(transparent);
			tv_offervol3.setBackgroundColor(transparent);
		} else {
			tv_BidV1.setBackgroundColor(transparent);
			tv_OfferV1.setBackgroundColor(transparent);
			tv_BidP1.setBackgroundColor(transparent);
			tv_OfferP1.setBackgroundColor(transparent);
		}
		tv_Price.setBackgroundColor(transparent);
		tv_Change.setBackgroundColor(transparent);
		tv_Quantity.setBackgroundColor(transparent);
		tv_totalTrade.setBackgroundColor(transparent);
	}

	private void setHighLight(TextView v, String oldVal, String newVal) {
		if (v != null) {
			oldVal = StringConst.validateNull(oldVal);
			newVal = StringConst.validateNull(newVal);
			if (newVal.equals(oldVal)) {
				v.setBackgroundColor(transparent);
			} else {
				v.setBackgroundColor(MTradingColor.highlight_color);
			}
		}
	}
}
