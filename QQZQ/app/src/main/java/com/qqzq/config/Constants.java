package com.qqzq.config;

import android.os.Environment;


public class Constants {

    // 3rd part API APP ID
    public static final String APP_NAME = "QQZQ";
    public static final String WEIXIN_APP_ID = "QQZQ";
    public static final String WEIBO_APP_ID = "XXXXX";
    public static final String MOD_APP_KEY = "aa0a1eb04fd2";
    public static final String MOD_APP_SECRET = "3b4e06fefdf9eb390dcbd20135421851";

    /**
     * 中国手机区号.
     */
    public static final String CHINA_MOBLIE_NO = "86";


    // connection configuration
    public static final int CONNECT_TIME_OUT = 40000;
    public static final int PAGE_SIZE = 4;
    public static final int UNLIMITED_PAGE_SIZE = 1000;

    public static final String GSON_REQUST_POST_PARAM_KEY = "json";
    public static final String SHARE_REFERENCE_NAME = "qqzq";



    //preference configuration
    public static final String PREF_APP = "PREF_QQZQ";
    // login user name
    public static final String PREF_USERNAME = "username";
    // login password
    public static final String PREF_PWD = "pwd";


    //extra configuration
    public static final String EXTRA_SELECTED_LOCATION = "EXTRA_SELECTED_LOCATION";
    public static final String EXTRA_SELECTED_PROVINCE_CODE = "EXTRA_SELECTED_PROVINCE_CODE";
    public static final String EXTRA_SELECTED_CITY_CODE = "EXTRA_SELECTED_CITY_CODE";
    public static final String EXTRA_SELECTED_TEAM = "EXTRA_SELECTED_TEAM";
    public static final String EXTRA_SELECTED_TEAM_ID = "EXTRA_SELECTED_TEAM_ID";
    public static final String EXTRA_SELECTED_TEAM_RULE = "EXTRA_SELECTED_TEAM_RULE";
    public static final String EXTRA_SELECTED_TEAM_NAME = "EXTRA_SELECTED_TEAM_NAME";
    public static final String EXTRA_SELECTED_TEAM_BALANCE = "EXTRA_SELECTED_TEAM_BALANCE";
    public static final String EXTRA_SELECTED_TEAM_BALANCE_DEADLINE = "EXTRA_SELECTED_TEAM_BALANCE_DEADLINE";
    public static final String EXTRA_EDITED_MEMBER_FEE = "EXTRA_EDITED_MEMBER_FEE";
    public static final String EXTRA_PAGE_TYPE = "EXTRA_PAGE_TYPE";
    public static final String EXTRA_PREV_PAGE_NAME = "EXTRA_PREV_PAGE_NAME";
    public static final String EXTRA_SELECTED_GAME_ID = "EXTRA_SELECTED_GAME_ID";
    public static final String EXTRA_SELECTED_GAME_NAME = "EXTRA_SELECTED_GAME_NAME";
    public static final String EXTRA_SELECTED_GAME_DATE = "EXTRA_SELECTED_GAME_DATE";
    public static final String EXTRA_SELECTED_GENDER = "EXTRA_SELECTED_GENDER";
    public static final String EXTRA_SELECTED_GENDER_ID = "EXTRA_SELECTED_GENDER_ID";
    public static final String EXTRA_USER_INFO = "EXTRA_USER_INFO";

    public static final String HTTP_HEADER_TOKER = "Token";


    public static final String PAGE_TYPE_NO_TEAM = "no_team";
    public static final String PAGE_TYPE_HAVE_TEAM = "have_team";
    public static final String PAGE_TYPE_NO_GAME = "no_game";
    public static final String PAGE_TYPE_HAVE_GAME = "have_game";

    //网络状况
    public static final String NET_GOOD = "网络状况良好";
    public static final String NET_BAD = "网络状况不佳，请检查网络连接!";
    public static final String FIAL_GET_INFO = "获取信息失败，请检查网络连接";

    //=================================== 后台API调用列表 ===================================
    public static final String FILE_SERVER_HOST = "http://121.43.229.24/";
    public static final String API_SERVER_HOST = "http://121.43.229.24:8080/qqzq/rest";
    public static final String IMAGE_PHOTO_TMP_PATH = Environment.getExternalStorageDirectory().getPath() + "/qqzq/tmp/";
    public static final String DB_PATH = Environment.getExternalStorageDirectory().getPath() + "/qqzq/db/";

    public static final String DB_NAME = "qqzq";


