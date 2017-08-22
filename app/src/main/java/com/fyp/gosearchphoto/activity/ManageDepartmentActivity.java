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
import com.fyp.gosearchphoto.model.DataDepartmentAdapter;
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class ManageDepartmentActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView ibMDepartmentFilter;
    private ImageView btnMDepartmentClose;
    private FloatingActionButton fabAddDepartment;


    private EditText etMDepartmentSearch;

    private CDataSource mDataSource;
    private DataDepartmentAdapter adapter;
    private String getKeywordSearch;
    private int getCompanyId;
    private RecyclerView mRecyclerView;
    private final String PAGE_NAME = "ManageDepartment";
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_department);

        ibMDepartmentFilter = (ImageView)findViewById(R.id.ibMDepartmentFilter);
        btnMDepartmentClose = (ImageView)findViewById(R.id.btnMDepartmentClose);
        fabAddDepartment = (FloatingActionButton) findViewById(R.id.fabManageDepartment);
        etMDepartmentSearch = (EditText) findViewById(R.id.etMDepartmentSearch);

        mContext =this;
        getCompanyId = PreferencesConfig.getCompanyIdPreference(mContext);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvManageDepartment);

        mRecyclerView.setLayoutManager(mLayoutManager);

        ibMDepartmentFilter.setOnClickListener(this);
        btnMDepartmentClose.setOnClickListener(this);
        fabAddDepartment.setOnClickListener(this);
        registerBroadcast();

        initializeDepartmentList();


    }

    private void initializeDepartmentList() {
        APIManager.getCompanyDepartment(mContext, getCompanyId, "all");

        // Hide keyboard
        Utilities.hideKeyboardNow(getWindow());
    }

    public void performDepartmentSearch(){

        getKeywordSearch = etMDepartmentSearch.getText().toString().trim();

        if(Utilities.checkIsNull(getKeywordSearch)==true){
            getKeywordSearch ="all";
        }
        APIManager.getCompanyDepartment(mContext, getCompanyId, getKeywordSearch);
        // Hide keyboard
        Utilities.hideKeyboardNow(getWindow());

    }


    public void setDeptDataAdapter(List<DataDepartment> du){
        adapter = new DataDepartmentAdapter(mContext, du, null);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);

        // Hide keyboard
        Utilities.hideKeyboardNow(getWindow());
    }


        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMDepartmentClose:
                startActivity(new Intent(ManageDepartmentActivity.this, TabActivity.class));
                break;


            case R.id.fabManageDepartment:
                startActivity(new Intent(ManageDepartmentActivity.this, TabMDepartmentActivity.class));
                break;

            case R.id.ibMDepartmentFilter:
                performDepartmentSearch();

                break;

        }
    }






    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {


                case ServiceHelper.PAYLOAD_GET_COMPANY_DEPARTMENTS:

                    DataDepartment ddata = (DataDepartment) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if (ddata.getStatus().equals("no data")) {
                        Log.i("department: ", "no data");

                    } else if (ddata.getStatus().equals("success")) {
                        List<DataDepartment> listFromServer = ddata.getDepartmentList();
                        Log.i("to string ", ddata.toString());
                        List<DataDepartment> dataItems = new ArrayList<>();

                        // Insert page to list
                        for (int i = 0; i < listFromServer.size(); i++) {
                            DataDepartment item = new DataDepartment();
                            item.setDepartment_name(listFromServer.get(i).getName());
                            item.setDescription(listFromServer.get(i).getDesc());
                            item.setPage_data_type(PAGE_NAME);
                            dataItems.add(item);
                        }

                        setDeptDataAdapter(dataItems);


                    } else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;

            }
        }

    };

    public void registerBroadcast() {
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_MANAGE_DEPARTMENT);
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }

}

