package com.imooc.nick.cardtestproject.view;

import com.imooc.nick.cardtestproject.bean.QuestionInfo;

import java.util.List;

public interface ITestView {

    void updateUI(List<QuestionInfo> list);
    void setBottomTipView(String count);
}
