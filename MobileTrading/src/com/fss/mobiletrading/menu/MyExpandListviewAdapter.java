package com.fss.mobiletrading.menu;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.internal.hi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MyExpandListviewAdapter extends
		ArrayAdapter<MenuItemActionWithImage> {

	private List<MenuItemActionWithImage> data;
	private List<SlideMenuItem> list;
	private List<SlideMenuItem> highlights;
	Context context;
	/**
	 * chứa position của các item đang đc actived trong list slidemenuitem
	 */
	List<Integer> listActived;

	public MyExpandListviewAdapter(Context context, int resource,
			List<MenuItemActionWithImage> objects) {
		super(context, resource, objects);
		this.context = context;
		data = objects;
		list = new ArrayList<SlideMenuItem>();
		listActived = new ArrayList<Integer>();
		highlights = new ArrayList<SlideMenuItem>();
		genSlideMenuItems();
	}

	private void genSlideMenuItems() {
		list.clear();
		for (int i = 0; i < data.size(); i++) {
			SlideMenuItem item = data.get(i).getSlideMenuItem();
			item.isExpand = false;
			list.add(item);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new MenuFunctionItemView(context);
		}
		((MenuFunctionItemView) convertView).setView(list.get(position));
		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		genSlideMenuItems();
		super.notifyDataSetChanged();
	}

	/**
	 * expand groupItem tại vị trí chỉ định trong list slidemenuitem, nếu phần <br>
	 * tử không phải là groupItem thì không làm gì cả
	 * 
	 * @param position
	 *            vị trí trong list slidemenuitem
	 * @return trả về số childItem được expand
	 */
	public int expand(int position) {
		if (position >= 0 && position < list.size()) {
			MenuItemActionWithImage groupItem = getGroupItem(list.get(position));
			if (groupItem == null) {
				return position;
			}
			List<MenuItemActionWithImage> childs = groupItem.getChildrenList();
			if (childs != null) {
				list.get(position).isExpand = true;
				for (int i = childs.size() - 1; i >= 0; i--) {
					list.add(position + 1, childs.get(i).getSlideMenuItem());
				}
				super.notifyDataSetChanged();
				return childs.size();
			}
		}
		return position;
	}

	/**
	 * collapse groupItem tại vị trí chỉ định trong list slidemenuitem, nếu phần <br>
	 * tử không phải là groupItem thì không làm gì cả
	 * 
	 * @param position
	 *            vị trí trong list slidemenuitem
	 */
	public void collapse(int position) {
		if (position >= 0 && position < list.size()) {
			MenuItemActionWithImage groupItem = getGroupItem(list.get(position));
			if (groupItem == null) {
				return;
			}
			List<MenuItemActionWithImage> childs = groupItem.getChildrenList();
			if (childs != null) {
				list.get(position).isExpand = false;
				for (int i = 1; i <= childs.size(); i++) {
					list.remove(position + 1);
				}
				super.notifyDataSetChanged();
			}
		}
	}

	/**
	 * tìm kiếm vị trí của group trong list SlideMenuItem
	 * 
	 * @param group
	 *            group muốn tìm kiếm vị trí
	 * @return trả về vị trí đầu tiên của group đc tìm thấy trong list, <br>
	 *         hoặc trả về -1 nếu không tìm thấy
	 */
	private int getGroupPosition(MenuItemActionWithImage group) {
		return list.indexOf(group.getSlideMenuItem());
	}

	private MenuItemActionWithImage getGroupItem(SlideMenuItem item) {
		if (item.locations.size() == 1) {
			return getMenuActionItem(item);
		}
		return null;
	}

	public MenuItemActionWithImage getMenuActionItem(SlideMenuItem item) {
		MenuItemActionWithImage menuItemActionWithImage = data
				.get(item.locations.get(0));
		if (item.locations.size() == 2) {
			return menuItemActionWithImage.getChildrenList().get(
					item.locations.get(1));
		} else {
			return menuItemActionWithImage;
		}
	}

	public MenuItemActionWithImage getMenuActionItem(int position) {
		SlideMenuItem item = list.get(position);
		if (item != null) {
			return getMenuActionItem(item);
		}
		return null;
	}

	public List<Integer> getLocations(String className) {
		if (className == null) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			SlideMenuItem item = list.get(i);
			if (className.equals(item.className)) {
				return item.locations;
			}
		}
		return null;
	}

	public void collapseAll() {
		notifyDataSetChanged();
	}

	/**
	 * kiểm tra phần tử ở 1 vị trí xác định trong adapter có phải là groupItem
	 * hay không
	 * 
	 * @param position
	 *            vị trí của phần tử cần xác định
	 * @return trả về true nếu nó là groupItem, trả về false nếu không tìm thấy <br>
	 *         phần tử hoặc phần tử không phải là groupItem
	 */
	public boolean isGroupItem(int position) {
		if (position >= 0 && position < list.size()) {
			return list.get(position).isGroupItem;
		} else {
			return false;
		}
	}

	/**
	 * kiểm tra phần tử ở 1 vị trí xác định trong adapter có phải là childItem
	 * hay không
	 * 
	 * @param position
	 *            vị trí của phần tử cần xác định
	 * @return trả về true nếu nó là childItem, trả về false nếu không tìm thấy <br>
	 *         phần tử hoặc phần tử không phải là childItem
	 */
	public boolean isChildItem(int position) {
		if (position >= 0 && position < list.size()) {
			return !(list.get(position).isGroupItem);
		} else {
			return false;
		}
	}

	/**
	 * kiểm tra phần tử ở 1 vị trí xác định trong adapter có đang expand hay
	 * không
	 * 
	 * @param position
	 *            vị trí của phần tử cần xác định
	 */
	public boolean isExpand(int position) {
		return list.get(position).isExpand;
	}

	public boolean isLogoutMenuItem(int position) {
		if (position == list.size() - 1) {
			return true;
		}
		return false;
	}

	public void addActivedItemPosition(int position) {
		listActived.add(position);
	}

	public void removeActivedItemPosition(int position) {
		int i = listActived.indexOf(position);
		if (i != -1) {
			listActived.remove(i);
		}
	}

	public void highlight(int position) {
		SlideMenuItem item = list.get(position);
		if (item != null) {
			item.isHighLight = true;
			if (!highlights.contains(item)) {
				highlights.add(item);
			}
		}
	}

	public void removeAllHighlight() {
		for (int i = 0; i < highlights.size(); i++) {
			SlideMenuItem item = highlights.get(i);
			item.isHighLight = false;
		}
		highlights.clear();
	}

	public void superNotifyDataSetChange() {
		super.notifyDataSetChanged();
	}
}
