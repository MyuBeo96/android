package com.fss.mobiletrading.connector;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

public class RequestRealtime {
	/**
	 * chế độ chạy định kỳ.(thời gian định kỳ interval)
	 */
	public final static int MODE_PERIODIC = 0;
	/**
	 * chế độ chạy trễ so với trigger.(thời gian trễ interval)
	 */
	public final static int MODE_DELAY = 1;
	private int interval;
	private int mode;
	private Timer timer;
	private MyRequest request;

	/**
	 * khởi tạo đối tượng requestRealTime
	 * 
	 * @param mode
	 *            chế độ request. Tham khảo các mode
	 *            {@link RequestRealtime#MODE_DELAY} ,
	 *            {@link RequestRealtime#MODE_PERIODIC}. <br>
	 *            Mặc định là {@link RequestRealtime#MODE_PERIODIC}
	 * 
	 * @param interval
	 *            thời gian request định kỳ hoặc thời gian delay
	 * 
	 */
	public RequestRealtime(int mode, int interval) {
		this.mode = mode;
		this.interval = interval;
	}

	public void setRequest(MyRequest task) {
		this.request = task;
	}

	/**
	 * chạy request realtime
	 */
	public void run() {
		isTimerRunning = true;
		switch (mode) {
		case MODE_PERIODIC:
			startPeriodic();
			break;
		case MODE_DELAY:
			trigger();
			break;
		default:
			break;
		}
	}

	public void trigger() {
		startDelay();
	}

	private boolean isTimerRunning = false;

	/**
	 * cancel request realtime
	 */
	public void stop() {
		if (timer != null) {
			timer.cancel();
		}
		isTimerRunning = false;
	}

	private void startPeriodic() {
		if (isTimerRunning == true) {
			timer = new Timer();
			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					if (request != null) {
						request.execute();
					}
				}
			};
			timer.schedule(task, 0, interval);
		}
	}

	private void startDelay() {
		if (request != null && isTimerRunning == true) {
			timer = new Timer();
			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					if (request != null) {
						request.execute();
					}
				}
			};
			timer.schedule(task, interval);
		}
	}

	public interface MyRequest {
		public void execute();
	}
}
