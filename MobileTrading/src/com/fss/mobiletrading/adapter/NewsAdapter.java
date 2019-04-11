package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;

import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.design.FilterArrayAdapter;
import com.fss.mobiletrading.function.watchlist.BangGia_View;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.view.NewsItemView;

public class NewsAdapter extends FilterArrayAdapter<NewsItem> {
	Context context;
	List<NewsItem> list_Tintuc;
	int itemSelectedPosition = -1;

	public NewsAdapter(Context context, int resource, List<NewsItem> object) {
		super(context, resource, object);
		this.context = context;
		this.list_Tintuc = object;
	}

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (convertView == null) {
			convertView = new NewsItemView(this.context);
		}
		((NewsItemView) convertView).setView(filteredData.get(position));
		return convertView;
	}

	public int getItemSelectedPosition() {
		return itemSelectedPosition;
	}

	public void setItemSelectedPosition(int itemSelectedPosition) {
		this.itemSelectedPosition = itemSelectedPosition;
		notifyDataSetChanged();
	}

}
