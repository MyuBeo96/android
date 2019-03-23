package com.fscuat.mobiletrading;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.fss.mobiletrading.common.Common;
import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.object.ResultObj;

public class OTPTypeActivity extends FragmentActivity implements INotifier {
    Button btnOTPTypeSMS;
    Button btnOTPTypeAPP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceProperties.isTablet = Common.isTablet(getApplicationContext());
        setContentView(R.layout.otptypeactivity);
        if (DeviceProperties.isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initView();
        initListener();
    }
    private void initView() {
        btnOTPTypeAPP= (Button) findViewById(R.id.btn_OTPType_APP);
        btnOTPTypeSMS= (Button) findViewById(R.id.btn_OTPType_SMS);
    }
    private void initListener(){
        btnOTPTypeAPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticObjectManager.otpType="APP";
                startMainActivity();
            }
        });
        btnOTPTypeSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticObjectManager.otpType="SMS";
                startMainActivity();
            }
        });
    }
    private  void startMainActivity(){
        if (DeviceProperties.isTablet) {
            startActivity(new Intent(this, MainActivity_Tablet.class));
        } else {
            Intent intentMobile = new Intent(this, MainActivity_Mobile.class);
            startActivity(intentMobile);
        }
    }
    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void notifyChangeAcctNo() {

    }
    @Override
    public void notifyResult(String key, ResultObj rObj) {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
