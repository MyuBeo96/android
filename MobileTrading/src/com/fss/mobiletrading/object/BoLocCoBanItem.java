package com.fss.mobiletrading.object;

import java.util.HashMap;

public class BoLocCoBanItem {
	public String BETA;
	public String EST_BASE;
	public String EST_EPS;
	public String PB;
	public String PE;
	public String PS;
	public String QUICK_RATIO;
	public String REALPRICE;
	public String REALPRICE_CHANGE;
	public String REALPRICE_PERCENT_CHANGE;
	public String STASSET_STLIAB;
	public String STT;
	public String SYMBOL;
	public String TOTAL_LOANS_ASSETS;
	public String TOTAL_LOANS_EQUITY;
	public String T_EBIT_DOANH_SO;
	public String T_GP_SALES;
	public String T_LAI_HOAT_DONG_DOANH_SO;
	public String T_LAI_THUAN_DOANH_SO;
	public String T_ROA;
	public String T_ROE;

	public BoLocCoBanItem() {
	}

	public BoLocCoBanItem(HashMap<String, String> paramHashMap) {
		this.STT = ((String) paramHashMap.get("STT"));
		this.SYMBOL = ((String) paramHashMap.get("SYMBOL"));
		this.REALPRICE = ((String) paramHashMap.get("REALPRICE"));
		this.REALPRICE_CHANGE = ((String) paramHashMap.get("REALPRICE_CHANGE"));
		this.REALPRICE_PERCENT_CHANGE = ((String) paramHashMap
				.get("REALPRICE_PERCENT_CHANGE"));
		this.PE = ((String) paramHashMap.get("PE"));
		this.PB = ((String) paramHashMap.get("PB"));
		this.EST_EPS = ((String) paramHashMap.get("EST_EPS"));
		this.PS = ((String) paramHashMap.get("PS"));
		this.QUICK_RATIO = ((String) paramHashMap.get("QUICK_RATIO"));
		this.STASSET_STLIAB = ((String) paramHashMap.get("STASSET_STLIAB"));
		this.TOTAL_LOANS_EQUITY = ((String) paramHashMap
				.get("TOTAL_LOANS_EQUITY"));
		this.TOTAL_LOANS_ASSETS = ((String) paramHashMap
				.get("TOTAL_LOANS_ASSETS"));
		this.T_GP_SALES = ((String) paramHashMap.get("T_GP_SALES"));
		this.T_EBIT_DOANH_SO = ((String) paramHashMap.get("T_EBIT_DOANH_SO"));
		this.T_LAI_HOAT_DONG_DOANH_SO = ((String) paramHashMap
				.get("T_LAI_HOAT_DONG_DOANH_SO"));
		this.T_LAI_THUAN_DOANH_SO = ((String) paramHashMap
				.get("T_LAI_THUAN_DOANH_SO"));
		this.T_ROE = ((String) paramHashMap.get("T_ROE"));
		this.T_ROA = ((String) paramHashMap.get("T_ROA"));
		this.EST_BASE = ((String) paramHashMap.get("EST_BASE"));
		this.BETA = ((String) paramHashMap.get("BETA"));
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.BoLocCoBanItem JD-Core Version: 0.6.0
 */