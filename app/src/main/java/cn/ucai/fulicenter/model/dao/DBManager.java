package cn.ucai.fulicenter.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;

import cn.ucai.fulicenter.model.bean.User;

/**
 * Created by Administrator on 2017/1/17 0017.
 */

public class DBManager {
    private static DBOpenHelper helper;
    static DBManager manager = new DBManager();

    public DBManager() {

    }

    public static void onInit(Context context) {
        helper = new DBOpenHelper(context);
    }

    public synchronized static DBManager getInstance() {
        if (helper == null) {
        }
        return manager;
    }

    public boolean saveUser(User user) {
        SQLiteDatabase database = helper.getWritableDatabase();
        if (database.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(UserDao.USER_COLUMN_NAME, user.getMuserName());
            values.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
            values.put(UserDao.USER_COLUMN_AVATAR, user.getMavatarId());
            values.put(UserDao.USER_COLUMN_AVATAR_PATH, user.getMavatarPath());
            values.put(UserDao.USER_COLUMN_AVATAR_TYPE, user.getMavatarType());
            values.put(UserDao.USER_COLUMN_AVATAR_SUFFIX, user.getMavatarSuffix());
            values.put(UserDao.USER_COLUMN_AVATAR_UPDATE_TIME, user.getMavatarLastUpdateTime());
            return database.replace(UserDao.USER_TABLE_NAME, null, values) != -1;
        }
        return false;
    }

    public User getUser(String username) {
        SQLiteDatabase database = helper.getReadableDatabase();
        String sql = "select * from " + UserDao.USER_TABLE_NAME + " where " + UserDao.USER_COLUMN_NAME + " =?";
        User user = new User();
        if (database.isOpen()) {
            Cursor cursor = database.rawQuery(sql, new String[]{username});
            if (cursor.moveToNext()) {
                user.setMuserName(username);
                user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_NICK)));
                user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR)));
                user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH)));
                user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE)));
                user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX)));
                user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_UPDATE_TIME)));
            }

        }
        return user;
    }
}
