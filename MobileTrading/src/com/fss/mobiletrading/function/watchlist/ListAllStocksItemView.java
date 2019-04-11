package com.fss.mobiletrading.function.watchlist;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.object.StockItem;

public class ListAllStocksItemView extends LinearLayout {
	TextView name;
	TextView symbol;
	ImageView select;
	SimpleAction selectedAction;
	SimpleAction unSelectedAction;
	FavWatchListItem item;
	StockItem stockItem;

	public ListAllStocksItemView(Context context, SimpleAction selectedAc,
			SimpleAction unSelectedAc) {
		super(context);
		selectedAction = selectedAc;
		unSelectedAction = unSelectedAc;
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.listallstock_item, this);
		name = ((TextView) findViewById(R.id.text_listallstocks_item_name));
		symbol = ((TextView) findViewById(R.id.text_listallstocks_item_symbol));
		select = (ImageView) findViewById(R.id.img_listallstocks_item_selected);

		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setStockSelect(!select.isSelected());
			}
		});
	}

	public void setView(StockItem item, boolean hasselect) {
		if (stockItem != item) {
			stockItem = item;
			String strName = item.getFullStock();
			if (strName != null) {
				name.setText(strName.replace("(", "").replace(")", ""));
			}

			symbol.setText(item.getMack());
			setStockSelect(hasselect);
			select.setSelected(hasselect);
		}
	}

	private void setStockSelect(boolean hasSelect) {
		select.setSelected(hasSelect);
		if (hasSelect) {
			select.setImageResource(R.drawable.ic_delete);
			if (selectedAction != null) {
				selectedAction.performAction(stockItem);
			}
		} else {
			select.setImageResource(R.drawable.ic_add);
			if (unSelectedAction != null) {
				unSelectedAction.performAction(stockItem);
			}
		}
	}
}