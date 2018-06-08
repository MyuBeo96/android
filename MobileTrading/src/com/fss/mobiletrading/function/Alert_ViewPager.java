package com.fss.mobiletrading.function;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.TabsPagerAdapter;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;

public class Alert_ViewPager extends AbstractFragment {
	public static int DOT_BIEN_CO_BAN = 4;
	public static int DOT_BIEN_GIA = 1;
	public static int DOT_BIEN_GIA_KL = 3;
	public static int DOT_BIEN_KL = 2;
	int initialiseIndex;
	private TabsPagerAdapter mAdapter;
	private ViewPager viewPager;

	public Alert_ViewPager() {
	}

	public static Alert_ViewPager newInstance(MainActivity mActivity,
			int position) {
		Alert_ViewPager self = new Alert_ViewPager();
		self.initialiseIndex = position;
		self.mainActivity = mActivity;
		return self;
	}

	private void initialiseViewPager(int index) {
		ArrayList<AbstractFragment> listFragments = new ArrayList<AbstractFragment>();
		listFragments.add(PriceAlert.newInstance(mainActivity));
		listFragments.add(QuantityAlert.newInstance(mainActivity));
		listFragments.add(QuantityPriceAlert.newInstance(mainActivity));
		listFragments.add(BasicAlert.newInstance(mainActivity));
		listFragments.add(MatchAlertList.newInstance(mainActivity));
		this.mAdapter = new TabsPagerAdapter(getChildFragmentManager(),
				listFragments);
		this.viewPager.setAdapter(this.mAdapter);
		this.viewPager.setCurrentItem(index, true);
		mainActivity.setTitleActionBar(listFragments.get(index).TITLE);
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.alert_viewpager,
				paramViewGroup, false);
		this.viewPager = ((ViewPager) localView
				.findViewById(R.id.alert_viewpager));
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
 * com.fss.fragment.Alert_ViewPager JD-Core Version: 0.6.0
 */