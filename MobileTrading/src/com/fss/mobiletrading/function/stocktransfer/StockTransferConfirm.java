package com.fss.mobiletrading.function.stocktransfer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.MyActionBar.Action;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;
import com.tcscuat.mobiletrading.design.LabelContentLayout;

import java.util.ArrayList;
import java.util.List;

public class StockTransferConfirm extends AbstractFragment {

	static final String SUBMITSTOCKTRANSFER = "SuccessService#2";

	LabelContentLayout tv_confirm_HienCo;
	LabelContentLayout tv_confirm_MaCK;
	LabelContentLayout tv_confirm_SoLuong;
	LabelContentLayout tv_confirm_TKgui;
	LabelContentLayout tv_confirm_TKnhan;
	Button btn_confirm_accept;
	StockTransferItem stockTransferItem;

	public static StockTransferConfirm newInstance(MainActivity mActivity) {

		StockTransferConfirm self = new StockTransferConfirm();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.StockTransferConfirm);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.stocktransfer_confirm, viewGroup,
				false);
		initView(view);
		initData();
		initListener();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		int width = getDimenResource(R.dimen.t_dialogstocktransfer_width);
		if (getDialog() != null) {
			getDialog().getWindow().setLayout(width, LayoutParams.WRAP_CONTENT);
		}
	}

	private void initListener() {
		btn_confirm_accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CallSubmitStockTransfer();
			}
		});
	}

	private void initData() {

	}

	private void initView(View view) {
		tv_confirm_TKgui = ((LabelContentLayout) view
				.findViewById(R.id.text_ChuyenKhoanCKInfo_TKChuyen));
		tv_confirm_TKnhan = ((LabelContentLayout) view
				.findViewById(R.id.text_ChuyenKhoanCKInfo_TKNhan));
		tv_confirm_MaCK = ((LabelContentLayout) view
				.findViewById(R.id.text_ChuyenKhoanCKInfo_MaCK));
		tv_confirm_HienCo = ((LabelContentLayout) view
				.findViewById(R.id.text_ChuyenKhoanCKInfo_HienCo));
		tv_confirm_SoLuong = ((LabelContentLayout) view
				.findViewById(R.id.text_ChuyenKhoanCKInfo_SoLuong));
		btn_confirm_accept = (Button) view
				.findViewById(R.id.btn_ChuyenKhoanCKInfo_ChapNhan);

	}

	@Override
	public void onResume() {
		super.onResume();
		showDialogCheck();
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

	private void showDialogCheck() {
		if (stockTransferItem != null) {
			tv_confirm_TKgui.setText(stockTransferItem.senderAfacctno
					.toString());
			tv_confirm_TKnhan.setText(stockTransferItem.beneficiaryAfacctno
					.toString());
			tv_confirm_MaCK.setText(stockTransferItem.symbol);
			tv_confirm_HienCo.setText(stockTransferItem.available);
			tv_confirm_SoLuong.setText(stockTransferItem.amount);
		}
	}

	private void CallSubmitStockTransfer() {
		if (stockTransferItem == null) {
			return;
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_SubmitStockTransfer));
		}
		{
			list_key.add("AfacctnoSend");
			list_value.add(stockTransferItem.senderAfacctno.ACCTNO);
		}
		{
			list_key.add("AfacctnoReceive");
			list_value.add(stockTransferItem.beneficiaryAfacctno.getValue());
		}
		{
			list_key.add("StockAvailable");
			list_value.add(stockTransferItem.available);
		}
		{
			list_key.add("Symbol");
			list_value.add(stockTransferItem.symbol);
		}
		{
			list_key.add("QTTY");
			list_value.add(stockTransferItem.amount);
		}
		{
			list_key.add("BlockQTTY");
			list_value.add("0");
		}
		StaticObjectManager.connectServer.callHttpPostService(
				SUBMITSTOCKTRANSFER, this, list_key, list_value);
		mainActivity.loadingScreen(true);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		switch (key) {
		case SUBMITSTOCKTRANSFER:
			final StockTransfer stockTransfer = (StockTransfer) mainActivity
					.getFragmentByName(StockTransfer.class.getName());
			stockTransfer.refesh();
			showDialogMessage(getStringResource(R.string.thong_bao),
					getStringResource(R.string.Giaodichthanhcong),
					new SimpleAction() {

						@Override
						public void performAction(Object obj) {
							mainActivity.backNavigateFragment();
							onDismiss(getDialog());
						}
					});
			break;

		default:
			break;
		}
	}

	@Override
	protected void isReceivedResponse(String key) {
		super.isReceivedResponse(key);
		switch (key) {
		case SUBMITSTOCKTRANSFER:
			mainActivity.loadingScreen(false);
			break;

		default:
			break;
		}
	}

	@Override
	public void getArgument(Object obj) {
		super.getArgument(obj);
		if (obj instanceof StockTransferItem) {
			stockTransferItem = (StockTransferItem) obj;
		}
	}
}
