package com.fss.mobiletrading.object;

public class ContactInfoItem {

	String idFacebook;
	String userSkype;
	String userYahoo;
	String email;
	String phone;

	public ContactInfoItem(String idFacebook, String userSkype,
			String userYahoo, String email, String phone) {
		super();
		this.idFacebook = idFacebook;
		this.userSkype = userSkype;
		this.userYahoo = userYahoo;
		this.email = email;
		this.phone = phone;
	}

	public String getIdFacebook() {
		return idFacebook;
	}

	public String getUserSkype() {
		return userSkype;
	}

	public String getUserYahoo() {
		return userYahoo;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}

}
