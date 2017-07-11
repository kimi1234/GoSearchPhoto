package com.fyp.gosearchphoto.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.menuadapter.Item;

import java.util.ArrayList;
import java.util.List;

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



    public static WindowManager.LayoutParams setPopUpWidth(Dialog dialog){

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return lp;
    }

    //Bottom Manager Menu
    public  static List<Item> createItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.manage_company, "Company Profile"));
        items.add(new Item(R.drawable.manage_department, "Manage Department"));
        items.add(new Item(R.drawable.manage_group, "Manage Group"));
        items.add(new Item(R.drawable.manage_user, "Manage User"));
        items.add(new Item(R.drawable.manage_album, "Manage Project Album"));
        return items;
    }


}
