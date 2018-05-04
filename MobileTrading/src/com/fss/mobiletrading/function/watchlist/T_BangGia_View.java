package com.fss.mobiletrading.function.watchlist;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.msbuat.mobiletrading.Login;
import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.BangGia_Adapter;
import com.fss.mobiletrading.adapter.SolenhGTC_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.MTradingColor;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.BangGia_Item;

public class T_BangGia_View extends BangGia_View {

	public T_BangGia_View(Context context) {
		super(context);
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.watchlist_item, this);
		this.tv_MaCK = ((TextView) findViewById(R.id.text_BangGia_item_Symbol));
		this.tv_Change = ((TextView) findViewById(R.id.text_BangGia_item_Change));
		this.tv_Percent = ((TextView) findViewById(R.id.text_BangGia_item_Percent));
		this.tv_Tran = ((TextView) findViewById(R.id.text_BangGia_item_Tran));
		this.tv_San = ((TextView) findViewById(R.id.text_BangGia_item_San));
		this.tv_ClosePrice = ((TextView) findViewById(R.id.text_BangGia_item_ClosePrice));
		this.imgview_kyhieu = ((ImageView) findViewById(R.id.Img_BangGia_item_KyHieu));
	}

	public void setView(BangGia_Item newitem) {
		if (newitem != null) {
			newitem.parse();
			this.tv_ClosePrice.setText(newitem.ClosePrice);
			this.tv_Change.setText(newitem.Change);
			this.tv_Percent.setText(newitem.Percent + "%");
			this.tv_MaCK.setText(newitem.Symbol);
			this.tv_Tran.setText(newitem.CeilingPrice);
			this.tv_San.setText(newitem.FloorPrice);

			setTextColor(newitem);

			BangGia_Item olditem = newitem.getOldItem();
			if (olditem == null) {
				clearAllHighLight();
			} else {
				setHighLight(olditem, newitem);
			}
		}
	}

}
