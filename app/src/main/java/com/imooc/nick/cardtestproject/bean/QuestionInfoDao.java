package com.imooc.nick.cardtestproject.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.imooc.nick.cardtestproject.util.StringConverter;
import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "QuestionBean".
*/
public class QuestionInfoDao extends AbstractDao<QuestionInfo, String> {

    public static final String TABLENAME = "QuestionBean";

    /**
     * Properties of entity QuestionInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Question_id = new Property(0, String.class, "question_id", true, "QUESTION_ID");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Answer = new Property(2, String.class, "answer", false, "ANSWER");
        public final static Property Options = new Property(3, String.class, "options", false, "OPTIONS");
        public final static Property Type = new Property(4, int.class, "type", false, "TYPE");
        public final static Property Option = new Property(5, String.class, "option", false, "OPTION");
        public final static Property Explain = new Property(6, String.class, "explain", false, "EXPLAIN");
        public final static Property Status = new Property(7, int.class, "status", false, "STATUS");
    }

    private final StringConverter optionsConverter = new StringConverter();

    public QuestionInfoDao(DaoConfig config) {
        super(config);
    }
    
    public QuestionInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"QuestionBean\" (" + //
                "\"QUESTION_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: question_id
                "\"TITLE\" TEXT," + // 1: title
                "\"ANSWER\" TEXT," + // 2: answer
                "\"OPTIONS\" TEXT," + // 3: options
                "\"TYPE\" INTEGER NOT NULL ," + // 4: type
                "\"OPTION\" TEXT," + // 5: option
                "\"EXPLAIN\" TEXT," + // 6: explain
                "\"STATUS\" INTEGER NOT NULL );"); // 7: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"QuestionBean\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, QuestionInfo entity) {
        stmt.clearBindings();
 
        String question_id = entity.getQuestion_id();
        if (question_id != null) {
            stmt.bindString(1, question_id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String answer = entity.getAnswer();
        if (answer != null) {
            stmt.bindString(3, answer);
        }
 
        List options = entity.getOptions();
        if (options != null) {
            stmt.bindString(4, optionsConverter.convertToDatabaseValue(options));
        }
        stmt.bindLong(5, entity.getType());
 
        String option = entity.getOption();
        if (option != null) {
            stmt.bindString(6, option);
        }
 
        String explain = entity.getExplain();
        if (explain != null) {
            stmt.bindString(7, explain);
        }
        stmt.bindLong(8, entity.getStatus());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, QuestionInfo entity) {
        stmt.clearBindings();
 
        String question_id = entity.getQuestion_id();
        if (question_id != null) {
            stmt.bindString(1, question_id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String answer = entity.getAnswer();
        if (answer != null) {
            stmt.bindString(3, answer);
        }
 
        List options = entity.getOptions();
        if (options != null) {
            stmt.bindString(4, optionsConverter.convertToDatabaseValue(options));
        }
        stmt.bindLong(5, entity.getType());
 
        String option = entity.getOption();
        if (option != null) {
            stmt.bindString(6, option);
        }
 
        String explain = entity.getExplain();
        if (explain != null) {
            stmt.bindString(7, explain);
        }
        stmt.bindLong(8, entity.getStatus());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public QuestionInfo readEntity(Cursor cursor, int offset) {
        QuestionInfo entity = new QuestionInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // question_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // answer
            cursor.isNull(offset + 3) ? null : optionsConverter.convertToEntityProperty(cursor.getString(offset + 3)), // options
            cursor.getInt(offset + 4), // type
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // option
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // explain
            cursor.getInt(offset + 7) // status
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, QuestionInfo entity, int offset) {
        entity.setQuestion_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAnswer(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setOptions(cursor.isNull(offset + 3) ? null : optionsConverter.convertToEntityProperty(cursor.getString(offset + 3)));
        entity.setType(cursor.getInt(offset + 4));
        entity.setOption(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setExplain(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setStatus(cursor.getInt(offset + 7));
     }
    
    @Override
    protected final String updateKeyAfterInsert(QuestionInfo entity, long rowId) {
        return entity.getQuestion_id();
    }
    
    @Override
    public String getKey(QuestionInfo entity) {
        if(entity != null) {
            return entity.getQuestion_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(QuestionInfo entity) {
        return entity.getQuestion_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
