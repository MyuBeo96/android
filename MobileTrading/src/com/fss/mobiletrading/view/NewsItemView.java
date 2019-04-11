package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcscuat.mobiletrading.Login;
import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.object.NewsItem;

public class NewsItemView extends LinearLayout {
	TextView tv_PostDate;
	TextView tv_Title;
	TextView tv_Desc;

	public NewsItemView(Context context) {
		super(context);
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.newsitem, this);
		this.tv_Title = ((TextView) findViewById(R.id.text_tintuc_item_title));
		this.tv_PostDate = ((TextView) findViewById(R.id.text_tintuc_item_PostDate));
		this.tv_Desc = ((TextView) findViewById(R.id.text_tintuc_item_desc));
	}

	public void setView(NewsItem item) {
		this.tv_Title.setText(item.Title);
		this.tv_PostDate.setText(item.PostDate);
		tv_Desc.setText(item.Content);
	}
}
