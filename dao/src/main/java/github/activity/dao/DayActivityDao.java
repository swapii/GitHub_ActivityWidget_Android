package github.activity.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import github.activity.dao.DayActivity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table DAY_ACTIVITY.
*/
public class DayActivityDao extends AbstractDao<DayActivity, Void> {

    public static final String TABLENAME = "DAY_ACTIVITY";

    /**
     * Properties of entity DayActivity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property User = new Property(0, String.class, "user", false, "USER");
        public final static Property Date = new Property(1, java.util.Date.class, "date", false, "DATE");
        public final static Property Count = new Property(2, Integer.class, "count", false, "COUNT");
    };


    public DayActivityDao(DaoConfig config) {
        super(config);
    }
    
    public DayActivityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DAY_ACTIVITY' (" + //
                "'USER' TEXT," + // 0: user
                "'DATE' INTEGER," + // 1: date
                "'COUNT' INTEGER);"); // 2: count
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DAY_ACTIVITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DayActivity entity) {
        stmt.clearBindings();
 
        String user = entity.getUser();
        if (user != null) {
            stmt.bindString(1, user);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(2, date.getTime());
        }
 
        Integer count = entity.getCount();
        if (count != null) {
            stmt.bindLong(3, count);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public DayActivity readEntity(Cursor cursor, int offset) {
        DayActivity entity = new DayActivity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // user
            cursor.isNull(offset + 1) ? null : new java.util.Date(cursor.getLong(offset + 1)), // date
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2) // count
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DayActivity entity, int offset) {
        entity.setUser(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDate(cursor.isNull(offset + 1) ? null : new java.util.Date(cursor.getLong(offset + 1)));
        entity.setCount(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(DayActivity entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(DayActivity entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}