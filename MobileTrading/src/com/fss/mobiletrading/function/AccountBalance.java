package com.fss.mobiletrading.function;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fss.mobiletrading.adapter.StockBalanceAdapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.balance.BalanceService;
import com.fss.mobiletrading.function.placeorder.OrderSetParams;
import com.fss.mobiletrading.function.placeorder.PlaceOrderService;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.MoneyInfoItem;
import com.fss.mobiletrading.object.Order;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.StockBalanceItem;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;
import com.tcscuat.mobiletrading.design.LabelContentLayout;
import com.tcscuat.mobiletrading.design.MyContextMenu;
import com.tcscuat.mobiletrading.design.MySpinner;

import java.util.ArrayList;
import java.util.List;
public class AccountBalance extends AbstractFragment {

	public static final String GETMONEYINFO = "GetMoneyInfoService";
	public static final String GETSTOCKSBYAFFACCNO = "GetStockByAffaccnoService";

	final int BALANCE_CASH = 0;
	final int BALANCE_STOCK = 1;

	MySpinner spn_switch;

	ListView lv_DMCK;
	LabelContentLayout tv_ChoVeT0;
	LabelContentLayout tv_ChoVeT1;
	LabelContentLayout tv_ChoVeT2;
	LabelContentLayout tv_RTT;
	LabelContentLayout tv_RutToiDa;
	LabelContentLayout tv_CashAvailable;
	LabelContentLayout tv_TienNopBoSung;
	LabelContentLayout tv_GiaTriNAV;
	LabelContentLayout tv_TongNo;
	LabelContentLayout tv_UngToiDa;
	LabelContentLayout tv_cotucchove;
	LinearLayout layout_balance_stock;
	LinearLayout layout_balance_cash;

	StockBalanceAdapter adapter;
	MoneyInfoItem moneyInfoItem;
	List<StockBalanceItem> listDMCKItem;
	List<String> list_Switch;
	int selectedItemPosition;
	MyContextMenu contextMenu;
	StockBalanceItem stockSelect;

