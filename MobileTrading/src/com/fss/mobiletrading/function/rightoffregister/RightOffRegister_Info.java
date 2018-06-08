package com.fss.mobiletrading.function.rightoffregister;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;

public class RightOffRegister_Info extends AbstractFragment {

	Button btn_close;
	RightOffRegisterItem rightOffRegisterItem;
	private TextView tv_ten_ck;
	private TextView tv_loai_ck;
	private TextView tv_ma_ck;
	private TextView tv_date_dk_cc;
	private TextView tv_menhgia_ck;
	private TextView tv_tlpb_ck;
	private TextView tv_giadatmua_ck;
	private TextView tv_timechuyennhuong;
	private TextView tv_timenoptien;
	private TextView tv_statusDesc;

	public static RightOffRegister_Info newInstance(MainActivity mActivity) {
		RightOffRegister_Info self = new RightOffRegister_Info();
		self.mainActivity = mActivity;
		self.TITLE = mActivity
				.getStringResource(R.string.RightOffRegister_Info);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.rightoffregister_info, viewGroup,
				false);
		tv_ten_ck = (TextView) view.findViewById(R.id.tv_ten_ck);
		tv_loai_ck = (TextView) view.findViewById(R.id.tv_loai_ck);
		tv_ma_ck = (TextView) view.findViewById(R.id.tv_ma_ck);
		tv_date_dk_cc = (TextView) view.findViewById(R.id.tv_date_dk_cc);
		tv_menhgia_ck = (TextView) view.findViewById(R.id.tv_menhgia_ck);
		tv_tlpb_ck = (TextView) view.findViewById(R.id.tv_tlpb_ck);
		tv_giadatmua_ck = (TextView) view.findViewById(R.id.tv_giadatmua_ck);
		tv_timechuyennhuong = (TextView) view
				.findViewById(R.id.tv_timechuyennhuong);
		tv_timenoptien = (TextView) view.findViewById(R.id.tv_timenoptien);
		btn_close = ((Button) view.findViewById(R.id.btn_alertdialog_negative));
		tv_statusDesc = (TextView) view.findViewById(R.id.text_statusdesc);
		initListener();
		return view;
	}

	private void initListener() {
		btn_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RightOffRegister_Info.this.getDialog().dismiss();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		ShowDialogChiTiet();
	}

	private void ShowDialogChiTiet() {
		if (rightOffRegisterItem != null) {
			tv_ten_ck.setText(rightOffRegisterItem.Symbol);
			tv_loai_ck.setText(rightOffRegisterItem.SecType);
			tv_ma_ck.setText(rightOffRegisterItem.Symbol);
			tv_date_dk_cc.setText(rightOffRegisterItem.ReportDate);
			tv_menhgia_ck.setText(Common
					.formatAmount(rightOffRegisterItem.ParValue));
			tv_tlpb_ck.setText(rightOffRegisterItem.RightOffRate);
			tv_giadatmua_ck.setText(Common
					.formatAmount(rightOffRegisterItem.ExPrice));
			tv_timechuyennhuong.setText(rightOffRegisterItem.FromDateTransfer
					+ " - " + rightOffRegisterItem.ToDateTransfer);
			tv_timenoptien.setText(rightOffRegisterItem.BeginDate + " - "
					+ rightOffRegisterItem.DueDate);
			tv_statusDesc.setText(rightOffRegisterItem.StatusDesc);
		}
	}

	@Override
	public void getArgument(Object obj) {
		super.getArgument(obj);
		if (obj instanceof RightOffRegisterItem) {
			rightOffRegisterItem = (RightOffRegisterItem) obj;
		}
	}

	@Override
	protected void process(String key, ResultObj rObj) {
	}

}
