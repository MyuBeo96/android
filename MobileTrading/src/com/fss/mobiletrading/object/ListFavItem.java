package com.fss.mobiletrading.object;

import com.fss.mobiletrading.consts.StringConst;

public class ListFavItem {
	public String favGroupId;
	public String favGroupName;
	public String favGroupMem;

	public ListFavItem(String favGroupId, String favGroupName,
			String favGroupMem) {
		super();
		this.favGroupId = favGroupId;
		this.favGroupName = favGroupName;
		this.favGroupMem = favGroupMem;
	}

	public ListFavItem(String favGroupId, String favGroupName) {
		super();
		this.favGroupId = favGroupId;
		this.favGroupName = favGroupName;
		this.favGroupMem = StringConst.EMPTY;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return favGroupName;
	}
}
