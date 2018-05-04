package com.fss.mobiletrading.function.watchlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.consts.StringConst;

public class FavWatchListItem {
	String CriteriaId = StringConst.EMPTY;
	String MarketId = StringConst.EMPTY;
	String CName = StringConst.EMPTY;
	List<FavWatchListItem> Subs;
	String FavMem = StringConst.EMPTY;
	int icon = 0;

	public FavWatchListItem(HashMap<String, String> hm) {
		CriteriaId = hm.get("CriteriaId");
		MarketId = hm.get("MarketId");
		CName = hm.get("CName");
		FavMem = hm.get("FavMem");
		MyJsonArray childs = null;
		try {
			childs = new MyJsonArray(hm.get("Subs"));
		} catch (NullPointerException e) {
		} catch (JSONException e) {
		}
		Subs = new ArrayList<FavWatchListItem>();
		if (childs != null && childs.length() > 0) {
			for (int i = 0; i < childs.length(); i++) {
				Subs.add(new FavWatchListItem(childs.getJSONObject(i)
						.getHashMap()));
			}
		}

	}

	public FavWatchListItem(String criteriaId, String marketId, String cname) {
		CriteriaId = criteriaId;
		MarketId = marketId;
		CName = cname;
	}

	public FavWatchListItem(String criteriaId, String cname) {
		CriteriaId = criteriaId;
		CName = cname;
	}

	public String getCriteriaId() {
		return CriteriaId;
	}

	public String getMarketId() {
		return MarketId;
	}

	public String getCName() {
		return CName;
	}

	/**
	 * Nếu không tồn tại các danh sách con thì method trả về null
	 * 
	 * @return
	 */
	public List<FavWatchListItem> getSubs() {
		return Subs;
	}

	public String getFavMem() {
		return FavMem;
	}

	public void setCriteriaId(String criteriaId) {
		if (criteriaId == null) {
			criteriaId = StringConst.EMPTY;
		}
		CriteriaId = criteriaId;
	}

	public void setMarketId(String marketId) {
		if (marketId == null) {
			marketId = StringConst.EMPTY;
		}
		MarketId = marketId;
	}

	public void setCName(String cName) {
		if (cName == null) {
			cName = StringConst.EMPTY;
		}
		CName = cName;
	}

	public void setFavMem(String favMem) {
		if (favMem == null) {
			favMem = StringConst.EMPTY;
		}
		FavMem = favMem;
	}

}
