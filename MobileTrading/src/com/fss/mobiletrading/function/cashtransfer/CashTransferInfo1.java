package com.fss.mobiletrading.function.cashtransfer;

public class CashTransferInfo1 {
	private String BALANCE;
	private String ADVAMT;
	private String FEEAMT;
	private String AVLWITHDRAW;

	public CashTransferInfo1(String bALANCE, String aDVAMT, String fEEAMT,
			String aVLWITHDRAW) {
		super();
		BALANCE = bALANCE;
		ADVAMT = aDVAMT;
		FEEAMT = fEEAMT;
		AVLWITHDRAW = aVLWITHDRAW;
	}

	public String getBALANCE() {
		return BALANCE;
	}

	public String getADVAMT() {
		return ADVAMT;
	}

	public String getFEEAMT() {
		return FEEAMT;
	}

	public String getAVLWITHDRAW() {
		return AVLWITHDRAW;
	}

}
