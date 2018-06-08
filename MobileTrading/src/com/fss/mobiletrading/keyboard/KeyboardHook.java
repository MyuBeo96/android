package com.fss.mobiletrading.keyboard;

import java.util.ArrayList;
import java.util.List;

import com.devsmart.android.ui.HorizontalListView;
import com.fss.mobiletrading.adapter.KBHookAdapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.StockItem;
import com.fscuat.mobiletrading.R;

import android.app.Service;
import android.content.Context;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

public class KeyboardHook extends LinearLayout {
	HorizontalListView kbhook;
	KBHookAdapter adapterKBHook;
	List<String> list_KBHook;
	String[] listAllStock;
	String[] listStock;
	EditText edt;
	TextWatcher textWatcher;

	public KeyboardHook(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.keyboardhook, this);
		kbhook = (HorizontalListView) findViewById(R.id.keyboardhook);
		init(context);
		initListener();
	}

	private void init(Context context) {
		// khởi tạo mảng các chứng khoán
		List<StockItem> list = StaticObjectManager.listStock;
		listAllStock = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			listAllStock[i] = list.get(i).toString();
		}

		list_KBHook = new ArrayList<String>();
		adapterKBHook = new KBHookAdapter(context,
				android.R.layout.simple_list_item_1, list_KBHook);
		kbhook.setAdapter(adapterKBHook);
		textWatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				setKBHookData(s.toString());
			}
		};
	}

	private void initListener() {
		kbhook.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (edt == null) {
					return;
				}
				String sym = list_KBHook.get(position).toString();
				try {
					edt.setText(sym);
				} catch (Exception e) {
				}

			}
		});
	}

	public void setKBHookData(String start) {
		// hiển thị trong 1 tập mã cho trước
		if (listStock != null) {
			if (start == null || start.length() == 0) {
				list_KBHook.clear();
				for (int i = 0; i < listStock.length; i++) {
					list_KBHook.add(listStock[i]);
				}
				adapterKBHook.notifyDataSetChanged();
				return;
			}
			list_KBHook.clear();
			for (int i = 0; i < listStock.length; i++) {
				String sym = listStock[i];
				if (sym.startsWith(start)) {
					list_KBHook.add(sym);
				}
			}
			if (list_KBHook.size() == 1
					&& start.equals(list_KBHook.get(0).toString())) {
				// nếu chỉ tồn tại 1 mã chứng khoán khớp với ký tự đã gõ thì ẩn
				// bàn phím
				if (edt != null) {
					edt.clearFocus();
				}
				return;
			}
			adapterKBHook.notifyDataSetChanged();

		}
		// hiển thị trong tất cả các mã
		else {
			if (start == null || start.length() == 0) {
				list_KBHook.clear();
				for (int i = 0; i < listAllStock.length; i++) {
					list_KBHook.add(listAllStock[i]);
				}
				adapterKBHook.notifyDataSetChanged();
				return;
			}
			list_KBHook.clear();
			for (int i = 0; i < listAllStock.length; i++) {
				String sym = listAllStock[i];
				if (sym.startsWith(start)) {
					list_KBHook.add(sym);
				}
			}
			if (list_KBHook.size() == 1
					&& start.equals(list_KBHook.get(0).toString())) {
				// nếu chỉ tồn tại 1 mã chứng khoán khớp với ký tự đã gõ thì ẩn
				// bàn phím
				if (edt != null) {
					edt.clearFocus();
				}
				return;
			}
			adapterKBHook.notifyDataSetChanged();
		}
	}

	public void show(View edt, String[] listStock) {
		this.setVisibility(VISIBLE);
		if (edt == null) {
			return;
		}
		setEdittextFocus(edt, listStock);
	}

	public void setEdittextFocus(View edt, String[] listStock) {
		if (edt instanceof EditText) {
			this.edt = (EditText) edt;
			this.edt.addTextChangedListener(textWatcher);
			this.edt.setText(StringConst.EMPTY);
			this.listStock = listStock;
			setKBHookData(null);
			kbhook.scrollTo(0);
		}
	}

	public void hide() {
		clearEdittextFocus();
		this.setVisibility(GONE);
	}

	public void clearEdittextFocus() {
		this.list_KBHook.clear();
		adapterKBHook.notifyDataSetChanged();
		this.listStock = null;
		if (edt != null) {
			edt.removeTextChangedListener(textWatcher);
		}
		edt = null;
	}
}
