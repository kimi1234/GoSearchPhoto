package com.fyp.gosearchphoto.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;

/**
 * Created by anamay on 6/14/17.
 */

public class APIManager {
    final static String rootStringUrl = "http://www.candy-loop.com/api";

    //  #1
    public static void checkUserExist(Context cont, String email) {
        // String checkUserAPI = rootStringUrl + "/checkUserExist.php?email=" + email;

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);
        String checkUserAPI = rootStringUrl + "/checkUserExist.php";

        Log.i("checkUserAPI", checkUserAPI);

        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_CHECKUSER_EXIST);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_CHECKUSER_EXIST);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(checkUserAPI);
            requestPackage.setParam("email", email);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(checkUserAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    //  #2.1
    public static void checkCompanyExist(Context cont, String companyName) {
      //  String checkCompanyAPI = rootStringUrl + "/checkCompanyExist.php?companyName=" + companyName;

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);
        String checkCompanyAPI = rootStringUrl + "/checkCompanyExist.php";

        Log.i("checkUserAPI", checkCompanyAPI);

        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_CHECKCOMPANY_EXIST);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_CHECKCOMPANY_EXIST);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(checkCompanyAPI);
            requestPackage.setParam("companyName", companyName);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(checkCompanyAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }


    // #2.2
    public static void getRegisterAPI(Context cont, String user_type, String fullname, String email, String password, String company_name, String industry, String desc) {
        String registerAPIInitial = rootStringUrl + "/doRegister.php?user_type=" + user_type +
                "&fullname=" + fullname +
                "&email=" + email +
                "&password=" + password +
                "&companyname=" + company_name +
                "&industry=" + industry +
                "&desc=" + desc;

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        Log.i("registerAPI", registerAPIInitial);
        String registerAPI = rootStringUrl + "/doRegister.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_REGISTER_ADMIN);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_REGISTER_ADMIN);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(registerAPI);
            requestPackage.setParam("user_type", user_type);
            requestPackage.setParam("full_name", fullname);
            requestPackage.setParam("email_add", email);
            requestPackage.setParam("password", password);
            requestPackage.setParam("company_name", company_name);
            requestPackage.setParam("company_category", industry);
            requestPackage.setParam("company_desc", desc);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(registerAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }


    // #3
    public static void getLogInAPI(Context cont, String email, String password) {
        String logInAPIInitial = rootStringUrl + "/doLogin.php?email=" + email + "&password=" + password;

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        Log.i("registerAPI", logInAPIInitial);
        String logInAPI = rootStringUrl + "/doLogin.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_LOGIN);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_LOGIN);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(logInAPI);
            requestPackage.setParam("email", email);
            requestPackage.setParam("password", password);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(logInAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    //  #4
    public static String getUpdateAdminProfileAPI(String username, String email, String password, int userid) {
        String updateProfileAPI = rootStringUrl + "/doUpdateProfile.php?fullname=" + username +
                "&email=" + email +
                "&password=" + password +
                "&user_id=" + userid;

        Log.i("updateProfileAPI", updateProfileAPI);
        return updateProfileAPI;
    }

    /*public static void getRegisterAPI(String user_type,String fullname, String email, String password, String company_name, String industry, String desc) {
        String registerAPI =rootStringUrl+ "/register.php?user_type="+user_type+
                "&fullname="+fullname+
                "&email="+email+
                "&password="+password+
                "&companyname="+company_name+
                "&industry="+industry+
                "&desc="+desc;

                Log.i("registerAPI",registerAPI);

    }

  public static String getLogInAPI(String username,  String password) {
        String logInAPI =rootStringUrl+ "/login.php?username="+username+"&password="+password;
        Log.i("logInAPI",logInAPI);
        return logInAPI;
    }


    public static String getUpdateProfileAPI(String username, String email, int userid) {
        String updateProfileAPI =rootStringUrl+ "/updateprofile.php?fullname="+username+"&email="+email+"&user_id="+userid;
        Log.i("updateProfileAPI",updateProfileAPI);
        return updateProfileAPI;
    }
*/

    public static String getUpdateCProfileAPI(int company_id, String company_name, String industry, String desc) {
        String updateProfileAPI =rootStringUrl+ "/updatecprofile.php?companyid="+company_id+"&companyname="+company_name+"&industry="+industry+"&desc="+desc;
        Log.i("updateCProfileAPI",updateProfileAPI);
        return updateProfileAPI;
    }
    public static String getChangePasswordAPI(int userid, String newpassword) {
        String changePassAPI =rootStringUrl+ "/changepassword.php?user_id="+userid+"&newpassword="+newpassword;
        Log.i("changePassAPI",changePassAPI);
        return changePassAPI;


    }
}
