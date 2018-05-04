package com.fss.mobiletrading.function.welcomscreen;

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
//
        return new ResultObj(EC, EM, DT);
    }
}