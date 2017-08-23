package com.fyp.gosearchphoto.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.fyp.gosearchphoto.model.DataAlbum;
import com.fyp.gosearchphoto.model.DataCompany;
import com.fyp.gosearchphoto.model.DataDepartment;
import com.fyp.gosearchphoto.model.DataGroup;
import com.fyp.gosearchphoto.model.DataImage;
import com.fyp.gosearchphoto.model.DataStatus;
import com.fyp.gosearchphoto.model.DataUser;
import com.fyp.gosearchphoto.utils.HttpHelper;
import com.fyp.gosearchphoto.utils.RequestPackage;
import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by anamay on 8/15/17.
 */

public class CandyLoopService extends IntentService {

    public static final String TAG = "MyService";
    public static String MY_SERVICE_PAGE = "Main";
    public static String MY_SERVICE_PAYLOAD = "myServicePayload";
    public static String REQUEST_PACKAGE = "requestPackage";
    private Gson gson;
    private LocalBroadcastManager manager;
    private Intent messageIntent;

    public CandyLoopService() {
        super("CandyLoopService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        RequestPackage requestPackage;

        Log.i("CLService SERVICE", MY_SERVICE_PAGE);
        Log.i("CLService REQUEST", REQUEST_PACKAGE);
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_REGISTER_ADMIN)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_CHECKUSER_EXIST:
                    gson = new Gson();
                    DataUser duItems = gson.fromJson(response, DataUser.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }

        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_REGISTER_COMPANY)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_CHECKCOMPANY_EXIST:
                    gson = new Gson();
                    DataCompany duItems = gson.fromJson(response, DataCompany.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;

                case ServiceHelper.REQUEST_REGISTER_ADMIN:
                    Log.i("Response", response);
                    gson = new Gson();
                    DataCompany duItems2 = gson.fromJson(response, DataCompany.class);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems2);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }

        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_LOGIN)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_LOGIN:
                    gson = new Gson();
                    DataUser duItems = gson.fromJson(response, DataUser.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;

            }
        }

        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_PROFILE)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_UPDATE_PROFILE:
                    gson = new Gson();
                    DataUser duItems = gson.fromJson(response, DataUser.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;

            }
        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_CHANGE_PASSWORD)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_CHANGE_PASSWORD:
                    gson = new Gson();
                    DataUser duItems = gson.fromJson(response, DataUser.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;

            }
        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_MANAGE_COMPANY)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_GET_COMPANY:
                    gson = new Gson();
                    DataCompany duItems = gson.fromJson(response, DataCompany.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_UPDATE_COMPANY:
                    gson = new Gson();
                    DataCompany duItems2 = gson.fromJson(response, DataCompany.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems2);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;

            }
        }

        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_MANAGE_USER)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_GET_COMPANY_USERS:
                    gson = new Gson();
                    DataUser duItems = gson.fromJson(response, DataUser.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;


            }
        }

        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_MANAGE_USER_PROFILE)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_GET_COMPANY_DEPARTMENTS:
                    gson = new Gson();
                    DataDepartment ddItems = gson.fromJson(response, DataDepartment.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, ddItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;

                case ServiceHelper.REQUEST_UPDATE_USER_PROFILE:
                    gson = new Gson();
                    DataStatus dsItems = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, dsItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_CHECKUSER_EXIST:
                    gson = new Gson();
                    DataStatus duItems = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_REGISTER_COMPANY_USER:
                    gson = new Gson();
                    DataStatus duItems1 = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems1);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;

            }
        }

        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_MANAGE_DEPARTMENT)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_GET_COMPANY_DEPARTMENTS:
                    gson = new Gson();
                    DataDepartment ddItems = gson.fromJson(response, DataDepartment.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, ddItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }
        }

        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_MANAGE_DEPARTMENT_PROFILE)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_CREATE_DEPARTMENT:
                    gson = new Gson();
                    DataStatus ddItems = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, ddItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }
        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_CHANGE_EMPLOYEE_PASSWORD)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_CHANGE_USER_PASSWORD:
                    gson = new Gson();
                    DataStatus ddItems = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, ddItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }
        }

        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_MANAGE_USER_ALBUM)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {
                case ServiceHelper.REQUEST_GET_ALBUM_LIST_BY_USER:
                    gson = new Gson();
                    DataAlbum ddItems = gson.fromJson(response, DataAlbum.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, ddItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;

                case ServiceHelper.REQUEST_GET_ALBUM_LIST_BY_OWNER:
                    gson = new Gson();
                    DataAlbum daItems = gson.fromJson(response, DataAlbum.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }
        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_MANAGE_PROJECT_ALBUM)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {


                case ServiceHelper.REQUEST_GET_ALBUM_LIST_BY_OWNER:
                    gson = new Gson();
                    DataAlbum daItems = gson.fromJson(response, DataAlbum.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }
        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_MANAGE_PROJECT_ALBUM_PROFILE)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {


                case ServiceHelper.REQUEST_CREATE_ALBUM:
                    gson = new Gson();
                    DataStatus daItems = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }
        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_MANAGE_GROUPS)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {


                case ServiceHelper.REQUEST_GET_GROUP_BY_USER:
                    gson = new Gson();
                    DataGroup daItems = gson.fromJson(response, DataGroup.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }
        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_MANAGE_GROUPS_PROFILE)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {


                case ServiceHelper.REQUEST_CREATE_GROUP:
                    gson = new Gson();
                    DataStatus daItems = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;

                case ServiceHelper.REQUEST_UPDATE_GROUP:
                    gson = new Gson();
                    DataStatus daItems1 = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems1);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_GET_GROUP_INFO:
                    gson = new Gson();
                    DataGroup dgItems1 = gson.fromJson(response, DataGroup.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, dgItems1);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_GET_COMPANY_USERS:
                    gson = new Gson();
                    DataUser duItems = gson.fromJson(response, DataUser.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, duItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_SHARE_GROUP_TO_USER:
                    gson = new Gson();
                    DataStatus daItems2 = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems2);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;

            }
        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_PUBLIC)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {


                case ServiceHelper.REQUEST_SEARCH_PUBLIC:
                    gson = new Gson();
                    DataImage daItems = gson.fromJson(response, DataImage.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_ADD_FAVOURITE:
                    gson = new Gson();
                    DataStatus daItems1 = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems1);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_REMOVE_FAVOURITE:
                    gson = new Gson();
                    DataStatus daItems2 = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems2);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }
        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_MYPHOTO)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {


                case ServiceHelper.REQUEST_SEARCH_MY_PHOTO:
                    gson = new Gson();
                    DataImage daItems = gson.fromJson(response, DataImage.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_ADD_FAVOURITE:
                    gson = new Gson();
                    DataStatus daItems1 = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems1);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_REMOVE_FAVOURITE:
                    gson = new Gson();
                    DataStatus daItems2 = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems2);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }
        }
        if (MY_SERVICE_PAGE.equals(ServiceHelper.PAGE_FAVOURITES)) {

            requestPackage =
                    (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

            String response;
            try {
                response = HttpHelper.downloadFromFeed(requestPackage);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (REQUEST_PACKAGE) {


                case ServiceHelper.REQUEST_SEARCH_MY_FAVOURITES:
                    gson = new Gson();
                    DataImage daItems = gson.fromJson(response, DataImage.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_ADD_FAVOURITE:
                    gson = new Gson();
                    DataStatus daItems1 = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems1);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
                case ServiceHelper.REQUEST_REMOVE_FAVOURITE:
                    gson = new Gson();
                    DataStatus daItems2 = gson.fromJson(response, DataStatus.class);
                    Log.i("Response", response);
                    messageIntent = new Intent(MY_SERVICE_PAGE);
                    messageIntent.putExtra(MY_SERVICE_PAYLOAD, daItems2);
                    manager = LocalBroadcastManager.getInstance(getApplicationContext());
                    manager.sendBroadcast(messageIntent);
                    break;
            }
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    public static void setService(String msg, String payload, String packge) {
        MY_SERVICE_PAGE = msg;
        MY_SERVICE_PAYLOAD = payload;
        REQUEST_PACKAGE = packge;
    }

    public static String getMyServicePage() {
        return MY_SERVICE_PAGE;
    }

    public static void setMyServicePage(String myServiceMessage) {
        MY_SERVICE_PAGE = myServiceMessage;
    }

    public static String getMyServicePayload() {
        return MY_SERVICE_PAYLOAD;
    }

    public static void setMyServicePayload(String myServicePayload) {
        MY_SERVICE_PAYLOAD = myServicePayload;
    }

    public static String getRequestPackage() {
        return REQUEST_PACKAGE;
    }

    public static void setRequestPackage(String requestPackage) {
        REQUEST_PACKAGE = requestPackage;
    }
}
