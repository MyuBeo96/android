package com.fss.mobiletrading.keyboard;

import android.app.Service;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fss.mobiletrading.consts.StringConst;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.DeviceProperties;

public class KBoardQuantity extends LinearLayout {

    static final String Key00 = "00";
    static final String Key000 = "000";
    static final int KEY_CODE_DEL = 50001;
    static final int KEY_CODE_XOA = 50002;
    static final int add1000 = 50003;
    static final int sub1000 = 50004;
    static final int add500 = 50005;
    static final int sub500 = 50006;
    static final int add100 = 50007;
    static final int sub100 = 50008;
    static final int adds00 = 50009;
    static final int adds000 = 50010;

    Keyboard mKeyboard;
    KeyboardView mKeyboardView;
    TranslateAnimation animateVisible;
    TranslateAnimation animateGone;
    Context context;
    int heightGone = 0;
    int heightVisible = 0;
    View focusCurrent;

    public KBoardQuantity(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        if (DeviceProperties.isTablet) {
            inflater.inflate(R.layout.kboard2, this);
            mKeyboard = new Keyboard(context, R.xml.t_kboard_text_quantity);
        } else {
            inflater.inflate(R.layout.kboard, this);
            mKeyboard = new Keyboard(context, R.xml.kboard_text_quantity);
        }
        this.context = context;

        // Lookup the KeyboardView
        mKeyboardView = (KeyboardView) findViewById(R.id.keyboardview);
        // Attach the keyboard to the view
        mKeyboardView.setKeyboard(mKeyboard);

        // hiệu ứng hiện ra ô màu trắng khi nhấn vào 1 ô bất kỳ
        mKeyboardView.setPreviewEnabled(false);

        // Install the key handler
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
    }

    private OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener() {
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (focusCurrent == null) {
                return;
            }
            EditText edt = ((EditText) focusCurrent);
            final Editable editable = edt.getText();
            int cursorStart = edt.getSelectionStart();
            int cursorEnd = edt.getSelectionEnd();
            if (primaryCode < 0 || editable == null) {
                return;
            }
            String oldstr = editable.toString();
            String newstr = null;
            if (oldstr.contains(StringConst.SEMI)) {
                newstr = oldstr.replace(StringConst.SEMI, StringConst.EMPTY);
            } else {
                newstr = oldstr;
            }
            long test = 0l;
            try {
                test = Long.parseLong(newstr);
            } catch (NumberFormatException e) {
            }
            switch (primaryCode) {
                case KEY_CODE_DEL:
                    if (cursorEnd > cursorStart) {
                        edt.getText().delete(cursorStart, cursorEnd);
                    } else if (cursorStart > 0) {
                        edt.getText().delete(cursorStart - 1, cursorStart);
                    }
                    break;
                case KEY_CODE_XOA:
                    editable.clear();
                    break;
                case add1000:
                    test = test + 1000;
                    edt.setText(String.valueOf(test));
                    break;
                case sub1000:
                    test = test - 1000;
                    edt.setText(String.valueOf(test));
                    break;
                case add500:
                    test = test + 500;
                    edt.setText(String.valueOf(test));
                    break;
                case sub500:
                    test = test - 500;
                    edt.setText(String.valueOf(test));
                    break;
                case add100:
                    test = test + 100;
                    edt.setText(String.valueOf(test));
                    break;
                case sub100:
                    test = test - 100;
                    edt.setText(String.valueOf(test));
                    break;
                case adds00:
                    editable.replace(cursorStart, cursorEnd, Key00);
                    break;
                case adds000:
                    editable.replace(cursorStart, cursorEnd, Key000);
                    break;
                default:
                    editable.replace(cursorStart, cursorEnd,
                            Character.toString((char) primaryCode));
                    break;
            }
        }


        @Override
        public void onPress(int arg0) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeUp() {
        }
    };

    public void hide() {
        if (focusCurrent != null) {
            focusCurrent.clearFocus();
        }
        mKeyboardView.setEnabled(false);
        focusCurrent = null;
        this.setVisibility(GONE);
    }

    public void show(View v) {
        if (v instanceof EditText) {
            focusCurrent = v;
            mKeyboardView.setEnabled(true);
            this.setVisibility(View.VISIBLE);
        }
    }

    public boolean isVisible() {
        return (this.getVisibility() == VISIBLE);
    }

    private void visibleWithAnimation(View view) {
        if (animateVisible == null || heightVisible == 0) {
            animateVisible = new TranslateAnimation(0, 0, view.getHeight(), 0);
            animateVisible.setDuration(600);
            heightVisible = view.getHeight();
        }
        view.startAnimation(animateVisible);
        view.setVisibility(View.VISIBLE);
    }

    private void goneWithAnimation(View view) {
        if (animateGone == null || heightGone == 0) {
            animateGone = new TranslateAnimation(0, 0, 0, view.getHeight());
            animateGone.setDuration(500);
            heightGone = view.getHeight();
        }
        view.startAnimation(animateGone);
        view.setVisibility(View.GONE);
    }

    public void registerEdittext(View v) {
        if (v instanceof EditText) {
            focusCurrent = v;
        }
    }

    public void unRegisterEdittext() {

        if (focusCurrent != null) {
            focusCurrent.clearFocus();
        }
        focusCurrent = null;
    }

}
