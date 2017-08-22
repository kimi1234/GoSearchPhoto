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
import com.fyp.gosearchphoto.model.DataUser;
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

public class ChangePassActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    private ImageView btnChangePassClose;
    private Button btnChangePassSave;

    private EditText etcp_oldPassword;
    private EditText etcp_newPassword;
    private EditText etcp_confirmNewPassword;

    private TextView tvChangePassValidation;

    String getOldPassword;
    String getNewPassword;
    String getNewConfirmPassword;

    String apiChangePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        mContext = this;
        btnChangePassClose = (ImageView)findViewById(R.id.btnChangePassClose);
        btnChangePassSave = (Button)findViewById(R.id.btnChangePassSave);

        etcp_oldPassword = (EditText)findViewById(R.id.etcp_oldPassword);
        etcp_newPassword = (EditText)findViewById(R.id.etcp_newPassword);
        etcp_confirmNewPassword = (EditText)findViewById(R.id.etcp_confirmNewPassword);

        tvChangePassValidation = (TextView)findViewById(R.id.tvChangePassValidation);
        initializeChangePasswordPage();
        registerBroadcast();
        btnChangePassClose.setOnClickListener(this);
        btnChangePassSave.setOnClickListener(this);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnChangePassClose:
                startActivity(new Intent(ChangePassActivity.this, ProfileActivity.class));

                break;
            case R.id.btnChangePassSave:

                getOldPassword = etcp_oldPassword.getText().toString().trim();
                getNewPassword = etcp_newPassword.getText().toString().trim();
                getNewConfirmPassword = etcp_confirmNewPassword.getText().toString().trim();
                int getUserID = PreferencesConfig.getUserIDPreference(mContext);  //get this in the db next time


                if(Utilities.checkIsNull(getOldPassword)== true
                        || Utilities.checkIsNull(getNewPassword)== true
                        || Utilities.checkIsNull(getNewConfirmPassword)== true)
                {
                    tvChangePassValidation.setText("Please enter all fields");
                    tvChangePassValidation.setVisibility(View.VISIBLE);

                }else{
                    if(getNewPassword.equals(getNewConfirmPassword)) {

                        changePasswordNow(getUserID,getOldPassword, getNewPassword);
                        tvChangePassValidation.setVisibility(View.GONE);
                    }else{

                        //    Utilities.displayToast(this, getPassword +"is not equal"+getConfirmPassword);

                        tvChangePassValidation.setText("Confirm password is not the same. Please try again");
                        tvChangePassValidation.setVisibility(View.VISIBLE);
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
                case ServiceHelper.PAYLOAD_CHANGE_PASSWORD:

                    DataUser du = (DataUser) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                     if(du.getStatus().equals("wrong password")){

                         tvChangePassValidation.setText("Authentication failed: Wrong password. Please try again");
                         tvChangePassValidation.setVisibility(View.VISIBLE);
                         etcp_oldPassword.setText("");
                         etcp_newPassword.setText("");
                         etcp_confirmNewPassword.setText("");

                    }else if(du.getStatus().equals("success")){
                         tvChangePassValidation.setVisibility(View.GONE);

                         etcp_oldPassword.setText("");
                         etcp_newPassword.setText("");
                         etcp_confirmNewPassword.setText("");
                         startActivity(new Intent(ChangePassActivity.this, LogInActivity.class));

                         Utilities.displayToast(mContext, "Password successfully changed");

                        Log.i("ProfileTAG", "It should openActivity class");

                    }else{
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;

            }
        }

    };

    public void changePasswordNow(int userID, String oldPassword, String newPassword){
        APIManager.getChangePasswordAPI(mContext, userID, oldPassword, newPassword);
    }

    public void registerBroadcast(){
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_CHANGE_PASSWORD);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }


    public  void initializeChangePasswordPage(){
        etcp_oldPassword.setText("");
        etcp_newPassword.setText("");
        etcp_confirmNewPassword.setText("");
        tvChangePassValidation.setVisibility(View.GONE);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }

}
