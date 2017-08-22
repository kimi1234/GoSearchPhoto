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
import com.fyp.gosearchphoto.utils.Utilities;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegister;
    final private String TAG= "RegisterActivity";

    private ImageView btnRegisterClose;
    private EditText etRegEmail;
    private EditText etRegPassword;
    private EditText etRegConfirmPassword;
    private EditText etRegFullname;
    private TextView tvRegisterValidation;

    String getEmail;
    String getFullname;
    String getPassword;
    String getConfirmPassword;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = (EditText) findViewById(R.id.etRegEmail);
        etRegPassword = (EditText) findViewById(R.id.etRegPass);
        etRegConfirmPassword = (EditText) findViewById(R.id.etRegConfirmPass);
        etRegFullname = (EditText) findViewById(R.id.etRegFullName);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegisterClose = (ImageView) findViewById(R.id.btnRegisterClose);
        tvRegisterValidation = (TextView) findViewById(R.id.tvRegisterValidation);

        mContext = this;
        initializeRegisterPage();
        registerBroadcast();


        btnRegister.setOnClickListener(this);
        btnRegisterClose.setOnClickListener(this);
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
                case ServiceHelper.PAYLOAD_CHECKUSER_EXIST:
                    DataUser du = (DataUser) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);
                    if (du.getStatus().equals("user does not exist")){

                        Intent registerIntent = new Intent(RegisterActivity.this, RegisterCompanyActivity.class);
                        registerIntent.putExtra("full_name", getFullname);
                        registerIntent.putExtra("email", getEmail);
                        registerIntent.putExtra("password", getPassword);

                        startActivity(registerIntent);
                        Utilities.displayToast(mContext, du.getStatus());



                    }else if(du.getStatus().equals("user exist")){
                        Utilities.displayToast(mContext, "Email already exists. Please try again");
                    }else{
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }


                break;
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:

                // String getUsername =

                getEmail = etRegEmail.getText().toString().trim();
                getFullname = etRegFullname.getText().toString().trim();
                getPassword = etRegPassword.getText().toString().trim();
                getConfirmPassword = etRegConfirmPassword.getText().toString().trim();

                if (Utilities.checkIsNull(getEmail) == true
                        || Utilities.checkIsNull(getFullname) == true
                        || Utilities.checkIsNull(getPassword) == true
                        || Utilities.checkIsNull(getConfirmPassword) == true) {
                    tvRegisterValidation.setText("Please enter all fields");
                    tvRegisterValidation.setVisibility(View.VISIBLE);

                } else {
                    if (Utilities.isValidEmail(getEmail) == true) {
                        if (getPassword.equals(getConfirmPassword)) {

                            //TODO: CALL API
                            registerNow(getEmail);
                            tvRegisterValidation.setVisibility(View.GONE);
                        } else {

                            //    Utilities.displayToast(this, getPassword +"is not equal"+getConfirmPassword);

                            tvRegisterValidation.setText("Password is not the same. Please try again");
                            tvRegisterValidation.setVisibility(View.VISIBLE);
                        }
                    } else {

                        tvRegisterValidation.setText("Invalid Email. Please try again");
                        tvRegisterValidation.setVisibility(View.VISIBLE);

                    }
                }

                break;
            case R.id.btnRegisterClose:
                //  startActivity(new Intent(RegisterActivity.this, RegisterCompanyActivity.class));

                startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                break;

        }
    }

    public void initializeRegisterPage() {
        etRegEmail.setText("");
        etRegFullname.setText("");
        etRegPassword.setText("");
        etRegConfirmPassword.setText("");
        tvRegisterValidation.setVisibility(View.GONE);
    }

    public void registerBroadcast(){
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_REGISTER_ADMIN);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }
    private void registerNow(String email) {
        Log.i(TAG, "register now called");
        APIManager.checkUserExist(mContext, email);

    }


}
