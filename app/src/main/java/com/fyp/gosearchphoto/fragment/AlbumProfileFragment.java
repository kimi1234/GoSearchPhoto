package com.fyp.gosearchphoto.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.activity.ManageAlbumActivity;
import com.fyp.gosearchphoto.model.DataAlbumAdapter;
import com.fyp.gosearchphoto.model.DataStatus;
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

public class AlbumProfileFragment extends Fragment implements  View.OnClickListener {


    private String album_name, owner_name, description, privacy_type, defaultPrivacyType;
    private int owner_id, album_id;

    private EditText etAlbumProfileName;
    private EditText etAlbumProfileDesc;
    private TextView tvAlbumProfileValidation;
    private Spinner spinnerAlbumProfileType;
    private Button btnAlbumProfileSave;
    private Button btnAlbumProfileDelete ;
    private Context mContext;

    private String getAlbumName, getDescription, getPrivacyType;

    public AlbumProfileFragment() {
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
       View vAlbumProfile = inflater.inflate(R.layout.fragment_album_profile, container, false);


        tvAlbumProfileValidation = (TextView)vAlbumProfile.findViewById(R.id.tvAlbumProfileValidation);
        etAlbumProfileName = (EditText) vAlbumProfile.findViewById(R.id.etAlbumProfileName);
        etAlbumProfileDesc = (EditText) vAlbumProfile.findViewById(R.id.etAlbumProfileDesc);
        btnAlbumProfileSave = (Button) vAlbumProfile.findViewById(R.id.btnAlbumProfileSave);
        btnAlbumProfileDelete = (Button) vAlbumProfile.findViewById(R.id.btnAlbumProfileDelete);
        spinnerAlbumProfileType = (Spinner) vAlbumProfile.findViewById(R.id.spinnerAlbumProfileType);

        btnAlbumProfileSave.setOnClickListener(this);
        btnAlbumProfileDelete.setOnClickListener(this);
        mContext = getContext();
        Bundle extras = getActivity().getIntent().getExtras();

        registerBroadcast();
        if (extras != null) {

            album_name = extras.getString(DataAlbumAdapter.ITEM_ALBUM_NAME);
            owner_name = extras.getString(DataAlbumAdapter.ITEM_OWNER_NAME);
            description = extras.getString(DataAlbumAdapter.ITEM_DESCRIPTION);
            privacy_type = extras.getString(DataAlbumAdapter.ITEM_PRIVACY_TYPE);

            owner_id = extras.getInt(DataAlbumAdapter.ITEM_OWNER_ID);
            album_id = extras.getInt(DataAlbumAdapter.ITEM_ALBUM_ID);

            etAlbumProfileName.setText(album_name);
            etAlbumProfileDesc.setText(description);
            btnAlbumProfileSave.setText("Update");
            Utilities.displayToast(getContext(),privacy_type);
            defaultPrivacyType = privacy_type;
            btnAlbumProfileDelete.setVisibility(View.VISIBLE);

        }else{
            defaultPrivacyType = "Private";
            btnAlbumProfileSave.setText("Create");
            btnAlbumProfileDelete.setVisibility(View.GONE);


        }
        tvAlbumProfileValidation.setVisibility(View.INVISIBLE);

        initSpinnerIndustry();

        Utilities.hideKeyboardNow(getActivity().getWindow());

        return vAlbumProfile;
    }


    private void initSpinnerIndustry() {
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.privacy_type_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerAlbumProfileType.setAdapter(staticAdapter);


        spinnerAlbumProfileType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                defaultPrivacyType=spinnerAlbumProfileType.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  Auto-generated method stub
            }
        });
        //Set default value for spinner;
        Utilities.selectSpinnerValue(spinnerAlbumProfileType, defaultPrivacyType);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnAlbumProfileDelete:
                AlertDialog.Builder builder;

                    builder = new AlertDialog.Builder(mContext,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

                builder.setTitle("Delete album")

                        .setMessage("Are you sure you want to delete this album?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                startActivity(new Intent(getActivity(), ManageAlbumActivity.class));
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

            case R.id.btnAlbumProfileSave:
                validateAlbum();

            break;

        }


    }

    private void validateAlbum(){
        getAlbumName = etAlbumProfileName.getText().toString().trim();
        getDescription = etAlbumProfileDesc.getText().toString().trim();

        getPrivacyType = defaultPrivacyType;

        if (Utilities.checkIsNull(getAlbumName) == true || Utilities.checkIsNull(getDescription)==true) {
            tvAlbumProfileValidation.setText("Please enter all fields");
            tvAlbumProfileValidation.setVisibility(View.VISIBLE);
        }else {
            if (btnAlbumProfileSave.getText().equals("Create")) {
                createAlbumNow(getAlbumName, getDescription, getPrivacyType);
            } else if (btnAlbumProfileSave.getText().equals("Update")) {

            }
        }

    }

    private void createAlbumNow(String albumName, String desc,  String type) {
        Log.i("UserProfile", "register now called");
        APIManager.getCreateAlbum(mContext,
                PreferencesConfig.getUserIDPreference(mContext),
                albumName, desc, type,
                PreferencesConfig.getCompanyIdPreference(mContext));

    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
                case ServiceHelper.PAYLOAD_CREATE_ALBUM:

                    DataStatus duData1 = (DataStatus) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    // Update Successful
                    if (duData1.getStatus().equals("success")) {
                        Utilities.displayToast(mContext, "Album successfully created");
                        startActivity(new Intent(mContext, ManageAlbumActivity.class));
                    } else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }


                    break;
            }
        }
    };

    public void registerBroadcast(){
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_MANAGE_PROJECT_ALBUM_PROFILE);
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
