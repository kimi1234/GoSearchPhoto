package com.fyp.gosearchphoto.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.database.CDataSource;
import com.fyp.gosearchphoto.model.DataAlbum;
import com.fyp.gosearchphoto.model.DataAlbumAdapter;
import com.fyp.gosearchphoto.model.DataUserAdapter;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.ArrayList;
import java.util.List;


public class GroupAlbumFragment extends Fragment implements View.OnClickListener {
    private ImageButton ibUserAlbumAdd;
    private CDataSource mDataSource;
    private DataAlbumAdapter adapter;
    private RecyclerView mRecyclerView;
    private String user_fullname,
            user_email, user_pass,
            user_type, user_dept;

    private int user_id;

    private String PAGE_NAME;
    private String getKeywordSearch;
    private Context mContext;
    private List<DataAlbum> topDataItems;
    private DataAlbumAdapter topAdapter;


    //
    // Bottom items
    //
    private List<DataAlbum> bottomDataItems;
    private DataAlbumAdapter bottomAdapter;
    private RecyclerView rvToAddAlbum;
    private RecyclerView rvExistingAlbum;
    private Button dialogButtonOK;
    private TextView tvDeptTitle;
    private EditText etMUserAlbumSearch;
    private Button dialogButtonCancel;
    private int user_cid;

    public GroupAlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vGroupAlbum = inflater.inflate(R.layout.fragment_group_album, container, false);


        ibUserAlbumAdd = (ImageButton) vGroupAlbum.findViewById(R.id.ibGroupAlbumAdd);

        mRecyclerView = (RecyclerView) vGroupAlbum.findViewById(R.id.rvAddedAlbum);
        mContext = getContext();

        ibUserAlbumAdd.setOnClickListener(this);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            user_fullname = extras.getString(DataUserAdapter.ITEM_USER_FULLNAME);
            user_id = extras.getInt(DataUserAdapter.ITEM_USER_ID);
            user_email = extras.getString(DataUserAdapter.ITEM_USER_EMAIL);
            user_type = extras.getString(DataUserAdapter.ITEM_USER_TYPE);
            user_cid = extras.getInt(DataUserAdapter.ITEM_USER_COMPANYID);
            user_dept = extras.getString(DataUserAdapter.ITEM_USER_DEPARTMENT);
            //The key argument here must match that used in the other activity


/*
            Log.i("user_fullname", user_fullname);
            Log.i("user_id", "USERID "+user_id);
            Log.i("user_email", user_email);
            Log.i("user_type", user_type);
            Log.i("user_dept", user_dept);
            Log.i("user_cid", "COMPANYID"+user_cid);
*/

