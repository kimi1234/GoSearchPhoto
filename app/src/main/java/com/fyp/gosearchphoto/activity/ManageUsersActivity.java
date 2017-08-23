package com.fyp.gosearchphoto.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.database.CDataSource;
import com.fyp.gosearchphoto.model.DataDepartment;
import com.fyp.gosearchphoto.model.DataUser;
import com.fyp.gosearchphoto.model.DataUserAdapter;
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.ArrayList;
import java.util.List;


public class ManageUsersActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView btnMUsersClose;
    private ImageView btnMUserSearch;
    private FloatingActionButton fabAddUsers;
    private EditText etMUserSearch;

    private CDataSource mDataSource;
    private DataUserAdapter adapter;
    private String getKeywordSearch;
    private int getCompanyId;
    private RecyclerView mRecyclerView;


    public static final String ITEM_DEPT_LIST = "department list";
    public static final String ITEM_PAGE = "user profile page";

    public String nextPage;

    private Context mContext;
    private DataDepartment deptList;

    private final String PAGE_NAME = "ManageUsers";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        mContext=this;

        btnMUsersClose = (ImageView)findViewById(R.id.ibMUserSearch);
        btnMUserSearch = (ImageView)findViewById(R.id.btnMUserClose);
        etMUserSearch = (EditText) findViewById(R.id.etMUserSearch);

        fabAddUsers = (FloatingActionButton) findViewById(R.id.fabManageUser);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvManageUsername);
        mRecyclerView.setLayoutManager(mLayoutManager);


        btnMUserSearch.setOnClickListener(this);
        btnMUsersClose.setOnClickListener(this);
        fabAddUsers.setOnClickListener(this);
        nextPage  = "Update";
        registerBroadcast();
        getUserListNow();
    }

    private void getUserListNow() {
        APIManager.getCompanyUsers(mContext, PreferencesConfig.getCompanyIdPreference(mContext), "all");
        Utilities.hideKeyboardNow(getWindow());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMUserClose:
                startActivity(new Intent(ManageUsersActivity.this, TabActivity.class));
                break;


            case R.id.fabManageUser:
                startActivity(new Intent(ManageUsersActivity.this, TabMUsersActivity.class));

                break;

            case R.id.ibMUserSearch:
                    performUserSearch();

        }
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
                case ServiceHelper.PAYLOAD_GET_COMPANY_USERS:

                    DataUser du = (DataUser) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if(du.getStatus().equals("no data")){
                        Utilities.displayToast(mContext, "No user found");

                    }else if(du.getStatus().equals("success")){
                        List<DataUser> listFromServer = du.getcUserList();
                        Log.i("to string ", du.toString());

                        // filter list and add pagename list
                        List<DataUser> dataItems = new ArrayList<>();

                        for (int i = 0; i < listFromServer.size(); i++) {
                            if(listFromServer.get(i).getType().equals("Employee")) {
                                DataUser item = new DataUser();
                                item.setUser_id(listFromServer.get(i).getId());
                                item.setFullname(listFromServer.get(i).getFullName());
                                item.setEmail(listFromServer.get(i).getEmail());
                                item.setDepartmentName(listFromServer.get(i).getDepartment());
                                item.setType(listFromServer.get(i).getType());
                                item.setPage_data_type(PAGE_NAME);

                                dataItems.add(item);
                            }
                        }

                        setUserDataAdapter(dataItems);

                    }else{
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }
                    break;
            }
        }

    };

    public void registerBroadcast(){
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_MANAGE_USER);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }
    public void performUserSearch(){
        getKeywordSearch = etMUserSearch.getText().toString().trim();
        if(Utilities.checkIsNull(getKeywordSearch)==true){
            getKeywordSearch ="all";
        }
        APIManager.getCompanyUsers(mContext, PreferencesConfig.getCompanyIdPreference(mContext), getKeywordSearch);
    }
    public void setUserDataAdapter(List<DataUser> du){
        adapter = new DataUserAdapter(mContext, du, null , null);
        Log.i("get companyId", "" +getCompanyId);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }
}
