package com.fss.mobiletrading.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Service;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.common.XmlParser;
import com.fss.mobiletrading.function.AppData;
import com.msbuat.mobiletrading.Login;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;

public class SlideMenu extends LinearLayout {

	LayoutInflater inflater;
	ImageButton btn_logout;
	ListView lv_SlideMenu;
	ImageView logo;
	MyExpandListviewAdapter adapter_expendable;
	TextView tv_sotieukhoan;

	List<MenuItemActionWithImage> list_DataHeader;
	List<SlideMenuItem> map;
	MainActivity mainActivity;

	public SlideMenu(Context context, AttributeSet attrs) {
		super(context, attrs);

		inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.slidemenu, this);
		logo = (ImageView) findViewById(R.id.logo);
		btn_logout = (ImageButton) findViewById(R.id.btn_slidemenu_logout);
		lv_SlideMenu = (ListView) findViewById(R.id.list_slidermenu);
		mainActivity = (MainActivity) context;
		init();
	}

	private void init() {
		initialise();
	}

	private void initialise() {
		if (AppData.language.equals(AppData.LOCALE_EN)) {
//			logo.setImageResource(R.drawable.ic_slidemenu_logo_en);
			logo.setImageResource(R.drawable.ic_fsc);
		}
		initialiseData();
		initialiseListener();
	}

	private void initialiseData() {
		if (list_DataHeader == null) {
			try {
				XmlParser xmlParser = new XmlParser(mainActivity);
				if (AppData.language == AppData.LOCALE_VI_VN) {
					if (StaticObjectManager.loginInfo.IsBroker) {
						list_DataHeader = xmlParser
								.parseStringActionImage(mainActivity
										.getResources()
										.getXml(R.xml.declare_menu_mobile_broker_vi));
					}
					else {
						list_DataHeader = xmlParser
								.parseStringActionImage(mainActivity
										.getResources().getXml(
												R.xml.declare_menu_mobile_vi));
					}
				}
				else if(AppData.language == AppData.LOCALE_ZH){
					if (StaticObjectManager.loginInfo.IsBroker) {
						list_DataHeader = xmlParser
								.parseStringActionImage(mainActivity
										.getResources()
										.getXml(R.xml.declare_menu_mobile_broker_en));
					} else {
						list_DataHeader = xmlParser
								.parseStringActionImage(mainActivity
										.getResources().getXml(
												R.xml.declare_menu_mobile_zh));
					}
				}
				else {
					if (StaticObjectManager.loginInfo.IsBroker) {
						list_DataHeader = xmlParser
								.parseStringActionImage(mainActivity
										.getResources()
										.getXml(R.xml.declare_menu_mobile_broker_en));
					} else {
						list_DataHeader = xmlParser
								.parseStringActionImage(mainActivity
										.getResources().getXml(
												R.xml.declare_menu_mobile_en));
					}
				}
			} catch (NotFoundException e) {

				e.printStackTrace();
			} catch (XmlPullParserException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}

			if (adapter_expendable == null) {
				adapter_expendable = new MyExpandListviewAdapter(mainActivity,
						android.R.layout.simple_list_item_1, list_DataHeader);
			}
			lv_SlideMenu.setAdapter(adapter_expendable);
			map = new ArrayList<SlideMenuItem>();
			for (int i = 0; i < list_DataHeader.size(); i++) {
				MenuItemActionWithImage item = list_DataHeader.get(i);
				map.add(item.getSlideMenuItem());
				List<MenuItemActionWithImage> childs = item.getChildrenList();
				if (childs != null) {
					for (int j = 0; j < childs.size(); j++) {
						MenuItemActionWithImage item2 = childs.get(j);
						map.add(item2.getSlideMenuItem());
					}
				}
			}
		}
	}

	private void initialiseListener() {
		lv_SlideMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (adapter_expendable.isLogoutMenuItem(position)) {
					mainActivity.logout();
					return;
				}
				if (adapter_expendable.isGroupItem(position)) {
					// trường hợp là groupItem
					if (adapter_expendable.isExpand(position)) {
						// nếu groupItem đang expand thì collapse
						adapter_expendable.collapse(position);
					} else {
						// nếu groupItem đang collapse thì expand
						int numberExpanded = adapter_expendable
								.expand(position);
						autoSetSelection(position + numberExpanded);
					}
				} else {
					// trường hợp là childItem
					MenuItemActionWithImage childItem = adapter_expendable
							.getMenuActionItem(position);
					if (childItem != null) {
						childItem.performAction();
					}
				}
			}
		});
	}

	private void autoSetSelection(int position) {
		int first = lv_SlideMenu.getFirstVisiblePosition();
		int end = lv_SlideMenu.getChildCount() + first;
		if (position < first || position >= end) {
			int selection = position - (end - first) + 2;
			if (selection >= 0) {
				lv_SlideMenu.setSelection(selection);
			} else {
				lv_SlideMenu.setSelection(0);
			}
		}
	}

	public void onOpen() {
	}

	public void onClose() {
	}

	public void setTitle(String str) {
	}

	public void setOnButtonClickListener(OnClickListener listener) {
		btn_logout.setOnClickListener(listener);
	}

	public void setSelectedItem(String className) {
		if (className == null) {
			return;
		}
		adapter_expendable.collapseAll();
		for (int i = 0; i < map.size(); i++) {
			SlideMenuItem item = map.get(i);
			if (className.equals(item.className)) {
				List<Integer> locations = item.locations;
				if (locations != null) {
					adapter_expendable.removeAllHighlight();
					lv_SlideMenu.setSelection(locations.get(0));
					adapter_expendable.expand(locations.get(0));
					adapter_expendable.highlight(locations.get(0));
					if (locations.size() > 1) {
						adapter_expendable.highlight(locations.get(0)
								+ locations.get(1) + 1);
					}
					adapter_expendable.superNotifyDataSetChange();
				}
			}
		}
	}
}
