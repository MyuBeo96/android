package com.webservices.signalr_websocket;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by giang.ngo on 03-03-2016.
 */
public class SignalrConnManager {

    private static SignalrConnManager mInstance;
    private Map<String, SignalrConnection> stackInstance;

    public SignalrConnManager() {
        stackInstance = new HashMap<String, SignalrConnection>();
    }

    public static synchronized SignalrConnManager getInstance() {
        if (null == mInstance) {
            mInstance = new SignalrConnManager();
        }
        return mInstance;
    }

    public synchronized SignalrConnection createSignalrConn(String tag, String server, String hubProxy) {
        if (stackInstance.containsKey(tag)) {
            SignalrConnection oldConnect = stackInstance.get(tag);
            oldConnect.disconnect();
            stackInstance.remove(tag);
        }

        SignalrConnection aInstance = new SignalrConnection();
        boolean isSuccessCreateConn = aInstance.initHubConnection(server, hubProxy);
        if (isSuccessCreateConn) {
            stackInstance.put(tag, aInstance);
            return aInstance;
        } else {
            return null;
        }

    }

    public interface OnConnectionDoneListener {
        void onConnectionDone();
    }
}
