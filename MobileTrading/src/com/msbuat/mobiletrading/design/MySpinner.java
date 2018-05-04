package com.msbuat.mobiletrading.design;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fss.mobiletrading.consts.StringConst;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.design.MyContextMenu.OnItemSelectedListener;

public class MySpinner extends RelativeLayout {

	final static int GRAVITY_CENTER_HORI = 0;
	final static int GRAVITY_LEFT = 1;
	final static int GRAVITY_RIGHT = 2;

	ImageButton btn;
	TextView label;
	TextView content;
	View line;
	MyContextMenu contextMenu;

	public MySpinner(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.myspinner, this);
		label = (TextView) findViewById(R.id.myspinner_label);
		content = (TextView) findViewById(R.id.text_myspinner_spn);
		btn = (ImageButton) findViewById(R.id.btn_myspinner);
		line = findViewById(R.id.line);
		line.setBackgroundColor(getResources().getColor(
				R.color.lc_separation_color));
		label.setVisibility(VISIBLE);
		contextMenu = new MyContextMenu(context);
		contextMenu.setTextview(content);
		content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				contextMenu.show();
			}
		});
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				contextMenu.show();
			}
		});
	}

	public MySpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.MySpinner);
		inflater.inflate(R.layout.myspinner, this);
		label = (TextView) findViewById(R.id.myspinner_label);
		content = (TextView) findViewById(R.id.text_myspinner_spn);
		btn = (ImageButton) findViewById(R.id.btn_myspinner);
		line = findViewById(R.id.line);
		line.setBackgroundColor(typedArray.getColor(
				R.styleable.MySpinner_separationColor,
				getResources().getColor(R.color.lc_separation_color)));
		contextMenu = new MyContextMenu(context);
		contextMenu.setTextview(content);
		content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				contextMenu.show();
			}
		});
		label.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray
				.getDimensionPixelSize(
						R.styleable.MySpinner_labelsize_dimen,
						getResources().getDimensionPixelSize(
								R.dimen.m_label_size)));
		content.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray
				.getDimensionPixelSize(
						R.styleable.MySpinner_contentsize_dimen,
						getResources().getDimensionPixelSize(
								R.dimen.m_content_size)));
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				contextMenu.show();
			}
		});
		label.setText(typedArray.getString(R.styleable.MySpinner_textMySpinner));
		if (typedArray.getBoolean(R.styleable.MySpinner_hasLabel, false)) {
			label.setVisibility(VISIBLE);
		}
		switch (typedArray.getInteger(R.styleable.MySpinner_gravity_hori,
				GRAVITY_LEFT)) {
		case GRAVITY_LEFT:
			content.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			break;
		case GRAVITY_RIGHT:
			content.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
			break;
		case GRAVITY_CENTER_HORI:
			content.setGravity(Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL);
			break;

		default:
			break;
		}
	}

	/**
	 * set dữ liệu cho contextmenu
	 * 
	 * @param list
	 */
	public void setChoises(List<?> list) {
		contextMenu.setChoises(list);
	}

	/**
	 * {@link MySpinner#setChoises(List)}
	 * 
	 * @param list
	 * @param gravity
	 *            gravity cho từng item trong contextmenu
	 */
	public void setChoises(List<?> list, int gravity) {
		contextMenu.setChoises(list, gravity);
	}

	/**
	 * Xóa tất cả item của spinner,và xóa text đang hiển thị
	 */
	public void clear() {
		contextMenu.clear();
		content.setText(StringConst.EMPTY);
	}

	public void setLabel(TextView label) {
		this.label = label;
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
}
