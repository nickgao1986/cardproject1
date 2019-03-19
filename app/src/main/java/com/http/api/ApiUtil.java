package com.http.api;

import android.content.Context;

import com.http.Util.Util;
import com.http.response.OkHttpCallback;

import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import okhttp3.Call;



public abstract class ApiUtil {

    /**
     * 状态码
     */
    private String mStatus = "";

    /**
     * 上下文
     */
    private Context mContext = null;

    /**
     * API发送监听
     */
    private ApiListener mApiListener = null;



    /**
     * 发送监听
     */
    private OkHttpCallback mSendListener = new OkHttpCallback() {

        @Override
        protected boolean isPostMainThread() {
            return isBackInMainThread();
        }

        @Override
        public void onSuccess(Call call, JSONObject response) {
            if (null != response) {
                mStatus = response.optString("status");

                if (isSuccess()) {
                    try {
                        parseData(response);
                        if (null != mApiListener) {
                            mApiListener.success(ApiUtil.this);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    if (null != mApiListener) {
                        mApiListener.failure(ApiUtil.this);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call call, IOException e) {
            if (null != mApiListener) {
                mApiListener.failure(ApiUtil.this);
            }
        }

    };

    protected boolean isBackInMainThread() {
        return true;
    }

    /**
     * http get
     *
     * @param context           ：上下文
     * @param listener          ：发送回调
     */
    public void get(Context context, ApiListener listener) {
        mContext = context;
        mApiListener = listener;
        if (Util.hasNetwork(mContext)) {
            OkHttpUtil.get(getUrl(), mSendListener);
        }
    }

    private HashMap<String,String> mBodyMap = new HashMap<>();

    public void post(ApiListener listener) {
        mApiListener = listener;
        OkHttpUtil.post(getUrl(), mSendListener,mBodyMap);

    }

    public void put(ApiListener listener) {
        mApiListener = listener;
        OkHttpUtil.put(getUrl(), mSendListener,mBodyMap);

    }

    public void delete(ApiListener listener) {
        mApiListener = listener;
        OkHttpUtil.delete(getUrl(), mSendListener,mBodyMap);

    }

    /**
     * 添加参数
     *
     * @param key
     * @param value
     */
    public void addParam(String key, String value) {
        mBodyMap.put(key, value);
    }

    public boolean isSuccess() {
        return ("0".equals(mStatus)) || ("200".equals(mStatus) || ("success".equals(mStatus)));
    }



    /**
     * 获取url
     * @return：http链接url
     */
    protected abstract String getUrl();


    /**
     * 解析数据
     *
     * @param jsonObject
     */
    protected abstract void parseData(JSONObject jsonObject) throws Exception;


}