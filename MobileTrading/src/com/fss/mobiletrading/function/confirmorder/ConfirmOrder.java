package com.fss.mobiletrading.function.confirmorder;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.ConfirmOrder_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ConfirmOrderItem;
import com.fss.mobiletrading.object.ItemString2;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.design.InputDate;
import com.fscuat.mobiletrading.design.LabelContentLayout;
import com.fscuat.mobiletrading.design.MySpinner;

public class ConfirmOrder extends AbstractFragment {

	final String GETCONFIRMORDERLIST = "GetConfirmOrderListService#GETCONFIRMORDERLIST";
	final String CONFIRMORDER = "SuccessService#CONFIRMORDER";

	ListView lv_ConfirmOrder;
	InputDate edt_datestart;
	InputDate edt_dateend;
	MySpinner spn_ExecType;
	Button btn_Update;
	Button btn_Confirm;
	CheckBox checkAll;

	LabelContentLayout edt_PinCode;
	LabelContentLayout tv_details_date;
	LabelContentLayout tv_details_afacctno;
	LabelContentLayout tv_details_symbol;
	LabelContentLayout tv_details_market;
	LabelContentLayout tv_details_buysell;
	LabelContentLayout tv_details_ordertype;
	LabelContentLayout tv_details_trademethod;
	LabelContentLayout tv_details_quantity;
	LabelContentLayout tv_details_price;
	LabelContentLayout tv_details_orderid;
	LabelContentLayout edt_details_pincode;

	List<ItemString2> list_ExecType;
	List<ConfirmOrderItem> list_ConfirmOrderItems;
	ConfirmOrder_Adapter adapter_ConfirmOrder;
	Dialog dialog_EnterPinCode;
	Dialog dialog_Details;
	String submitOrderIds;
	ConfirmOrderItem selectedItem;

