package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.watchlist.BangGia_View;
import com.fss.mobiletrading.function.watchlist.T_BangGia_View;
import com.fss.mobiletrading.object.BangGia_Item;
import com.fscuat.mobiletrading.DeviceProperties;
import java.util.List;

/**
 * @author Admin
 * 
 */
public class BangGia_Adapter extends ArrayAdapter<BangGia_Item> {
	List<BangGia_Item> list;
	Context context;
	SimpleAction mItemClickAction;
	SimpleAction mCancelClickAction;
	public boolean isUpdate = true;
	boolean isItemScrollable = true;
	String stockSelected = StringConst.EMPTY;

	public BangGia_Adapter(Context paramContext, int paramInt,
			List<BangGia_Item> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.list = paramList;
	}

	public View getView(int position, View convertView, ViewGroup ViewGroup) {
		if (convertView == null) {
			if (DeviceProperties.isTablet) {
				convertView = new T_BangGia_View(this.context);
			} else {
				convertView = new BangGia_View(this.context, this,
						mItemClickAction, mCancelClickAction);
			}

		}
		BangGia_Item item = list.get(position);
		((BangGia_View) convertView).setView(item);
		return convertView;
	}

	/**
	 * xóa highlight
	 */
	public void clearHighLight() {
		int listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			list.get(i).setOldItem(null);
		}
		notifyDataSetChanged();
	}

	/**
	 * đổ dữ liệu vào listview
	 */
	public void loadData() {
		isUpdate = true;
		notifyDataSetChanged();
	}

	public void setItemClickAction(SimpleAction action) {
		mItemClickAction = action;
	}

	public void setmCancelClickAction(SimpleAction mCancelClickAction) {
		this.mCancelClickAction = mCancelClickAction;
	}

	@Override
	public void notifyDataSetChanged() {
		if (isUpdate) {
			super.notifyDataSetChanged();
		}
	}

	public boolean isItemScrollable() {
		return isItemScrollable;
	}

	public void setItemScrollable(boolean isItemScrollable) {
		this.isItemScrollable = isItemScrollable;
	}

}
