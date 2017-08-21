package com.fyp.gosearchphoto.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.activity.ManageDepartmentActivity;
import com.fyp.gosearchphoto.model.DataDepartmentAdapter;
import com.fyp.gosearchphoto.utils.Utilities;


public class DeptProfileFragment extends Fragment implements View.OnClickListener {

    private EditText etDeptProfileName,
            etDeptProfileDesc;

    private Button btnDeptProfileSave,
            btnDeptProfileDelete;
    private String dept_name, dept_desc;
    private int company_id, dept_id;
    private Context mContext;

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
        btnDeptProfileSave.setOnClickListener(this);
        mContext = getContext();
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            dept_name = extras.getString(DataDepartmentAdapter.ITEM_DEPT_NAME);
            dept_desc = extras.getString(DataDepartmentAdapter.ITEM_DEPT_DESC);
            company_id = extras.getInt(DataDepartmentAdapter.ITEM_DEPT_COMPANYID);
            dept_id = extras.getInt(DataDepartmentAdapter.ITEM_DEPT_ID);

            etDeptProfileName.setText(dept_name);
            etDeptProfileDesc.setText(dept_desc);

            btnDeptProfileSave.setText("Update");

        } else {
            btnDeptProfileSave.setText("Create");

        }

        return vDeptProfile;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDeptProfileSave:
                Utilities.displayToast(getContext(), "Saved");
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

}