package com.fss.mobiletrading.object;

/**
 * Tương ứng với 1 lệnh đặt
 * 
 * @author Admin
 * 
 */
public class Order {
	public static final String SIDE_NB = "NB";
	public static final String SIDE_NS = "NS";

	private String AFACCTNO;
	private String CUSTODYCD;
	private String SYMBOL;
	private String SIDE;
	private String PRICETYPE;
	private String QTTY;
	private String PRICE;
	private String SPLITQTTY;
	private String FROMDATE;
	private String TODATE;

	public Order(String sYMBOL, String sIDE, String qTTY, String pRICE) {
		super();
		SYMBOL = sYMBOL;
		SIDE = sIDE;
		QTTY = qTTY;
		PRICE = pRICE;
	}

	public Order(String sYMBOL) {
		super();
		SYMBOL = sYMBOL;
	}

	public Order(String sYMBOL, String qTTY, String pRICE) {
		super();
		SYMBOL = sYMBOL;
		QTTY = qTTY;
		PRICE = pRICE;
	}

	public String getAFACCTNO() {
		return AFACCTNO;
	}

	public void setAFACCTNO(String aFACCTNO) {
		AFACCTNO = aFACCTNO;
	}

	public String getCUSTODYCD() {
		return CUSTODYCD;
	}

	public void setCUSTODYCD(String cUSTODYCD) {
		CUSTODYCD = cUSTODYCD;
	}

	public String getSYMBOL() {
		return SYMBOL;
	}

	public void setSYMBOL(String sYMBOL) {
		SYMBOL = sYMBOL;
	}

	public String getSIDE() {
		return SIDE;
	}

	public void setSIDE(String sIDE) {
		SIDE = sIDE;
	}

	public String getPRICETYPE() {
		return PRICETYPE;
	}

	public void setPRICETYPE(String pRICETYPE) {
		PRICETYPE = pRICETYPE;
	}

	public String getQTTY() {
		return QTTY;
	}

	public void setQTTY(String qTTY) {
		QTTY = qTTY;
	}

	public String getPRICE() {
		return PRICE;
	}

	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}

	public String getSPLITQTTY() {
		return SPLITQTTY;
	}

	public void setSPLITQTTY(String sPLITQTTY) {
		SPLITQTTY = sPLITQTTY;
	}

	public String getFROMDATE() {
		return FROMDATE;
	}

	public void setFROMDATE(String fROMDATE) {
		FROMDATE = fROMDATE;
	}

	public String getTODATE() {
		return TODATE;
	}

	public void setTODATE(String tODATE) {
		TODATE = tODATE;
	}

}
