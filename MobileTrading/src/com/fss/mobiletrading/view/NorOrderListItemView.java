package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.util.Log;
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

import com.fss.mobiletrading.adapter.Solenh_Adapter;
import com.fss.mobiletrading.adapter.Solenh_Adapter.OnSelectedCancelOrderItem;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.object.SolenhItem;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;
import com.msbuat.mobiletrading.design.SelectorImageView;

public class NorOrderListItemView extends LinearLayout {

	HorizontalScrollView scrollview;
	final int DELTA_MAX = 100;
	final int DELTA_MIN = 5;
	VelocityTracker mVelocityTracker;
	TextView mTextViewWidth;
	SolenhItem solenhItem;
	SimpleAction mItemClickAction;
	SimpleAction mBuyClickAction;
	SimpleAction mSellClickAction;
	SimpleAction mCancelClickAction;
	SimpleAction mItemLongClickAction;
	SimpleAction mAmendClickAction;

	Solenh_Adapter adapter;

	Button btn_cancel;
	TextView tv_CustodyCd;
	TextView tv_Side;
	TextView tv_MaCK;
	TextView tv_SoLuong;
	TextView tv_Gia;
	TextView tv_TrangThai;
	Button btn_buy;
	Button btn_sell;
	OnSelectedCancelOrderItem onSelectedCancelOrderItem;

	/**
	 * only tablet
	 */
	Button btn_amend;
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
	TextView tv_MatchedPrice;
	/**
	 * only tablet
	 */
	TextView tv_PriceType;
	/**
	 * only tablet
	 */
	SelectorImageView isSelectCancel;

	public NorOrderListItemView(Context context, Solenh_Adapter adapter,
			SimpleAction mClickAction, SimpleAction mBuyAction,
			SimpleAction mSellAction, SimpleAction mCancelAction,
			SimpleAction mLongClickAction) {
		super(context);

		this.adapter = adapter;
		mItemClickAction = mClickAction;
		mItemLongClickAction = mLongClickAction;
		mBuyClickAction = mBuyAction;
		mSellClickAction = mSellAction;
		mCancelClickAction = mCancelAction;
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.normalorderlist_item, this);

		tv_CustodyCd = ((TextView) findViewById(R.id.text_solenh_item_CustodyCd));
		tv_MaCK = ((TextView) findViewById(R.id.text_solenh_item_tv_MaCK));
		tv_Side = ((TextView) findViewById(R.id.text_solenh_item_tv_side));
		tv_SoLuong = ((TextView) findViewById(R.id.text_solenh_item_tv_SoLuong));
		tv_Gia = ((TextView) findViewById(R.id.text_solenh_item_tv_Gia));
		tv_TrangThai = ((TextView) findViewById(R.id.text_solenh_item_tv_TrangThai));
		mTextViewWidth = ((TextView) findViewById(R.id.text_solenh_item_contentwidth));
		scrollview = ((HorizontalScrollView) findViewById(R.id.horiscrollview_solenh_item));
		btn_buy = (Button) (findViewById(R.id.btn_solenh_item_buy));
		btn_sell = (Button) (findViewById(R.id.btn_solenh_item_sell));
		btn_amend = (Button) (findViewById(R.id.btn_solenh_item_amend));
		tv_Afacctno = ((TextView) findViewById(R.id.text_solenh_item_afacctno));
		tv_MatchedQtty = ((TextView) findViewById(R.id.text_solenh_item_matchedqtty));
		tv_MatchedPrice = ((TextView) findViewById(R.id.text_solenh_item_matchedprice));
		tv_PriceType = ((TextView) findViewById(R.id.text_solenh_item_pricetype));
		btn_cancel = (Button) (findViewById(R.id.btn_solenh_item_cancel));
		isSelectCancel = (SelectorImageView) (findViewById(R.id.img_nororderlist_item_iscancel));

		if (DeviceProperties.isTablet) {
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mCancelClickAction != null) {
						mCancelClickAction.performAction(solenhItem);
					}
				}
			});
			btn_amend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mAmendClickAction != null) {
						mAmendClickAction.performAction(solenhItem);
					}
				}
			});
			isSelectCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					isSelectCancel.setActivated(!isSelectCancel.isActivated());
					if (onSelectedCancelOrderItem != null) {
						onSelectedCancelOrderItem.onSelected(
								isSelectCancel.isActivated(),
								solenhItem.OrderId);
					}
				}
			});
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
					Log.i("hhhhhhhhhhhhhhhhh", "SolenhItemView touch");
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
							NorOrderListItemView.this.adapter.isUpdate = false;
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
			btn_buy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mBuyClickAction != null) {
						mBuyClickAction.performAction(solenhItem);
						scrollLeft();
					}
				}
			});
			btn_sell.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mSellClickAction != null) {
						mSellClickAction.performAction(solenhItem);
						scrollLeft();
					}
				}
			});
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mCancelClickAction != null) {
						mCancelClickAction.performAction(solenhItem);
						scrollLeft();
					}
				}
			});
		}
	}

	public void setView(SolenhItem item, boolean isselectcancel) {
		if (DeviceProperties.isTablet) {
			isSelectCancel.setActivated(isselectcancel);
		}
		if (item == solenhItem) {
			return;
		}

		this.solenhItem = item;
		tv_MaCK.setText(item.Symbol);
		tv_SoLuong.setText(Common.formatAmount(item.Qtty));
		tv_Gia.setText(item.Price);
		tv_TrangThai.setText(item.Status);
		if (DeviceProperties.isTablet) {
			tv_CustodyCd.setText(item.CustodyCd);
			tv_Afacctno.setText(item.AfAcctno);
			tv_MatchedQtty.setText(Common.formatAmount(item.Matched));
			tv_MatchedPrice.setText(item.MatchedPrice);
			tv_PriceType.setText(item.PriceType);
			if (item.Side.equals(PlaceOrder.SIDE_NB)) {
				tv_Side.setText(getResources().getString(R.string.Mua));
				tv_Side.setTextColor(getResources().getColor(R.color.color_Mua));
			} else {
				tv_Side.setTextColor(getResources().getColor(R.color.color_Ban));
				tv_Side.setText(getResources().getString(R.string.Ban));
			}
			if (item.isCancellable.equals(StringConst.TRUE)) {
				btn_cancel.setVisibility(VISIBLE);
			} else {
				btn_cancel.setVisibility(INVISIBLE);
			}
			if (item.isModifiable.equals(StringConst.TRUE)) {
				btn_amend.setVisibility(VISIBLE);
			} else {
				btn_amend.setVisibility(INVISIBLE);
			}
		} else {
			tv_CustodyCd.setText(item.CustodyCd + "_" + item.AfAcctno);
			if (item.Side.equals(PlaceOrder.SIDE_NB)) {
				// tv_MaCK.setTextColor(getResources().getColor(R.color.color_Mua));
				tv_Side.setText(getResources().getString(R.string.Mua));
				tv_Side.setTextColor(getResources().getColor(R.color.color_Mua));
				tv_SoLuong.setTextColor(getResources().getColor(
						R.color.color_Mua));
				tv_Gia.setTextColor(getResources().getColor(R.color.color_Mua));
			} else {
				// tv_MaCK.setTextColor(getResources().getColor(R.color.color_Ban));
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

	public void setOnSelectedCancelOrderItem(
			OnSelectedCancelOrderItem onSelectedCancelOrderItem) {
		this.onSelectedCancelOrderItem = onSelectedCancelOrderItem;
	}

	public void setAmendClickAction(SimpleAction mAmendClickAction) {
		this.mAmendClickAction = mAmendClickAction;
	}
}
