package com.imooc.nick.cardtestproject.presenter;

import android.content.Context;

import com.http.Util.Util;
import com.imooc.nick.cardtestproject.api.GetQuestionInfoApi;
import com.imooc.nick.cardtestproject.api.HistoryQuestionGetApi;
import com.imooc.nick.cardtestproject.bean.QuestionInfo;
import com.imooc.nick.cardtestproject.db.QuestionDbController;
import com.imooc.nick.cardtestproject.view.ITestView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
//        new HistoryQuestionGetApi().get(mContext, new ApiListener() {
//            @Override
//            public void success(ApiUtil api) {
//                HistoryQuestionGetApi apiBase = (HistoryQuestionGetApi)api;
//                mHistoryList = apiBase.list;
//                getCurrentQuestionApi();
//            }
//
//            @Override
//            public void failure(ApiUtil api) {
//
//            }
//        });

        //todo
        String history = getFromAssets("history.json", mContext);
        try{
            JSONObject object = new JSONObject(history);
            HistoryQuestionGetApi api = new HistoryQuestionGetApi();
            api.loadlocalData(object);
            mHistoryList = api.list;
            getCurrentQuestionApi();
        }catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    /**
     * 获取本地的数据
     * @param fileName
     * @param context
     * @return
     */
    public String getFromAssets(String fileName,final Context context){
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getCurrentQuestionApi() {
//        new GetQuestionInfoApi().get(mContext, new ApiListener() {
//            @Override
//            public void success(ApiUtil api) {
//                GetQuestionInfoApi apiBase = (GetQuestionInfoApi)api;
//                mCurrentInfo = apiBase.mInfo;
//                mHistoryList.add(0,mCurrentInfo);
//                handleDataBase();
//                refreshData();
//            }
//
//            @Override
//            public void failure(ApiUtil api) {
//
//            }
//        });
        //todo
        String question = getFromAssets("question.json", mContext);
        try{
            JSONObject object = new JSONObject(question);
            GetQuestionInfoApi api = new GetQuestionInfoApi();
            api.loadlocalData(object);
            mCurrentInfo = api.mInfo;
            mHistoryList.add(0,mCurrentInfo);
            handleDataBase();
            refreshData();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
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
            if(status == 0) {
                deleteList.add(info);
            }else if(status == 1){
                paperList.add(info);
            }
        }


        QuestionDbController.getInstance().delete(deleteList);
        QuestionDbController.getInstance().insertOrReplace(paperList);
    }
}
