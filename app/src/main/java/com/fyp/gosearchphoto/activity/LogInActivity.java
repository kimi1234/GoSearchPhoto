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

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogin;
    private ImageView btnLogInClose;
    private TextView tvCreateAccount;
    private TextView tvLogInValidation;
    private EditText etUsername;
    private EditText etPassword;
    private Context mContext;
    private String LogInTAG  ="LogInActivity";
    String apiLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        btnLogin = (Button)findViewById(R.id.btnLogIn);
        btnLogInClose = (ImageView)findViewById(R.id.btnLogInClose);
        tvCreateAccount = (TextView)findViewById(R.id.tvCreateAccount);
        tvLogInValidation = (TextView)findViewById(R.id.tvLogInValidation);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etUsername = (EditText)findViewById(R.id.etUsername);
        mContext = this;
        initializeLogInPage();
        registerBroadcast();
        //TODO : Remove this on launch
        etPassword.setText("ana");
        etUsername.setText("ana@gmail.com");

        btnLogin.setOnClickListener(this);
        btnLogInClose.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogIn:
                String getEmail = etUsername.getText().toString().trim();
                String getPassword = etPassword.getText().toString().trim();
                if(Utilities.checkIsNull(getEmail)==true|| Utilities.checkIsNull(getPassword)== true){
                    tvLogInValidation.setText("Please enter all fields");
                    tvLogInValidation.setVisibility(View.VISIBLE);
                }else if (Utilities.isValidEmail(getEmail) == true) {
                    logInNow(getEmail, getPassword);

                }else{
                    tvLogInValidation.setText("Invalid Email. Please try again");
                    tvLogInValidation.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.tvCreateAccount:
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));

                break;

            case R.id.btnLogInClose:
                startActivity(new Intent(LogInActivity.this, MainActivity.class));

                break;
        }
    }
    public void registerBroadcast(){
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_LOGIN);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }

    public  void initializeLogInPage(){
        etUsername.setText("");
        etPassword.setText("");
        tvLogInValidation.setVisibility(View.GONE);
    }

    public void logInNow(String email, String password){
       APIManager.getLogInAPI(mContext, email, password);

    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
         //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
                case ServiceHelper.PAYLOAD_LOGIN:

                    DataUser du = (DataUser) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if (du.getStatus().equals("wrong password")){
                        tvLogInValidation.setText(du.getStatus()+". Please try again");
                        tvLogInValidation.setVisibility(View.VISIBLE);
                        etPassword.setText("");

                    }else if(du.getStatus().equals("user does not exist")){
                        tvLogInValidation.setText(du.getStatus()+". Please try again");
                        tvLogInValidation.setVisibility(View.VISIBLE);
                        etPassword.setText("");

                    }else if(du.getStatus().equals("success")){
                        int userId = du.getUserId();
                        String userType = du.getType();
                        int companyId = du.getCompanyId();
                        String department = du.getDepartmentName();
                        String email = du.getEmail();
                        String fullname = du.getFullName();

                        //Set Preferences
                        PreferencesConfig.setUserIDPreference(userId, mContext);
                        PreferencesConfig.setEmailPreference(email, mContext);
                        PreferencesConfig.setEmailPreference(fullname, mContext);
                        PreferencesConfig.setUserTypePreference(userType, mContext);
                        PreferencesConfig.setCompanyIdPreference(companyId, mContext);
                        PreferencesConfig.setDepartment(department, mContext);
                        Log.i(LogInTAG, du.toString());
                        startActivity(new Intent(LogInActivity.this, TabActivity.class));

                        Log.i(LogInTAG, "It should openActivity class");

                    }else{
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;

            }
        }

    };


}