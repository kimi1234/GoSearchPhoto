package com.fyp.gosearchphoto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.database.CDataSource;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

public class ChangePassActivity extends AppCompatActivity implements View.OnClickListener{

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

        btnChangePassClose = (ImageView)findViewById(R.id.btnChangePassClose);
        btnChangePassSave = (Button)findViewById(R.id.btnChangePassSave);

        etcp_oldPassword = (EditText)findViewById(R.id.etcp_oldPassword);
        etcp_newPassword = (EditText)findViewById(R.id.etcp_newPassword);
        etcp_confirmNewPassword = (EditText)findViewById(R.id.etcp_confirmNewPassword);

        tvChangePassValidation = (TextView)findViewById(R.id.tvChangePassValidation);
        initializeChangePasswordPage();
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
                int getUserID = 3;  //get this in the db next time


                if(Utilities.checkIsNull(getOldPassword)== true
                        || Utilities.checkIsNull(getNewPassword)== true
                        || Utilities.checkIsNull(getNewConfirmPassword)== true)
                {
                    tvChangePassValidation.setText("Please enter all fields");
                    tvChangePassValidation.setVisibility(View.VISIBLE);

                }else{
                    if(getNewPassword.equals(getNewConfirmPassword)) {
                        apiChangePassword = APIManager.getChangePasswordAPI(getUserID, getNewPassword);

                        Utilities.displayToast(this, apiChangePassword);
                        checkPassword();
                        tvChangePassValidation.setVisibility(View.GONE);
                    }else{

                        //    Utilities.displayToast(this, getPassword +"is not equal"+getConfirmPassword);

                        tvChangePassValidation.setText("Password is not the same. Please try again");
                        tvChangePassValidation.setVisibility(View.VISIBLE);
                    }
                }



                break;

        }
    }

    public  void initializeChangePasswordPage(){
        etcp_oldPassword.setText("");
        etcp_newPassword.setText("");
        etcp_confirmNewPassword.setText("");
        tvChangePassValidation.setVisibility(View.GONE);

    }

    public void checkPassword(){
        String getPreferencePass = PreferencesConfig.getPasswordPreference(this).toString().trim();
        int getUserId = PreferencesConfig.getUserIDPreference(this);


        if(getPreferencePass.equals(getOldPassword)) {
            startActivity(new Intent(ChangePassActivity.this, LogInActivity.class));

            CDataSource.getInstance(this)
                    .updatePassword(getNewPassword
                            , getUserId );
            Utilities.displayToast(this, "Change Successful");
        }else{
            Utilities.displayToast(this, "Wrong password, Please try again");
            initializeChangePasswordPage();

        }
    }
}
