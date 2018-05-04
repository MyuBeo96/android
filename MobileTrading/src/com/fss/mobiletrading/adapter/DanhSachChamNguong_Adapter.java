package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.function.watchlist.BangGia_View;
import com.fss.mobiletrading.object.DanhSachChamNguongItem;
import com.fss.mobiletrading.view.DanhSachChamNguongItemView;

public class DanhSachChamNguong_Adapter extends
		ArrayAdapter<DanhSachChamNguongItem> {
	Context context;
	List<DanhSachChamNguongItem> listItem;
	Map<Integer, DanhSachChamNguongItemView> mv_Views = new HashMap<Integer, DanhSachChamNguongItemView>();

	public DanhSachChamNguong_Adapter(Context paramContext, int paramInt,
			List<DanhSachChamNguongItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.listItem = paramList;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (!hm.containsKey(position)) {
			DanhSachChamNguongItemView view = new DanhSachChamNguongItemView(
					this.context);
			hm.put(position, view);
			if (position % 2 == 0) {
				view.setBackgroundResource(R.drawable.background_listview_item);
			}
		}
		View view = hm.get(position);
		((DanhSachChamNguongItemView) view).setView(listItem.get(position));

		return view;
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.adapter.DanhSachChamNguong_Adapter JD-Core Version: 0.6.0
 */