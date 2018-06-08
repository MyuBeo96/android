package com.fscuat.mobiletrading.design;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fscuat.mobiletrading.R;

public class DialogLoading extends Dialog {

	LoadingImage loading;

	public DialogLoading(Context context) {
		super(context, Window.FEATURE_NO_TITLE);
		setCancelable(false);
		setContentView(R.layout.dialog_loading);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setBackgroundDrawable(new ColorDrawable(0));
		loading = (LoadingImage) findViewById(R.id.img_loading);
	}

	@Override
	public void show() {
		super.show();
		loading.start();
	}

	@Override
	public void dismiss() {
		super.dismiss();
		loading.stop();
	}
}
