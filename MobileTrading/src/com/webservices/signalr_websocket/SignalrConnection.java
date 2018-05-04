package com.webservices.signalr_websocket;

import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonElement;
import com.logging.LogConfig;

import microsoft.aspnet.signalr.client.Action;
import microsoft.aspnet.signalr.client.ConnectionState;
import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.LogLevel;
import microsoft.aspnet.signalr.client.Logger;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.StateChangedCallback;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;

/**
 * Created by Admin on 18-07-2016.
 */
public class SignalrConnection {

    final static int TIME_RESTART = 3000;
    final static String SIGNALRCONNECTION_LOG = "Signalr Log";


    private HubConnection hubConnection;
    private HubProxy hubProxy;
    private boolean forceStop = false;  //bien chi ra rang hubconnection bat buoc bi dong, khong phai do mat ket noi
    Logger logger;
    SignalrConnManager.OnConnectionDoneListener onConnectionDoneListener;
    Handler handler;

    public HubProxy getHubProxy() {
        return hubProxy;
    }

    public SignalrConnection() {
        this.handler = new Handler();
        logger = initLogger();
    }

    public boolean initHubConnection(String server, String hubName) {

        forceStop = false;

        logger.log("Server:." + server + "\n" + "HubName:." + hubName, LogLevel.Information);

        // Create a connection to the server
        hubConnection = new HubConnection(server, "", true, logger);
        if (hubConnection == null) {
            logger.log("Connection creating is error", LogLevel.Critical);
            return false;
        }

        // Create the hub proxy
        hubProxy = hubConnection.createHubProxy(hubName);
        if (hubProxy == null) {
            logger.log("HubProxy creating is error", LogLevel.Critical);
            return false;
        }

        // Subscribe to the events
        hubConnection.error(new ErrorCallback() {

            @Override
            public void onError(Throwable error) {
                logger.log(error.getMessage(), LogLevel.Critical);
            }
        });

        hubConnection.connected(new Runnable() {

            @Override
            public void run() {
                logger.log("HubConnection is connected", LogLevel.Critical);
            }
        });
        hubConnection.closed(new Runnable() {

            @Override
            public void run() {
                logger.log("HubConnection is disconnected", LogLevel.Critical);
            }
        });

        hubConnection.received(new MessageReceivedHandler() {
            @Override
            public void onMessageReceived(JsonElement json) {
                logger.log("Receive:." + json, LogLevel.Information);
            }
        });
        hubConnection.stateChanged(new StateChangedCallback() {
            @Override
            public void stateChanged(ConnectionState oldState, final ConnectionState newState) {
                logger.log("oldState:." + oldState + " newState:." + newState, LogLevel.Information);
                if (newState == ConnectionState.Disconnected) {
                    if (!forceStop) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startConnect();
                            }
                        }, TIME_RESTART);
                    }
                } else if (newState == ConnectionState.Reconnecting) {
                    hubConnection.stop();
                    hubConnection.disconnect();
                }

            }
        });
        return true;
    }

    public SignalRFuture<Void> startConnect() {
        if (hubConnection != null) {
            SignalRFuture<Void> future = hubConnection.start()
                    .done(new Action<Void>() {
                        @Override
                        public void run(Void obj) throws Exception {
                            hubConnection.getLogger().log("HubConnection is done connecting", LogLevel.Information);
                            if (onConnectionDoneListener != null) {
                                onConnectionDoneListener.onConnectionDone();
                            }
                        }
                    }).onError(new ErrorCallback() {
                        @Override
                        public void onError(Throwable error) {
                            hubConnection.getLogger().log(error.getMessage(), LogLevel.Information);
                        }
                    });
            return future;
        }
        logger.log("Connection starting is error", LogLevel.Critical);
        return null;
    }

    public void disconnect() {
        try {
            forceStop = true;
            hubProxy = null;
            hubConnection.stop();
            hubConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Logger initLogger() {
        Logger logger = new Logger() {
            @Override
            public void log(String message, LogLevel level) {
                Log.i(LogConfig.DEBUG_TAG,SIGNALRCONNECTION_LOG+ message);
            }
        };
        return logger;
    }

    public void setOnConnectionDoneListener(SignalrConnManager.OnConnectionDoneListener onConnectionDoneListener) {
        this.onConnectionDoneListener = onConnectionDoneListener;
    }
}
