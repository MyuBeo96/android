package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.TabsPagerAdapter;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;

public class TraCuu_ViewPager extends AbstractFragment {
	public static final int LS_CHUYEN_TIEN = 3;
	public static final int LS_DKQM = 4;
	public static final int LS_LENH = 2;
	public static final int LS_UNG_TRUOC = 5;
	public static final int SAO_KE_CK = 1;
	public static final int SAO_KE_TIEN = 0;
	private int initialiseIndex;
	private TabsPagerAdapter mAdapter;
	private ViewPager viewPager;

	public TraCuu_ViewPager() {
	}

	public static TraCuu_ViewPager newInstance(MainActivity mActivity,
			int position) {
		TraCuu_ViewPager self = new TraCuu_ViewPager();
		self.initialiseIndex = position;
		self.mainActivity = mActivity;
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.tracuu_viewpager,
				paramViewGroup, false);
		this.viewPager = ((ViewPager) localView
				.findViewById(R.id.tracuu_viewpager));
		initialiseViewPager(this.initialiseIndex);
		return localView;
	}

	public void onResume() {
		super.onResume();

	}

	private void initialiseViewPager(int index) {
		final ArrayList<AbstractFragment> listFragments = new ArrayList<AbstractFragment>();
		listFragments.add(CashStatement.newInstance(mainActivity));
		listFragments.add(StockStatement.newInstance(mainActivity));
		// listFragments.add(OrderHistory.newInstance(mainActivity));
		listFragments.add(CashTransferHistory.newInstance(mainActivity));
		listFragments.add(RightOffRegisterHistory.newInstance(mainActivity));
		listFragments.add(AdvanceHistory.newInstance(mainActivity));

		this.mAdapter = new TabsPagerAdapter(getChildFragmentManager(),
				listFragments);
		this.viewPager.setAdapter(this.mAdapter);
		this.viewPager.setCurrentItem(index, true);
		mainActivity.setTitleActionBar(listFragments.get(index).TITLE);
		this.viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				mainActivity.setTitleActionBar(listFragments.get(arg0).TITLE);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub

	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.TraCuu_ViewPager JD-Core Version: 0.6.0
 */