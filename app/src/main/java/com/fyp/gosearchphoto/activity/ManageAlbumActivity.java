package com.fyp.gosearchphoto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.fyp.gosearchphoto.R;

public class ManageAlbumActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView btnMAlbumClose;
    private FloatingActionButton fabAddAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_album);


        btnMAlbumClose = (ImageView)findViewById(R.id.btnMAlbumClose);
        fabAddAlbum = (FloatingActionButton) findViewById(R.id.fabManageAlbum);

        btnMAlbumClose.setOnClickListener(this);
        fabAddAlbum.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMAlbumClose:
                startActivity(new Intent(ManageAlbumActivity.this, TabActivity.class));
                break;


            case R.id.fabManageAlbum:
                startActivity(new Intent(ManageAlbumActivity.this, TabMAlbumActivity.class));
                break;

        }
    }
}
