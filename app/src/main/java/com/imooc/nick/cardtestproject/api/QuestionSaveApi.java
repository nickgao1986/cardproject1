package com.imooc.nick.cardtestproject.api;



import com.http.api.ApiUtil;
import com.imooc.nick.cardtestproject.CardContants;
import com.imooc.nick.cardtestproject.bean.RankInfo;

import org.json.JSONObject;

public class QuestionSaveApi extends ApiUtil {

    public RankInfo mRankInfo = new RankInfo();

    public QuestionSaveApi(String question_id, String answer){
        addParam("question_id",question_id);
        addParam("answer",answer);
    }

    @Override
    protected String getUrl() {
        return CardContants.URL+"/submit";
    }

    @Override
    protected void parseData(JSONObject jsonObject) throws Exception {
        try {
            JSONObject dataInfo = (JSONObject) jsonObject.get("data");
            JSONObject rankInfo = (JSONObject)dataInfo.get("rank_info");
            mRankInfo.is_correct = rankInfo.optString("is_correct");
            mRankInfo.total_count = rankInfo.optString("total_count");
            mRankInfo.correct_count = rankInfo.optString("correct_count");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadLocalData(JSONObject jsonObject) throws Exception {
        try {
            JSONObject dataInfo = (JSONObject) jsonObject.get("data");
            JSONObject rankInfo = (JSONObject)dataInfo.get("rank_info");
            mRankInfo.is_correct = rankInfo.optString("is_correct");
            mRankInfo.total_count = rankInfo.optString("total_count");
            mRankInfo.correct_count = rankInfo.optString("correct_count");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected boolean isBackInMainThread() {
        return true;
    }
}
