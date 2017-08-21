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
import com.fyp.gosearchphoto.model.DataGroup;
import com.fyp.gosearchphoto.model.DataGroupAdapter;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

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

// Hide keyboard
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
        mDataSource = CDataSource.getInstance(this);

        List<DataGroup> listFromDB1 = mDataSource.getAllGroupByOwnerId(PreferencesConfig.getUserIDPreference(this), "ManageGroup");
        // Customize Adapter
        adapter = new DataGroupAdapter(this, listFromDB1, null);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }
    private void performSearch() {

        mDataSource = CDataSource.getInstance(this);
        getKeywordSearch = etMGroupearch.getText().toString().trim();


        List<DataGroup> listFromDB1 = mDataSource.getAllGroupUserByName(PreferencesConfig.getUserIDPreference(this),getKeywordSearch,  "ManageGroup");
        // Customize Adapter
        adapter = new DataGroupAdapter(this, listFromDB1, null);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
        Utilities.hideKeyboard(this);
    }

}
