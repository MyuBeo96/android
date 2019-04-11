package com.tcscuat.mobiletrading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.menu.MenuItemAction;
import com.tcscuat.mobiletrading.design.TabItem;
import com.tcscuat.mobiletrading.design.TabMenuLevelFirstItem;
import com.tcscuat.mobiletrading.design.TabMenuLevelSecondItem;
import com.tcscuat.mobiletrading.design.TabMenuLevelThirdItem;
import com.tcscuat.mobiletrading.design.TabMenuLevelThirdItem_2;
import com.tcscuat.mobiletrading.design.TabSelector;

/**
 * Class này đại diện cho 1 menu ngang 3 cấp dạng tab, mỗi một tab chứa 1 class
 * dạng fragment hoặc các tab con. <br>
 * gồm các API
 * <p>
 * - api khởi tạo:
 * <p>
 * - api set tab được chọn trong trường hợp người dùng chuyển đến class không
 * thông qua menu
 * 
 * @author giang.ngo
 * @version 1.0
 * 
 */
public class TabMenu {

	HashMap<Integer, TabSelector> hmap = new HashMap<Integer, TabSelector>();
	Context context;
	LinearLayout lay_menulevel1;
	LinearLayout lay_menulevel2;
	LinearLayout lay_menulevel3;
	HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
	TabSelector originalmenu;
	TabSelector secondmenu;
	TabSelector thirdmenu;
	final Integer[] defaultLocation = { 0, 0, 0 };
	Integer[] location = { 0, 0, 0 };

	public TabMenu(LinearLayout pv_menulevel1, LinearLayout pv_menulevel2,
			LinearLayout pv_menulevel3, List<MenuItemAction> list,
			Context pv_context) {
		lay_menulevel1 = pv_menulevel1;
		lay_menulevel2 = pv_menulevel2;
		lay_menulevel3 = pv_menulevel3;
		context = pv_context;
		for (int i = 0; i < list.size(); i++) {
			List<Integer> locations = new ArrayList<Integer>();
			locations.add(i);
			MenuItemAction item = list.get(i);
			if (item.getClassName().length() != 0) {
				map.put(item.getClassName(), locations);
			} else {
				List<MenuItemAction> child = item.getChildrenList();
				if (child != null && child.size() > 0) {
					for (int j = 0; j < child.size(); j++) {
						List<Integer> childlocation = new ArrayList<Integer>();
						childlocation.addAll(locations);
						childlocation.add(j);
						initMap(child.get(j), childlocation);
					}
				}
			}
		}
		initMenuLevelOne(list);
	}

