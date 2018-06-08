package com.fss.mobiletrading.function.oddorderregister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.adapter.OddOrderRegisterAdapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.MenuFunction;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;

public class OddOrderRegister extends AbstractFragment {
	final String GETODDLOTLIST = "GetOddLotListService#GETODDLOTLIST";
	final String GETCANCELODDLOTLIST = "GetCancelOddLotListService#GETCANCELODDLOTLIST";
	final String ODDLOTREGISTER = "SuccessService#ODDLOTREGISTER";
	final String ODDLOTCANCEL = "SuccessService#ODDLOTCANCEL";

	final int SELLABLE_STATE = 0;
	final int CACNCELLED_STATE = 1;

	ListView lv_OddOrder;
	Button btn_Sellable;
	Button btn_Cancelled;
	TextView tv_dialogdetails_symbol;
	TextView tv_dialogdetails_quantity;
	TextView tv_dialogdetails_price;
	TextView tv_dialogdetails_value;
	TextView tv_dialogdetails_fee;
	TextView tv_dialogdetails_tax;
	TextView tv_dialogdetails_rcvamount;
	EditText edt_dialogdetails_pin;
	Button btn_dialogdetails_Accept;
	Button btn_dialogdetails_Cancel;

	List<OddLotItem> list_OddLotItems;
	OddOrderRegisterAdapter adapter;
	int state;
	Dialog dialogDetails;
	OddLotItem selectedItem;

