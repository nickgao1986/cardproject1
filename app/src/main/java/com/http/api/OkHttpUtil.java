package com.http.api;

import com.http.response.OkHttpCallback;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpUtil {

    private static OkHttpClient mOkHttpClient = null;
    private enum REQUEST_TYPE{
        POST,PUT,DELETE
    }
    public static void init() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS);
            mOkHttpClient = clientBuilder.build();
        }
    }


    public static void get(String url, OkHttpCallback okHttpCallback) {
        Call call = null;
        try {
            HashMap<String,String> commonHap = ApiCommonParams.fetchCommonsParams();
            url = getFinalUrl(url,commonHap);
            Request request =  new Request.Builder().url(url).build();
            call = mOkHttpClient.newCall(request);
            call.enqueue(okHttpCallback);
        } catch (Throwable e) {
            e.printStackTrace();
            okHttpCallback.onFailure(call, new IOException("get", e));
        }
    }

    public static String getFinalUrl(String url, HashMap<String,String> urlParamsMap) {
        if (urlParamsMap != null) {
            String paramString = getUrlAppendStr(urlParamsMap);
            url += url.contains("?") ? "&" : "?";
            url += paramString;
        }
        return url;
    }

    private static String getUrlAppendStr(HashMap<String,String> urlParamsMap) {
        StringBuilder result = new StringBuilder();
        for (HashMap.Entry<String, String> entry : urlParamsMap.entrySet()) {
            try {
                result.append(entry.getKey());
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (Throwable e) {
                return "";
            }
        }
        return result.toString();
    }


    private static RequestBody createEncodingBuilderBody(HashMap<String,String> bodyMap) {
        FormBody.Builder builder = new FormBody.Builder();

        for (HashMap.Entry<String, String> entry : bodyMap.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    public static void sendRequestWithBody(String url, OkHttpCallback okHttpCallback,HashMap<String,String> bodyMap,
                                           REQUEST_TYPE type) {

        Call call = null;
        try {
            RequestBody body = createEncodingBuilderBody(bodyMap);
            Request request = null;
            if(type == REQUEST_TYPE.POST) {
                request =  new Request.Builder().post(body).url(url).build();
            }else if(type == REQUEST_TYPE.PUT) {
                request =  new Request.Builder().put(body).url(url).build();
            }else if(type == REQUEST_TYPE.DELETE) {
                request =  new Request.Builder().delete(body).url(url).build();
            }
            call = mOkHttpClient.newCall(request);
            call.enqueue(okHttpCallback);
        } catch (Throwable e) {
            e.printStackTrace();
            okHttpCallback.onFailure(call, new IOException("get", e));
        }

    }


    public static void post(String url, OkHttpCallback okHttpCallback,HashMap<String,String> bodyMap) {
        Call call = null;
        try {
            RequestBody body = createEncodingBuilderBody(bodyMap);
            Request request =  new Request.Builder().post(body).url(url).build();
            call = mOkHttpClient.newCall(request);
            call.enqueue(okHttpCallback);
        } catch (Throwable e) {
            e.printStackTrace();
            okHttpCallback.onFailure(call, new IOException("get", e));
        }
    }


    public static void put(String url, OkHttpCallback okHttpCallback,HashMap<String,String> bodyMap) {
        Call call = null;
        try {
            RequestBody body = createEncodingBuilderBody(bodyMap);
            Request request =  new Request.Builder().put(body).url(url).build();

            call = mOkHttpClient.newCall(request);
            call.enqueue(okHttpCallback);
        } catch (Throwable e) {
            e.printStackTrace();
            okHttpCallback.onFailure(call, new IOException("put", e));
        }
    }


    public static void delete(String url, OkHttpCallback okHttpCallback,HashMap<String,String> bodyMap) {
        Call call = null;
        try {
            RequestBody body = createEncodingBuilderBody(bodyMap);
            Request request =  new Request.Builder().delete(body).url(url).build();

            call = mOkHttpClient.newCall(request);
            call.enqueue(okHttpCallback);
        } catch (Throwable e) {
            e.printStackTrace();
            okHttpCallback.onFailure(call, new IOException("delete", e));
        }
    }

}
