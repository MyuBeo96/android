package com.fss.mobiletrading.function.watchlist;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.function.watchlist.FavWatchListItem;
import com.fss.mobiletrading.function.watchlist.FavWatchListItemView;
import com.fss.mobiletrading.object.BangGia_Item;
import com.fss.mobiletrading.view.NorOrderListItemView;

public class FavWatchList_Adapter extends ArrayAdapter<FavWatchListItem> {
	List<FavWatchListItem> list;
	SimpleAction deleteAction;
	SimpleAction clickAction;

	Context context;

	public FavWatchList_Adapter(Context paramContext, int paramInt,
			List<FavWatchListItem> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list = paramList;
	}

	HashMap<Integer, View> hm = new HashMap<Integer, View>();

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new FavWatchListItemView(context, deleteAction,
					clickAction);
		}
		((FavWatchListItemView) convertView).setView(list.get(position));
		return convertView;
	}

	public void setDeleteAction(SimpleAction deleteAction) {
		this.deleteAction = deleteAction;
	}

	public void setClickAction(SimpleAction clickAction) {
		this.clickAction = clickAction;
	}

}
