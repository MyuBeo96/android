package com.fss.designcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Mr.Incredible on 3/10/2016.
 */
public class SymbolEdittext extends EditText {

    public enum SymbolEdittextType {
        Single,Multiple
    }

    private SymbolEdittextType symbolEdittextType;

    public SymbolEdittextType getSymbolEdittextType() {
        return symbolEdittextType;
    }

    public void setSymbolEdittextType(SymbolEdittextType symbolEdittextType) {
        this.symbolEdittextType = symbolEdittextType;
    }

    public SymbolEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SymbolEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSymbol(int positionSet, String symbol) {
        if (symbolEdittextType == SymbolEdittextType.Multiple) {
            this.setText(this.getText().toString().substring(0, positionSet) + symbol + ",");
            this.setSelection(this.getText().length());
        } else {
            this.setText(symbol);
            this.setSelection(this.getText().length());
        }

    }

}
