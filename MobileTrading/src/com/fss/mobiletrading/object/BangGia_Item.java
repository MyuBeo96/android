package com.fss.mobiletrading.object;

import java.util.HashMap;

import org.json.JSONException;

import android.R.color;
import android.graphics.Color;

import com.fss.mobiletrading.common.MTradingColor;
import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.consts.StringConst;

public class BangGia_Item {
	public String BidP1;
	public String BidP2;
	public String BidP3;
	public String BidV1;
	public String BidV2;
	public String BidV3;
	public String CeilingPrice;
	public String Change;
	public String ClosePrice;
	public String FloorPrice;
	public String OffP1;
	public String OffP2;
	public String OffP3;
	public String OffV1;
	public String OffV2;
	public String OffV3;
	public String Percent;
	public String RefPrice;
	public String StockName;
	public String Symbol;
	public String closeVol;
	public String totalTrading;
	public String totalVal;

	public int BidP1Color = Color.WHITE;
	public int BidP2Color = Color.WHITE;
	public int BidP3Color = Color.WHITE;
	public int BidV1Color = Color.WHITE;
	public int BidV2Color = Color.WHITE;
	public int BidV3Color = Color.WHITE;
	public int CeilingPriceColor = Color.WHITE;
	public int ChangeColor = Color.WHITE;
	public int ClosePriceColor = Color.WHITE;
	public int FloorPriceColor = Color.WHITE;
	public int OffP1Color = Color.WHITE;
	public int OffP2Color = Color.WHITE;
	public int OffP3Color = Color.WHITE;
	public int OffV1Color = Color.WHITE;
	public int OffV2Color = Color.WHITE;
	public int OffV3Color = Color.WHITE;
	public int PercentColor = Color.WHITE;
	public int RefPriceColor = Color.WHITE;
	public int StockNameColor = Color.WHITE;
	public int SymbolColor = Color.WHITE;
	public int closeVolColor = Color.WHITE;
	public int totalTradingColor = Color.WHITE;
	public int totalValColor = Color.WHITE;

	public String LS;
	public String json;
	MyJsonObject obj = null;
	boolean parsed = false;

	public BangGia_Item(String symbol) {
		this.Symbol = symbol;
		this.LS = StringConst.EMPTY;
	}

	public BangGia_Item(String symbol, String close, String LS) {
		this.Symbol = symbol;
		this.StockName = "";
		this.CeilingPrice = "";
		this.ClosePrice = close;
		this.FloorPrice = "";
		this.Percent = "";
		this.Change = "";
		this.RefPrice = "";
		this.totalTrading = "";
		this.totalVal = "";
		this.closeVol = "";
		this.BidP1 = "";
		this.BidV1 = "";
		this.OffP1 = "";
		this.OffV1 = "";
		this.BidP2 = "";
		this.BidV2 = "";
		this.OffP2 = "";
		this.OffV2 = "";
		this.BidP3 = "";
		this.BidV3 = "";
		this.OffP3 = "";
		this.OffV3 = "";
		this.LS = LS;
	}

	public BangGia_Item(String bidP1, String bidP2, String bidP3, String bidV1,
			String bidV2, String bidV3, String ceilingPrice, String change,
			String closePrice, String floorPrice, String offP1, String offP2,
			String offP3, String offV1, String offV2, String offV3,
			String percent, String refPrice, String stockName, String symbol,
			String closeVol, String totalTrading, String totalVal, String lS) {
		super();
		BidP1 = bidP1;
		BidP2 = bidP2;
		BidP3 = bidP3;
		BidV1 = bidV1;
		BidV2 = bidV2;
		BidV3 = bidV3;
		CeilingPrice = ceilingPrice;
		Change = change;
		ClosePrice = closePrice;
		FloorPrice = floorPrice;
		OffP1 = offP1;
		OffP2 = offP2;
		OffP3 = offP3;
		OffV1 = offV1;
		OffV2 = offV2;
		OffV3 = offV3;
		Percent = percent;
		RefPrice = refPrice;
		StockName = stockName;
		Symbol = symbol;
		this.closeVol = closeVol;
		this.totalTrading = totalTrading;
		this.totalVal = totalVal;
		LS = lS;
	}

