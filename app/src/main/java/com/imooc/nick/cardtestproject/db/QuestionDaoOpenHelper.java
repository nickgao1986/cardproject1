package com.imooc.nick.cardtestproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.imooc.nick.cardtestproject.CardApplication;
import com.imooc.nick.cardtestproject.bean.DaoMaster;
import com.imooc.nick.cardtestproject.bean.DaoSession;

import org.greenrobot.greendao.database.Database;

public class QuestionDaoOpenHelper extends DaoMaster.OpenHelper {

    private static DaoSession mDaoSession;

    public QuestionDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static void initDatabase() {
        QuestionDaoOpenHelper helper = new QuestionDaoOpenHelper(CardApplication.mContext, "question.db", null);
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        mDaoSession = daoMaster.newSession();
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if (oldVersion == newVersion) {
            return;
        }
        switch (oldVersion) {
            case 1:
                DaoMaster.dropAllTables(db, true);
                DaoMaster.createAllTables(db, false);
            default:
                break;

        }
    }

}
