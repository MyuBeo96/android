package com.fss.mobiletrading.function;

/**
 * Created by Pham Khang on 8/8/2016.
 */
public class AppConfig {
    private static AppConfig instance = new AppConfig();
    public Boolean enableSSO = true;
    public static AppConfig getInstance(){
        return instance;
    }
    public void setEnableSSO(Boolean enableSSO){
        instance.enableSSO = enableSSO;
    }
    public Boolean getEnableSSO(){
        return  instance.enableSSO;
    }
}
