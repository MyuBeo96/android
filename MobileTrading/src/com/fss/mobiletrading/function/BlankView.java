package com.fss.mobiletrading.function;

import java.util.HashMap;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BlankView extends AbstractFragment {

	public static BlankView newInstance(MainActivity mActivity) {
		// TODO Auto-generated method stub
		BlankView self = new BlankView();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.BlankView);
		return self;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		return paramLayoutInflater.inflate(R.layout.blankview, paramViewGroup,
				false);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub

	}

}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.BlankView JD-Core Version: 0.6.0
 */