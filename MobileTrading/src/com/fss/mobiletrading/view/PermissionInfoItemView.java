package com.fss.mobiletrading.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fscuat.mobiletrading.R;
import com.fss.mobiletrading.function.authorizationinfo.PermissionInfoItem;
import com.fss.mobiletrading.object.AcctnoItem;

public class PermissionInfoItemView extends LinearLayout {
	CheckBox checkbox_View;
	CheckBox checkbox_Send;
	CheckBox checkbox_Edit;
	CheckBox checkbox_Search;
	TextView tv_FunctionName;

	public PermissionInfoItemView(Context context) {
		super(context);
		((LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.permission_table_item, this);
		tv_FunctionName = ((TextView) findViewById(R.id.text_permissiontable_item_functionname));
		checkbox_View = ((CheckBox) findViewById(R.id.checkbox_permissiontable_item_view));
		checkbox_Send = ((CheckBox) findViewById(R.id.checkbox_permissiontable_item_send));
		checkbox_Edit = ((CheckBox) findViewById(R.id.checkbox_permissiontable_item_edit));
		checkbox_Search = ((CheckBox) findViewById(R.id.checkbox_permissiontable_item_search));

	}

	public void setView(PermissionInfoItem item) {
		tv_FunctionName.setText(item.OTMNCODE);
		String permiss = item.OTRIGHT;
		if (permiss != null && permiss.charAt(0) == 'Y') {
			checkbox_View.setChecked(true);
		} else {
			checkbox_View.setChecked(false);
		}
		if (permiss != null && permiss.charAt(1) == 'Y') {
			checkbox_Send.setChecked(true);
		} else {
			checkbox_Send.setChecked(false);
		}
		if (permiss != null && permiss.charAt(2) == 'Y') {
			checkbox_Edit.setChecked(true);
		} else {
			checkbox_Edit.setChecked(false);
		}
		if (permiss != null && permiss.charAt(3) == 'Y') {
			checkbox_Search.setChecked(true);
		} else {
			checkbox_Search.setChecked(false);
		}
	}
}
