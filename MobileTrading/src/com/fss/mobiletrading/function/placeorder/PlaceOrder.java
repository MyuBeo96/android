package com.fss.mobiletrading.function.placeorder;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fss.designcontrol.SymbolEdittext;
import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.watchlist.StockIndex;
import com.fss.mobiletrading.keyboard.KBoardPrice;
import com.fss.mobiletrading.keyboard.KBoardQuantity;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.FindStock;
import com.fss.mobiletrading.object.ResultObj;
import com.fss.mobiletrading.object.StockBalanceItem;
import com.fss.mobiletrading.object.StockItem;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.MainActivity_Mobile;
import com.fscuat.mobiletrading.MainActivity_Tablet;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;
import com.fscuat.mobiletrading.design.DialogDate;
import com.fscuat.mobiletrading.design.Edittext_Gia;
import com.fscuat.mobiletrading.design.Edittext_LoaiLenh;
import com.fscuat.mobiletrading.design.Edittext_SoLuong;
import com.fscuat.mobiletrading.design.MyContextMenu;
import com.fscuat.mobiletrading.design.MyContextMenu.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrder extends AbstractFragment {
    /**
     * khai báo các error code khi đặt lệnh
     *
     * @author Admin
     */
    public enum FIELD_ERROR {
        ERR_AT_ZERO, ERR_AT_SYMBOL, ERR_AT_SIDE, ERR_AT_PRICE, ERR_AT_QTTY, ERR_AT_PIN, ERR_AT_QUOTEQTTY, ERR_AT_LIMITPRICE, ERR_AT_AFACCTNO, ERR_AT_PRICETYPE, ERR_AT_FROMDATE, ERR_AT_TODATE, ERR_AT_SPLITQTTY
    }

    ;

    // bảng mã lỗi
    // ERR_AT_SYMBOL = 1;
    // ERR_AT_SIDE = 2;
    // ERR_AT_PRICE = 3;
    // ERR_AT_QTTY = 4;
    // ERR_AT_PIN = 5;
    // ERR_AT_QUOTEQTTY = 6;
    // ERR_AT_LIMITPRICE = 7;
    // ERR_AT_AFACCTNO = 8;
    // ERR_AT_PRICETYPE = 9;
    // ERR_AT_FROMDATE = 10;
    // ERR_AT_TODATE = 11;
    // ERR_AT_SPLITQTTY = 12;

    public final static int GTCORDER_TYPE = 1;
    public final static int NORMALORDER_TYPE = 0;

    public final static String SIDE_NB = "NB";
    public final static String SIDE_NS = "NS";
    static final String GTCORDERPRICETYPE = "LO";

    static final String CHECKORDER = "CheckOrderService#CHECKORDER";
    static final String CHECKGTCORDER = "CheckOrderService#CHECKGTCORDER";
    static final String FINDSTOCK = "FindStockService#FINDSTOCK";
    static final String FINDSTOCK2 = "FindStockService#FINDSTOCK2";
    static final String GETEXCHANGEBYSYMBOL = "GetExchangeBySymbolService#GETEXCHANGEBYSYMBOL";
    static final String GETSTOCKSBYAFFACCNO = "GetStockByAffaccnoService#GETSTOCKSBYAFFACCNO";

    protected RelativeLayout lay_CTin1;

    protected Button btn_Ban;
    protected Button btn_DatLenh;
    protected Button btn_Mua;
    protected SymbolEdittext edt_MaCK;

    protected Edittext_SoLuong edt_SplitQtty;
    protected Edittext_Gia edttg_Gia;
    protected Edittext_LoaiLenh edttg_LoaiLenh;
    protected Edittext_SoLuong edttg_SoLuong;

    protected FindStock findStock;
    protected String gia = StringConst.EMPTY;
    protected String flag_side = SIDE_NB;
    protected int KLcothemua;
    protected int KLcotheban;

    protected TextView tv_GiaKhopCuoi;
    protected TextView tv_San;
    protected TextView tv_Tran;
    protected TextView tv_RefPrice;

    protected TextView lbl_PPSE;
    protected TextView lbl_Rttbuy;
    protected TextView lbl_TyLeVay;
    protected TextView lbl_KLduocmua;

    protected TextView tv_PPSE;
    protected TextView tv_Rttbuy;
    protected TextView tv_TyLeVay;
    protected TextView tv_KLduocmua;

    protected TextView lbl_KLduocban;
    protected TextView lbl_ChoVe;
    protected TextView lbl_PP0;
    protected TextView lbl_Rttsell;
    protected TextView lbl_NNBan;
    protected TextView lbl_NNBanSell;

    protected TextView tv_KLduocban;
    protected TextView tv_ChoVe;
    protected TextView tv_PP0;
    protected TextView tv_Rttsell;
    protected TextView tv_Company;
    protected TextView tv_RoomNN;
    protected TextView tv_NNMua;
    protected TextView tv_NNBan;
    protected TextView tv_NNBanSell;

    protected String[] list_Sell_Symbol;
    private String[] listAllStock;
    protected StockItem stockItemCurrent;
    protected String afacctno;

    private OrderSetParams orderInit;

    int placeOrderType = NORMALORDER_TYPE;
    ScrollView scrollview;
    String tradeplace;
    /**
     * only tablet
     */
    StockIndex stockInfor_index;
    /**
     * only tablet
     */
    KBoardPrice kBoardPrice;
    /**
     * only tablet
     */
    KBoardQuantity kBoardQuantity;
    /**
     * only tablet
     */
    protected View view_dategtcorder;
    /**
     * only tablet
     */
    protected View view_splitQtty;
    /**
     * only tablet
     */
    protected View view_pricetype;
    /**
     * only tablet
     */
    protected TextView tv_Account;
    /**
     * only tablet
     */
    protected RelativeLayout switchplaceordertype;
    /**
     * only tablet
     */
    protected DialogDate dld_fromdate;
    /**
     * only tablet
     */
    protected DialogDate dld_todate;
    /**
     * only tablet
     */
    protected EditText edt_fromdate;
    /**
     * only tablet
     */
    protected EditText edt_todate;
    /**
     * only tablet
     */
    MyContextMenu changeAfacctno;

    public int getPlaceOrderType() {
        return placeOrderType;
    }

    //    public void setPlaceOrderType(int placeOrderType) {
//        if (placeOrderType == NORMALORDER_TYPE) {
//            this.placeOrderType = placeOrderType;
//            setNormalPlaceOrderType();
//        } else if (placeOrderType == GTCORDER_TYPE) {
//            this.placeOrderType = placeOrderType;
//            ((MainActivity_Mobile)getActivity()).setActionbarPlaceOrder(R.color.placeorder_buy_color);
//            setGTCPlaceOrderType();
//        }
//    }
    public void setPlaceOrderType(int placeOrderType) {
        if (placeOrderType == NORMALORDER_TYPE) {
            this.placeOrderType = placeOrderType;
            setNormalPlaceOrderType();
        } else if (placeOrderType == GTCORDER_TYPE) {
            this.placeOrderType = placeOrderType;
            if (!DeviceProperties.isTablet) {
                ((MainActivity_Mobile) getActivity()).setActionbarPlaceOrder(R.color.placeorder_buy_color);
            }
            setGTCPlaceOrderType();
        }
    }

    private void setNormalPlaceOrderType() {
        if (view_splitQtty != null) {
            view_splitQtty.setVisibility(View.VISIBLE);
        }
        if (view_pricetype != null) {
            view_pricetype.setVisibility(View.VISIBLE);
        }
        if (switchplaceordertype != null) {
            switchplaceordertype.setActivated(false);
        }
        if (view_dategtcorder != null) {
            view_dategtcorder.setVisibility(View.GONE);
        }
        edttg_LoaiLenh.setText(GTCORDERPRICETYPE);
        clearFormWhenChangePlaceOrderType();
        // clearFormStockInfo();
    }

    private void setGTCPlaceOrderType() {
        if (view_splitQtty != null) {
            view_splitQtty.setVisibility(View.GONE);
        }
        if (view_pricetype != null) {
            view_pricetype.setVisibility(View.GONE);
        }
        if (switchplaceordertype != null) {
            switchplaceordertype.setActivated(true);
        }
        if (view_dategtcorder != null) {
            view_dategtcorder.setVisibility(View.VISIBLE);
        }
        edttg_LoaiLenh.setPriceType(GTCORDERPRICETYPE);
        clearFormWhenChangePlaceOrderType();
        // clearFormStockInfo();
    }

    /**
     * khoi tao voi mainActivity
     *
     * @param mActivity
     * @return
     */
    public static PlaceOrder newInstance(MainActivity mActivity) {
        PlaceOrder self = new PlaceOrder();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.Trade);
        return self;
    }

    /**
     * khởi tạo với thông tin lệnh
     *
     * @param orderSetParams
     * @return
     */
    public static PlaceOrder newInstance(OrderSetParams orderSetParams) {
        PlaceOrder self = new PlaceOrder();
        self.setOrderInit(orderSetParams);
        self.mainActivity = (MainActivity) self.getActivity();
        self.TITLE = self.mainActivity.getStringResource(R.string.Trade);
        return self;
    }

    protected int getLayoutId() {
        return R.layout.placeorder;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle bundle) {

        final View view = inflater.inflate(getLayoutId(), viewGroup, false);
        lay_CTin1 = (RelativeLayout) view.findViewById(R.id.lay_CTin1);
        btn_Ban = (Button) view.findViewById(R.id.btn_DatLenh_Ban);
        btn_Mua = (Button) view.findViewById(R.id.btn_DatLenh_Mua);
        btn_DatLenh = (Button) view.findViewById(R.id.btn_DatLenh_DatLenh);
        edt_MaCK = ((SymbolEdittext) view.findViewById(R.id.autoEdt_DatLenh_Symbol));
        edt_MaCK.setSymbolEdittextType(SymbolEdittext.SymbolEdittextType.Single);
        tv_Account = ((TextView) view
                .findViewById(R.id.autoEdt_DatLenh_Afacctno));
        view_splitQtty = view.findViewById(R.id.lay_order_splitqtty);
        view_dategtcorder = view.findViewById(R.id.lay_order_date);
        view_pricetype = view.findViewById(R.id.lay_order_pricetype);
        switchplaceordertype = (RelativeLayout) view
                .findViewById(R.id.selector_placeordertype);
        edttg_SoLuong = (Edittext_SoLuong) view
                .findViewById(R.id.cus_edt_DatLenh_SoLuong);
        edttg_LoaiLenh = (Edittext_LoaiLenh) view
                .findViewById(R.id.cus_edt_DatLenh_LoaiLenh);
        edttg_Gia = (Edittext_Gia) view.findViewById(R.id.cus_edt_DatLenh_Gia);
        tv_GiaKhopCuoi = (TextView) view
                .findViewById(R.id.text_DatLenh_GiaKhopCuoi);
        tv_Tran = (TextView) view.findViewById(R.id.text_DatLenh_Tran);
        tv_San = (TextView) view.findViewById(R.id.text_DatLenh_San);
        tv_RefPrice = (TextView) view.findViewById(R.id.text_DatLenh_refprice);

        lbl_PPSE = (TextView) view.findViewById(R.id.lbl_datlenh_SucMuaBuy);
        tv_PPSE = (TextView) view.findViewById(R.id.text_datlenh_SucMuaBuy);

        lbl_Rttbuy = (TextView) view.findViewById(R.id.lbl_datlenh_rttbuy);
        tv_Rttbuy = (TextView) view.findViewById(R.id.text_datlenh_rttbuy);

        lbl_TyLeVay = (TextView) view.findViewById(R.id.lbl_datlenh_tylevay);
        tv_TyLeVay = (TextView) view.findViewById(R.id.text_datlenh_tylevay);

        lbl_KLduocmua = (TextView) view
                .findViewById(R.id.lbl_datlenh_KLduocmua);
        tv_KLduocmua = (TextView) view
                .findViewById(R.id.text_datlenh_KLduocmua);

        lbl_Rttsell = (TextView) view.findViewById(R.id.lbl_datlenh_rttsell);
        tv_Rttsell = (TextView) view.findViewById(R.id.text_datlenh_rttsell);

        lbl_KLduocban = (TextView) view
                .findViewById(R.id.lbl_datlenh_KLduocban);
        tv_KLduocban = (TextView) view
                .findViewById(R.id.text_datlenh_KLduocban);

        lbl_ChoVe = (TextView) view.findViewById(R.id.lbl_datlenh_ChoVe);
        tv_ChoVe = (TextView) view.findViewById(R.id.text_datlenh_ChoVe);

        lbl_PP0 = (TextView) view.findViewById(R.id.lbl_datlenh_SucMuaSell);
        tv_PP0 = (TextView) view.findViewById(R.id.text_datlenh_SucMuaSell);

        tv_Company = (TextView) view.findViewById(R.id.text_DatLenh_Company);
        tv_RoomNN = (TextView) view.findViewById(R.id.text_DatLenh_RoomNN);
        tv_NNMua = (TextView) view.findViewById(R.id.text_DatLenh_NNMua);

        lbl_NNBan = (TextView) view.findViewById(R.id.lbl_datlenh_nnban);
        tv_NNBan = (TextView) view.findViewById(R.id.text_DatLenh_NNBan);

        lbl_NNBanSell = (TextView) view.findViewById(R.id.lbl_datlenh_nnbansell);
        tv_NNBanSell = (TextView) view.findViewById(R.id.text_DatLenh_NNBanSell);
        edt_SplitQtty = ((Edittext_SoLuong) view
                .findViewById(R.id.edttg_DatLenh_SplitQtty));

        kBoardPrice = (KBoardPrice) view
                .findViewById(R.id.t_placeorder_kboardsymbol_price);
        kBoardQuantity = (KBoardQuantity) view
                .findViewById(R.id.t_placeorder_kboardsymbol_quantity);
        edt_fromdate = (EditText) view.findViewById(R.id.edt_order_fromdate);
        edt_todate = (EditText) view.findViewById(R.id.edt_order_todate);
        if (DeviceProperties.isTablet && StaticObjectManager.loginInfo.IsBroker) {
            switchplaceordertype.setVisibility(View.GONE);
        }
        initialise();
        initialiseListener();
        if (DeviceProperties.isTablet) {
            Common.setupUI(view.findViewById(R.id.datlenh), this.getDialog());
        } else {
            Common.setupUI(view.findViewById(R.id.datlenh), mainActivity);
        }
        //background buy placeorder
        lay_CTin1.setBackgroundResource(R.color.place_buyorder_background);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        Window w = d.getWindow();
        w.setBackgroundDrawableResource(R.color.transparent);
        w.setGravity(Gravity.LEFT);
        WindowManager.LayoutParams l = w.getAttributes();
        l.x = getDimenResource(R.dimen.t_framewatchlist_width);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return d;
    }

    @Override
    public void onStart() {
        super.onStart();
        int width = getDimenResource(R.dimen.t_placeorder_dialog_width);
        int height = LayoutParams.WRAP_CONTENT;
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(width, height);
        }
    }

    protected void initialise() {
        edt_MaCK.setInputType(InputType.TYPE_NULL);
        edt_MaCK.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edt_MaCK.setTextIsSelectable(true);
        listAllStock = StaticObjectManager.getListAllStock();
        if (DeviceProperties.isTablet) {
            if (changeAfacctno == null) {
                changeAfacctno = new MyContextMenu(mainActivity);
                // đặt set listener cho changeAfacctno ở đây vì changeAfacctno k
                // bị mất object mỗi lần oncreateView
                changeAfacctno
                        .setOnItemSelectedListener(new OnItemSelectedListener() {

                            @Override
                            public void onItemSelect(Object obj, int position) {
                                afacctno = ((AcctnoItem) obj).ACCTNO;
                                CallFindStockByAfacctno();
                                CallGetStocksByAffaccno();
                            }
                        });
            }
            changeAfacctno.setTextview(tv_Account);
            changeAfacctno
                    .setChoises(StaticObjectManager.loginInfo.contractList);
            changeAfacctno.setSelection(StaticObjectManager.acctnoItem);
        }
        edt_fromdate.setInputType(InputType.TYPE_NULL);
        edt_todate.setInputType(InputType.TYPE_NULL);
        if (dld_fromdate == null) {
            dld_fromdate = new DialogDate(mainActivity);
            dld_fromdate.setEdt(edt_fromdate);
        } else {
            dld_fromdate.setEdt(edt_fromdate);
            dld_fromdate.resetDate();
        }
        if (dld_todate == null) {
            dld_todate = new DialogDate(mainActivity);
            dld_todate.setEdt(edt_todate);
        } else {
            dld_todate.setEdt(edt_todate);
            dld_todate.resetDate();
        }
    }

    private void initWithValue() {
        // check co khoi tao lenh hay khong
        if (orderInit != null) {

            // set số lưu ký
            // set số tiểu khoản
            afacctno = StaticObjectManager.acctnoItem.ACCTNO;
            // set lệnh thường hay lệnh điều kiện
            if (orderInit.isGTCOrder) {
                setPlaceOrderType(GTCORDER_TYPE);
            } else {
                setPlaceOrderType(NORMALORDER_TYPE);
            }
            // set lệnh mua hay bán
            if (SIDE_NS.equals(orderInit.sideOrder)) {
                functionBtnBanClickNotClearForm();
            } else {
                functionBtnMuaClickNotClearForm();
            }

            // set mã chứng khoán => call getExchangeBySymbol => set loại lệnh
            // => set giá
            edt_MaCK.setText(orderInit.symbolOrder);

            // set số lượng
            if (orderInit.quantityOrder != null) {
                edttg_SoLuong.setText(orderInit.quantityOrder);
            }

            // set giá
            if (orderInit.priceOrder != null) {
                edttg_Gia.setText(orderInit.priceOrder);
            } else {
                edttg_Gia.setText(StringConst.EMPTY);
            }

            // set khối lượng chia
            // set thời gian đặt
            orderInit = null;
        } else {
            // set số lưu ký
            // set số tiểu khoản
            afacctno = StaticObjectManager.acctnoItem.ACCTNO;
            // set lệnh thường hay lệnh điều kiện
            setDefaultPlaceOrderType();
            // set lệnh mua hay bán
            functionBtnMuaClick();
            // set mã chứng khoán
            // set số lượng
            // set loại lệnh
            // set giá
            // set khối lượng chia
            // set thời gian đặt
        }
    }

    protected void setDefaultPlaceOrderType() {
        setPlaceOrderType(NORMALORDER_TYPE);
    }

    protected void initialiseListener() {

        if (DeviceProperties.isTablet) {
            edt_fromdate.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    dld_todate.setDate(s.toString());
                }
            });

            tv_Account.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (StaticObjectManager.loginInfo.IsBroker) {
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        ((MainActivity_Tablet) mainActivity)
                                .showChooseAccountFragmentWithPosition(
                                        location[0], location[1]);
                    } else {
                        changeAfacctno.show();
                    }

                }
            });
            switchplaceordertype.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (getPlaceOrderType() == NORMALORDER_TYPE) {
                        setPlaceOrderType(GTCORDER_TYPE);
                        switchplaceordertype.setActivated(true);

                    } else {
                        setPlaceOrderType(NORMALORDER_TYPE);
                        switchplaceordertype.setActivated(false);
                    }
                }
            });
        }
