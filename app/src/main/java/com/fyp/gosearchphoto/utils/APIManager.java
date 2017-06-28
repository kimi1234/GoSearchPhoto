package com.fyp.gosearchphoto.utils;

import android.util.Log;

/**
 * Created by anamay on 6/14/17.
 */

public class APIManager {
    final static String rootStringUrl="";
    public static String getRegisterAPI(String username, String email, String password) {
        String registerAPI =rootStringUrl+ "/register.php?username="+username+"&password="+password+"&email="+email;
        Log.i("registerAPI",registerAPI);
        return registerAPI;
    }

    public static String getLogInAPI(String username,  String password) {
        String logInAPI =rootStringUrl+ "/login.php?username="+username+"&password="+password;
        Log.i("logInAPI",logInAPI);
        return logInAPI;
    }

    public static String getUpdateProfileAPI(String username, String email, int userid) {
        String updateProfileAPI =rootStringUrl+ "/updateprofile.php?username="+username+"&email="+email+"&user_id="+userid;
        Log.i("updateProfileAPI",updateProfileAPI);
        return updateProfileAPI;

    }

    public static String getChangePasswordAPI(int userid, String newpassword) {
        String changePassAPI =rootStringUrl+ "/changepassword.php?user_id="+userid+"&newpassword="+newpassword;
        Log.i("changePassAPI",changePassAPI);
        return changePassAPI;


    }
}
