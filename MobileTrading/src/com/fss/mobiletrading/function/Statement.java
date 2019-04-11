package com.fss.mobiletrading.function;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;
import com.tcscuat.mobiletrading.MainActivity;
import com.tcscuat.mobiletrading.R;
import com.tcscuat.mobiletrading.design.MyContextMenu;
import com.tcscuat.mobiletrading.design.MySpinner;

import java.util.ArrayList;
import java.util.List;

public class Statement extends AbstractFragment {

    static final int CashStatement = 0;
    static final int StockStatement = 1;

    int stateStatement;

    String CASH_STATEMENT;
    String STOCK_STATEMENT;

    MySpinner spn_switch;
    List<String> list_spinner;
    AbstractFragment cashStatement;
    AbstractFragment stockStatement;

    public static Statement newInstance(MainActivity mActivity) {
        Statement sefl = new Statement();
        sefl.mainActivity = mActivity;
        sefl.TITLE = mActivity.getStringResource(R.string.Statement);
        return sefl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statement, container, false);
        spn_switch = (MySpinner) view.findViewById(R.id.spn_statement);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        CASH_STATEMENT = getStringResource(R.string.CashStatement);
        STOCK_STATEMENT = getStringResource(R.string.StockStatement);
        if (list_spinner == null) {
            list_spinner = new ArrayList<String>();
            list_spinner.add(CASH_STATEMENT);
            list_spinner.add(STOCK_STATEMENT);
        }
        spn_switch.setChoises(list_spinner);
        cashStatement = mainActivity.mapFragment.get(CashStatement.class
                .getName());
        stockStatement = mainActivity.mapFragment.get(StockStatement.class
                .getName());
    }

    private void initListener() {
        spn_switch
                .setOnItemSelectedListener(new MyContextMenu.OnItemSelectedListener() {

                    @Override
                    public void onItemSelect(Object obj, int position) {
                        if (position == CashStatement) {
                            stateStatement = CashStatement;
                            mainActivity.displayFragment(
                                    CashStatement.class.getName(),
                                    R.id.layout_statement);
                        }
                        if (position == StockStatement) {
                            stateStatement = StockStatement;
                            mainActivity.displayFragment(
                                    StockStatement.class.getName(),
                                    R.id.layout_statement);
                        }
                    }
                });
    }
    @Override
    public void addActionToActionBar() {
        super.addActionToActionBar();
        setBackLogoActionMenu();
    }

    @Override
    protected void process(String key, ResultObj rObj) {

    }

    @Override
    public void onResume() {
        super.onResume();
        spn_switch.setSelection(0);
    }

    @Override
    public void onShowed() {
        super.onShowed();
        if (stateStatement == CashStatement) {
            cashStatement.onShowed();
        }
        if (stateStatement == StockStatement) {
            stockStatement.onShowed();
        }

    }

    @Override
    public void onHide() {
        super.onHide();
        if (stateStatement == CashStatement) {
            cashStatement.onHide();
        }
        if (stateStatement == StockStatement) {
            stockStatement.onHide();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mainActivity.getSupportFragmentManager().beginTransaction()
                .remove(cashStatement).commit();
        mainActivity.getSupportFragmentManager().beginTransaction()
                .remove(stockStatement).commit();
    }

    @Override
    public void notifyChangeAcctNo() {
        super.notifyChangeAcctNo();
        stockStatement.notifyChangeAcctNo();
        cashStatement.notifyChangeAcctNo();
    }

    @Override
    public void refresh() {
        super.refresh();
        if (stateStatement == CashStatement) {
            cashStatement.refresh();
        }
        if (stateStatement == StockStatement) {
            stockStatement.refresh();
        }
    }
}
