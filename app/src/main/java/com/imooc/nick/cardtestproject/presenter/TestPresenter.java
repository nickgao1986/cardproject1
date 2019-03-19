package com.imooc.nick.cardtestproject.presenter;

import android.content.Context;

import com.http.Util.Util;
import com.http.api.ApiListener;
import com.http.api.ApiUtil;
import com.imooc.nick.cardtestproject.api.GetQuestionInfoApi;
import com.imooc.nick.cardtestproject.api.HistoryQuestionGetApi;
import com.imooc.nick.cardtestproject.bean.QuestionInfo;
import com.imooc.nick.cardtestproject.db.QuestionDbController;
import com.imooc.nick.cardtestproject.view.ITestView;

import java.util.ArrayList;
import java.util.List;

public class TestPresenter {

    private ITestView mITestView;
    private Context mContext;
    private List<QuestionInfo> mHistoryList;
    private QuestionInfo mCurrentInfo;

    public TestPresenter(ITestView iTestView) {
        mContext = (Context)iTestView;
        mITestView = iTestView;
    }

    public void getData() {
        if(Util.hasNetwork(mContext)) {
            getHistory();
        }else{
            refreshData();
        }

    }

    private void getHistory(){
        new HistoryQuestionGetApi().get(mContext, new ApiListener() {
            @Override
            public void success(ApiUtil api) {
                HistoryQuestionGetApi apiBase = (HistoryQuestionGetApi)api;
                mHistoryList = apiBase.list;
                getCurrentQuestionApi();
            }

            @Override
            public void failure(ApiUtil api) {

            }
        });
    }

    private void getCurrentQuestionApi() {
        new GetQuestionInfoApi().get(mContext, new ApiListener() {
            @Override
            public void success(ApiUtil api) {
                GetQuestionInfoApi apiBase = (GetQuestionInfoApi)api;
                mCurrentInfo = apiBase.mInfo;
                mHistoryList.add(0,mCurrentInfo);
                handleDataBase();
                refreshData();
            }

            @Override
            public void failure(ApiUtil api) {

            }
        });
    }


    private void refreshData() {
        mHistoryList = QuestionDbController.getInstance().queryAll();
        if(mITestView != null) {
            mITestView.updateUI(mHistoryList);
        }
    }

    private void handleDataBase() {
        List<QuestionInfo> deleteList = new ArrayList<>();
        ArrayList<QuestionInfo> paperList = new ArrayList();

        for (int i=0;i<mHistoryList.size();i++) {
            QuestionInfo info = mHistoryList.get(i);
            int status = info.status;
            if(status == -1) {
                deleteList.add(info);
            }else if(status == 0){
                paperList.add(info);
            }
        }


        QuestionDbController.getInstance().delete(deleteList);
        QuestionDbController.getInstance().insertOrReplace(paperList);
    }
}
