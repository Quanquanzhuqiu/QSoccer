package com.qqzq.util;

import android.os.Environment;


public class Constants {
	public static final String API_SERVER_HOST = "http://121.43.229.24:8080/qqzq/rest";

	// 3rd part API APP ID
	public static final String WEIXIN_APP_ID = "XXXXX";
	public static final String WEIBO_APP_ID = "XXXXX";

	// connection configuration
	public static final int CONNECT_TIME_OUT = 40000;
	public static final int PAGE_SIZE = 16;

	public static final String SHARE_REFERENCE_NAME = "qqzq";

	//preference configuration
	// login user name
	public static final String PREF_USERNAME = "username";
	// login password
	public static final String PREF_PWD = "pwd";

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ API list ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// =============================== ע����֤���� ===============================
	//�û�ע��
	public static final String API_USER_REGISTER_URL = "/authc/register";
	//�û���¼
	public static final String API_USER_LOGIN_URL = "/authc/login";
	// �޸�����
	public static final String API_USER_EDIT_USER_URL = "/authc/pwd";
	//�û��˳�
	public static final String API_USER_LOGOUT_URL = "/authc/logout";
	
}
