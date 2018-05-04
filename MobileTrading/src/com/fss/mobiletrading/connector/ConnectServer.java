package com.fss.mobiletrading.connector;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.function.AppConfig;
import com.fss.mobiletrading.interfaces.INotifier;
import com.fss.mobiletrading.jsonservice.ATService;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonArrayService;
import com.fss.mobiletrading.jsonservice.AbstractProcessJsonObjectService;
import com.fss.mobiletrading.object.ResultObj;
import com.msbuat.mobiletrading.BuildConfig;
import com.msbuat.mobiletrading.MSTradeAppConfig;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class ConnectServer implements IConnectServer {
    private static final int THREAD_POOL = 20;
    private static final int TIMEOUT = 20000;
    public static DefaultHttpClient client;
    public HashMap<String, Object> mapService = new HashMap<String, Object>();
    String TAG_CONNECTSERVER = "ConnectServer";
    static final String post = "POST";

    private static final String HEADER_COOKIES = "Cookie";

    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String ENCODING_GZIP = "gzip";
    private static final String REQ_ENCODING_GZIP = "gzip, deflate";
    private static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    private static final String HEADER_CONTENT_TYPE = "Content-type";
    private static final String VALUE_CONTENT_TYPE = "application/json";

    private BasicHttpParams longHttpParams;
    HashMap<String, AsyncTask> stackRequest = new HashMap<>();

    // khởi tạo
    public ConnectServer() {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        longHttpParams = new BasicHttpParams();
        initHttpParams(basicHttpParams, 20000);
        initHttpParams(longHttpParams, 120000);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory
                .getSocketFactory(), 443));
        ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(
                basicHttpParams, schemeRegistry);
        client = new DefaultHttpClient(threadSafeClientConnManager,
                basicHttpParams);

        client.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) {
                // Add header to accept gzip content
                if (!request.containsHeader(HEADER_ACCEPT_ENCODING)) {
                    request.addHeader(HEADER_ACCEPT_ENCODING, REQ_ENCODING_GZIP);
                }
                // Add header to set Content-Type
                if (!request.containsHeader(HEADER_CONTENT_TYPE)) {
                    request.addHeader(HEADER_CONTENT_TYPE, VALUE_CONTENT_TYPE);
                } else {
                    request.setHeader(HEADER_CONTENT_TYPE, VALUE_CONTENT_TYPE);
                }
            }
        });

        client.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse response, HttpContext context) {

                if (response.containsHeader(HEADER_CONTENT_ENCODING)) {
                    if (response.getFirstHeader(HEADER_CONTENT_ENCODING)
                            .getValue().equals(ENCODING_GZIP)) {
                        response.setEntity(new InflatingEntity(response
                                .getEntity()));
                    }
                }
            }
        });
    }

    private void initHttpParams(HttpParams params, int timeout) {
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
        HttpProtocolParams.setUseExpectContinue(params, true);
        HttpConnectionParams.setConnectionTimeout(params, timeout);
        HttpConnectionParams.setSoTimeout(params, timeout);
    }

    /**
     * nếu có kết nối internet thì khởi tạo cục kết nối
     *
     * @param context
     * @throws NullPointerException nếu context == null
     */
    public static void checkConnection(Context context) {

        if (ConnectionDetector.isConnectingToInternet(context
                .getApplicationContext())) {
        } else {
        }
        StaticObjectManager.connectServer = new ConnectServer();
    }

    private void updateRequestToStack(String apiIdentify, AsyncTask task) {
        AsyncTask oldTask = stackRequest.get(apiIdentify);
        if (oldTask != null && oldTask.getStatus() != AsyncTask.Status.FINISHED) {
            oldTask.cancel(true);
        }
        stackRequest.put(apiIdentify, task);
    }

    public void callHttpGetService(String key, INotifier iNotifier, String link) {
        if (link.length() == 0) {
            return;
        }
        HttpGetService localHttpGetService = new HttpGetService(iNotifier, key);
        updateRequestToStack(key, localHttpGetService);
        String[] stringArray = new String[1];
        stringArray[0] = link;
        try {
            localHttpGetService.executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, stringArray);
        } catch (IllegalStateException e) {
            iNotifier.notifyResult(key, null);
        }

    }

    public void callGetATService(String key, INotifier iNotifier, String link) {
        Log.i(key, link);

        GetATService getATService = new GetATService(iNotifier, key);
        String[] stringArray = new String[1];
        stringArray[0] = link;
        getATService.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                stringArray);
    }

    public void callHttpPostService(String key, INotifier iNotifier,
                                    List<String> list_key, List<String> list_value) {
        if (list_value.get(0).length() == 0) {
            return;
        }
        HttpPostService httpPostService = new HttpPostService(iNotifier, key);
        updateRequestToStack(key, httpPostService);
        List[] arrayList = new List[2];
        arrayList[0] = list_key;
        arrayList[1] = list_value;
        try {
            httpPostService.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                    arrayList);
        } catch (IllegalStateException e) {
            iNotifier.notifyResult(key, null);
        }

    }

    /**
     * gọi request post lên server với thời gian chờ lâu
     *
     * @param key
     * @param iNotifier
     * @param list_key
     * @param list_value
     * @param isLongRequest bằng true nếu muốn tăng thời gian chờ
     */
    public void callHttpPostService(String key, INotifier iNotifier,
                                    List<String> list_key, List<String> list_value,
                                    boolean isLongRequest) {
        if (list_value.get(0).length() == 0) {
            return;
        }
        HttpPostService httpPostService = new HttpPostService(iNotifier, key);
        updateRequestToStack(key, httpPostService);
        if (isLongRequest) {
            httpPostService.setHttpparams(longHttpParams);
        }
        List[] arrayList = new List[2];
        arrayList[0] = list_key;
        arrayList[1] = list_value;
        try {
            httpPostService.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                    arrayList);
        } catch (IllegalStateException e) {
            iNotifier.notifyResult(key, null);
        }

    }

    class HttpGetService extends AsyncTask<String, String, ResultObj> {
        String key;
        INotifier notifier;

        public HttpGetService(INotifier iNotifier, String key) {
            this.notifier = iNotifier;
            this.key = key;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG_CONNECTSERVER, key + " onPreExecute");

        }

        protected ResultObj doInBackground(String... params) {
            Log.i(TAG_CONNECTSERVER, key + " doInBackground");
//            Log.i(TAG_CONNECTSERVER, key +MSTradeAppConfig.address_server + params[0] );
//            HttpGet get = new HttpGet(MSTradeAppConfig.address_server
//                    + params[0]);
            Log.i(TAG_CONNECTSERVER, key + BuildConfig.address_server + params[0] );
            HttpGet get = new HttpGet(BuildConfig.address_server
                    + params[0]);
            get.addHeader("Cookie", StaticObjectManager.sessionCookie);
            String result;
            try {
                HttpResponse response = ConnectServer.client.execute(get);
                if (response == null) {
                    result = "null";
                } else {
                    result = inputStreamToString(
                            response.getEntity().getContent()).toString();
                    if (result.length() == 0)
                        result = "no_data";
                }
            } catch (SocketException localSocketException) {
                result = "error1";
            } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
                localUnsupportedEncodingException.printStackTrace();
                result = "error1";
            } catch (ClientProtocolException localClientProtocolException) {
                localClientProtocolException.printStackTrace();
                result = "error1";
            } catch (IOException localIOException) {
                result = "error1";
                localIOException.printStackTrace();
            }

            // nhận về được chuỗi kết quả từ server trả về : result
            Log.i(key, result);
            long starttime = System.currentTimeMillis();
            ResultObj rObj = parseJson2Object(key, result, notifier);
            Log.i(TAG_CONNECTSERVER,
                    key + " is parsed"
                            + (System.currentTimeMillis() - starttime));
            return rObj;
        }

        protected void onPostExecute(ResultObj rObj) {
            super.onPostExecute(rObj);
            if (notifier != null) {
                notifier.notifyResult(key, rObj);
            }
        }
    }

    class HttpPostService extends AsyncTask<List<String>, String, ResultObj> {
        String key;
        INotifier notifier;
        HttpParams httpparams;

        public HttpPostService(INotifier iNotifier, String key) {
            this.notifier = iNotifier;
            this.key = key;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        public void setHttpparams(HttpParams httpparams) {
            this.httpparams = httpparams;
        }

        @Override
        protected ResultObj doInBackground(List<String>... params) {
            Log.i(TAG_CONNECTSERVER, key + " doInBackground");
            String result;
//            String url = MSTradeAppConfig.address_server + params[1].get(0);
            String url = BuildConfig.address_server + params[1].get(0);
            Log.d("url", url);
            HttpPost post = new HttpPost(url);
            if (httpparams != null) {
                post.setParams(httpparams);
            }
            String param = "{";
            for (int i = 1; i < params[0].size(); i++) {
                if (params[1].get(i) == null) {
                    param = param + "\"" + params[0].get(i) + "\":\"\"";

                } else {
                    param = param + "\"" + params[0].get(i) + "\":\"" + params[1].get(i) + "\"";
                }
                if (i != params[0].size() - 1) {
                    param = param + ",";
                }
            }
            param = param + "}";
            Log.i(TAG_CONNECTSERVER,key+ ":url:" + url);
            Log.i(TAG_CONNECTSERVER,key+ ":params:" + param);


            try {
                StringEntity jsonPara = new StringEntity(param, "UTF-8");
                post.setEntity(jsonPara);
                post.addHeader("Cookie", StaticObjectManager.sessionCookie);
                HttpResponse response = ConnectServer.client.execute(post);
                if (response == null) {
                    result = "null";
                } else {
                    result = inputStreamToString(
                            response.getEntity().getContent()).toString();
                    if (result.length() == 0)
                        result = "no_data";
                }
            } catch (SocketException socketException) {
                result = "error1";
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                unsupportedEncodingException.printStackTrace();
                result = "error1";
            } catch (ClientProtocolException clientProtocolException) {
                clientProtocolException.printStackTrace();
                result = "error1";
            } catch (IOException iOException) {
                result = "error1";
                iOException.printStackTrace();
            }

            // nhận về được chuỗi kết quả từ server trả về : result
            Log.i(key, result);
            Log.i("url 1", result);
            long starttime = System.currentTimeMillis();
            ResultObj rObj = parseJson2Object(key, result, notifier);
            Log.i(TAG_CONNECTSERVER,
                    key + " is parsed"
                            + (System.currentTimeMillis() - starttime));
            return rObj;
        }

        protected void onPostExecute(ResultObj rObj) {
            super.onPostExecute(rObj);
            if (notifier != null) {
                notifier.notifyResult(key, rObj);
            }
        }
    }

    private ResultObj parseJson2Object(String key, String result,
                                       INotifier notifier) {
        String[] service = key.split("#");
        if (!mapService.containsKey(service[0])) {
            String classPath = "com.fss.mobiletrading.jsonservice."
                    + service[0];
            String classPath1 = null;
            if (notifier != null) {
                classPath1 = notifier.getClass().getPackage().getName() + "."
                        + service[0];
            }

            Class cls = null;
            if (classPath1 != null) {
                try {
                    cls = Class.forName(classPath1);
                    mapService.put(service[0], cls.newInstance());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (cls == null) {
                try {
                    cls = Class.forName(classPath);
                    mapService.put(service[0], cls.newInstance());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {

                    e.printStackTrace();
                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                }
            }
        }
        Object obj = mapService.get(service[0]);
        if (obj != null) {
            if (obj instanceof AbstractProcessJsonArrayService) {
                ResultObj rObj = ((AbstractProcessJsonArrayService) obj)
                        .getArrayResult(result);
                return rObj;
            } else {
                ResultObj rObj = ((AbstractProcessJsonObjectService) obj)
                        .getObjectResult(result);
                return rObj;
            }
        } else {
            return null;
        }
    }

    public StringBuilder inputStreamToString(InputStream paramInputStream)
            throws IOException {

        StringBuilder localStringBuilder = new StringBuilder();

        try {
            InputStreamReader isr = new InputStreamReader(paramInputStream);
            BufferedReader localBufferedReader = new BufferedReader(isr);
            char[] buffer = new char[1024 * 4];
            int n = 0;
            while (-1 != (n = localBufferedReader.read(buffer))) {
                localStringBuilder.append(buffer, 0, n);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return localStringBuilder;
    }

    class GetATService extends AsyncTask<String, String, String> {
        String key;
        INotifier notifier;

        public GetATService(INotifier iNotifier, String key) {
            this.notifier = iNotifier;
            this.key = key;
        }

        protected String doInBackground(String... params) {
//            HttpGet get = new HttpGet(MSTradeAppConfig.address_server
//                    + params[0]);
            HttpGet get = new HttpGet(BuildConfig.address_server
                    + params[0]);
            String result;
            get.addHeader("Cookie", StaticObjectManager.sessionCookie);
            try {
                HttpResponse response = ConnectServer.client.execute(get);
                if (response == null) {
                    result = "null";
                } else {
                    result = inputStreamToString(
                            response.getEntity().getContent()).toString();
                    if (result.length() == 0)
                        result = "no_data";
                }
            } catch (SocketException localSocketException) {
                result = "error1";
            } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
                localUnsupportedEncodingException.printStackTrace();
                result = "error1";
            } catch (ClientProtocolException localClientProtocolException) {
                localClientProtocolException.printStackTrace();
                result = "error1";
            } catch (IOException localIOException) {
                result = "error1";
                localIOException.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i(key, result);
            ResultObj rObj = new ATService().processObject(result, null);

            if (notifier != null) {
                notifier.notifyResult(key, rObj);
            }
        }
    }

    class InflatingEntity extends HttpEntityWrapper {
        public InflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        @Override
        public InputStream getContent() throws IOException {
            return new GZIPInputStream(wrappedEntity.getContent());
        }

        @Override
        public long getContentLength() {
            return -1;
        }
    }

    public void parseInputStream(String key, INotifier iNotifier, InputStream is) {
        JsonParserAsyn jsonParserAsyn = new JsonParserAsyn(iNotifier, key);
        try {
            jsonParserAsyn
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, is);
        } catch (IllegalStateException e) {
            iNotifier.notifyResult(key, null);
        }

    }

    class JsonParserAsyn extends AsyncTask<InputStream, String, ResultObj> {
        String key;
        INotifier notifier;

        public JsonParserAsyn(INotifier iNotifier, String key) {
            this.notifier = iNotifier;
            this.key = key;
        }

        @Override
        protected ResultObj doInBackground(InputStream... params) {
            String result = null;
            try {
                result = inputStreamToString(params[0]).toString();
                Log.i(key, result);
            } catch (IOException e) {
            }
            Log.i(key, "data.:" + result);
            ResultObj rObj = parseJson2Object(key, result, notifier);
            return rObj;
        }

        @Override
        protected void onPostExecute(ResultObj result) {
            super.onPostExecute(result);
            if (notifier != null) {
                notifier.notifyResult(key, result);
            }
        }

    }
}
