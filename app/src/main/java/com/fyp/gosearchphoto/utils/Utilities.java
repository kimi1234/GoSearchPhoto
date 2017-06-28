package com.fyp.gosearchphoto.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by anamay on 6/14/17.
 */

public class Utilities {


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public static void displayToast(Context context, String message){
        Toast.makeText(context,
                message,
                Toast.LENGTH_LONG).show();
    }

    public static boolean checkIsNull(String textview){
        String checkString = textview.trim();

        if(checkString.isEmpty() || checkString.length() == 0 || checkString.equals("") || checkString == null){
            Log.i("is it Null?","true");

            return true;
        }else{

            Log.i("is it Null?","false");

            return false;
        }

    }
}
