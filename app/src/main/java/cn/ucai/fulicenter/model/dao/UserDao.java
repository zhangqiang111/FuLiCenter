package cn.ucai.fulicenter.model.dao;

import cn.ucai.fulicenter.application.FuliCenterApplication;
import cn.ucai.fulicenter.model.bean.User;

/**
 * Created by Administrator on 2017/1/17 0017.
 */

public class UserDao {
    public static final String USER_TABLE_NAME = "t_fulicenter_user";
    public static final String USER_COLUMN_NAME = "m_user_name";
    public static final String USER_COLUMN_NICK = "m_user_nick";
    public static final String USER_COLUMN_AVATAR = "m_user_avatar";
    public static final String USER_COLUMN_AVATAR_PATH = "m_user_avatar_path";
    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_user_avatar_suffix";
    public static final String USER_COLUMN_AVATAR_TYPE = "m_user_avatar_type";
    public static final String USER_COLUMN_AVATAR_UPDATE_TIME = "m_user_updata_time";

    private static UserDao instance;

    public UserDao() {
        DBManager.onInit(FuliCenterApplication.getInstance());
    }
    public static UserDao getInstance(){
        if (instance==null){
            instance = new UserDao();
        }
        return instance;
    }
    public boolean saveUser(User user){
        return DBManager.getInstance().saveUser(user);
    }
}
