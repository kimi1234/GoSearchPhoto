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
import com.fyp.gosearchphoto.model.DataDepartmentAdapter;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.ArrayList;
import java.util.List;


public class DeptAlbumFragment extends Fragment implements View.OnClickListener {
    private ImageButton ibDeptAlbumAdd;
    private CDataSource mDataSource;
    private DataAlbumAdapter adapter;
    private RecyclerView mRecyclerView;
    private String user_fullname,
            user_email, user_pass,
            user_type, user_dept;

    private int user_id, user_cid;

    private int dept_id;

    private String PAGE_NAME;
    private String getKeywordSearch;
    public Context mContext;
    List<DataAlbum> topDataItems;
    DataAlbumAdapter topAdapter;

    //
    // Bottom items
    //
    public List<DataAlbum> bottomDataItems;
    private DataAlbumAdapter bottomAdapter;
    RecyclerView rvToAddAlbum;
    RecyclerView rvExistingAlbum;
    Button dialogButtonOK;
    TextView tvDeptTitle;
    EditText etMUserAlbumSearch;
    Button dialogButtonCancel;


    //private ImageButton
    public DeptAlbumFragment() {
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
        View vDeptAlbum = inflater.inflate(R.layout.fragment_dept_album, container, false);
        ibDeptAlbumAdd = (ImageButton) vDeptAlbum.findViewById(R.id.ibDepartmentAlbumAdd);
        mRecyclerView  = (RecyclerView)vDeptAlbum.findViewById(R.id.rvAddedDeptAlbum);

        ibDeptAlbumAdd.setOnClickListener(this);

        mContext = getContext();

        Bundle extras = getActivity().getIntent().getExtras();
        dept_id =0;
        if (extras != null) {
            dept_id = extras.getInt(DataDepartmentAdapter.ITEM_DEPT_ID);

            displayDeptAlbum();
        }

        return vDeptAlbum;
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ibDepartmentAlbumAdd:
               /*
                    String myTxt = String.valueOf(user_id);*/
                showAddAlbum(getContext());

                break;

        }

    }
    public void showAddAlbum(Context context) {
        CDataSource mDataSource_popup;

        final Dialog album_dialog = new Dialog(context);
        album_dialog.setContentView(R.layout.popup_add_existing_album);
        album_dialog.setTitle("Add Existing Album");

        tvDeptTitle = (TextView) album_dialog.findViewById(R.id.lblThirdLayerDepartmentTitle);
        etMUserAlbumSearch = (EditText)album_dialog.findViewById(R.id.etMUserAlbumSearch);
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
        if(user_dept !=null) {
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

    private void displayTopItemList() {
        CDataSource mDataSource_popup;
        mDataSource_popup = CDataSource.getInstance(getContext());
        PAGE_NAME ="DAExistingAlbumTop";
        topDataItems = mDataSource_popup.getAllAlbumItems(
                PreferencesConfig.getUserIDPreference(getContext()),
                PAGE_NAME);

        topAdapter = new DataAlbumAdapter(getContext(), topDataItems, null, null, null, DeptAlbumFragment.this);
        Log.i("get count", "" + topAdapter.getItemCount());
        topAdapter.notifyDataSetChanged();
        rvExistingAlbum.setAdapter(topAdapter);
    }

    public void displayDeptAlbum(){
        if(dept_id!=0) {
            mDataSource = CDataSource.getInstance(getContext());

            PAGE_NAME = "DepartmentAlbum";
            List<DataAlbum> listFromDB2 = mDataSource.getAlbumByDepartmentID(dept_id, PAGE_NAME);

            adapter = new DataAlbumAdapter(getContext(), listFromDB2, null, null, null, DeptAlbumFragment.this);
            Log.i("get count", "" + adapter.getItemCount());
            adapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(adapter);
        }

    }
    public void instantiateBottomList(){
        bottomDataItems = new ArrayList<>();
    }
    public void addbottomItem(DataAlbum item){
        bottomDataItems.add(item);
        bottomAdapter= new DataAlbumAdapter(getContext(), bottomDataItems, null, null, null, DeptAlbumFragment.this);
        bottomAdapter.notifyDataSetChanged();
        rvToAddAlbum.setAdapter(bottomAdapter);
    }

    public void addtopItem(DataAlbum item){
        Utilities.displayToast(getContext(), ""+topDataItems.size());

        if(topDataItems.size()==0){
            topAdapter = null;
        }
        topDataItems.add(item);
        topAdapter= new DataAlbumAdapter(getContext(), topDataItems, null, null, null, DeptAlbumFragment.this);
        topAdapter.notifyDataSetChanged();
        rvExistingAlbum.setAdapter(topAdapter);
    }

    public void getBottomListValues(){
        for(DataAlbum b : bottomDataItems) {
            Log.i("getAlbumId",""+b.getAlbumId());
            /*   Log.i("getOwner_id",""+b.getOwner_id());
            Log.i("getAlbum_name", b.getAlbum_name());
            Log.i("getOwner_name", b.getOwner_name());
            Log.i("getDescription", b.getDescription());
            Log.i("getPrivacy_type", b.getPrivacy_type());
            Log.i("setPage_data_type", b.getPrivacy_type());
            */
            Log.i("~~~~~~","~~~~~~~~~~~~");
            //update, check for collisions, etc
        }
    }

    public boolean checkBottomItemExists(int albumID){
        for (DataAlbum item : bottomDataItems) {
            if (item.getAlbumId()==albumID) {
                return true;
            }
        }
        return false;
    }

    public void performAlbumSearch(){
        PAGE_NAME ="DAExistingAlbumTop";
        //  etMUserAlbumSearch.clearFocus();


        ArrayList<DataAlbum> listFromDB = mDataSource.getAllAlbumByNameFrag(
                PreferencesConfig.getUserIDPreference(getContext()), // owner id
                PAGE_NAME,        //Page Data type
                getKeywordSearch, // Album name
                null, // UserAlbum Fragment
                null, //GroupAlbum Fragment
                DeptAlbumFragment.this // DepartmentAlbum Fragment
        );


        if(listFromDB.size()>0) {
            topAdapter = new DataAlbumAdapter(getContext(), listFromDB, null, null, null,DeptAlbumFragment.this);
            topAdapter.notifyDataSetChanged();
            rvExistingAlbum.setAdapter(topAdapter);
        }
        Log.i("SIZE", "SIZE: "+listFromDB.size());
    }
}