	public BangGia_Item(String json, String LS) {
		this.json = json;
		this.LS = LS;
	}

	public void parse() {
		if (parsed) {
			return;
		}
		try {
			obj = new MyJsonObject(json);
			BidP1 = obj.getString("BidP1");
			BidP2 = obj.getString("BidP2");
			BidP3 = obj.getString("BidP3");
			BidV1 = obj.getString("BidV1");
			BidV2 = obj.getString("BidV2");
			BidV3 = obj.getString("BidV3");
			CeilingPrice = obj.getString("CeilingPrice");
			Change = obj.getString("Change");
			ClosePrice = obj.getString("ClosePrice");
			FloorPrice = obj.getString("FloorPrice");
			OffP1 = obj.getString("OffP1");
			OffP2 = obj.getString("OffP2");
			OffP3 = obj.getString("OffP3");
			OffV1 = obj.getString("OffV1");
			OffV2 = obj.getString("OffV2");
			OffV3 = obj.getString("OffV3");
			Percent = obj.getString("Percent");
			RefPrice = obj.getString("RefPrice");
			StockName = obj.getString("StockName");
			Symbol = obj.getString("Symbol");
			closeVol = obj.getString("closeVol");
			totalTrading = obj.getString("totalTrading");
			totalVal = obj.getString("totalVal");

			MyJsonObject css = new MyJsonObject(obj.getString("Css"));
			
			BidP1Color = MTradingColor.PriceColor.getColorByName(css
					.getString("BidP1"));
			BidP2Color = MTradingColor.PriceColor.getColorByName(css.getString("BidP2"));
			BidP3Color = MTradingColor.PriceColor.getColorByName(css.getString("BidP3"));
			BidV1Color = MTradingColor.PriceColor.getColorByName(css.getString("BidV1"));
			BidV2Color = MTradingColor.PriceColor.getColorByName(css.getString("BidV2"));
			BidV3Color = MTradingColor.PriceColor.getColorByName(css.getString("BidV3"));
			CeilingPriceColor = MTradingColor.PriceColor.getColorByName(css.getString("CeilingPrice"));
			ChangeColor = MTradingColor.PriceColor.getColorByName(css.getString("Change"));
			ClosePriceColor = MTradingColor.PriceColor.getColorByName(css.getString("ClosePrice"));
			FloorPriceColor = MTradingColor.PriceColor.getColorByName(css.getString("FloorPrice"));
			OffP1Color = MTradingColor.PriceColor.getColorByName(css.getString("OffP1"));
			OffP2Color = MTradingColor.PriceColor.getColorByName(css.getString("OffP2"));
			OffP3Color = MTradingColor.PriceColor.getColorByName(css.getString("OffP3"));
			OffV1Color = MTradingColor.PriceColor.getColorByName(css.getString("OffV1"));
			OffV2Color = MTradingColor.PriceColor.getColorByName(css.getString("OffV2"));
			OffV3Color = MTradingColor.PriceColor.getColorByName(css.getString("OffV3"));
			PercentColor = MTradingColor.PriceColor.getColorByName(css.getString("Percent"));
			RefPriceColor = MTradingColor.PriceColor.getColorByName(css.getString("RefPrice"));
			StockNameColor = MTradingColor.PriceColor.getColorByName(css.getString("StockName"));
			SymbolColor = MTradingColor.PriceColor.getColorByName(css.getString("Symbol"));
			closeVolColor = MTradingColor.PriceColor.getColorByName(css.getString("closeVol"));
			totalTradingColor = MTradingColor.PriceColor.getColorByName(css.getString("totalTrading"));
			totalValColor = MTradingColor.PriceColor.getColorByName(css.getString("totalVal"));

			parsed = true;
		} catch (NullPointerException e) {
			parsed = false;
		} catch (JSONException e) {
			parsed = false;
		}
	}

	BangGia_Item oldItem;

	public BangGia_Item getOldItem() {
		return oldItem;
	}

	public void setOldItem(BangGia_Item oldItem) {
		this.oldItem = oldItem;
	}

}
