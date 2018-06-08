package com.fss.mobiletrading.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.fscuat.mobiletrading.MyActionBar.Action;

public class BuilderDialogWithAction extends AlertDialog.Builder {

	WindowManager.LayoutParams params;
	TextView tv_Error;
	Context context;

	public BuilderDialogWithAction(Context context) {
		super(context);
		this.context = context;
		setCancelable(false);
		if (tv_Error == null) {
			tv_Error = new TextView(context);
			tv_Error.setGravity(Gravity.CENTER_HORIZONTAL);
			tv_Error.setPadding(0, 20, 0, 20);
			tv_Error.setTypeface(null, Typeface.BOLD);
		}
		setView(tv_Error);
	}

	public AlertDialog create(final Action action, String title, String msg) {

		setTitle(title);
		tv_Error.setText(msg);
		setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				if (action != null) {
					action.performAction(null);
				}
			}
		});
		return create();
	}
}
