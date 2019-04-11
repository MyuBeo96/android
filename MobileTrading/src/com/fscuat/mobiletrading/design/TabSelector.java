package com.tcscuat.mobiletrading.design;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;

import com.tcscuat.mobiletrading.R;

public class TabSelector extends LinearLayout {

	List<TabItem> items;
	OnItemSelectedListener onTabSelectedListener;
	int selectedTabPosition = -1;

	public TabSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
		items = new ArrayList<TabItem>();
		setBackgroundResource(R.color.header_background_color);
	}

	private void trigger(View v) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setSelected(false);
		}
		selectedTabPosition = indexOfChild(v);
		items.get(indexOfChild(v)).setSelected(true);
		onTabSelectedListener.onItemSelected(null, v, indexOfChild(v),
				v.getId());
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		setOnTabItemClick();
	}

	public void setOnTabItemClick() {
		for (int i = 0; i < getChildCount(); i++) {
			if (this.getChildAt(i) instanceof TabItem) {
				TabItem item = (TabItem) this.getChildAt(i);
				item.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						trigger(v);
					}
				});
				items.add(item);
			}
		}
	}

	public void setOnTabSelectedListener(
			OnItemSelectedListener onTabSelectedListener) {
		this.onTabSelectedListener = onTabSelectedListener;
	}

	/**
	 * tự động chọn item, cập nhật ui và thực hiện hàm trigger của item đó
	 * 
	 * @param position
	 */
	public void setItemSelected(int position) {
		try {
			if (items.get(position) != null) {
				trigger(items.get(position));
			}

		} catch (IndexOutOfBoundsException e) {
		}
	}

	/**
	 * tự động chọn item, chỉ cập nhập ui
	 * 
	 * @param position
	 */
	public void setUIItemSelected(int position) {
		try {
			View v = items.get(position);
			if (v != null) {
				for (int i = 0; i < items.size(); i++) {
					items.get(i).setSelected(false);
				}
				selectedTabPosition = indexOfChild(v);
				items.get(indexOfChild(v)).setSelected(true);
			}
		} catch (IndexOutOfBoundsException e) {
		}
	}

	/**
	 * Trả v�? vị trí của tab đang selected.
	 * 
	 * @return trả v�? -1 nếu k có tab nào đang đc ch�?n
	 */
	public int getTabSelectedPosition() {
		return selectedTabPosition;
	}

	public TabItem getTabItemAtPosition(int position) {
		try {
			return items.get(position);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}

	}

	public void setVisibility(int visibility, int position) {
		TabItem item = (TabItem) this.getChildAt(position);
		if (item != null) {
			item.setVisibility(visibility);
		}
	}
}
