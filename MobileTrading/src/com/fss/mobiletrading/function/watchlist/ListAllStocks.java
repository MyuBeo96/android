package com.fss.mobiletrading.function.watchlist;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.StockItem;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.MyActionBar.Action;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;
import com.tcscuat.mobiletrading.design.SearchStockUI;
import com.tcscuat.mobiletrading.design.SearchStockUI.OnFocusChangeListenerCustom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListAllStocks extends AbstractFragment {

	static final String EDITFAV = "SuccessService#EDITFAV";

	ListView lv_allstock;
	SearchStockUI searchStockUI;
	List<StockItem> listStockItem;
	List<StockItem> listStockItemClone;
	ListAllStocks_Adapter adapter;
	FavWatchListItem currentFav;
	Action editFavAction;
	List<String> listSelect;
	/**
	 * only tablet
	 */
	Button btn_save;
	Button btn_cancel;

	public static ListAllStocks newInstance(MainActivity mActivity) {

		ListAllStocks self = new ListAllStocks();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.ListAllStocks);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View view = inflater.inflate(R.layout.listallstocks, container, false);
		lv_allstock = (ListView) view.findViewById(R.id.listview_listallstocks);
		searchStockUI = (SearchStockUI) view
				.findViewById(R.id.searchtext_listallstock_symbol);
		btn_save = (Button) view.findViewById(R.id.btn_listallstock_save);
		btn_cancel = (Button) view.findViewById(R.id.btn_listallstock_cancel);
		searchStockUI.setVisibleButton(false);
		searchStockUI.setVisibleClearField(true);
		init();
		Common.setupUI(view.findViewById(R.id.listallstocks), mainActivity);
		initListener();
		return view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog d = super.onCreateDialog(savedInstanceState);
		Window w = d.getWindow();
		w.setBackgroundDrawableResource(R.color.transparent);
		w.setGravity(Gravity.LEFT | Gravity.TOP);
		WindowManager.LayoutParams l = w.getAttributes();
		l.x = getDimenResource(R.dimen.t_framewatchlist_width);
		l.y = getDimenResource(R.dimen.t_header_height);
		w.setDimAmount(0.4f);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return d;
	}

	@Override
	public void onStart() {
		super.onStart();
		int width = getDimenResource(R.dimen.t_dialoglistallstock_width);
		int height = mainActivity.getHeightScreen()
				- getDimenResource(R.dimen.t_header_height)
				- getDimenResource(R.dimen.t_footer_height);
		if (getDialog() != null) {
			getDialog().getWindow().setLayout(width, height);
		}
	}

	private void init() {
		if (listStockItem == null) {
			listStockItem = StaticObjectManager.listStock;
		}
		if (listStockItemClone == null) {
			listStockItemClone = new ArrayList<StockItem>();
			listStockItemClone.addAll(listStockItem);
		}
		if (listSelect == null) {
			listSelect = new ArrayList<String>();
		}
		if (adapter == null) {
			adapter = new ListAllStocks_Adapter(mainActivity,
					android.R.layout.simple_list_item_1, listStockItemClone);
			adapter.setSelectAc(new SimpleAction() {

				@Override
				public void performAction(Object obj) {
					if (obj instanceof StockItem) {
						StockItem item = (StockItem) obj;
						addStockToFav(item);
					}
				}
			});
			adapter.setUnselectAc(new SimpleAction() {

				@Override
				public void performAction(Object obj) {
					if (obj instanceof StockItem) {
						StockItem item = (StockItem) obj;
						removeStockToFav(item);
					}
				}
			});
		}
		lv_allstock.setAdapter(adapter);
	}

	private void initListener() {
		if (DeviceProperties.isTablet) {
			btn_save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					savePriceBoardFav();
				}
			});
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onDismiss(getDialog());
				}
			});
			searchStockUI
					.setOnFocusChangeListenerCustom(new OnFocusChangeListenerCustom() {

						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							if (hasFocus) {
								mainActivity.showKBoardHook(true, v, StaticObjectManager.getListAllStock());
								mainActivity.showKBoardSymbol(true, v);
							} else {
								mainActivity.showKBoardHook(false, null, null);
								mainActivity.showKBoardSymbol(false, null);
							}
						}
					});
		}
		searchStockUI.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					listStockItemClone.clear();
					listStockItemClone.addAll(listStockItem);
					adapter.notifyDataSetChanged();
				} else {
					listStockItemClone.clear();
					for (int i = 0; i < listStockItem.size(); i++) {
						if (listStockItem.get(i).toString()
								.startsWith(s.toString())) {
							listStockItemClone.add(listStockItem.get(i));
						}
					}
					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		listSelect.clear();
		if (currentFav != null && currentFav.getFavMem() != null) {
			listSelect.addAll(Arrays.asList(currentFav.getFavMem().split(",")));
			adapter.setSelectedItems(listSelect);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		searchStockUI.getEditText().getText().clear();
	}

	private void addStockToFav(StockItem item) {
		if (item != null && !listSelect.contains(item.getMack())) {
			listSelect.add(item.getMack());
			adapter.setSelectedItems(listSelect);
		}
	}

	private void removeStockToFav(StockItem item) {
		if (isShow) {
			listSelect.remove(item.getMack());
			adapter.setSelectedItems(listSelect);
		}
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoAction();
		if (editFavAction == null) {
			editFavAction = new Action() {

				@Override
				public void performAction(View view) {
					savePriceBoardFav();
				}

				@Override
				public String getText() {
					return mainActivity.getStringResource(R.string.DongY);
				}

				@Override
				public int getDrawable() {
					return 0;
				}
			};
		}
		mainActivity.addAction(editFavAction);
	}

	private void savePriceBoardFav() {
		String symbol = StringConst.EMPTY;
		for (int i = 0; i < listSelect.size(); i++) {
			symbol = symbol + listSelect.get(i) + ",";
		}
		currentFav.setFavMem(symbol);
		CallEditFav(currentFav.getCriteriaId(), currentFav.getCName(),
				currentFav.getFavMem());
	}

	@Override
	protected void setBackLogoAction() {
		if (!DeviceProperties.isTablet) {
			mainActivity.setHomeLogoAction(new Action() {

				@Override
				public void performAction(View view) {
					performBackAction();
				}

				@Override
				public int getDrawable() {
					return 0;
				}

				@Override
				public String getText() {
					return mainActivity.getStringResource(R.string.Cancel);
				}
			});
		}
	}

	@Override
	protected void performBackAction() {
		if (mainActivity != null) {
			mainActivity.displayFragment(WatchList.class.getName());
		}
	}

	private void CallEditFav(String id, String name, String symbols) {

		if (currentFav == null) {
			return;
		}
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(getResources()
					.getString(R.string.controller_EditFav));
		}
		{
			list_key.add("Id");
			list_value.add(currentFav.getCriteriaId());
		}
		{
			list_key.add("name");
			list_value.add(currentFav.getCName());
		}
		{
			list_key.add("symbols");
			list_value.add(currentFav.getFavMem());
		}
		StaticObjectManager.connectServer.callHttpPostService(EDITFAV, this,
				list_key, list_value);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		switch (key) {
		case EDITFAV:
			showDialogMessage(getStringResource(R.string.Giaodichthanhcong),
					rObj.EM, new SimpleAction() {

						@Override
						public void performAction(Object obj) {
							mainActivity.displayFragment(WatchList.class
									.getName());
						}
					});
			if (DeviceProperties.isTablet) {
				WatchList watchList = (WatchList) mainActivity
						.getFragmentByName(WatchList.class.getName());
				watchList.onChangeFavListener();
				onDismiss(getDialog());
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void getArgument(Object obj) {
		super.getArgument(obj);
		if (obj instanceof FavWatchListItem) {
			currentFav = (FavWatchListItem) obj;
		}
	}
}
