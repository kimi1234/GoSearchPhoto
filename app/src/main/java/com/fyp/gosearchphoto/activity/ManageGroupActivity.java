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
import com.fyp.gosearchphoto.model.DataGroup;
import com.fyp.gosearchphoto.model.DataGroupAdapter;
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class ManageGroupActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView btnMGroupClose;
    private FloatingActionButton fabAddGroup;
    private EditText etMGroupearch;
    private ImageView btnMGroupSearch;

    private CDataSource mDataSource;
    private DataGroupAdapter adapter;
    private String getKeywordSearch;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private String PAGE_NAME = "ManageGroup";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);


        etMGroupearch = (EditText)findViewById(R.id.etMGroupSearch);
        btnMGroupSearch = (ImageView)findViewById(R.id.ibMGroupSearch);
        btnMGroupClose = (ImageView)findViewById(R.id.btnMGroupClose);
        fabAddGroup = (FloatingActionButton) findViewById(R.id.fabManageGroup);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvManageGroup);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btnMGroupSearch.setOnClickListener(this);
        btnMGroupClose.setOnClickListener(this);
        fabAddGroup.setOnClickListener(this);
        mContext = this;

        registerBroadcast();

        Utilities.hideKeyboardNow(getWindow());

        initializeSearchData();


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMGroupClose:
                startActivity(new Intent(ManageGroupActivity.this, TabActivity.class));
                break;


            case R.id.fabManageGroup:
                startActivity(new Intent(ManageGroupActivity.this, TabMGroupActivity.class));
                break;

            case R.id.ibMGroupSearch:
                performSearch();
            break;

        }
    }
    private void initializeSearchData() {
        APIManager.getGroupByUser(mContext,
                PreferencesConfig.getUserIDPreference(mContext),
                "all");
    }

    private void setGroupDataAdapter(List<DataGroup> dg){
        adapter = new DataGroupAdapter(this, dg, null);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }

    private void performSearch() {

        getKeywordSearch = etMGroupearch.getText().toString().trim();

        if(Utilities.checkIsNull(getKeywordSearch)==true){
            getKeywordSearch ="all";
        }
        APIManager.getGroupByUser(mContext,
                PreferencesConfig.getUserIDPreference(mContext),
                getKeywordSearch);

        Utilities.hideKeyboard(this);
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
                case ServiceHelper.PAYLOAD_GET_GROUP_BY_USER:

                    DataGroup du = (DataGroup) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if(du.getStatus().equals("no data")){
                        Utilities.displayToast(mContext, "No group found");

                    }else if(du.getStatus().equals("success")){
                        List<DataGroup> listFromServer = du.getGroupList();
                        Log.i("to string ", du.toString());

                        // filter list and add pagename list
                        List<DataGroup> dataItems = new ArrayList<>();

                        for (int i = 0; i < listFromServer.size(); i++) {
                                DataGroup item = new DataGroup();
                                item.setGroup_id(listFromServer.get(i).getGroup_id());
                                item.setGroup_name(listFromServer.get(i).getGroupName());
                                item.setPage_data_type(PAGE_NAME);
                                dataItems.add(item);
                        }

                        setGroupDataAdapter(dataItems);

                    }else{
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;

            }
        }

    };

    public void registerBroadcast(){
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_MANAGE_GROUPS);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }

}
