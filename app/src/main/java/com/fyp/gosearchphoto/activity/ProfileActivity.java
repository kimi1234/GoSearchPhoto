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

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnChangePassword;
    private Button btnProfileDeleteAccount;
    private Button btnChangePassSave;
    private ImageView btnProfileClose;
    private TextView tvProfileValidation;

    List<DataUser> listFromDB;
    private EditText etProfileEmail;
    private EditText etProfileUsername;
    private EditText etProfilePassword;

    String apiUpdateProfile;
    String getUsername ;
    String getPassword ;
    String getEmail ;
    int getUserId;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mContext = this;
        btnChangePassword = (Button)findViewById(R.id.btnProfileChangePass);
        btnProfileDeleteAccount = (Button)findViewById(R.id.btnProfileDeleteAccount);
        btnChangePassSave = (Button)findViewById(R.id.btnProfileSave);
        btnProfileClose = (ImageView)findViewById(R.id.btnProfileClose);
        tvProfileValidation = (TextView)findViewById(R.id.tvProfileValidation);

        etProfileUsername = (EditText)findViewById(R.id.etProfileUsername);
        etProfileEmail = (EditText)findViewById(R.id.etProfileEmail);
        etProfilePassword = (EditText)findViewById(R.id.etProfilePassword);

        initializeLogInPage();
        registerBroadcast();
        btnChangePassword.setOnClickListener(this);
        btnChangePassSave.setOnClickListener(this);
        btnProfileClose.setOnClickListener(this);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnProfileSave:
                 getUsername = etProfileUsername.getText().toString().trim();
                 getPassword = etProfilePassword.getText().toString().trim();
                 getEmail = etProfileEmail.getText().toString().trim();
                int userID = PreferencesConfig.getUserIDPreference(mContext);

                if(Utilities.checkIsNull(getUsername)==true ||Utilities.checkIsNull(getEmail)==true ){
                    tvProfileValidation.setText("Please enter all fields");
                    tvProfileValidation.setVisibility(View.VISIBLE);
                }else{
                    if(Utilities.checkIsNull(getPassword)==true){
                        tvProfileValidation.setText("Please enter password for validation");
                        tvProfileValidation.setVisibility(View.VISIBLE);
                    }else{
                        if (Utilities.isValidEmail(getEmail) == true) {
                            updateProfileNow(getUsername, getEmail, getPassword, userID);

                        } else {
                            tvProfileValidation.setText("Invalid Email. Please try again");
                            tvProfileValidation.setVisibility(View.VISIBLE);
                        }
                    }
                }

                break;
            case R.id.btnProfileDeleteAccount:
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));

                break;

            case R.id.btnProfileClose:
                startActivity(new Intent(ProfileActivity.this, TabActivity.class));

                break;
            case R.id.btnProfileChangePass:
                startActivity(new Intent(ProfileActivity.this, ChangePassActivity.class));

                break;
        }
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
                case ServiceHelper.PAYLOAD_UPDATE_PROFILE:

                    DataUser du = (DataUser) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if (du.getStatus().equals("wrong password")){
                        tvProfileValidation.setText(du.getStatus()+". Please try again");
                        tvProfileValidation.setVisibility(View.VISIBLE);
                        etProfilePassword.setText("");

                    }else if(du.getStatus().equals("email already exist")){
                        tvProfileValidation.setText(du.getStatus()+". Please try again");
                        tvProfileValidation.setVisibility(View.VISIBLE);
                        etProfilePassword.setText("");

                    }else if(du.getStatus().equals("success")){
                        tvProfileValidation.setVisibility(View.GONE);

                        String email = getEmail;
                        String fullname = getUsername;

                        //Set Preferences
                        PreferencesConfig.setEmailPreference(email, mContext);
                        PreferencesConfig.setFullnamePreference(fullname, mContext);

                        startActivity(new Intent(ProfileActivity.this, TabActivity.class));
                        Utilities.displayToast(mContext, "Profile Update Successful");

                        Log.i("ProfileTAG", "It should openActivity class");

                    }else{
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;

            }
        }

    };
    public void registerBroadcast(){
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_PROFILE);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }
    public  void initializeLogInPage(){
        etProfileEmail.setText("");
        etProfilePassword.setText("");
        etProfileUsername.setText("");
        tvProfileValidation.setVisibility(View.GONE);
        getUserInfoNow();
    }

    public void getUserInfoNow(){
        getUserId = PreferencesConfig.getUserIDPreference(mContext);
        String fullname = PreferencesConfig.getFullnamePreference(mContext);
        String email = PreferencesConfig.getEmailPreference(mContext);
        String usertype = PreferencesConfig.getUserTypePreference(mContext);
            etProfileEmail.setText(email);
            etProfileUsername.setText(fullname);
        Log.i("PROFILE USER TYPE:", usertype);

        if(usertype.equals("Admin")){
            btnChangePassSave.setVisibility(View.VISIBLE);
            btnProfileDeleteAccount.setVisibility(View.VISIBLE);
            etProfilePassword.setVisibility(View.VISIBLE);

            Utilities.enableEditText(etProfileEmail);
            Utilities.enableEditText(etProfileUsername);

        }else if(usertype.equals("Employee")){
            btnChangePassSave.setVisibility(View.GONE);
            btnProfileDeleteAccount.setVisibility(View.GONE);
            etProfilePassword.setVisibility(View.GONE);
            Utilities.disableEditText(etProfileEmail);
            Utilities.disableEditText(etProfileUsername);
        }


    }

    public void updateProfileNow(String username, String email, String pass, int userid){
        APIManager.getUpdateAdminProfileAPI(mContext, username, email, pass, userid);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }

}