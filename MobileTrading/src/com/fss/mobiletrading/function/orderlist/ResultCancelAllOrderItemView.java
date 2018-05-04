package com.fss.mobiletrading.function.orderlist;

import android.app.Service;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.object.SolenhItem;

public class ResultCancelAllOrderItemView extends LinearLayout {
	TextView custodycd;
	TextView side;
	TextView symbol;
	ImageView issuccess;

	public ResultCancelAllOrderItemView(Context context) {
		super(context);
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.resultcancelallorder_item_layout, this);
		custodycd = ((TextView) findViewById(R.id.text_resultcancelallorder_item_custodycd));
		side = ((TextView) findViewById(R.id.text_resultcancelallorder_item_side));
		symbol = ((TextView) findViewById(R.id.text_resultcancelallorder_item_symbol));
		issuccess = (ImageView) findViewById(R.id.img_resultcancelallorder_item_issuccess);
	}

	public void setView(SolenhItem item) {
		custodycd.setText(item.CustodyCd + "." + item.AfAcctno);
		if (item.Side.equals(PlaceOrder.SIDE_NB)) {
			side.setText(getResources().getString(R.string.Mua));
			side.setTextColor(getResources().getColor(R.color.color_Mua));
		} else {
			side.setText(getResources().getString(R.string.Ban));
			side.setTextColor(getResources().getColor(R.color.color_Ban));
		}
		symbol.setText(item.Symbol);
		if (item.ErrorCode.equals("0")) {
			issuccess.setSelected(true);
		} else {
			issuccess.setSelected(false);
		}

	}
}
