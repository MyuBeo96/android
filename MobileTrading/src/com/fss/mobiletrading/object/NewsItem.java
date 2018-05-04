package com.fss.mobiletrading.object;

import java.util.HashMap;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.consts.StringConst;

public class NewsItem {
	public String Content;
	public String Id;
	public String PostDate;
	public String Title;
	private String Desc;
	private boolean isShortNews;

	public NewsItem(HashMap<String, String> hm) {
		this.Title = hm.get("Title");
		this.PostDate = hm.get("PostDate");
		this.Id = hm.get("Id");
		if (hm.containsKey("Description")) {
			this.Content = hm.get("Description");
		}
		if (hm.containsKey("Content")) {
			this.Content = hm.get("Content");
		}
		if (isShortNews) {

		}
		StringBuilder builder = new StringBuilder();
		if (Title != null) {
			builder.append(Common.convertUTF8ToLatin(Title.toLowerCase()));
			builder.append(StringConst.separator_thang);
		}
		if (Content != null) {
			builder.append(Common.convertUTF8ToLatin(Content.toLowerCase()));
			builder.append(StringConst.separator_thang);
		}
		Desc = builder.toString();

	}

	public String getDesc() {
		return Desc;
	}

	public NewsItem(String title, String content) {
		this.Title = title;
		this.Content = content;
	}

	public boolean isShortNews() {
		return isShortNews;
	}

	public void setShortNews(boolean isShortNews) {
		this.isShortNews = isShortNews;
	}

}
