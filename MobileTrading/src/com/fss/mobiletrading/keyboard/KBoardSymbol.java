package com.fss.mobiletrading.keyboard;

import android.app.Service;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.msbuat.mobiletrading.R;

public class KBoardSymbol extends LinearLayout {

    final int KEY_CODE_DEL = 50001;

    Keyboard mKeyboard;
    KeyboardView mKeyboardView;
    TranslateAnimation animateVisible;
    TranslateAnimation animateGone;
    Context context;
    int heightGone = 0;
    int heightVisible = 0;
    View focusCurrent;

    public KBoardSymbol(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.kboard, this);
        this.context = context;

        // Create the Keyboard
        mKeyboard = new Keyboard(context, R.xml.kboard_text);
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
            EditText edt = (EditText) focusCurrent;
            int cursorStart = edt.getSelectionStart();
            int cursorEnd = edt.getSelectionEnd();
            if (primaryCode < 0) {
                return;
            }
            if (primaryCode == KEY_CODE_DEL) {
                if (cursorEnd > cursorStart) {
                    edt.getText().delete(cursorStart, cursorEnd);
                } else if (cursorStart > 0) {
                    edt.getText().delete(cursorStart - 1, cursorStart);
                }
            } else if (primaryCode == 10000) {
                edt.getText().replace(cursorStart, cursorEnd, "_");
            }else if (primaryCode == 10001) {
                edt.getText().replace(cursorStart, cursorEnd, ",");
            }
            else {
                edt.getText().replace(cursorStart, cursorEnd,
                        Character.toString((char) primaryCode));
            }
            edt.setSelection(edt.length());
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
        clearEdittextFocus();
        this.setVisibility(GONE);
    }

    public void clearEdittextFocus() {
        if (focusCurrent != null) {
            focusCurrent.clearFocus();
        }
        mKeyboardView.setEnabled(false);
        focusCurrent = null;
    }

    public void show(View v) {
        this.setVisibility(View.VISIBLE);
        if (v == null) {
            return;
        }
        setEdittextFocus(v);
    }

    public void setEdittextFocus(View v) {
        if (v instanceof EditText) {
            focusCurrent = v;
            mKeyboardView.setEnabled(true);

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
