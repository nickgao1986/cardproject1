package com.imooc.nick.cardtestproject.db;


import com.imooc.nick.cardtestproject.bean.DaoSession;
import com.imooc.nick.cardtestproject.bean.QuestionInfo;
import com.imooc.nick.cardtestproject.bean.QuestionInfoDao;

import java.util.List;

public class QuestionDbController {

    private static QuestionDbController instance;
    private QuestionInfoDao dao;
    public static DaoSession daoSession;


    public static QuestionDbController getInstance() {
        if (instance == null) {
            instance = new QuestionDbController();
        }
        return instance;
    }

    public QuestionDbController() {
        initGreenDao();
    }


    private void initGreenDao() {
        daoSession = QuestionDaoOpenHelper.getDaoSession();
        dao = daoSession.getQuestionInfoDao();
    }

    /**
     * 数据新增或者更新
     *
     * @param bean
     */
    public long insert(QuestionInfo bean) {
        return dao.insertOrReplace(bean);
    }

    public void update(QuestionInfo bean) {
        dao.update(bean);
    }

    public void update(List<QuestionInfo> beanList) {
        dao.updateInTx(beanList);
    }

    public void delete(QuestionInfo bean) {
        dao.delete(bean);
    }


    public void delete(List<QuestionInfo> beanList) {
        dao.deleteInTx(beanList);
    }

    public void insertOrReplace(List<QuestionInfo> bean) {
        this.dao.insertOrReplaceInTx(bean);
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    public List<QuestionInfo> queryAll() {
       // return dao.queryRaw("", "");
        return dao.queryBuilder().list();
    }

    /**
     * 数据新增或者更新
     *
     * @param bean
     */
    public void insertAll(List<QuestionInfo> bean) {
        dao.insertOrReplaceInTx(bean);
    }
}
