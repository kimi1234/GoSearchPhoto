package com.fyp.gosearchphoto.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.database.CDataSource;
import com.fyp.gosearchphoto.model.DataDepartment;
import com.fyp.gosearchphoto.model.DataUserAdapter;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment implements View.OnClickListener {


    private Spinner spinnerUserProfileDept;
    private Button btnUserProfileRegister,
            btnUserProfileDeleteAcct, btnUserProfileCP;

    private EditText etUserProfileFullName,
            etUserProfileEmail;

    private String user_fullname,
            user_email, user_pass,
            user_type, user_dept;

    private int user_id, user_cid;


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
        View vUserProfile =  inflater.inflate(R.layout.fragment_user_profile, container, false);

        etUserProfileFullName = (EditText) vUserProfile.findViewById(R.id.etUserProfileFullName);
        etUserProfileEmail = (EditText) vUserProfile.findViewById(R.id.etUserProfileEmail);
        btnUserProfileRegister = (Button) vUserProfile.findViewById(R.id.btnUserProfileRegister);
        btnUserProfileCP = (Button) vUserProfile.findViewById(R.id.btnUserProfileCP);
        btnUserProfileDeleteAcct = (Button) vUserProfile.findViewById(R.id.btnUserProfileDeleteAcct);
        spinnerUserProfileDept = (Spinner) vUserProfile.findViewById(R.id.spinnerUserProfileDept);

        btnUserProfileDeleteAcct.setOnClickListener(this);
        btnUserProfileRegister.setOnClickListener(this);
        btnUserProfileCP.setOnClickListener(this);

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            user_fullname = extras.getString(DataUserAdapter.ITEM_USER_FULLNAME);
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


           /* DataUser du = new DataUser();
            du.setUserId(Integer.parseInt(user_id));
            */

/*
           Log.i("user_fullname", user_fullname);
            Log.i("user_id", "USERID "+user_id);
            Log.i("user_email", user_email);
            Log.i("user_type", user_type);
            Log.i("user_dept", user_dept);
            Log.i("user_cid", "COMPANYID"+user_cid);

*/


        }else{

            btnUserProfileRegister.setText("Register");

            btnUserProfileDeleteAcct.setVisibility(View.GONE);
        }

        initSpinnerDepartment();
        Utilities.hideKeyboardNow(getActivity().getWindow());

        // Inflate the layout for this fragment
        return vUserProfile;


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUserProfileDeleteAcct:


                break;
            case R.id.btnUserProfileRegister:
                break;

            case R.id.btnUserProfileCP:
                break;
        }
    }
    private void initSpinnerDepartment() {
        List<String> lables = new ArrayList<String>();

        CDataSource mDataSource = CDataSource.getInstance(getContext());

        List<DataDepartment> listFromDB2 = mDataSource.getAllDeptItems(PreferencesConfig.getUserIDPreference(getContext()));


        for (int i = 0; i < listFromDB2.size(); i++) {
            lables.add(listFromDB2.get(i).getDepartment_name());
        }


        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerUserProfileDept.setAdapter(spinnerAdapter);

    }





}
