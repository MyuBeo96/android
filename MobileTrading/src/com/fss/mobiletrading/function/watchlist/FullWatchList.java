package com.fss.mobiletrading.function.watchlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fss.mobiletrading.adapter.FullWatchListAdapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.object.BangGia_Item;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.MainActivity_Tablet;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.DeviceProperties;
import com.tcscuat.mobiletrading.design.SearchStockUI;
import com.tcscuat.mobiletrading.design.TabSelector;

import java.util.ArrayList;

public class FullWatchList extends WatchList {

	FullWatchListAdapter adapterBangGia;
	LinearLayout lay_BidOff;
	TextView header_Bid;
	TextView header_Off;
	String[] bidLabel;
	String[] offLabel;
	int count = 0;

	public static FullWatchList newInstance(MainActivity mActivity) {

		FullWatchList self = new FullWatchList();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.FullWatchList);
		return self;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		View view = inflater.inflate(R.layout.fullwatchlist, container, false);
		tabSelector = (TabSelector) view.findViewById(R.id.tab_fullwatchlist);
		// btn_CancelSearch = ((Button) view
		// .findViewById(R.id.btn_banggiafull_HuySearch));
		// edt_SearchSymbol = ((EditText) view
		// .findViewById(R.id.edt_banggiafull_search));
		// lay_Search = ((RelativeLayout) view
		// .findViewById(R.id.lay_banggiafull_Search));
		searchStock = (SearchStockUI) view
				.findViewById(R.id.searchtext_fullwatchlist_all);
		listview_BangGia = ((ListView) view
				.findViewById(R.id.listview_banggiafull));
		lay_BidOff = ((LinearLayout) view
				.findViewById(R.id.lay_banggiafull_bidoff));
		header_Bid = ((TextView) view
				.findViewById(R.id.header_fullwatchlist_bid));
		header_Off = ((TextView) view
				.findViewById(R.id.header_fullwatchlist_off));
		img_addStock = (ImageView) view
				.findViewById(R.id.img_watchlist_addstock);
		img_expand = (ImageView) view.findViewById(R.id.img_watchlist_expand);
		img_searchStock = (ImageView) view
				.findViewById(R.id.img_watchlist_search);
		tv_favname = (TextView) view.findViewById(R.id.text_watchlist_favname);
		btn_openFav = (Button) view.findViewById(R.id.btn_openfav);
		initialise();
		Common.setupUI(view.findViewById(R.id.fullwatchlist), getActivity());
		initialiseListener();
		return view;
	}

	@Override
	public void initialise() {
		if (listItemBangGia == null) {
			listItemBangGia = new ArrayList<BangGia_Item>();
		} else {
			listItemBangGia.clear();
		}
		if (listItemBangGiaClone == null) {
			listItemBangGiaClone = new ArrayList<BangGia_Item>();
		} else {
			listItemBangGiaClone.clear();
		}
		if (adapterBangGia == null) {
			adapterBangGia = new FullWatchListAdapter(getActivity(),
					android.R.layout.simple_list_item_1, listItemBangGiaClone);
		} else {
			adapterBangGia.loadData();
		}

		listview_BangGia.setAdapter(adapterBangGia);
		bidLabel = new String[] {
				getStringResource(R.string.banggia_MuaGia1KL1),
				getStringResource(R.string.banggia_MuaGia2KL2),
				getStringResource(R.string.banggia_MuaGia3KL3) };
		offLabel = new String[] {
				getStringResource(R.string.banggia_BanGia1KL1),
				getStringResource(R.string.banggia_BanGia2KL2),
				getStringResource(R.string.banggia_BanGia3KL3) };

		if (clearHighLightRunable == null) {
			clearHighLightRunable = new Runnable() {

				@Override
				public void run() {
					try {
						clearHighLight();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		}
		if (DeviceProperties.isTablet) {
			searchStock.setVisibleButton(false);
			searchStock.setVisibleClearField(true);
		}
	}

	@Override
	public void initialiseListener() {
		super.initialiseListener();
		listview_BangGia.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (DeviceProperties.isTablet) {
					((MainActivity_Tablet) mainActivity)
							.showFullPriceBoard(false);
				}
				StockItemInformation stockItemInformation = (StockItemInformation) mainActivity.mapFragment
						.get(StockItemInformation.class.getName());
				if (stockItemInformation != null) {
					stockItemInformation.receiverParameter(listItemBangGiaClone
							.get(position).Symbol);
				}
//				StockIndex stockIndex = (StockIndex) mainActivity.mapFragment
//						.get(StockIndex.class.getName());
//				if (stockIndex != null) {
//					stockIndex.receiverparameter(listItemBangGiaClone.get(position).Symbol);
//				}
			}
		});
		if (!DeviceProperties.isTablet) {
			lay_BidOff.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					adapterBangGia.next();
					count++;
					header_Bid.setText(bidLabel[count % 3]);
					header_Off.setText(offLabel[count % 3]);
				}
			});
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void clearHighLight() {
		adapterBangGia.clearHighLight();
	}

	@Override
	protected void uploadChangeItem() {
		filterBySymbol(searchStock.getEditText().getText().toString());
		adapterBangGia.loadData();
	}

	@Override
	public void getArgument(Object obj) {
		super.getArgument(obj);
		if (obj instanceof FavWatchListItem) {
			currentFav = (FavWatchListItem) obj;
		}
	}

	public void notifyNewDataSet() {
		adapterBangGia.loadData();
	}

}
