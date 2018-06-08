package com.fss.mobiletrading.function.guaranteelist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.guaranteelist.GuaranteeItem;
import com.fss.mobiletrading.function.guaranteelist.GuaranteeListService;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.AcctnoItemChild;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MSTradeAppConfig;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.design.LabelContentLayout;
import com.fscuat.mobiletrading.design.MyContextMenu.OnItemSelectedListener;
import com.fscuat.mobiletrading.design.MySpinner;

public class GuaranteeList extends AbstractFragment {
	public static final String GETGUARANTEELIST = "GetGuaranteeListService#GETGUARANTEELIST";
	public static final String FINDCUSTODYCD = "FindCustodyCdServiceChild#FINDCUSTODYCD";
	public static final String FINDCUSTODYCDANDGETGUARANTEELIST = "FindCustodyCdService#FINDCUSTODYCDANDGETGUARANTEELIST";
	LinearLayout ll_line1;
	LinearLayout ll_line2;
	LinearLayout ll_line3;
	EditText accountno;
	MySpinner subaccount;
	LabelContentLayout accountname;
	LabelContentLayout assettotal;
	LabelContentLayout debttotal;
	LabelContentLayout nobaolanh;
	LabelContentLayout baolanhdacaptrongngay;
	LabelContentLayout buyvalueperday;
	LabelContentLayout totalmoney;
	LabelContentLayout rttcono;
	LabelContentLayout rttbono;
	Button btn_search;
	Button btn_allcustomers;
	GuaranteeItem guaranteeItem;
	AcctnoItemChild acctnoItem;
	List<AcctnoItemChild> listTieuKhoan;
	public static int selectedPosition = 0;
	boolean mBoolean = false;

	// int temp_selectedPosition = 0;

