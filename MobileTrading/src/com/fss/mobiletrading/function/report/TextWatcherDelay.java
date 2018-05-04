package com.fss.mobiletrading.function.report;

import java.util.Timer;
import java.util.TimerTask;

import android.text.Editable;
import android.text.TextWatcher;

import com.fss.mobiletrading.common.SimpleAction;

public class TextWatcherDelay implements TextWatcher {
	private int delay = 500;
	Timer timer = new Timer();
	SimpleAction action;

	/**
	 * khởi tạo TextWatcherDelay
	 * 
	 * @param delay
	 *            thời gian delay giữa 2 lần text thay đổi. Nếu text thay đổi
	 *            dưới 2 lần này thì không thực hiện task
	 * @param action
	 *            Task muốn làm khi text thay đổi
	 */
	public TextWatcherDelay(int delay, SimpleAction action) {
		this.delay = delay;
		this.action = action;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		timer.cancel();
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (action != null) {
					action.performAction(null);
				}
			}
		}, delay);
	}

}
