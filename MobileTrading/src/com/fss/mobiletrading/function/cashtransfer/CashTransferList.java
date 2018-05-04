package com.fss.mobiletrading.function.cashtransfer;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.BankAccItem;
import com.fss.mobiletrading.object.BankAccList;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;
import com.msbuat.mobiletrading.design.LabelContentLayout;
import com.msbuat.mobiletrading.design.MyContextMenu.OnItemSelectedListener;
import com.msbuat.mobiletrading.design.MySpinner;

import java.util.ArrayList;
import java.util.List;

public class CashTransferList extends AbstractFragment {

	final String GETBANKACCLIST = "GetBankAccListService#GETBANKACCLIST";
	final String REMOVETRANSFERREGISTER = "SuccessService#REMOVETRANSFERREGISTER";

	// các hằng số tương ứng với vị trí của kiểu chuyển tiền trong spinner
	int INTERNAL_TRANSFER_TYPE = 0;
	int CASH_TRANSFER_TYPE = 1;

	int STATE_TRANSFER = INTERNAL_TRANSFER_TYPE;

	ListView lv_BankAcc;
	List<BankAccItem> listBankAcc = new ArrayList<BankAccItem>();
	TransferListAdapter transferListAdapter;
	BankAccItem longClickItem;
	MySpinner spn_cashtransferlist;
	List<String> list_spinner;

	Dialog dialog_detail;
	LabelContentLayout tv_detail_beneficiaryname;
	LabelContentLayout tv_detail_bankaccount;
	LabelContentLayout tv_detail_bank;
	LabelContentLayout tv_detail_branch;
	LabelContentLayout tv_detail_city;
	LabelContentLayout tv_detail_custodycd;
	LabelContentLayout tv_detail_afacctno;
	LinearLayout header_internal;
	LinearLayout header_bank;

	/**
	 * Trả về 1 trong 2 giá trị INTERNAL_TRANSFER_TYPE tương ứng với danh sách
	 * chuyển tiền nội bộ,CASH_TRANSFER_TYPE tương ứng với danh sách chuyển tiền
	 * ra ngoài. Mặc định là chuyển tiền nội bộ
	 * 
	 * @return
	 */
	public int getStateTransfer() {
		return STATE_TRANSFER;
	}

