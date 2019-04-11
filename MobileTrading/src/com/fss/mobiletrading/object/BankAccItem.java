package com.fss.mobiletrading.object;

import java.util.HashMap;

import com.fss.mobiletrading.consts.StringConst;
import com.tcscuat.mobiletrading.Login;

public class BankAccItem {

	public String ACNIDCODE;
	public String ACNIDDATE;
	public String ACNIDPLACE;
	public String AFACCTNO;
	public String CITYBANK;
	public String CITYEF;
	public String CUSTODYCD;
	public String EN_REG_TYPE;
	public String FEEAMT;
	public String FEECD;
	public String FEENAME;
	public String FEERATE;
	public String FORP;
	public String MAXVAL;
	public String MINVAL;
	public String MTFRTIME;
	public String MTTOTIME;
	public String REFID;
	public String REFID1;
	public String REG_ACCTNO;
	public String REG_BENEFICARY_INFO;
	public String REG_BENEFICARY_NAME;
	public String REG_CUSTID;
	public String REG_CUSTODYCD;
	public String AFTYPE;
	public String REG_TYPE;
	public String TYPE;
	public String TYPEMNEMONIC;
	public String VATRATE;
	public String MNEMONIC;

	public BankAccItem(HashMap<String, String> hm) {
		this.REFID = hm.get("REFID");
		this.REFID1 = hm.get("REFID1");
		this.CITYEF = hm.get("CITYEF");
		this.CITYBANK = hm.get("CITYBANK");
		this.AFACCTNO = hm.get("AFACCTNO");
		this.TYPE = hm.get("TYPE");
		this.REG_TYPE = hm.get("REG_TYPE");
		this.EN_REG_TYPE = hm.get("EN_REG_TYPE");
		this.REG_CUSTID = hm.get("REG_CUSTID");
		this.REG_ACCTNO = hm.get("REG_ACCTNO");
		this.REG_CUSTODYCD = hm.get("REG_CUSTODYCD");
		this.REG_BENEFICARY_NAME = hm.get("REG_BENEFICARY_NAME");
		this.REG_BENEFICARY_INFO = hm.get("REG_BENEFICARY_INFO");
		this.FEECD = hm.get("FEECD");
		this.FEENAME = hm.get("FEENAME");
		this.FORP = hm.get("FORP");
		this.FEEAMT = hm.get("FEEAMT");
		this.FEERATE = hm.get("FEERATE");
		this.MINVAL = hm.get("MINVAL");
		this.MAXVAL = hm.get("MAXVAL");
		this.VATRATE = hm.get("VATRATE");
		this.ACNIDCODE = hm.get("ACNIDCODE");
		this.ACNIDDATE = hm.get("ACNIDDATE");
		this.ACNIDPLACE = hm.get("ACNIDPLACE");
		this.CUSTODYCD = hm.get("CUSTODYCD");
		this.MTFRTIME = hm.get("MTFRTIME");
		this.MTTOTIME = hm.get("MTTOTIME");
		this.TYPEMNEMONIC = hm.get("TYPEMNEMONIC");
		this.AFTYPE = hm.get("AFTYPE");
		this.MNEMONIC = hm.get("MNEMONIC");
	}

	/**
	 * khởi tạo các tiểu khoản nội bộ
	 * 
	 * @param item
	 */
	public BankAccItem(AcctnoItem item) {
		this.REG_ACCTNO = item.ACCTNO;
		this.AFTYPE = item.AFTYPE;
		this.TYPE = "0";
		this.REG_CUSTODYCD = item.CUSTODYCD;

		this.REFID = StringConst.EMPTY;
		this.REFID1 = StringConst.EMPTY;
		this.CITYEF = StringConst.EMPTY;
		this.CITYBANK = StringConst.EMPTY;
		this.AFACCTNO = StringConst.EMPTY;
		this.REG_TYPE = StringConst.EMPTY;
		this.EN_REG_TYPE = StringConst.EMPTY;
		this.REG_CUSTID = StringConst.EMPTY;
		this.REG_BENEFICARY_NAME = StringConst.EMPTY;
		this.REG_BENEFICARY_INFO = StringConst.EMPTY;
		this.FEECD = StringConst.EMPTY;
		this.FEENAME = StringConst.EMPTY;
		this.FORP = StringConst.EMPTY;
		this.FEEAMT = StringConst.EMPTY;
		this.FEERATE = StringConst.EMPTY;
		this.MINVAL = StringConst.EMPTY;
		this.MAXVAL = StringConst.EMPTY;
		this.VATRATE = StringConst.EMPTY;
		this.ACNIDCODE = StringConst.EMPTY;
		this.ACNIDDATE = StringConst.EMPTY;
		this.ACNIDPLACE = StringConst.EMPTY;
		this.CUSTODYCD = StringConst.EMPTY;
		this.MTFRTIME = StringConst.EMPTY;
		this.MTTOTIME = StringConst.EMPTY;
		this.TYPEMNEMONIC = StringConst.EMPTY;
		this.MNEMONIC = StringConst.EMPTY;
	}

	/**
	 * Trả về số tài khoản ngân hàng nếu là item chuyển tiền ra ngoài, trả về số
	 * lưu ký nếu là chuyển tiền nội bộ
	 * 
	 * @return
	 */
	public String getAccount() {
		if (TYPE.matches("0")) {
			return REG_CUSTODYCD;
		} else {
			return REG_ACCTNO;
		}
	}

	public String toString() {
		if (TYPE.matches("0")) {
			return REG_ACCTNO + "." + AFTYPE;
		}
		else {
			return REG_ACCTNO;
		}


	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.jsonobject.BankAccItem JD-Core Version: 0.6.0
 */