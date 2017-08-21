package com.fyp.gosearchphoto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.database.CDataSource;
import com.fyp.gosearchphoto.model.DataAlbum;
import com.fyp.gosearchphoto.model.DataAlbumAdapter;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.List;

public class ManageAlbumActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView ibMAlbumSearch;
    private ImageView btnMAlbumClose;
    private FloatingActionButton fabAddAlbum;
    private RecyclerView mRecyclerView ;
    private EditText etMAlbumSearch;

    private int getUserID;
    private CDataSource mDataSource;
    private DataAlbumAdapter adapter;
    private String getKeywordSearch;
    private String PAGE_NAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_album);


        ibMAlbumSearch = (ImageView)findViewById(R.id.ibMAlbumSearch);
        btnMAlbumClose = (ImageView)findViewById(R.id.btnMAlbumClose);
        fabAddAlbum = (FloatingActionButton) findViewById(R.id.fabManageAlbum);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvManageAlbum);
        etMAlbumSearch = (EditText) findViewById(R.id.etMAlbumSearch);

        ibMAlbumSearch.setOnClickListener(this);
        btnMAlbumClose.setOnClickListener(this);
        fabAddAlbum.setOnClickListener(this);
        initializeUserAlbumList();

    }
    private void initializeUserAlbumList() {
        getUserID = PreferencesConfig.getUserIDPreference(this);

        mDataSource = CDataSource.getInstance(this);
        PAGE_NAME = "ManageAlbum";

        List<DataAlbum> listFromDB1 = mDataSource.getAllAlbumItems(getUserID, PAGE_NAME);
        // Customize Adapter
        adapter = new DataAlbumAdapter(this, listFromDB1, null , null, null, null);
        Log.i("get userID", "" +getUserID);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);

        // Hide keyboard
        Utilities.hideKeyboardNow(getWindow());
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMAlbumClose:
                startActivity(new Intent(ManageAlbumActivity.this, TabActivity.class));
                break;


            case R.id.fabManageAlbum:
                startActivity(new Intent(ManageAlbumActivity.this, TabMAlbumActivity.class));
                break;
            case  R.id.ibMAlbumSearch:
                performUserSearch();
                break;

        }
    }
    public void performUserSearch(){

        getKeywordSearch = etMAlbumSearch.getText().toString().trim();
        List<DataAlbum> listFromDB2 = mDataSource.getAllAlbumByNameFrag(getUserID, PAGE_NAME, getKeywordSearch, null, null, null);

        adapter = new DataAlbumAdapter(this, listFromDB2, null, null, null, null);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);

    }
}
