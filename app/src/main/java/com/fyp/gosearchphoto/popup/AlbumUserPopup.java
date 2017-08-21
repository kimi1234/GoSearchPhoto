package com.fyp.gosearchphoto.popup;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.utils.Utilities;

/**
 * Created by anamay on 8/17/17.
 */

public class AlbumUserPopup {
    private Context mContext;
    private Dialog album_user_dialog;
    private Button album_user_dialogButtonOK;
    private Button album_user_dialogButtonCancel;
    private Spinner spinnerAlbumPermissionType;
    private String getSelectedPermission;

    public void showAlbumUserPopup(Context getcontext) {
        mContext = getcontext;

        album_user_dialog = new Dialog(mContext);
        album_user_dialog.setContentView(R.layout.popup_add_albumuser);
        album_user_dialog.setTitle("showAlbumPopup");

        album_user_dialogButtonCancel = (Button) album_user_dialog.findViewById(R.id.dialog_btn_cancel);
        album_user_dialogButtonOK = (Button) album_user_dialog.findViewById(R.id.dialog_btn_ok);
        spinnerAlbumPermissionType = (Spinner) album_user_dialog.findViewById(R.id.spinnerAlbumPermissionType);
        initSpinnerPermission();


        album_user_dialog.show();
        //  Set dialog width to fill parent and height to wrap content
        album_user_dialog.getWindow()
                .setAttributes(Utilities.setPopUpWidth(album_user_dialog));


        // set the custom dialog components - text, image and button
        // if button is clicked, close the custom dialog
        album_user_dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album_user_dialog.dismiss();
            }
        });
        album_user_dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album_user_dialog.dismiss();
            }
        });

    }

    private void initSpinnerPermission() {
        //TODO Set industry default when connected to db
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(mContext, R.array.who_can_view_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerAlbumPermissionType.setAdapter(staticAdapter);



        spinnerAlbumPermissionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                getSelectedPermission=spinnerAlbumPermissionType.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  Auto-generated method stub
            }
        });
//        Utilities.selectSpinnerValue(staticSpinner, "Others");
    }
}
