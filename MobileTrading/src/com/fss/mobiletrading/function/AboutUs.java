package com.fss.mobiletrading.function;

import java.util.ArrayList;
import java.util.List;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msbuat.mobiletrading.R;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.ContactInfoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.MainActivity;

public class AboutUs extends AbstractFragment {

	public static final String CONTACTINFO = "ContactInfoService#CONTACTINFO";

	LinearLayout btn_Email;
	LinearLayout btn_Yahoo;
	LinearLayout btn_Skype;
	LinearLayout btn_Facebook;
	LinearLayout btn_Phone1;
	LinearLayout btn_Phone2;
	TextView tv_phone1;
	LinearLayout lay_aboutus_phone1;
	TextView tv_phone2;
	Intent emailIntent;
	Intent facebookIntent;
	Intent skypeIntent;
	Intent yahooIntent;
	Intent callIntent;
	String[] phones;

	public String[] getPhones() {
		return phones;
	}

	public static AboutUs newInstance(MainActivity mActivity) {

		AboutUs self = new AboutUs();
		self.mainActivity = mActivity;
		self.TITLE = mActivity.getStringResource(R.string.About);
		return self;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.aboutus, viewGroup, false);
		btn_Email = (LinearLayout) view.findViewById(R.id.lay_aboutus_mail);
		btn_Yahoo = (LinearLayout) view.findViewById(R.id.lay_aboutus_yahoo);
		btn_Yahoo.setVisibility(View.GONE);
		btn_Skype = (LinearLayout) view.findViewById(R.id.lay_aboutus_skype);
		btn_Skype.setVisibility(View.GONE);
		btn_Facebook = (LinearLayout) view.findViewById(R.id.lay_aboutus_face);
		btn_Facebook.setVisibility(View.GONE);
		btn_Phone1 = (LinearLayout) view.findViewById(R.id.lay_aboutus_phone1);
		btn_Phone2 = (LinearLayout) view.findViewById(R.id.lay_aboutus_phone2);
		btn_Phone2.setVisibility(View.GONE);
		tv_phone1 = (TextView) view.findViewById(R.id.text_aboutus_phone1);
		tv_phone2 = (TextView) view.findViewById(R.id.text_aboutus_phone2);

		init();
		initListener();
		return view;
	}

	private void init() {

	}

	public void initIntent(ContactInfoItem item) {
		if (item == null) {
			return;
		}
		// khởi tạo cho emailIntent
		emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("text/plain");
		emailIntent.putExtra(Intent.EXTRA_EMAIL, item.getEmail().split(","));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
		emailIntent.putExtra(Intent.EXTRA_TEXT, "text");

		// khởi tạo cho facebookIntent
		try {
			facebookIntent = new Intent(Intent.ACTION_VIEW);
			facebookIntent.setData(Uri.parse("fb://profile/"
					+ item.getIdFacebook()));
		} catch (ActivityNotFoundException e) {
		} catch (Exception e) {
		}

		try {
			// khởi tạo cho yahooIntent
			yahooIntent = mainActivity.getPackageManager()
					.getLaunchIntentForPackage(
							"com.yahoo.mobile.client.android.im");
			// yahooIntent = new Intent(Intent.ACTION_VIEW,
			// Uri.parse("imto:huong314@yahoo.com"));
			// yahooIntent.setComponent(new ComponentName(
			// "com.yahoo.mobile.client.android.im",
			// "com.yahoo.mobile.client.android.im.SendToActivity"));
			yahooIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		} catch (ActivityNotFoundException e) {
			// yahooIntent.setData(Uri.parse("https://vn.yahoo.com/"));
		} catch (Exception e) {
		}

		try {
			// khởi tạo dữ liệu cho skypeIntent
			skypeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("skype:"
					+ item.getUserSkype() + "?chat"));
			skypeIntent.setComponent(new ComponentName("com.skype.raider",
					"com.skype.raider.Main"));
			skypeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		} catch (ActivityNotFoundException e) {
			// skypeIntent.setData(Uri.parse("https://vn.yahoo.com/"));
		} catch (Exception e) {
		}

		try {
			callIntent = new Intent(Intent.ACTION_CALL);
		} catch (Exception e) {
		}

		phones = item.getPhone().split("/");
	}

	private void initListener() {
		btn_Email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openEmailApp();
			}
		});
		btn_Yahoo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String msg = openYahoo();
				if (msg.length() > 0) {
					showDialogMessage(
							mainActivity.getStringResource(R.string.thong_bao),
							msg);
				}
			}
		});
		btn_Skype.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String msg = openSkype();
				if (msg.length() > 0) {
					showDialogMessage(
							mainActivity.getStringResource(R.string.thong_bao),
							msg);
				}
			}
		});
		btn_Facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openFaceBook();
			}
		});
		btn_Phone1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callPhone(tv_phone1.getText().toString());
			}
		});
		btn_Phone2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callPhone(tv_phone2.getText().toString());
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		CallContactInfo(this);
	}

	public void CallContactInfo(INotifier notifier) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(MSTradeAppConfig.controller_ContactInfo);
		}
		StaticObjectManager.connectServer.callHttpPostService(CONTACTINFO,
				notifier, list_key, list_value);
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		ContactInfoItem contactInfo = (ContactInfoItem) rObj.obj;
		initIntent(contactInfo);
		if (phones != null) {
			if (phones.length >= 1) {
				tv_phone1.setText(phones[0]);
			}
			if (phones.length >= 2) {
				tv_phone2.setText(phones[1]);
			}
		}
	}

	public void openEmailApp() {
		if (emailIntent == null) {
			return;
		}
		try {
			mainActivity.startActivity(Intent.createChooser(emailIntent,
					"Send email..."));
		} catch (Exception e) {
		}

	}

	public void openFaceBook() {
		if (facebookIntent == null) {
			return;
		}
		try {
			mainActivity.startActivity(facebookIntent);
		} catch (Exception e) {
			e.printStackTrace();
			facebookIntent.setData(Uri.parse("https://www.facebook.com"));
			mainActivity.startActivity(facebookIntent);
		}

	}

	public String openSkype() {
		if (skypeIntent == null) {
			return mainActivity
					.getStringResource(R.string.SkypeChuaDuocCaiDatSan);
		}
		try {
			mainActivity.startActivity(skypeIntent);
		} catch (Exception e) {
			return mainActivity
					.getStringResource(R.string.SkypeChuaDuocCaiDatSan);
		}
		return StringConst.EMPTY;
	}

	public String openYahoo() {
		if (yahooIntent == null) {
			return mainActivity
					.getStringResource(R.string.YahooChuaDuocCaiDatSan);
		}
		try {
			mainActivity.startActivity(yahooIntent);
		} catch (Exception e) {
			return mainActivity
					.getStringResource(R.string.YahooChuaDuocCaiDatSan);
		}
		return StringConst.EMPTY;
	}

	public void callPhone(String phone) {
		if (callIntent == null) {
			return;
		}
		// format số điện thoại theo định dạng +841689903...
		callIntent.setData(Uri.parse("tel:" + "+"
				+ phone.replace("(", "".replace(")", "").trim())));

		try {
			mainActivity.startActivity(callIntent);
		} catch (Exception e) {
		}

	}
}
