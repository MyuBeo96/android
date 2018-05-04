package com.fss.mobiletrading.function.coporateactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.MyActionBar.Action;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;
import com.msbuat.mobiletrading.design.LabelContentLayout;

public class CoporateActionsDetail extends AbstractFragment {
	LabelContentLayout tv_detail_afacctno;
	LabelContentLayout tv_detail_symbol;
	LabelContentLayout tv_detail_eventtype;
	LabelContentLayout tv_detail_closingdate;
	LabelContentLayout tv_detail_trade;
	LabelContentLayout tv_detail_rate;
	LabelContentLayout tv_detail_receivingQtty;
	LabelContentLayout tv_detail_receivingCash;
	LabelContentLayout tv_detail_actiondate;
	LabelContentLayout tv_detail_status;
	CoporateActionItem coporateActionItem;

	public static CoporateActionsDetail newInstance(MainActivity mActivity) {
		CoporateActionsDetail self = new CoporateActionsDetail();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.ChiTietThongTinQuyen);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.coporateaction_detail, container,
				false);
		tv_detail_afacctno = (LabelContentLayout) view
				.findViewById(R.id.text_coporateaction_details_afacctno);
		tv_detail_symbol = (LabelContentLayout) view
				.findViewById(R.id.text_coporateaction_details_symbol);
		tv_detail_eventtype = (LabelContentLayout) view
				.findViewById(R.id.text_coporateaction_details_eventtype);
		tv_detail_closingdate = (LabelContentLayout) view
				.findViewById(R.id.text_coporateaction_details_closingdate);
		tv_detail_trade = (LabelContentLayout) view
				.findViewById(R.id.text_coporateaction_details_trade);
		tv_detail_rate = (LabelContentLayout) view
				.findViewById(R.id.text_coporateaction_details_rate);
		tv_detail_receivingQtty = (LabelContentLayout) view
				.findViewById(R.id.text_coporateaction_details_receivingQtty);
		tv_detail_receivingCash = (LabelContentLayout) view
				.findViewById(R.id.text_coporateaction_details_receivingCash);
		tv_detail_actiondate = (LabelContentLayout) view
				.findViewById(R.id.text_coporateaction_details_actiondate);
		tv_detail_status = (LabelContentLayout) view
				.findViewById(R.id.text_coporateaction_details_status);
		init();
		initListener();
		return view;
	}

	private void initListener() {

	}

	private void init() {

	}

	@Override
	public void onResume() {
		super.onResume();
		showCoporateActionDetails(coporateActionItem);
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoAction();
	}

	@Override
	protected void setBackLogoAction() {
		if (!DeviceProperties.isTablet) {
			mainActivity.setHomeLogoAction(new Action() {

				@Override
				public void performAction(View view) {
					if (mainActivity != null) {
						mainActivity.backNavigateFragment();
					}
				}

				@Override
				public int getDrawable() {
					return R.drawable.ic_back;
				}

				@Override
				public String getText() {
					return null;
				}
			});
		}
	}

	@Override
	protected void process(String key, ResultObj rObj) {

	}

	private void showCoporateActionDetails(CoporateActionItem item) {
		tv_detail_afacctno.setText(StaticObjectManager.acctnoItem.ACCTNO);
		if (item != null) {
			tv_detail_symbol.setText(item.getSYMBOL());
			tv_detail_eventtype.setText(item.getCATYPE());
			tv_detail_closingdate.setText(item.getREPORTDATE());
			tv_detail_trade.setText(Common.formatAmount(item.getSLCKSH()));
			tv_detail_rate.setText(item.getTYLE());
			tv_detail_receivingQtty.setText(Common.formatAmount(item
					.getSLCKCV()));
			tv_detail_receivingCash
					.setText(Common.formatAmount(item.getSTCV()));
			tv_detail_actiondate.setText(item.getACTIONDATE());
			tv_detail_status.setText(item.getSTATUS());
		}
	}

	@Override
	public void getArgument(Object obj) {
		super.getArgument(obj);
		if (obj instanceof CoporateActionItem) {
			coporateActionItem = (CoporateActionItem) obj;
		}
	}

}
