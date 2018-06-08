package com.fss.mobiletrading.function.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fss.mobiletrading.adapter.NewsAdapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.AppData;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.Login;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.FilterArrayAdapter.Condition;

import java.util.ArrayList;
import java.util.List;

public class NewsBySymbol extends AbstractFragment {

	static final String GETALLNEWSSYMBOL = "GetAllNewsSymbolService";
	static final String GETNEXTNEWSBYID = "GetNextNewsByIdService";
	NewsAdapter adapter;
	String lastNewID;
	List<NewsItem> list_Tintuc;
	ListView lv_NewsBySymbol;
	String symbol;
	String filterText = StringConst.EMPTY;

	public static NewsBySymbol newInstance(MainActivity mActivity) {
		NewsBySymbol self = new NewsBySymbol();
		self.mainActivity = mActivity;
		return self;
	}

	public NewsBySymbol() {

	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.newsbysymbol,
				paramViewGroup, false);
		lv_NewsBySymbol = ((ListView) localView
				.findViewById(R.id.listview_NewsBySymbol));
		initialise();
		initialiseListener();
		return localView;
	}

	private void initialise() {
		if (list_Tintuc == null) {
			list_Tintuc = new ArrayList<NewsItem>();
		} else {
			list_Tintuc.clear();
		}

		if (adapter == null) {
			adapter = new NewsAdapter(getActivity(),
					android.R.layout.simple_list_item_1, list_Tintuc);
			adapter.addCondition(new Condition<NewsItem>() {

				@Override
				public boolean checkCondition(NewsItem item) {
					if (item.getDesc().contains(filterText)) {
						return true;
					}
					return false;
				}
			});
		}
		lv_NewsBySymbol.setAdapter(adapter);
	}

	private void initialiseListener() {
		lv_NewsBySymbol.setOnTouchListener(new OnTouchListener() {

			int countup;
			int countdown;
			final int SENSITIVITY = 9;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("hhhhhhhhhhhhhhh", "listviewTouch");
				if (lv_NewsBySymbol.getChildCount() == 0) {
					return true;
				}
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					countup = 0;
					countdown = 0;
					break;
				case MotionEvent.ACTION_MOVE:

					if (lv_NewsBySymbol.getFirstVisiblePosition() == 0
							&& lv_NewsBySymbol.getChildAt(0).getTop() == 0) {
						countup++;
						if (countup > SENSITIVITY) {
							v.getParent().requestDisallowInterceptTouchEvent(
									false);
							return false;
						}
					}
					if (lv_NewsBySymbol.getLastVisiblePosition() == lv_NewsBySymbol
							.getCount() - 1
							&& lv_NewsBySymbol.getChildAt(
									lv_NewsBySymbol.getLastVisiblePosition()
											- lv_NewsBySymbol
													.getFirstVisiblePosition())
									.getBottom() == lv_NewsBySymbol
									.getMeasuredHeight()) {
						countdown++;
						if (countdown > SENSITIVITY) {
							v.getParent().requestDisallowInterceptTouchEvent(
									false);
							return false;
						}
					}

					break;
				default:
					break;
				}
				v.getParent().requestDisallowInterceptTouchEvent(true);
				v.onTouchEvent(event);
				return true;
			}
		});
		lv_NewsBySymbol.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				boolean isNotLastNews = false;
				try {
					isNotLastNews = (list_Tintuc.get(-1 + list_Tintuc.size())).Id != lastNewID;
				} catch (IndexOutOfBoundsException e) {
				}
				if ((firstVisibleItem + visibleItemCount == totalItemCount)
						&& (totalItemCount > 0) && isNotLastNews)
					CallGetNextNewsByID();
			}
		});
		lv_NewsBySymbol.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.setItemSelectedPosition(position);
				showNewsDetails(list_Tintuc.get(position));
			}
		});
	}

	public void onResume() {
		super.onResume();
		CallGetAllNewsSymbol();
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
		setBackLogoAction();
	}

	private void CallGetAllNewsSymbol() {

		if (symbol != null) {
			List<String> list_key = new ArrayList<String>();
			List<String> list_value = new ArrayList<String>();
			{
				list_key.add("link");
				list_value
						.add(getStringResource(R.string.controller_GetAllNewsSymbol));
			}
			{
				list_key.add("lang");
				list_value.add(AppData.language);
			}
			{
				list_key.add("symbol");
				list_value.add(symbol);
			}
			StaticObjectManager.connectServer.callHttpPostService(
					GETALLNEWSSYMBOL, this, list_key, list_value);
		}

	}

	private void CallGetNextNewsByID() {
		String newId = list_Tintuc.get(-1 + list_Tintuc.size()).Id;
		try {

			ArrayList<String> list_key = new ArrayList<String>();
			ArrayList<String> list_value = new ArrayList<String>();
			{
				list_key.add("giang");
				list_value
						.add(getStringResource(R.string.controller_GetNextNewsByID));
			}
			{
				list_key.add("symbol");
				list_value.add(symbol);
			}
			{
				list_key.add("lang");
				list_value.add(AppData.language);
			}
			{
				list_key.add("node");
				list_value.add(newId);
			}
			StaticObjectManager.connectServer.callHttpPostService(
					GETNEXTNEWSBYID, this, list_key, list_value);
			lastNewID = newId;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		switch (key) {
		case GETALLNEWSSYMBOL:
			if (rObj.obj != null) {
				list_Tintuc.clear();
				list_Tintuc.addAll((List<NewsItem>) rObj.obj);
				adapter.notifyDataSetChanged();
				if (DeviceProperties.isTablet) {
					if (0 < adapter.getCount()) {
						lv_NewsBySymbol.performItemClick(
								adapter.getView(0, null, null), 0,
								adapter.getItemId(0));
					} else {
						mainActivity.sendArgumentToFragment(
								NewsBySymbolDetail.class.getName(), null);
					}
				}
			}
			break;
		case GETNEXTNEWSBYID:
			if (rObj.obj != null) {
				list_Tintuc.addAll((List<NewsItem>) rObj.obj);
				adapter.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * Truy�?n vào mã chứng khoán cần lấy tin tức
	 * 
	 * @param symbol
	 */
	public void receiverParameter(String symbol) {
		this.symbol = symbol;
		if (this.isResumed()) {
			CallGetAllNewsSymbol();
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		CallGetAllNewsSymbol();
	}

	public void notifyFilter() {
		if (DeviceProperties.isTablet) {
			adapter.notifyDataSetChanged();
			if (0 < adapter.getCount()) {
				lv_NewsBySymbol
						.performItemClick(adapter.getView(0, null, null), 0,
								adapter.getItemId(0));
			} else {
				mainActivity.sendArgumentToFragment(
						NewsBySymbolDetail.class.getName(), null);
			}
		}
	}

	private void showNewsDetails(NewsItem selectedItem) {
		selectedItem.setShortNews(true);
		mainActivity.sendArgumentToFragment(NewsBySymbolDetail.class.getName(),
				selectedItem);
		if (DeviceProperties.isTablet) {

		} else {
			mainActivity.navigateFragment(NewsBySymbolDetail.class.getName(),
					selectedItem);
		}
	}

	public void setFilterText(String filterText) {
		this.filterText = filterText;
	}
}