            displayUserAlbum();

        }
        return vGroupAlbum;
    }


    public void showAddAlbum(Context context) {
        CDataSource mDataSource_popup;

        final Dialog album_dialog = new Dialog(context);
        album_dialog.setContentView(R.layout.popup_add_existing_album);
        album_dialog.setTitle("Add Existing Album");

        tvDeptTitle = (TextView) album_dialog.findViewById(R.id.lblThirdLayerDepartmentTitle);
        etMUserAlbumSearch = (EditText) album_dialog.findViewById(R.id.etMUserAlbumSearch);
        dialogButtonCancel = (Button) album_dialog.findViewById(R.id.dialog_btn_cancel);
        dialogButtonOK = (Button) album_dialog.findViewById(R.id.dialog_btn_ok);
        rvExistingAlbum = (RecyclerView) album_dialog.findViewById(R.id.rvExistingAlbum);
        rvToAddAlbum = (RecyclerView) album_dialog.findViewById(R.id.rvToAddAlbum);
        // set the custom dialog components - text, image and button
        etMUserAlbumSearch.setOnClickListener(this);
        etMUserAlbumSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getKeywordSearch = etMUserAlbumSearch.getText().toString().trim();
                    Utilities.displayToast(getContext(), "search clicked");
                    performAlbumSearch();
                    Utilities.hideKeyboard(getActivity());

                    return true;
                }

                return false;
            }
        });


        displayTopItemList();

        String getDeptName;
        if (user_dept != null) {
            if (user_dept.equals("No Department")) {
                getDeptName = "Administrator";
            } else {
                getDeptName = user_dept + " Department";
            }

            tvDeptTitle.setText(getDeptName);
        }
        // if button is clicked, close the custom dialog
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album_dialog.dismiss();
            }
        });

        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBottomListValues();
                album_dialog.dismiss();
            }
        });


        album_dialog.show();
        //  Set dialog width to fill parent and height to wrap content
        album_dialog.getWindow()
                .setAttributes(Utilities.setPopUpWidth(album_dialog));
        instantiateBottomList();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ibGroupAlbumAdd:
                showAddAlbum(getContext());
               /*
                    String myTxt = String.valueOf(user_id);
                    Utilities.displayToast(getContext(), "USERID" +myTxt);
                */
                break;
        }

    }

    private void displayTopItemList() {
        CDataSource mDataSource_popup;
        mDataSource_popup = CDataSource.getInstance(getContext());
        PAGE_NAME = "GroupExistingAlbumTop";
        topDataItems = mDataSource_popup.getAllAlbumItems(
                PreferencesConfig.getUserIDPreference(getContext()),
                PAGE_NAME);

        topAdapter = new DataAlbumAdapter(getContext(), topDataItems, null, null, GroupAlbumFragment.this, null);
        Log.i("get count", "" + topAdapter.getItemCount());
        topAdapter.notifyDataSetChanged();
        rvExistingAlbum.setAdapter(topAdapter);
    }

    public void displayUserAlbum() {
        mDataSource = CDataSource.getInstance(getContext());

        PAGE_NAME = "ManageGroupAlbum";
        List<DataAlbum> listFromDB2 = mDataSource.getAllAlbumItems(user_id, PAGE_NAME);

        adapter = new DataAlbumAdapter(getContext(), listFromDB2, null, null, GroupAlbumFragment.this, null);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);


    }

    public void instantiateBottomList() {
        bottomDataItems = new ArrayList<>();
    }

    public void addbottomItem(DataAlbum item) {
        bottomDataItems.add(item);
        bottomAdapter = new DataAlbumAdapter(getContext(), bottomDataItems, null, null, GroupAlbumFragment.this, null);
        bottomAdapter.notifyDataSetChanged();
        rvToAddAlbum.setAdapter(bottomAdapter);
    }

    public void addtopItem(DataAlbum item) {
        topDataItems.add(item);
        topAdapter = new DataAlbumAdapter(getContext(), topDataItems, null, null, GroupAlbumFragment.this, null);
        topAdapter.notifyDataSetChanged();
        rvExistingAlbum.setAdapter(topAdapter);
    }

    public void getBottomListValues() {
        for (DataAlbum b : bottomDataItems) {
            Log.i("getAlbumId", "" + b.getAlbumId());
            Log.i("getOwner_id", "" + b.getOwner_id());
            Log.i("getAlbum_name", b.getAlbum_name());
            Log.i("getOwner_name", b.getOwner_name());
            Log.i("getDescription", b.getDescription());
            Log.i("getPrivacy_type", b.getPrivacy_type());
            Log.i("setPage_data_type", b.getPrivacy_type());
            Log.i("~~~~~~", "~~~~~~~~~~~~");
            //update, check for collisions, etc
        }
    }

    public boolean checkBottomItemExists(int albumID) {
        for (DataAlbum item : bottomDataItems) {
            if (item.getAlbumId() == albumID) {
                Log.i("pageDataType", "yes it exist");

                return true;
            }
        }
        Log.i("pageDataType", "no it doesnt exist");

        return false;
    }

    public void performAlbumSearch() {
        PAGE_NAME = "GroupExistingAlbumTop";
        //  etMUserAlbumSearch.clearFocus();

        ArrayList<DataAlbum> listFromDB = mDataSource.getAllAlbumByNameFrag(
                PreferencesConfig.getUserIDPreference(getContext()),
                PAGE_NAME, getKeywordSearch, null, GroupAlbumFragment.this, null);

        if(listFromDB.size()>0) {

            topAdapter = new DataAlbumAdapter(getContext(), listFromDB, null, null, GroupAlbumFragment.this, null);
            topAdapter.notifyDataSetChanged();
            rvExistingAlbum.setAdapter(topAdapter);
        }
        Log.i("SIZE", "SIZE: " + listFromDB.size());
    }
}