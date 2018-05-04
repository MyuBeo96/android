package com.fss.mobiletrading.service;

import com.fss.mobiletrading.common.MyJsonObject;
import com.fss.mobiletrading.function.AppConfig;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonObjectService;
import com.fss.mobiletrading.object.ResultObj;

import org.json.JSONException;

/**
 * Created by Pham Khang on 8/8/2016.
 */
public class GetConfigServerService extends AbstractProcessJsonObjectService {

    @Override
    protected ResultObj processObject(MyJsonObject DT, String EM, int EC) {
        AppConfig appConfig = AppConfig.getInstance();
        try {
            String enableSSO = (String) DT.get("enableSSO");
            enableSSO.toUpperCase();
            if (enableSSO.equals("TRUE")) {
                appConfig.setEnableSSO(true);
            } else if (enableSSO.equals("FALSE")) {
                appConfig.setEnableSSO(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ResultObj(EC, EM, appConfig);
    }
}