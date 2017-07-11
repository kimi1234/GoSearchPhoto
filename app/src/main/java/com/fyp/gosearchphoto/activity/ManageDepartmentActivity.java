package com.fyp.gosearchphoto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.fyp.gosearchphoto.R;

public class ManageDepartmentActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView btnMDepartmentClose;
    private FloatingActionButton fabAddDepartment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_department);

        btnMDepartmentClose = (ImageView)findViewById(R.id.btnMDepartmentClose);
        fabAddDepartment = (FloatingActionButton) findViewById(R.id.fabManageDepartment);

        btnMDepartmentClose.setOnClickListener(this);
        fabAddDepartment.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMDepartmentClose:
                startActivity(new Intent(ManageDepartmentActivity.this, TabActivity.class));
                break;


            case R.id.fabManageDepartment:
                startActivity(new Intent(ManageDepartmentActivity.this, TabMDepartmentActivity.class));
                break;

        }
    }
}