	public static CashTransferList newInstance(MainActivity mActivity) {

		CashTransferList self = new CashTransferList();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.CashTransferList);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.cashtransferlist, viewGroup,
				false);
		initView(view);
		initData();
		initListener();
		registerForContextMenu(lv_BankAcc);
		return view;
	}

	private void initView(View view) {
		if (DeviceProperties.isTablet) {
			header_bank = (LinearLayout) view.findViewById(R.id.header_bank);
			header_internal = (LinearLayout) view
					.findViewById(R.id.header_internal);
			lv_BankAcc = (ListView) view.findViewById(R.id.listview_BankAcc);
			spn_cashtransferlist = (MySpinner) view
					.findViewById(R.id.spn_cashtransferlist);
		} else {
			lv_BankAcc = (ListView) view.findViewById(R.id.listview_BankAcc);
			spn_cashtransferlist = (MySpinner) view
					.findViewById(R.id.spn_cashtransferlist);
		}
	}

	private void initData() {
		if (listBankAcc == null) {
			listBankAcc = new ArrayList<BankAccItem>();
		}
		if (transferListAdapter == null) {
			transferListAdapter = new TransferListAdapter(getActivity(),
					android.R.layout.simple_list_item_1, listBankAcc);
		}
		lv_BankAcc.setAdapter(transferListAdapter);
		if (list_spinner == null) {
			list_spinner = new ArrayList<String>();
			list_spinner
					.add(getStringResource(R.string.DanhSachChuyenTienNoiBo));
			list_spinner
					.add(getStringResource(R.string.DanhSachChuyenTienRaNgoai));
		}
		spn_cashtransferlist.setChoises(list_spinner);
	}

	private void initListener() {
		if (DeviceProperties.isTablet) {

		} else {
			lv_BankAcc.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (STATE_TRANSFER == CASH_TRANSFER_TYPE) {
						transferListAdapter.setExpandPosition(position);
						transferListAdapter.notifyDataSetChanged();
					}
				}
			});
		}
		// lv_BankAcc.setOnItemLongClickListener(new OnItemLongClickListener() {
		//
		// @Override
		// public boolean onItemLongClick(AdapterView<?> parent, View view,
		// int position, long id) {
		//
		// longClickItem = listBankAcc.get(position);
		// return false;
		// }
		// });

		spn_cashtransferlist
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelect(Object obj, int position) {
						if (position == 0) {
							STATE_TRANSFER = INTERNAL_TRANSFER_TYPE;
							if (DeviceProperties.isTablet) {
								header_bank.setVisibility(View.GONE);
								header_internal.setVisibility(View.VISIBLE);
							}
						} else if (position == 1) {
							STATE_TRANSFER = CASH_TRANSFER_TYPE;
							if (DeviceProperties.isTablet) {
								header_bank.setVisibility(View.VISIBLE);
								header_internal.setVisibility(View.GONE);
							}
						}
						updateCashTransferList();
					}
				});
	}

	@Override
	public void onResume() {
		super.onResume();
		spn_cashtransferlist.setSelection(0);
		CallGetBankAccList(StaticObjectManager.acctnoItem.ACCTNO);

	}

	private void CallGetBankAccList(String paramString) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_GetCashTransferList));
		}
		{
			list_key.add("pv_afacctno");
			list_value.add(paramString);
		}
		StaticObjectManager.connectServer.callHttpPostService(GETBANKACCLIST,
				this, list_key, list_value);

	}

	private void CallRemoveTransferRegister(String pr_AfAcctno,
			String pr_TransferType, String pr_BeneficiaryAfacctno,
			String pr_BackAccountNo) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_RemoveTransferRegister));
		}
		{
			list_key.add("AfAcctno");
			list_value.add(pr_AfAcctno);
		}
		{
			list_key.add("TransferType");
			list_value.add(pr_TransferType);
		}
		{
			list_key.add("BeneficiaryAfacctno");
			list_value.add(pr_BeneficiaryAfacctno);
		}
		{
			list_key.add("BackAccountNo");
			list_value.add(pr_BackAccountNo);
		}
		StaticObjectManager.connectServer.callHttpPostService(
				REMOVETRANSFERREGISTER, this, list_key, list_value);

	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case GETBANKACCLIST:
			if (rObj.obj != null) {
				StaticObjectManager.listBankAcc = (BankAccList) rObj.obj;
				updateCashTransferList();
			}
			break;
		case REMOVETRANSFERREGISTER:
			CallGetBankAccList(StaticObjectManager.acctnoItem.ACCTNO);
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			break;

		default:
			break;
		}
	}

	private void updateCashTransferList() {
		listBankAcc.clear();
		if (StaticObjectManager.listBankAcc != null) {
			if (getStateTransfer() == INTERNAL_TRANSFER_TYPE) {
				listBankAcc.addAll(StaticObjectManager.listBankAcc.listAcc);
				transferListAdapter.setExpandPosition(-1);
			} else {
				listBankAcc.addAll(StaticObjectManager.listBankAcc.listBank);
				listBankAcc.addAll(StaticObjectManager.listBankAcc.listBankMSB);
			}
			transferListAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		CallGetBankAccList(StaticObjectManager.acctnoItem.ACCTNO);
	}

	private void CreateDialogDetail() {

		dialog_detail = new Dialog(mainActivity, R.style.cusDialog);
		dialog_detail.setCancelable(true);
		dialog_detail
				.setTitle(getStringResource(R.string.ChiTietNguoiThuHuong));
		dialog_detail.setContentView(R.layout.cashtransferitemdetail);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog_detail.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog_detail.getWindow().setAttributes(lp);

		tv_detail_beneficiaryname = ((LabelContentLayout) dialog_detail
				.findViewById(R.id.text_cashtransferitemdetail_beneficiaryname));
		tv_detail_bankaccount = ((LabelContentLayout) dialog_detail
				.findViewById(R.id.text_cashtransferitemdetail_bankaccount));
		tv_detail_bank = ((LabelContentLayout) dialog_detail
				.findViewById(R.id.text_cashtransferitemdetail_bank));
		tv_detail_branch = ((LabelContentLayout) dialog_detail
				.findViewById(R.id.text_cashtransferitemdetail_branch));
		tv_detail_city = ((LabelContentLayout) dialog_detail
				.findViewById(R.id.text_cashtransferitemdetail_city));
		tv_detail_custodycd = ((LabelContentLayout) dialog_detail
				.findViewById(R.id.text_cashtransferitemdetail_custodycd));
		tv_detail_afacctno = ((LabelContentLayout) dialog_detail
				.findViewById(R.id.text_cashtransferitemdetail_afacctno));

	}

	private void displayDetail(BankAccItem item) {
		if (dialog_detail == null) {
			return;
		}
		tv_detail_beneficiaryname.getContent()
				.setText(item.REG_BENEFICARY_NAME);
		tv_detail_bankaccount.getContent().setText(item.REG_ACCTNO);
		tv_detail_bank.getContent().setText(item.REG_BENEFICARY_INFO);
		tv_detail_branch.getContent().setText(item.CITYBANK);
		tv_detail_city.getContent().setText(item.CITYEF);
		tv_detail_custodycd.getContent().setText(item.REG_CUSTODYCD);
		tv_detail_afacctno.getContent().setText(item.REG_ACCTNO);
		if (getStateTransfer() == INTERNAL_TRANSFER_TYPE) {
			// nếu là chuyển tiền nội bộ thì k hiện dialog chi tiết lên nữa
			// gone
			tv_detail_bankaccount.setVisibility(View.GONE);
			tv_detail_bank.setVisibility(View.GONE);
			tv_detail_branch.setVisibility(View.GONE);
			tv_detail_city.setVisibility(View.GONE);
			// visible
			tv_detail_custodycd.setVisibility(View.VISIBLE);
			tv_detail_afacctno.setVisibility(View.VISIBLE);

		} else if (getStateTransfer() == CASH_TRANSFER_TYPE) {
			// visible
			tv_detail_bankaccount.setVisibility(View.VISIBLE);
			tv_detail_bank.setVisibility(View.VISIBLE);
			tv_detail_branch.setVisibility(View.VISIBLE);
			tv_detail_city.setVisibility(View.VISIBLE);
			// gone
			tv_detail_custodycd.setVisibility(View.GONE);
			tv_detail_afacctno.setVisibility(View.GONE);
			dialog_detail.show();
		}

	}
}
