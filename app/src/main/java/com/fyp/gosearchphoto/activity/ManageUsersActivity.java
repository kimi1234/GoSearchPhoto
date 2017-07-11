package com.fyp.gosearchphoto.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fyp.gosearchphoto.R;

public class ManageUsersActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView btnMUsersClose;
    private FloatingActionButton fabAddUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);


        btnMUsersClose = (ImageView)findViewById(R.id.btnMUserClose);
        fabAddUsers = (FloatingActionButton) findViewById(R.id.fabManageUser);

        btnMUsersClose.setOnClickListener(this);
        fabAddUsers.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMUserClose:
                startActivity(new Intent(ManageUsersActivity.this, TabActivity.class));
                break;


            case R.id.fabManageUser:
                startActivity(new Intent(ManageUsersActivity.this, TabMUsersActivity.class));
                break;

        }
    }
}
