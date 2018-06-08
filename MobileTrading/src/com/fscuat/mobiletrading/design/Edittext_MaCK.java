package com.fscuat.mobiletrading.design;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.database.DataSetObserver;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.StockItem;
import com.fscuat.mobiletrading.R;

public class Edittext_MaCK extends LinearLayout {

	Context context;
	Button btn_Giam, btn_Tang;
	public AutoCompleteTextView autoEdittext;
	public ArrayAdapter<StockItem> adapter;
	public List<StockItem> listStock;
	public int currentStock = 0;
	public StockItem stockItem;

	public Edittext_MaCK(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub

		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.edittext_machungkhoan, this);
		btn_Giam = (Button) findViewById(R.id.btn_EdittextMaChungKhoan_Giam);
		btn_Tang = (Button) findViewById(R.id.btn_EdittextMaChungKhoan_Tang);
		autoEdittext = (AutoCompleteTextView) findViewById(R.id.edt_EdittextMaChungKhoan);
		init();

		adapter = new ArrayAdapter<StockItem>(this.context,
				android.R.layout.simple_list_item_1, listStock);
		autoEdittext.setAdapter(adapter);

		adapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();

				int dropDownAnchor = autoEdittext.getDropDownAnchor();
				if (dropDownAnchor == 0) {
					Log.d("autoEdittext", "drop down id = 0"); // popup is not
					// displayed
					return;
				}

				Object item = adapter.getItem(0);
				Log.v("registerDataSetObserver", StringConst.EMPTY + item);
				if (adapter.getCount() == 1) {
					autoEdittext.dismissDropDown();
					currentStock = listStock.indexOf(item);
				} else {
					stockItem = (StockItem) item;
				}

			}
		});

		autoEdittext.setText(listStock.get(currentStock).toString());
//		Edittext_SoLuong.tradeplace = listStock.get(currentStock).getTradePlace();

	}

	public void setText(String str) {
		autoEdittext.setText(str);
	}

	public Editable getText() {
		return autoEdittext.getText();
	}

	public void addTextChangedListener(TextWatcher watcher) {
		autoEdittext.addTextChangedListener(watcher);
	}

	public void removeTextChangedListener(TextWatcher watcher) {
		autoEdittext.removeTextChangedListener(watcher);
	}

	private void init() {
		// TODO Auto-generated method stub
		listStock = StaticObjectManager.listStock;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		autoEdittext.setOnItemClickListener(onItemClickListener);
	}

	public void NextStock() {
		// TODO Auto-generated method stub
		if (currentStock < listStock.size() - 1) {
			currentStock++;
			autoEdittext.setText(listStock.get(currentStock).toString());
		}
	}

	public void PreviousStock() {
		// TODO Auto-generated method stub
		if (currentStock > 0) {
			currentStock--;
			autoEdittext.setText(listStock.get(currentStock).toString());
		}

	}

	public void setOnClickNextListener(OnClickListener clickListener) {
		btn_Tang.setOnClickListener(clickListener);
	}

	public void setOnClickPreviousListener(OnClickListener clickListener) {
		btn_Giam.setOnClickListener(clickListener);
	}
}
