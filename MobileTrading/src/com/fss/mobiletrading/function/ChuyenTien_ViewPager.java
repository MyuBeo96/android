package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.cashtransfer.BankCashTransfer;
import com.fss.mobiletrading.function.cashtransfer.InternalCashTransfer;
import com.fss.mobiletrading.function.cashtransfer.SCCashTransfer;
import com.fss.mobiletrading.object.BankAccList;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;

public class ChuyenTien_ViewPager extends AbstractFragment {

	public final static int CT_RANGOAI = 0;
	public final static int CT_MSB = 1;
	public final static int CT_NOIBO = 2;
	final String GETBANKACCLIST = "GetBankAccListService#GETBANKACCLIST";
	private int initialiseIndex;
	private TabsPagerAdapter mAdapter;
	private ViewPager viewPager;

	public ChuyenTien_ViewPager() {
	}

	public static ChuyenTien_ViewPager newInstance(MainActivity mActivity,
			int position) {
		ChuyenTien_ViewPager self = new ChuyenTien_ViewPager();
		self.initialiseIndex = position;
		self.mainActivity = mActivity;
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.chuyentien_viewpager, viewGroup,
				false);
		viewPager = ((ViewPager) view.findViewById(R.id.chuyentien_viewpager));
		return view;
	}

	private void initialiseViewPager(int index) {
		final ArrayList<AbstractFragment> listFragments = new ArrayList<AbstractFragment>();
		listFragments.add(BankCashTransfer.newInstance(mainActivity));
		listFragments.add(SCCashTransfer.newInstance(mainActivity));
		listFragments.add(InternalCashTransfer.newInstance(mainActivity));

		mAdapter = new TabsPagerAdapter(getChildFragmentManager(),
				listFragments);
		viewPager.setAdapter(mAdapter);
		viewPager.setCurrentItem(index, true);
		mainActivity.setTitleActionBar(listFragments.get(index).TITLE);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

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

	public void onResume() {
		super.onResume();
		CallGetBankAccList(StaticObjectManager.acctnoItem.ACCTNO);
	}

	private void CallGetBankAccList(String paramString) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_getBankAccList));
		}
		{
			list_key.add("pv_afacctno");
			list_value.add(paramString);
		}
		StaticObjectManager.connectServer.callHttpPostService(GETBANKACCLIST,
				this, list_key, list_value);

	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		switch (key) {
		case GETBANKACCLIST:
			if (rObj.obj != null) {
				StaticObjectManager.listBankAcc = (BankAccList) rObj.obj;
				initialiseViewPager(initialiseIndex);
			}
			break;

		default:
			break;
		}
	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.ChuyenTien_ViewPager JD-Core Version: 0.6.0
 */