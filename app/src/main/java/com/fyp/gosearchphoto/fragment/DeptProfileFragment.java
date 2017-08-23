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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.activity.ManageDepartmentActivity;
import com.fyp.gosearchphoto.model.DataDepartmentAdapter;
import com.fyp.gosearchphoto.model.DataStatus;
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;


public class DeptProfileFragment extends Fragment implements View.OnClickListener {

    private EditText etDeptProfileName,
            etDeptProfileDesc;

    private Button btnDeptProfileSave,
            btnDeptProfileDelete;
    private String dept_name, dept_desc;
    private String getDept_name, getDept_desc;
    private int company_id, dept_id;
    private Context mContext;

    private TextView tvDeptProfileValidation;

    public DeptProfileFragment() {
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
        View vDeptProfile = inflater.inflate(R.layout.fragment_dept_profile, container, false);

        etDeptProfileName = (EditText) vDeptProfile.findViewById(R.id.etDeptProfileName);
        etDeptProfileDesc = (EditText) vDeptProfile.findViewById(R.id.etDeptProfileDesc);
        btnDeptProfileSave = (Button) vDeptProfile.findViewById(R.id.btnDeptProfileSave);
        btnDeptProfileDelete = (Button) vDeptProfile.findViewById(R.id.btnDeptProfileDelete);
        tvDeptProfileValidation = (TextView) vDeptProfile.findViewById(R.id.tvDeptProfileValidation);
        btnDeptProfileSave.setOnClickListener(this);
        mContext = getContext();
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            dept_name = extras.getString(DataDepartmentAdapter.ITEM_DEPT_NAME);
            dept_desc = extras.getString(DataDepartmentAdapter.ITEM_DEPT_DESC);
            //company_id = extras.getInt(DataDepartmentAdapter.ITEM_DEPT_COMPANYID);
            etDeptProfileName.setText(dept_name);
            etDeptProfileDesc.setText(dept_desc);

            btnDeptProfileSave.setText("Update");
            btnDeptProfileDelete.setVisibility(View.VISIBLE);

        } else {
            btnDeptProfileDelete.setVisibility(View.GONE);
            btnDeptProfileSave.setText("Create");

        }

        tvDeptProfileValidation.setVisibility(View.INVISIBLE);
        Utilities.hideKeyboardNow(getActivity().getWindow());

        registerBroadcast();

        return vDeptProfile;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDeptProfileSave:
                registerDepartmentNow();

                break;

            case R.id.btnDeptProfileDelete:
                AlertDialog.Builder builder;

                builder = new AlertDialog.Builder(mContext,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

                builder.setTitle("Delete department")

                        .setMessage("Are you sure you want to delete this department?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                startActivity(new Intent(getActivity(), ManageDepartmentActivity.class));
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

    private void registerDepartmentNow(){
        getDept_name =  etDeptProfileName.getText().toString().trim();
        getDept_desc = etDeptProfileDesc.getText().toString().trim();
        if (Utilities.checkIsNull(getDept_name) == true || Utilities.checkIsNull(getDept_desc)==true) {
            tvDeptProfileValidation.setText("Please enter all fields");
            tvDeptProfileValidation.setVisibility(View.VISIBLE);
        }else {
            tvDeptProfileValidation.setVisibility(View.INVISIBLE);

            if (btnDeptProfileSave.getText().equals("Create")) {
                APIManager.getCreateDepartment(mContext,
                        PreferencesConfig.getUserIDPreference(mContext),
                        PreferencesConfig.getCompanyIdPreference(mContext),
                        getDept_name,
                        getDept_desc);

            } else if (btnDeptProfileSave.getText().equals("Update")) {


            }
        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {


                case ServiceHelper.PAYLOAD_CREATE_DEPARTMENT:
                    DataStatus duData1 = (DataStatus) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);
                    duData1.toString();
                    // Update Successful
                    if (duData1.getStatus().equals("department already exist")) {
                        tvDeptProfileValidation.setVisibility(View.VISIBLE);
                        tvDeptProfileValidation.setText("Department name already exist. Please try again");
                    }else if(duData1.getStatus().equals("success")){
                        Utilities.displayToast(mContext, "Department successfully created");
                        startActivity(new Intent(mContext, ManageDepartmentActivity.class));
                    }else{
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }


                    break;
            }
        }

    };
    public void registerBroadcast() {
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_MANAGE_DEPARTMENT_PROFILE);
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }


}