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
import com.fyp.gosearchphoto.utils.Utilities;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRegister;

    private ImageView btnRegisterClose;
    private EditText etRegEmail;
    private EditText etRegPassword;
    private EditText etRegConfirmPassword;
    private EditText etRegUsername;
    private TextView tvRegisterValidation;

    String getEmail;
    String getUsername;
    String getPassword;
    String getConfirmPassword;
    String apiRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = (EditText)findViewById(R.id.etRegEmail);
        etRegPassword = (EditText)findViewById(R.id.etRegPass);
        etRegConfirmPassword = (EditText)findViewById(R.id.etRegConfirmPass);
        etRegUsername = (EditText)findViewById(R.id.etRegUsername);

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegisterClose = (ImageView)findViewById(R.id.btnRegisterClose);
        tvRegisterValidation = (TextView)findViewById(R.id.tvRegisterValidation);

        initializeRegisterPage();

        
        

        btnRegister.setOnClickListener(this);
        btnRegisterClose.setOnClickListener(this);
    }




    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:

               // String getUsername =

                getEmail = etRegEmail.getText().toString().trim();
                getUsername = etRegUsername.getText().toString().trim();
                getPassword = etRegPassword.getText().toString().trim();
                getConfirmPassword = etRegConfirmPassword.getText().toString().trim();
                
                if(Utilities.checkIsNull(getEmail)== true
                        || Utilities.checkIsNull(getUsername)== true
                        || Utilities.checkIsNull(getPassword)== true
                        || Utilities.checkIsNull(getConfirmPassword)== true)
                {
                    tvRegisterValidation.setText("Please enter all fields");
                    tvRegisterValidation.setVisibility(View.VISIBLE);

                }else {
                    if (Utilities.isValidEmail(getEmail) == true) {
                            if(getPassword.equals(getConfirmPassword)) {
                                apiRegister = APIManager.getRegisterAPI(getUsername, getEmail, getPassword);

                                Utilities.displayToast(this, "Registration Successful");
                                Utilities.displayToast(this, apiRegister);
                                registerNow( getUsername,  getEmail, getPassword);
                                tvRegisterValidation.setVisibility(View.GONE);
                            }else{

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
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                break;

        }
    }
    public  void initializeRegisterPage(){
        etRegEmail.setText("");
        etRegUsername.setText("");
        etRegPassword.setText("");
        etRegConfirmPassword.setText("");
        tvRegisterValidation.setVisibility(View.GONE);

    }
    public void registerNow(String userName, String email, String password){
        final CDataSource databaseAccess = CDataSource.getInstance(this);
        if(databaseAccess.checkUsernameExists(userName)){
            Utilities.displayToast(this, "user already exists. Please try again");

        }else{
            databaseAccess.insertUser(userName, email, password);

            startActivity(new Intent(RegisterActivity.this, MainActivity.class));

            Utilities.displayToast(this, "user registration success");

        }
    }


}
