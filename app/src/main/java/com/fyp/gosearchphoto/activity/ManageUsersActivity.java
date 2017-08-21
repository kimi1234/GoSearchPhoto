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
import com.fyp.gosearchphoto.model.DataUser;
import com.fyp.gosearchphoto.model.DataUserAdapter;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

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

    private final String PAGE_NAME = "ManageUsers";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);


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

        initializeUserList();
    }

    private void initializeUserList() {
        getCompanyId = PreferencesConfig.getCompanyIdPreference(this);

        mDataSource = CDataSource.getInstance(this);

        List<DataUser> listFromDB1 = mDataSource.getAllUserByCompanyId(getCompanyId, PAGE_NAME);
        // Customize Adapter
        adapter = new DataUserAdapter(this, listFromDB1, null , null);
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

    public void performUserSearch(){

        getKeywordSearch = etMUserSearch.getText().toString().trim();
        List<DataUser> listFromDB2 = mDataSource.getUsersByName(getKeywordSearch, PreferencesConfig.getCompanyIdPreference(this), PAGE_NAME);

        adapter = new DataUserAdapter(this, listFromDB2, null, null);
        Log.i("get companyId", "" +getCompanyId);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);

    }
}
