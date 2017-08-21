package com.fyp.gosearchphoto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_department);

        ibMDepartmentFilter = (ImageView)findViewById(R.id.ibMDepartmentFilter);
        btnMDepartmentClose = (ImageView)findViewById(R.id.btnMDepartmentClose);
        fabAddDepartment = (FloatingActionButton) findViewById(R.id.fabManageDepartment);
        etMDepartmentSearch = (EditText) findViewById(R.id.etMDepartmentSearch);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvManageDepartment);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ibMDepartmentFilter.setOnClickListener(this);
        btnMDepartmentClose.setOnClickListener(this);
        fabAddDepartment.setOnClickListener(this);

        initializeDepartmentList();


    }

    private void initializeDepartmentList() {
        getCompanyId = PreferencesConfig.getCompanyIdPreference(this);

        mDataSource = CDataSource.getInstance(this);

        List<DataDepartment> listFromDB1 = mDataSource.getAllDepartmentByCompanyId(getCompanyId, PAGE_NAME);
        // Customize Adapter
        adapter = new DataDepartmentAdapter(this, listFromDB1, null);
        Log.i("get companyId", "" +getCompanyId);
        Log.i("get count", "" + adapter.getItemCount());
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

    public void performDepartmentSearch(){

        getKeywordSearch = etMDepartmentSearch.getText().toString().trim();
        List<DataDepartment> listFromDB2 = mDataSource.getDepartmentByName(PreferencesConfig.getCompanyIdPreference(this), PAGE_NAME, getKeywordSearch);

        adapter = new DataDepartmentAdapter(this, listFromDB2, null);
        Log.i("get companyId", "" +getCompanyId);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);

    }
}
