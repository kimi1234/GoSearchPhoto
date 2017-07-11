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
import com.fyp.gosearchphoto.model.DataUser;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnChangePassword = (Button)findViewById(R.id.btnProfileChangePass);
        btnProfileDeleteAccount = (Button)findViewById(R.id.btnProfileDeleteAccount);
        btnChangePassSave = (Button)findViewById(R.id.btnProfileSave);
        btnProfileClose = (ImageView)findViewById(R.id.btnProfileClose);
        tvProfileValidation = (TextView)findViewById(R.id.tvProfileValidation);

        etProfileUsername = (EditText)findViewById(R.id.etProfileUsername);
        etProfileEmail = (EditText)findViewById(R.id.etProfileEmail);
        etProfilePassword = (EditText)findViewById(R.id.etProfilePassword);

        initializeLogInPage();
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
                int userID = 1;   //later get user id in db

                if(Utilities.checkIsNull(getUsername)==true ||Utilities.checkIsNull(getEmail)==true ){
                    tvProfileValidation.setText("Please enter all fields");
                    tvProfileValidation.setVisibility(View.VISIBLE);
                }else{
                    if(Utilities.checkIsNull(getPassword)==true){
                        tvProfileValidation.setText("Please enter password for validation");
                        tvProfileValidation.setVisibility(View.VISIBLE);
                    }else{
                        if (Utilities.isValidEmail(getEmail) == true) {
                                apiUpdateProfile = APIManager.getUpdateProfileAPI(getUsername, getEmail, userID);

                                Utilities.displayToast(this, apiUpdateProfile);
                                checkPassword();

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

    public  void initializeLogInPage(){
        etProfileEmail.setText("");
        etProfilePassword.setText("");
        etProfileUsername.setText("");
        tvProfileValidation.setVisibility(View.GONE);
        getUserInfoNow();
    }

    public void getUserInfoNow(){
        getUserId = PreferencesConfig.getUserIDPreference(this);
         listFromDB =  CDataSource.getInstance(this)
                .getUserInfoByID(getUserId);

         etProfileEmail.setText(listFromDB.get(0).getEmail());
         etProfileUsername.setText(listFromDB.get(0).getUserName());
    }

    public void checkPassword(){
        String getPreferencePass = PreferencesConfig.getPasswordPreference(this).toString().trim();
        String getPassEditor =  etProfilePassword.getText().toString().trim();


        if(getPreferencePass.equals(getPassEditor)) {
            startActivity(new Intent(ProfileActivity.this, TabActivity.class));

            CDataSource.getInstance(this)
                    .updateUserInfo(getUsername,
                            getEmail, getUserId );
            Utilities.displayToast(this, "Profile Update Successful");
        }else{
            Utilities.displayToast(this, "Wrong password, Please try again");
            Utilities.displayToast(this, PreferencesConfig.getPasswordPreference(this)+"is not equal to"+etProfilePassword.getText().toString());
            etProfilePassword.setText("");

        }
    }
}