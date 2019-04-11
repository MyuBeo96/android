package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.object.AuthorizationInfoItem;
import com.fss.mobiletrading.view.AuthorizationInfoItemView;

public class AuthorizationInfo_Adapter extends
		ArrayAdapter<AuthorizationInfoItem> {
	List<AuthorizationInfoItem> list;

	Context context;

	public AuthorizationInfo_Adapter(Context paramContext, int paramInt,
			List<AuthorizationInfoItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list = paramList;
	}

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (convertView == null) {
			convertView = new AuthorizationInfoItemView(this.context);
		}
		((AuthorizationInfoItemView) convertView).setView(list.get(position));

		return convertView;
	}
}
