package com.fscuat.mobiletrading.design;

import android.app.Service;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import com.fss.mobiletrading.object.StockItem;
import com.fscuat.mobiletrading.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Admin
 */
public class Edittext_Gia extends LinearLayout {

    public static final int MODE_NORMAL_ORDER = 0;
    public static final int MODE_GTC_ORDER = 1;
    final BigDecimal PRICE_100 = new BigDecimal("10.00").setScale(2,
            RoundingMode.HALF_DOWN);
    final BigDecimal PRICE_4995 = new BigDecimal("49.95").setScale(2,
            RoundingMode.HALF_DOWN);
    final BigDecimal PRICE_500 = new BigDecimal("50.00").setScale(2,
            RoundingMode.HALF_DOWN);
    final BigDecimal PRICE_995 = new BigDecimal("99.5").setScale(1,
            RoundingMode.HALF_DOWN);
    final BigDecimal PRICE_1000 = new BigDecimal("100.0").setScale(1,
            RoundingMode.HALF_DOWN);
    private int modeOrder;

    private String ceilPrice;
    private String floorPrice;
    private String closePrice;
    double doubleCeilPrice;
    double doubleFloorPrice;
    double doubleClosePrice;

    Context context;
    ImageButton btn_Decrease, btn_Increase;
    EditText edtPrice;
    public String tradeplace;
    List<TextWatcher> textwatcher;

    /**
     * set mode hoạt động cho đối tượng Edittext_Gia
     *
     * @param modeOrder nhận 1 trong 2 giá trị {@link Edittext_Gia#MODE_NORMAL_ORDER}
     *                  và {@link Edittext_Gia#MODE_GTC_ORDER}. Mặc định là
     *                  {@link Edittext_Gia#MODE_NORMAL_ORDER}
     */
    public void setModeOrder(int modeOrder) {
        if (modeOrder == MODE_GTC_ORDER) {
            this.modeOrder = MODE_GTC_ORDER;
        } else {
            this.modeOrder = MODE_NORMAL_ORDER;
        }

    }

    public void setCeilPrice(String ceilPrice) {
        this.ceilPrice = ceilPrice;
        doubleCeilPrice = Double.MAX_VALUE;
        try {
            doubleCeilPrice = Double.parseDouble(ceilPrice);
        } catch (Exception e) {
        }
    }

    public void setFloorPrice(String floorPrice) {
        this.floorPrice = floorPrice;
//        doubleFloorPrice = Double.MIN_VALUE;
        doubleFloorPrice = 0.00;
        try {
            doubleFloorPrice = Double.parseDouble(floorPrice);
        } catch (Exception e) {
        }
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
        doubleClosePrice = 0d;
        try {
            doubleClosePrice = Double.parseDouble(closePrice);
        } catch (Exception e) {
        }
    }

    public String getClosePrice() {
        return closePrice;
    }

    public String getFloorPrice() {
        return floorPrice;
    }

    public String getCeilPrice() {
        return ceilPrice;
    }


    public Edittext_Gia(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.priceedittext_layout, this);
        btn_Decrease = (ImageButton) findViewById(R.id.btn_Decrease);
        btn_Increase = (ImageButton) findViewById(R.id.btn_Increace);
        edtPrice = (EditText) findViewById(R.id.edt_price);
        textwatcher = new ArrayList<TextWatcher>();
        edtPrice.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });
        edtPrice.setFilters(new InputFilter[]{new FomatPrice()});
        doubleFloorPrice = 0d;
        doubleCeilPrice = 0d;
        modeOrder = MODE_NORMAL_ORDER;
        TextWatcher tw = new TextWatcher() {
            String old;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                old = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Double value = Double.parseDouble(s.toString());
                    if (value < 0) {
                        removeAllTextChangedListener();
                        edtPrice.setText("0.00");
                        addAllTextChangedListener();
                    }
                } catch (Exception e) {
                }
            }
        };

        textwatcher.add(tw);
        edtPrice.addTextChangedListener(tw);

        btn_Decrease.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
//						edtPrice.setText(changePriceWithStep(edtPrice.getText().toString(), new BigDecimal(-0.1)));
                        BigDecimal gia;
                        try {
                            gia = new BigDecimal(edtPrice.getText().toString());
                        } catch (Exception e) {
                            gia = new BigDecimal(0);
                        }
                        switch (tradeplace) {
                            case StockItem.HOSE:
//                                if (gia.doubleValue() > 0 && gia.compareTo(PRICE_500) <= 0) {
//                                    gia = gia.add(new BigDecimal(-0.1));
//                                } else if (gia.compareTo(PRICE_500) > 0
//                                        && gia.compareTo(PRICE_1000) <= 0) {
//                                    gia = gia.add(new BigDecimal(-0.5));
//                                } else {
//                                    gia = gia.add(new BigDecimal(-1.0));
//                                }
                                if (gia.doubleValue() > 0 && gia.compareTo(PRICE_100) <= 0) {
                                    gia = gia.add(new BigDecimal(-0.01));
                                } else if (gia.compareTo(PRICE_100) > 0
                                        && gia.compareTo(PRICE_500) <=0) {
                                    gia = gia.add(new BigDecimal(-0.05));
                                } else {
                                    gia = gia.add(new BigDecimal(-0.1));
                                }
                                break;
                            case StockItem.HNX:
                                gia = gia.add(new BigDecimal(-0.1));
                                break;
                            case StockItem.UPCOM:
                                gia = gia.add(new BigDecimal(-0.1));
                                break;

                            default:
                                break;
                        }
                        gia = gia.setScale(2, BigDecimal.ROUND_HALF_UP);
                        if (gia.floatValue() < doubleFloorPrice) {
                            edtPrice.setText(String.valueOf(doubleFloorPrice));
                        } else {
                            edtPrice.setText(String.valueOf(gia));
                        }
                    }
                }

        );
        btn_Increase.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