	public static ConfirmOrder newInstance(MainActivity mActivity) {
		ConfirmOrder self = new ConfirmOrder();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.ConfirmOrder);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.confirm_order, container, false);
		lv_ConfirmOrder = (ListView) view
				.findViewById(R.id.listview_confirm_order);
		edt_datestart = (InputDate) view
				.findViewById(R.id.edt_confirmorder_fromdate);
		edt_dateend = (InputDate) view
				.findViewById(R.id.edt_confirmorder_todate);
		spn_ExecType = (MySpinner) view
				.findViewById(R.id.spn_confirmorder_exectype);
		btn_Update = (Button) view.findViewById(R.id.btn_confirmorder_update);
		btn_Confirm = (Button) view.findViewById(R.id.btn_confirmorder_confirm);
		checkAll = (CheckBox) view
				.findViewById(R.id.checkbox_confirmorder_check);
		checkAll.setChecked(false);

		initialise();
		return view;
	}

	private void initialise() {

		initialiseData();
		initialiseListener();
	}

	private void initialiseData() {

		if (dialog_EnterPinCode == null) {
			createDialogEnterPinCode();
		}
		if (dialog_Details == null) {
			createDialogDetails();
		}
		if (list_ConfirmOrderItems == null) {
			list_ConfirmOrderItems = new ArrayList<ConfirmOrderItem>();
		}
		if (adapter_ConfirmOrder == null) {
			adapter_ConfirmOrder = new ConfirmOrder_Adapter(getActivity(),
					android.R.layout.simple_list_item_2, list_ConfirmOrderItems);
		}

		if (list_ExecType == null) {
			list_ExecType = new ArrayList<ItemString2>();
			String[] array_ExecType = getResources().getStringArray(
					R.array.ConfirmOrder_ExecType);
			for (String str : array_ExecType) {
				try {
					String[] array = str.split("#");
					list_ExecType.add(new ItemString2(array[0], array[1]));
				} catch (Exception e) {

				}
			}
		}
		spn_ExecType.setChoises(list_ExecType);
		lv_ConfirmOrder.setAdapter(adapter_ConfirmOrder);

	}

	private void initialiseListener() {

		btn_Update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (spn_ExecType.getContextMenu().getSelectedItem() == null) {
					return;
				}

				try {
					String pr_Custodycd = null;
					String pr_FromDate = null;
					String pr_ToDate = null;
					String pr_ExecType = null;

					pr_Custodycd = StaticObjectManager.acctnoItem.CUSTODYCD;
					pr_FromDate = edt_datestart.toString();
					pr_ToDate = edt_dateend.toString();
					pr_ExecType = ((ItemString2) spn_ExecType.getContextMenu()
							.getSelectedItem()).getValue();

					CallGetConfirmOrderList(pr_Custodycd, pr_FromDate,
							pr_ToDate, pr_ExecType);
				} catch (NullPointerException e) {

					showDialogMessage(getStringResource(R.string.Loi),
							getStringResource(R.string.NullError));
				} catch (Exception e) {

					showDialogMessage(getStringResource(R.string.Loi),
							ConfirmOrder.class.getName()
									+ getStringResource(R.string.ErrorProgram));
				}
			}
		});

		btn_Confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				submitOrderIds = adapter_ConfirmOrder.getSubmitOrderIds();
				if (submitOrderIds != null && submitOrderIds.length() > 0) {
					dialog_EnterPinCode.show();
				} else {
					showDialogMessage(getStringResource(R.string.thong_bao),
							getStringResource(R.string.ThucHienKhongThanhCong));
				}

			}
		});

		checkAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (adapter_ConfirmOrder != null) {
					adapter_ConfirmOrder.setCheckAll(isChecked);
					adapter_ConfirmOrder.notifyDataSetChanged();
				}
			}
		});
		lv_ConfirmOrder.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				adapter_ConfirmOrder.setClickCheckBox(position);
			}
		});
		lv_ConfirmOrder
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {

						selectedItem = list_ConfirmOrderItems.get(position);
						showDialogDetails();
						return false;
					}
				});
	}

	@Override
	public void onResume() {

		super.onResume();
		list_ConfirmOrderItems.clear();
		adapter_ConfirmOrder.notifyDataSetChanged();
		checkAll.setChecked(false);
	}

	@Override
	public void onPause() {

		super.onPause();
	}

	private void CallGetConfirmOrderList(String pr_Custodycd,
			String pr_FromDate, String pr_ToDate, String pr_ExecType) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_GetConfirmOrderList));
		}
		{
			list_key.add("Custodycd");
			list_value.add(pr_Custodycd);
		}
		{
			list_key.add("FromDate");
			list_value.add(pr_FromDate);
		}
		{
			list_key.add("ToDate");
			list_value.add(pr_ToDate);
		}
		{
			list_key.add("ExecType");
			list_value.add(pr_ExecType);
		}
		StaticObjectManager.connectServer.callHttpPostService(
				GETCONFIRMORDERLIST, this, list_key, list_value);
	}

	private void CallConfirmOrder(String pr_SubmitOrderIds, String pr_pincode) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value.add(getStringResource(R.string.controller_ConfirmOrder));
		}
		{
			list_key.add("SubmitOrderIds");
			list_value.add(pr_SubmitOrderIds);
		}
		{
			list_key.add("Pin");
			list_value.add(pr_pincode);
		}
		StaticObjectManager.connectServer.callHttpPostService(CONFIRMORDER,
				this, list_key, list_value);
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case GETCONFIRMORDERLIST:
			if (rObj.obj != null) {
				list_ConfirmOrderItems.clear();
				list_ConfirmOrderItems
						.addAll((List<ConfirmOrderItem>) rObj.obj);
				checkAll.setChecked(false);
				adapter_ConfirmOrder.setCheckAll(false);
				adapter_ConfirmOrder.notifyDataSetChanged();
			}
			break;
		case CONFIRMORDER:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			btn_Update.performClick();
			break;

		default:
			break;
		}
	}

	private void createDialogEnterPinCode() {

		dialog_EnterPinCode = new Dialog(getActivity());
		dialog_EnterPinCode.setCancelable(true);
		dialog_EnterPinCode.setTitle(getStringResource(R.string.NhapPin));
		dialog_EnterPinCode.setContentView(R.layout.enter_pin_code_dialog);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog_EnterPinCode.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog_EnterPinCode.getWindow().setAttributes(lp);
		edt_PinCode = ((LabelContentLayout) dialog_EnterPinCode
				.findViewById(R.id.edt_enterpincode_pin));
		Button btn_ChapNhan = (Button) dialog_EnterPinCode
				.findViewById(R.id.btn_enterpincode_accept);
		Button btn_Cancel = (Button) dialog_EnterPinCode
				.findViewById(R.id.btn_enterpincode_cancel);
		Common.setupUI(
				dialog_EnterPinCode.findViewById(R.id.enter_pin_code_dialog),
				dialog_EnterPinCode);
		btn_ChapNhan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (edt_PinCode.getEditContent().length() > 0) {
					CallConfirmOrder(submitOrderIds, edt_PinCode.getText()
							.toString());
					submitOrderIds = null;
					edt_PinCode.setText(StringConst.EMPTY);
					dialog_EnterPinCode.cancel();
				} else {
					showDialogMessage(getStringResource(R.string.thong_bao),
							getStringResource(R.string.NhapPin));
				}

			}
		});
		btn_Cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				submitOrderIds = null;
				edt_PinCode.setText(StringConst.EMPTY);
				dialog_EnterPinCode.cancel();
			}
		});

	}

	private void createDialogDetails() {

		dialog_Details = new Dialog(getActivity());
		dialog_Details.setCancelable(true);
		dialog_Details.setTitle(getStringResource(R.string.chi_tiet));
		dialog_Details.setContentView(R.layout.confirm_order_dialog_details);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog_Details.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog_Details.getWindow().setAttributes(lp);

		tv_details_date = (LabelContentLayout) dialog_Details
				.findViewById(R.id.text_confirmorderdetails_date);
		tv_details_afacctno = (LabelContentLayout) dialog_Details
				.findViewById(R.id.text_confirmorderdetails_afacctno);
		tv_details_symbol = (LabelContentLayout) dialog_Details
				.findViewById(R.id.text_confirmorderdetails_symbol);
		tv_details_market = (LabelContentLayout) dialog_Details
				.findViewById(R.id.text_confirmorderdetails_market);
		tv_details_buysell = (LabelContentLayout) dialog_Details
				.findViewById(R.id.text_confirmorderdetails_buysell);
		tv_details_ordertype = (LabelContentLayout) dialog_Details
				.findViewById(R.id.text_confirmorderdetails_ordertype);
		tv_details_trademethod = (LabelContentLayout) dialog_Details
				.findViewById(R.id.text_confirmorderdetails_trademethod);
		tv_details_quantity = (LabelContentLayout) dialog_Details
				.findViewById(R.id.text_confirmorderdetails_quantity);
		tv_details_price = (LabelContentLayout) dialog_Details
				.findViewById(R.id.text_confirmorderdetails_price);
		tv_details_orderid = (LabelContentLayout) dialog_Details
				.findViewById(R.id.text_confirmorderdetails_orderid);
		edt_details_pincode = (LabelContentLayout) dialog_Details
				.findViewById(R.id.edt_confirmorderdetails_pin);
		Button btn_ChapNhan = (Button) dialog_Details
				.findViewById(R.id.btn_confirmorderdetails_accept);
		Button btn_Cancel = (Button) dialog_Details
				.findViewById(R.id.btn_confirmorderdetails_cancel);
		Common.setupUI(
				dialog_Details.findViewById(R.id.confirm_order_dialog_details),
				dialog_Details);
		btn_ChapNhan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (edt_details_pincode.getEditContent().length() > 0) {
					CallConfirmOrder(selectedItem.ORDERID, edt_details_pincode
							.getText().toString());
					dialog_Details.cancel();
				} else {
					showDialogMessage(getStringResource(R.string.thong_bao),
							getStringResource(R.string.NhapPin));
				}

			}
		});
		btn_Cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog_Details.cancel();
			}
		});
	}

	private void showDialogDetails() {

		tv_details_date.setText(selectedItem.TXDATE);
		tv_details_afacctno.setText(selectedItem.AFACCTNO);
		tv_details_symbol.setText(selectedItem.SYMBOL);
		tv_details_market.setText(selectedItem.TRADEPLACE);
		tv_details_buysell.setText(selectedItem.EXECTYPE);
		tv_details_ordertype.setText(selectedItem.PRICETYPE);
		tv_details_trademethod.setText(selectedItem.VIA);
		tv_details_quantity.setText(selectedItem.ORDERQTTY);
		tv_details_price.setText(selectedItem.QUOTEPRICE);
		tv_details_orderid.setText(selectedItem.ORDERID);
		edt_details_pincode.setText(StringConst.EMPTY);
		dialog_Details.show();
	}

}
