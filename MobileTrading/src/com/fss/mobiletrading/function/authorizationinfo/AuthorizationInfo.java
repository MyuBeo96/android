package com.fss.mobiletrading.function.authorizationinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fss.mobiletrading.adapter.AuthorizationInfo_Adapter;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.object.AuthorizationInfoItem;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.AbstractFragment;
import com.msbuat.mobiletrading.MainActivity;
import com.msbuat.mobiletrading.R;
import com.msbuat.mobiletrading.DeviceProperties;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationInfo extends AbstractFragment {

	final String GETAUTHORITYINFO = "GetAuthorityInfoService#GETAUTHORITYINFO";

	ListView lv_Authorization;

	List<AuthorizationInfoItem> list_AuthorizationInfo;
	List<PermissionInfoItem> list_PermissionInfoItem;
	AuthorizationInfo_Adapter adapter_AuthorizationInfo;

	public PermissionObservable permissionObservable;
	PermissionTable permissionTable;

	public static AuthorizationInfo newInstance(MainActivity pr_mainActivity) {
		AuthorizationInfo authorizationInfo = new AuthorizationInfo();
		authorizationInfo.mainActivity = pr_mainActivity;
		if (authorizationInfo.permissionObservable == null) {
			authorizationInfo.permissionObservable = new PermissionObservable();
		}
		authorizationInfo.TITLE = pr_mainActivity
				.getStringResource(R.string.AuthorizationInfo);
		return authorizationInfo;
	}

	public AuthorizationInfo() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.authorization_info, container,
				false);
		lv_Authorization = (ListView) view
				.findViewById(R.id.listview_authorization_info);
		initialise();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		int width = getDimenResource(R.dimen.t_dialog_authorizationinfo_width);
		int height = getDimenResource(R.dimen.t_dialog_authorizationinfo_height);
		if (getDialog() != null) {
			getDialog().getWindow().setLayout(width, height);
		}
	}

	private void initialise() {

		initialiseData();
		initialiseListener();
	}

	private void initialiseData() {

		if (list_AuthorizationInfo == null) {
			list_AuthorizationInfo = new ArrayList<AuthorizationInfoItem>();
		}
		if (adapter_AuthorizationInfo == null) {
			adapter_AuthorizationInfo = new AuthorizationInfo_Adapter(
					getActivity(), android.R.layout.simple_list_item_1,
					list_AuthorizationInfo);

		}
		if (list_PermissionInfoItem == null) {
			list_PermissionInfoItem = new ArrayList<PermissionInfoItem>();
		}
		lv_Authorization.setAdapter(adapter_AuthorizationInfo);
	}

	private void initialiseListener() {

		lv_Authorization.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				try {
					permissionObservable.clearList();
					for (PermissionInfoItem item : list_PermissionInfoItem) {
						if (item.AUTHCUSTID.equals(list_AuthorizationInfo
								.get(position).CUSTID)) {
							permissionObservable.add(item);
						}
					}
					permissionObservable.notifyObservers();
					if (DeviceProperties.isTablet) {
						mainActivity.getFragmentByName(
								PermissionTable.class.getName()).onResume();
					} else {
						mainActivity.navigateFragment(PermissionTable.class
								.getName());
					}
				} catch (Exception e) {
				}
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		if (DeviceProperties.isTablet) {
			getChildFragmentManager().beginTransaction()
					.remove(permissionTable).commit();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		CallGetAuthorityInfo();
		if (DeviceProperties.isTablet) {
			if (permissionTable == null) {
				permissionTable = (PermissionTable) mainActivity
						.getFragmentByName(PermissionTable.class.getName());
			}
			getChildFragmentManager().beginTransaction()
					.replace(R.id.container_permissiontable, permissionTable)
					.commit();
		}
	}

	@Override
	public void addActionToActionBar() {
		super.addActionToActionBar();
	}

	private void CallGetAuthorityInfo() {

		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value
					.add(getStringResource(R.string.controller_GetAuthorityInfo));
		}

		StaticObjectManager.connectServer.callHttpPostService(GETAUTHORITYINFO,
				this, list_key, list_value);

	}

	@Override
	protected void process(String key, ResultObj rObj) {

		switch (key) {
		case GETAUTHORITYINFO:
			if (rObj.obj != null) {
				list_AuthorizationInfo.clear();
				Object[] array = (Object[]) rObj.obj;
				list_AuthorizationInfo
						.addAll((List<AuthorizationInfoItem>) array[0]);
				list_PermissionInfoItem.clear();
				list_PermissionInfoItem
						.addAll((List<PermissionInfoItem>) array[1]);

				adapter_AuthorizationInfo.notifyDataSetChanged();
				if (DeviceProperties.isTablet) {
					if (list_AuthorizationInfo.size() > 0) {
						lv_Authorization.performItemClick(
								adapter_AuthorizationInfo
										.getView(0, null, null), 0,
								adapter_AuthorizationInfo.getItemId(0));
					}
				}
			}
			break;

		default:
			break;
		}
	}

}
/*
 * { "EC": 0, "EM": "", "DT": { "DI": [ { "VALDATE": "19/12/2014", "EXPDATE":
 * "19/12/2044", "CUSTID": "0001018537", "FULLNAME": "NIệm Khìn", "IDCODE":
 * "12344321", "CUSTODYCD": "091C606060" }, { "VALDATE": "05/01/2015",
 * "EXPDATE": "05/01/2045", "CUSTID": "0001920014", "FULLNAME":
 * "Phan Thị Thanh Tuyền", "IDCODE": "300614601", "CUSTODYCD": "091C000033" } ],
 * "DR": [ { "AUTOID": "552812", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Chuyển tiền", "OTRIGHT": "NNNNNN", "DELTD": "N" },
 * { "AUTOID": "552813", "AFACCTNO": "0001020881", "AUTHCUSTID": "0001018537",
 * "OTMNCODE": "Ứng trước tiền bán", "OTRIGHT": "YYYYYN", "DELTD": "N" }, {
 * "AUTOID": "552815", "AFACCTNO": "0001020881", "AUTHCUSTID": "0001018537",
 * "OTMNCODE": "Đặt lệnh thông thường", "OTRIGHT": "YYYYYN", "DELTD": "N" }, {
 * "AUTOID": "552822", "AFACCTNO": "0001020881", "AUTHCUSTID": "0001018537",
 * "OTMNCODE": "Chuyển đổi trái phiếu", "OTRIGHT": "YYYYYN", "DELTD": "N" }, {
 * "AUTOID": "552824", "AFACCTNO": "0001020881", "AUTHCUSTID": "0001018537",
 * "OTMNCODE": "Tạo thông điệp cty chứng khoán", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "553043", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Đặt lệnh thông thường", "OTRIGHT": "YYYYYN",
 * "DELTD": "N" }, { "AUTOID": "552816", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Đặt lệnh điều kiện", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "552825", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Giao dịch lô lẻ", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "553046", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Tra cứu giao dịch", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "553050", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Chuyển đổi trái phiếu", "OTRIGHT": "YYYYYN",
 * "DELTD": "N" }, { "AUTOID": "553054", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Chuyển khoản chứng khoán", "OTRIGHT": "YYYYYN",
 * "DELTD": "N" }, { "AUTOID": "552818", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Tra cứu giao dịch", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "552819", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Cảnh báo thông minh", "OTRIGHT": "YYYYYN",
 * "DELTD": "N" }, { "AUTOID": "553041", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Ứng trước tiền bán", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "553042", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Đăng ký quyền mua", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "552823", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Gửi tiết kiệm", "OTRIGHT": "NNNNYN", "DELTD": "N"
 * }, { "AUTOID": "552826", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Chuyển khoản chứng khoán", "OTRIGHT": "YYYYYN",
 * "DELTD": "N" }, { "AUTOID": "553044", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Đặt lệnh điều kiện", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "553047", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Cảnh báo thông minh", "OTRIGHT": "YYYYYN",
 * "DELTD": "N" }, { "AUTOID": "553048", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Cảnh báo thị trường", "OTRIGHT": "YYYYYN",
 * "DELTD": "N" }, { "AUTOID": "552827", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Đăng ký người thụ hưởng", "OTRIGHT": "YYYYYN",
 * "DELTD": "N" }, { "AUTOID": "553045", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Đặt lệnh nhóm", "OTRIGHT": "YYYYYN", "DELTD": "N"
 * }, { "AUTOID": "553049", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Cảnh báo công ty", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "552817", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Đặt lệnh nhóm", "OTRIGHT": "YYYYYN", "DELTD": "N"
 * }, { "AUTOID": "552820", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Cảnh báo thị trường", "OTRIGHT": "YYYYYN",
 * "DELTD": "N" }, { "AUTOID": "553040", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Chuyển tiền", "OTRIGHT": "YYYYYN", "DELTD": "N" },
 * { "AUTOID": "553052", "AFACCTNO": "0001020881", "AUTHCUSTID": "0001920014",
 * "OTMNCODE": "Tạo thông điệp cty chứng khoán", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "553055", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Đăng ký người thụ hưởng", "OTRIGHT": "YYYYYN",
 * "DELTD": "N" }, { "AUTOID": "552814", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Đăng ký quyền mua", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "552821", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001018537", "OTMNCODE": "Cảnh báo công ty", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" }, { "AUTOID": "553051", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Gửi tiết kiệm", "OTRIGHT": "NNNNNN", "DELTD": "N"
 * }, { "AUTOID": "553053", "AFACCTNO": "0001020881", "AUTHCUSTID":
 * "0001920014", "OTMNCODE": "Giao dịch lô lẻ", "OTRIGHT": "YYYYYN", "DELTD":
 * "N" } ] } }
 */
