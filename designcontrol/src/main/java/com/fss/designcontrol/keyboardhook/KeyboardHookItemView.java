package com.fss.designcontrol.keyboardhook;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by giang.ngo on 09-12-2015.
 */
public class KeyboardHookItemView extends RecyclerView.ViewHolder {

    TextView tv;

    public KeyboardHookItemView(View view) {
        super(view);
        tv = (TextView) view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(tv.getText().toString());
                }
            }
        });
    }

    public void setData(String symbol) {
        tv.setText(symbol);
    }

    KeyboardHookNew.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(KeyboardHookNew.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
