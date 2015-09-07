package com.qqzq.common;

import android.os.Environment;

import java.text.SimpleDateFormat;


public class Constants {

    // 3rd part API APP ID
    public static final String WEIXIN_APP_ID = "XXXXX";
    public static final String WEIBO_APP_ID = "XXXXX";

    public static final String FILE_SERVER_HOST = "http://121.43.229.24/";
    public static final String API_SERVER_HOST = "http://121.43.229.24:8080/qqzq/rest";
    public static final String IMAGE_PHOTO_TMP_PATH = Environment.getExternalStorageDirectory().getPath() + "/qqzq/tmp/";


    // connection configuration
    public static final int CONNECT_TIME_OUT = 40000;
    public static final int PAGE_SIZE = 6;

    public static final String GSON_REQUST_POST_PARAM_KEY = "json";

    public static final String SHARE_REFERENCE_NAME = "qqzq";

    //preference configuration
    // login user name
    public static final String PREF_USERNAME = "username";
    // login password
    public static final String PREF_PWD = "pwd";

    public static final String LOGO_CONTENT_TYPE = "image/jpeg";

    //=================================== API list ===================================

    // ~~~~~~~~~~~~~~~~~~~~~~~~~ 注册认证部分 ~~~~~~~~~~~~~~~~~~~~~~~~~
    //用户注册
    public static final String API_USER_REGISTER_URL = API_SERVER_HOST + "/authc/register";
    //用户登录
    public static final String API_USER_LOGIN_URL = API_SERVER_HOST + "/authc/login";
    // 修改密码
    public static final String API_USER_EDIT_USER_URL = API_SERVER_HOST + "/authc/pwd";
    //用户退出
    public static final String API_USER_LOGOUT_URL = API_SERVER_HOST + "/authc/logout";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~ 注册球队部分 ~~~~~~~~~~~~~~~~~~~~~~~~~
    //创建球队
    public static final String API_CREATE_TEAM_URL = API_SERVER_HOST + "/team/teams";
    //根据条件查询球队信息
    public static final String API_FIND_TEAM_URL = API_SERVER_HOST + "/team/teams";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~ 上传文件部分 ~~~~~~~~~~~~~~~~~~~~~~~~~
    //上传文件到分布式文件系统
    public static final String API_FILE_UPLOAD_FASTDFS_URL = API_SERVER_HOST + "/file/fastdfs";

}
