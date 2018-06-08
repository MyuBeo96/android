package com.fscuat.mobiletrading;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.connector.CheckSessionFragment;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.jsonservice.Error;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.MyActionBar.Action;

import java.lang.reflect.Field;

/**
 * 
 * @author Admin
 * 
 */
public abstract class AbstractFragment extends DialogFragment implements
		INotifier {

	public String TITLE;
	public boolean onScreen = false;
	protected MainActivity mainActivity;
	Dialog dialog_ShowError;
	Dialog dialog;
	TextView tv_message;
	TextView tv_Title;
	TextView tv_Positive;
	TextView tv_Negative;
	NotifyReceivedResponse notifyReceivedResponse;
	public boolean isShow = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		notifyReceivedResponse = new NotifyReceivedResponse();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog d = super.onCreateDialog(savedInstanceState);
		Window w = d.getWindow();
		w.setBackgroundDrawableResource(R.drawable.bg_dialogfragment);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return d;
	}

	@Override
	public void onStart() {
		super.onStart();
		// if (getDialog() != null) {
		// getDialog().getWindow().setLayout(
		// WindowManager.LayoutParams.WRAP_CONTENT,
		// WindowManager.LayoutParams.WRAP_CONTENT);
		// }
	}

	@Override
	public void onResume() {
		super.onResume();
		((MainActivity) getActivity()).addResumingFragment(this);
		isShow = true;
		onScreen = true;
		if (mainActivity == null) {
			mainActivity = (MainActivity) getActivity();
		}

		if (dialog_ShowError == null) {
			createDialogShowMessage();
		}
	}

	private void createDialogShowMessage() {
		dialog_ShowError = new Dialog(mainActivity);
		dialog_ShowError.setCancelable(false);
		dialog_ShowError.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_ShowError.setContentView(R.layout.alert_dialog);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog_ShowError.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog_ShowError.getWindow().setAttributes(lp);
		dialog_ShowError.getWindow()
				.setBackgroundDrawable(new ColorDrawable(0));

		tv_message = (TextView) dialog_ShowError
				.findViewById(R.id.text_alertdialog_message);
		tv_Title = (TextView) dialog_ShowError
				.findViewById(R.id.text_alertdialog_title);
		tv_Positive = (TextView) dialog_ShowError
				.findViewById(R.id.text_alertdialog_possitive);
		tv_Negative = (TextView) dialog_ShowError
				.findViewById(R.id.text_alertdialog_negative);
	}

	public void onShowed() {
		isShow = true;
	}

	public void onHide() {
		isShow = false;
	}

	@Override
	public void onPause() {
		super.onPause();
		((MainActivity) getActivity()).removeResumingFragment(this);
		onScreen = false;
		if (mainActivity != null) {
			mainActivity.hideKeyboard(mainActivity.getCurrentFocus());
		}
	}

	@Override
	public void notifyChangeAcctNo() {

	}

	@Override
	public void notifyResult(final String key, final ResultObj rObj) {

		try {
			if (rObj != null) {
				switch (rObj.EC) {
				case Error.ERROR_CONNECT_SERVER:
					if (onScreen) {
						showDialogMessage(R.string.Loi,
								R.string.error_connect_server);
					}
					preProcess(key, null);
					break;
				case Error.ERROR_TIMEOUT:
					if (key.equals(CheckSessionFragment.CHECKSESSION)) {
						if (dialog_ShowError == null) {
							createDialogShowMessage();
						}
						showMessageCheckSession();
					}
					preProcess(StringConst.EMPTY, null);
					break;
				case Error.ERROR_OTHER:
					if (onScreen) {
					if (notifyReceivedResponse != null) {
                                notifyReceivedResponse.setKey(key);
                                mainActivity.delay.postAtTime(notifyReceivedResponse, 200);
                            }
						if (isShow) {
							showDialogMessage(
									getStringResource(R.string.thong_bao),
									rObj.EM, new SimpleAction() {

										@Override
										public void performAction(Object obj) {
											try {
												processErrorOther(key, rObj);
											} catch (Exception e) {
												e.printStackTrace();
											}

										}
									});
						} else {
							processErrorOther(key, rObj);
						}
					}
					preProcess(key, rObj);
					break;
				case Error.ERROR_PROCESSJSON:
					if (onScreen) {
						showDialogMessage(R.string.Loi,
								R.string.error_processjson);
					}
					preProcess(StringConst.EMPTY, null);
					break;
				case Error.ERROR_NODATA:
					// Common.showDialog(getActivity(),
					// getStringResource(R.string.Loi), getResources()
					// .getString(R.string.error_NoData));
					preProcess(StringConst.EMPTY, null);
					break;

				default:
					preProcess(key, rObj);
					break;
				}
			} else {
				Log.e(key, "has no service");
				preProcess(StringConst.EMPTY, null);
			}
		} catch (Exception e) {

			e.printStackTrace();
			preProcess(StringConst.EMPTY, null);
		}

	}

	protected void preProcess(String key, ResultObj rObj) {
		if (rObj == null) {
			processNull(key);
		} else {
			if (rObj.EC == Error.ERROR_OTHER) {
				// processErrorOther(key, rObj);
			} else {
				try {
					process(key, rObj);
					if (notifyReceivedResponse != null) {
						notifyReceivedResponse.setKey(key);
						mainActivity.delay.postAtTime(notifyReceivedResponse,
								200);
					}
				} catch (Exception e) {
					e.printStackTrace();
					showDialogMessage(getStringResource(R.string.Loi), key
							+ getStringResource(R.string.ErrorProgram));
					if (notifyReceivedResponse != null) {
						notifyReceivedResponse.setKey(key);
						mainActivity.delay.postAtTime(notifyReceivedResponse,
								200);
					}
					errorProcess();
				}
			}
		}
	}

	protected void errorProcess() {

	}

	protected void processNull(String key) {
		if (notifyReceivedResponse != null) {
			notifyReceivedResponse.setKey(key);
			mainActivity.delay.postAtTime(notifyReceivedResponse, 200);
		}
	}

	protected void processErrorOther(String key, ResultObj rObj) {
		
	}

	/**
	 * hàm này được g�?i khi nhận được response trả v�?(có thể thành công hoặc
	 * thất bại) của webservice
	 * 
	 * @param key
	 */
	protected void isReceivedResponse(String key) {

	}

	public void showDialogMessage(String title, String msg) {
		if (title != null) {
			tv_Title.setText(title);
		}
		if (msg != null) {
			tv_message.setText(msg);
		}
		tv_Positive.setText(getStringResource(R.string.XacNhan));
		setOnClickDialog(null, null);
		if (isShow) {
			dialog_ShowError.show();
		}
	}

	public void showDialogMessage(String title, String msg,
			final SimpleAction positiveAction) {
		if (title != null) {
			tv_Title.setText(title);
		}
		if (msg != null) {
			tv_message.setText(msg);
		}
		tv_Positive.setText(getStringResource(R.string.XacNhan));
		setOnClickDialog(positiveAction, null);
		if (isShow) {
			dialog_ShowError.show();
		}
	}

	public void showDialogMessage(String title, String msg, String positive,
			final SimpleAction positiveAction) {
		if (title != null) {
			tv_Title.setText(title);
		}
		if (msg != null) {
			tv_message.setText(msg);
		}
		if (positive != null) {
			tv_Positive.setText(positive);
		} else {
			tv_Positive.setText(getStringResource(R.string.XacNhan));
		}
		setOnClickDialog(positiveAction, null);
		if (isShow) {
			dialog_ShowError.show();
		}
	}

	public void showDialogMessage(String title, String msg,
			final SimpleAction positiveAction, SimpleAction negativeAction,
			String positive, String nesitive) {
		if (title != null) {
			tv_Title.setText(title);
		}
		if (msg != null) {
			tv_message.setText(msg);
		}
		if (positive != null) {
			tv_Positive.setText(positive);
		} else {
			tv_Positive.setText(getStringResource(R.string.XacNhan));
		}

		if (nesitive != null) {
			tv_Negative.setText(nesitive);
		}
		setOnClickDialog(positiveAction, negativeAction);
		if (isShow) {
			dialog_ShowError.show();
		}
	}

	public void showDialogMessage(int titleid, int msgid) {
		showDialogMessage(getStringResource(titleid), getStringResource(msgid));
	}

	public void showMessageCheckSession() {
		tv_Title.setText(mainActivity.getStringResource(R.string.Loi));
		tv_message.setText(mainActivity
				.getStringResource(R.string.error_timeout));
		tv_Positive.setText(mainActivity.getStringResource(R.string.XacNhan));
		setOnClickDialog(new SimpleAction() {

			@Override
			public void performAction(Object obj) {
				if (mainActivity != null) {
					mainActivity.finish();
				}
			}
		}, null);
		dialog_ShowError.show();
	}

	public String getStringResource(int id) {

		try {
			return getResources().getString(id);
		} catch (Exception e) {
			if (mainActivity != null) {
				mainActivity.getStringResource(id);
			}
		}
		return StringConst.EMPTY;
	}

	public int getColorResource(int id) {

		try {
			return getResources().getColor(id);
		} catch (Exception e) {

		}
		return Color.WHITE;
	}

	/**
	 * lấy các kích thước đã khai báo trong resource
	 * 
	 * @param id
	 * @return trả về kích thước sau khi quy đổi sang pixel, trả về 0 nếu có lỗi
	 *         xảy ra
	 */
	public int getDimenResource(int id) {
		try {
			return getResources().getDimensionPixelSize(id);
		} catch (Exception e) {

		}
		return 0;
	}

	protected void setHomeLogoAction() {
		if (!DeviceProperties.isTablet) {
			mainActivity.setHomeLogoAction(new Action() {

				@Override
				public void performAction(View view) {
					showHomeLogoAction();
				}

				@Override
				public int getDrawable() {

					return R.drawable.image_menu;
				}

				@Override
				public String getText() {

					return null;
				}
			});
		}
	}

	protected void setBackLogoAction() {
		if (!DeviceProperties.isTablet) {
			mainActivity.setHomeLogoAction(new Action() {

				@Override
				public void performAction(View view) {
					performBackAction();
				}

				@Override
				public int getDrawable() {
					return R.drawable.ic_back;
				}

				@Override
				public String getText() {
					return null;
				}
			});
		}
	}
	protected void setBackLogoActionMenu(){
		if(!DeviceProperties.isTablet){
			mainActivity.setHomeLogoAction(new Action() {

				@Override
				public void performAction(View view) {
					showHomeLogoAction();
				}

				@Override
				public int getDrawable() {
					return R.drawable.ic_back_menu;
				}

				@Override
				public String getText() {
					return null;
				}
			});
		}
	}

	protected void showHomeLogoAction() {
		if (mainActivity != null) {
			mainActivity.showMenu(true);
		}
	}

	protected void performBackAction() {
		if (mainActivity != null) {
			mainActivity.backNavigateFragment();
		}
	}
	abstract protected void process(String key, ResultObj rObj);

	public void getArgument(Object obj) {

	}

	@Override
	public String toString() {

		return TITLE;
	}

	public void resetActionBar() {
		if (mainActivity != null) {
			mainActivity.clearHomeLogoAction();
			mainActivity.removeAllAction();
		}
	}

	public void addActionToActionBar() {
		resetActionBar();
		if (mainActivity != null && TITLE != null && TITLE.length() > 0) {
			mainActivity.setTitleActionBar(TITLE);
		}
	}

	public void refresh() {

	}

	private void setOnClickDialog(final SimpleAction positive,
			final SimpleAction negative) {
		if (positive == null) {
			tv_Positive.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog_ShowError.dismiss();
				}
			});
		} else {
			tv_Positive.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog_ShowError.dismiss();
					positive.performAction(null);
				}
			});
		}

		if (negative == null) {
			tv_Negative.setVisibility(View.GONE);
			dialog_ShowError.findViewById(R.id.line_alertdialog_1)
					.setVisibility(View.GONE);
			tv_Negative.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog_ShowError.dismiss();
				}
			});
		} else {
			dialog_ShowError.findViewById(R.id.line_alertdialog_1)
					.setVisibility(View.VISIBLE);
			tv_Negative.setVisibility(View.VISIBLE);
			tv_Negative.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog_ShowError.dismiss();
					negative.performAction(null);
				}
			});
		}
	}

	class NotifyReceivedResponse implements Runnable {
		String key;

		public void setKey(String key) {
			this.key = key;
		}

		@Override
		public void run() {
			isReceivedResponse(key);
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();

		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
