package com.fss.mobiletrading.function.NTSListener;

import android.util.Log;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.AccountBalance;
import com.fss.mobiletrading.function.CashStatement;
import com.fss.mobiletrading.function.Statement;
import com.fss.mobiletrading.function.StockStatement;
import com.fss.mobiletrading.function.cashtransfer.CashTransfer;
import com.fss.mobiletrading.function.orderlist.GTCOrderDetail;
import com.fss.mobiletrading.function.orderlist.GTCOrderList;
import com.fss.mobiletrading.function.orderlist.NormalOrderList;
import com.fss.mobiletrading.function.orderlist.OrderDetail;
import com.fss.mobiletrading.function.placeorder.GTCPlaceOrder;
import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fss.mobiletrading.function.portfolio.Porfolio;
import com.fss.mobiletrading.function.stocktransfer.StockTransfer;
import com.logging.LogConfig;
import com.msbuat.mobiletrading.MSTradeAppConfig;
import com.msbuat.mobiletrading.MyApplication;
import com.webservices.signalr_websocket.SignalrConnManager;
import com.webservices.signalr_websocket.SignalrConnection;

import java.util.Timer;
import java.util.TimerTask;

import microsoft.aspnet.signalr.client.Action;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;

/**
 * Created by Admin on 06-06-2016.
 */
public class NTSHandler {

    public static final int CHECK_NEWEVENTWS_PRIOD = 1000;
    public static final String orderChange = "OD";
    public static final String cashBalanceChange = "CI";
    public static final String stockBalanceChange = "SE";

    public static final String orderChangeFragment = PlaceOrder.class.getName() + "#"
            + GTCPlaceOrder.class.getName() + "#"
            + NormalOrderList.class.getName() + "#"
            + GTCOrderList.class.getName() + "#"
            + OrderDetail.class.getName() + "#"
            + GTCOrderDetail.class.getName() + "#";
    public static final String cashBalanceChangeFragment = PlaceOrder.class.getName() + "#"
            + GTCPlaceOrder.class.getName() + "#"
            + AccountBalance.class.getName() + "#"
            + CashTransfer.class.getName() + "#"
            + CashStatement.class.getName() + "#"
            + Statement.class.getName() + "#";
    public static final String stockBalanceChangeFragment =
            Porfolio.class.getName() + "#"
                    + StockTransfer.class.getName() + "#"
                    + StockStatement.class.getName() + "#"
                    + Statement.class.getName() + "#";

    static final String webSocketTag = "NTSServer.NTSHub";
    static final String NTSChannel = "M";
    static final String GetAllInfoMethod = "GetAllInfo";
    static final String EventName = "updateInfo";

    static NTSHandler mInstance;

    Timer checkNewEventWS;
    private boolean hasODEvent = false;
    private boolean hasCIEvent = false;
    private boolean hasSEEvent = false;

    SignalrConnection ntsInfoReceiver;

    NTSUpdateInfoListener ntsUpdateInfoListener;

    public static NTSHandler newInstance() {
        mInstance = new NTSHandler();
        return mInstance;
    }

    public NTSHandler() {

    }

    public void connect() {
        ntsInfoReceiver = SignalrConnManager.getInstance().
                createSignalrConn(webSocketTag, MSTradeAppConfig.NTServerUrl, MSTradeAppConfig.NTServerHubName);
        if (ntsInfoReceiver != null) {
            ntsInfoReceiver.setOnConnectionDoneListener(new SignalrConnManager.OnConnectionDoneListener() {
                @Override
                public void onConnectionDone() {
                    getAllInfo();
                }
            });
            listenerEvent(EventName);
            ntsInfoReceiver.startConnect();
            runJobCheckNewEventWS();
        } else {
            Log.e(LogConfig.INFO_TAG, String.format("NTSHandler.connect.: Connection to %s/%s is failed", MSTradeAppConfig.NTServerUrl, MSTradeAppConfig.NTServerHubName));
        }
    }

    private void listenerEvent(String eventName) {
        if (ntsInfoReceiver != null) {
            Log.i(LogConfig.TESTLOG_TAG, "register handler event");
            ntsInfoReceiver.getHubProxy().on(eventName, new SubscriptionHandler1<NTSInfo>() {
                @Override
                public void run(NTSInfo nTSInfo) {
                    Log.i(LogConfig.INFO_TAG, "NTSHandler.nTSInfo:" + nTSInfo);
                    if (MyApplication.isActivityVisible()) {
                        try {
                            if (nTSInfo.e.contains(NTSHandler.orderChange)) {
                                hasODEvent = true;
                            }
                            if (nTSInfo.e.contains(NTSHandler.cashBalanceChange)) {
                                hasCIEvent = true;
                            }
                            if (nTSInfo.e.contains(NTSHandler.stockBalanceChange)) {
                                hasSEEvent = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, NTSInfo.class);
        }
    }

    private void getAllInfo() {
        String tokenID = StaticObjectManager.accessTokenName;
        String Username = StaticObjectManager.loginInfo.UserName;
        String Channel = NTSChannel;
        String AfList = StaticObjectManager.getAfAcctnoList();
        UserParams userParams = new UserParams(tokenID, Username, Channel, AfList);
        ntsInfoReceiver.getHubProxy().invoke(NTSInfo.class, GetAllInfoMethod, userParams).done(new Action<NTSInfo>() {
            @Override
            public void run(NTSInfo nTSInfo) throws Exception {
                Log.i(LogConfig.INFO_TAG, "GetAllInfo.nTSInfo:" + nTSInfo);
            }
        });
    }

    public void closeConnectWebsocket() {
        ntsInfoReceiver.disconnect();
    }

    private void runJobCheckNewEventWS() {
        if (checkNewEventWS != null) {
            checkNewEventWS.cancel();
        }
        checkNewEventWS = new Timer();
        checkNewEventWS.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (hasODEvent) {
                    hasODEvent = false;
                    if (ntsUpdateInfoListener != null) {
                        ntsUpdateInfoListener.receiveODEvent();
                    }
                }
                if (hasSEEvent) {
                    hasSEEvent = false;
                    if (ntsUpdateInfoListener != null) {
                        ntsUpdateInfoListener.receiveSEEvent();
                    }
                }
                if (hasCIEvent) {
                    hasCIEvent = false;
                    if (ntsUpdateInfoListener != null) {
                        ntsUpdateInfoListener.receiveCIEvent();
                    }
                }

            }
        }, 0, CHECK_NEWEVENTWS_PRIOD);
    }

    public void setNtsUpdateInfoListener(NTSUpdateInfoListener ntsUpdateInfoListener) {
        this.ntsUpdateInfoListener = ntsUpdateInfoListener;
    }
}
