package com.fss.mobiletrading.common;

import android.content.Context;

import com.fss.mobiletrading.connector.ConnectServer;
import com.fss.mobiletrading.function.AppData;
import com.fss.mobiletrading.object.AcctnoItem;
import com.fss.mobiletrading.object.BRRptItem;
import com.fss.mobiletrading.object.BankAccList;
import com.fss.mobiletrading.object.LoginItem;
import com.fss.mobiletrading.object.StockItem;
import com.msbuat.mobiletrading.CallAnotherAppManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StaticObjectManager {
	public static ConnectServer connectServer;
	public static LoginItem loginInfo;
	public static AcctnoItem acctnoItem;
	public static List<StockItem> listStock;
	public static String[] listAllStock;
	public static int selectedMenuItemPosition = 3;
	public static BankAccList listBankAcc;
	public static Calendar calendar;
	public static SimpleDateFormat simpleDateFormat;
	public static AcctnoItem defaultbrokeracctno;
	public static boolean isLogin = false;
	public static boolean flagNotifyComeAtNotLogin = false;
	public static String deviceToken = "";
	public static CallAnotherAppManager callAnotherAppManager;
	public static List<BRRptItem> list_BrRptItems;
    public static String sessionCookie = "";
    public static String accessTokenName = "";

	public static void ClearData() {
		loginInfo = null;
		acctnoItem = null;
		selectedMenuItemPosition = 3;
		listBankAcc = null;
		defaultbrokeracctno = null;
		isLogin = false;
	}

	public StaticObjectManager() {
	}

	/**
	 * Trả về Object StockItem dựa vào tên mã chứng khoán
	 * 
	 * @param symbol
	 * @return object của StockItem, nếu mã chứng khoán bằng null hoặc không tồn
	 *         tại thì trả về giá trị null
	 */
	public static StockItem getStockItem(String symbol) {
		if (symbol != null) {
			for (StockItem item : listStock) {
				if (item.toString().matches(symbol)) {
					return item;
				}
			}
		}
		return null;
	}

	public static List<StockItem> searchStockItem(List<StockItem> para_list,
			String seq) {
		List<StockItem> list = new ArrayList<StockItem>();
		if (para_list != null && seq != null) {
			for (StockItem stockItem : para_list) {
				if (stockItem.toString().startsWith(seq)) {
					list.add(stockItem);
				}
			}
		}
		return list;
	}

	public static void initSystemBeforeLogin(Context context) {
		// khởi tạo object quản lý việc gọi tới các ứng dụng khác
		initCallAnotherAppManager(context);
	}

	public static void initSystemAfterLogin() {
		isLogin = true;
		simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		calendar = Calendar.getInstance();
		try {
			calendar.setTime(simpleDateFormat
					.parse(StaticObjectManager.loginInfo.TxDateString));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void initCallAnotherAppManager(Context context) {
		callAnotherAppManager = new CallAnotherAppManager(context);
	}

	public static Calendar getCalendarFromString(String str) {
		if (str == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(simpleDateFormat.parse(str));
		} catch (ParseException e) {
			return null;
		}
		return calendar;
	}

	public static int getPositionAcctno() {
		for (int i = 0; i < loginInfo.contractList.size(); i++) {
			if (loginInfo.contractList.get(i).ACCTNO
					.equals(loginInfo.CurrentAcctno)) {
				return i;
			}
		}
		return 0;
	}

    public static String getAfAcctnoList() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < loginInfo.contractList.size(); i++) {
            AcctnoItem acctnoItem = loginInfo.contractList.get(i);
            builder.append(acctnoItem.ACCTNO + ",");
        }
        return builder.toString();
    }

	public static String[] getListAllStock(){
		List<StockItem> list = StaticObjectManager.listStock;
		listAllStock = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			listAllStock[i] = list.get(i).toString();
		}
		return listAllStock;
	}
}