	public static AccountBalance newInstance(MainActivity mActivity) {

		AccountBalance self = new AccountBalance();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.SoDu);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.accountbalance, viewGroup, false);
		initView(view);
		init();
		initialiseListener();
		return view;
	}

	private void initView(View view) {

		tv_CashAvailable = ((LabelContentLayout) view
				.findViewById(R.id.text_tienmat));
		tv_UngToiDa = ((LabelContentLayout) view
				.findViewById(R.id.text_ungtoida));
		tv_RutToiDa = ((LabelContentLayout) view
				.findViewById(R.id.text_ruttoida));
		tv_TongNo = ((LabelContentLayout) view.findViewById(R.id.text_tongno));
		tv_RTT = ((LabelContentLayout) view.findViewById(R.id.text_RTT));
		tv_TienNopBoSung = ((LabelContentLayout) view
				.findViewById(R.id.text_tiennopbosung));
		tv_ChoVeT0 = ((LabelContentLayout) view.findViewById(R.id.text_choveT0));
		tv_ChoVeT1 = ((LabelContentLayout) view.findViewById(R.id.text_choveT1));
		tv_ChoVeT2 = ((LabelContentLayout) view.findViewById(R.id.text_choveT2));
		tv_GiaTriNAV = ((LabelContentLayout) view.findViewById(R.id.text_GiaTriNAV));
		tv_cotucchove = (LabelContentLayout) view
				.findViewById(R.id.text_cotucchove);
		lv_DMCK = ((ListView) view.findViewById(R.id.listview_DMCK_rutgon));
		layout_balance_stock = (LinearLayout) view
				.findViewById(R.id.layout_balance_stock);
		layout_balance_cash = (LinearLayout) view
				.findViewById(R.id.layout_balance_cash);
		spn_switch = ((MySpinner) view.findViewById(R.id.spn_accountbalance));
	}

	SimpleAction onBuyClickListener;
	SimpleAction onSellClickListener;

	private void init() {
		if (onBuyClickListener == null) {
			onBuyClickListener = new SimpleAction() {

				@Override
				public void performAction(Object obj) {
					if (obj instanceof StockBalanceItem) {
						StockBalanceItem item = (StockBalanceItem) obj;
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.getITEM(), Order.SIDE_NB,
								null, null));
					}
				}
			};
		}
		if (onSellClickListener == null) {
			onSellClickListener = new SimpleAction() {

				@Override
				public void performAction(Object obj) {
					if (obj instanceof StockBalanceItem) {
						StockBalanceItem item = (StockBalanceItem) obj;
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.getITEM(), Order.SIDE_NS,
								null, null));

					}
				}
			};
		}
		if (listDMCKItem == null) {
			listDMCKItem = new ArrayList<StockBalanceItem>();
		}
		listDMCKItem.clear();
		if (adapter == null) {
			adapter = new StockBalanceAdapter(mainActivity,
					android.R.layout.simple_list_item_1, listDMCKItem);
			adapter.setOnBuyClickListener(onBuyClickListener);
			adapter.setOnSellClickListener(onSellClickListener);
		}
		adapter.notifyDataSetChanged();

		if (list_Switch == null) {
			list_Switch = new ArrayList<String>();
			list_Switch.add(getStringResource(R.string.SoDuTien));
			list_Switch.add(getStringResource(R.string.SoDuChungKhoan));
		}
		spn_switch.setChoises(list_Switch);
		lv_DMCK.setAdapter(adapter);
		// registerForContextMenu(lv_DMCK);
		if (contextMenu == null) {
			List<String> side = new ArrayList<String>();
			side.add(getStringResource(R.string.Mua));
			side.add(getStringResource(R.string.Ban));
			contextMenu = new MyContextMenu(mainActivity);
			contextMenu.setChoises(side);
		}
	}

	private void initialiseListener() {
		contextMenu
				.setOnItemSelectedListener(new MyContextMenu.OnItemSelectedListener() {
					@Override
					public void onItemSelect(Object obj, int position) {
						if (position == 0) {
							onBuyClickListener.performAction(stockSelect);
						} else {
							onSellClickListener.performAction(stockSelect);
						}
					}
				});

		spn_switch
				.setOnItemSelectedListener(new MyContextMenu.OnItemSelectedListener() {
					@Override
					public void onItemSelect(Object obj, int position) {
						if (position == BALANCE_CASH) {
							layout_balance_cash.setVisibility(View.VISIBLE);
							layout_balance_stock.setVisibility(View.GONE);
							CallGetMoneyInfo(AccountBalance.this);
						} else {
							layout_balance_cash.setVisibility(View.GONE);
							layout_balance_stock.setVisibility(View.VISIBLE);
							CallGetStocksByAffaccno();
						}
					}
				});
		// lv_DMCK.setOnItemLongClickListener(new OnItemLongClickListener() {
		// @Override
		// public boolean onItemLongClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// selectedTabPosition = position;
		// return false;
		// }
		// });

		// hàm này chỉ hoạt động trên mobile vì trên tablet trong itemview có
		// chứa 1 button, button này ngan onItemClickListener xảy ra
		lv_DMCK.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				stockSelect = listDMCKItem.get(position);
				contextMenu.show();
			}
		});
	}
	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoActionMenu();
	}
	public void onResume() {
		super.onResume();
		spn_switch.setSelection(0);
	}

	public void CallGetMoneyInfo(INotifier notifier) {
		boolean rs = BalanceService.CallGetMoneyInfo(
				StaticObjectManager.acctnoItem.ACCTNO, this, GETMONEYINFO);
		if (!rs) {
			clearCashStatement();
			clearStockStatement();
		}
	}

	private void CallGetStocksByAffaccno() {
		boolean callGetStockByAfacctno = PlaceOrderService
				.CallGetStocksByAffaccno(StaticObjectManager.acctnoItem.ACCTNO,
						this, GETSTOCKSBYAFFACCNO);
	}

	private void DisplaySoDu() {
		if (moneyInfoItem != null) {
			tv_CashAvailable.getContent().setText(
					Common.formatAmount(moneyInfoItem.BALANCE));
		}
		tv_UngToiDa.getContent().setText(
				Common.formatAmount(moneyInfoItem.AVLADVANCE));
		tv_RutToiDa.getContent().setText(
				Common.formatAmount(moneyInfoItem.AVLWITHDRAW));
		tv_TongNo.getContent().setText(
				Common.formatAmount(moneyInfoItem.TOTALLOAN));
		if(moneyInfoItem.MARGINRATE.equals("100000")||moneyInfoItem.MARGINRATE.equals("10000000")){
			tv_RTT.getContent().setText("∞");
		}
		else {
			tv_RTT.getContent().setText(Common.formatAmount(moneyInfoItem.MARGINRATE));
		}

		tv_TienNopBoSung.getContent().setText(
				Common.formatAmount(moneyInfoItem.ADDVND));
		tv_ChoVeT0.getContent().setText(
				Common.formatAmount(moneyInfoItem.CASH_RECEIVING_T0));
		tv_ChoVeT1.getContent().setText(
				Common.formatAmount(moneyInfoItem.CASH_RECEIVING_T1));
		tv_ChoVeT2.getContent().setText(
				Common.formatAmount(moneyInfoItem.CASH_RECEIVING_T2));
		tv_GiaTriNAV.getContent().setText(Common.formatAmount(moneyInfoItem.NAV));
        if (DeviceProperties.isTablet) {
			tv_cotucchove.setText(Common.formatAmount(moneyInfoItem
					.getCARECEIVING()));
//			if(moneyInfoItem.CASH_RECEIVING_T2.length()>7){
//				tv_ChoVeT2.getContent().setText(
//						Common.formatAmount(moneyInfoItem.CASH_RECEIVING_T2.substring(0,6))+"\n" +Common.formatAmount(moneyInfoItem.CASH_RECEIVING_T2.substring(7)));
//			}
		}
	}

	private void clearCashStatement() {
		tv_UngToiDa.getContent().setText(StringConst.EMPTY);
		tv_RutToiDa.getContent().setText(StringConst.EMPTY);
		tv_GiaTriNAV.getContent().setText(StringConst.EMPTY);
		tv_TongNo.getContent().setText(StringConst.EMPTY);
		tv_RTT.getContent().setText(StringConst.EMPTY);
		tv_TienNopBoSung.getContent().setText(StringConst.EMPTY);
		tv_ChoVeT0.getContent().setText(StringConst.EMPTY);
		tv_ChoVeT1.getContent().setText(StringConst.EMPTY);
		tv_ChoVeT2.getContent().setText(StringConst.EMPTY);
		tv_CashAvailable.getContent().setText(StringConst.EMPTY);
	}

	private void clearStockStatement() {
		listDMCKItem.clear();
		adapter.notifyDataSetChanged();
	}

	public void notifyChangeAcctNo() {
		super.notifyChangeAcctNo();
		CallGetStocksByAffaccno();
		CallGetMoneyInfo(AccountBalance.this);
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case GETSTOCKSBYAFFACCNO:
			listDMCKItem.clear();
			listDMCKItem.addAll((List<StockBalanceItem>) rObj.obj);
			adapter.notifyDataSetChanged();
			break;
		case GETMONEYINFO:
			moneyInfoItem = ((MoneyInfoItem) rObj.obj);
			DisplaySoDu();
			break;

		default:
			break;
		}
	}

	@Override
	protected void processErrorOther(String key, ResultObj rObj) {
		super.processErrorOther(key, rObj);
		switch (key) {
		case GETSTOCKSBYAFFACCNO:
			clearStockStatement();
			break;
		case GETMONEYINFO:
			clearCashStatement();
			break;

		default:
			break;
		}
	}

    @Override
    public void refresh() {
        super.refresh();
        if (spn_switch.getContextMenu().getSelectedItem().toString().equals(getStringResource(R.string.SoDuChungKhoan))) {
            layout_balance_cash.setVisibility(View.GONE);
            layout_balance_stock.setVisibility(View.VISIBLE);
            CallGetStocksByAffaccno();
        } else {
            layout_balance_cash.setVisibility(View.VISIBLE);
            layout_balance_stock.setVisibility(View.GONE);
            CallGetMoneyInfo(AccountBalance.this);
        }
    }

}
