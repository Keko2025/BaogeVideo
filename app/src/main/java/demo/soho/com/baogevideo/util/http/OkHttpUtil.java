package demo.soho.com.baogevideo.util.http;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import demo.soho.com.baogevideo.model.CommonResponse;
import demo.soho.com.baogevideo.util.L;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dell on 2017/3/15.
 */
public class OkHttpUtil {
    public static String TAG = "debug-okhttp";
    public static boolean isDebug = true;
    private OkHttpClient client;
    public static final int TIMEOUT = 1000 * 60;                                                    // 超时时间
    public static final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");        //json请求
    private Handler handler = new Handler(Looper.getMainLooper());
    public OkHttpUtil() {
        init();
    }
    /**
     * construct
     * @param okHttpClient custom okhttpclient
     */
    public OkHttpUtil(OkHttpClient okHttpClient) {
        if(client == null) {
            synchronized (OkHttpUtil.class) {
                if (client == null) {
                    if (okHttpClient == null) {
                        client = new OkHttpClient();
                    } else {
                        client = okHttpClient;
                    }
                }
            }
        }
        client = okHttpClient;
    }
    private OkHttpClient init() {
        if(client == null){
            client = new OkHttpClient().newBuilder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
//                    .addInterceptor(new MyInterceptors())
                    .sslSocketFactory(createSSLSocketFactory())
                    .hostnameVerifier(new TrustAllHostnameVerifier())
                    .build();
        }
        return client;
    }

    //1、实现X509TrustManager接口：
    private static class TrustAllCerts implements X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        @Override
        public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
    }
    //2、实现HostnameVerifier接口：
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
    //3，方法createSSLSocketFactory()调用类TrustAllcert,获取SSLSocketFactory：
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null,  new TrustManager[] { new TrustAllCerts() }, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {}
        return ssfFactory;
    }

    /**
     * get请求
     * @param url
     * @param callback
     */
    public void get(String url, final HttpCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        onStart(callback);
        L.e("get方法url："+ url);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    L.e("responseBody:" + response.body());
                    if (response.isSuccessful() && response.body() != null) {
                        String msg = response.body().string();
                        CommonResponse jsonBean = new Gson().fromJson(msg,CommonResponse.class);
                        if(jsonBean.getStatus() != 1) {
                            onError(callback,msg);
                        }else {
                            onSuccess(callback,msg);
                        }
                    } else {
                        L.e("responseBody:" + response.body());
                        throw new IOException("Unexpected code " + response);
                    }
                }catch (IllegalStateException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                onFailureResp(callback, e.getMessage());
                L.e("onFailure():" + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    /**
     * post请求，json数据为body
     * @param url
     * @param json
     * @param callback
     */
    public void postJson(String url, String json, final HttpCallback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        onStart(callback);
        L.e("postJson:"+ url);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String msg = response.body().string();
                    CommonResponse reponse = new Gson().fromJson(msg,CommonResponse.class);
                    if(reponse.getStatus() == 1){
                        onSuccess(callback,msg);
                    }else {
                        onError(callback,msg);
                    }
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }
    /**
     * post请求 map
     * @param url
     * @param map
     * @param callback
     */
    public void post(String url, Map<String, Object> map, final HttpCallback callback) {
        /**
         * 创建请求的参数body
         */
        FormBody.Builder builder = new FormBody.Builder();
        /**
         * 遍历key
         */
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                L.e("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        L.e("post：" + url);
        onStart(callback);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String msg = response.body().string();
                    if (response.isSuccessful()) {
                        L.e(msg);
                        CommonResponse reponse = new Gson().fromJson(msg,CommonResponse.class);
                        if(reponse.getStatus() == 1){
                            onSuccess(callback,msg);
                        } else {
                            onError(callback,msg);
                        }
                    } else {
                        L.e("yichang"+msg);
                        throw new IOException("Unexpected code " + response);
                    }
                }catch (IllegalStateException e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * delete body方法
     * @param url
     * @param callback
     */
    public void delete(String url,String json, final HttpCallback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        L.e("delete方法url："+ url);
        onStart(callback);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String msg = response.body().string();
                    CommonResponse reponse = new Gson().fromJson(msg,CommonResponse.class);
                    if(reponse.getStatus() == 1){
                        onSuccess(callback,msg);
                    }else {
                        onError(callback,msg);
                    }
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * delete无参方法
     * @param url
     * @param callback
     */
    public void delete(String url,final HttpCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        L.e("delete方法url："+ url);
        onStart(callback);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String msg = response.body().string();
                    CommonResponse reponse = new Gson().fromJson(msg,CommonResponse.class);
                    if(reponse.getStatus() == 1){
                        onSuccess(callback,msg);
                    }else {
                        onError(callback,msg);
                    }
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * patch
     * @param url
     * @param callback
     */
    public void patch(String url, Map<String, Object> map,final HttpCallback callback){
        FormBody.Builder builder = new FormBody.Builder();
        /**
         * 遍历key
         */
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                L.e("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .build();
        L.e("patch："+ url);
        onStart(callback);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String msg = response.body().string();
                    CommonResponse reponse = new Gson().fromJson(msg,CommonResponse.class);
                    if(reponse.getStatus() == 1){
                        onSuccess(callback,msg);
                    }else {
                        onError(callback,msg);
                    }
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
    /**
     * patchJson，json数据为body
     * @param url
     * @param json
     * @param callback
     */
    public void patchJson(String url, String json, final HttpCallback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .build();
        L.e("postJson:" + url);
        onStart(callback);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String msg = response.body().string();
                    CommonResponse reponse = new Gson().fromJson(msg,CommonResponse.class);
                    if(reponse.getStatus() == 1){
                        onSuccess(callback,msg);
                    }else {
                        onError(callback,msg);
                    }
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }

    /**
     * log信息打印
     *
     * @param params
     */
    public void debug(String params) {
        if (false == isDebug) {
            return;
        }

        if (null == params) {
            L.e(TAG, "params is null");
        } else {
            L.e(TAG, params);
        }
    }

    private void onStart(HttpCallback callback) {
        if (null != callback) {
            callback.onStart();
        }
    }
    private void onSuccess(final HttpCallback callback, final String data) {
        debug(data);
        if (null != callback) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess(data);
                }
            });
        }
    }
    private void onError(final HttpCallback callback, final String msg) {
        if (null != callback) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onError(msg);
                }
            });
        }
    }
    private void onFailureResp(final HttpCallback callback, final String msg) {
        if (null != callback) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(msg);
                }
            });
        }
    }
    /**
     * http请求回调
     * @author dell
     */
    public static abstract class HttpCallback {
        public void onStart() {}
        public abstract void onSuccess(String data);
        public void onError(String msg) {}
        public void onFailure(String msg) {}
    }

    /**
     * cacle tag
     * @param tag
     */
    public void cancel(Object tag) {
        Dispatcher dispatcher = client.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}