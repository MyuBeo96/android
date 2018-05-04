package com.fss.mobiletrading.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.XmlResourceParser;

import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.menu.MenuItemAction;
import com.fss.mobiletrading.menu.MenuItemActionWithImage;
import com.msbuat.mobiletrading.MainActivity;

public class XmlParser {

	XmlPullParserFactory factory;
	XmlPullParser parser;
	MainActivity mActivity;

	public XmlParser(MainActivity mActivity) {
		// TODO Auto-generated constructor stub
		this.mActivity = mActivity;
		try {
			factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void parse(XmlResourceParser xpp, List<MyMenu> menuList)
	// throws XmlPullParserException, IOException {
	// MyMenu element = null;
	// while (xpp.next() != XmlPullParser.END_DOCUMENT) {
	// int eventType = xpp.getEventType();
	// if (eventType == XmlPullParser.START_TAG) {
	// String tag = xpp.getName();
	// if (tag.matches("tabmenu")) {
	// String title = xpp.getAttributeValue(0);
	// element = new MyMenu();
	// element.setTitle(title);
	// menuList.add(element);
	// }
	// if (tag.matches("child")) {
	// List<MyMenu> childList = new ArrayList<MyMenu>();
	// if (element != null) {
	// element.setChildrenList(childList);
	// }
	// parse(xpp, childList);
	// }
	// } else if (eventType == XmlPullParser.END_TAG) {
	// String tag = xpp.getName();
	// if (tag.matches("child")) {
	// break;
	// }
	// }
	// }
	// }

	public List<MenuItemAction> parseStringAction(XmlResourceParser xpp)
			throws XmlPullParserException, IOException {
		MenuItemAction action = null;
		List<MenuItemAction> menuList = new ArrayList<MenuItemAction>();
		while (xpp.next() != XmlPullParser.END_DOCUMENT) {
			int eventType = xpp.getEventType();
			if (eventType == XmlPullParser.START_TAG) {
				String tag = xpp.getName();
				if (tag.matches("tabmenu")) {
					String className = xpp.getAttributeValue(null, "class");
					String title = xpp.getAttributeValue(null, "title");
					String strStyle = xpp.getAttributeValue(null, "style");
					Class<?> clazz = null;
					try {
						clazz = Class.forName(className);
					} catch (Exception e) {
						className = StringConst.EMPTY;
					}
					int style = MenuItemAction.STYLE_1;
					try {
						style = Integer.parseInt(strStyle);
					} catch (NumberFormatException e) {
					}
					action = new MenuItemAction(title, mActivity, clazz,
							className, style);
					menuList.add(action);
				}
				if (tag.matches("child")) {
					if (action != null) {
						action.setChildrenList(parseStringAction(xpp));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				String tag = xpp.getName();
				if (tag.matches("child")) {
					return menuList;
				}
			}
		}
		return menuList;
	}

	public List<MenuItemActionWithImage> parseStringActionImage(
			XmlResourceParser xpp) throws XmlPullParserException, IOException {
		MenuItemActionWithImage action = null;
		List<MenuItemActionWithImage> menuList = new ArrayList<MenuItemActionWithImage>();
		while (xpp.next() != XmlPullParser.END_DOCUMENT) {
			int eventType = xpp.getEventType();
			if (eventType == XmlPullParser.START_TAG) {
				String tag = xpp.getName();
				if (tag.matches("tabmenu")) {
					String className = xpp.getAttributeValue(null, "class");
					String title = xpp.getAttributeValue(null, "title");
					String parameter = xpp.getAttributeValue(null, "parameter");
					String icon = xpp.getAttributeValue(null, "icon");
					Class<?> clazz = null;
					try {
						clazz = Class.forName(className);
					} catch (Exception e) {
					}
					action = new MenuItemActionWithImage(icon, title,
							mActivity, clazz, parameter);
					menuList.add(action);
				}
				if (tag.matches("child")) {
					if (action != null) {
						action.setChildrenList(parseStringActionImage(xpp));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				String tag = xpp.getName();
				if (tag.matches("child")) {
					return menuList;
				}
			}
		}
		setIndexPositionForMenu(menuList);
		return menuList;
	}

	private void setIndexPositionForMenu(List<MenuItemActionWithImage> list) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				MenuItemActionWithImage item = list.get(i);
				item.locations.add(i);
				List<MenuItemActionWithImage> childs = item.getChildrenList();
				if (childs != null) {
					for (int j = 0; j < childs.size(); j++) {
						MenuItemActionWithImage item2 = childs.get(j);
						item2.locations.add(i);
						item2.locations.add(j);
					}
				}
			}
		}
	}

}
