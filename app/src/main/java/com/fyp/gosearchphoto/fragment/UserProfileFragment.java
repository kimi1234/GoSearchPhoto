package com.fyp.gosearchphoto.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
import com.fyp.gosearchphoto.activity.ManageUsersActivity;
import com.fyp.gosearchphoto.model.DataDepartment;
import com.fyp.gosearchphoto.model.DataStatus;
import com.fyp.gosearchphoto.model.DataUserAdapter;
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private Spinner spinnerUserProfileDept;
    private Button btnUserProfileRegister,
            btnUserProfileDeleteAcct, btnUserProfileCP;

    private EditText etUserProfileFullName,
            etUserProfileEmail, etUserProfilePass,
            etUserProfileCPass;

    private String user_fullname,
            user_email, user_pass,
            user_type, user_dept;

    private String getETFullname, getETEmail, getSPINdepartment,
    getETPassword, getETCPassword;

    private TextView tvUserProfileValidation;
    private int user_id, user_cid;

    private ArrayList<DataDepartment> deptList;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vUserProfile = inflater.inflate(R.layout.fragment_user_profile, container, false);

        etUserProfileFullName = (EditText) vUserProfile.findViewById(R.id.etUserProfileFullName);
        etUserProfileEmail = (EditText) vUserProfile.findViewById(R.id.etUserProfileEmail);
        etUserProfilePass = (EditText) vUserProfile.findViewById(R.id.etUserProfilePass);
        etUserProfileCPass = (EditText) vUserProfile.findViewById(R.id.etUserProfileCPass);


        btnUserProfileRegister = (Button) vUserProfile.findViewById(R.id.btnUserProfileRegister);
        btnUserProfileCP = (Button) vUserProfile.findViewById(R.id.btnUserProfileCP);
        btnUserProfileDeleteAcct = (Button) vUserProfile.findViewById(R.id.btnUserProfileDeleteAcct);
        spinnerUserProfileDept = (Spinner) vUserProfile.findViewById(R.id.spinnerUserProfileDept);
        tvUserProfileValidation = (TextView) vUserProfile.findViewById(R.id.tvUserProfileValidation);

        btnUserProfileDeleteAcct.setOnClickListener(this);
        btnUserProfileRegister.setOnClickListener(this);
        btnUserProfileCP.setOnClickListener(this);
        mContext = getContext();
        user_dept = "";
        etUserProfileFullName.setText("");
        etUserProfileEmail.setText("");
        etUserProfilePass.setText("");
        etUserProfileCPass.setText("");


        spinnerUserProfileDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                getSPINdepartment = spinnerUserProfileDept.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  Auto-generated method stub
            }
        });
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            user_fullname = extras.getString(DataUserAdapter.ITEM_USER_FULLNAME);
            // doSomething

            user_id = extras.getInt(DataUserAdapter.ITEM_USER_ID);
            user_email = extras.getString(DataUserAdapter.ITEM_USER_EMAIL);
            user_type = extras.getString(DataUserAdapter.ITEM_USER_TYPE);
            user_cid = extras.getInt(DataUserAdapter.ITEM_USER_COMPANYID);
            user_dept = extras.getString(DataUserAdapter.ITEM_USER_DEPARTMENT);
            //The key argument here must match that used in the other activity

            etUserProfileFullName.setText(user_fullname);
            etUserProfileEmail.setText(user_email);
            btnUserProfileRegister.setText("Update");
            btnUserProfileDeleteAcct.setVisibility(View.VISIBLE);
            btnUserProfileCP.setVisibility(View.VISIBLE);
            etUserProfilePass.setVisibility(View.GONE);
            etUserProfileCPass.setVisibility(View.GONE);
            registerBroadcast();
            getDepartmentListNow();
        } else {
            user_id=0;
            btnUserProfileRegister.setText("Register");

            btnUserProfileDeleteAcct.setVisibility(View.GONE);
            btnUserProfileCP.setVisibility(View.GONE);

            etUserProfilePass.setVisibility(View.VISIBLE);
            etUserProfileCPass.setVisibility(View.VISIBLE);

        }
        tvUserProfileValidation.setVisibility(View.INVISIBLE);


        Utilities.hideKeyboardNow(getActivity().getWindow());

        // Inflate the layout for this fragment
        return vUserProfile;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Do your Work
            if (user_id != 0) {
                registerBroadcast();
                getDepartmentListNow();
            }
        } else {
            // Do your Work
            LocalBroadcastManager.getInstance(mContext)
                    .unregisterReceiver(mBroadcastReceiver);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUserProfileDeleteAcct:


                break;
            case R.id.btnUserProfileRegister:
                updateUProfileNow();
                break;

            case R.id.btnUserProfileCP:
                break;
        }
    }

    public void updateUProfileNow() {
        getETFullname = etUserProfileFullName.getText().toString().trim();
        getETEmail = etUserProfileEmail.getText().toString().trim();
        getETPassword =etUserProfilePass.getText().toString().trim();
        getETCPassword = etUserProfileCPass.getText().toString().trim();

        if (Utilities.checkIsNull(getETFullname) == true || Utilities.checkIsNull(getETEmail)==true) {
            tvUserProfileValidation.setText("Please enter all fields");
            tvUserProfileValidation.setVisibility(View.VISIBLE);
        } else {

            if (Utilities.isValidEmail(getETEmail) == true) {

                if (btnUserProfileRegister.getText().equals("Update")) {
                    //TODO:  pass company_id to aPI
                    tvUserProfileValidation.setVisibility(View.INVISIBLE);

                    APIManager.getUpdateUserProfile(mContext,
                            getSPINdepartment,
                            "Employee",
                            getETFullname,
                            getETEmail,
                            user_id
                    );
                } else if (btnUserProfileRegister.getText().equals("Register")) {

                    if (Utilities.checkIsNull(getETPassword) == true || Utilities.checkIsNull(getETCPassword)==true) {
                        tvUserProfileValidation.setText("Please enter all fields");
                        tvUserProfileValidation.setVisibility(View.VISIBLE);
                    }else{
                        if (getETPassword.equals(getETCPassword)) {

                            //TODO: CALL API
                            registerNow(getETEmail);
                            tvUserProfileValidation.setVisibility(View.INVISIBLE);
                        } else {
                            tvUserProfileValidation.setText("Password is not the same. Please try again");
                            tvUserProfileValidation.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } else {
                tvUserProfileValidation.setText("Invalid Email. Please try again");
                tvUserProfileValidation.setVisibility(View.VISIBLE);

            }
        }

    }

    private void registerNow(String email) {
        Log.i("UserProfile", "register now called");
        APIManager.checkUserExist(mContext, email);

    }
    private void getDepartmentListNow() {
        APIManager.getCompanyDepartment(mContext, PreferencesConfig.getCompanyIdPreference(mContext), "all");
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {


                case ServiceHelper.PAYLOAD_GET_COMPANY_DEPARTMENTS:

                    DataDepartment ddata = (DataDepartment) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if (ddata.getStatus().equals("no data")) {
                        Utilities.displayToast(mContext, "Please create department first to create user");
                        startActivity(new Intent(mContext, ManageUsersActivity.class));

                    } else if (ddata.getStatus().equals("success")) {
                        List<DataDepartment> listFromServer = ddata.getDepartmentList();
                        Log.i("to string ", ddata.toString());
                        List<String> lables = new ArrayList<String>();

                        for (int i = 0; i < listFromServer.size(); i++) {
                            lables.add(listFromServer.get(i).getName());
                            Log.i("Dept name", listFromServer.get(i).getName() + " ");

                        }

                        // Creating adapter for spinner
                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_dropdown_item, lables);

                        // Drop down layout style - list view with radio button
                        spinnerAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        spinnerUserProfileDept.setAdapter(spinnerAdapter);
                        if (!user_dept.equals("")) {
                            Utilities.selectSpinnerValue(spinnerUserProfileDept, user_dept);
                        }

                    } else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;
                case ServiceHelper.PAYLOAD_UPDATE_USER_PROFILE:

                    DataStatus duData = (DataStatus) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    // Update Successful
                    if (duData.getStatus().equals("user does not exist")) {
                        Utilities.displayToast(mContext, "User update successful");
                    } else if (duData.getStatus().equals("email already exist") || duData.getStatus().equals("user exist")) {
                        Utilities.displayToast(mContext, "Email already exist. Please try again");
                    } else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }


                    break;
                case ServiceHelper.PAYLOAD_REGISTER_COMPANY_USER:

                    DataStatus duData1 = (DataStatus) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    // Update Successful
                    if (duData1.getStatus().equals("success")) {
                        Utilities.displayToast(mContext, "User successfully created");
                        startActivity(new Intent(mContext, ManageUsersActivity.class));
                    }else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }


                    break;
                case ServiceHelper.PAYLOAD_CHECKUSER_EXIST:
                    DataStatus du = (DataStatus) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);
                    if (du.getStatus().equals("user does not exist")){

                       APIManager.getRegisterCompanyUser(mContext,
                               getSPINdepartment,
                               "Employee",
                               getETFullname,
                               getETCPassword,
                               getETEmail,
                               PreferencesConfig.getCompanyIdPreference(mContext)
                       );


                    }else if(du.getStatus().equals("user exist")){
                        tvUserProfileValidation.setText("Email already exists. Please try again");
                        tvUserProfileValidation.setVisibility(View.VISIBLE);
                    }else{
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }


                    break;


            }
        }

    };


    public void registerBroadcast() {
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_MANAGE_USER_PROFILE);
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }


}
