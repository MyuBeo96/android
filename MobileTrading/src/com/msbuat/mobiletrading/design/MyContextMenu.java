package com.msbuat.mobiletrading.design;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.consts.StringConst;
import com.msbuat.mobiletrading.R;
import com.google.android.gms.drive.internal.ad;
import com.google.android.gms.drive.internal.i;
import com.google.android.gms.internal.co;

public class MyContextMenu extends Dialog {

	ListView lv;
	TextView text;
	List<Object> listChoise;
	ContextMenuAdapter<?> adapter;
	Context context;
	TextView textview;
	OnItemSelectedListener onItemSelectedListener;
	Object selectedItem;

	public MyContextMenu(Context context) {
		super(context);
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(true);
		setContentView(R.layout.dialog_contextmenu);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(lp);
		getWindow().setGravity(Gravity.BOTTOM);
		getWindow().setBackgroundDrawable(new ColorDrawable(0));
		lv = (ListView) findViewById(R.id.lv_dialog_contextmenu);
		text = (TextView) findViewById(R.id.text_dialog_contextmenu_cancel);
		text.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				MyContextMenu.this.dismiss();
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				setSelectedItem(listChoise.get(position), position);
			}
		});
	}

	/**
	 * Xóa tất cả menuitem
	 */
	public void clear() {
		listChoise.clear();
	}

	/**
	 * set dữ liệu cho contextmenu
	 * 
	 * @param list
	 */
	public void setChoises(List<?> list) {
		if (list == null) {
			return;
		}
		if (listChoise == null) {
			listChoise = new ArrayList<Object>();
		} else {
			listChoise.clear();
		}
		listChoise.addAll(list);
		if (adapter == null) {
			adapter = new ContextMenuAdapter<>(context,
					android.R.layout.simple_list_item_1, listChoise);
			lv.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		// if (list.size() >= 8) {
		// lv.setLayoutParams(lpmorethan8);
		// } else {
		// lv.setLayoutParams(lplessthan8);
		// }
		if (listChoise.size() > 0) {
			setSelectedItem(listChoise.get(0), 0);
		} else if (listChoise.size() == 0) {
			textview.setText(StringConst.EMPTY);
			selectedItem = null;
		}
	}

	/**
	 * set dữ liệu cho contextmenu
	 * 
	 * @param list
	 * @param gravity
	 *            gravity của từng item trong context view
	 */
	public void setChoises(List<?> list, int gravity) {
		setChoises(list);
		adapter.setGravity(gravity);
	}

	public void setOnItemSelectedListener(
			OnItemSelectedListener onItemSelectedListener) {
		this.onItemSelectedListener = onItemSelectedListener;
	}

	public void setSelection(int position) {
		if (listChoise != null && listChoise.size() - 1 >= position) {
			setSelectedItem(listChoise.get(position), position);
		}
	}

	public void setSelection(Object obj) {
		int position = listChoise.indexOf(obj);
		if (position != -1) {
			setSelection(position);
		}
	}

	public void setTextview(TextView textview) {
		this.textview = textview;
	}

	private void setSelectedItem(Object selectedItem, int position) {
		if (this.isShowing()) {
			this.dismiss();
		}
		this.selectedItem = selectedItem;
		if (textview != null) {
			textview.setText(selectedItem.toString());
		}
		if (onItemSelectedListener != null) {
			onItemSelectedListener.onItemSelect(selectedItem, position);
		}

	}

	/**
	 * 
	 * @return trả v�? phần tử được ch�?n trong mảng
	 * @throws bắn
	 *             ra exception NullPointerException nếu mảng dữ liệu không có
	 *             phần tử nào
	 */
	public Object getSelectedItem() {
		return selectedItem;
	}

	public interface OnItemSelectedListener {
		/**
		 * 
		 * @param item
		 *            object đã ch�?n
		 * @param position
		 *            vị trí của item đã ch�?n
		 */
		public void onItemSelect(Object obj, int position);
	}

	class ContextMenuItemView extends LinearLayout {
		TextView text;

		public ContextMenuItemView(Context context, int gravity) {
			super(context);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.contextmenu_item, this);
			text = (TextView) findViewById(R.id.text);
			text.setGravity(gravity);
		}

		private void setView(String str) {
			text.setText(str);
		}
	}

	class ContextMenuAdapter<T> extends ArrayAdapter<T> {

		List<?> list;
		Context context;
		int gravity = Gravity.CENTER;

		public ContextMenuAdapter(Context context, int resource, List<T> objects) {
			super(context, resource, objects);
			list = objects;
			this.context = context;
		}

		/**
		 * 
		 * @param gravity
		 *            gravity của từng item trong context view
		 */
		public void setGravity(int gravity) {
			this.gravity = gravity;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new ContextMenuItemView(context, gravity);
			}
			((ContextMenuItemView) convertView).setView(list.get(position)
					.toString());
			return convertView;
		}
	}
}
