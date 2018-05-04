package com.fss.mobiletrading.function.watchlist;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.MyActionBar.Action;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;
import com.msbuat.mobiletrading.design.LabelContentLayout;

import java.util.ArrayList;
import java.util.List;

public class ListFavWatchList extends AbstractFragment {

	static final String LISTCRITERIA = "ListCriteriaService#LISTCRITERIA";
	static final String DELETEFAV = "SuccessService#DELETEFAV";
	static final String EDITFAV = "SuccessService#EDITFAV";

	ListView lv_fav;
	LinearLayout lay_addfav;
	LabelContentLayout edt_namefav;
	LabelContentLayout edt_symbolsoffav;
	Button btn_addfav;
	Button btn_cancel;
	/**
	 * only tablet
	 */
	TextView tv_favname;
	/**
	 * only tablet
	 */
	ImageView img_back;
	/**
	 * only tablet
	 */
	ImageView img_addfav;

	FavWatchList_Adapter adapter;
	List<FavWatchListItem> listFav;
	List<FavWatchListItem> dataFav;
	List<Integer> historyListFav;
	Action action_AddListFav;

	public static ListFavWatchList newInstance(MainActivity mActivity) {

		ListFavWatchList self = new ListFavWatchList();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.ListFavWatchList);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View view = inflater.inflate(R.layout.listfavwatchlist, container,
				false);
		lv_fav = (ListView) view.findViewById(R.id.listview_listfavwatchlist);
		lay_addfav = (LinearLayout) view
				.findViewById(R.id.lay_listfavwatchlist_addfav);
		edt_namefav = (LabelContentLayout) view
				.findViewById(R.id.edt_listfavwatchlist_name);
		edt_symbolsoffav = (LabelContentLayout) view
				.findViewById(R.id.edt_listfavwatchlist_symbols);
		btn_addfav = (Button) view
				.findViewById(R.id.btn_listfavwatchlist_Accept);
		btn_cancel = (Button) view
				.findViewById(R.id.btn_listfavwatchlist_Cancel);

