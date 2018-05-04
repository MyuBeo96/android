package com.fss.mobiletrading.object;

import java.util.List;

public class BRRptItem {
	public String RptName;
	public String RptId;
	public String RptSource;
	public List<RptFieldItem> list_RptField;

	public BRRptItem(String rptName, String rptId, String rptSource,
			List<RptFieldItem> list_RptField) {
		super();
		RptName = rptName;
		RptId = rptId;
		RptSource = rptSource;
		this.list_RptField = list_RptField;
	}

}