//        btn_Mua.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                lay_CTin1.setBackgroundResource(R.color.place_buyorder_background);
//                Log.e("la_CTin1", String.valueOf(lay_CTin1));
//                btn_DatLenh.setBackgroundResource(R.drawable.backgroundbutton);
//                functionBtnMuaClick();
//                ((MainActivity_Mobile)getActivity()).setActionbarPlaceOrder(R.color.placeorder_buy_color);
//
//            }
//        });
//        btn_Ban.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                lay_CTin1.setBackgroundResource(R.color.place_sellorder_background);
//                btn_DatLenh.setBackgroundResource(R.drawable.background_sellbutton);
//                functionBtnBanClick();
//                ((MainActivity_Mobile)getActivity()).setActionbarPlaceOrder(R.color.placeorder_sell_color);
//            }
//        });
        btn_Mua.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                lay_CTin1.setBackgroundResource(R.color.place_buyorder_background);
                btn_DatLenh.setBackgroundResource(R.drawable.backgroundbutton);
                functionBtnMuaClick();
                if (!DeviceProperties.isTablet) {
                    ((MainActivity_Mobile) getActivity()).setActionbarPlaceOrder(R.color.placeorder_buy_color);
                }


            }
        });
        btn_Ban.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                lay_CTin1.setBackgroundResource(R.color.place_sellorder_background);
                btn_DatLenh.setBackgroundResource(R.drawable.background_sellbutton);
                functionBtnBanClick();
                if (!DeviceProperties.isTablet) {
                    ((MainActivity_Mobile) getActivity()).setActionbarPlaceOrder(R.color.placeorder_sell_color);
                }
            }
        });
        edttg_SoLuong.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (DeviceProperties.isTablet) {
                        showKBQuantity(true, edttg_SoLuong.toEditText());
                    } else {
                        mainActivity.showKBoardQuantity(true,
                                edttg_SoLuong.toEditText());
                    }
                } else {
                    if (DeviceProperties.isTablet) {
                        // showKBQuantity(false, null);
                    } else {
                        mainActivity.showKBoardQuantity(false,
                                edttg_SoLuong.toEditText());
                    }
                }
            }
        });

        edt_SplitQtty.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (DeviceProperties.isTablet) {
                        showKBQuantity(true, edt_SplitQtty.toEditText());
                    } else {
                        mainActivity.showKBoardQuantity(true,
                                edt_SplitQtty.toEditText());
                    }
                } else {
                    if (DeviceProperties.isTablet) {
                        // showKBQuantity(false, null);
                    } else {
                        mainActivity.showKBoardQuantity(false,
                                edt_SplitQtty.toEditText());
                    }
                }

            }
        });

        edttg_Gia.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (DeviceProperties.isTablet) {
                        showKBPrice(true, edttg_Gia.toEditText());
                    } else {
                        mainActivity.showKBoardPrice(true,
                                edttg_Gia.toEditText());
                    }

                } else {
                    if (DeviceProperties.isTablet) {
                        // showKBPrice(false, null);
                    } else {
                        mainActivity.showKBoardPrice(false, edttg_Gia.toEditText());
                    }
                    if (edttg_Gia.getText().length() > 0
                            && !edttg_Gia.getText().toString().contains(".")) {
                        edttg_Gia
                                .setText(edttg_Gia.getText().toString() + ".0");
                    }
                }
            }
        });

        edttg_Gia.addTextChangedListener(new TextWatcher() {

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
                if (s != null && (placeOrderType == NORMALORDER_TYPE)) {
                    // nếu là lệnh thường thì gọi findstock2
                    CallFindStockByAfacctno();
                }
            }
        });

        edttg_LoaiLenh.setOnClickChangeButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (placeOrderType == NORMALORDER_TYPE) {
                    changePriceType();
                } else {
                    edttg_Gia.setEnabled(true);
                }
            }
        });
        edt_MaCK.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                return true;
            }
        });
        edt_MaCK.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (flag_side.equals(SIDE_NB)) {
                        mainActivity.showKBoardHook(true, edt_MaCK, listAllStock);
                    } else {
                        mainActivity.showKBoardHook(true, edt_MaCK,
                                list_Sell_Symbol);
                    }
                    mainActivity.showKBoardSymbol(true, edt_MaCK);
                } else {
                    CallFindStock();
                    mainActivity.showKBoardSymbol(false, null);
                    mainActivity.showKBoardHook(false, null, null);
                }
            }
        });
        edt_MaCK.addTextChangedListener(new TextWatcher() {

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
                    clearFormStockInfo();
                    clearPriceAndQuantity();
                    if (DeviceProperties.isTablet) {
                        stockInfor_index.receiverparameter(StringConst.EMPTY);
                    }
                } else {
                    stockItemCurrent = StaticObjectManager.getStockItem(s
                            .toString());
                    if (stockItemCurrent != null) {
                        edttg_SoLuong.tradeplace = stockItemCurrent
                                .getTradePlace();
                        edttg_Gia.tradeplace = stockItemCurrent.getTradePlace();
                        edt_SplitQtty.tradeplace = stockItemCurrent
                                .getTradePlace();
                        tradeplace = stockItemCurrent.getTradePlace();
                        CallGetExchangebysymbol();
                        // CallFindStock(); // bỏ đi, giờ khi ô mã chứng khoán
                        // mất focus thì gọi
                        if (DeviceProperties.isTablet) {
                            stockInfor_index.receiverparameter(stockItemCurrent
                                    .toString());
                        }
                    } else {
                        edttg_SoLuong.tradeplace = StringConst.EMPTY;
                        edttg_Gia.tradeplace = StringConst.EMPTY;
                        tradeplace = StringConst.EMPTY;
                    }
                }
            }
        });
        btn_DatLenh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (placeOrderType == NORMALORDER_TYPE) {
                    CallCheckOrder();
                } else {
                    CallCheckGTCOrder();
                }
            }
        });
    }

    protected void clearFormStockInfo() {

        tv_PPSE.setText(StringConst.EMPTY);
        tv_Rttbuy.setText(StringConst.EMPTY);
        tv_TyLeVay.setText(StringConst.EMPTY);
        tv_KLduocmua.setText(StringConst.EMPTY);

        tv_KLduocban.setText(StringConst.EMPTY);
        tv_ChoVe.setText(StringConst.EMPTY);
        tv_PP0.setText(StringConst.EMPTY);
        tv_Rttsell.setText(StringConst.EMPTY);

        tv_Tran.setText(StringConst.EMPTY);
        tv_San.setText(StringConst.EMPTY);
        tv_GiaKhopCuoi.setText(StringConst.EMPTY);
        tv_RefPrice.setText(StringConst.EMPTY);

        tv_Company.setText(StringConst.EMPTY);
        tv_RoomNN.setText(StringConst.EMPTY);
        tv_NNMua.setText(StringConst.EMPTY);
        tv_NNBan.setText(StringConst.EMPTY);
        tv_NNBanSell.setText(StringConst.EMPTY);
    }

    public void onPause() {
        super.onPause();
        if (DeviceProperties.isTablet) {
            stockInfor_index.refresh();
            getChildFragmentManager().beginTransaction()
                    .remove(stockInfor_index).commit();
        }
        clearForm();
    }

    @Override
    public void refresh() {
        super.refresh();
        CallFindStockByAfacctno();
    }

    public void onResume() {
        super.onResume();
        if (DeviceProperties.isTablet) {
            if (stockInfor_index == null) {
                stockInfor_index = StockIndex.newInstance(mainActivity);
            }
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_placeorder_stockindex,
                            stockInfor_index).commit();
        }
        // phải đặt hàm initWithValue ở dưới hàm khởi tạo stockInforIndex vì khi
        // fill mã chứng khoán => onTextChange => set mã chứng khoán cho hàm
        // stockindex
        initWithValue();

    }

    protected void CallCheckOrder() {
        boolean callcheckorder = PlaceOrderService.CallCheckOrder(afacctno,
                StaticObjectManager.acctnoItem.CUSTODYCD, flag_side, edt_MaCK
                        .getText().toString(), edttg_LoaiLenh.getText()
                        .toString(), edttg_SoLuong.getText().toString(),
                edttg_Gia.getText().toString(), edt_SplitQtty.getText()
                        .toString(), this, CHECKORDER);
        if (callcheckorder) {
            btn_DatLenh.setEnabled(false);
        }
    }

    private void CallCheckGTCOrder() {
        boolean callcheckgtcorder = PlaceOrderService.CallCheckGTCOrder(
                afacctno, StaticObjectManager.acctnoItem.CUSTODYCD, flag_side,
                edt_MaCK.getText().toString(), GTCORDERPRICETYPE, edttg_SoLuong
                        .getText().toString(), edttg_Gia.getText().toString(),
                edt_fromdate.getText().toString(), edt_todate.getText()
                        .toString(), this, CHECKGTCORDER);
        if (callcheckgtcorder) {
            isCheckOrderLoading();
            btn_DatLenh.setEnabled(false);
        }
    }

    protected void CallFindStock() {
        String price = StringConst.EMPTY;
        if (placeOrderType == NORMALORDER_TYPE) {
            price = edttg_Gia.getText().toString();
        }
        String key = FINDSTOCK + StringConst.SEMI
                + String.valueOf(registerChangeFormStockInfo());
        boolean callFindStock = PlaceOrderService.CallFindStock(edt_MaCK
                        .getText().toString(), flag_side, price, edttg_SoLuong
                        .getText().toString(), edttg_LoaiLenh.getText().toString(),
                afacctno, this, key);
        if (callFindStock) {
            isCheckOrderLoading();
        }
    }

    // lấy các thông tin về tổng tiền, kl được mua, được bán,....tránh
    // ảnh hưởng đến logic phần giá
    protected void CallFindStockByAfacctno() {
        String price = StringConst.EMPTY;
        if (placeOrderType == NORMALORDER_TYPE) {
            price = edttg_Gia.getText().toString();
        }
        String key = FINDSTOCK2 + StringConst.SEMI
                + String.valueOf(registerChangeFormStockInfo());
        boolean callFindStock = PlaceOrderService.CallFindStock(edt_MaCK
                        .getText().toString(), flag_side, price, edttg_SoLuong
                        .getText().toString(), edttg_LoaiLenh.getText().toString(),
                afacctno, this, key);
        if (callFindStock) {
            isCheckOrderLoading();
        }
    }

    protected void CallGetExchangebysymbol() {
        boolean getExchangeBySymbol = PlaceOrderService
                .CallGetExchangebysymbol(edt_MaCK.getText().toString(), this,
                        GETEXCHANGEBYSYMBOL);
        if (getExchangeBySymbol) {
            isCheckOrderLoading();
        }
    }

    protected void CallGetStocksByAffaccno() {
        boolean callGetStocksByAffaccno = PlaceOrderService
                .CallGetStocksByAffaccno(afacctno, this, GETSTOCKSBYAFFACCNO);
        if (callGetStocksByAffaccno) {
            isCheckOrderLoading();
        }
    }

    protected void changePriceType() {
        // sau khi lấy được các loại lệnh, thì phải gọi lại findstock để truyền
        // loại lệnh lên và lấy về khối lượng đc mua max. Nhưng vì findstock sẽ
        // đc gọi mỗi khi ô nhập giá thay
        // đổi nên cần check trong hàm này những trường hợp ô giá k thay đổi để
        // gọi findstock
        if ("LO".equals(edttg_LoaiLenh.getText().toString())) {
            edttg_Gia.setEnabled(true);
            if (gia != null && gia.length() > 0) {
                edttg_Gia.setText(gia);
                gia = null;
            } else {
                edttg_Gia.setText(StringConst.EMPTY);
            }

        } else {
            edttg_Gia.setEnabled(false);
            if (flag_side.equals(SIDE_NB)) {
                edttg_Gia.setText(edttg_Gia.getCeilPrice());
            } else {
                edttg_Gia.setText(edttg_Gia.getFloorPrice());
            }
        }
    }

    protected void displayDatLenh() {
        if (placeOrderType == NORMALORDER_TYPE) {
            setCeilFloorForEdittextGia();
        }
        displayFormStockInfo();
    }

    private void setCeilFloorForEdittextGia() {
        if (findStock != null) {

            edttg_Gia.setCeilPrice(findStock.stockInfo.CeilPrice);
            edttg_Gia.setFloorPrice(findStock.stockInfo.FloorPrice);
            edttg_Gia.setClosePrice(findStock.stockInfo.ClosePrice);
        }
    }

    /**
     * biến seq đánh dấu các lần gọi findstock hoặc findstockbyafacctno (đều dẫn
     * đến thay đổi formStockInfo) nếu findstock trả về có seq nhỏ hơn seq hiện
     * tại thì k đc update formStockInfo seq tự tăng mỗi lần gọi findstock hoặc
     * findstockbyafacctno , được reset khi PlaceOrder onDestroy;
     */
    int seq = 0;

    /**
     * hàm đăng ký lấy seq
     */
    private int registerChangeFormStockInfo() {
        seq++;
        return seq;
    }

    protected void displayFormStockInfo() {
        try {
            if (findStock != null) {
                tv_Company.setText(findStock.stockInfo.stockname);
                tv_RoomNN.setText(Common
                        .formatAmount(findStock.stockInfo.foreignRemain));
                tv_NNMua.setText(Common
                        .formatAmount(findStock.stockInfo.foreignBuy));
                tv_NNBan.setText(Common
                        .formatAmount(findStock.stockInfo.foreignSell));
                tv_NNBanSell.setText(Common
                        .formatAmount(findStock.stockInfo.foreignSell));
                tv_PPSE.setText(Common
                        .formatAmount(findStock.stockInfo.CashAvaiable));
                tv_PP0.setText(Common
                        .formatAmount(findStock.stockInfo.CashAvaiable));
                tv_KLduocmua.setText(Common
                        .formatAmount(findStock.orderInfo.MaxQty));
                tv_KLduocban.setText(Common
                        .formatAmount(findStock.orderInfo.MaxQty));
                tv_ChoVe.setText(Common
                        .formatAmount(findStock.stockInfo.Receiving));
                tv_TyLeVay.setText(Common
                        .formatAmount(findStock.orderInfo.MRRATIOLOAN));

                tv_Rttbuy.setText(findStock.orderInfo.Rtt);
                tv_Rttsell.setText(findStock.orderInfo.Rtt);

                tv_Tran.setText(findStock.stockInfo.CeilPrice);
                tv_San.setText(findStock.stockInfo.FloorPrice);
                tv_RefPrice.setText(findStock.stockInfo.RefPrice);
                tv_GiaKhopCuoi.setText(findStock.stockInfo.ClosePrice);
                tv_GiaKhopCuoi.setTextColor(getResources().getColor(
                        Common.getColor(findStock.stockInfo.ClosePrice,
                                findStock.stockInfo.CeilPrice,
                                findStock.stockInfo.FloorPrice,
                                findStock.stockInfo.RefPrice)));
                try {
                    KLcothemua = Integer.parseInt(tv_KLduocmua.getText()
                            .toString().replace(",", StringConst.EMPTY));
                } catch (NumberFormatException e) {
                    KLcothemua = 0;
                }
                try {
                    KLcotheban = Integer.parseInt(tv_KLduocban.getText()
                            .toString().replace(",", StringConst.EMPTY));
                } catch (NumberFormatException e) {
                    KLcotheban = 0;
                }
            }
        } catch (IllegalStateException e) {
        }
    }

    /**
     * vì khi gọi hàm functionBtnBanClick => clearForm => gọi findStock, ... =>
     * ảnh hưởng dữ liệu hàm này chỉ thay đổi về giao diện, không ảnh hưởng đến
     * dữ liệu
     */
    protected void functionBtnBanClickNotClearForm() {
        CallGetStocksByAffaccno();

        // đổi màu button mua bán
        // btn_Mua.setBackgroundColor(getColorResource(R.color.throughout));
        // btn_Ban.setBackgroundColor(getColorResource(R.color.placeorder_sell_color));
        btn_Mua.setSelected(false);
        btn_Ban.setSelected(true);

        // thiết lập cờ mua bán
        flag_side = SIDE_NS;

        // hiện các field tương ứng, phải được thực hiện sau khi đã thiết lập cờ
        displayFormStockInfo();

        if (flag_side.equals(SIDE_NS)) {
            // hiển thị và ẩn các trường tương ứng
            lbl_KLduocban.setVisibility(TextView.VISIBLE);
            lbl_ChoVe.setVisibility(TextView.VISIBLE);
            lbl_PP0.setVisibility(TextView.VISIBLE);
            lbl_Rttsell.setVisibility(TextView.VISIBLE);

            tv_KLduocban.setVisibility(TextView.VISIBLE);
            tv_ChoVe.setVisibility(TextView.VISIBLE);
            tv_PP0.setVisibility(TextView.VISIBLE);
            tv_Rttsell.setVisibility(TextView.VISIBLE);

            lbl_PPSE.setVisibility(TextView.GONE);
            lbl_TyLeVay.setVisibility(TextView.GONE);
            lbl_KLduocmua.setVisibility(TextView.GONE);
            lbl_Rttbuy.setVisibility(TextView.GONE);

            tv_PPSE.setVisibility(TextView.GONE);
            tv_TyLeVay.setVisibility(TextView.GONE);
            tv_KLduocmua.setVisibility(TextView.GONE);
            tv_Rttbuy.setVisibility(TextView.GONE);
            if (!DeviceProperties.isTablet) {
                lbl_NNBanSell.setVisibility(TextView.VISIBLE);
                tv_NNBanSell.setVisibility(TextView.VISIBLE);
                lbl_NNBan.setVisibility(TextView.GONE);
                tv_NNBan.setVisibility(TextView.GONE);
            }
        } else {
            // hiển thị và ẩn các trường tương ứng
            lbl_KLduocban.setVisibility(TextView.GONE);
            lbl_ChoVe.setVisibility(TextView.GONE);
            lbl_PP0.setVisibility(TextView.GONE);
            lbl_Rttsell.setVisibility(TextView.GONE);

            tv_KLduocban.setVisibility(TextView.GONE);
            tv_ChoVe.setVisibility(TextView.GONE);
            tv_PP0.setVisibility(TextView.GONE);
            tv_Rttsell.setVisibility(TextView.GONE);

            lbl_PPSE.setVisibility(TextView.VISIBLE);
            lbl_TyLeVay.setVisibility(TextView.VISIBLE);
            lbl_KLduocmua.setVisibility(TextView.VISIBLE);
            lbl_Rttbuy.setVisibility(TextView.VISIBLE);

            tv_PPSE.setVisibility(TextView.VISIBLE);
            tv_TyLeVay.setVisibility(TextView.VISIBLE);
            tv_KLduocmua.setVisibility(TextView.VISIBLE);
            tv_Rttbuy.setVisibility(TextView.VISIBLE);
            if (!DeviceProperties.isTablet) {
                lbl_NNBan.setVisibility(TextView.VISIBLE);
                tv_NNBan.setVisibility(TextView.VISIBLE);
                lbl_NNBanSell.setVisibility(TextView.GONE);
                tv_NNBanSell.setVisibility(TextView.GONE);
            }
        }
    }

    protected void functionBtnBanClick() {
        functionBtnBanClickNotClearForm();

        // xóa các trường hiện tại
        clearForm();

    }

    /**
     * vì khi gọi hàm functionBtnMuaClick => clearForm => gọi findStock, ... =>
     * ảnh hưởng dữ liệu hàm này chỉ thay đổi về giao diện, không ảnh hưởng đến
     * dữ liệu
     */
    protected void functionBtnMuaClickNotClearForm() {

        // đổi màu button mua bán
        // btn_Mua.setBackgroundColor(getColorResource(R.color.placeorder_buy_color));
        // btn_Ban.setBackgroundColor(getColorResource(R.color.throughout));
        btn_Mua.setSelected(true);
        btn_Ban.setSelected(false);

        // thiết lập cờ mua bán
        flag_side = SIDE_NB;

        // hiện các field tương ứng, phải được thực hiện sau khi đã thiết lập cờ
        displayFormStockInfo();

        if (flag_side.equals(SIDE_NS)) {
            // hiển thị và ẩn các trường tương ứng
            lbl_KLduocban.setVisibility(TextView.VISIBLE);
            lbl_ChoVe.setVisibility(TextView.VISIBLE);
            lbl_PP0.setVisibility(TextView.VISIBLE);
            lbl_Rttsell.setVisibility(TextView.VISIBLE);

            tv_KLduocban.setVisibility(TextView.VISIBLE);
            tv_ChoVe.setVisibility(TextView.VISIBLE);
            tv_PP0.setVisibility(TextView.VISIBLE);
            tv_Rttsell.setVisibility(TextView.VISIBLE);

            lbl_PPSE.setVisibility(TextView.GONE);
            lbl_TyLeVay.setVisibility(TextView.GONE);
            lbl_KLduocmua.setVisibility(TextView.GONE);
            lbl_Rttbuy.setVisibility(TextView.GONE);

            tv_PPSE.setVisibility(TextView.GONE);
            tv_TyLeVay.setVisibility(TextView.GONE);
            tv_KLduocmua.setVisibility(TextView.GONE);
            tv_Rttbuy.setVisibility(TextView.GONE);
            if (!DeviceProperties.isTablet) {
                lbl_NNBanSell.setVisibility(TextView.VISIBLE);
                tv_NNBanSell.setVisibility(TextView.VISIBLE);
            }
        } else {
            // hiển thị và ẩn các trường tương ứng
            lbl_KLduocban.setVisibility(TextView.GONE);
            lbl_ChoVe.setVisibility(TextView.GONE);
            lbl_PP0.setVisibility(TextView.GONE);
            lbl_Rttsell.setVisibility(TextView.GONE);

            tv_KLduocban.setVisibility(TextView.GONE);
            tv_ChoVe.setVisibility(TextView.GONE);
            tv_PP0.setVisibility(TextView.GONE);
            tv_Rttsell.setVisibility(TextView.GONE);

            lbl_PPSE.setVisibility(TextView.VISIBLE);
            lbl_TyLeVay.setVisibility(TextView.VISIBLE);
            lbl_KLduocmua.setVisibility(TextView.VISIBLE);
            lbl_Rttbuy.setVisibility(TextView.VISIBLE);

            tv_PPSE.setVisibility(TextView.VISIBLE);
            tv_TyLeVay.setVisibility(TextView.VISIBLE);
            tv_KLduocmua.setVisibility(TextView.VISIBLE);
            tv_Rttbuy.setVisibility(TextView.VISIBLE);
            if (!DeviceProperties.isTablet) {
                lbl_NNBan.setVisibility(TextView.VISIBLE);
                tv_NNBan.setVisibility(TextView.VISIBLE);
            }
        }
    }

    protected void functionBtnMuaClick() {

        functionBtnMuaClickNotClearForm();

        // xóa các trường hiện tại
        clearForm();
    }

    protected void isCheckOrderLoading() {
        // loading.setVisibility(ImageView.VISIBLE);
        // loading.setAnimation(Common.getRotateAnimation());
    }

    protected void isCheckOrderLoaded() {
        // loading.setVisibility(ImageView.GONE);
        // loading.setAnimation(null);
    }

    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
        afacctno = StaticObjectManager.acctnoItem.ACCTNO;
        if (afacctno.length() != 0) {
            CallFindStock();
            CallGetStocksByAffaccno();
            if (DeviceProperties.isTablet && StaticObjectManager.loginInfo.IsBroker) {
                tv_Account.setText(StaticObjectManager.acctnoItem.toString());
            }
        } else {
            if (DeviceProperties.isTablet && StaticObjectManager.loginInfo.IsBroker) {
                tv_Account.setText(StringConst.EMPTY);
            }
        }

    }

    @Override
    protected void process(String key, ResultObj rObj) {

        switch (key) {
            case GETEXCHANGEBYSYMBOL:
                if (rObj.obj != null) {
                    List<String> localList = (List<String>) rObj.obj;
                    edttg_LoaiLenh.setListItem(localList);
                }
                break;
            case GETSTOCKSBYAFFACCNO:
                if (rObj.obj != null) {
                    List<StockBalanceItem> listDMCK = ((List<StockBalanceItem>) rObj.obj);
                    List<String> positions = new ArrayList<String>();
                    for (int i = 0; i < listDMCK.size(); i++) {
                        StockBalanceItem item = listDMCK.get(i);
                        if (!item.getTRADE().equals("0")) { // Loại bỏ các mã chứng
                            // khoán chờ về (khối lượng
                            // trade =0 )

                            // check mã nhận đc có nằm trong danh sách mã ban đầu
                            // không
                            StockItem stockItem = StaticObjectManager
                                    .getStockItem(item.toString());
                            if (stockItem != null) {
                                positions.add(stockItem.toString());
                            }
                        }
                    }
                    list_Sell_Symbol = new String[positions.size()];
                    for (int i = 0; i < positions.size(); i++) {
                        list_Sell_Symbol[i] = positions.get(i);
                    }
                    if (edt_MaCK.isFocused()) {
                        mainActivity.showKBoardHook(true, edt_MaCK,
                                list_Sell_Symbol);
                    }
                }
                break;
            case CHECKORDER:
                OrderSetParams order = new OrderSetParams(
                        StaticObjectManager.acctnoItem.CUSTODYCD, afacctno,
                        edt_MaCK.getText().toString(), flag_side, edttg_Gia
                        .getText().toString(), edttg_SoLuong.getText()
                        .toString(), edttg_LoaiLenh.getText().toString(),
                        edt_SplitQtty.getText().toString());
                showOrderConfirm(order);
                btn_DatLenh.setEnabled(true);
                break;
            case CHECKGTCORDER:
                OrderSetParams gtcorder = new OrderSetParams(
                        StaticObjectManager.acctnoItem.CUSTODYCD, afacctno,
                        edt_MaCK.getText().toString(), flag_side, edttg_Gia
                        .getText().toString(), edttg_SoLuong.getText()
                        .toString(), OrderConfirm.DEFAULT_PRICETYPE,
                        edt_fromdate.getText().toString(), edt_todate.getText()
                        .toString());
                showOrderConfirm(gtcorder);
                btn_DatLenh.setEnabled(true);
                break;

            default:
                // vì với request findstock và findstock2 có gắn thêm 1 sequence nên
                // phải tách ra như này
                String[] keys = key.split(StringConst.SEMI);
                switch (keys[0]) {
                    case FINDSTOCK:
                        // nếu seq của request là seq mới nhất thì cập nhật dữ liệu
                        if (Integer.parseInt(keys[1]) == seq) {
                            if (rObj.obj != null) {
                                findStock = ((FindStock) rObj.obj);
                                displayDatLenh();
                            }
                        }
                        break;
                    case FINDSTOCK2:
                        // nếu seq của request là seq mới nhất thì cập nhật dữ liệu
                        if (Integer.parseInt(keys[1]) == seq) {
                            if (rObj.obj != null) {
                                findStock = ((FindStock) rObj.obj);
                                displayDatLenh();
                            }
                        }
                        break;

                    default:
                        break;
                }
                break;
        }
        isCheckOrderLoaded();
    }

    /**
     * chuyển lệnh tới màn hình xác nhận lệnh
     *
     * @param order thông tin lệnh
     */
    private void showOrderConfirm(OrderSetParams order) {
        mainActivity
                .sendArgumentToFragment(OrderConfirm.class.getName(), order);
        mainActivity.navigateFragment(OrderConfirm.class.getName());
    }

    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoActionMenu();
    }

    @Override
    protected void isReceivedResponse(String key) {
        super.isReceivedResponse(key);
        isCheckOrderLoaded();
        btn_DatLenh.setEnabled(true);
    }

    @Override
    protected void processErrorOther(String key, ResultObj rObj) {
        super.processErrorOther(key, rObj);
        switch (key) {
            case CHECKORDER:
                int focus = (int) rObj.obj;
                focusTo(focus);
                break;
            case CHECKGTCORDER:
                int gtcfocus = (int) rObj.obj;
                focusTo(gtcfocus);
                break;

            default:
                // vì với request findstock và findstock2 có gắn thêm 1 sequence nên
                // phải tách ra như này
                // vì với request findstock và findstock2 có gắn thêm 1 sequence nên
                // phải tách ra như này
                String[] keys = key.split(StringConst.SEMI);
                switch (keys[0]) {
                    case FINDSTOCK:

                        edttg_Gia.setCeilPrice(StringConst.EMPTY);
                        edttg_Gia.setFloorPrice(StringConst.EMPTY);
                        edttg_Gia.setClosePrice(StringConst.EMPTY);
                        clearFormStockInfo();
                        clearForm();
                        break;

                    default:
                        break;
                }
                break;
        }
    }

    /**
     * focus tới một trường trong form đặt lệnh
     *
     * @param focus trường muốn focus tới. tham khảo trong {@link FIELD_ERROR}
     */
    protected void focusTo(int focus) {
        FIELD_ERROR error = null;
        try {
            error = FIELD_ERROR.values()[focus];
        } catch (IndexOutOfBoundsException e) {
        }
        if (error != null) {
            switch (error) {
                case ERR_AT_QTTY:
                    edttg_SoLuong.requestFocus(View.FOCUS_RIGHT);
                    break;
                case ERR_AT_PRICE:
                    edttg_Gia.requestFocus(View.FOCUS_RIGHT);
                    edttg_Gia.setText(StringConst.EMPTY);
                    break;
                case ERR_AT_PRICETYPE:
                    edttg_LoaiLenh.requestFocus(View.FOCUS_RIGHT);
                    break;
                case ERR_AT_SYMBOL:
                    edt_MaCK.requestFocus(View.FOCUS_RIGHT);
                    break;
                case ERR_AT_FROMDATE:
                    edt_fromdate.requestFocus(View.FOCUS_RIGHT);
                    break;
                case ERR_AT_TODATE:
                    edt_todate.requestFocus(View.FOCUS_RIGHT);
                    break;
                case ERR_AT_SPLITQTTY:
                    edt_SplitQtty.requestFocus(View.FOCUS_RIGHT);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Set thông tin của lệnh từ bên ngoài
     *
     * @param orderInit
     */
    public void setOrderInit(OrderSetParams orderInit) {
        this.orderInit = orderInit;
        if (this.isResumed()) {
            fillOutOrder();
        }
    }

    /**
     * điền thông tin lệnh vào các trường trên màn hình
     */
    private void fillOutOrder() {
        if (orderInit != null) {

            // set số lưu ký
            // set số tiểu khoản
            // afacctno = StaticObjectManager.acctnoItem.ACCTNO;
            // set lệnh thường hay lệnh điều kiện
            if (orderInit.isGTCOrder) {
                setPlaceOrderType(GTCORDER_TYPE);
            } else {
                setPlaceOrderType(NORMALORDER_TYPE);
            }
            // set lệnh mua hay bán
            if (SIDE_NS.equals(orderInit.sideOrder)) {
                functionBtnBanClickNotClearForm();
            } else {
                functionBtnMuaClickNotClearForm();
            }

            // set mã chứng khoán => call getExchangeBySymbol => set loại lệnh
            // => set giá
            if (orderInit.symbolOrder != null
                    && orderInit.symbolOrder.length() > 0) {
                edt_MaCK.setText(orderInit.symbolOrder);
            }
            // set số lượng
            if (orderInit.quantityOrder != null) {
                edttg_SoLuong.setText(orderInit.quantityOrder);
            }

            // set giá
            if (orderInit.priceOrder != null) {
                edttg_Gia.setText(orderInit.priceOrder);
            } else {
                edttg_Gia.setText(StringConst.EMPTY);
            }
            // set khối lượng chia
            // set thời gian đặt
        }
    }

    /**
     * xóa thông tin lệnh trên màn hình
     */
    protected void clearForm() {
        edt_MaCK.setText(StringConst.EMPTY);
        edttg_SoLuong.setText(StringConst.EMPTY);
        edttg_Gia.setText(StringConst.EMPTY);
        edttg_SoLuong.tradeplace = StringConst.EMPTY;
        edttg_Gia.tradeplace = StringConst.EMPTY;
        edttg_Gia.setCeilPrice(StringConst.EMPTY);
        edttg_Gia.setFloorPrice(StringConst.EMPTY);
        edttg_Gia.setClosePrice(StringConst.EMPTY);
        // set loại lệnh về LO
        edttg_LoaiLenh.setText("LO");
        if (placeOrderType == NORMALORDER_TYPE) {
            edt_SplitQtty.setText(StringConst.EMPTY);
            edt_SplitQtty.tradeplace = StringConst.EMPTY;
        } else {
            dld_fromdate.resetDate();
            dld_fromdate.resetDate();
        }
    }

    private void clearFormWhenChangePlaceOrderType() {
        edttg_Gia.setCeilPrice(StringConst.EMPTY);
        edttg_Gia.setFloorPrice(StringConst.EMPTY);
        edttg_Gia.setClosePrice(StringConst.EMPTY);
        if (placeOrderType == NORMALORDER_TYPE) {
            edt_SplitQtty.setText(StringConst.EMPTY);
            edt_SplitQtty.tradeplace = StringConst.EMPTY;
            setCeilFloorForEdittextGia();
        } else {
            dld_fromdate.resetDate();
            dld_fromdate.resetDate();
        }
    }

    private void showKBPrice(boolean isShow, View v) {
        if (isShow) {
            kBoardPrice.show(v);
            kBoardQuantity.setVisibility(View.GONE);
        } else {
            kBoardPrice.hide();
            kBoardQuantity.setVisibility(View.VISIBLE);
        }

    }

    private void showKBQuantity(boolean isShow, View v) {
        if (isShow) {
            kBoardQuantity.show(v);
            kBoardPrice.setVisibility(View.GONE);
        } else {
            kBoardQuantity.hide();
            kBoardPrice.setVisibility(View.VISIBLE);
        }
    }

    private void clearPriceAndQuantity() {
        edttg_SoLuong.setText(StringConst.EMPTY);
        edttg_Gia.setText(StringConst.EMPTY);
        edttg_SoLuong.tradeplace = StringConst.EMPTY;
        edttg_Gia.tradeplace = StringConst.EMPTY;
        edttg_Gia.setCeilPrice(StringConst.EMPTY);
        edttg_Gia.setFloorPrice(StringConst.EMPTY);
        edttg_Gia.setClosePrice(StringConst.EMPTY);
    }
}
