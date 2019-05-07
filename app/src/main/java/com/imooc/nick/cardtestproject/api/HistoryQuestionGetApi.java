package com.imooc.nick.cardtestproject.api;

import com.google.gson.Gson;
import com.http.api.ApiUtil;
import com.imooc.nick.cardtestproject.CardContants;
import com.imooc.nick.cardtestproject.bean.QuestionInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryQuestionGetApi extends ApiUtil{

    public List<QuestionInfo> list = new ArrayList<>();

    @Override
    protected String getUrl() {
        return CardContants.URL+"/history";
    }

    @Override
    protected void parseData(JSONObject jsonObject) {
        try{
            JSONObject dataInfo = jsonObject.optJSONObject("data");
            JSONArray array = (JSONArray)dataInfo.get("history_list");
            if(list != null) {
                list.clear();
            }

            for (int i=0;i<array.length();i++) {
                QuestionInfo questionInfo = new Gson().fromJson(array.get(i).toString(),QuestionInfo.class);
                list.add(questionInfo);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //todo
    public void loadlocalData(JSONObject jsonObject) {
        try{
            JSONObject dataInfo = jsonObject.optJSONObject("data");
            JSONArray array = (JSONArray)dataInfo.get("history_list");
            if(list != null) {
                list.clear();
            }

            for (int i=0;i<array.length();i++) {
                QuestionInfo questionInfo = new Gson().fromJson(array.get(i).toString(),QuestionInfo.class);
                list.add(questionInfo);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
