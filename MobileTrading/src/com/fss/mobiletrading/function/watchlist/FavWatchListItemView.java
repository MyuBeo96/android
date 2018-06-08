package com.fss.mobiletrading.function.watchlist;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.common.SimpleAction;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;

public class FavWatchListItemView extends LinearLayout {
	TextView name;
	ImageButton delete;
	SimpleAction deleteAction;
	SimpleAction clickAction;
	ImageView icon;
	FavWatchListItem item;

	public FavWatchListItemView(Context context, SimpleAction deleteAc,
			SimpleAction clickAc) {
		super(context);
		deleteAction = deleteAc;
		clickAction = clickAc;
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.listfavwatchlist_item, this);
		name = ((TextView) findViewById(R.id.text_listfavwatchlist_item_name));
		delete = (ImageButton) findViewById(R.id.btn_listfavwatchlist_item_delete);
		icon = (ImageView) findViewById(R.id.img_listfavwatchlist_item_icon);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (deleteAction != null) {
					deleteAction.performAction(item);
				}
			}
		});
		// name.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// if (clickAction != null) {
		// clickAction.performAction(item);
		// }
		// }
		// });

		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v.getId() != delete.getId()) {
					clickAction.performAction(item);
				}
			}
		});
	}

	public void setView(FavWatchListItem item) {
		if (this.item != item) {
			this.item = item;
		}
		name.setText(item.getCName());
		int criteriaId = Integer.MIN_VALUE;
		try {
			criteriaId = Integer.parseInt(item.getCriteriaId());
		} catch (NumberFormatException e) {
		}
		if (criteriaId > 0) {
			delete.setVisibility(VISIBLE);
		} else {
			delete.setVisibility(GONE);
		}
		if (DeviceProperties.isTablet) {
			if (item.icon != 0) {
				icon.setImageResource(item.icon);
				icon.setVisibility(View.VISIBLE);
			} else {
				icon.setVisibility(View.GONE);
			}

		}
	}
}
