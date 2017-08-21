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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.model.DataCompany;
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.Utilities;

public class RegisterCompanyActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;
    private Spinner staticSpinner;
    private ImageView btnRegCProfileClose;
    private Button btnGoBackCompany;
    private Button btnRegisterCompany;
    private EditText etRegCProfileName;
    private EditText etRegCProfileDesc;
    private TextView tvRegCompanyProfileValidation;

    //Got from register activity
    private String apiRegister;
    private String getFullname;
    private String getEmail;
    private String getPassword;

    // get data from this activity
    private String getCompanyName;
    private String getIndustry;
    private String getDescription;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        staticSpinner = (Spinner) findViewById(R.id.spinnerIndustryReg);
        btnRegCProfileClose = (ImageView)findViewById(R.id.btnRegCompanyProfileClose);
        btnGoBackCompany = (Button)findViewById(R.id.btnGoBackCompany);
        btnRegisterCompany = (Button)findViewById(R.id.btnRegisterCompany);
        etRegCProfileName = (EditText)findViewById(R.id.etRegCProfileName);
        etRegCProfileDesc = (EditText)findViewById(R.id.etRegCProfileDesc);
        tvRegCompanyProfileValidation = (TextView)findViewById(R.id.tvRegCompanyProfileValidation);
        mContext = this;

        initSpinnerIndustry();
        registerBroadcast();
        // Hide validation
        tvRegCompanyProfileValidation.setVisibility(View.GONE);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getFullname = extras.getString("full_name");
             getEmail = extras.getString("email");
             getPassword = extras.getString("password");
            //The key argument here must match that used in the other activity
            Utilities.displayToast(this, getFullname +" "+getEmail+" "+getPassword);
        }

    }

    private void initSpinnerIndustry() {
        getIndustry = "Others";
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.industry_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        btnRegCProfileClose.setOnClickListener(this);
        btnGoBackCompany.setOnClickListener(this);
        btnRegisterCompany.setOnClickListener(this);

        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                getIndustry=staticSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  Auto-generated method stub
            }
        });
        //Set default value for spinner;
        Utilities.selectSpinnerValue(staticSpinner, "Others");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegCompanyProfileClose:
                startActivity(new Intent(RegisterCompanyActivity.this, MainActivity.class));
                break;
            case R.id.btnGoBackCompany:
                startActivity(new Intent(RegisterCompanyActivity.this, RegisterActivity.class));

                break;

            case R.id.btnRegisterCompany:
                checkCompanyExist();

                break;
        }
    }

    public void checkCompanyExist(){


        getCompanyName = etRegCProfileName.getText().toString().trim();
        getDescription = etRegCProfileDesc.getText().toString().trim();


        if(Utilities.checkIsNull(getCompanyName)== true){
            tvRegCompanyProfileValidation.setText("Please Enter Company Name");
            tvRegCompanyProfileValidation.setVisibility(View.VISIBLE);
        }else{

            checkCompanyNameExist(getEmail);
        }
    }

    public void checkCompanyNameExist(String email){
        APIManager.checkCompanyExist(mContext, email);

    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
                case ServiceHelper.PAYLOAD_CHECKCOMPANY_EXIST:
                    DataCompany du = (DataCompany) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);
                    if (du.getStatus().equals("company does not exist")){
                        Utilities.displayToast(mContext, du.getStatus());
                        registerAdminNow();

                    }else if(du.getStatus().equals("company does exist")){
                        Utilities.displayToast(mContext, "Company Name already exists. Please try again");
                    }else{
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;
                case ServiceHelper.PAYLOAD_REGISTER_ADMIN:
                    DataCompany du1 = (DataCompany) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);
                    if (du1.getStatus().equals("success")){
                        Utilities.displayToast(mContext, "Registration Successful");
                        startActivity(new Intent(RegisterCompanyActivity.this, MainActivity.class));
                    }else if (du1.getStatus().equals("fail")){
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }
                    break;
            }
        }

    };
    public void registerBroadcast(){
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_REGISTER_COMPANY);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }
    public void registerAdminNow(){
        APIManager.getRegisterAPI(mContext,
                                "Admin",
                                getFullname,
                                getEmail,
                                getPassword,
                                getCompanyName,
                                getIndustry,
                                getDescription);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }


}
