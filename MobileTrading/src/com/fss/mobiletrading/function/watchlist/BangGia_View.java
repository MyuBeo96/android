package com.fss.mobiletrading.function.watchlist;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fss.mobiletrading.adapter.BangGia_Adapter;
import com.fss.mobiletrading.common.MTradingColor;
import com.fss.mobiletrading.common.MTradingColor.PriceColor;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.object.BangGia_Item;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;

public class BangGia_View extends LinearLayout {

    private static final int TIMEMIN = 20;
    ImageView imgview_kyhieu;
    TextView tv_Change;
    TextView tv_ClosePrice;
    TextView tv_MaCK;
    TextView tv_Percent;
    TextView tv_San;
    TextView tv_Tran;

    private BangGia_Adapter adapter;
    private SimpleAction clickAction;
    private SimpleAction cancelAction;

    private final int DELTA_MAX = 100;
    private final int DELTA_MIN = 5;
    private VelocityTracker mVelocityTracker;
    private TextView mTextViewWidth;
    private HorizontalScrollView scrollview;
    private BangGia_Item newitem;
    private Button btn_cancel;
    static int transparent = Color.parseColor("#00ffffff");

    public BangGia_View(Context context) {
        super(context);
    }

    public BangGia_View(Context context, BangGia_Adapter adapter,
                        SimpleAction mClickAction, SimpleAction mCancelAction) {
        super(context);
        this.adapter = adapter;
        clickAction = mClickAction;
        cancelAction = mCancelAction;
        ((LayoutInflater) context
                .getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.watchlist_item, this);
        this.tv_MaCK = ((TextView) findViewById(R.id.text_BangGia_item_Symbol));
        this.tv_Change = ((TextView) findViewById(R.id.text_BangGia_item_Change));
        this.tv_Percent = ((TextView) findViewById(R.id.text_BangGia_item_Percent));
        this.tv_Tran = ((TextView) findViewById(R.id.text_BangGia_item_Tran));
        this.tv_San = ((TextView) findViewById(R.id.text_BangGia_item_San));
        this.tv_ClosePrice = ((TextView) findViewById(R.id.text_BangGia_item_ClosePrice));
        this.imgview_kyhieu = ((ImageView) findViewById(R.id.Img_BangGia_item_KyHieu));
        mTextViewWidth = ((TextView) findViewById(R.id.text_banggia_item_contentwidth));
        scrollview = ((HorizontalScrollView) findViewById(R.id.horiscrollview_banggia_item));

        if (DeviceProperties.isTablet) {

        } else {
            final int mMinFlingVelocity = ViewConfiguration.get(
                    scrollview.getContext()).getScaledMinimumFlingVelocity() * 10;
            final int mMaxFlingVelocity = ViewConfiguration.get(
                    scrollview.getContext()).getScaledMaximumFlingVelocity();
            mTextViewWidth
                    .setWidth(context.getResources().getDisplayMetrics().widthPixels);
            btn_cancel = (Button) (findViewById(R.id.btn_banggia_item_cancel));

            btn_cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (cancelAction != null) {
                        cancelAction.performAction(newitem);
                        scrollLeft();
                    }
                }
            });
            scrollview.setOnTouchListener(new OnTouchListener() {

                float mDownX;
                float mDownY;
                float mUpX;
                boolean isclick = false;
                long starttime;

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            starttime = System.currentTimeMillis();
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
                            long endtime = System.currentTimeMillis();
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

                            if (Math.abs(mUpX - mDownX) < DELTA_MIN && isclick
                                    && (endtime - starttime) > TIMEMIN) {
                                if (scrollview.getScrollX() == 0) {
                                    clickAction.performAction(newitem);
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
                                BangGia_View.this.adapter.isUpdate = false;
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

    public void setView(BangGia_Item newitem) {
        if (newitem != null) {
            newitem.parse();

            this.newitem = newitem;
            this.tv_ClosePrice.setText(newitem.ClosePrice);
            this.tv_Change.setText(newitem.Change);
            this.tv_Percent.setText(newitem.Percent + "%");
            this.tv_MaCK.setText(newitem.Symbol);
            this.tv_Tran.setText(newitem.CeilingPrice);
            this.tv_San.setText(newitem.FloorPrice);

            setTextColor(newitem);
            BangGia_Item olditem = newitem.getOldItem();
            if (olditem == null) {
                clearAllHighLight();
            } else {
                setHighLight(olditem, newitem);
            }

            if (!DeviceProperties.isTablet) {
                if (!BangGia_View.this.adapter.isItemScrollable()) {
                    if (btn_cancel.getVisibility() == View.VISIBLE) {
                        btn_cancel.setVisibility(View.GONE);
                    }
                } else {
                    if (btn_cancel.getVisibility() == View.GONE) {
                        btn_cancel.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    protected void setTextColor(BangGia_Item newitem) {

        this.tv_ClosePrice.setTextColor(newitem.ClosePriceColor);
        this.tv_Change.setTextColor(newitem.ChangeColor);
        this.tv_Percent.setTextColor(newitem.PercentColor);
        this.tv_MaCK.setTextColor(newitem.SymbolColor);

        int closePriceColor = newitem.ClosePriceColor;
        if (closePriceColor == PriceColor.hitCeil.getColor()) {
            this.imgview_kyhieu.setImageResource(R.drawable.ic_upceil);
        } else if (closePriceColor == PriceColor.hitFloor.getColor()) {
            this.imgview_kyhieu.setImageResource(R.drawable.ic_downfloor);
        } else if (closePriceColor == PriceColor.gainer.getColor()) {
            this.imgview_kyhieu.setImageResource(R.drawable.ic_up);
        } else if (closePriceColor == PriceColor.loser.getColor()) {
            this.imgview_kyhieu.setImageResource(R.drawable.ic_downred);
        } else {
            this.imgview_kyhieu.setImageResource(R.drawable.ic_yellow);
        }
    }

    protected void setHighLight(BangGia_Item olditem, BangGia_Item newitem) {
        if (newitem != null) {
            if (newitem.ClosePrice != null) {
                // nếu olditem != null và giá trị mới và giá trị cũ khác nhau
                // thì
                // highlight, còn không xóa highlight
                if (olditem.ClosePrice != null
                        && !newitem.ClosePrice.equals(olditem.ClosePrice)) {
                    tv_ClosePrice
                            .setBackgroundColor(MTradingColor.highlight_color);
                } else {
                    tv_ClosePrice.setBackgroundColor(transparent);
                }
            }
            if (newitem.Change != null) {
                // nếu olditem != null và giá trị mới và giá trị cũ khác nhau
                // thì
                // highlight, còn không xóa highlight
                if (olditem.Change != null
                        && !newitem.Change.equals(olditem.Change)) {
                    tv_Change.setBackgroundColor(MTradingColor.highlight_color);
                } else {
                    tv_Change.setBackgroundColor(transparent);
                }
            }
            if (newitem.Percent != null) {
                // nếu olditem != null và giá trị mới và giá trị cũ khác nhau
                // thì
                // highlight, còn không xóa highlight
                if (olditem.Percent != null
                        && !newitem.Percent.equals(olditem.Percent)) {
                    tv_Percent
                            .setBackgroundColor(MTradingColor.highlight_color);
                } else {
                    tv_Percent.setBackgroundColor(transparent);
                }
            }
        }
    }

    protected void clearAllHighLight() {
        this.tv_Change.setBackgroundColor(transparent);
        this.tv_Percent.setBackgroundColor(transparent);
        this.tv_ClosePrice.setBackgroundColor(transparent);
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
        BangGia_View.this.adapter.isUpdate = false;
        scrollview.post(new Runnable() {

            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_RIGHT);
            }
        });
    }
}
