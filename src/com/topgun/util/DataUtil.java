package com.topgun.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSONObject;
import com.topgun.entity.User;

/**
 * 数据处理Util
 * 
 * @author liushunxin
 *
 */
public class DataUtil {

	public static final String TAG = "DataUtil";

	public static final String SHAREPREFERENCE_USER_INFO = "user_info";
	
	public static final String EDITOR_USER_USERNAME = "userUsername";
	public static final String EDITOR_USER_PWD = "userPwd";
	public static final String EDITOR_USER_IS_PWD = "userSavePwd";
	public static String APPLICATION_ISSUE_DATA = "issueData";
	public static String EDITOR_USER_ID = "userId";
	public static String EDITOR_USER_NAME = "userName";
	public static String EDITOR_USER_EMAIL = "userEmail";

	/**
	 * 保存登录信息到本地
	 * 
	 * @param context
	 * @param user
	 * @param isLogin
	 */
	public static void saveUserLoginInfo(Context context,User user,boolean isLogin){
		if(user == null) return;
		SharedPreferences sharedPre = context.getSharedPreferences(
				SHAREPREFERENCE_USER_INFO, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPre.edit();
		editor.putString(EDITOR_USER_USERNAME, user.getUsername());
		if (isLogin && isIsPwd(context)) {
			editor.putString(EDITOR_USER_PWD,user.getPwd());
		}else {
			editor.putString(EDITOR_USER_PWD,null);			
		}

		if (user.getId() != null) {
			editor.putString(EDITOR_USER_ID, user.getId());
		}

		editor.putString(EDITOR_USER_NAME, user.getName());

		editor.putString(EDITOR_USER_EMAIL, user.getEmail());
		
		UIUtil.showLogI(TAG, "user", user);

		editor.commit();
	}

	
	public static void cleanUserInfo(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				SHAREPREFERENCE_USER_INFO, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPre.edit();
		editor.clear();
		editor.commit();
	}
	
	/**
	 * 从本地获取登录信息
	 * 
	 * @param context
	 * @return
	 */
	public static User getUserLoginInfo(Context context){
		User user;
		SharedPreferences sharedPre = context.getSharedPreferences(
				SHAREPREFERENCE_USER_INFO, Activity.MODE_PRIVATE);
		String username = sharedPre.getString(EDITOR_USER_USERNAME,null);
		String pwd = sharedPre.getString(EDITOR_USER_PWD,null);

		String id = sharedPre.getString(EDITOR_USER_ID,null);
		String name = sharedPre.getString(EDITOR_USER_NAME,null);
		String email = sharedPre.getString(EDITOR_USER_EMAIL,null);

		if (!isIsPwd(context)) {
			pwd = null;
		}
		user = new User(username, pwd, id, name, email);
		UIUtil.showLogI(TAG, "user", user);
		return user;
	}

	/**
	 * 是否保存密码到本地
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isIsPwd(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				SHAREPREFERENCE_USER_INFO, Activity.MODE_PRIVATE);
		boolean isSavePed = sharedPre.getBoolean(EDITOR_USER_IS_PWD,false);
		return isSavePed;
	}

	/**
	 * 获取是否从本地保存密码的信息
	 * 
	 * @param context
	 * @param isPwd
	 */
	public static void saveIsPwd(Context context,boolean isPwd){
		SharedPreferences sharedPre = context.getSharedPreferences(
				SHAREPREFERENCE_USER_INFO, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPre.edit();
		editor.putBoolean(EDITOR_USER_IS_PWD,isPwd);
		editor.commit();
	}

	public static void getDataCheck(){

	}

	public static void saveIssue(Context context,JSONObject obj){
		if (obj == null) return;
		SharedPreferences sharedPre = context.getSharedPreferences(
				SHAREPREFERENCE_USER_INFO, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPre.edit();
		editor.putString(APPLICATION_ISSUE_DATA,obj.toString());
		editor.commit();
	}

	public static JSONObject getIssue(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				SHAREPREFERENCE_USER_INFO, Activity.MODE_PRIVATE);
		String s = sharedPre.getString(APPLICATION_ISSUE_DATA, null);
		JSONObject object = JSONObject.parseObject(s);
		return object;
	}

}
