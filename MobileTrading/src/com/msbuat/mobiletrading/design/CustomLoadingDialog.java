package com.msbuat.mobiletrading.design;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;

/**
 * Created by Admin on 06-06-2016.
 */
public class CustomLoadingDialog extends Dialog {

    ImageView imgvloading;
    TextView tvText;
    private AnimationDrawable loadingViewAnim = null;
    public CustomLoadingDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_loading_dialog);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        initView();
        imgvloading.setBackgroundResource(R.anim.animation_loading);
        loadingViewAnim = (AnimationDrawable) imgvloading.getBackground();

    }
    private void initView() {
        imgvloading = (ImageView) findViewById(R.id.imageview_loading);
        tvText = (TextView) findViewById(R.id.textview_content);
    }

    @Override
    public void show() {
        super.show();
        loadingViewAnim.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        loadingViewAnim.stop();
    }
    public void setContentText(String text) {
        tvText.setText(text+"...");
    }
}
