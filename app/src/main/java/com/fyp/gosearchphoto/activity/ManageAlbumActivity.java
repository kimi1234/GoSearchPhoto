package com.fyp.gosearchphoto.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
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
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class ManageAlbumActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ibMAlbumSearch;
    private ImageView btnMAlbumClose;
    private FloatingActionButton fabAddAlbum;
    private RecyclerView mRecyclerView;
    private EditText etMAlbumSearch;

    private int getUserID;
    private CDataSource mDataSource;
    private DataAlbumAdapter adapter;
    private String getKeywordSearch;
    private String PAGE_NAME;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_album);


        ibMAlbumSearch = (ImageView) findViewById(R.id.ibMAlbumSearch);
        btnMAlbumClose = (ImageView) findViewById(R.id.btnMAlbumClose);
        fabAddAlbum = (FloatingActionButton) findViewById(R.id.fabManageAlbum);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvManageAlbum);
        etMAlbumSearch = (EditText) findViewById(R.id.etMAlbumSearch);
        mContext = this;
        ibMAlbumSearch.setOnClickListener(this);
        btnMAlbumClose.setOnClickListener(this);
        fabAddAlbum.setOnClickListener(this);
        registerBroadcast();
        initializeUserAlbumList();
        Utilities.hideKeyboardNow(getWindow());


    }

    private void initializeUserAlbumList() {
        PAGE_NAME = "ManageAlbum";
        APIManager.getALbumListbyOwner(mContext,
                PreferencesConfig.getUserIDPreference(mContext),
                "all");

    }

    private void setAlbumDataAdapter(List<DataAlbum> du) {
        adapter = new DataAlbumAdapter(this, du, null, null, null, null);
        Log.i("get userID", "" + getUserID);
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
            case R.id.ibMAlbumSearch:
                performUserSearch();
                break;

        }
    }


    public void performUserSearch() {

        getKeywordSearch = etMAlbumSearch.getText().toString().trim();

        if (Utilities.checkIsNull(getKeywordSearch) == true) {
            getKeywordSearch = "all";
        }
        APIManager.getALbumListbyOwner(mContext,
                PreferencesConfig.getUserIDPreference(mContext),
                getKeywordSearch);
    }

    public void registerBroadcast() {
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_MANAGE_PROJECT_ALBUM);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
                case ServiceHelper.PAYLOAD_GET_ALBUM_LIST_BY_OWNER:

                    DataAlbum du = (DataAlbum) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if (du.getStatus().equals("no data")) {
                        Utilities.displayToast(mContext, "No album found");

                    } else if (du.getStatus().equals("success")) {
                        List<DataAlbum> listFromServer = du.getAlbumList();
                        Log.i("to string ", du.toString());

                        // filter list and add pagename list
                        List<DataAlbum> dataItems = new ArrayList<>();

                        for (int i = 0; i < listFromServer.size(); i++) {
                            DataAlbum item = new DataAlbum();
                            item.setAlbum_name(listFromServer.get(i).getName());
                            item.setAlbumId(listFromServer.get(i).getAlbum_id());
                            item.setPrivacy_type(listFromServer.get(i).getPrivacy_type());
                            item.setDescription(listFromServer.get(i).getDescription());
                            item.setPage_data_type(PAGE_NAME);

                            dataItems.add(item);

                        }

                        setAlbumDataAdapter(dataItems);

                    } else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;

            }
        }

    };

}
