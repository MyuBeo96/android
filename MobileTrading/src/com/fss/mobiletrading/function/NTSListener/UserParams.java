package com.fss.mobiletrading.function.NTSListener;

/**
 * Created by Admin on 07-06-2016.
 */
public class UserParams {
    public UserParams(String tokenID, String username, String channel, String afList) {
        this.tokenID = tokenID;
        Username = username;
        Channel = channel;
        AfList = afList;
    }

    public String tokenID;
    public String Username;
    public String Channel;
    public String AfList;

    @Override
    public String toString() {
        return "UserParams[tokenID:"+tokenID+" Username:"+Username+" Channel:"+Channel+" AfList:"+AfList+"]";
    }
}
