package com.fss.mobiletrading.function.report;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.SimpleAction;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ItemString2;
import com.fss.mobiletrading.object.ResultObj;
import com.fscuat.mobiletrading.AbstractFragment;
import com.fscuat.mobiletrading.MainActivity;
import com.fscuat.mobiletrading.R;

public class ReportSearchDialog extends AbstractFragment {
	final static String GETDATASOURCE = "GetDataSourceParse#GETDATASOURCE";
	Context context;
	EditText input;
	ListView lv_result;
	List<ItemString2> resultList;
	ArrayAdapter<ItemString2> adapterResult;
	private String lastSeq = StringConst.EMPTY;
	private String code = StringConst.EMPTY;
	TextView tvResult;

	public void setTvResult(TextView edtResult) {
		this.tvResult = edtResult;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static ReportSearchDialog newInstance(MainActivity mActivity) {
		ReportSearchDialog self = new ReportSearchDialog();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.Report);
		return self;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.reportsearchdialog, container,
				false);
		input = (EditText) view.findViewById(R.id.edittext);
		lv_result = (ListView) view.findViewById(R.id.listview_result);
		initData();
		initListener();
		input.setEnabled(enabled);
		Common.setupUI(view, getDialog());
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		int width = getDimenResource(R.dimen.reportsearch_dialog_width);
		int height = getDimenResource(R.dimen.reportsearch_dialog_height);
		if (getDialog() != null) {
			getDialog().getWindow().setLayout(width, height);
		}
	}

	private void initData() {
		if (resultList == null) {
			resultList = new ArrayList<ItemString2>();
			adapterResult = new ArrayAdapter<>(mainActivity,
					R.layout.textviewoflistview, resultList);
		} else {
			resultList.clear();
			adapterResult.notifyDataSetChanged();
		}
		lv_result.setAdapter(adapterResult);
	}

	TextWatcherDelay textWatcher;

	private void initListener() {
		textWatcher = new TextWatcherDelay(500, new SimpleAction() {

			@Override
			public void performAction(Object obj) {
				lastSeq = StringConst.EMPTY;
				callGetDataSource(code, input.getText().toString(), lastSeq);
			}
		});
		input.addTextChangedListener(textWatcher);
		input.addTextChangedListener(new TextWatcher() {

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
				resultList.clear();
				adapterResult.notifyDataSetChanged();
			}
		});
		lv_result.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (tvResult != null) {
					ItemString2 item = resultList.get(position);
					tvResult.setText(item.getValue());
				}
				onDismiss(getDialog());
			}
		});
		lv_result.setOnScrollListener(new OnScrollListener() {
			long starttime = 0;;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				long endtime = System.currentTimeMillis();
				if ((firstVisibleItem + visibleItemCount == totalItemCount)
						&& (totalItemCount > 0)) {
					if ((endtime - starttime) > 3000) {
						callGetDataSource(code, input.getText().toString(),
								lastSeq);
						starttime = endtime;
					}
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		input.setText(inputText);
		inputText = StringConst.EMPTY;
	}

	private void callGetDataSource(String code, String filter, String lastSeq) {
		ReportService.CallGetDataSource(code, filter, lastSeq, this,
				GETDATASOURCE);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		switch (key) {
		case GETDATASOURCE:
			List<ItemString2> list = (List<ItemString2>) rObj.obj;
			String ls = list.get(0).toString();
			list.remove(0);
			resultList.addAll(list);
			adapterResult.notifyDataSetChanged();
			lastSeq = ls;
			break;

		default:
			break;
		}
	}

	public void show() {
		show(mainActivity.fm, null);
	}

	boolean enabled = true;

	public void setEnableImport(boolean enabled) {
		if (this.isResumed()) {
			input.setEnabled(enabled);
		}
		this.enabled = enabled;
	}

	@Override
	public void onPause() {
		super.onPause();
		input.removeTextChangedListener(textWatcher);
		input.getText().clear();
		input.addTextChangedListener(textWatcher);
	}

	String inputText = StringConst.EMPTY;

	public void setInputText(String text) {
		if (this.isResumed()) {
			input.setText(text);
		} else {
			inputText = text;
		}

	}

	private void isLoading() {

	}

	private void isLoaded() {

	}

}