	// /**
	// * khi truyền className vào method sẽ check nếu className nằm trong menu
	// sẽ
	// * tự động trỏ tới vị trí className trong menu
	// */
	// public void setLocationInMenu(String className) {
	// if (className == null) {
	// return;
	// }
	// // nếu class nằm trong danh sách class của menu thì tiếp tục
	// if (map.containsKey(className)) {
	// // locations là mảng chứa các vị trí trên menu cấp 1, menu cấp 2,
	// // menu cấp 3 tương ứng để trỏ tới class
	// List<Integer> locations = map.get(className);
	// // nếu class thuộc tabitem của menu cấp 1
	// if (locations.size() <= 1 || locations.get(1) == null) {
	// // check vị trí phải khác tabitem đang chọn của menu cấp 1
	// if (originalmenu.getTabSelectedPosition() != locations.get(0)) {
	// originalmenu.setUIItemSelected(locations.get(0));
	// if (secondmenu != null) {
	// secondmenu.removeAllViews();
	// }
	// if (thirdmenu != null) {
	// thirdmenu.removeAllViews();
	// }
	// }
	// } else {
	// // check vị trí phải khác tabitem đang chọn của menu cấp 1
	// if (originalmenu.getTabSelectedPosition() != locations.get(0)) {
	// originalmenu.setItemSelected(locations.get(0));
	// }
	// // nếu class thuộc tabitem của menu cấp 2
	// if (locations.size() <= 2 || locations.get(2) == null) {
	// // check vị trí phải khác tabitem đang chọn của menu cấp 2
	// if (secondmenu.getTabSelectedPosition() != locations.get(1)) {
	// secondmenu.setUIItemSelected(locations.get(1));
	// if (thirdmenu != null) {
	// thirdmenu.removeAllViews();
	// }
	// }
	// } else {
	// // check vị trí phải khác tabitem đang chọn của menu cấp 2
	// int l1 = locations.get(1);
	// if (secondmenu.getTabSelectedPosition() != l1) {
	// TabItem item = secondmenu.getTabItemAtPosition(l1);
	// if (item instanceof TabMenuLevelSecondItem) {
	// ((TabMenuLevelSecondItem) item)
	// .setPositionSelected(locations.get(2));
	// }
	// secondmenu.setItemSelected(l1);
	// }
	// // // check vị trí phải khác tabitem đang chọn của menu cấp
	// // 3
	// // if (thirdmenu.getTabSelectedPosition() !=
	// // locations.get(2)) {
	// // thirdmenu.setUIItemSelected(locations.get(2));
	// // }
	// }
	// }
	// }
	// }
	/**
	 * khi truyền className vào method sẽ check nếu className nằm trong menu sẽ
	 * tự động trỏ tới vị trí className trong menu
	 */
	public void setLocationInMenu(String className) {
		if (className == null) {
			return;
		}
		// nếu class nằm trong danh sách class của menu thì tiếp tục
		if (map.containsKey(className)) {
			// locations là mảng chứa các vị trí trên menu cấp 1, menu cấp 2,
			// menu cấp 3 tương ứng để trỏ tới class
			List<Integer> locations = map.get(className);
			for (int i = 0; i < locations.size(); i++) {
				if (i < location.length) {
					location[i] = locations.get(i);
				} else {
					break;
				}
			}
			originalmenu.setItemSelected(location[0]);
		}
		for (int i = 0; i < defaultLocation.length; i++) {
			location[i] = defaultLocation[i];
		}
	}

	private void initMap(MenuItemAction item, List<Integer> locations) {
		if (item.getClassName().length() != 0) {
			map.put(item.getClassName(), locations);
		} else {
			List<MenuItemAction> child = item.getChildrenList();
			if (child != null && child.size() > 0) {
				for (int i = 0; i < child.size(); i++) {
					List<Integer> childlocation = new ArrayList<Integer>();
					childlocation.addAll(locations);
					childlocation.add(i);
					initMap(child.get(i), childlocation);
				}
			}
		}
	}

