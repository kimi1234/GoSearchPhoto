package com.fyp.gosearchphoto.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.fyp.gosearchphoto.activity.ManageGroupActivity;
import com.fyp.gosearchphoto.database.CDataSource;
import com.fyp.gosearchphoto.model.DataGroupAdapter;
import com.fyp.gosearchphoto.model.DataUser;
import com.fyp.gosearchphoto.model.DataUserAdapter;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class GroupProfileFragment extends Fragment implements View.OnClickListener {

    private String getGroupName;
    private int getGroupID;

    private EditText etGroupProfileName;
    private Button btnDeptProfileSave;
    private ImageButton ibGroupProfileUserAdd;

    private RecyclerView rvGroupUser;
    private String PAGE_NAME  = "GroupProfile";

    private CDataSource mDataSource;
    private DataUserAdapter adapter;
    public Context mContext;

    //  Dialog Button
    private EditText etGUserSearch;
    private EditText etGroupName;
    private RecyclerView rvExistingUsers;
    private RecyclerView rvToAddGUsers;
    private Button dialogButtonCancel;
    private Button dialogButtonOK;
    private Button btnDeptProfileDelete;


    private List<DataUser> topDataItems;
    private DataUserAdapter topAdapter;

    private DataUserAdapter bottomAdapter;
    public List<DataUser> bottomDataItems;

    private int getCompanyId;
    private String getKeywordSearch;

    public GroupProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentPRIC
        View vGroupProfile = inflater.inflate(R.layout.fragment_group_profile, container, false);
        etGroupProfileName = (EditText) vGroupProfile.findViewById(R.id.etGroupProfileName);
        btnDeptProfileSave = (Button) vGroupProfile.findViewById(R.id.btnDeptProfileSave);
        btnDeptProfileDelete = (Button) vGroupProfile.findViewById(R.id.btnDeptProfileDelete);
        ibGroupProfileUserAdd = (ImageButton) vGroupProfile.findViewById(R.id.ibGroupProfileUserAdd);
        rvGroupUser = (RecyclerView) vGroupProfile.findViewById(R.id.rvGroupUser);

        ibGroupProfileUserAdd.setOnClickListener(this);
        btnDeptProfileSave.setOnClickListener(this);

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            getGroupName = extras.getString(DataGroupAdapter.ITEM_GROUP_NAME);
            getGroupID = extras.getInt(DataGroupAdapter.ITEM_GROUP_ID);


            etGroupProfileName.setText(getGroupName);
            btnDeptProfileSave.setText("Update");

        }else{
            etGroupProfileName.setText("");
            btnDeptProfileSave.setText("Create");
        }
        mContext = getContext();

        initializeGroupUserList();

        return vGroupProfile;
    }

    private void initializeGroupUserList() {

        mDataSource = CDataSource.getInstance(mContext);

        List<DataUser> listFromDB1 = mDataSource.getAllGroupUserByGroupId(getGroupID, PAGE_NAME);
        // Customize Adapter
        adapter = new DataUserAdapter(getContext(), listFromDB1, GroupProfileFragment.this, null);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        rvGroupUser.setAdapter(adapter);

        // Hide keyboard
        Utilities.hideKeyboardNow(getActivity().getWindow());
    }


    public  void deleteGroupUser(String username, int userid){
        Utilities.displayToast(mContext, "USER NAME: "+ username+" ID: "+userid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDeptProfileSave:
                Utilities.displayToast(getContext(), "Update button clicked");

                break;
            case R.id.ibGroupProfileUserAdd:
                Utilities.displayToast(getContext(), "Add button clicked");
                showAddGroupUsers(getContext());
                break;

            case R.id.btnDeptProfileDelete:
                AlertDialog.Builder builder;

                builder = new AlertDialog.Builder(mContext,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

                builder.setTitle("Delete group")

                        .setMessage("Are you sure you want to delete this group?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                startActivity(new Intent(getActivity(), ManageGroupActivity.class));
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.problem_alert)
                        .show();
                break;


        }
    }


    public void showAddGroupUsers(Context context){
        CDataSource mDataSource_popup;

        final Dialog album_dialog = new Dialog(context);
        album_dialog.setContentView(R.layout.popup_creategroup);
        album_dialog.setTitle("Add Existing Album");

        etGUserSearch = (EditText)album_dialog.findViewById(R.id.etGUserSearch);
        etGroupName = (EditText)album_dialog.findViewById(R.id.etGroupName);
        rvExistingUsers = (RecyclerView) album_dialog.findViewById(R.id.rvExistingUsers);
        rvToAddGUsers = (RecyclerView) album_dialog.findViewById(R.id.rvToAddGUsers);
        dialogButtonCancel = (Button) album_dialog.findViewById(R.id.dialog_btn_cancel);
        dialogButtonOK = (Button) album_dialog.findViewById(R.id.dialog_btn_ok);

        initializeUserList();


        album_dialog.show();
        //  Set dialog width to fill parent and height to wrap content
        album_dialog.getWindow()
                .setAttributes(Utilities.setPopUpWidth(album_dialog));
        instantiateBottomList();

        etGroupName.setVisibility(View.GONE);
        etGUserSearch.setOnClickListener(this);
        etGUserSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getKeywordSearch = etGUserSearch.getText().toString().trim();
                    Utilities.displayToast(getContext(), "search clicked");
                    performUserSearch();
                    Utilities.hideKeyboard(getActivity());

                    return true;
                }

                return false;
            }
        });
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
    }

    private void initializeUserList() {
        getCompanyId = PreferencesConfig.getCompanyIdPreference(getContext());

        mDataSource = CDataSource.getInstance(getContext());
        PAGE_NAME = "CreateGroupUserTop";

        topDataItems = mDataSource.getAllUserByCompanyId(getCompanyId, PAGE_NAME);


        // Customize Adapter
        topAdapter = new DataUserAdapter(getContext(), topDataItems, GroupProfileFragment.this, null);
        Log.i("get companyId", "" +getCompanyId);
        Log.i("get count", "" + adapter.getItemCount());
        topAdapter.notifyDataSetChanged();
        rvExistingUsers.setAdapter(topAdapter);

        // Hide keyboard
        Utilities.hideKeyboardNow(getActivity().getWindow());
    }


    private void instantiateBottomList(){
        bottomDataItems = new ArrayList<>();
    }
    public void addbottomItem(DataUser item){
        bottomDataItems.add(item);
        bottomAdapter= new DataUserAdapter(getContext(), bottomDataItems, GroupProfileFragment.this, null);
        bottomAdapter.notifyDataSetChanged();
        rvToAddGUsers.setAdapter(bottomAdapter);
    }

    public void addtopItem(DataUser item){
        topDataItems.add(item);
        topAdapter= new DataUserAdapter(getContext(), topDataItems, GroupProfileFragment.this, null);
        topAdapter.notifyDataSetChanged();
        rvExistingUsers.setAdapter(topAdapter);
    }


    public void getBottomListValues(){
        for(DataUser b : bottomDataItems) {
            Log.i("getUserId",""+b.getUserId());
            Log.i("getFullName",""+b.getFullName());
            Log.i("getCompanyId",""+ b.getCompanyId());
            Log.i("getPage_data_type", b.getPage_data_type());
            Log.i("~~~~~~","~~~~~~~~~~~~");
            //update, check for collisions, etc
        }
    }

    public boolean checkBottomItemExists(int userID){
        for (DataUser item : bottomDataItems) {
            if (item.getUserId()==userID) {
                return true;
            }
        }
        return false;
    }

    private void performUserSearch(){
        PAGE_NAME ="CreateGroupUserTop";
        //  etMUserAlbumSearch.clearFocus();
        getKeywordSearch = etGUserSearch.getText().toString().trim();

        topDataItems = mDataSource.getUsersByNameFrag(getKeywordSearch, PreferencesConfig.getCompanyIdPreference(getContext()), PAGE_NAME, GroupProfileFragment.this, null);

        topAdapter= new DataUserAdapter(getContext(), topDataItems, GroupProfileFragment.this, null);
        topAdapter.notifyDataSetChanged();
        rvExistingUsers.setAdapter(topAdapter);
        Log.i("SIZE", "SIZE: "+topDataItems.size());
    }
}