	public static OddOrderRegister newInstance(MainActivity mActivity) {
		OddOrderRegister self = new OddOrderRegister();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.OddOrderRegister);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.odd_order_register, container,
				false);
		lv_OddOrder = (ListView) view
				.findViewById(R.id.listview_odd_order_register);
		btn_Sellable = (Button) view
				.findViewById(R.id.btn_oddorderregister_sellable);
		btn_Cancelled = (Button) view
				.findViewById(R.id.btn_oddorderregister_cancelled);
		initialise();

		return view;
	}

	private void initialise() {

		initialiseData();
		initialiseListener();
		createDialogCheckOrder();
	}

	private void initialiseData() {

		if (list_OddLotItems == null) {
			list_OddLotItems = new ArrayList<OddLotItem>();
		}
		if (adapter == null) {
			adapter = new OddOrderRegisterAdapter(getActivity(),
					android.R.layout.simple_list_item_1, list_OddLotItems);
		}
		lv_OddOrder.setAdapter(adapter);

	}

	private void initialiseListener() {

		btn_Sellable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				functionSellableClick();
			}
		});
		btn_Cancelled.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				functionCancelledClick();
			}
		});
		lv_OddOrder.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				selectedItem = list_OddLotItems.get(position);
				displayDialogDetails();
			}
		});
	}

	@Override
	public void onResume() {

		super.onResume();
		functionSellableClick();
	}

	@Override
	public void onPause() {

		super.onPause();
	}

	private void CallGetOddLotList(String pr_Afacctno) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_GetOddLotList));
		}
		{
			list_key.add("Afacctno");
			list_value.add(pr_Afacctno);
		}
		StaticObjectManager.connectServer.callHttpPostService(GETODDLOTLIST,
				this, list_key, list_value);

	}

	private void CallGetCancelOddLotList(String pr_Afacctno) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_GetCancelOddLotList));
		}
		{
			list_key.add("Afacctno");
			list_value.add(pr_Afacctno);
		}
		StaticObjectManager.connectServer.callHttpPostService(
				GETCANCELODDLOTLIST, this, list_key, list_value);

	}

	private void CallOddLotRegister(String pr_Afacctno, String pr_Symbol,
			String pr_Qtty, String pr_QuotePrice, String pr_Pin) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_OddLotRegister));
		}
		{
			list_key.add("Afacctno");
			list_value.add(pr_Afacctno);
		}
		{
			list_key.add("Symbol");
			list_value.add(pr_Symbol);
		}
		{
			list_key.add("Qtty");
			list_value.add(pr_Qtty);
		}
		{
			list_key.add("QuotePrice");
			list_value.add(pr_QuotePrice);
		}
		{
			list_key.add("Pin");
			list_value.add(pr_Pin);
		}
		StaticObjectManager.connectServer.callHttpPostService(ODDLOTREGISTER,
				this, list_key, list_value);
	}

	private void CallOddLotCancel(String pr_Afacctno, String pr_Symbol,
			String pr_Qtty, String pr_TxNum, String pr_TxDate, String pr_Pin) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();

		{
			list_key.add("link");
			list_value.add(getStringResource(R.string.controller_OddLotCancel));
		}
		{
			list_key.add("Afacctno");
			list_value.add(pr_Afacctno);
		}
		{
			list_key.add("Symbol");
			list_value.add(pr_Symbol);
		}
		{
			list_key.add("Qtty");
			list_value.add(pr_Qtty);
		}
		{
			list_key.add("TxNum");
			list_value.add(pr_TxNum);
		}
		{
			list_key.add("TxDate");
			list_value.add(pr_TxDate);
		}
		{
			list_key.add("Pin");
			list_value.add(pr_Pin);
		}
		StaticObjectManager.connectServer.callHttpPostService(ODDLOTCANCEL,
				this, list_key, list_value);
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case GETODDLOTLIST:
			if (rObj.obj != null) {
				if (state == SELLABLE_STATE) {
					list_OddLotItems.clear();
					list_OddLotItems.addAll((List<OddLotItem>) rObj.obj);
					adapter.notifyDataSetChanged();
				}
			}
			break;
		case GETCANCELODDLOTLIST:
			if (rObj.obj != null) {
				if (state == CACNCELLED_STATE) {
					list_OddLotItems.clear();
					list_OddLotItems.addAll((List<OddLotItem>) rObj.obj);
					adapter.notifyDataSetChanged();
				}
			}
			break;
		case ODDLOTREGISTER:
			if (rObj.EM != null) {
				showDialogMessage(getStringResource(R.string.thong_bao),
						rObj.EM);
			} else {
				showDialogMessage(getStringResource(R.string.thong_bao),
						getStringResource(R.string.Giaodichthanhcong));
			}

			if (StaticObjectManager.acctnoItem.ACCTNO != null) {
				CallGetOddLotList(StaticObjectManager.acctnoItem.ACCTNO);
			}
			break;
		case ODDLOTCANCEL:
			if (rObj.EM != null) {
				showDialogMessage(getStringResource(R.string.thong_bao),
						rObj.EM);
			} else {
				showDialogMessage(getStringResource(R.string.thong_bao),
						getStringResource(R.string.Giaodichthanhcong));
			}
			if (StaticObjectManager.acctnoItem.ACCTNO != null) {
				CallGetCancelOddLotList(StaticObjectManager.acctnoItem.ACCTNO);
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void processNull(String key) {

		super.processNull(key);
	}

	private void functionSellableClick() {

		btn_Sellable.setBackgroundColor(getResources().getColor(
				R.color.color_Mua));
		btn_Cancelled.setBackgroundColor(Color.parseColor("#00ffffff"));
		state = SELLABLE_STATE;
		if (StaticObjectManager.acctnoItem.ACCTNO != null) {
			CallGetOddLotList(StaticObjectManager.acctnoItem.ACCTNO);
		}

	}

	private void functionCancelledClick() {

		btn_Sellable.setBackgroundColor(Color.parseColor("#00ffffff"));
		btn_Cancelled.setBackgroundColor(getResources().getColor(
				R.color.color_Mua));
		state = CACNCELLED_STATE;
		if (StaticObjectManager.acctnoItem.ACCTNO != null) {
			CallGetCancelOddLotList(StaticObjectManager.acctnoItem.ACCTNO);
		}

	}

	private void createDialogCheckOrder() {

		dialogDetails = new Dialog(getActivity());
		dialogDetails.setCancelable(true);
		dialogDetails
				.setContentView(R.layout.odd_order_register_dialog_details);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialogDetails.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialogDetails.getWindow().setAttributes(lp);

		tv_dialogdetails_symbol = (TextView) dialogDetails
				.findViewById(R.id.text_oddorderregisterdetails_symbol);
		tv_dialogdetails_quantity = (TextView) dialogDetails
				.findViewById(R.id.text_oddorderregisterdetails_quantity);
		tv_dialogdetails_price = (TextView) dialogDetails
				.findViewById(R.id.text_oddorderregisterdetails_price);
		tv_dialogdetails_value = (TextView) dialogDetails
				.findViewById(R.id.text_oddorderregisterdetails_value);
		tv_dialogdetails_fee = (TextView) dialogDetails
				.findViewById(R.id.text_oddorderregisterdetails_fee);
		tv_dialogdetails_tax = (TextView) dialogDetails
				.findViewById(R.id.text_oddorderregisterdetails_tax);
		tv_dialogdetails_rcvamount = (TextView) dialogDetails
				.findViewById(R.id.text_oddorderregisterdetails_receiveamount);
		edt_dialogdetails_pin = (EditText) dialogDetails
				.findViewById(R.id.edt_oddorderregisterdetails_pin);
		btn_dialogdetails_Accept = (Button) dialogDetails
				.findViewById(R.id.btn_oddorderregisterdetails_accept);
		btn_dialogdetails_Cancel = (Button) dialogDetails
				.findViewById(R.id.btn_oddorderregisterdetails_cancel);
		Common.setupUI(dialogDetails
				.findViewById(R.id.odd_order_register_dialog_details),
				dialogDetails);
		btn_dialogdetails_Accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (edt_dialogdetails_pin.length() > 0) {
					if (state == SELLABLE_STATE && selectedItem != null
							&& StaticObjectManager.acctnoItem.ACCTNO != null) {
						CallOddLotRegister(
								StaticObjectManager.acctnoItem.ACCTNO,
								selectedItem.SYMBOL, selectedItem.QUANTITY,
								selectedItem.QUOTEPRICE, edt_dialogdetails_pin
										.getText().toString());
					}
					if (state == CACNCELLED_STATE && selectedItem != null
							&& StaticObjectManager.acctnoItem.ACCTNO != null) {
						CallOddLotCancel(StaticObjectManager.acctnoItem.ACCTNO,
								selectedItem.SYMBOL, selectedItem.QUANTITY,
								selectedItem.TXNUM, selectedItem.TXDATE,
								edt_dialogdetails_pin.getText().toString());

					}
					dialogDetails.dismiss();
					edt_dialogdetails_pin.getText().clear();
				} else {
					showDialogMessage(getStringResource(R.string.thong_bao),
							getStringResource(R.string.NhapPin));
				}

			}
		});
		btn_dialogdetails_Cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				edt_dialogdetails_pin.getText().clear();
				dialogDetails.dismiss();
			}
		});
	}

	private void displayDialogDetails() {

		if (selectedItem != null) {
			tv_dialogdetails_symbol.setText(selectedItem.SYMBOL);
			tv_dialogdetails_quantity.setText(Common
					.formatAmount(selectedItem.QUANTITY));
			tv_dialogdetails_price.setText(Common
					.formatAmount(selectedItem.QUOTEPRICE));
			tv_dialogdetails_value.setText(Common
					.formatAmount(selectedItem.AMOUNT));
			tv_dialogdetails_fee.setText(Common
					.formatAmount(selectedItem.FEEAMT));
			tv_dialogdetails_tax.setText(Common
					.formatAmount(selectedItem.TAXAMT));
			tv_dialogdetails_rcvamount.setText(Common
					.formatAmount(selectedItem.RCVAMT));
		}
		if (state == SELLABLE_STATE) {
			dialogDetails.setTitle(getStringResource(R.string.DangKyBanLoLe));
			btn_dialogdetails_Accept
					.setText(getStringResource(R.string.DangKy));
		} else {
			dialogDetails
					.setTitle(getStringResource(R.string.HuyDangKyBanLoLe));
			btn_dialogdetails_Accept
					.setText(getStringResource(R.string.HuyDangKy));
		}
		dialogDetails.show();

	}

}
