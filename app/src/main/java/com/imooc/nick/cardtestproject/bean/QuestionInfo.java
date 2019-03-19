package com.imooc.nick.cardtestproject.bean;

import android.support.annotation.Keep;

import com.imooc.nick.cardtestproject.util.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Keep
@Entity(nameInDb = "QuestionBean")
public class QuestionInfo implements Serializable {

    public static final long serialVersionUID = 3243243243243L;

    @Id
    public String question_id;

    public String title;

    public String answer;

    @Convert(converter = StringConverter.class , columnType = String.class)
    public List<String> options = new ArrayList<String>();

    public int type;

    public String option;

    public String explain;

    /**
     * 0表示数据被删除了，1表示数据是正常的
     */
    public int status = 0;

    @Generated(hash = 63002511)
    public QuestionInfo(String question_id, String title, String answer,
            List<String> options, int type, String option, String explain,
            int status) {
        this.question_id = question_id;
        this.title = title;
        this.answer = answer;
        this.options = options;
        this.type = type;
        this.option = option;
        this.explain = explain;
        this.status = status;
    }

    @Generated(hash = 1065106606)
    public QuestionInfo() {
    }

    public String getQuestion_id() {
        return this.question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getOptions() {
        return this.options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOption() {
        return this.option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getExplain() {
        return this.explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
