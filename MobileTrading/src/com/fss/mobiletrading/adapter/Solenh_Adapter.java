package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fss.mobiletrading.common.CustomHorizontalScrollView;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.SolenhItem;
import com.fss.mobiletrading.view.NorOrderListItemView;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;
import java.util.ArrayList;
import java.util.List;

public class Solenh_Adapter extends ArrayAdapter<SolenhItem> {

	static String separator = ",";
	String selectedOrderId = StringConst.EMPTY;
	Context context;
	List<SolenhItem> list;
	CustomHorizontalScrollView mFocusView;
	SimpleAction mItemClickAction;
	SimpleAction mBuyClickAction;
	SimpleAction mSellClickAction;
	SimpleAction mCancelClickAction;
	SimpleAction mItemLongClickAction;
	SimpleAction mAmendClickAction;
	boolean isselect = false;
	int item_odd_bg_color;
	int item_even_bg_color;

	/**
	 * biến cho phép adapter có đc update hay k Nếu có 1 item đang trong trạng
	 * thái thay đổi vị trí thì k đc phép update
	 */
	public boolean isUpdate = true;

	public Solenh_Adapter(Context context, int paramInt, List<SolenhItem> object) {
		super(context, paramInt, object);
		this.context = context;
		item_odd_bg_color = context.getResources().getColor(
				R.color.listviewitem_odd_bg);
		item_even_bg_color = context.getResources().getColor(
				R.color.listviewitem_even_bg);
		list = object;
		filterItemPosition = new ArrayList<Integer>();
	}

	List<Integer> filterItemPosition;
	int countData;

	@Override
	public int getCount() {
		return filterItemPosition.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new NorOrderListItemView(context, this,
					mItemClickAction, mBuyClickAction, mSellClickAction,
					mCancelClickAction, mItemLongClickAction);
			if (DeviceProperties.isTablet) {
				((NorOrderListItemView) convertView)
						.setOnSelectedCancelOrderItem(new OnSelectedCancelOrderItem() {

							@Override
							public void onSelected(boolean isSelected,
									String orderId) {
								if (orderId != null) {
									if (isSelected) {
										if (!selectedOrderId.contains(orderId)) {
											selectedOrderId = selectedOrderId
													+ orderId + separator;
										}

									} else {
										if (selectedOrderId
												.contains((orderId + separator))) {
											selectedOrderId = selectedOrderId.replace(
													(orderId + separator),
													StringConst.EMPTY);
										}
									}
								}
							}
						});
			}
			((NorOrderListItemView) convertView)
					.setAmendClickAction(mAmendClickAction);
		}
		if (DeviceProperties.isTablet) {
			if ((position % 2) == 0) {
				convertView.setBackgroundColor(item_even_bg_color);
			} else {
				convertView.setBackgroundColor(item_odd_bg_color);
			}
		}
		SolenhItem item = null;
		item = list.get(filterItemPosition.get(position));
		((NorOrderListItemView) convertView).setView(item,
				(selectedOrderId.contains(item.OrderId)));
		return convertView;
	}

	public void setItemClickAction(SimpleAction action) {
		mItemClickAction = action;
	}

	public void setmBuyClickAction(SimpleAction mBuyClickAction) {
		this.mBuyClickAction = mBuyClickAction;
	}

	public void setmSellClickAction(SimpleAction mSellClickAction) {
		this.mSellClickAction = mSellClickAction;
	}

	public void setmCancelClickAction(SimpleAction mCancelClickAction) {
		this.mCancelClickAction = mCancelClickAction;
	}

	public void setmItemLongClickAction(SimpleAction mItemLongClickAction) {
		this.mItemLongClickAction = mItemLongClickAction;
	}

	public void setmAmendClickAction(SimpleAction mAmendClickAction) {
		this.mAmendClickAction = mAmendClickAction;
	}

	@Override
	public void notifyDataSetChanged() {
		if (isUpdate) {
			super.notifyDataSetChanged();
		}
	}

	public void selectedCancelAllItem(boolean isselected) {
		int sizelist = list.size();
		if (isselected) {
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < sizelist; i++) {
				b.append(list.get(i).OrderId + separator);
			}
			selectedOrderId = b.toString();
			super.notifyDataSetChanged();
		} else {
			selectedOrderId = StringConst.EMPTY;
			super.notifyDataSetChanged();
		}
	}

	public interface OnSelectedCancelOrderItem {
		public void onSelected(boolean isSelected, String orderId);
	}

	public String getSelectedCancelItem() {
		StringBuilder builder = new StringBuilder();
		return selectedOrderId;
	}

	public List<Integer> getFilterItemPosition() {
		return filterItemPosition;
	}

	public void setFilterItemPosition(List<Integer> filterItemPosition) {
		this.filterItemPosition = filterItemPosition;
	}
}
