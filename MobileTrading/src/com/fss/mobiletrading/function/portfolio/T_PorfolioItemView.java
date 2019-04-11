package com.fss.mobiletrading.function.portfolio;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.consts.StringConst;

public class T_PorfolioItemView extends PorfolioItemView {
	Context context;

	TextView tv_item;
	TextView tv_stockavailable;
	TextView tv_total;
	TextView tv_chovequyen;
	TextView tv_pendingmatch;
	TextView tv_marketprice;
	TextView tv_marketvalue;
	TextView tv_receivingT0;
	TextView tv_receivingT1;
	TextView tv_receivingT2;
	TextView tv_receivingT3;
	TextView tv_giabinhquan;
	TextView tv_giavon;
	TextView tv_PL;
	TextView tv_percentPL;

	PorfolioItem mPorfolioItem;

	public T_PorfolioItemView(Context context) {
		super(context);
		this.context = context;
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.porfolio_item, this);
		tv_item = (TextView) findViewById(R.id.text_item);
		tv_stockavailable = (TextView) findViewById(R.id.text_stockavailable);
		tv_total = (TextView) findViewById(R.id.text_total);
		tv_chovequyen = (TextView) findViewById(R.id.text_chovequyen);
		tv_pendingmatch = (TextView) findViewById(R.id.text_pendingmatch);
		tv_marketprice = (TextView) findViewById(R.id.text_marketprice);
		tv_marketvalue = (TextView) findViewById(R.id.text_marketvalue);
		tv_receivingT0 = (TextView) findViewById(R.id.text_receivingT0);
		tv_receivingT1 = (TextView) findViewById(R.id.text_receivingT1);
		tv_receivingT2 = (TextView) findViewById(R.id.text_receivingT2);
		tv_receivingT3 = (TextView) findViewById(R.id.text_receivingT3);
		tv_giabinhquan = (TextView) findViewById(R.id.text_giabinhquan);
		tv_giavon = (TextView) findViewById(R.id.text_giavon);
		tv_PL = (TextView) findViewById(R.id.text_pl);
		tv_percentPL = (TextView) findViewById(R.id.text_percentpl);
	}

	@Override
	public void setView(PorfolioItem item) {
		mPorfolioItem = item;
		tv_item.setText(item.getItem());
		tv_total.setText(item.getTotalBalance());
		tv_marketvalue.setText(item.getMarketPrice());
		tv_giavon.setText(item.getCostValue());
		tv_PL.setText(item.getUnrealized_PL());
		tv_percentPL.setText(item.getPercent_PL());
		tv_stockavailable.setText(item.getTrade());
		tv_chovequyen.setText(item.getReceiving_Right());
		tv_pendingmatch.setText(item.getSecured());
		tv_marketprice.setText(item.getBasicPrice());
		tv_receivingT0.setText(item.getReceiving_T0());
		tv_receivingT1.setText(item.getReceiving_T1());
		tv_receivingT2.setText(item.getReceiving_T2());
		tv_receivingT3.setText(item.getReceiving_T3());
		tv_giabinhquan.setText(item.getAvgBuyPrice());

		double percentPL;
		try {
			percentPL = Double.parseDouble(item.get_Unrealized_PL());
		} catch (NumberFormatException e) {
			percentPL = 0d;
		}
		if (percentPL > 0) {
			tv_PL.setTextColor(getResources().getColor(R.color.color_Mua));
			tv_percentPL.setTextColor(getResources()
					.getColor(R.color.color_Mua));
			tv_marketprice.setTextColor(getResources().getColor(
					R.color.color_Mua));
		} else if (percentPL == 0) {
			tv_PL.setTextColor(getResources().getColor(R.color.color_ThamChieu));
			tv_percentPL.setTextColor(getResources().getColor(
					R.color.color_ThamChieu));
			tv_marketprice.setTextColor(getResources().getColor(
					R.color.color_ThamChieu));
		} else {
			tv_PL.setTextColor(getResources().getColor(R.color.color_Ban));
			tv_percentPL.setTextColor(getResources()
					.getColor(R.color.color_Ban));
			tv_marketprice.setTextColor(getResources().getColor(
					R.color.color_Ban));
		}

	}
}
