package com.fyp.gosearchphoto.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.Utilities;

public class CompanyProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner staticSpinner;
    private ImageView btnCProfileClose;


    private EditText etCProfileName;
    private EditText etCProfileDesc;
    private TextView tvCompanyProfileValidation;
    private Button btnUpdateCompany;

    // get data from this activity
    private String getCompanyName;
    private String getIndustry;
    private String getDescription;

    private String apiUpdateCProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        staticSpinner = (Spinner) findViewById(R.id.spinnerIndustry);
        btnCProfileClose = (ImageView)findViewById(R.id.btnCompanyProfileClose);
        btnUpdateCompany = (Button)findViewById(R.id.btnUpdateCProfile);

        etCProfileName = (EditText)findViewById(R.id.etCProfileName);
        etCProfileDesc = (EditText)findViewById(R.id.etCProfileDesc);
        tvCompanyProfileValidation = (TextView)findViewById(R.id.tvCompanyProfileValidation);
        tvCompanyProfileValidation.setVisibility(View.GONE);
        initSpinnerIndustry();


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
                getIndustry=staticSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  Auto-generated method stub
            }
        });
//        Utilities.selectSpinnerValue(staticSpinner, "Others");
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

    private void updateCProfileNow(){
        getCompanyName = etCProfileName.getText().toString().trim();
        getDescription = etCProfileDesc.getText().toString().trim();


        if(Utilities.checkIsNull(getCompanyName)== true){
            tvCompanyProfileValidation.setText("Please Enter Company Name");
            tvCompanyProfileValidation.setVisibility(View.VISIBLE);
        }else{
            //TODO:  pass company_id to aPI
            apiUpdateCProfile = APIManager.getUpdateCProfileAPI( 1,
                    getCompanyName,
                    getIndustry,
                    getDescription);
            Utilities.displayToast(this, "Registration Successful");
            Utilities.displayToast(this, apiUpdateCProfile);

        }
    }
}
