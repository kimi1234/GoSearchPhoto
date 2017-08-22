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
    public static void getUpdateAdminProfileAPI(Context cont, String username, String email, String password, int userid) {
        String updateProfileInitial = rootStringUrl + "/doUpdateProfile.php?fullname=" + username +
                "&email=" + email +
                "&password=" + password +
                "&user_id=" + userid;

        Log.i("updateProfileInitial", updateProfileInitial);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String updateProfileAPI = rootStringUrl + "/doUpdateProfile.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_UPDATE_PROFILE);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_UPDATE_PROFILE);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(updateProfileAPI);
            requestPackage.setParam("fullname", username);
            requestPackage.setParam("email", email);
            requestPackage.setParam("password", password);
            requestPackage.setParam("user_id", Integer.toString(userid));

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(updateProfileAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }


    // #6
    public static void getChangePasswordAPI(Context cont, int userid, String oldpassword, String newpassword) {
        String changePassInitial = rootStringUrl + "/doChangePassword.php?user_id=" + userid +
                "&oldpassword=" + oldpassword +
                "&newpassword=" + newpassword;

        Log.i("changePassInitial", changePassInitial);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String changePassAPI = rootStringUrl + "/doChangePassword.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_CHANGE_PASSWORD);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_CHANGE_PASSWORD);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(changePassAPI);
            requestPackage.setParam("oldpassword", oldpassword);
            requestPackage.setParam("newpassword", newpassword);
            requestPackage.setParam("user_id", Integer.toString(userid));

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(changePassAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }
    //  #7
    public static void getCompanyInfo(Context cont, int company_id) {
        String getCompanyInfoInitial = rootStringUrl + "/doGetCompany.php?companyid=" + company_id;

        Log.i("changePassInitial", getCompanyInfoInitial);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getCompanyInfoAPI = rootStringUrl + "/doGetCompany.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_GET_COMPANY);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_GET_COMPANY);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getCompanyInfoAPI);
            requestPackage.setParam("companyid", Integer.toString(company_id));

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getCompanyInfoAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }


    // #8
    public static void getUpdateCProfileAPI(Context cont, int company_id, String company_name, String industry, String desc) {
        String updateProfileInitial = rootStringUrl + "/doUpdateCompany.php?companyid=" + company_id +
                "&companyname=" + company_name + "&industry=" + industry + "&desc=" + desc;
        Log.i("updateProfileInitial", updateProfileInitial);

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String updateCProfileAPI = rootStringUrl + "/doUpdateCompany.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_UPDATE_COMPANY);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_UPDATE_COMPANY);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(updateCProfileAPI);
            requestPackage.setParam("companyid", Integer.toString(company_id));
            requestPackage.setParam("companyname", company_name);
            requestPackage.setParam("industry", industry);
            requestPackage.setParam("desc", desc);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(updateCProfileAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    // #14.1 &14.2
    public static void getCompanyUsers(Context cont, int company_id, String keyword) {
        String getCompanyUsersInitial = rootStringUrl + "/getCompanyUsers.php?companyid=" + company_id +
                "&keyword=" + keyword;

        Log.i("getCompanyUsersInitial", getCompanyUsersInitial);

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getCompanyUsersAPI = rootStringUrl + "/getCompanyUsers.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_GET_COMPANY_USERS);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_GET_COMPANY_USERS);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getCompanyUsersAPI);
            requestPackage.setParam("companyid", Integer.toString(company_id));
            requestPackage.setParam("keyword", keyword);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getCompanyUsersAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    // #15 & #32
    public static void getCompanyDepartment(Context cont, int company_id, String keyword) {
        String getCompanyDepartmentInitial = rootStringUrl + "/getCompanyDepartments.php?companyid=" + company_id +
                "&keyword=" + keyword;

        Log.i("getCompanyDeptInit", getCompanyDepartmentInitial);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getCompanyDepartmentAPI = rootStringUrl + "/getCompanyDepartments.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_GET_COMPANY_DEPARTMENTS);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_GET_COMPANY_DEPARTMENTS);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getCompanyDepartmentAPI);
            requestPackage.setParam("companyid", Integer.toString(company_id));
            requestPackage.setParam("keyword", keyword);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getCompanyDepartmentAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }



    }

    //  #16
    public static void getUpdateUserProfile(Context cont, String department, String type, String fullname, String email, int user_id) {
        String updateUserProfileInit = rootStringUrl + "/doUpdateUserProfile.php?department=" + department +
                "&type=" + type +
                "&fullname=" + fullname +
                "&email=" + email +
                "&user_id=" + user_id;

        Log.i("updateUserProfileInit", updateUserProfileInit);

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String updateUserProfileAPI = rootStringUrl + "/doUpdateUserProfile.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_UPDATE_USER_PROFILE);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_UPDATE_USER_PROFILE);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(updateUserProfileAPI);
            requestPackage.setParam("department", department);
            requestPackage.setParam("fullname", fullname);
            requestPackage.setParam("email", email);
            requestPackage.setParam("user_id", Integer.toString(user_id));

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(updateUserProfileAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    //  #17
    public static void getRegisterCompanyUser(Context cont, String department, String type, String fullname, String password, String email, int company_id) {
        String getRegisterCompanyInit = rootStringUrl + "/doRegisterCompanyUser.php?department=" + department +
                "&type=" + type +
                "&fullname=" + fullname +
                "&password=" + password +
                "&email=" + email;

        Log.i("getRegisterCompanyAPI", getRegisterCompanyInit);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getRegisterCompanyUserAPI = rootStringUrl + "/doRegisterCompanyUser.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_REGISTER_COMPANY_USER);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_REGISTER_COMPANY_USER);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getRegisterCompanyUserAPI);
            requestPackage.setParam("department", department);
            requestPackage.setParam("type", type);
            requestPackage.setParam("fullname", fullname);
            requestPackage.setParam("email", email);
            requestPackage.setParam("company_id", Integer.toString(company_id));

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getRegisterCompanyUserAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }
    // #33
    public static void getCreateDepartment(Context cont, int userid, int companyid, String departmentName, String desc) {
        String getCreateDepartmeninitt = rootStringUrl + "/createDepartment.php?userid=" + userid +
                "&companyid=" + companyid +
                "&departmentName=" + departmentName +
                "&desc=" + desc;

        Log.i("getCreateDepartment", getCreateDepartmeninitt);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getCreateDepartment = rootStringUrl + "/createDepartment.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_CREATE_DEPARTMENT);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_CREATE_DEPARTMENT);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getCreateDepartment);
            requestPackage.setParam("userid", Integer.toString(userid));
            requestPackage.setParam("companyid", Integer.toString(companyid));
            requestPackage.setParam("departmentName", departmentName);
            requestPackage.setParam("desc", desc);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getCreateDepartment));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
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


    public static String getUpdateCProfileAPI(int company_id, String company_name, String industry, String desc) {
        String updateProfileAPI =rootStringUrl+ "/updatecprofile.php?companyid="+company_id+"&companyname="+company_name+"&industry="+industry+"&desc="+desc;
        Log.i("updateCProfileAPI",updateProfileAPI);
        return updateProfileAPI;
    }
   public static String getChangePasswordAPI(int userid, String newpassword) {
        String changePassAPI =rootStringUrl+ "/changepassword.php?user_id="+userid+"&newpassword="+newpassword;
        Log.i("changePassAPI",changePassAPI);
        return changePassAPI;


    }*/
}
