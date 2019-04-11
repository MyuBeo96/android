package com.fss.mobiletrading.function.placeorder;

import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.R;

public class GTCPlaceOrder extends PlaceOrder {

	public static GTCPlaceOrder newInstance(MainActivity mActivity) {

		GTCPlaceOrder self = new GTCPlaceOrder();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.ConditionalTrade);
		return self;
	}

	/**
	 * khởi tạo với thông tin lệnh
	 * 
	 * @param orderSetParams
	 * @return
	 */
	public static GTCPlaceOrder newInstance(OrderSetParams orderSetParams) {
		GTCPlaceOrder self = new GTCPlaceOrder();
		self.setOrderInit(orderSetParams);
		self.mainActivity = (MainActivity) self.getActivity();
		self.TITLE = self.mainActivity
				.getStringResource(R.string.ConditionalTrade);
		return self;
	}

	@Override
	protected void setDefaultPlaceOrderType() {
		setPlaceOrderType(GTCORDER_TYPE);
	}
}
