package com.msbuat.mobiletrading;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.AppData;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.map.BankMapPositionItem;
import com.fss.mobiletrading.object.ResultObj;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends Activity {

	static final String MMAP = "MMapService#MMAP";
	private GoogleMap googleMap;
	List<BankMapPositionItem> pointArr;
	AbstractFragment drawMap;
	Button btnBack;
	ArrayList<Integer> x = new ArrayList<Integer>();
	Button imgbtnSearch;
	boolean findLocation = true;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.map_activity);
		imgbtnSearch = (Button) findViewById(R.id.btn_Search);
		if (DeviceProperties.isTablet) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}

	private void showGPSDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(R.string.GPS)
				.setCancelable(false)
				.setPositiveButton(R.string.gotoGPS,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(callGPSSettingIntent);
							}
						});
		alertDialogBuilder.setNegativeButton(R.string.Cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						finish();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	private void showWifiDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(R.string.Wifi)
				.setCancelable(false)
				.setPositiveButton(R.string.gotoWifi,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_WIFI_SETTINGS);
								startActivity(callGPSSettingIntent);
							}
						});
		alertDialogBuilder.setNegativeButton(R.string.Cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						finish();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	private Boolean isOnlineWifi() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnected()) {
			return true;
		}
		return false;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle(R.string.MaritimeBankBranch);
		try {
			for (int i = 0; i < pointArr.size(); i++) {
				menu.add(0, i, 0, pointArr.get(i).getBrName());
			}
		} catch (Exception e) {
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		for (int i = 0; i < pointArr.size(); i++) {
			if (item.getItemId() == x.get(i)) {
				double lat = Double.parseDouble(pointArr.get(item.getItemId())
						.getLatitude());
				double lon = Double.parseDouble(pointArr.get(item.getItemId())
						.getLongitude());
				googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
						new LatLng(lat, lon), 16.0f));
				break;
			}
		}
		return super.onContextItemSelected(item);
	}

	public void back(View v) {
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
				&& isOnlineWifi()) {
			initilizeMap();
			drawMap = new AbstractFragment() {

				@Override
				protected void process(String key, ResultObj rObj) {
					switch (key) {
					case MMAP:
						List<BankMapPositionItem> data = (List<BankMapPositionItem>) rObj.obj;
						updatePosition(data);
						pointArr = data;
						for (int i = 0; i < pointArr.size(); i++) {
							x.add(i);
						}
						break;
					default:
						break;
					}
				}
			};
			imgbtnSearch.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					registerForContextMenu(imgbtnSearch);
					openContextMenu(imgbtnSearch);
				}
			});
		} else if (!locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			showGPSDisabledAlertToUser();
		} else
			showWifiDisabledAlertToUser();
		CallMMap(drawMap);
	}

	private void CallMMap(INotifier notifier) {
		List<String> list_key = new ArrayList<String>();
		List<String> list_value = new ArrayList<String>();
		{
			list_key.add("link");
			list_value.add(getResources().getString(R.string.controller_MMap));
		}
		{
			list_key.add("lang");
			list_value.add(AppData.language);
		}
		StaticObjectManager.connectServer.callHttpPostService(MMAP, notifier,
				list_key, list_value);

	}

	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map_mapactivity)).getMap();
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(), R.string.Sorry,
						Toast.LENGTH_SHORT).show();
			} else {
				googleMap.setMyLocationEnabled(true);
				googleMap
						.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

							@Override
							public void onMyLocationChange(Location arg0) {
								if (findLocation) {
									googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
											new LatLng(arg0.getLatitude(), arg0
													.getLongitude()), 16.0f));
									findLocation = false;
								}
							}
						});
			}
		}
	}

	private void updatePosition(List<BankMapPositionItem> positionList) {
		if (positionList != null && positionList.size() > 0) {
			for (int i = 0; i < positionList.size(); i++) {
				MarkerOptions markerOptions = new MarkerOptions();
				try {
					double latitude = Double.parseDouble(positionList.get(i)
							.getLatitude());
					double longitude = Double.parseDouble(positionList.get(i)
							.getLongitude());
					markerOptions.position(new LatLng(latitude, longitude));
					markerOptions.title(positionList.get(i).getBrName());
					markerOptions.snippet(positionList.get(i).getBrAddr());
					markerOptions.icon(
							BitmapDescriptorFactory
									.fromResource(R.drawable.location_icon))
							.draggable(true);
					googleMap.addMarker(markerOptions);
				} catch (NumberFormatException e) {
				}
			}
		} else {

		}

	}
}
