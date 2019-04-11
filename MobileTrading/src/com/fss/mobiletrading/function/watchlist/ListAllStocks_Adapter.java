package com.fss.mobiletrading.function.watchlist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.object.StockItem;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;

import java.util.HashMap;
import java.util.List;

public class ListAllStocks_Adapter extends ArrayAdapter<StockItem> {
	List<StockItem> list;
	SimpleAction unselectAc;
	SimpleAction selectAc;
	List<String> selectedItems;
	int item_odd_bg_color;
	int item_even_bg_color;
	Context context;

	public ListAllStocks_Adapter(Context paramContext, int paramInt,
			List<StockItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list = paramList;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new ListAllStocksItemView(context, selectAc,
					unselectAc);
		}
		boolean select = false;
		if (selectedItems != null) {
			select = selectedItems.contains(list.get(position).getMack());
		}
		((ListAllStocksItemView) convertView).setView(list.get(position),
				select);
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		return convertView;
	}

	public void setSelectedItems(List<String> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public SimpleAction getUnselectAc() {
		return unselectAc;
	}

	public void setUnselectAc(SimpleAction unselectAc) {
		this.unselectAc = unselectAc;
	}

	public SimpleAction getSelectAc() {
		return selectAc;
	}

	public void setSelectAc(SimpleAction selectAc) {
		this.selectAc = selectAc;
	}

}
