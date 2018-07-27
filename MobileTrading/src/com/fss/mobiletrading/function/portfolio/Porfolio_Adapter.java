package com.fss.mobiletrading.function.portfolio;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.common.SimpleAction;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;

import java.util.List;

public class Porfolio_Adapter extends ArrayAdapter<PorfolioItem> {
	Context context;
	List<PorfolioItem> listitem;
	Double sumMKTAMT = 0d;
	int item_odd_bg_color;
	int item_even_bg_color;
	/**
	 * biến set cho phép adapter có đc update hay k Nếu có 1 item đang trong
	 * trạng thái thay đổi vị trí thì k đc phép update
	 */
	public boolean isUpdate = true;
	SimpleAction mBuyAction;
	SimpleAction mSellAction;

	public Porfolio_Adapter(Context context, int paramInt,
			List<PorfolioItem> object) {
		super(context, paramInt, object);
		this.context = context;
		listitem = object;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
	}
	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (convertView == null) {
			if (DeviceProperties.isTablet) {
				convertView = new T_PorfolioItemView(this.context);
			} else {
				convertView = new M_PorfolioItemView(this.context, this,
						mBuyAction, mSellAction);
			}
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
			((T_PorfolioItemView) convertView).setView(listitem.get(position));
		} else {
			((M_PorfolioItemView) convertView).setView(listitem.get(position));
		}
		return convertView;
	}

	public void setSumMKTAMT(Double paramDouble) {
		sumMKTAMT = paramDouble;
	}

	@Override
	public void notifyDataSetChanged() {
		if (isUpdate) {
			super.notifyDataSetChanged();
		}

	}

	public void setmBuyAction(SimpleAction mBuyAction) {
		this.mBuyAction = mBuyAction;
	}

	public void setmSellAction(SimpleAction mSellAction) {
		this.mSellAction = mSellAction;
	}

}
