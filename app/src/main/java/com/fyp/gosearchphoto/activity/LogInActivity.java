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

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogin;
    private ImageView btnLogInClose;
    private TextView tvCreateAccount;
    private TextView tvLogInValidation;
    private EditText etUsername;
    private EditText etPassword;

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
        initializeLogInPage();
        btnLogin.setOnClickListener(this);
        btnLogInClose.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogIn:
                String getUsername = etUsername.getText().toString().trim();
                String getPassword = etPassword.getText().toString().trim();
                if(Utilities.checkIsNull(getUsername)==true|| Utilities.checkIsNull(getPassword)== true){
                    tvLogInValidation.setText("Please enter all fields");
                    tvLogInValidation.setVisibility(View.VISIBLE);
                }else{

                    apiLogIn = APIManager.getLogInAPI(getUsername, getPassword);

                    Utilities.displayToast(this, apiLogIn);
                    logInNow(getUsername, getPassword);
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

    public  void initializeLogInPage(){
        etUsername.setText("");
        etPassword.setText("");
        tvLogInValidation.setVisibility(View.GONE);
    }

    public void logInNow(String userName, String password){
        final CDataSource databaseAccess = CDataSource.getInstance(this);
        if(databaseAccess.checkLogIn(userName, password)){
            startActivity(new Intent(LogInActivity.this, TabActivity.class));

            List<DataUser> listFromDB = databaseAccess.getUser(userName);
            int userId = listFromDB.get(0).getUserId();
            Utilities.displayToast(this, "user exists userID: "+userId);

            PreferencesConfig.setUserIDPreference(userId, this);
            PreferencesConfig.setPasswordPreference(password, this);

        }else{
            Utilities.displayToast(this, "user does not exists, please try again");

        }
    }
}