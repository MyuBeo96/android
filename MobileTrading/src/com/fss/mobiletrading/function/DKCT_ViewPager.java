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

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.TabsPagerAdapter;
import com.fss.mobiletrading.function.cashtransfer.BankCashTransferRegister;
import com.fss.mobiletrading.function.cashtransfer.InternalCashTransferRegister;
import com.fss.mobiletrading.function.cashtransfer.SCCashTransferRegister;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;

public class DKCT_ViewPager extends AbstractFragment {

	static boolean isInitialise = false;
	public final static int DKCT_NOIBO = 2;
	public final static int DKCT_MSB = 1;
	public final static int DKCT_RANGOAI = 0;

	private int initialiseIndex;
	private TabsPagerAdapter mAdapter;

	private ViewPager viewPager;

	public DKCT_ViewPager() {
	}

	public static DKCT_ViewPager newInstance(MainActivity mActivity,
			int position) {
		DKCT_ViewPager self = new DKCT_ViewPager();
		self.initialiseIndex = position;
		self.mainActivity = mActivity;
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.dkct_viewpager, viewGroup, false);
		viewPager = ((ViewPager) view.findViewById(R.id.dkct_viewpager));
		initialiseViewPager(initialiseIndex);
		return view;
	}

	private void initialiseViewPager(int index) {
		isInitialise = true;
		final ArrayList<AbstractFragment> listFragment = new ArrayList<AbstractFragment>();
		listFragment.add(BankCashTransferRegister.newInstance(mainActivity));
		listFragment.add(SCCashTransferRegister.newInstance(mainActivity));
		listFragment
				.add(InternalCashTransferRegister.newInstance(mainActivity));

		mAdapter = new TabsPagerAdapter(getChildFragmentManager(), listFragment);

		viewPager.setAdapter(mAdapter);
		viewPager.setCurrentItem(index, true);
		mainActivity.setTitleActionBar(listFragment.get(index).TITLE);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				mainActivity.setTitleActionBar(listFragment.get(arg0).TITLE);
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

	public void onResume() {
		super.onResume();
		// try {
		// // initialiseViewPager(initialiseIndex);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub

	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.DKCT_ViewPager JD-Core Version: 0.6.0
 */