	private void initMenuLevelOne(List<MenuItemAction> list) {
		originalmenu = new TabSelector(context, null);
		originalmenu.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		originalmenu.setBackgroundResource(R.drawable.bg_ffffff_corner5);
		int size_list = list.size();
		for (int i = 0; i < size_list; i++) {
			MenuItemAction menuItem = list.get(i);
			TabMenuLevelFirstItem item = new TabMenuLevelFirstItem(context,
					null);
			item.setAction(menuItem);
			if (i == 0) {
				item.setBackgroundResource(R.drawable.bg_tabmenulevelone_item_start);
			} else if (i == size_list - 1) {
				item.setBackgroundResource(R.drawable.bg_tabmenulevelone_item_end);
			} else {
				item.setBackgroundResource(R.drawable.bg_tabmenulevelone_item);
			}
			originalmenu.addView(item, i);
		}
		originalmenu.setOnTabItemClick();
		originalmenu.setOnTabSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (view instanceof TabMenuLevelFirstItem) {
					TabMenuLevelFirstItem item = (TabMenuLevelFirstItem) view;
					List<MenuItemAction> child = item.getAction()
							.getChildrenList();
					if (child != null && child.size() > 0) {
						uploadMenuLevelSecond(child);
					} else {
						uploadMenuLevelSecond(null);
						item.performAction();
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		if (lay_menulevel1 != null) {
			lay_menulevel1.addView(originalmenu);
		}
	}

	int padding = Common.convertDp2Px(context, 5);

	private void uploadMenuLevelSecond(List<MenuItemAction> list) {
		if (list == null) {
			lay_menulevel2.removeAllViews();
			uploadMenuLevelThird(null);
			return;
		}
		if (!hmap.containsKey(list.hashCode())) {
			TabSelector menulevel = new TabSelector(context, null);
			menulevel.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			menulevel.setPadding(0, padding, 0, padding);
			for (int i = 0; i < list.size(); i++) {
				MenuItemAction menuItem = list.get(i);
				if (menuItem.getStyle() == MenuItemAction.STYLE_1) {
					TabMenuLevelSecondItem item = new TabMenuLevelSecondItem(
							context, null, list.size());
					item.setAction(menuItem);
					menulevel.addView(item, i);
				} else {
					TabMenuLevelThirdItem item = new TabMenuLevelThirdItem(
							context, null);
					item.setAction(menuItem);
					menulevel.addView(item, i);
				}
			}
			menulevel.setOnTabItemClick();
			menulevel.setOnTabSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					if (view instanceof TabMenuLevelSecondItem) {
						TabMenuLevelSecondItem item = (TabMenuLevelSecondItem) view;
						List<MenuItemAction> child = item.getAction()
								.getChildrenList();
						if (child != null && child.size() > 0) {
							uploadMenuLevelThird(child);
						} else {
							uploadMenuLevelThird(null);
							item.performAction();
						}
					} else if (view instanceof TabMenuLevelThirdItem) {
						TabMenuLevelThirdItem item = (TabMenuLevelThirdItem) view;
						List<MenuItemAction> child = item.getAction()
								.getChildrenList();
						if (child != null && child.size() > 0) {
							uploadMenuLevelThird(child);
						} else {
							uploadMenuLevelThird(null);
							item.performAction();
						}
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
			hmap.put(list.hashCode(), menulevel);
		}
		if (lay_menulevel2 != null) {
			lay_menulevel2.removeAllViews();
			secondmenu = hmap.get(list.hashCode());
			lay_menulevel2.addView(secondmenu);
			secondmenu.setItemSelected(location[1]);

		}
	}

	private void uploadMenuLevelThird(List<MenuItemAction> list) {
		if (list == null) {
			lay_menulevel3.removeAllViews();
			return;
		}
		if (!hmap.containsKey(list.hashCode())) {
			TabSelector menulevel = new TabSelector(context, null);
			menulevel.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			menulevel.setPadding(0, padding, 0, padding);
			for (int i = 0; i < list.size(); i++) {
				MenuItemAction menuItem = list.get(i);
				if (menuItem.getStyle() == MenuItemAction.STYLE_1) {
					TabMenuLevelSecondItem item = new TabMenuLevelSecondItem(
							context, null, list.size());
					item.setAction(menuItem);
					menulevel.addView(item, i);
				} else {
					TabMenuLevelThirdItem_2 item = new TabMenuLevelThirdItem_2(
							context, null);
					item.setAction(menuItem);
					menulevel.addView(item, i);
				}
			}
			menulevel.setOnTabItemClick();
			menulevel.setOnTabSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					if (view instanceof TabMenuLevelThirdItem_2) {
						TabMenuLevelThirdItem_2 item = (TabMenuLevelThirdItem_2) view;
						item.performAction();
					} else if (view instanceof TabMenuLevelSecondItem) {
						TabMenuLevelSecondItem item = (TabMenuLevelSecondItem) view;
						item.performAction();
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			hmap.put(list.hashCode(), menulevel);
		}
		if (lay_menulevel3 != null) {
			lay_menulevel3.removeAllViews();
			thirdmenu = hmap.get(list.hashCode());
			lay_menulevel3.addView(thirdmenu);
			thirdmenu.setItemSelected(location[2]);
		}
	}
}
