package com.fss.mobiletrading.connector;

import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;

public class CheckSessionFragment extends AbstractFragment {

	public static final String CHECKSESSION = "CheckSessionService#CHECKSESSION";

	public static CheckSessionFragment newIntances(MainActivity m) {
		CheckSessionFragment self = new CheckSessionFragment();
		self.mainActivity = m;
		return self;
	}

	@Override
	protected void process(String key, ResultObj rObj) {

	}

}
