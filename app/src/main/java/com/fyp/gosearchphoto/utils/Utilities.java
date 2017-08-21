package com.fyp.gosearchphoto.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.menuadapter.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by anamay on 6/14/17.
 */

public class Utilities {
    // Activity request codes
    private static final int PICK_FROM_FILES = 101;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;

    public static final String KEY_FILE_URL = "IMAGE_FILE_URL";


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static void displayToast(Context context, String message) {
        Toast.makeText(context,
                message,
                Toast.LENGTH_LONG).show();
    }

    public static boolean checkIsNull(String textview) {
        String checkString = textview.trim();

        if (checkString.isEmpty() || checkString.length() == 0 || checkString.equals("") || checkString == null) {
            Log.i("is it Null?", "true");

            return true;
        } else {

            Log.i("is it Null?", "false");

            return false;
        }
    }

    public static String checkValueIfNull(String textview) {
        String checkString = textview;

        if (checkString == null) {
            Log.i("is it Null?", "true");

            checkString = "This is null";
        }
        return checkString;
    }

    public static void selectSpinnerValue(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(myString)) {
                spinner.setSelection(i);
                break;
            }
        }
    }


    public static WindowManager.LayoutParams setPopUpWidth(Dialog dialog) {

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return lp;
    }

    //Bottom Manager Menu
    public static List<Item> createItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.manage_company, "Company Profile"));
        items.add(new Item(R.drawable.manage_department, "Manage Department"));
        items.add(new Item(R.drawable.manage_group, "Manage Group"));
        items.add(new Item(R.drawable.manage_user, "Manage User"));
        items.add(new Item(R.drawable.manage_album, "Manage Project Album"));
        return items;
    }

    public static void hideKeyboard(Activity context) {
        // Check if no view has focus:
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideKeyboardNow(Window window) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }


    /**
     * Helper to retrieve the path of an image URI
     */
    public static String getPath(Uri uri, Activity act) {

        if (uri == null) {
            Toast.makeText(act, "Invalid File", Toast.LENGTH_SHORT).show();
            return null;
        }
        // Try to retrieve the image from the media store
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = act.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }


        return uri.getPath();
    }

    /**
     * Checking device has camera hardware or not
     */
    public static boolean isDeviceSupportCamera(Activity act) {
        if (act.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // This device has a camera
            return true;
        } else {
            // No camera on this device
            return false;
        }
    }


    // Advanced search
    private EditText etUploadFromDate;
    private EditText etUploadToDate;
    private String selectedButtonStr = "UploadFrom";
    private String getImageType;
    private Calendar myCalendarFrom;
    private Calendar myCalendarTo;
    private Spinner spinnerImageType;
    private Dialog adv_menu_dialog;
    private Button adv_menu_dialogButtonCancel;
    private Button adv_menu_dialogButtonOK;
    private Context mContext;
    private String pageFrom;
 /*

    *   Advanced Menu
    * */

    public void showAdvancedMenu() {

        adv_menu_dialog = new Dialog(mContext);
        adv_menu_dialog.setContentView(R.layout.popup_advpublic_search);
        adv_menu_dialog.setTitle("Advanced Menu");

        adv_menu_dialogButtonCancel = (Button) adv_menu_dialog.findViewById(R.id.dialog_btn_cancel);
        adv_menu_dialogButtonOK = (Button) adv_menu_dialog.findViewById(R.id.dialog_btn_ok);
        Button btnUploadToDate = (Button) adv_menu_dialog.findViewById(R.id.btnUploadToDate);
        Button btnUploadFromDate = (Button) adv_menu_dialog.findViewById(R.id.btnUploadFromDate);


        EditText etTitle = (EditText) adv_menu_dialog.findViewById(R.id.etTitle);
        EditText etDesc = (EditText) adv_menu_dialog.findViewById(R.id.etDesc);
        etUploadFromDate = (EditText) adv_menu_dialog.findViewById(R.id.etUploadFromDate);
        etUploadToDate = (EditText) adv_menu_dialog.findViewById(R.id.etUploadToDate);
        EditText etTag = (EditText) adv_menu_dialog.findViewById(R.id.etTag);
        EditText etUploadedBy = (EditText) adv_menu_dialog.findViewById(R.id.etUploadedBy);

        spinnerImageType = (Spinner) adv_menu_dialog.findViewById(R.id.spinnerImageType);
        initSpinnerImageType();
        myCalendarFrom = Calendar.getInstance();
        myCalendarTo = Calendar.getInstance();

        if(pageFrom.equals("MyPhotos")){
            etUploadedBy.setVisibility(View.GONE);
        }else{
            etUploadedBy.setVisibility(View.VISIBLE);
        }
        //  Set dialog width to fill parent and height to wrap content
        adv_menu_dialog.getWindow()
                .setAttributes(Utilities.setPopUpWidth(adv_menu_dialog));


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                if (selectedButtonStr.equals("UploadTo")) {

                    myCalendarTo.set(Calendar.YEAR, year);
                    myCalendarTo.set(Calendar.MONTH, monthOfYear);
                    myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                } else {
                    myCalendarFrom.set(Calendar.YEAR, year);
                    myCalendarFrom.set(Calendar.MONTH, monthOfYear);
                    myCalendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                }
                updateLabel();

            }

        };

        btnUploadFromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                myCalendarFrom = Calendar.getInstance();
                selectedButtonStr = "UploadFrom";
                new DatePickerDialog(mContext, date, myCalendarFrom
                        .get(Calendar.YEAR), myCalendarFrom.get(Calendar.MONTH),
                        myCalendarFrom.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnUploadToDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                myCalendarTo = Calendar.getInstance();
                selectedButtonStr = "UploadTo";
                new DatePickerDialog(mContext, date, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        // set the custom dialog components - text, image and button
        // if button is clicked, close the custom dialog
        adv_menu_dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adv_menu_dialog.hide();
                adv_menu_dialog.dismiss();

            }
        });
        adv_menu_dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adv_menu_dialog.hide();
                adv_menu_dialog.dismiss();
            }
        });



    }

    public void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        int catalog_outdated = 0;
        if (selectedButtonStr.equals("UploadTo")) {
            etUploadToDate.setText(sdf.format(myCalendarTo.getTime()));
        } else {
            etUploadFromDate.setText(sdf.format(myCalendarFrom.getTime()));
        }

        if (myCalendarFrom.after(myCalendarTo)) {
            catalog_outdated = 1;
        }
        displayToast(mContext, "outdated" + catalog_outdated);

    }

    private void initSpinnerImageType() {
        //TODO Set industry default when connected to db
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(mContext, R.array.photo_type_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerImageType.setAdapter(staticAdapter);


        spinnerImageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                getImageType = spinnerImageType.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  Auto-generated method stub
            }
        });
//        Utilities.selectSpinnerValue(staticSpinner, "Others");
    }

 int count = 0;
    public void showAdvanceSearch(Context con, String pageFrom) {
        mContext = con;
        this.pageFrom = pageFrom;
        showAdvancedMenu();
        if(!adv_menu_dialog.isShowing()){
            displayToast(mContext, "it is not showing"+count);
            count++;

            adv_menu_dialog.show();
        }else{
            displayToast(mContext, "it is not showing");
        }

    }
}