	public static GuaranteeList newInstance(MainActivity mActivity) {
		GuaranteeList self = new GuaranteeList();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.Danhsachkhachhang);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.guaranteelist, container, false);
		initView(view);
		initData();
		initListener();
		Common.setupUI(view, mainActivity);
		return view;
	}

	public void initView(View view) {
		accountno = (EditText) view.findViewById(R.id.qltk_dskh_accountno);
		subaccount = (MySpinner) view
				.findViewById(R.id.spn_qltk_dskh_subaccount);
		accountname = (LabelContentLayout) view
				.findViewById(R.id.qltk_dskh_customername);
		assettotal = (LabelContentLayout) view
				.findViewById(R.id.qltk_dskh_assettotal);
		debttotal = (LabelContentLayout) view
				.findViewById(R.id.qltk_dskh_debttotal);
		nobaolanh = (LabelContentLayout) view
				.findViewById(R.id.qltk_dskh_nobaolanh);
		baolanhdacaptrongngay = (LabelContentLayout) view
				.findViewById(R.id.qltk_dskh_baolanhdacaptrongngay);
		buyvalueperday = (LabelContentLayout) view
				.findViewById(R.id.qltk_dskh_buyvalueperday);
		totalmoney = (LabelContentLayout) view
				.findViewById(R.id.qltk_dskh_totalmoney);
		rttcono = (LabelContentLayout) view.findViewById(R.id.qltk_dskh_rtt);
		rttbono = (LabelContentLayout) view
				.findViewById(R.id.qltk_dskh_rttbono);
		ll_line1 = (LinearLayout) view.findViewById(R.id.ll_qltk_dskh_line1);
		ll_line2 = (LinearLayout) view.findViewById(R.id.ll_qltk_dskh_line2);
		ll_line3 = (LinearLayout) view.findViewById(R.id.ll_qltk_dskh_line3);
		btn_search = (Button) view.findViewById(R.id.btn_qltk_dskh_search);
		btn_allcustomers = (Button) view
				.findViewById(R.id.btn_qltk_dskh_allcustomers);
	}

	public void initData() {
		if (listTieuKhoan == null) {
			listTieuKhoan = new ArrayList<AcctnoItemChild>();
			subaccount.setChoises(listTieuKhoan);
		}
	}

	public void initListener() {
		accountno.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					if (accountno.getText().length() == 0) {
						listTieuKhoan.clear();
						subaccount.setChoises(listTieuKhoan);
						Clear();
					} else {
						mBoolean = false;
						CallFindCustodyCd(FINDCUSTODYCD);
					}
				}
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// check chua nhap so luu ky => show cau thong bao chua nhap so
				// luu ky
				if (accountno.getText().toString().equals(
						MSTradeAppConfig.USERNAME_DEFAULT)) {
					showDialogMessage(
							"",
							getResources().getString(
									R.string.error_no_enter_account));
					return;
				}
				if (accountno.getText().length() == 0) {
					showDialogMessage(
							"",
							getResources().getString(
									R.string.error_no_enter_account));
					return;
				}
				// Log.e("Log:", accountno.getEditContent().hasFocus() + "");
				// if (accountno.getEditContent().hasFocus()) {
				// CallFindCustodyCd(FINDCUSTODYCDANDGETGUARANTEELIST);
				// } else {
				// da click ra ngoai man hinh
				if (mBoolean) {
					if (subaccount.getContextMenu().getSelectedItem() == null) {
						showDialogMessage(
								"",
								getResources().getString(
										R.string.error_no_enter_subaccount));
					} else {

						String afacctno = ((AcctnoItem) subaccount
								.getContextMenu().getSelectedItem()).ACCTNO;
						callGetGuaranteeListService(afacctno);
					}
				}
				// }

			}
		});
		subaccount.getContextMenu().setOnItemSelectedListener(
				new OnItemSelectedListener() {

					@Override
					public void onItemSelect(Object obj, int position) {
						Clear();
					}
				});
	}

	public void callGetGuaranteeListService(String pv_afacctno) {
		GuaranteeListService.getGuaranteeList(pv_afacctno, this,
				GETGUARANTEELIST, StaticObjectManager.connectServer);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ClearField();
	}

	private void ClearField() {
		accountno.setText(MSTradeAppConfig.USERNAME_DEFAULT);
		listTieuKhoan.clear();
		subaccount.setChoises(listTieuKhoan);
	}

	private void Clear() {
		accountname.setText(StringConst.EMPTY);
		assettotal.setText(StringConst.EMPTY);
		debttotal.setText(StringConst.EMPTY);
		nobaolanh.setText(StringConst.EMPTY);
		baolanhdacaptrongngay.setText(StringConst.EMPTY);
		buyvalueperday.setText(StringConst.EMPTY);
		totalmoney.setText(StringConst.EMPTY);
		rttbono.setText(StringConst.EMPTY);
		rttcono.setText(StringConst.EMPTY);
	}

	@Override
	public void notifyChangeAcctNo() {
		// TODO Auto-generated method stub
		super.notifyChangeAcctNo();
	}

	public void showGuaranteeList(GuaranteeItem guaranteeitem) {
		if (guaranteeitem != null) {
			accountname.setText(guaranteeitem.fullname);
			assettotal.setText(guaranteeitem.realass);
			debttotal.setText(guaranteeitem.od_amt);
			nobaolanh.setText(guaranteeitem.t0amt);
			baolanhdacaptrongngay.setText(guaranteeitem.t0_in_day);
			buyvalueperday.setText(guaranteeitem.totalbuyqtty);
			totalmoney.setText(guaranteeitem.balance);
			rttbono.setText(guaranteeitem.marginrat2);
			rttcono.setText(guaranteeitem.marginrate);
		}
	}

	@Override
	protected void processErrorOther(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		super.processErrorOther(key, rObj);
		switch (key) {
		case FINDCUSTODYCD:
			Clear();
			ClearField();
			break;
		default:
			break;
		}
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub
		switch (key) {
		case GETGUARANTEELIST:
			List<GuaranteeItem> listGuaranteeItem = ((List<GuaranteeItem>) rObj.obj);
			if (listGuaranteeItem == null || listGuaranteeItem.size() == 0) {
				// show câu thông báo không có dữ liệu để hiển thị
				showDialogMessage(
						"",
						getResources().getString(
								R.string.error_no_data_to_display));
				// clear màn hình
				Clear();
			} else {
				guaranteeItem = listGuaranteeItem.get(0);
				showGuaranteeList(guaranteeItem);
			}

			break;
		case FINDCUSTODYCD:
			if (rObj.obj != null) {
				listTieuKhoan.clear();
				List<AcctnoItemChild> list = ((List<AcctnoItemChild>) rObj.obj);
				for (int i = 0; i < list.size(); i++) {
					listTieuKhoan.add(list.get(i));
				}
				subaccount.setChoises(listTieuKhoan);
				mBoolean = true;
			}
			break;
		case FINDCUSTODYCDANDGETGUARANTEELIST:
			if (rObj.obj != null) {
				listTieuKhoan.clear();
				List<AcctnoItemChild> list = ((List<AcctnoItemChild>) rObj.obj);
				for (int i = 0; i < list.size(); i++) {
					listTieuKhoan.add(list.get(i));
				}
				subaccount.setChoises(listTieuKhoan);
				if (mBoolean) {
					// tu dong goi ham getGuaranteeList
					if (subaccount.getContextMenu().getSelectedItem() == null) {
						showDialogMessage(
								"",
								getResources().getString(
										R.string.error_no_enter_subaccount));
					} else {
						String afacctno = ((AcctnoItem) subaccount
								.getContextMenu().getSelectedItem()).ACCTNO;
						callGetGuaranteeListService(afacctno);
					}
				}
			}
			break;
		default:
			break;
		}
	}

	private void CallFindCustodyCd(String key) {
		if (!StaticObjectManager.loginInfo.IsBroker) {
			return;
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_FindCustodyCd));
		}
		{
			list_key.add("CustodyCd");
			list_value.add(accountno.getText().toString());
		}

		StaticObjectManager.connectServer.callHttpPostService(key, this,
				list_key, list_value);
	}
}
