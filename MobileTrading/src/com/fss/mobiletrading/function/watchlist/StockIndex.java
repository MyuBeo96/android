package com.fss.mobiletrading.function.watchlist;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.connector.RequestRealtime;
import com.fss.mobiletrading.connector.RequestRealtime.MyRequest;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.placeorder.OrderSetParams;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.StockDetailsItem;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MSTradeAppConfig;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.TextViewHightLight;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class StockIndex extends AbstractFragment {

	private final int UPDATE_INTERVAL = 3000;
	private final int HIGHLIGHT_INTERVAL = 1000;
	String symbol;
	public static final String STOCKDETAILS = "StockDetailsService#STOCKDETAILS";
	public static final String STOCKDETAILSREALTIME = "StockDetailsService#STOCKDETAILSREALTIME";
	Button btn_bid1;
	Button btn_bid2;
	Button btn_bid3;
	ImageView imgview_kyhieu;
	Button btn_offer1;
	Button btn_offer2;
	Button btn_offer3;
	public StockDetailObservable stockDetail;
	/**
	 * only tablet
	 */
	TextView tv_symbol;
	TextViewHightLight tv_Bid1;
	TextViewHightLight tv_Bid2;
	TextViewHightLight tv_Bid3;
	TextViewHightLight tv_BidPrice1;
	TextViewHightLight tv_BidPrice2;
	TextViewHightLight tv_BidPrice3;
	TextViewHightLight tv_Change;
	TextViewHightLight tv_ClosePrice;
	TextViewHightLight tv_Percent;
	TextView tv_Company;
	TextViewHightLight tv_Offer1;
	TextViewHightLight tv_Offer2;
	TextViewHightLight tv_Offer3;
	TextViewHightLight tv_OfferPrice1;
	TextViewHightLight tv_OfferPrice2;
	TextViewHightLight tv_OfferPrice3;
	RelativeLayout lay_bid1;
	RelativeLayout lay_bid2;
	RelativeLayout lay_bid3;
	RelativeLayout lay_offer1;
	RelativeLayout lay_offer2;
	RelativeLayout lay_offer3;

	TextViewHightLight tv_Ceil;
	TextViewHightLight tv_Ref;
	TextViewHightLight tv_Floor;
	TextViewHightLight tv_Highest;
	TextViewHightLight tv_Average1;
	TextViewHightLight tv_Lowest;
	TextViewHightLight tv_KhoiLuong;
	TextViewHightLight tv_TongKhoiLuong;
	TextViewHightLight tv_foreignRemain;
	TextViewHightLight tv_Average;
	TextViewHightLight tv_BangGiaCT_NNMua;
	TextViewHightLight tv_BangGiaCT_NNban;
	RelativeLayout lay_percentBidOff;
	TextView tv_market;

	TextView tv_ratioBidVol;
	TextView tv_ratioOffVol;
	View v_ratioBidVol;

	Runnable clearHighLight;
	RequestRealtime requestRealtime;
	String lastSeq = StringConst.EMPTY;

	public static StockIndex newInstance(MainActivity mainActivity) {

		StockIndex self = new StockIndex();
		self.mainActivity = mainActivity;
		// self.TITLE = mainActivity.getStringResource(R.string.SymbolDetails);
		return self;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		clearHighLight = new Runnable() {

			@Override
			public void run() {
				clearHightLight();
			}
		};

		stockDetail = new StockDetailObservable();
		requestRealtime = new RequestRealtime(RequestRealtime.MODE_DELAY,
				UPDATE_INTERVAL);
		requestRealtime.setRequest(new MyRequest() {

			@Override
			public void execute() {
				if (lastSeq.length() == 0) {
					isReceivedResponse(STOCKDETAILSREALTIME);
					return;
				}
				boolean rs = CallStockDetails(lastSeq, symbol, StockIndex.this,
						STOCKDETAILSREALTIME);
				if (!rs) {
					clearView();
				}
			}
		});
	}

	protected int getLayout() {
		return R.layout.stockinfo_index;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View view = paramLayoutInflater.inflate(getLayout(), paramViewGroup,
				false);
		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}
		tv_symbol = (TextView) view.findViewById(R.id.text_BangGiaCT_Symbol);
		tv_Company = ((TextView) view.findViewById(R.id.text_BangGiaCT_TenCty));
		tv_ClosePrice = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_GiaKhopCuoi));
		tv_Change = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_Change));
		tv_Percent = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_Percent));
		tv_KhoiLuong = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_khoiluong));
		tv_TongKhoiLuong = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_tongkhoiluong));
		tv_foreignRemain = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_foreignRemain));
		tv_Ceil = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_Tran));
		tv_Floor = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_San));
		tv_Ref = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_ThamChieu));
		tv_Highest = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_CaoNhat));
		tv_Lowest = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_ThapNhat));
		tv_Average = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_Average));
		tv_market = ((TextView) view
				.findViewById(R.id.text_stockinfo_index_market));
		tv_BangGiaCT_NNban = (TextViewHightLight) view.findViewById(R.id.text_BangGiaCT_NNBan);
		tv_BangGiaCT_NNMua = (TextViewHightLight) view.findViewById(R.id.text_BangGiaCT_NNMua);
		tv_Bid1 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_Bid1));
		tv_Bid2 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_Bid2));
		tv_Bid3 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_Bid3));
		tv_BidPrice1 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_BidPrice1));
		tv_BidPrice2 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_BidPrice2));
		tv_BidPrice3 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_BidPrice3));
		tv_Offer1 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_Offer1));
		tv_Offer2 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_Offer2));
		tv_Offer3 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_Offer3));
		tv_OfferPrice1 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_OfferPrice1));
		tv_OfferPrice2 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_OfferPrice2));
		tv_OfferPrice3 = ((TextViewHightLight) view
				.findViewById(R.id.text_BangGiaCT_OfferPrice3));
		btn_bid1 = (Button) view.findViewById(R.id.btn_stockitem_index_bid1);
		btn_bid2 = (Button) view.findViewById(R.id.btn_stockitem_index_bid2);
		btn_bid3 = (Button) view.findViewById(R.id.btn_stockitem_index_bid3);
		btn_offer1 = (Button) view
				.findViewById(R.id.btn_stockitem_index_offer1);
		btn_offer2 = (Button) view
				.findViewById(R.id.btn_stockitem_index_offer2);
		btn_offer3 = (Button) view
				.findViewById(R.id.btn_stockitem_index_offer3);
		imgview_kyhieu = ((ImageView) view
				.findViewById(R.id.Img_BangGiaCT_KyHieu));

		lay_bid1 = (RelativeLayout) view
				.findViewById(R.id.lay_stockinfo_index_bid1);
		lay_bid2 = (RelativeLayout) view
				.findViewById(R.id.lay_stockinfo_index_bid2);
		lay_bid3 = (RelativeLayout) view
				.findViewById(R.id.lay_stockinfo_index_bid3);
		lay_offer1 = (RelativeLayout) view
				.findViewById(R.id.lay_stockinfo_index_offer1);
		lay_offer2 = (RelativeLayout) view
				.findViewById(R.id.lay_stockinfo_index_offer2);
		lay_offer3 = (RelativeLayout) view
				.findViewById(R.id.lay_stockinfo_index_offer3);
		lay_percentBidOff = (RelativeLayout) view
				.findViewById(R.id.lay_percentBidOff);
		tv_ratioBidVol = (TextView) view.findViewById(R.id.text_bidpercent);
		tv_ratioOffVol = (TextView) view.findViewById(R.id.text_offpercent);
		v_ratioBidVol = view.findViewById(R.id.view_bidpercent);
		Common.setupUI(view.findViewById(R.id.stockinfo_index), mainActivity);
		initialise();
		initialiseListener();
		return view;
	}

	protected void initialise() {
		if (isShowLayoutPercentBidOff) {
			lay_percentBidOff.setVisibility(View.VISIBLE);
		} else {
			lay_percentBidOff.setVisibility(View.GONE);
		}

	}

	protected void initialiseListener() {
		btn_bid1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_BidPrice1 != null
							&& tv_BidPrice1.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NS,
								tv_BidPrice1.getText().toString(), null));

					}
				}
			}
		});
		btn_bid2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_BidPrice2 != null
							&& tv_BidPrice2.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NS,
								tv_BidPrice2.getText().toString(), null));

					}
				}
			}
		});
		btn_bid3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_BidPrice3 != null
							&& tv_BidPrice3.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NS,
								tv_BidPrice3.getText().toString(), null));

					}
				}
			}
		});
		btn_offer1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_OfferPrice1 != null
							&& tv_OfferPrice1.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NB,
								tv_OfferPrice1.getText().toString(), null));

					}
				}
			}
		});
		btn_offer2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_OfferPrice2 != null
							&& tv_OfferPrice2.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NB,
								tv_OfferPrice2.getText().toString(), null));

					}
				}
			}
		});
		btn_offer3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_OfferPrice3 != null
							&& tv_OfferPrice3.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NB,
								tv_OfferPrice3.getText().toString(), null));

					}
				}
			}
		});

		lay_bid1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_BidPrice1 != null
							&& tv_BidPrice1.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NS,
								tv_BidPrice1.getText().toString(), null));

					}
				}
			}
		});
		lay_bid2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_BidPrice2 != null
							&& tv_BidPrice2.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NS,
								tv_BidPrice2.getText().toString(), null));

					}
				}
			}
		});
		lay_bid3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_BidPrice3 != null
							&& tv_BidPrice3.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NS,
								tv_BidPrice3.getText().toString(), null));

					}
				}
			}
		});
		lay_offer1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_OfferPrice1 != null
							&& tv_OfferPrice1.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NB,
								tv_OfferPrice1.getText().toString(), null));

					}
				}
			}
		});
		lay_offer2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_OfferPrice2 != null
							&& tv_OfferPrice2.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NB,
								tv_OfferPrice2.getText().toString(), null));

					}
				}
			}
		});
		lay_offer3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StockDetailsItem item = stockDetail.getStockDetailsItem();
				if (item != null) {
					if (tv_OfferPrice3 != null
							&& tv_OfferPrice3.getText().toString().length() > 0) {
						mainActivity.setOrderToPlaceOrder(new OrderSetParams(
								null, null, item.symbol, PlaceOrder.SIDE_NB,
								tv_OfferPrice3.getText().toString(), null));

					}
				}
			}
		});

	}

	public void onResume() {
		super.onResume();
		boolean rs = CallStockDetails(lastSeq, symbol, StockIndex.this,
				STOCKDETAILS);
		if (!rs) {
			clearView();
		}
		if (symbol != null) {
			requestRealtime.run();
		}

	}

	@Override
	public void onShowed() {
		super.onShowed();
	}

	public void onPause() {
		super.onPause();
		requestRealtime.stop();
	}

	@Override
	public void onHide() {
		super.onHide();
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
	}

	public static boolean CallStockDetails(String lastSeq, String symbol,
			INotifier notifier, String key) {
		if (symbol != null && symbol.length() > 0) {
			List<String> list_key = new ArrayList<String>();
			List<String> list_value = new ArrayList<String>();
			{
				list_key.add("link");
				list_value.add(MSTradeAppConfig.controller_StockDetails);
			}
			{
				list_key.add("lastSeq");
				list_value.add(lastSeq);
			}
			{
				list_key.add("chooseStock");
				list_value.add(symbol);
			}
			StaticObjectManager.connectServer.callHttpPostService(key,
					notifier, list_key, list_value);
			return true;
		} else {
			return false;
		}
	}

	private void clearHightLight() {

		tv_ClosePrice.setText(tv_ClosePrice.getText());
		tv_Change.setText(tv_Change.getText());
		tv_Percent.setText(tv_Percent.getText());

		tv_KhoiLuong.setText(tv_KhoiLuong.getText());
		tv_TongKhoiLuong.setText(tv_TongKhoiLuong.getText());
		tv_foreignRemain.setText(tv_foreignRemain.getText());

		tv_Ceil.setText(tv_Ceil.getText());
		tv_Floor.setText(tv_Floor.getText());
		tv_Ref.setText(tv_Ref.getText());

		tv_Highest.setText(tv_Highest.getText());
		tv_Lowest.setText(tv_Lowest.getText());
		tv_Average.setText(tv_Average.getText());

		tv_Bid1.setText(tv_Bid1.getText());
		tv_Bid2.setText(tv_Bid2.getText());
		tv_Bid3.setText(tv_Bid3.getText());
		tv_BidPrice1.setText(tv_BidPrice1.getText());
		tv_BidPrice2.setText(tv_BidPrice2.getText());
		tv_BidPrice3.setText(tv_BidPrice3.getText());
		tv_Offer1.setText(tv_Offer1.getText());
		tv_Offer2.setText(tv_Offer2.getText());
		tv_Offer3.setText(tv_Offer3.getText());
		tv_OfferPrice1.setText(tv_OfferPrice1.getText());
		tv_OfferPrice2.setText(tv_OfferPrice2.getText());
		tv_OfferPrice3.setText(tv_OfferPrice3.getText());
		tv_BangGiaCT_NNMua.setText(tv_BangGiaCT_NNMua.getText());
		tv_BangGiaCT_NNban.setText(tv_BangGiaCT_NNban.getText());

	}

	private void displayView(StockDetailsItem item) {
		try {
			int i;
			String floor = null;
			if (item != null) {
				if (item.symbol.equals(symbol)) {
					if (WatchList.HNX.equals(item.FloorCode)) {
						floor = getStringResource(R.string.HNX);
					} else if (WatchList.HOSE.equals(item.FloorCode)) {
						floor = getStringResource(R.string.HOSE);
					} else if (WatchList.UPCOM.equals(item.FloorCode)) {
						floor = getStringResource(R.string.UPCOM);
					}

					if (DeviceProperties.isTablet) {
						int ratioBidVol = 50;
						if (item.ratioBidVol != Integer.MIN_VALUE) {
							ratioBidVol = item.ratioBidVol;
						}
						tv_ratioBidVol.setText(ratioBidVol
								+ StringConst.CHARACTER_PERCENT);
						tv_ratioOffVol.setText((100 - ratioBidVol)
								+ StringConst.CHARACTER_PERCENT);
						v_ratioBidVol
								.setLayoutParams(new LinearLayout.LayoutParams(
										0, LayoutParams.MATCH_PARENT,
										ratioBidVol));
						tv_symbol.setText(item.symbol);
						tv_market.setText(floor);
						tv_Company.setText(item.FullName);
					} else {
						tv_Company.setText(floor + "/" + item.FullName);
					}

					tv_ClosePrice.setText(item.closePrice);
					tv_Change.setText(item.change);
					tv_Percent.setText(item.changePercent);
					tv_KhoiLuong.setText(item.closeVol);
					tv_TongKhoiLuong.setText(item.totalTrading);
					tv_foreignRemain.setText(item.foreignRemain);
					tv_Ceil.setText(item.ceiling);
					tv_Floor.setText(item.floor);
					tv_Ref.setText(item.reference);
					tv_Highest.setText(item.high);
					tv_Lowest.setText(item.low);
					tv_Average.setText(item.averagePrice);
					tv_Highest.setTextColor(getResources().getColor(
							Common.getColor(item.high, item.ceiling,
									item.floor, item.reference)));
					tv_Lowest.setTextColor(getResources().getColor(
							Common.getColor(item.low, item.ceiling, item.floor,
									item.reference)));
					tv_Average.setTextColor(getResources().getColor(
							Common.getColor(item.averagePrice, item.ceiling,
									item.floor, item.reference)));
					tv_BangGiaCT_NNban.setText(item.foreignSell);
					tv_BangGiaCT_NNMua.setText(item.foreignBuy);
					// tv_average.setContentColor(getResources().getColor(
					// Common.getColor(stockDetailsItem.averagePrice,
					// stockDetailsItem.ceiling, stockDetailsItem.floor,
					// stockDetailsItem.reference)));

					tv_Bid1.setText(item.bidVol1);
					tv_Bid2.setText(item.bidVol2);
					tv_Bid3.setText(item.bidVol3);
					tv_BidPrice1.setText(item.bidPrice1);
					tv_BidPrice2.setText(item.bidPrice2);
					tv_BidPrice3.setText(item.bidPrice3);
					tv_Offer1.setText(item.offerVol1);
					tv_Offer2.setText(item.offerVol2);
					tv_Offer3.setText(item.offerVol3);
					tv_OfferPrice1.setText(item.offerPrice1);
					tv_OfferPrice2.setText(item.offerPrice2);
					tv_OfferPrice3.setText(item.offerPrice3);
					tv_Bid1.setTextColor(getResources().getColor(
							Common.getColor(item.bidPrice1, item.ceiling,
									item.floor, item.reference)));
					tv_BidPrice1.setTextColor(getResources().getColor(
							Common.getColor(item.bidPrice1, item.ceiling,
									item.floor, item.reference)));
					tv_Bid2.setTextColor(getResources().getColor(
							Common.getColor(item.bidPrice2, item.ceiling,
									item.floor, item.reference)));
					tv_BidPrice2.setTextColor(getResources().getColor(
							Common.getColor(item.bidPrice2, item.ceiling,
									item.floor, item.reference)));
					tv_Bid3.setTextColor(getResources().getColor(
							Common.getColor(item.bidPrice3, item.ceiling,
									item.floor, item.reference)));
					tv_BidPrice3.setTextColor(getResources().getColor(
							Common.getColor(item.bidPrice3, item.ceiling,
									item.floor, item.reference)));
					tv_Offer1.setTextColor(getResources().getColor(
							Common.getColor(item.offerPrice1, item.ceiling,
									item.floor, item.reference)));
					tv_OfferPrice1.setTextColor(getResources().getColor(
							Common.getColor(item.offerPrice1, item.ceiling,
									item.floor, item.reference)));
					tv_Offer2.setTextColor(getResources().getColor(
							Common.getColor(item.offerPrice2, item.ceiling,
									item.floor, item.reference)));
					tv_OfferPrice2.setTextColor(getResources().getColor(
							Common.getColor(item.offerPrice2, item.ceiling,
									item.floor, item.reference)));
					tv_Offer3.setTextColor(getResources().getColor(
							Common.getColor(item.offerPrice3, item.ceiling,
									item.floor, item.reference)));
					tv_OfferPrice3.setTextColor(getResources().getColor(
							Common.getColor(item.offerPrice3, item.ceiling,
									item.floor, item.reference)));

					i = Common.getColor(item.closePrice, item.ceiling,
							item.floor, item.reference);

					tv_Change.setTextColor(getResources().getColor(i));
					tv_Percent.setTextColor(getResources().getColor(i));
					tv_ClosePrice.setTextColor(getResources().getColor(i));
					tv_KhoiLuong.setTextColor(getResources().getColor(i));
					if (DeviceProperties.isTablet) {
						tv_symbol.setTextColor(getResources().getColor(i));
					}

					if ((i == R.color.color_Mua) || (i == R.color.color_Tran)) {
						imgview_kyhieu.setImageResource(R.drawable.ic_up);
					} else {
						if (i == R.color.color_ThamChieu) {
							imgview_kyhieu
									.setImageResource(R.drawable.ic_yellow);
						} else {
							imgview_kyhieu
									.setImageResource(R.drawable.ic_downred);
						}
					}

				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	private void clearView() {
		mainActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (DeviceProperties.isTablet) {
					tv_symbol.setText(StringConst.EMPTY);
					tv_Floor.setText(StringConst.EMPTY);
				}
				tv_Company.setText(StringConst.EMPTY);
				tv_ClosePrice.setText(StringConst.EMPTY);
				tv_Change.setText(StringConst.EMPTY);
				tv_Percent.setText(StringConst.EMPTY);
				tv_KhoiLuong.setText(StringConst.EMPTY);
				tv_TongKhoiLuong.setText(StringConst.EMPTY);
				tv_foreignRemain.setText(StringConst.EMPTY);
				tv_Ceil.setText(StringConst.EMPTY);
				tv_Floor.setText(StringConst.EMPTY);
				tv_Ref.setText(StringConst.EMPTY);
				tv_Highest.setText(StringConst.EMPTY);
				tv_Lowest.setText(StringConst.EMPTY);
				tv_Bid1.setText(StringConst.EMPTY);
				tv_Bid2.setText(StringConst.EMPTY);
				tv_Bid3.setText(StringConst.EMPTY);
				tv_BidPrice1.setText(StringConst.EMPTY);
				tv_BidPrice2.setText(StringConst.EMPTY);
				tv_BidPrice3.setText(StringConst.EMPTY);
				tv_Offer1.setText(StringConst.EMPTY);
				tv_Offer2.setText(StringConst.EMPTY);
				tv_Offer3.setText(StringConst.EMPTY);
				tv_OfferPrice1.setText(StringConst.EMPTY);
				tv_OfferPrice2.setText(StringConst.EMPTY);
				tv_OfferPrice3.setText(StringConst.EMPTY);
				tv_Average.setText(StringConst.EMPTY);
				tv_BangGiaCT_NNMua.setText(StringConst.EMPTY);
				tv_BangGiaCT_NNMua.setText(StringConst.EMPTY);
				// imgview_kyhieu.setImageResource(0);
			}
		});
	}

	@Override
	public void refresh() {
		super.refresh();
		symbol = StringConst.EMPTY;
		clearView();
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case STOCKDETAILS:
			if (rObj.obj != null) {
				StockDetailsItem stockDetailsItem = ((StockDetailsItem) rObj.obj);
				if (!lastSeq.equals(stockDetailsItem.LS)) {
					displayView(stockDetailsItem);
					if (lastSeq.length() == 0 || lastSeq.equals("0")) {
						stockDetail.setNewData(true);
					} else {
						stockDetail.setNewData(false);
					}
					stockDetail.setStockDetailsItem(stockDetailsItem);
					lastSeq = stockDetailsItem.LS;
				}
			}
			break;
		case STOCKDETAILSREALTIME:
			if (rObj.obj != null) {
				StockDetailsItem stockDetailsItem = ((StockDetailsItem) rObj.obj);
				if (symbol.equals(stockDetailsItem.symbol)) {
					if (!lastSeq.equals(stockDetailsItem.LS)) {
						displayView(stockDetailsItem);
						if (lastSeq.length() == 0 || lastSeq.equals("0")) {
							stockDetail.setNewData(true);
						} else {
							stockDetail.setNewData(false);
						}
						stockDetail.setStockDetailsItem(stockDetailsItem);
						lastSeq = stockDetailsItem.LS;
					}
				}
			}
			break;

		default:
			break;
		}
	}

	final Handler handler = new Handler();

	@Override
	protected void isReceivedResponse(String key) {
		super.isReceivedResponse(key);
		switch (key) {
		case STOCKDETAILS:
			handler.postDelayed(clearHighLight, HIGHLIGHT_INTERVAL);
			break;
		case STOCKDETAILSREALTIME:
			handler.postDelayed(clearHighLight, HIGHLIGHT_INTERVAL);
			requestRealtime.trigger();
			break;

		default:
			break;
		}
	}

	@Override
	protected void processNull(String key) {

		super.processNull(key);

	}

	/**
	 * truyền vào mã chứng khoán để xem thông tin về mã
	 * 
	 * @param symbol
	 */
	public void receiverparameter(String symbol) {
		this.symbol = symbol;
		lastSeq = StringConst.EMPTY;
		if (this.isResumed()) {
			boolean rs = CallStockDetails(lastSeq, symbol, this, STOCKDETAILS);
			if (!rs) {
				clearView();
			}
		}
	}

	boolean isShowLayoutPercentBidOff = true;

	public void showLayoutPercentBidOff(boolean isshow) {
		isShowLayoutPercentBidOff = isshow;
	}

	public void addObserver(Observer observer) {
		stockDetail.addObserver(observer);
	}

	public void deleteObserver(Observer observer) {
		stockDetail.deleteObserver(observer);
	}
}
