package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.TabsPagerAdapter;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;

public class BoLocCP_ViewPager extends AbstractFragment {
	public static int DOT_BIEN_CO_BAN;
	public static int DOT_BIEN_GIA = 0;
	public static int DOT_BIEN_GIA_KL;
	public static int DOT_BIEN_KL = 1;
	int initialiseIndex;
	private TabsPagerAdapter mAdapter;
	private ViewPager viewPager;

	static {
		DOT_BIEN_GIA_KL = 2;
		DOT_BIEN_CO_BAN = 3;
	}

	public static BoLocCP_ViewPager newInstance(MainActivity mActivity,
			int position) {
		BoLocCP_ViewPager self = new BoLocCP_ViewPager();
		self.initialiseIndex = position;
		self.mainActivity = mActivity;
		return self;
	}

	private void initialiseViewPager(int index) {
		ArrayList<AbstractFragment> listFragments = new ArrayList<AbstractFragment>();
		listFragments.add(PriceFilter.newInstance(mainActivity));
		listFragments.add(QuantityFilter.newInstance(mainActivity));
		listFragments.add(QuantityPriceFilter.newInstance(mainActivity));
		listFragments.add(BasicFilter.newInstance(mainActivity));

		this.mAdapter = new TabsPagerAdapter(getChildFragmentManager(),
				listFragments);
		this.viewPager.setAdapter(this.mAdapter);
		this.viewPager.setCurrentItem(index, true);
		mainActivity.setTitleActionBar(listFragments.get(index).TITLE);
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(
				R.layout.boloccp_viewpager, paramViewGroup, false);
		this.viewPager = ((ViewPager) localView
				.findViewById(R.id.boloccp_viewpager));
		initialiseViewPager(this.initialiseIndex);
		return localView;
	}

	public void onResume() {
		super.onResume();
		try {
			initialiseViewPager(this.initialiseIndex);
			return;
		} catch (Exception localException) {
			while (true)
				localException.printStackTrace();
		}
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		// tạo actionbar tương ứng
		setHomeLogoAction();
	}

	public void setCurrentPage(int paramInt) {
		this.viewPager.setCurrentItem(paramInt, true);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub

	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.BoLocCP_ViewPager JD-Core Version: 0.6.0
 */