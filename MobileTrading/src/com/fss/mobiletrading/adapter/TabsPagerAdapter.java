package com.fss.mobiletrading.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fscuat.mobiletrading.AbstractFragment;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
	FragmentManager fm;
	private List<AbstractFragment> fragmentList;

	public TabsPagerAdapter(FragmentManager paramFragmentManager,
			List<AbstractFragment> paramList) {
		super(paramFragmentManager);
		this.fm = paramFragmentManager;
		this.fragmentList = paramList;
	}

	public int getCount() {
		return this.fragmentList.size();
	}

	public Fragment getItem(int paramInt) {
		return (Fragment) this.fragmentList.get(paramInt);
	}

	public int getItemPosition(Object paramObject) {
		return super.getItemPosition(paramObject);
	}
}
