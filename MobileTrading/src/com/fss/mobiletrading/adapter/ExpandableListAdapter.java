package com.fss.mobiletrading.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.fss.mobiletrading.menu.MenuFunctionItemView;
import com.fss.mobiletrading.menu.MenuItemActionWithImage;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<MenuItemActionWithImage> list_DataHeader;
	private HashMap<Integer, List<MenuItemActionWithImage>> list_DataChild;
	HashMap<Integer, View> hm = new HashMap<Integer, View>();
	int childHashcode = -1;
	int groupHashcode = -1;

	public ExpandableListAdapter(Context context,
			List<MenuItemActionWithImage> listDataHeader,
			HashMap<Integer, List<MenuItemActionWithImage>> listChildData) {
		this.context = context;
		this.list_DataHeader = listDataHeader;
		this.list_DataChild = listChildData;
		for (int i = 0; i < listDataHeader.size(); i++) {
			MenuItemActionWithImage item = list_DataHeader.get(i);
			MenuFunctionItemView view = new MenuFunctionItemView(this.context);
			hm.put(item.hashCode(), view);
			if (item.getChildrenList() != null) {
				for (int j = 0; j < item.getChildrenList().size(); j++) {
					MenuItemActionWithImage childItem = item.getChildrenList()
							.get(j);
					hm.put(childItem.hashCode(), new MenuFunctionItemView(
							this.context));
				}
			}
		}
	}

	@Override
	public int getGroupCount() {

		return list_DataHeader.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		MenuItemActionWithImage item = list_DataHeader.get(groupPosition);
		if (list_DataChild.containsKey(item.hashCode())) {
			return list_DataChild.get(item.hashCode()).size();
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {

		return list_DataHeader.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {

		MenuItemActionWithImage item = list_DataHeader.get(groupPosition);
		if (list_DataChild.containsKey(item.hashCode())) {
			return list_DataChild.get(item.hashCode()).get(childPosition);
		}
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {

		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childPosition;
	}

	@Override
	public boolean hasStableIds() {

		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		MenuItemActionWithImage item = list_DataHeader.get(groupPosition);
		if (!hm.containsKey(item.hashCode())) {
			MenuFunctionItemView view = new MenuFunctionItemView(this.context);
			hm.put(item.hashCode(), view);
		}
		int h = item.hashCode();
		View view = hm.get(h);
//		((MenuFunctionItemView) view).setActivated((h == groupHashcode));

//		((MenuFunctionItemView) view).setView(item);

		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		try {
			MenuItemActionWithImage groupItem = list_DataHeader
					.get(groupPosition);
			if (list_DataChild.containsKey(groupItem.hashCode())) {
				MenuItemActionWithImage childItem = list_DataChild.get(
						groupItem.hashCode()).get(childPosition);
				if (!hm.containsKey(childItem.hashCode())) {
					MenuFunctionItemView view = new MenuFunctionItemView(
							this.context);
					hm.put(childItem.hashCode(), view);
				}
				int h = childItem.hashCode();
				View view = hm.get(h);
//				((MenuFunctionItemView) view)
//						.setActivated((h == childHashcode));
//				((MenuFunctionItemView) view).setView(childItem);
				return view;
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public void setChildActivedPosition(int groupActivedPosition,
			int childActivedPosition) {
		List<MenuItemActionWithImage> l;
		int h = 0;
		if (groupActivedPosition >= 0
				&& groupActivedPosition < list_DataHeader.size()) {
			h = list_DataHeader.get(groupActivedPosition).hashCode();
			if (childActivedPosition == -1) {
				groupHashcode = h;
				childHashcode = 0;
			}
		}

		if (list_DataChild.containsKey(h)) {
			l = list_DataChild.get(h);
			if (childActivedPosition >= 0 && childActivedPosition < l.size()) {
				childHashcode = l.get(childActivedPosition).hashCode();
				groupHashcode = h;
			}
		}

	}
}
