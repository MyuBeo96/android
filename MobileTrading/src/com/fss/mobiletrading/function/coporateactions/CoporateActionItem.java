package com.fss.mobiletrading.function.coporateactions;

import java.util.HashMap;

public class CoporateActionItem {
	private String SYMBOL;
	private String CATYPE;
	private String REPORTDATE;
	private String SLCKSH;
	private String TYLE;
	private String SLCKCV;
	private String STCV;
	private String ACTIONDATE;
	private String STATUS;

	public CoporateActionItem(HashMap<String, String> hm) {
		super();
		SYMBOL = hm.get("SYMBOL");
		CATYPE = hm.get("CATYPE");
		REPORTDATE = hm.get("REPORTDATE");
		SLCKSH = hm.get("SLCKSH");
		TYLE = hm.get("TYLE");
		SLCKCV = hm.get("SLCKCV");
		STCV = hm.get("STCV");
		ACTIONDATE = hm.get("ACTIONDATE");
		STATUS = hm.get("STATUS");
	}

	public String getSYMBOL() {
		return SYMBOL;
	}

	public String getCATYPE() {
		return CATYPE;
	}

	public String getREPORTDATE() {
		return REPORTDATE;
	}

	public String getSLCKSH() {
		return SLCKSH;
	}

	public String getTYLE() {
		return TYLE;
	}

	public String getSLCKCV() {
		return SLCKCV;
	}

	public String getSTCV() {
		return STCV;
	}

	public String getACTIONDATE() {
		return ACTIONDATE;
	}

	public String getSTATUS() {
		return STATUS;
	}

}
