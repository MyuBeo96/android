package com.fss.designcontrol.keyboardhook;

import android.app.Service;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.fss.designcontrol.R;
import com.fss.designcontrol.SymbolEdittext;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by giang.ngo on 09-12-2015.
 */
public class KeyboardHook extends LinearLayout {

    RecyclerView recyclerView;
    KeyboardHookAdapter adapter;
    List<String> listSymsToShow;
    String[] allSyms;
    SymbolEdittext edt;
    TextWatcher textWatcher;
    String oldListSymbols = "";
    String textEditted;
    int index = 0;

    public KeyboardHook(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.keyboardhook_new, this);
        initTextWatcher();
        recyclerView = (RecyclerView) findViewById(R.id.kbhook_recyclerview);
        initRecyclerView(context);
    }

    private void initTextWatcher() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (s.toString().contains(",")) {
                    index = str.lastIndexOf(",");
                    oldListSymbols = str.substring(0, index + 1);
                    textEditted = str.substring(index + 1, str.length());
                } else {
                    oldListSymbols = "";
                    textEditted = str;
                }
                if (isCustomKeyboardVisible()) {
                    refreshDataOfKeyboardHook();
                }
            }
        };
    }

    private void initRecyclerView(Context context) {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        listSymsToShow = new ArrayList<>();
        adapter = new KeyboardHookAdapter(listSymsToShow);
        adapter.setOnItemClickListener(new KeyboardHookNew.OnItemClickListener() {
            @Override
            public void onClick(String symbol) {
                if (edt != null) {
                    edt.setSymbol(oldListSymbols.length(), symbol);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void show(View v, String[] syms) {
        if (v instanceof SymbolEdittext) {
            edt = (SymbolEdittext) v;
            edt.addTextChangedListener(textWatcher);
            textEditted = edt.getText().toString();
        } else {
            edt = null;
        }
        allSyms = syms;
        refreshDataOfKeyboardHook();
        this.setVisibility(VISIBLE);
    }

    public void refreshWithSyms(String[] syms) {
        allSyms = syms;
        refreshDataOfKeyboardHook();
    }

    public void hide() {
        edt = null;
        this.setVisibility(GONE);
    }

    private void refreshDataOfKeyboardHook() {
        String filterText = (edt == null || edt.getText().toString().endsWith(",")) ? "" : textEditted;
//        String filterText = (edt == null || edt.getText().toString().endsWith(",")) ? "" : edt.getText().toString();
        filterSymbolByText(filterText);
        adapter.notifyDataSetChanged();
    }

    private void filterSymbolByText(String filterText) {
        listSymsToShow.clear();
        if (allSyms == null) {
            return;
        }

        // neu filterText rong => khong filter (add het tat ca cac ma)
        if (filterText == null || filterText.length() == 0) {
            int count = allSyms.length;
            for (int i = 0; i < count; i++) {
                listSymsToShow.add(allSyms[i]);
            }
            return;
        }

        int count = allSyms.length;
        for (int i = 0; i < count; i++) {
            String symCode = allSyms[i];
            if (symCode.startsWith(filterText)) {
                listSymsToShow.add(allSyms[i]);
            }
        }

    }

    public interface OnItemClickListener {
        public void onClick(String symbol);
    }

    public boolean isCustomKeyboardVisible() {
        return (this.getVisibility() == VISIBLE);
    }
}
