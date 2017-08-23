package com.fyp.gosearchphoto.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.model.DataStatus;
import com.fyp.gosearchphoto.model.DataUserAdapter;
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

public class ChangeEPassActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnChangeEPassSave;
    private ImageView btnChangeEPassClose;
    private EditText etcp_newEPassword;
    private EditText etcp_confirmNewEPassword;
    private EditText etcp_adminPassword;

    private TextView tvChangeEPassValidation;
    private Context mContext;

    private String getENewPass, getECNewPassword, getAdminPass;
    private int getUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_epass);

        mContext = this;
        btnChangeEPassClose = (ImageView)findViewById(R.id.btnChangeEPassClose);
        btnChangeEPassSave = (Button)findViewById(R.id.btnChangeEPassSave);

        etcp_newEPassword = (EditText)findViewById(R.id.etcp_newEPassword);
        etcp_confirmNewEPassword = (EditText)findViewById(R.id.etcp_confirmNewEPassword);
        etcp_adminPassword = (EditText)findViewById(R.id.etcp_adminPassword);

        tvChangeEPassValidation = (TextView)findViewById(R.id.tvChangeEPassValidation);


        btnChangeEPassClose.setOnClickListener(this);
        btnChangeEPassSave.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        getUserID=0;
        if (extras != null) {
            getUserID = extras.getInt(DataUserAdapter.ITEM_USER_ID);
        }

        initializePage();
        registerBroadcast();

    }

    private void initializePage() {
        etcp_newEPassword.setText("");
        etcp_confirmNewEPassword.setText("");
        etcp_adminPassword.setText("");
        tvChangeEPassValidation.setVisibility(View.INVISIBLE);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnChangeEPassClose:
                startActivity(new Intent(ChangeEPassActivity.this, TabMUsersActivity.class));

                break;
            case R.id.btnChangeEPassSave:

                getENewPass = etcp_newEPassword.getText().toString().trim();
                getECNewPassword = etcp_confirmNewEPassword.getText().toString().trim();
                getAdminPass = etcp_adminPassword.getText().toString().trim();
                int getAdminID = PreferencesConfig.getUserIDPreference(mContext);  //get this in the db next time


                if(Utilities.checkIsNull(getENewPass)== true
                        || Utilities.checkIsNull(getECNewPassword)== true
                        || Utilities.checkIsNull(getAdminPass)== true)
                {
                    tvChangeEPassValidation.setText("Please enter all fields");
                    tvChangeEPassValidation.setVisibility(View.VISIBLE);

                }else{
                    if(getENewPass.equals(getECNewPassword)) {

                        changeEPasswordNow(getUserID,getENewPass, getAdminID,getAdminPass);
                        tvChangeEPassValidation.setVisibility(View.INVISIBLE);
                    }else{

                        tvChangeEPassValidation.setText("Confirm password is not the same. Please try again");
                        tvChangeEPassValidation.setVisibility(View.VISIBLE);
                    }
                }



                break;

        }
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
                case ServiceHelper.PAYLOAD_CHANGE_USER_PASSWORD:

                    DataStatus du = (DataStatus) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if(du.getStatus().equals("wrong password")){

                        tvChangeEPassValidation.setText("Authentication failed: Wrong admin password. Please try again");
                        tvChangeEPassValidation.setVisibility(View.VISIBLE);
                        etcp_newEPassword.setText("");
                        etcp_confirmNewEPassword.setText("");
                        etcp_adminPassword.setText("");

                    }else if(du.getStatus().equals("success")){
                        tvChangeEPassValidation.setVisibility(View.GONE);

                        etcp_newEPassword.setText("");
                        etcp_confirmNewEPassword.setText("");
                        etcp_adminPassword.setText("");
                        startActivity(new Intent(ChangeEPassActivity.this, ManageUsersActivity.class));

                        Utilities.displayToast(mContext, "User password successfully changed");

                        Log.i("ProfileTAG", "It should openActivity class");

                    }else{
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;

            }
        }

    };

    public void changeEPasswordNow(int userID, String newPass, int adminID, String adminPass){
        APIManager.getChangeUserPasswordAPI(mContext, userID, newPass, adminID, adminPass);
    }

    public void registerBroadcast(){
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_CHANGE_EMPLOYEE_PASSWORD);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }
}
