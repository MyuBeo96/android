package com.fscuat.mobiletrading.design;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.report.FieldInReport;
import com.fss.mobiletrading.object.ItemString2;
import com.fscuat.mobiletrading.R;
import com.fscuat.mobiletrading.design.MyContextMenu.OnItemSelectedListener;

public class SpinnerInReport extends RelativeLayout implements FieldInReport {

	final static int GRAVITY_CENTER_HORI = 0;
	final static int GRAVITY_LEFT = 1;
	final static int GRAVITY_RIGHT = 2;

	ImageButton btn;
	TextView text;
	TextView content;
	View line;
	MyContextMenu contextMenu;
	private String FName;

	public void setFName(String fName) {
		FName = fName;
	}

	public SpinnerInReport(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.spinnerinreport, this);
		text = (TextView) findViewById(R.id.myspinner_label);
		content = (TextView) findViewById(R.id.text_myspinner_spn);
		// line = findViewById(R.id.line);
		// line.setBackgroundColor(getResources().getColor(
		// R.color.lc_separation_color));
		contextMenu = new MyContextMenu(context);
		contextMenu.setTextview(content);
		content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				contextMenu.show();
			}
		});
	}

	public void setChoises(List<?> list) {
		contextMenu.setChoises(list);
	}

	@Override
	public void clear() {
		contextMenu.setSelection(0);
	}

	public void setLabel(String label) {
		text.setText(label);
	}

	public void setSelection(int position) {
		contextMenu.setSelection(position);
	}

	public void setOnItemSelectedListener(OnItemSelectedListener l) {
		contextMenu.setOnItemSelectedListener(l);
	}

	public MyContextMenu getContextMenu() {
		return contextMenu;
	}

	@Override
	public boolean performClick() {
		content.performClick();
		return super.performClick();
	}

	@Override
	public String getFName() {
		return FName;
	}

	@Override
	public String getValue() {
		Object obj = contextMenu.getSelectedItem();
		if (obj instanceof ItemString2) {
			ItemString2 item = (ItemString2) obj;
			return item.getValue();
		}
		return StringConst.EMPTY;
	}
}
