package com.fss.mobiletrading.function.portfolio;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.consts.StringConst;

public class M_PorfolioItemView extends PorfolioItemView {
	Context context;
	TextView tv_MaCK;
	TextView tv_TongCong;
	TextView tv_Floor;
	TextView tv_StockName;

	TextView tv_BasicPrice;
	TextView tv_GiaVon;
	TextView tv_PL;

	TextView tv_Change;
	TextView tv_ChangePercent;
	TextView tv_GiaTriTT;
	TextView tv_PLpercent;

	TextView mTextViewWidth;
	HorizontalScrollView scrollview;
	Porfolio_Adapter adapter;
	SimpleAction mBuyClickAction;
	SimpleAction mSellClickAction;
	PorfolioItem mPorfolioItem;

	final int DELTA_MAX = 100;
	final int DELTA_MIN = 5;
	VelocityTracker mVelocityTracker;

	public M_PorfolioItemView(Context context, Porfolio_Adapter adapter,
			SimpleAction mBuyAction, SimpleAction mSellAction) {
		super(context);
		this.context = context;
		this.adapter = adapter;
		this.mBuyClickAction = mBuyAction;
		this.mSellClickAction = mSellAction;
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.porfolio_item, this);
		tv_MaCK = ((TextView) findViewById(R.id.text_dmck_item_MaCk));
		tv_TongCong = ((TextView) findViewById(R.id.text_dmck_item_HienCo));
		tv_Floor = ((TextView) findViewById(R.id.text_dmck_item_floor));
		tv_StockName = ((TextView) findViewById(R.id.text_dmck_item_symbolname));

		tv_BasicPrice = ((TextView) findViewById(R.id.text_dmck_item_MarketPrice));
		tv_GiaVon = ((TextView) findViewById(R.id.text_dmck_item_costprice));
		tv_PL = ((TextView) findViewById(R.id.text_dmck_item_TotalPL));

		tv_Change = ((TextView) findViewById(R.id.text_dmck_item_Change));
		tv_PLpercent = ((TextView) findViewById(R.id.text_dmck_item_TotalPLPercent));
		tv_GiaTriTT = ((TextView) findViewById(R.id.text_dmck_item_GiaTriTT));
		tv_ChangePercent = ((TextView) findViewById(R.id.text_dmck_item_ChangePercent));

		mTextViewWidth = ((TextView) findViewById(R.id.text_porfolioitem_contentwidth));
		scrollview = ((HorizontalScrollView) findViewById(R.id.horiscrollview_porfolioitem));

		mTextViewWidth
				.setWidth(context.getResources().getDisplayMetrics().widthPixels
						- getResources().getDimensionPixelSize(
								R.dimen.m_paddingLeft) * 2);
		final int mMinFlingVelocity = ViewConfiguration.get(
				scrollview.getContext()).getScaledMinimumFlingVelocity() * 10;
		final int mMaxFlingVelocity = ViewConfiguration.get(
				scrollview.getContext()).getScaledMaximumFlingVelocity();

		scrollview.setOnTouchListener(new OnTouchListener() {

			float mDownX;
			float mDownY;
			float mUpX;

			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {

				switch (motionEvent.getActionMasked()) {
				case MotionEvent.ACTION_DOWN:
					mDownX = motionEvent.getRawX();
					mDownY = motionEvent.getRawY();
					if (mVelocityTracker == null) {
						mVelocityTracker = VelocityTracker.obtain();
					} else {
						mVelocityTracker.clear();
					}
					mVelocityTracker.addMovement(motionEvent);
					break;
				case MotionEvent.ACTION_UP:
					if (mVelocityTracker == null) {
						break;
					}
					mVelocityTracker.addMovement(motionEvent);
					mVelocityTracker.computeCurrentVelocity(1000);
					float velocityX = mVelocityTracker.getXVelocity();
					float absVelocityX = Math.abs(velocityX);
					float absVelocityY = Math.abs(mVelocityTracker
							.getYVelocity());
					mUpX = motionEvent.getRawX();

					if (mUpX > mDownX) {
						scrollLeft();
					} else {
						if ((mMinFlingVelocity <= absVelocityX
								&& absVelocityX <= mMaxFlingVelocity && absVelocityX > absVelocityY)
								|| Math.abs(mUpX - mDownX) > DELTA_MAX) {
							scrollRight();
						} else {
							scrollLeft();
						}
					}
					break;
				case MotionEvent.ACTION_MOVE:
					if (mVelocityTracker == null) {
						break;
					}
					mVelocityTracker.addMovement(motionEvent);
					float deltaX = motionEvent.getRawX() - mDownX;
					float deltaY = motionEvent.getRawY() - mDownY;
					if (Math.abs(deltaY) > Math.abs(deltaX)) {
						scrollLeft();
					} else {
						M_PorfolioItemView.this.adapter.isUpdate = false;
					}
					break;
				case MotionEvent.ACTION_CANCEL:
					if (mVelocityTracker == null) {
						break;
					}
					mVelocityTracker.recycle();
					mVelocityTracker = null;

					break;

				default:
					break;
				}
				return false;
			}
		});

		Button btn_buy = (Button) (findViewById(R.id.btn_porfolioitem_buy));
		Button btn_sell = (Button) (findViewById(R.id.btn_porfolioitem_sell));

		btn_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBuyClickAction != null) {
					mBuyClickAction.performAction(mPorfolioItem);
					scrollLeft();
				}
			}
		});
		btn_sell.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSellClickAction != null) {
					mSellClickAction.performAction(mPorfolioItem);
					scrollLeft();
				}
			}
		});
	}

	@Override
	public void setView(PorfolioItem item) {
		scrollview.scrollTo(0, 0);
		if (item == mPorfolioItem) {
			return;
		}
		mPorfolioItem = item;

		tv_MaCK.setText(item.getSYMBOL());
		tv_TongCong.setText(Common.formatAmount(item.getTOTAL()));
		tv_Floor.setText(item.getMARKETCODE());
		tv_StockName.setText(item.getFULLNAME());

		tv_BasicPrice.setText(Common.formatAmount(item.getMARKETPRICE()));
		tv_GiaVon.setText(Common.formatAmount(item.getCOSTVALUE()));
		tv_PLpercent.setText(item.getPERCENT_PL());

		tv_Change.setText(item.getPRICECHANGE());
		tv_ChangePercent.setText(item.getPERCENT_PRICECHANGE() + "%");

		int color0 = Common.getColor(item.getPRICECHANGE());
		tv_Change.setTextColor(color0);
		tv_ChangePercent.setTextColor(color0);

		tv_GiaTriTT.setText(Common.formatAmount(item.getMARKETVALUE()));
		tv_PL.setText(Common.formatAmount(item.getUNREALIZED_PL()));

		int color1 = Common.getColor(item.getPERCENT_PL());
		tv_PLpercent.setTextColor(color1);
		tv_PL.setTextColor(color1);
		tv_GiaTriTT.setTextColor(color1);
		tv_BasicPrice.setTextColor(color1);

	}

	public void scrollLeft() {
		this.adapter.isUpdate = true;
		scrollview.post(new Runnable() {

			@Override
			public void run() {
				scrollview.fullScroll(ScrollView.FOCUS_LEFT);
			}
		});

	}

	public void scrollRight() {
		scrollview.post(new Runnable() {

			@Override
			public void run() {
				scrollview.fullScroll(ScrollView.FOCUS_RIGHT);
			}
		});
	}
}
