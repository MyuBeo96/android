package com.fss.mobiletrading.allocateguarantee;

import java.util.HashMap;

public class AllocateInfo {
    public String CUSTODYCD;
    public String FULLNAME;
    public String CONTRACTCHK;
    public String USERTYPE;
    public String USERID;
    public String ACCTNO;
    public String T0CAL;
    public String ADVANCELINE;
    public String MARGINRATE;
    public String MARGINRATE_T0;
    public String SETOTAL;
    public String TOTALLOAN;
    public String PP;
    public String T0DEB;
    public String PERIOD;
    public String T0AMTUSED;
    public String TOAMT;
    public String SYMBOLAMT;
    public String SOURCE;
    public String TLID;
    public String TLIDNAME;
    public String RLIMIT;
    public String ACCLIMIT;
    public String CUSTAVLLIMIT;
    public String ACCUSED;
    public String T0OVRQ;
    public String V_DESC;
    public String TLFULLNAME;
    public String TLGROUP;
    public String WARN_MESSAGE;
    public String NAVNO;
    public String SYMBOL;
    public String T0AMTPENDING;

    public AllocateInfo(HashMap<String, String> hm) {
        CUSTODYCD = hm.get("CUSTODYCD");
        FULLNAME = hm.get("FULLNAME");
        CONTRACTCHK = hm.get("CONTRACTCHK");
        USERTYPE = hm.get("USERTYPE");
        USERID = hm.get("USERID");
        ACCTNO = hm.get("ACCTNO");
        T0CAL = hm.get("T0CAL");
        ADVANCELINE = hm.get("ADVANCELINE");
        MARGINRATE = hm.get("MARGINRATE");
        MARGINRATE_T0 = hm.get("MARGINRATE_T0");
        SETOTAL = hm.get("SETOTAL");
        TOTALLOAN = hm.get("TOTALLOAN");
        PP = hm.get("PP");
        T0DEB = hm.get("T0DEB");
        PERIOD = hm.get("PERIOD");
        T0AMTUSED = hm.get("T0AMTUSED");
        T0AMTPENDING = hm.get("T0AMTPENDING");
        TOAMT = hm.get("TOAMT");
        SYMBOLAMT = hm.get("SYMBOLAMT");
        SOURCE = hm.get("SOURCE");
        TLID = hm.get("TLID");
        TLIDNAME = hm.get("TLIDNAME");
        RLIMIT = hm.get("RLIMIT");
        ACCLIMIT = hm.get("ACCLIMIT");
        CUSTAVLLIMIT = hm.get("CUSTAVLLIMIT");
        ACCUSED = hm.get("ACCUSED");
        T0OVRQ = hm.get("T0OVRQ");
        V_DESC = hm.get("V_DESC");
        TLFULLNAME = hm.get("TLFULLNAME");
        TLGROUP = hm.get("TLGROUP");
        WARN_MESSAGE = hm.get("WARN_MESSAGE");
        NAVNO = hm.get("NAVNO");
        SYMBOL = hm.get("SYMBOLFLAG");
        T0AMTPENDING = hm.get("T0AMTPENDING");
    }

    public boolean getSymbolFlag() {
        int symbol = Integer.parseInt(SYMBOL);
        if (symbol == 0) {
            return false; // An o nhap edittext
        } else {
            return true; // show o nhap edittext;
        }
    }

    public long getT0AMTPENDING() {
        long amtPending = Long.parseLong(T0AMTPENDING.replace(",", ""));
        return amtPending;
    }
}
