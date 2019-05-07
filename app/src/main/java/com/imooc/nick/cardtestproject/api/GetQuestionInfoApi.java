package com.imooc.nick.cardtestproject.api;

import com.google.gson.Gson;
import com.http.api.ApiUtil;
import com.imooc.nick.cardtestproject.CardContants;
import com.imooc.nick.cardtestproject.bean.QuestionInfo;

import org.json.JSONObject;

public class GetQuestionInfoApi extends ApiUtil {

    public QuestionInfo mInfo;
    @Override
    protected String getUrl() {
        return CardContants.URL+"/getQuestion";
    }

    @Override
    protected void parseData(JSONObject jsonObject) throws Exception {
        try {
            JSONObject data =  jsonObject.optJSONObject("data");
            JSONObject info =  data.optJSONObject("info");

            mInfo = new Gson().fromJson(info.toString(),QuestionInfo.class);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //todo
    public void loadlocalData(JSONObject jsonObject) {
        try {
            JSONObject data =  jsonObject.optJSONObject("data");
            JSONObject info =  data.optJSONObject("info");

            mInfo = new Gson().fromJson(info.toString(),QuestionInfo.class);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
