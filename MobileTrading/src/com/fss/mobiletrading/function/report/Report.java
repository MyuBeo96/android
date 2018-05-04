package com.fss.mobiletrading.function.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.app.Notification.Action;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.MyJsonArray;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.BRRptItem;
import com.fss.mobiletrading.object.ItemString2;
import com.fss.mobiletrading.object.NewsItem;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.design.LinearLayoutEdittext;
import com.msbuat.mobiletrading.design.MyContextMenu.OnItemSelectedListener;
import com.msbuat.mobiletrading.design.MySpinner;

public class Report extends AbstractFragment {
	static final String REPORTPARSE = "ReportParser2#REPORTPARSE";
	static final String GETBRLIST = "GetBRListParse#GETBRLIST";

	Button btn_ChapNhan;
	HashMap<String, LinearLayoutEdittext> hmLinearLayout;
	LinearLayout lay_reportinput;
	// LinearLayout lay_result;
	ScrollView scrollview_ver;
	View lay_headerpopout;
	String layoutVisibleId;
	List<ItemString2> list_report;
	MySpinner spn_ReportList;
	ReportSearchDialog reportSearchDialog;
	boolean isCalculatedHeaderHeight = false;

	public Report() {
		initUndergroundService();
	}

	public static Report newInstance(MainActivity mActivity) {
		Report self = new Report();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.Report);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle Bundle) {
		View view = inflater.inflate(R.layout.report, viewGroup, false);
		spn_ReportList = ((MySpinner) view.findViewById(R.id.spn_reportlist));
		lay_reportinput = ((LinearLayout) view
				.findViewById(R.id.lay_report_input));
		// lay_result = ((LinearLayout)
		// view.findViewById(R.id.lay_Report_result));
		webview = ((WebView) view.findViewById(R.id.webview_reportdata));
		scrollview_ver = (ScrollView) view.findViewById(R.id.scrollview_ver);
		lay_headerpopout = view.findViewById(R.id.lay_headerpopout);
		btn_ChapNhan = ((Button) view.findViewById(R.id.btn_Report_Accept));
		initialise(lay_reportinput);
		initialiseListener();
		// lay_result.addOnLayoutChangeListener(new OnLayoutChangeListener() {
		// @Override
		// public void onLayoutChange(View v, int left, int top, int right,
		// int bottom, int oldLeft, int oldTop, int oldRight,
		// int oldBottom) {
		// // đoạn code này chỉ cần thực hiện 1 lần
		// if (!isCalculatedHeaderHeight) {
		// int headerViewsSize = headerViews.size();
		// int maxHeaderHeight = 0;
		// for (int i = 0; i < headerViewsSize; i++) {
		// View header = headerViews.get(i);
		// int headerHeight = header.getHeight();
		// if (headerHeight > maxHeaderHeight) {
		// maxHeaderHeight = headerHeight;
		// }
		// }
		// for (int i = 0; i < headerViewsSize; i++) {
		// View header = headerViews.get(i);
		// ViewGroup.LayoutParams layoutparams = header
		// .getLayoutParams();
		// layoutparams.height = maxHeaderHeight;
		// header.setLayoutParams(layoutparams);
		// }
		//
		// {
		// // tự động scroll tới view báo cáo
		// int scrollY = lay_reportinput.getHeight()
		// + btn_ChapNhan.getHeight()
		// + Common.convertDp2Px(mainActivity, 20);
		// scrollview_ver.scrollTo(0, scrollY);
		// }
		// isCalculatedHeaderHeight = true;
		// }
		// }
		// });
		return view;
	}

	private void initialise(LinearLayout view) {
		if (reportSearchDialog == null) {
			reportSearchDialog = ReportSearchDialog.newInstance(mainActivity);
		}
		hmLinearLayout = new HashMap<String, LinearLayoutEdittext>();
		list_report = new ArrayList<ItemString2>();
		List<BRRptItem> listBrRptItem = StaticObjectManager.list_BrRptItems;
		if ((listBrRptItem != null) && (listBrRptItem.size() > 0)) {
			for (int i = 0; i < listBrRptItem.size(); i++) {

				BRRptItem bRRptItem = listBrRptItem.get(i);
				list_report.add(new ItemString2(bRRptItem.RptName,
						bRRptItem.RptId));

				LinearLayoutEdittext layout = new LinearLayoutEdittext(
						getActivity(), bRRptItem.list_RptField,
						reportSearchDialog);
				Common.setupUI(layout, getActivity());
				hmLinearLayout.put(bRRptItem.RptId, layout);
				layout.setVisibility(LinearLayout.GONE);
				view.addView(layout);
			}

			btn_ChapNhan.setVisibility(Button.VISIBLE);
			spn_ReportList.setChoises(list_report, Gravity.LEFT
					| Gravity.CENTER_VERTICAL);
		}

	}

	int scrollY;

	private void initialiseListener() {
		spn_ReportList.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelect(Object obj, int position) {
				if (layoutVisibleId != null) {
					hmLinearLayout.get(layoutVisibleId).setVisibility(
							LinearLayout.GONE);
				}
				LinearLayoutEdittext layout = hmLinearLayout
						.get(((ItemString2) obj).getValue());
				layout.setVisibility(LinearLayout.VISIBLE);
				layout.reset();
				// lay_result.setVisibility(LinearLayout.GONE);
				clearWebView();
				layoutVisibleId = ((ItemString2) obj).getValue();
				headerViews.clear();
				isCalculatedHeaderHeight = false;
			}
		});

		scrollview_ver.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_SCROLL:

					if (scrollview_ver.getScrollY() > scrollY) {
						lay_headerpopout.setVisibility(View.VISIBLE);
					} else {
						lay_headerpopout.setVisibility(View.GONE);
					}
					break;

				default:
					break;
				}
				return false;
			}
		});

		btn_ChapNhan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CallReport();
				isCalculatedHeaderHeight = false;
				// reset view cũ
				isGettingReport();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if (list_report.size() > 0) {
			spn_ReportList.setSelection(0);
		}
	}

	private void CallReport() {
		LinearLayoutEdittext localLinearLayoutEdittext = (LinearLayoutEdittext) hmLinearLayout
				.get(layoutVisibleId);
		boolean rs = ReportService.CallReport(layoutVisibleId,
				localLinearLayoutEdittext.rptParams,
				localLinearLayoutEdittext.getValue(), this, REPORTPARSE);
		if (!rs) {
			isReceivedResponse(REPORTPARSE);
		}
	}

	List<View> headerViews = new ArrayList<View>();

	// private void DisplayResult(List<MyJsonArray> resultList)
	// throws JSONException {
	// WindowManager.LayoutParams layoutParams;
	// if ((resultList != null) && (resultList.size() > 0)) {
	// lay_result.setVisibility(LinearLayout.VISIBLE);
	// lay_result.removeAllViews();
	// layoutParams = new WindowManager.LayoutParams();
	// layoutParams.width = LayoutParams.WRAP_CONTENT;
	// layoutParams.height = LayoutParams.WRAP_CONTENT;
	//
	// MyJsonArray arrayTitle = resultList.get(0);
	// int arrayTitleLength = arrayTitle.length();
	// for (int i = 0; i < arrayTitleLength; i++) {
	// LinearLayout view = new LinearLayout(getActivity());
	// view.setOrientation(LinearLayout.VERTICAL);
	// view.setLayoutParams(layoutParams);
	//
	// TextView tv_title = (TextView) getActivity()
	// .getLayoutInflater().inflate(
	// R.layout.report_result_header_layout, null);
	// tv_title.setSingleLine(false);
	// tv_title.setText(arrayTitle.getString(i));
	// view.addView(tv_title);
	// headerViews.add(tv_title);
	// int resultListSize = resultList.size();
	// for (int j = 1; j < resultListSize; j++) {
	// TextView tv = (TextView) getActivity().getLayoutInflater()
	// .inflate(R.layout.report_result_content_layout,
	// null);
	// tv.setText(resultList.get(j).getString(i));
	// view.addView(tv);
	// }
	// lay_result.addView(view);
	// }
	//
	// }
	// }

	private void loadDataReport(String data) {
		showDetailNewsItem(data);
	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case REPORTPARSE:
			if (rObj.obj != null) {
				// try {
				// DisplayResult((List<MyJsonArray>) rObj.obj);
				// } catch (JSONException e) {
				// }
				loadDataReport((String) rObj.obj);
			}
			break;

		default:
			break;
		}
	}

	protected void isReceivedResponse(String key) {
		switch (key) {
		case REPORTPARSE:
			isGettedReport();
			break;

		default:
			break;
		}
	};

	/**
	 * vì lấy dữ liệu báo cáo lâu nên phải disable nút chấp nhận và spinner chọn
	 * mẫu báo cáo đi
	 * 
	 * isGettingReport đang lấy dữ liệu báo cáo isGettedReport lấy xong dữ liệu
	 * báo cáo
	 */
	private void isGettingReport() {
		btn_ChapNhan.setEnabled(false);
		spn_ReportList.setEnabled(false);
	}

	/**
	 * vì lấy dữ liệu báo cáo lâu nên phải disable nút chấp nhận và spinner chọn
	 * mẫu báo cáo đi
	 * 
	 * isGettingReport đang lấy dữ liệu báo cáo isGettedReport lấy xong dữ liệu
	 * báo cáo
	 */
	private void isGettedReport() {
		btn_ChapNhan.setEnabled(true);
		spn_ReportList.setEnabled(true);
	}

	INotifier notifier;

	/**
	 * gọi các service khởi tạo dữ liệu của chức năng, các service này được gọi
	 * ngầm
	 * 
	 */
	protected void initUndergroundService() {
		if (notifier == null) {
			notifier = new INotifier() {

				@Override
				public void notifyResult(String key, ResultObj rObj) {
					if (rObj != null) {
						if (rObj.EC == 0) {
							switch (key) {
							case GETBRLIST:
								StaticObjectManager.list_BrRptItems = (List<BRRptItem>) rObj.obj;
								break;

							default:
								break;
							}
						}
					}
				}

				@Override
				public void notifyChangeAcctNo() {

				}
			};
		}

		ReportService.CallGetBRList(notifier, GETBRLIST);
	}

	WebView webview;

	private void showDetailNewsItem(String data) {
		webview.setVisibility(View.VISIBLE);
		webview.getSettings().setJavaScriptEnabled(true);
		// String replace = newsItem.Content.replace(oldChar, newChar);
		webview.loadDataWithBaseURL(StringConst.EMPTY, data, "text/html",
				"UTF-8", StringConst.EMPTY);
	}

	private void clearWebView() {
		webview.loadUrl("about:blank");
		webview.setVisibility(View.GONE);
	}

}
