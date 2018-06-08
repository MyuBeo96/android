package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
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

import com.fss.mobiletrading.adapter.SolenhGTC_Adapter;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.object.SolenhItem;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;

public class GTCOrderListItemView extends LinearLayout {

	final int DELTA_MAX = 100;
	final int DELTA_MIN = 5;
	VelocityTracker mVelocityTracker;
	TextView mTextViewWidth;
	HorizontalScrollView scrollview;

	SimpleAction mCancelClickAction;
	SimpleAction mItemClickAction;

	SolenhGTC_Adapter adapter;
	SolenhItem solenhItem;

	TextView tv_CustodyCd;
	TextView tv_Gia;
	TextView tv_MaCK;
	TextView tv_SoLuong;
	TextView tv_TrangThai;
	TextView tv_Side;
	TextView tv_fromDate;
	TextView tv_toDate;
	/**
	 * only tablet
	 */
	TextView tv_Afacctno;
	/**
	 * only tablet
	 */
	TextView tv_MatchedQtty;
	/**
	 * only tablet
	 */
	TextView tv_RemainQtty;
	/**
	 * only tablet
	 */
	Button btn_cancel;

	public GTCOrderListItemView(Context context, SolenhGTC_Adapter adapter,
			SimpleAction mClickAction, SimpleAction mCancelAction) {
		super(context);
		this.adapter = adapter;
		mItemClickAction = mClickAction;
		mCancelClickAction = mCancelAction;

		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.gtcorderlist_item, this);
		tv_CustodyCd = ((TextView) findViewById(R.id.text_solenhgtc_item_CustodyCd));
		tv_MaCK = ((TextView) findViewById(R.id.text_solenhgtc_item_MaCK));
		tv_SoLuong = ((TextView) findViewById(R.id.text_solenhgtc_item_SoLuong));
		tv_Gia = ((TextView) findViewById(R.id.text_solenhgtc_item_Gia));
		tv_Side = ((TextView) findViewById(R.id.text_solenhgtc_item_Side));
		tv_TrangThai = ((TextView) findViewById(R.id.text_solenhgtc_item_TrangThai));
		tv_fromDate = ((TextView) findViewById(R.id.text_solenhgtc_item_fromDate));
		tv_toDate = ((TextView) findViewById(R.id.text_solenhgtc_item_toDate));
		mTextViewWidth = ((TextView) findViewById(R.id.text_solenhgtc_item_contentwidth));
		scrollview = ((HorizontalScrollView) findViewById(R.id.horiscrollview_solenhgtc_item));
		btn_cancel = (Button) findViewById(R.id.btn_solenhgtc_item_cancel);
		tv_Afacctno = (TextView) findViewById(R.id.text_solenhgtc_item_afacctno);
		tv_MatchedQtty = (TextView) findViewById(R.id.text_solenhgtc_item_matchedqtty);
		tv_RemainQtty = (TextView) findViewById(R.id.text_solenhgtc_item_remainqtty);

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mCancelClickAction != null) {
					mCancelClickAction.performAction(solenhItem);
					scrollLeft();
				}
			}
		});
		if (DeviceProperties.isTablet) {

		} else {
			mTextViewWidth
					.setWidth(context.getResources().getDisplayMetrics().widthPixels);
			final int mMinFlingVelocity = ViewConfiguration.get(
					scrollview.getContext()).getScaledMinimumFlingVelocity() * 10;
			final int mMaxFlingVelocity = ViewConfiguration.get(
					scrollview.getContext()).getScaledMaximumFlingVelocity();
			scrollview.setOnTouchListener(new OnTouchListener() {

				float mDownX;
				float mDownY;
				float mUpX;
				boolean isclick = false;

				@Override
				public boolean onTouch(View view, MotionEvent motionEvent) {
					switch (motionEvent.getActionMasked()) {
					case MotionEvent.ACTION_DOWN:
						isclick = true;
						mDownX = motionEvent.getRawX();
						mDownY = motionEvent.getRawY();
						if (mVelocityTracker == null) {
							mVelocityTracker = VelocityTracker.obtain();
						} else {
							mVelocityTracker.clear();
						}
						mVelocityTracker.addMovement(motionEvent);
						// check longclick
						// handler.postDelayed(mLongPressed, 1000);
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

						if (Math.abs(mUpX - mDownX) < DELTA_MIN && isclick) {
							if (scrollview.getScrollX() == 0) {
								mItemClickAction.performAction(solenhItem);
							} else {
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
							GTCOrderListItemView.this.adapter.isUpdate = false;
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
		}
	}

	public void setView(SolenhItem item) {
		this.solenhItem = item;
		tv_MaCK.setText(item.Symbol);
		tv_SoLuong.setText(Common.formatAmount(item.Qtty));
		tv_Gia.setText(item.Price);
		tv_TrangThai.setText(item.Status);
		tv_fromDate.setText(item.fromDate);
		tv_toDate.setText(item.toDate);
		if (DeviceProperties.isTablet) {
			tv_CustodyCd.setText(item.CustodyCd);
			tv_Afacctno.setText(item.AfAcctno);
			tv_MatchedQtty.setText(Common.formatAmount(item.Matched));
			tv_RemainQtty.setText(Common.formatAmount(item.RemainQtty));
			if (item.Side.equals(PlaceOrder.SIDE_NB)) {
				tv_Side.setText(getResources().getString(R.string.Mua));
				tv_Side.setTextColor(getResources().getColor(R.color.color_Mua));
			} else {
				tv_Side.setText(getResources().getString(R.string.Ban));
				tv_Side.setTextColor(getResources().getColor(R.color.color_Ban));
			}
			if (item.isCancellable.equals(StringConst.TRUE)) {
				btn_cancel.setVisibility(VISIBLE);
			} else {
				btn_cancel.setVisibility(INVISIBLE);
			}
		} else {
			tv_CustodyCd.setText(item.CustodyCd + "_" + item.AfAcctno);
			if (item.Side.equals(PlaceOrder.SIDE_NB)) {
				tv_Side.setText(getResources().getString(R.string.Mua));
				tv_Side.setTextColor(getResources().getColor(R.color.color_Mua));
				tv_SoLuong.setTextColor(getResources().getColor(
						R.color.color_Mua));
				tv_Gia.setTextColor(getResources().getColor(R.color.color_Mua));
			} else {
				tv_Side.setText(getResources().getString(R.string.Ban));
				tv_Side.setTextColor(getResources().getColor(R.color.color_Ban));
				tv_SoLuong.setTextColor(getResources().getColor(
						R.color.color_Ban));
				tv_Gia.setTextColor(getResources().getColor(R.color.color_Ban));
			}
		}
	}

	public void scrollLeft() {
		this.adapter.isUpdate = true;
		if (scrollview != null) {
			scrollview.post(new Runnable() {

				@Override
				public void run() {
					scrollview.fullScroll(ScrollView.FOCUS_LEFT);
				}
			});
		}
	}

	public void scrollRight() {
		if (scrollview != null) {
			scrollview.post(new Runnable() {

				@Override
				public void run() {
					scrollview.fullScroll(ScrollView.FOCUS_RIGHT);
				}
			});
		}
	}
}