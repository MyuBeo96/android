package com.fss.mobiletrading.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.devsmart.android.ui.HorizontalListView;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.keyboard.KBoardPrice;
import com.fss.mobiletrading.keyboard.KBoardQuantity;
import com.msbuat.mobiletrading.R;

public class Common {

	static View.OnKeyListener listener;

	public static void setupUI(View view, final Activity activity) {
		// xem lại chỗ này, if else quá thừa
		if (view != null) {
			if (!(view instanceof EditText)
					&& !(view instanceof KBoardQuantity)
					&& !(view instanceof KBoardPrice)) {
				if ((view.isEnabled()) && !(view instanceof HorizontalListView)) {
					view.setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							Common.hideSoftKeyboard(activity);
							return false;
						}
					});
				}
			} else {
				if (view instanceof EditText) {
					view.setOnKeyListener(new OnKeyListener() {

						@Override
						public boolean onKey(View v, int keyCode, KeyEvent event) {
							if (keyCode == 66)
								Common.hideSoftKeyboard(activity);
							return false;
						}
					});
				}
				if (view instanceof KBoardPrice
						|| view instanceof KBoardQuantity) {
					return;
				}
			}

			if ((view instanceof ViewGroup))
				for (int i = 0; i <= ((ViewGroup) view).getChildCount(); i++) {
					setupUI(((ViewGroup) view).getChildAt(i), activity);
				}
		}

	}

	public static void setupUI(View view, final Dialog dialog) {
		// xem lại chỗ này, if else quá thừa
		if (view != null) {
			if ((!(view instanceof EditText)
					&& !(view instanceof KBoardQuantity) && !(view instanceof KBoardPrice))) {
				if ((view.isEnabled()) && !(view instanceof HorizontalListView)) {
					view.setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							if (!(v instanceof Button)) {
								Common.hideSoftKeyboard(dialog);
							}
							return false;
						}
					});
				}
			} else {
				if (view instanceof EditText) {
					view.setOnKeyListener(new OnKeyListener() {

						@Override
						public boolean onKey(View v, int keyCode, KeyEvent event) {
							if (keyCode == 66)
								Common.hideSoftKeyboard(dialog);
							return false;
						}
					});
				}
				if (view instanceof KBoardPrice
						|| view instanceof KBoardQuantity) {
					return;
				}
			}
			if ((view instanceof ViewGroup))
				for (int i = 0; i <= ((ViewGroup) view).getChildCount(); i++) {
					setupUI(((ViewGroup) view).getChildAt(i), dialog);
				}
		}

	}

	public static void hideSoftKeyboard(Activity activity) {
		if ((activity != null) && (activity.getCurrentFocus() != null)) {
			InputMethodManager imm = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(activity.getCurrentFocus()
					.getWindowToken(), 0);
			activity.getCurrentFocus().clearFocus();
		}
	}

	public static void hideSoftKeyboard(Dialog dialog) {
		if ((dialog != null) && (dialog.getCurrentFocus() != null)) {
			InputMethodManager imm = (InputMethodManager) dialog.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(dialog.getCurrentFocus()
					.getWindowToken(), 0);
			dialog.getCurrentFocus().clearFocus();
		}
	}

	public static String[] readFile(File file) {
		try {
			FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			StringBuilder builder = new StringBuilder();
			while (true) {
				String line = bufferedReader.readLine();
				if (line != null) {
					builder.append(line + "#");
				} else {
					break;
				}
			}
			bufferedReader.close();
			reader.close();
			if (builder.length() > 0) {
				builder.deleteCharAt(builder.length() - 1);
			}

			return builder.toString().split("#");
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();

		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public static void writeFile(File file, String str) {
		try {

			FileOutputStream fileOutputStream = new FileOutputStream(file);
			OutputStreamWriter dataOutputStream = new OutputStreamWriter(
					fileOutputStream, "UTF8");
			dataOutputStream.write(str);
			dataOutputStream.close();
			fileOutputStream.close();
			return;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}

	// public static void showDialog(Activity activity, String title,
	// String message) {
	// AlertDialog.Builder buider = new AlertDialog.Builder(activity);
	// buider.setTitle(title);
	// buider.setMessage(message);
	// buider.setCancelable(false);
	// buider.setPositiveButton("OK", new OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// }
	// });
	// buider.create().show();
	// }

	// public static void showDialog2(Activity activity, String title,
	// String message, DialogInterface.OnClickListener positive,
	// DialogInterface.OnClickListener negative) {
	// AlertDialog.Builder buider = new AlertDialog.Builder(activity);
	// buider.setTitle(title);
	// buider.setMessage(message);
	// buider.setCancelable(false);
	// buider.setPositiveButton("NO", negative);
	// buider.setNegativeButton("YES", positive);
	// buider.create().show();
	// }

	public static RotateAnimation getRotateAnimation() {
		RotateAnimation localRotateAnimation = new RotateAnimation(0.0F,
				360.0F, 1, 0.54F, 1, 0.54F);
		localRotateAnimation.setInterpolator(new LinearInterpolator());
		localRotateAnimation.setRepeatCount(-1);
		localRotateAnimation.setDuration(1200L);
		return localRotateAnimation;
	}

	public static String formatAmount(Double value) {
		double rounded = Math.round(value);
		DecimalFormat decimalFormat = new DecimalFormat();
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setGroupingSeparator(',');
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		return decimalFormat.format(rounded);
	}

	public static String formatAmount(Long value) {
		double rounded = Math.round(value);
		DecimalFormat decimalFormat = new DecimalFormat();
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setGroupingSeparator(',');
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		return decimalFormat.format(rounded);
	}

	public static String formatAmount(String paramString) {
		Double.valueOf(0.0D);
		try {
			String str2 = formatAmount(Double.valueOf(Double
					.parseDouble(paramString.replace(",", StringConst.EMPTY))));
			return str2;
		} catch (Exception localException) {
			return StringConst.EMPTY;
		}
	}

	public static void registrySeparatorNumber(final EditText edt) {
		edt.addTextChangedListener(new TextWatcher() {

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
				String text = null;
				if (!s.toString().equals(StringConst.EMPTY)) {
					try {
						text = String.format(
								"%,d",
								Long.parseLong(s.toString().replace(",",
										StringConst.EMPTY))).replace(".", ",");
					} catch (Exception e) {

					}
					if (text != null) {
						edt.removeTextChangedListener(this);
						s.clear();
						s.append(text);
						edt.addTextChangedListener(this);
					}
				}
			}
		});
	}

	public static DatePickerDialog createDialogChonDate(Activity pr_Activity,
			final EditText pr_EditText) {

		pr_EditText.setText(StaticObjectManager.simpleDateFormat
				.format(StaticObjectManager.calendar.getTime()));
		return new DatePickerDialog(pr_Activity, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				String month;
				if (1 + view.getMonth() < 10) {
					month = "0" + (1 + view.getMonth());
				} else {
					month = 1 + view.getMonth() + StringConst.EMPTY;
				}
				String str2;
				if (view.getDayOfMonth() < 10) {
					str2 = "0" + view.getDayOfMonth();
				} else {
					str2 = view.getDayOfMonth() + StringConst.EMPTY;
				}
				String str3 = str2 + "/" + month + "/" + view.getYear();

				pr_EditText.setText(str3);
			}
		}, StaticObjectManager.calendar.get(1),
				StaticObjectManager.calendar.get(2),
				StaticObjectManager.calendar.get(5));
	}

	public static DatePickerDialog createDialogDate(Context context,
			final TextView pr_TextView) {

		pr_TextView.setText(StaticObjectManager.simpleDateFormat
				.format(StaticObjectManager.calendar.getTime()));
		final DatePickerDialog picker = new DatePickerDialog(context,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
					}
				}, StaticObjectManager.calendar.get(1),
				StaticObjectManager.calendar.get(2),
				StaticObjectManager.calendar.get(5));
		picker.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				DatePicker view = picker.getDatePicker();
				String month;
				if (1 + view.getMonth() < 10) {
					month = "0" + (1 + view.getMonth());
				} else {
					month = 1 + view.getMonth() + StringConst.EMPTY;
				}
				String str2;
				if (view.getDayOfMonth() < 10) {
					str2 = "0" + view.getDayOfMonth();
				} else {
					str2 = view.getDayOfMonth() + StringConst.EMPTY;
				}
				String str3 = str2 + "/" + month + "/" + view.getYear();

				pr_TextView.setText(str3);
			}
		});
		return picker;
	}

	public static int getColor(String close, String ceil, String floor,
			String ref) {
		if (close == null || close.length() == 0) {
			return R.color.color_ThamChieu;
		}

		Double refDouble = 0d, ceilDouble = 0d, floorDouble = 0d, closeDouble = 0d;
		try {
			refDouble = Double.valueOf(ref);
		} catch (NumberFormatException e) {

		}
		try {
			ceilDouble = Double.valueOf(ceil);
		} catch (NumberFormatException e) {

		}
		try {
			floorDouble = Double.valueOf(floor);
		} catch (NumberFormatException e) {

		}
		try {
			closeDouble = Double.valueOf(close);
		} catch (NumberFormatException e) {
			return R.color.white_text;
		}

		if (closeDouble > refDouble) {
			if (Double.compare(closeDouble, ceilDouble) == 0) {
				return R.color.color_Tran;
			} else {
				return R.color.color_Mua;
			}
		} else {
			if (closeDouble < refDouble) {
				if (Double.compare(closeDouble, floorDouble) == 0) {
					return R.color.color_San;
				} else {
					return R.color.color_Ban;
				}
			} else {
				return R.color.color_ThamChieu;
			}
		}

	}

	/**
	 * Check price, nếu price lớn hơn 0 thì màu mua, nhỏ hơn 0 thì màu bán, bằng
	 * 0 thì màu sàn
	 * 
	 * @param price
	 * @return
	 */
	public static int getColor(String price) {
		Double priceDouble = 0d;
		try {
			priceDouble = Double.valueOf(price);
		} catch (NumberFormatException e) {
		}

		if (priceDouble > 0) {
			return Color.parseColor("#00ff00");
		} else {
			if (priceDouble < 0) {
				return Color.parseColor("#ff0000");
			} else {
				return Color.parseColor("#ffcc00");
			}
		}

	}

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static int convertDp2Px(Context context, float dps) {
		if (context == null) {
			return 0;
		}
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dps * scale + 0.5f);
	}

	/**
	 * parse từ string sang double
	 * 
	 * @param value
	 * @return trả về 0d nếu parse lỗi
	 */
	public static double parseDouble(String value) {
		double d = 0d;
		try {
			d = Double.parseDouble(value);
		} catch (NumberFormatException e) {
		}
		return d;
	}

	/**
	 * parse từ string sang long
	 * 
	 * @param value
	 * @return trả về 0l nếu parse lỗi
	 */
	public static long parseLong(String value) {
		long d = 0l;
		try {
			d = Long.parseLong(value);
		} catch (NumberFormatException e) {
		}
		return d;
	}

	static String regex_a = "á|à|ả|ã|ạ|â|ấ|ầ|ẩ|ẫ|ậ|ă|ắ|ằ|ẳ|ẵ";
	static String latin_a = "a";
	static String regex_d = "đ";
	static String latin_d = "d";
	static String regex_e = "é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ";
	static String latin_e = "e";
	static String regex_i = "í|ì|ỉ|ĩ|ị";
	static String latin_i = "i";
	static String regex_o = "ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ";
	static String latin_o = "o";
	static String regex_u = "ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự";
	static String latin_u = "u";
	static String regex_y = "ý|ỳ|ỷ|ỹ|ỵ";
	static String latin_y = "y";

	public static String convertUTF8ToLatin(String str_utf8) {
		if (str_utf8 == null) {
			return null;
		}
		String latin = null;
		latin = str_utf8.replaceAll(regex_a, latin_a)
				.replaceAll(regex_d, latin_d).replaceAll(regex_e, latin_e)
				.replaceAll(regex_i, latin_i).replaceAll(regex_o, latin_o)
				.replaceAll(regex_u, latin_u).replaceAll(regex_y, latin_y);
		return latin;
	}
}