    // ~~~~~~~~~~~~~~~~~~~~~~~~~ 注册认证部分 ~~~~~~~~~~~~~~~~~~~~~~~~~
    //用户注册
    public static final String API_USER_REGISTER_URL = API_SERVER_HOST + "/authc/register";
    //用户登录
    public static final String API_USER_LOGIN_URL = API_SERVER_HOST + "/authc/login";
    // 修改密码
    public static final String API_USER_MODIFY_PASSWORD_URL = API_SERVER_HOST + "/authc/pwdmodification";
    //用户退出
    public static final String API_USER_LOGOUT_URL = API_SERVER_HOST + "/authc/logout";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~ 地区选择部分 ~~~~~~~~~~~~~~~~~~~~~~~~~
    public static final String API_FIND_NATIONS_URL = API_SERVER_HOST + "/system/nations";
    public static final String API_FIND_PROVINCE_URL = API_SERVER_HOST + "/system/provinces";
    public static final String API_FIND_CITY_URL = API_SERVER_HOST + "/system/citys";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~ 球队部分 ~~~~~~~~~~~~~~~~~~~~~~~~~
    //创建球队
    public static final String API_CREATE_TEAM_URL = API_SERVER_HOST + "/team/teams";
    //根据条件查询球队信息
    public static final String API_FIND_TEAM_URL = API_SERVER_HOST + "/team/teams";
    //根据球队id获取球队详细信息
    public static final String API_FIND_TEAM_BY_ID_URL = API_SERVER_HOST + "/team/teams/{0}";
    //查询用户所在球队
    public static final String API_FIND_TEAM_MEMBER_BY_ID_URL = API_SERVER_HOST + "/team/teams/members/{0}";
    //哈讯球队成员列表
    public static final String API_FIND_TEAM_MEMBER_DETAIL_BY_ID_URL = API_SERVER_HOST + "/team/teams/{0}/members";
    //当用户申请加入球队时调用此接口
    public static final String API_JOIN_TEAM_URL = API_SERVER_HOST + "/team/teams/{0}/application";
    //创建球队章程
    public static final String API_CREATE_TEAM_RULE_URL = API_SERVER_HOST + "/team/teams/{0}/rule";
    //录入球队余额
    public static final String API_ADD_INCOME_SPONSOR_URL = API_SERVER_HOST + "/team/teams/{0}/teambalance";
    //录入球队队员余额
    public static final String API_ADD_INCOME_MEMBER_URL = API_SERVER_HOST + "/team/teams/{0}/memberbalance";
    //扣除球队余额
    public static final String API_PAY_USE_TEAM_COMMON_FEE_URL = API_SERVER_HOST + "/team/teams/{0}/teambalancededuction";
    //扣除球队队员余额
    public static final String API_PAY_USE_TEAM_MEMBER_FEE_URL = API_SERVER_HOST + "/team/teams/{0}/memberbalancededuction";
    // ~~~~~~~~~~~~~~~~~~~~~~~~~ 活动部分 ~~~~~~~~~~~~~~~~~~~~~~~~~
    //创建球队
    public static final String API_GAME_PUBLISH_URL = API_SERVER_HOST + "/activity/activities";
    //根据条件查询球队信息
    public static final String API_FIND_GAME_URL = API_SERVER_HOST + "/activity/activities";
    //根据用户id获取活动详细信息
    public static final String API_FIND_GAME_BY_ID_URL = API_SERVER_HOST + "/activity/activities/{0}";
    //用户参加活动报名
    public static final String API_GAME_JOIN_APPLICATION_URL = API_SERVER_HOST + "/activity/activities/{0}/application";
    //用户取消活动
    public static final String API_GAME_CANCEL_APPLICATION_URL = API_SERVER_HOST + "/activity/activities/{0}/manalcancel";
    //获取活动报名名单
    public static final String API_GAME_SIGNUP_LIST_URL = API_SERVER_HOST + "/activity/activities/{0}/signup_list";
    //记录出勤情况
    public static final String API_ATTENDANCE_URL = API_SERVER_HOST + "/activity/activities/{0}/attendance";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~ 上传文件部分 ~~~~~~~~~~~~~~~~~~~~~~~~~
    //上传文件到分布式文件系统
    public static final String API_FILE_UPLOAD_FASTDFS_URL = API_SERVER_HOST + "/file/fastdfs";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~ 个人资料设置部分 ~~~~~~~~~~~~~~~~~~~~~~~~~
    //创建球队
    public static final String API_ME_SETTING_URL = API_SERVER_HOST + "/user/users";
    //根据用户ID获取用户信息
    public static final String API_GET_ME_INFO_URL = API_SERVER_HOST + "/user/userinfos";
}