//						edtPrice.setText(changePriceWithStep(edtPrice.getText().toString(), new BigDecimal(0.1)));
                        BigDecimal gia;
                        try {
                            gia = new BigDecimal(edtPrice.getText().toString())
                                    .setScale(2, RoundingMode.HALF_DOWN);
                        } catch (Exception e) {
                            gia = new BigDecimal(0);
                        }
                        if (gia.floatValue() == 0 && doubleCeilPrice != Double.MAX_VALUE) {
                            edtPrice.setText(String.valueOf(doubleCeilPrice));
                            return;
                        }
                        switch (tradeplace) {
                            case StockItem.HOSE:
//                                if (gia.floatValue() >= 0 && gia.compareTo(PRICE_499) <= 0) {
//                                    gia = gia.add(new BigDecimal(0.1));
//                                } else if (gia.compareTo(PRICE_500) >= 0
//                                        && gia.compareTo(PRICE_1000) < 0) {
//                                    gia = gia.add(new BigDecimal(0.5));
//                                } else {
//                                    gia = gia.add(new BigDecimal(1.0));
//                                }
                                if (gia.doubleValue() > 0 && gia.compareTo(PRICE_100) < 0) {
                                    gia = gia.add(new BigDecimal(0.01));
                                } else if (gia.compareTo(PRICE_100) >= 0
                                        && gia.compareTo(PRICE_500) < 0) {
                                    gia = gia.add(new BigDecimal(0.05));
                                } else {
                                    gia = gia.add(new BigDecimal(0.1));
                                }

                                break;
                            case StockItem.HNX:
                                gia = gia.add(new BigDecimal(0.1));
                                break;
                            case StockItem.UPCOM:
                                gia = gia.add(new BigDecimal(0.1));
                                break;

                            default:
                                break;
                        }
                        gia = gia.setScale(2, BigDecimal.ROUND_HALF_UP);
                        if (gia.floatValue() > doubleCeilPrice) {
                            edtPrice.setText(String.valueOf(doubleCeilPrice));
                        } else {
                            edtPrice.setText(String.valueOf(gia));
                        }
                    }
                }

        );
    }

    private String changePriceWithStep(String price, BigDecimal step) {
        BigDecimal value;
        try {
            value = new BigDecimal(price)
                    .setScale(2, RoundingMode.HALF_DOWN);
        } catch (Exception e) {
            return closePrice;
        }

        if (value.floatValue() == 0) {
            return closePrice;
        }

        value = value.add(step);
        value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (modeOrder == MODE_NORMAL_ORDER) {
            if (value.floatValue() > doubleCeilPrice) {
                return ceilPrice;
            }
            if (value.floatValue() < doubleFloorPrice) {
                return floorPrice;
            }
        }
        String priceChanged = String.valueOf(value);
        if (modeOrder == MODE_NORMAL_ORDER) {
            priceChanged = (value.floatValue() > doubleCeilPrice) ? ceilPrice : priceChanged;
            priceChanged = (value.floatValue() < doubleFloorPrice) ? floorPrice : priceChanged;
        }
        return priceChanged;
    }

    public void setText(String str) {
        edtPrice.setText(str);
    }

    public Editable getText() {
        return edtPrice.getText();
    }

    @Override
    public void setEnabled(boolean enabled) {
        edtPrice.setEnabled(enabled);
        btn_Decrease.setEnabled(enabled);
        btn_Increase.setEnabled(enabled);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        edtPrice.addTextChangedListener(textWatcher);
        textwatcher.add(textWatcher);
    }

    public void removeTextChangedListener(TextWatcher textWatcher) {
        edtPrice.removeTextChangedListener(textWatcher);
        textwatcher.remove(textWatcher);
    }

    private void addAllTextChangedListener() {
        for (int i = 0; i < textwatcher.size(); i++) {
            edtPrice.addTextChangedListener(textwatcher.get(i));
        }
    }

    private void removeAllTextChangedListener() {
        for (int i = 0; i < textwatcher.size(); i++) {
            edtPrice.removeTextChangedListener(textwatcher.get(i));
        }
    }

    public void setOnFocusChangeListener(OnFocusChangeListener listener) {
        edtPrice.setOnFocusChangeListener(listener);

    }

    public EditText toEditText() {
        return edtPrice;
    }

    static class FomatPrice implements InputFilter {
        Pattern mPattern;

        public FomatPrice() {
//        (([0-9]+((\.[0-9]{0,2})?)||(\.)?)    ((\.[0-9]{0,2})?)||(\.)?)
        /*
        ([0-9]?+((\.[0-9]{0,2})?)||(\.)?)
        - [0-9]? có thể chứa nhiều số từ 0 đến 9 hoặc không( trường hợp bắt đầu từ dấu chấm)
        - +((\.[0-9]{0,2})?) có thể sau đó là dấu \. tiếp theo là số từ 0 - 9, lặp lại 0 dến 2 lần [0-9]
        -  ||(\.)? hoặc có thể có dấu chấm hoặc không
         */
            mPattern = Pattern.compile("(([0-9]?)+((\\.[0-9]{0,2})?)||(\\.)?)");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }
}

