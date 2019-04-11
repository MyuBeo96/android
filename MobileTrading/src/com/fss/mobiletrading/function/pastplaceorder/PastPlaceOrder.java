package com.fss.mobiletrading.function.pastplaceorder;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.fss.designcontrol.SymbolEdittext;
import com.fss.mobiletrading.adapter.PastPlaceOrderAdapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.keyboard.KBoardSymbol;
import com.fss.mobiletrading.keyboard.KeyboardHook;
import com.fss.mobiletrading.object.StockItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.DeviceProperties;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.design.InputDate;

public class PastPlaceOrder extends AbstractFragment {
    final static String PASTPLACEORDER = "PastPlaceOrderParse#PASTPLACEORDER";
    PastPlaceOrderAdapter adapterPastPlaceOrder;
    InputDate edt_dateend;
    InputDate edt_datestart;
    SymbolEdittext edt_stock;
    List<PastPlaceOrderItem> listPastPlaceOrder;
    ListView lv_pastplaceorder;

    public static PastPlaceOrder newInstance(MainActivity mActivity) {

        PastPlaceOrder self = new PastPlaceOrder();
        self.mainActivity = mActivity;
        self.TITLE = mActivity.getStringResource(R.string.PastPlaceOrder);
        return self;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {
        View localView = inflater.inflate(R.layout.pastplaceorder, container,
                false);
        edt_stock = (SymbolEdittext) localView.findViewById(R.id.edt_stock);
        edt_datestart = ((InputDate) localView.findViewById(R.id.fromdate));
        edt_dateend = ((InputDate) localView.findViewById(R.id.todate));
        edt_stock.setSymbolEdittextType(SymbolEdittext.SymbolEdittextType.Single);

        lv_pastplaceorder = ((ListView) localView
                .findViewById(R.id.listview_pastplaceorder));
        initialise();
        initialiseListener();
        return localView;
    }

    private void initialise() {
        if (listPastPlaceOrder == null) {
            listPastPlaceOrder = new ArrayList<PastPlaceOrderItem>();
        } else {
            listPastPlaceOrder.clear();
        }

        if (adapterPastPlaceOrder == null) {
            adapterPastPlaceOrder = new PastPlaceOrderAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, listPastPlaceOrder);
        } else {
            adapterPastPlaceOrder.notifyDataSetChanged();
        }
        lv_pastplaceorder.setAdapter(adapterPastPlaceOrder);
        edt_datestart.setLabel(getStringResource(R.string.TuNgay));
        edt_dateend.setLabel(getStringResource(R.string.DenNgay));
    }

    private void initialiseListener() {
        edt_datestart
                .setOnChangeTextDateListener(new InputDate.OnChangeTextDateListener() {

                    @Override
                    public void onChange(String date) {
                        hideKeyBoard();
                        CallPastPlaceOrder(edt_datestart.toString(),
                                edt_dateend.toString(),
                                StaticObjectManager.acctnoItem.ACCTNO);
                    }
                });
        edt_dateend.setOnChangeTextDateListener(new InputDate.OnChangeTextDateListener() {;
            @Override
            public void onChange(String date) {
                hideKeyBoard();
                CallPastPlaceOrder(edt_datestart.toString(),
                        edt_dateend.toString(),
                        StaticObjectManager.acctnoItem.ACCTNO);
            }
        });
        if (!DeviceProperties.isTablet) {
            lv_pastplaceorder.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    adapterPastPlaceOrder.setExpandPosition(position);
                    hideKeyBoard();
                }
            });
        }
        edt_stock.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                return true;
            }
        });

        edt_stock.setInputType(InputType.TYPE_NULL);
        edt_stock.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edt_stock.setTextIsSelectable(true);
        edt_stock.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    List<StockItem> list = StaticObjectManager.listStock;
                    String[] listAllStock = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        listAllStock[i] = list.get(i).toString();
                    }

                        mainActivity.showKBoardHook(true, v, listAllStock);
                        mainActivity.showKBoardSymbol(true, v);

                } else {
                    hideKeyBoard();

                }
            }
        });
        edt_stock.addTextChangedListener(new TextWatcher() {
            int DELAYTIME = 500;
            Timer timer = new Timer();

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
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        mainActivity.delay.post(new Runnable() {
                            @Override
                            public void run() {
                                CallPastPlaceOrder(edt_datestart.toString(),
                                        edt_dateend.toString(),
                                        StaticObjectManager.acctnoItem.ACCTNO,
                                        edt_stock.getText().toString());


                            }
                        });
                    }
                }, DELAYTIME);


            }
        });
    }

    protected void CallPastPlaceOrder(String fromdate, String todate,
                                      String acctno) {
        PastPlaceOrderService.CallPastPlaceOrder(fromdate, todate, acctno,
                this, PASTPLACEORDER);
    }

    protected void CallPastPlaceOrder(String fromdate, String todate,
                                      String acctno, String stock) {
        PastPlaceOrderService.CallPastPlaceOrder(fromdate, todate, acctno,
                stock, this, PASTPLACEORDER);
    }

    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoActionMenu();
    }

    @Override
    public void onResume() {
        super.onResume();
        edt_datestart.resetDate();
        edt_dateend.resetDate();
    }

    @Override
    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
        CallPastPlaceOrder(edt_datestart.toString(), edt_dateend.toString(),
                StaticObjectManager.acctnoItem.ACCTNO);
    }

    @Override
    protected void process(String key, ResultObj rObj) {
        switch (key) {
            case PASTPLACEORDER:
                List<PastPlaceOrderItem> rs = (List<PastPlaceOrderItem>) rObj.obj;
                listPastPlaceOrder.clear();
                listPastPlaceOrder.addAll(rs);
                adapterPastPlaceOrder.notifyDataSetChanged();
                if (edt_stock.isFocused()) {
                    mainActivity.showKBoardHook(true, edt_stock,
                            StaticObjectManager.getListAllStock());
                }
                break;

            default:
                break;
        }
    }
    private void hideKeyBoard(){
        mainActivity.showKBoardHook(false, null, null);
        mainActivity.showKBoardSymbol(false, null);
    }

}
