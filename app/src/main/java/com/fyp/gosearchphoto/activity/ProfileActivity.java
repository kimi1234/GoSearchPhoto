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
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.Utilities;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnChangePassword;
    private Button btnProfileDeleteAccount;
    private Button btnChangePassSave;
    private ImageView btnProfileClose;
    private TextView tvProfileValidation;


    private EditText etProfileEmail;
    private EditText etProfileUsername;
    private EditText etProfilePassword;

    String apiUpdateProfile;

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
                String getUsername = etProfileUsername.getText().toString().trim();
                String getPassword = etProfilePassword.getText().toString().trim();
                String getEmail = etProfileEmail.getText().toString().trim();
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

                                Utilities.displayToast(this, "Update Profile Successful");
                                Utilities.displayToast(this, apiUpdateProfile);

                            startActivity(new Intent(ProfileActivity.this, TabActivity.class));

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

    }
}