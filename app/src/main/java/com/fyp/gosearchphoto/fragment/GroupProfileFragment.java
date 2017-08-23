package com.fyp.gosearchphoto.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
import com.fyp.gosearchphoto.model.DataGroup;
import com.fyp.gosearchphoto.model.DataGroupAdapter;
import com.fyp.gosearchphoto.model.DataStatus;
import com.fyp.gosearchphoto.model.DataUser;
import com.fyp.gosearchphoto.model.DataUserAdapter;
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupProfileFragment extends Fragment implements View.OnClickListener {

    private String getGroupName;
    private int getGroupID;

    private EditText etGroupProfileName;
    private Button btnGroupProfileSave;
    private ImageButton ibGroupProfileUserAdd;

    private RecyclerView rvGroupUser;
    private String PAGE_NAME = "GroupProfile";

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
    private Button btnGroupProfileDelete;


    private List<DataUser> topDataItems;
    private DataUserAdapter topAdapter;
    private TextView tvGroupProfileValidation;

    private DataUserAdapter bottomAdapter;
    public List<DataUser> bottomDataItems;
    public List<DataUser> existingGroupUsers;


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
        btnGroupProfileSave = (Button) vGroupProfile.findViewById(R.id.btnGrpProfileSave);
        btnGroupProfileDelete = (Button) vGroupProfile.findViewById(R.id.btnGrpProfileDelete);
        ibGroupProfileUserAdd = (ImageButton) vGroupProfile.findViewById(R.id.ibGroupProfileUserAdd);
        rvGroupUser = (RecyclerView) vGroupProfile.findViewById(R.id.rvGroupUser);
        tvGroupProfileValidation = (TextView) vGroupProfile.findViewById(R.id.tvGroupProfileValidation);

        ibGroupProfileUserAdd.setOnClickListener(this);
        btnGroupProfileSave.setOnClickListener(this);
        PAGE_NAME = "GroupProfile";
        Bundle extras = getActivity().getIntent().getExtras();
        mContext = getContext();
        registerBroadcast();
        getGroupID = 0;
        if (extras != null) {
            getGroupName = extras.getString(DataGroupAdapter.ITEM_GROUP_NAME);
            getGroupID = extras.getInt(DataGroupAdapter.ITEM_GROUP_ID);


            etGroupProfileName.setText(getGroupName);
            btnGroupProfileSave.setText("Update");
            btnGroupProfileDelete.setVisibility(View.VISIBLE);
        } else {
            etGroupProfileName.setText("");
            btnGroupProfileSave.setText("Create");
            btnGroupProfileDelete.setVisibility(View.GONE);
        }
        if (getGroupID != 0) {
            initializeGroupUserList();
        }
        tvGroupProfileValidation.setVisibility(View.INVISIBLE);

        return vGroupProfile;
    }

    private void validateGroupInfo() {
        getGroupName = etGroupProfileName.getText().toString().trim();

        if (Utilities.checkIsNull(getGroupName) == true) {
            tvGroupProfileValidation.setText("Please enter group name");
            tvGroupProfileValidation.setVisibility(View.VISIBLE);
        } else {
            if (btnGroupProfileSave.getText().equals("Create")) {
                createGroupNow(getGroupName);
            } else if (btnGroupProfileSave.getText().equals("Update")) {
                updateGroupNow(getGroupName);
            }
        }
    }

    private void updateGroupNow(String grpname) {
        APIManager.getUpdateGroup(mContext,
                PreferencesConfig.getUserIDPreference(mContext),
                getGroupID,
                grpname
        );

    }

    private void createGroupNow(String grpname) {
        APIManager.getCreateGroup(mContext,
                grpname,
                PreferencesConfig.getUserIDPreference(mContext));

    }

    private void getCompanyUserListNow() {
        APIManager.getCompanyUsers(mContext,
                PreferencesConfig.getCompanyIdPreference(mContext),
                "all");
    }

    private void initializeGroupUserList() {

        APIManager.getGroupInfo(mContext,
                PreferencesConfig.getUserIDPreference(mContext),
                getGroupID
        );
        // Hide keyboard
        Utilities.hideKeyboardNow(getActivity().getWindow());
    }


    public void setGroupUserDataAdapter(List<DataUser> du) {
        // Customize Adapter
        existingGroupUsers = du;
        adapter = new DataUserAdapter(getContext(), du, GroupProfileFragment.this, null);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        rvGroupUser.setAdapter(adapter);

        // Hide keyboard
        Utilities.hideKeyboardNow(getActivity().getWindow());
    }


    public void deleteGroupUser(String username, int userid) {
        Utilities.displayToast(mContext, "USER NAME: " + username + " ID: " + userid);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGrpProfileSave:

                validateGroupInfo();
                break;
            case R.id.ibGroupProfileUserAdd:

                if (getGroupID != 0) {
                    showAddGroupUsers(mContext);
                } else {
                    Utilities.displayToast(mContext, "Please create group first.");
                }
                break;

            case R.id.btnGrpProfileDelete:
                AlertDialog.Builder builder;

                builder = new AlertDialog.Builder(mContext, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

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


    public void showAddGroupUsers(Context context) {
        bottomDataItems = new ArrayList<DataUser>();


        final Dialog album_dialog = new Dialog(context);
        album_dialog.setContentView(R.layout.popup_creategroup);
        album_dialog.setTitle("Add Existing Album");

        etGUserSearch = (EditText) album_dialog.findViewById(R.id.etGUserSearch);
        etGroupName = (EditText) album_dialog.findViewById(R.id.etGroupName);
        rvExistingUsers = (RecyclerView) album_dialog.findViewById(R.id.rvExistingUsers);
        rvToAddGUsers = (RecyclerView) album_dialog.findViewById(R.id.rvToAddGUsers);
        dialogButtonCancel = (Button) album_dialog.findViewById(R.id.dialog_btn_cancel);
        dialogButtonOK = (Button) album_dialog.findViewById(R.id.dialog_btn_ok);

        initializeUserList();


        album_dialog.show();
        //  Set dialog width to fill parent and height to wrap content
        album_dialog.getWindow()
                .setAttributes(Utilities.setPopUpWidth(album_dialog));

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
        PAGE_NAME = "CreateGroupUserTop";
        getCompanyUserListNow();
        // Hide keyboard
        Utilities.hideKeyboardNow(getActivity().getWindow());
    }

    public void setUserDataAdapter(List<DataUser> du) {
        // Customize Adapter

        topDataItems = du;
        topAdapter = new DataUserAdapter(getContext(), du, GroupProfileFragment.this, null);
        Log.i("get companyId", "" + getCompanyId);
        Log.i("get count", "" + topAdapter.getItemCount());
        topAdapter.notifyDataSetChanged();
        rvExistingUsers.setAdapter(topAdapter);

        // Hide keyboard
        Utilities.hideKeyboardNow(getActivity().getWindow());


    }


    public void addbottomItem(DataUser item) {
        bottomDataItems.add(item);
        bottomAdapter = new DataUserAdapter(getContext(), bottomDataItems, GroupProfileFragment.this, null);
        bottomAdapter.notifyDataSetChanged();
        rvToAddGUsers.setAdapter(bottomAdapter);
    }

    public void addtopItem(DataUser item) {
        topDataItems.add(item);
        topAdapter = new DataUserAdapter(getContext(), topDataItems, GroupProfileFragment.this, null);
        topAdapter.notifyDataSetChanged();
        rvExistingUsers.setAdapter(topAdapter);
    }


    public void getBottomListValues() {

        JSONObject groupUserObj = new JSONObject();
        try {
            groupUserObj.put("group_id", getGroupID);
            groupUserObj.put("owner_id", PreferencesConfig.getUserIDPreference(mContext));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();

        for (DataUser b : bottomDataItems) {
            JSONObject userObj = new JSONObject();
            try {
                userObj.put("user_id", b.getUser_id());
                jsonArray.put(userObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("getUserId", "" + b.getUser_id());
            Log.i("getFullName", "" + b.getFullname());
            Log.i("getCompanyId", "" + b.getCompany_id());
            Log.i("getPage_data_type", b.getPage_data_type());
            Log.i("~~~~~~", "~~~~~~~~~~~~");
            //update, check for collisions, etc
        }
        try {
            groupUserObj.put("userList", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        addGroupUsersNow(groupUserObj.toString());
        Log.i("TO ADD GROUP USER", groupUserObj.toString());
    }

    private void addGroupUsersNow(String jsonstr) {
        APIManager.getShareGroupToUser(mContext, jsonstr);
    }

    public boolean checkBottomItemExists(int userID) {
        for (DataUser item : bottomDataItems) {
            if (item.getUser_id() == userID) {
                return true;
            }
        }
        return false;
    }


    public boolean checkExistingUserItemExists(int userID) {
        for (DataUser item : existingGroupUsers) {
            if (item.getUser_id() == userID) {
                return true;
            }
        }
        return false;
    }


    public void performUserSearch() {
        PAGE_NAME = "CreateGroupUserTop";


        getKeywordSearch = etGUserSearch.getText().toString().trim();

        if (Utilities.checkIsNull(getKeywordSearch) == true) {
            getKeywordSearch = "all";
        }
        APIManager.getCompanyUsers(mContext, PreferencesConfig.getCompanyIdPreference(mContext), getKeywordSearch);

    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
                case ServiceHelper.PAYLOAD_CREATE_GROUP:

                    DataStatus duData1 = (DataStatus) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    // Update Successful
                    if (duData1.getStatus().equals("success")) {
                        Utilities.displayToast(mContext, "Album successfully created");
                        startActivity(new Intent(mContext, ManageGroupActivity.class));
                    } else if (duData1.getStatus().equals("group already exist")) {

                        tvGroupProfileValidation.setVisibility(View.VISIBLE);
                        tvGroupProfileValidation.setText("Group name already exist. Please try again");

                    } else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }


                    break;
                case ServiceHelper.PAYLOAD_UPDATE_GROUP:

                    DataStatus duData2 = (DataStatus) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    // Update Successful
                    if (duData2.getStatus().equals("success")) {
                        Utilities.displayToast(mContext, "Album successfully updated");
                        startActivity(new Intent(mContext, ManageGroupActivity.class));
                    } else if (duData2.getStatus().equals("no group found")) {

                        tvGroupProfileValidation.setVisibility(View.VISIBLE);
                        tvGroupProfileValidation.setText("Group not found. Please try again");
                        startActivity(new Intent(mContext, ManageGroupActivity.class));

                    } else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }


                    break;
                case ServiceHelper.PAYLOAD_GET_GROUP_INFO:
                    DataGroup du = (DataGroup) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if (du.getStatus().equals("no data")) {
                        Utilities.displayToast(mContext, "No group user found");

                    } else if (du.getStatus().equals("success")) {
                        List<DataGroup> listFromServer = du.getGroupUserList();
                        Log.i("to string ", du.toString());

                        // filter list and add pagename list
                        List<DataUser> dataItems = new ArrayList<>();

                        for (int i = 0; i < listFromServer.size(); i++) {
                            DataUser item = new DataUser();
                            item.setUser_id(listFromServer.get(i).getUser_id());
                            item.setFullname(listFromServer.get(i).getUserName());
                            item.setPage_data_type(PAGE_NAME);

                            dataItems.add(item);
                        }


                        setGroupUserDataAdapter(dataItems);

                    } else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }


                    break;
                case ServiceHelper.PAYLOAD_GET_COMPANY_USERS:

                    DataUser dusr = (DataUser) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if (dusr.getStatus().equals("no data")) {
                        Utilities.displayToast(mContext, "No user found");

                    } else if (dusr.getStatus().equals("success")) {
                        List<DataUser> listFromServer = dusr.getcUserList();
                        Log.i("to string ", listFromServer.toString());

                        // filter list and add pagename list
                        List<DataUser> dataItems = new ArrayList<>();

                        for (int i = 0; i < listFromServer.size(); i++) {
                            if (listFromServer.get(i).getType().equals("Employee")) {

                                if (checkBottomItemExists(listFromServer.get(i).getId()) == false && checkExistingUserItemExists(listFromServer.get(i).getId()) == false) {

                                    DataUser item = new DataUser();
                                    item.setUser_id(listFromServer.get(i).getId());
                                    item.setFullname(listFromServer.get(i).getFullName());
                                    item.setPage_data_type(PAGE_NAME);

                                    dataItems.add(item);
                                }

                            }
                        }

                        setUserDataAdapter(dataItems);

                    } else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;
                case ServiceHelper.PAYLOAD_SHARE_GROUP_TO_USER:

                    DataStatus dsItem = (DataStatus) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);
                    if (dsItem.getStatus().equals("success")) {
                        Utilities.displayToast(mContext, "User successfully added");
                    } else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;

            }
        }
    };

    public void registerBroadcast() {
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_MANAGE_GROUPS_PROFILE);
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Do your Work
            registerBroadcast();

        } else {

        }
    }
}
