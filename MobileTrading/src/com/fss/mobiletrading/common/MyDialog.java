package com.fss.mobiletrading.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class MyDialog extends Dialog {

	WindowManager.LayoutParams params;

	public MyDialog(Context context, int layout) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(true);
		params = getWindow().getAttributes();
		params.width = LayoutParams.WRAP_CONTENT;
		params.height = LayoutParams.WRAP_CONTENT;
		getWindow().setGravity(Gravity.BOTTOM | Gravity.LEFT);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		getWindow().setBackgroundDrawable(new ColorDrawable(0));
		setContentView(layout);
	}

	public void showAtPosition(View v) {
		int yCoordinates = v.getHeight();
		params.x = 5;
		params.y = yCoordinates;
		this.show();
	}
}
