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
import com.fyp.gosearchphoto.model.DataDepartmentAdapter;
import com.fyp.gosearchphoto.model.DataUser;
import com.fyp.gosearchphoto.model.DataUserAdapter;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.ArrayList;
import java.util.List;


public class DeptUserFragment extends Fragment implements View.OnClickListener {
    private ImageButton ibDeptUserAdd;
    private RecyclerView rvAddedDeptUsers;


    //  Dialog Button
    private EditText etDUserSearch;
    private EditText etGroupName;
    private RecyclerView rvExistingUsers;
    private RecyclerView rvToAddDUsers;
    private Button dialogButtonCancel;
    private Button dialogButtonOK;

    private List<DataUser> topDataItems;
    private DataUserAdapter topAdapter;

    private DataUserAdapter bottomAdapter;
    public List<DataUser> bottomDataItems;

    private int getDepartmentId;
    private String getKeywordSearch;
    private RecyclerView rvGroupUser;
    private String PAGE_NAME  = "DepartmentUser";

    private CDataSource mDataSource;
    private DataUserAdapter adapter;
    public Context mContext;


    public DeptUserFragment() {
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
        View vDeptUser = inflater.inflate(R.layout.fragment_dept_user, container, false);
        ibDeptUserAdd = (ImageButton) vDeptUser.findViewById(R.id.ibDeptUserAdd);
        rvAddedDeptUsers = (RecyclerView) vDeptUser.findViewById(R.id.rvAddedDeptUsers);
        getDepartmentId= 0;
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            getDepartmentId = extras.getInt(DataDepartmentAdapter.ITEM_DEPT_ID);
            initializeDepartmentUserList();

        }
            ibDeptUserAdd.setOnClickListener(this);

        return vDeptUser;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibDeptUserAdd:
                Utilities.displayToast(getContext(), "Add button clicked");
                showAddDepartmentUsers(getContext());

                break;


        }
    }

    public void showAddDepartmentUsers(Context context){
        CDataSource mDataSource_popup;

        final Dialog album_dialog = new Dialog(context);
        album_dialog.setContentView(R.layout.popup_create_dept_user);
        album_dialog.setTitle("Add Existing Album");

        etDUserSearch = (EditText)album_dialog.findViewById(R.id.etDUserSearch);
        etGroupName = (EditText)album_dialog.findViewById(R.id.etDepartmentName);
        rvExistingUsers = (RecyclerView) album_dialog.findViewById(R.id.rvExistingUsers);
        rvToAddDUsers = (RecyclerView) album_dialog.findViewById(R.id.rvToAddDUsers);
        dialogButtonCancel = (Button) album_dialog.findViewById(R.id.dialog_btn_cancel);
        dialogButtonOK = (Button) album_dialog.findViewById(R.id.dialog_btn_ok);

        initializeTopUserList();


        album_dialog.show();
        //  Set dialog width to fill parent and height to wrap content
        album_dialog.getWindow()
                .setAttributes(Utilities.setPopUpWidth(album_dialog));
        instantiateBottomList();

        etGroupName.setVisibility(View.GONE);
        etDUserSearch.setOnClickListener(this);
        etDUserSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getKeywordSearch = etDUserSearch.getText().toString().trim();
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
    private void instantiateBottomList(){
        bottomDataItems = new ArrayList<DataUser>();
    }
    public void getBottomListValues(){
        for(DataUser b : bottomDataItems) {
            Log.i("getUserId",""+b.getUserId());
            Log.i("getFullName",""+b.getFullName());
            Log.i("getDeptId",""+getDepartmentId);
            //update, check for collisions, etc
        }
    }

    private void initializeDepartmentUserList() {
        mDataSource = CDataSource.getInstance(mContext);
        if(getDepartmentId!= 0) {
        PAGE_NAME = "DepartmentUser";
        List<DataUser> listFromDB1 = mDataSource.getAllUserByDepartmentId(getDepartmentId, PAGE_NAME);
        // Customize Adapter
        adapter = new DataUserAdapter(getContext(), listFromDB1, null, DeptUserFragment.this);
        Log.i("get count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        rvAddedDeptUsers.setAdapter(adapter);

        // Hide keyboard
        Utilities.hideKeyboardNow(getActivity().getWindow());
    }
    }



    private void initializeTopUserList() {

            int getCompanyId = PreferencesConfig.getCompanyIdPreference(getContext());
            mDataSource = CDataSource.getInstance(getContext());
            PAGE_NAME = "CreateDeptUserTop";

            topDataItems = mDataSource.getAllUserByCompanyId(getCompanyId, PAGE_NAME);


            // Customize Adapter
            topAdapter = new DataUserAdapter(getContext(), topDataItems, null, DeptUserFragment.this);
        //    Log.i("get count", "" + adapter.getItemCount());
            topAdapter.notifyDataSetChanged();
            rvExistingUsers.setAdapter(topAdapter);


        // Hide keyboard
        Utilities.hideKeyboardNow(getActivity().getWindow());
    }
    private void performUserSearch(){
        PAGE_NAME ="CreateDeptUserTop";
        //  etMUserAlbumSearch.clearFocus();
        getKeywordSearch = etDUserSearch.getText().toString().trim();

        topDataItems = mDataSource.getUsersByNameFrag(getKeywordSearch, PreferencesConfig.getCompanyIdPreference(getContext()), PAGE_NAME, null, DeptUserFragment.this);

        topAdapter = new DataUserAdapter(getContext(), topDataItems, null, DeptUserFragment.this);
        topAdapter.notifyDataSetChanged();
        rvExistingUsers.setAdapter(topAdapter);
        Log.i("SIZE", "SIZE: "+topDataItems.size());
        Log.i("keyword", getKeywordSearch);
    }
    public boolean checkBottomItemExists(int userID){
        for (DataUser item : bottomDataItems) {
            if (item.getUserId()==userID) {
                return true;
            }
        }
        return false;
    }
    public void addtopItem(DataUser item){
        topAdapter.notifyDataSetChanged();

        topDataItems.add(item);
        topAdapter= new DataUserAdapter(getContext(), topDataItems, null, DeptUserFragment.this);
        topAdapter.notifyDataSetChanged();
        rvExistingUsers.setAdapter(topAdapter);
    }

    public void addbottomItem(DataUser item){
        bottomDataItems.add(item);
        bottomAdapter= new DataUserAdapter(getContext(), bottomDataItems, null, DeptUserFragment.this);
        bottomAdapter.notifyDataSetChanged();
        rvToAddDUsers.setAdapter(bottomAdapter);
    }
}

