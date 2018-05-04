package com.fss.mobiletrading.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.MyJsonObject;

public class RptFieldItem {
	public String FName;
	public String FValue;
	public String FTitle;
	public String FType;
	public String FSearch;
	public String FFilter;
	public boolean IsParam;
	public List<ItemString2> FSet;
	static final String key = "Key";
	static final String val = "Val";
	public boolean isVisible = true;
	public boolean isDisable = false;

	public RptFieldItem(String para_FName, String para_FValue,
			String para_FTitle, String para_FType, String para_FSearch,
			String para_FFilter, boolean visible, boolean disable,
			MyJsonArray para_FSet) {
		super();
		FName = para_FName;
		FValue = para_FValue;
		FTitle = para_FTitle;
		FType = para_FType;
		FSearch = para_FSearch;
		FFilter = para_FFilter;
		isDisable = disable;
		isVisible = visible;	
		if (para_FSet == null) {
			FSet = null;
		} else {
			FSet = new ArrayList<ItemString2>();
			int paraFSetLength = para_FSet.length();
			for (int i = 0; i < paraFSetLength; i++) {
				MyJsonObject item = para_FSet.getJSONObject(i);
				FSet.add(new ItemString2(item.getString(val), item
						.getString(key)));
			}

		}
	}
}
