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
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

public class CompanyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner staticSpinner;
    private ImageView btnCProfileClose;


    private EditText etCProfileName;
    private EditText etCProfileDesc;
    private TextView tvCompanyProfileValidation;
    private Button btnUpdateCompany;
    private Context mContext;

    // get data from this activity
    private String getCompanyName;
    private String getIndustry;
    private String getDescription;

    private String apiUpdateCProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        mContext = this;
        staticSpinner = (Spinner) findViewById(R.id.spinnerIndustry);
        btnCProfileClose = (ImageView) findViewById(R.id.btnCompanyProfileClose);
        btnUpdateCompany = (Button) findViewById(R.id.btnUpdateCProfile);

        etCProfileName = (EditText) findViewById(R.id.etCProfileName);
        etCProfileDesc = (EditText) findViewById(R.id.etCProfileDesc);
        tvCompanyProfileValidation = (TextView) findViewById(R.id.tvCompanyProfileValidation);
        tvCompanyProfileValidation.setVisibility(View.GONE);
        initSpinnerIndustry();
        registerBroadcast();
        getCompanyInfoNow();

    }

    private void initSpinnerIndustry() {
        //TODO Set industry default when connected to db
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.industry_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        btnCProfileClose.setOnClickListener(this);
        btnUpdateCompany.setOnClickListener(this);

        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                getIndustry = staticSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  Auto-generated method stub
            }
        });



    }

    private void getCompanyInfoNow() {
        APIManager.getCompanyInfo(mContext, PreferencesConfig.getCompanyIdPreference(mContext));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCompanyProfileClose:
                startActivity(new Intent(CompanyProfileActivity.this, TabActivity.class));
                break;

            case R.id.btnUpdateCProfile:
                updateCProfileNow();
                break;

        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

            switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
                case ServiceHelper.PAYLOAD_GET_COMPANY:

                    DataCompany dc = (DataCompany) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if (dc.getStatus().equals("success")) {
                        int userId = dc.getUser_id();
                        String companyName = dc.getCompanyname();
                        String companyDesc = dc.getDesc();
                        String industry = dc.getIndustry();
                        etCProfileDesc.setText(companyDesc);
                        etCProfileName.setText(companyName);
                        //Set Preferences
                        Utilities.selectSpinnerValue(staticSpinner, industry);
                        getIndustry = industry;
                        Log.i("ManageCOMPANY", dc.toString());

                        Log.i("ManageCOMPANY", "It should openActivity class");

                    } else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;
                case ServiceHelper.PAYLOAD_UPDATE_COMPANY:

                    DataCompany dc1 = (DataCompany) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                    if (dc1.getStatus().equals("success")) {
                        Utilities.displayToast(mContext, "Company information update successful");


                    } else if (dc1.getStatus().equals("company already exist")){
                        Utilities.displayToast(mContext, "Update fail. Company already exist");

                    }else {
                        Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                    }

                    break;

            }
        }

    };


    public void registerBroadcast() {
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_MANAGE_COMPANY);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }

    private void updateCProfileNow() {
        getCompanyName = etCProfileName.getText().toString().trim();
        getDescription = etCProfileDesc.getText().toString().trim();


        if (Utilities.checkIsNull(getCompanyName) == true ||Utilities.checkIsNull(getDescription)) {
            tvCompanyProfileValidation.setText("Please enter all fields");
            tvCompanyProfileValidation.setVisibility(View.VISIBLE);
        } else {
            //TODO:  pass company_id to aPI

            APIManager.getUpdateCProfileAPI( mContext,
                    PreferencesConfig.getCompanyIdPreference(mContext),
                    getCompanyName,
                    getIndustry,
                    getDescription);
            //Utilities.displayToast(this, "Registration Successful");
            //Utilities.displayToast(this, apiUpdateCProfile);


        }
    }
}