		img_back = (ImageView) view
				.findViewById(R.id.img_listfavwatchlist_back);
		img_addfav = (ImageView) view
				.findViewById(R.id.img_listfavwatchlist_addfav);
		tv_favname = (TextView) view
				.findViewById(R.id.text_listfavwatchlist_favname);
		Common.setupUI(view.findViewById(R.id.listfavwatchlist), mainActivity);
		init();
		initListener();
		return view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog d = super.onCreateDialog(savedInstanceState);
		Window w = d.getWindow();
		w.setBackgroundDrawableResource(R.drawable.bg_dialogfragment_bottom);
		WindowManager.LayoutParams l = w.getAttributes();
		l.x = getDimenResource(R.dimen.m_paddingLeft);
		l.y = getDimenResource(R.dimen.header_height)
				+ getDimenResource(R.dimen.t_header_height);
		w.setDimAmount(0.0f);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		w.setGravity(Gravity.LEFT | Gravity.TOP);
		return d;
	}

	@Override
	public void onStart() {
		super.onStart();
		int width = getDimenResource(R.dimen.listfavwatchlist_dialog_width);
		int height = getDimenResource(R.dimen.t_dialogchooseafacctno_height);
		if (getDialog() != null) {
			getDialog().getWindow().setLayout(width, height);
		}
	}

	private void init() {
		if (listFav == null) {
			listFav = new ArrayList<FavWatchListItem>();
		} else {
			listFav.clear();
		}
		if (historyListFav == null) {
			historyListFav = new ArrayList<Integer>();
		} else {
			historyListFav.clear();
		}

		if (adapter == null) {
			adapter = new FavWatchList_Adapter(mainActivity,
					android.R.layout.simple_list_item_1, listFav);
			adapter.setClickAction(new SimpleAction() {

				@Override
				public void performAction(Object obj) {
					if (obj instanceof FavWatchListItem) {
						final FavWatchListItem fav = (FavWatchListItem) obj;
						if (fav.CriteriaId.length() == 0
								|| fav.CriteriaId.equals("0")) {
							addHistory(listFav.indexOf(fav));
							setFavName(fav.CName);
						} else {
							mainActivity.sendArgumentToFragment(
									FullWatchList.class.getName(), fav);
							mainActivity.sendArgumentToFragment(
									WatchList.class.getName(), fav);
							mainActivity.displayFragment(WatchList.class
									.getName());
							onDismiss(getDialog());
						}
					}
				}
			});
			adapter.setDeleteAction(new SimpleAction() {

				@Override
				public void performAction(Object obj) {
					if (obj instanceof FavWatchListItem) {
						final FavWatchListItem fav = (FavWatchListItem) obj;

						showDialogMessage(
								getStringResource(R.string.XoaDanhMuc),
								getStringResource(R.string.BanCoMuonXoaDanhMucNayKhong)
										+ " \"" + fav.getCName() + "\"?",
								new SimpleAction() {

									@Override
									public void performAction(Object obj) {
										CallDeleteFav(fav.getCriteriaId());
									}
								}, new SimpleAction() {

									@Override
									public void performAction(Object obj) {
									}
								}, getStringResource(R.string.Yes),
								getStringResource(R.string.No));
					}
				}
			});
		} else {
			adapter.notifyDataSetChanged();
		}
		lv_fav.setAdapter(adapter);
		setFavName(getStringResource(R.string.ListFavWatchList));
	}

	private void initListener() {
		if (DeviceProperties.isTablet) {
			img_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					hideEditFavForm();
					onBack();
				}
			});
			img_addfav.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onAddFav();
				}
			});
		}
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideEditFavForm();
			}
		});
		btn_addfav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CallEditFav(StringConst.EMPTY, edt_namefav.getText(),
						edt_symbolsoffav.getText().toUpperCase());
			}
		});
	}

	private void hideEditFavForm() {
		if (lay_addfav.getVisibility() == View.VISIBLE) {
			edt_namefav.setText(StringConst.EMPTY);
			edt_symbolsoffav.setText(StringConst.EMPTY);
			lay_addfav.setVisibility(View.GONE);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		edt_namefav.setText(StringConst.EMPTY);
		edt_symbolsoffav.setText(StringConst.EMPTY);
		CallListCriteria();
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setHomeLogoAction();
		if (action_AddListFav == null) {
			action_AddListFav = new Action() {

				@Override
				public void performAction(View view) {
					onAddFav();
				}

				@Override
				public int getDrawable() {

					return R.drawable.ic_add2;
				}

				@Override
				public String getText() {

					return null;
				}
			};
		}
		mainActivity.addAction(action_AddListFav);
	}

	private void onAddFav() {
		lay_addfav.setVisibility(View.VISIBLE);
	}

	@Override
	public void setHomeLogoAction() {
		if (!DeviceProperties.isTablet) {
			mainActivity.setHomeLogoAction(new Action() {

				@Override
				public void performAction(View view) {
					onBack();
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

	private void onBack() {
		if (historyListFav.size() == 1) {
			if (DeviceProperties.isTablet) {
				onDismiss(getDialog());
			} else {
				mainActivity.displayFragment(WatchList.class.getName());
			}
		} else {
			backToPreviousList();
		}
	}

	private void CallListCriteria() {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(getStringResource(R.string.controller_ListCriteria));
		}
		StaticObjectManager.connectServer.callHttpPostService(LISTCRITERIA,
				this, list_key, list_value);
	}

	private void CallDeleteFav(String id) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(getStringResource(R.string.controller_DeleteFav));
		}
		{
			list_key.add("Id");
			list_value.add(id);
		}
		StaticObjectManager.connectServer.callHttpPostService(DELETEFAV, this,
				list_key, list_value);
	}

	private void CallEditFav(String id, String name, String symbols) {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(getResources()
					.getString(R.string.controller_EditFav));
		}
		{
			list_key.add("Id");
			list_value.add(id);
		}
		{
			list_key.add("name");
			list_value.add(name);
		}
		{
			list_key.add("symbols");
			list_value.add(symbols);
		}
		StaticObjectManager.connectServer.callHttpPostService(EDITFAV, this,
				list_key, list_value);
		btn_addfav.setEnabled(false);
	}

	@Override
	protected void processErrorOther(String key, ResultObj rObj) {
		super.processErrorOther(key, rObj);
		btn_addfav.setEnabled(true);
	}

	protected void processNull(String key) {
		super.processNull(key);
		btn_addfav.setEnabled(true);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		switch (key) {
		case LISTCRITERIA:
			dataFav = (List<FavWatchListItem>) rObj.obj;
			try {
				dataFav.get(0).icon = R.drawable.ic_priceboard_portfolio;
				dataFav.get(1).icon = R.drawable.ic_priceboard_favourite;
				dataFav.get(2).icon = R.drawable.ic_priceboard_market;
			} catch (IndexOutOfBoundsException e) {
			}
			if (historyListFav.size() == 0) {
				historyListFav.add(0);
			}
			updateList();
			break;
		case DELETEFAV:
			showDialogMessage(getStringResource(R.string.thong_bao), rObj.EM);
			CallListCriteria();
			break;
		case EDITFAV:
			CallListCriteria();
			showDialogMessage(getStringResource(R.string.Giaodichthanhcong),
					rObj.EM);
			edt_namefav.setText(StringConst.EMPTY);
			edt_symbolsoffav.setText(StringConst.EMPTY);
			btn_addfav.setEnabled(true);
			break;
		default:
			break;
		}

	}

	private void addHistory(int position) {
		if (position >= 0) {
			historyListFav.add(position);
		}
		updateList();

	}

	private void updateList() {
		List<FavWatchListItem> listFavDraft = null;
		listFavDraft = dataFav;
		String favName = getStringResource(R.string.ListFavWatchList);
		String criteriaId = null;
		for (int i = 1; i < historyListFav.size(); i++) {
			FavWatchListItem item = listFavDraft.get(historyListFav.get(i));
			listFavDraft = item.getSubs();
			favName = item.getCName();
			criteriaId = item.CriteriaId;
		}
		setFavName(favName);
		if (criteriaId == null || criteriaId.length() == 0
				|| criteriaId.startsWith("-")) {
			mainActivity.removeAction(action_AddListFav);
			if (DeviceProperties.isTablet) {
				img_addfav.setVisibility(View.GONE);
			}
		} else {
			if (DeviceProperties.isTablet) {
				img_addfav.setVisibility(View.VISIBLE);
			}
			mainActivity.addAction(action_AddListFav);
		}
		if (listFavDraft != null) {
			listFav.clear();
			listFav.addAll(listFavDraft);
			adapter.notifyDataSetChanged();
		}
	}

	private void setFavName(String favName) {
		if (DeviceProperties.isTablet) {
			if (historyListFav.size() <= 1) {
				img_back.setVisibility(View.INVISIBLE);
			} else {
				img_back.setVisibility(View.VISIBLE);
			}
		}

		if (tv_favname != null) {
			tv_favname.setText(favName);
		}
	}

	private void backToPreviousList() {
		if (historyListFav.size() <= 1) {
			return;
		}
		historyListFav.remove(historyListFav.size() - 1);
		updateList();
	}
}
