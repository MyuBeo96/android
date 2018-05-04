package com.fss.mobiletrading.object;

import com.fss.mobiletrading.consts.StringConst;

public class ResultObj {
	public int EC; // errorcode
	public String EM; // errormessage
	public Object obj;
	public int OEC;

	public ResultObj(int EC, String EM, Object obj) {
		this.EC = EC;
		this.EM = EM;
		this.obj = obj;
	}

	public ResultObj(int EC, String EM, Object obj, int OEC) {
		this.EC = EC;
		this.EM = EM;
		this.obj = obj;
		this.OEC = OEC;
	}

	public ResultObj() {
		this.EC = -100;
		this.EM = StringConst.EMPTY;
		this.obj = null;
	}

}
