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

    //  #9
    public static void getAddFavourite(Context cont, int image_id, int user_id) {
        String getAddFavouriteInit = rootStringUrl + "/doAddFavourite.php?image_id=" + image_id +
                "&user_id=" + user_id;

        Log.i("getAddFavouriteAPI", getAddFavouriteInit);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getAddFavouriteAPI = rootStringUrl + "/doAddFavourite.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_ADD_FAVOURITE);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_ADD_FAVOURITE);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getAddFavouriteAPI);

            requestPackage.setParam("image_id", Integer.toString(image_id));
            requestPackage.setParam("user_id", Integer.toString(user_id));

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getAddFavouriteAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    //  #10
    public static void getRemoveFavourite(Context cont,int image_id, int user_id) {
        String getRemoveFavouriteInit = rootStringUrl + "/doRemoveFavourite.php?image_id=" + image_id +
                "&user_id=" + user_id;

        Log.i("doRemoveFavourite", getRemoveFavouriteInit);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getRemoveFavouriteAPI = rootStringUrl + "/doRemoveFavourite.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_REMOVE_FAVOURITE);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_REMOVE_FAVOURITE);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getRemoveFavouriteAPI);
            requestPackage.setParam("image_id", Integer.toString(image_id));
            requestPackage.setParam("user_id", Integer.toString(user_id));

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getRemoveFavouriteAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    // #11.1
    public static void getSearchPublicAPI(Context cont, int company_id, int user_id, String searchby, String keyword, String sortby) {
        String searchPublicInit = rootStringUrl + "/doSearchPublic.php?companyid=" + company_id +
                "&user_id=" + user_id +
                "&searchby=" + searchby +
                "&keyword=" + keyword +
                "&sortby=" + sortby;

        Log.i("searchPublicAPI", searchPublicInit);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String searchPublicAPI = rootStringUrl + "/doSearchPublic.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_SEARCH_PUBLIC);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_SEARCH_PUBLIC);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(searchPublicAPI);
            requestPackage.setParam("companyid", Integer.toString(company_id));
            requestPackage.setParam("user_id", Integer.toString(user_id));
            requestPackage.setParam("searchby", searchby);
            requestPackage.setParam("keyword", keyword);
            requestPackage.setParam("sortby", sortby);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(searchPublicAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }

    }

    // #11.2
    public static void getAlbumInfo(Context cont, int album_id) {
        String getAlbumInfoInit = rootStringUrl + "/getAlbumInfo.php?album_id=" + album_id;

        Log.i("getAlbumInfoAPI", getAlbumInfoInit);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getAlbumInfoAPI = rootStringUrl + "/getAlbumInfo.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_GET_ALBUM_INFO);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_GET_ALBUM_INFO);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getAlbumInfoAPI);
            requestPackage.setParam("album_id", Integer.toString(album_id));

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getAlbumInfoAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }    }

    // #12
    public static void getSearchMyPhotoAPI(Context cont, int company_id, int user_id, String searchby, String keyword, String sortby) {
        String searchMyPhotoInit = rootStringUrl + "/doSearchMyPhoto.php?company_id=" + company_id +
                "&user_id=" + user_id +
                "&searchby=" + searchby +
                "&keyword=" + keyword +
                "&sortby=" + sortby;

        Log.i("searchMyPhotoAPI", searchMyPhotoInit);


        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String searchMyPhotoAPI = rootStringUrl + "/doSearchMyPhoto.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_SEARCH_MY_PHOTO);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_SEARCH_MY_PHOTO);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(searchMyPhotoAPI);
            requestPackage.setParam("companyid", Integer.toString(company_id));
            requestPackage.setParam("userid", Integer.toString(user_id));
            requestPackage.setParam("searchby", searchby);
            requestPackage.setParam("keyword", keyword);
            requestPackage.setParam("sortby", sortby);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(searchMyPhotoAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    // #13
    public static void getSearchMyFavouriteAPI(Context cont, int company_id, int user_id, String searchby, String keyword, String sortby) {
        String searchMyFaveInit = rootStringUrl + "/doSearchMyFavourites.php?company_id=" + company_id +
                "&user_id=" + user_id +
                "&searchby=" + searchby +
                "&keyword=" + keyword +
                "&sortby=" + sortby;

        Log.i("searchMyFaveAPI", searchMyFaveInit);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String searchMyFaveAPI = rootStringUrl + "/doSearchMyFavourites.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_SEARCH_MY_FAVOURITES);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_SEARCH_MY_FAVOURITES);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(searchMyFaveAPI);
            requestPackage.setParam("companyid", Integer.toString(company_id));
            requestPackage.setParam("userid", Integer.toString(user_id));
            requestPackage.setParam("searchby", searchby);
            requestPackage.setParam("keyword", keyword);
            requestPackage.setParam("sortby", sortby);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(searchMyFaveAPI));
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
    // #18
    public static void getChangeUserPasswordAPI(Context cont, int user_id, String newpassword, int admin_id, String adminpassword) {
        String changePassAPI = rootStringUrl + "/doChangeUserPassword.php?user_id=" + user_id +
                "&newpassword=" + newpassword +
                "&admin_id=" + admin_id +
                "&adminpassword=" + adminpassword;


        Log.i("getChangeUserPassword", changePassAPI);

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String changeEPassAPI = rootStringUrl + "/doChangeUserPassword.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_CHANGE_USER_PASSWORD);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_CHANGE_USER_PASSWORD);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(changeEPassAPI);
            requestPackage.setParam("user_id", Integer.toString(user_id));
            requestPackage.setParam("newpassword", newpassword);
            requestPackage.setParam("admin_id", Integer.toString(admin_id));
            requestPackage.setParam("adminpassword", adminpassword);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(changeEPassAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }




    }
    // #33
    public static void getCreateDepartment(Context cont, int userid, int companyid, String departmentName, String desc) {
        String getCreateDepartmeninitt = rootStringUrl + "/doCreateDepartment.php?userid=" + userid +
                "&companyid=" + companyid +
                "&departmentName=" + departmentName +
                "&desc=" + desc;

        Log.i("getCreateDepartment", getCreateDepartmeninitt);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getCreateDepartment = rootStringUrl + "/doCreateDepartment.php";
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

    //  #19
    public static void getAlbumListByUser(Context cont, int user_id) {
        String getAlbumListByUserInit = rootStringUrl + "/getAlbumListByUser.php?user_id=" + user_id;

        Log.i("getAlbumListByUserAPI", getAlbumListByUserInit);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getAlbumListByUserAPI = rootStringUrl + "/getAlbumListByUser.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_GET_ALBUM_LIST_BY_USER);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_GET_ALBUM_LIST_BY_USER);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getAlbumListByUserAPI);
            requestPackage.setParam("user_id", Integer.toString(user_id));


            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getAlbumListByUserAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }


    //  #20
    public static void getRemoveUserAlbum(Context cont, int album_id, int user_id) {
        String getRemoveUserAlbumInit = rootStringUrl + "/doRemoveUserAlbum.php?album_id=" + album_id +
                "&user_id=" + user_id;
        Log.i("getRemoveUserAlbumAPI", getRemoveUserAlbumInit);

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getRemoveUserAlbumAPI = rootStringUrl + "/doRemoveUserAlbum.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_REMOVE_USER_ALBUM);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_REMOVE_USER_ALBUM);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getRemoveUserAlbumAPI);
            requestPackage.setParam("user_id", Integer.toString(user_id));
            requestPackage.setParam("album_id", Integer.toString(album_id));


            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getRemoveUserAlbumAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    //  #21
    public static void getALbumListbyOwner(Context cont, int owner_id, String keyword) {
        String getALbumListbyOwnerInit = rootStringUrl + "/getAlbumListByOwner.php?owner_id=" + owner_id +
                "&keyword=" + keyword;

        Log.i("getALbumListbyOwnerAPI", getALbumListbyOwnerInit);

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getALbumListbyOwnerAPI = rootStringUrl + "/getAlbumListByOwner.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_GET_ALBUM_LIST_BY_OWNER);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_GET_ALBUM_LIST_BY_OWNER);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getALbumListbyOwnerAPI);
            requestPackage.setParam("owner_id", Integer.toString(owner_id));
            requestPackage.setParam("keyword", keyword);


            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getALbumListbyOwnerAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    // #24
    public static void getGroupInfo(Context cont, int owner_id, int groupid) {
        String getGroupInfoAPI = rootStringUrl + "/getGroupInfo.php?owner_id=" + owner_id +
                "&groupid=" + groupid;

        Log.i("getGroupInfoAPI", getGroupInfoAPI);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getALbumListbyOwnerAPI = rootStringUrl + "/getGroupInfo.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_GET_GROUP_INFO);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_GET_GROUP_INFO);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getALbumListbyOwnerAPI);
            requestPackage.setParam("owner_id", Integer.toString(owner_id));
            requestPackage.setParam("groupid", Integer.toString(groupid));


            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getALbumListbyOwnerAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }    }

    // #25
    public static void getUpdateGroup(Context cont, int ownerid, int groupid, String groupname) {
        String getUpdateGroupInit = rootStringUrl + "/doUpdateGroup.php?ownerid=" + ownerid +
                "&groupid=" + groupid +
                "&groupname=" + groupname;

        Log.i("getUpdateGroupAPI", getUpdateGroupInit);



        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getUpdateGroupAPI = rootStringUrl + "/doUpdateGroup.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_UPDATE_GROUP);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_UPDATE_GROUP);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getUpdateGroupAPI);
            requestPackage.setParam("ownerid", Integer.toString(ownerid));
            requestPackage.setParam("groupid", Integer.toString(groupid));
            requestPackage.setParam("groupname", groupname);


            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getUpdateGroupAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    // #23.1 & 23.2
    public static void getGroupByUser(Context cont, int userid, String keyword) {
        String getGroupByUserInit = rootStringUrl + "/getGroupByUser.php?userid=" + userid +
                "&keyword=" + keyword;

        Log.i("getGroupByUserAPI", getGroupByUserInit);

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getGroupByUserAPI = rootStringUrl + "/getGroupByUser.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_GET_GROUP_BY_USER);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_GET_GROUP_BY_USER);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getGroupByUserAPI);
            requestPackage.setParam("userid", Integer.toString(userid));
            requestPackage.setParam("keyword", keyword);


            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getGroupByUserAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }

    }


    // #27
    public static void getCreateGroup(Context cont, String groupname, int owner_id){
        String getDeleteGroupUserInit = rootStringUrl + "/doCreateGroup.php?groupname=" + groupname +
                "&owner_id=" + owner_id;

        Log.i("getDeleteGroupUserAPI", getDeleteGroupUserInit);

        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getDeleteGroupUserAPI = rootStringUrl + "/doCreateGroup.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_CREATE_GROUP);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_CREATE_GROUP);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getDeleteGroupUserAPI);
            requestPackage.setParam("owner_id", Integer.toString(owner_id));
            requestPackage.setParam("groupname", groupname);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getDeleteGroupUserAPI));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    // #28 This should users to groups
    public static void getShareGroupToUser(Context cont, String strjson) {
        String getShareGroupToUserInit = rootStringUrl + "/doShareGroupToUser.php?json=" + strjson;


        Log.i("getShareGroupToUser", getShareGroupToUserInit);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getShareGroupToUser = rootStringUrl + "/doShareGroupToUser.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_SHARE_GROUP_TO_USER);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_SHARE_GROUP_TO_USER);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getShareGroupToUser);
            requestPackage.setParam("json", strjson);

            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getShareGroupToUser));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }
    }

    // #41
    public static void getCreateAlbum(Context cont, int userid, String albumName, String desc, String type, int company_id) {
        String getCreateAlbumInit = rootStringUrl + "/doCreateAlbum.php?userid=" + userid +
                "&albumName=" + albumName +
                "&desc=" + desc +
                "&type=" + type;

        Log.i("getCreateAlbumInit", getCreateAlbumInit);
        boolean networkOk = NetworkHelper.hasNetworkAccess(cont);

        String getCreateAlbum = rootStringUrl + "/doCreateAlbum.php";
        if (networkOk) {
            CandyLoopService.setRequestPackage(ServiceHelper.REQUEST_CREATE_ALBUM);
            CandyLoopService.setMyServicePayload(ServiceHelper.PAYLOAD_CREATE_ALBUM);
            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(getCreateAlbum);
            requestPackage.setParam("userid", Integer.toString(userid));
            requestPackage.setParam("company_id", Integer.toString(company_id));
            requestPackage.setParam("albumName", albumName);
            requestPackage.setParam("desc", desc);
            requestPackage.setParam("type", type);


            Intent intent = new Intent(cont, CandyLoopService.class);
            intent.setData(Uri.parse(getCreateAlbum));
            intent.putExtra(CandyLoopService.REQUEST_PACKAGE, requestPackage);
            cont.startService(intent);
        } else {
            Utilities.displayToast(cont, ServiceHelper.ERROR_NETWORK_MSG);
        }    }


}
