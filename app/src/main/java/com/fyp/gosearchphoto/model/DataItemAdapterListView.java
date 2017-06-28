package com.fyp.gosearchphoto.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.gosearchphoto.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by anamay on 6/28/17.
 */

public class DataItemAdapterListView extends ArrayAdapter {

    //Declare a list that reference data items
    List<DataItem> mDataItems;
    LayoutInflater mInflater;

    public DataItemAdapterListView(Context context, List<DataItem> objects) {
        super(context, R.layout.list_item, objects);

        // persistent reference to the data and inflater object that i'm working with
        // mInflater to open and read into memory the xml layout file
        mDataItems = objects;
        mInflater = LayoutInflater.from(context);
    }

    /**
     *  Each time the array adapter encounters a new data item and wants to display it
     *  it's going to look for getView method. You must override it to customize the display
     *
     *  Convert view is a reference tool layout. Now that convert view might or might not be null.
     *  If the adapter is recycling a view for a list row, then it won't be null.
     *  I'll actually get a layout. But if it's the first time then it'll be null.
     *  So my job is going to be to make sure that it's not null.
     */


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            //instantiate it if it is null
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.itemNameText);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        DataItem item = mDataItems.get(position);

        tvName.setText(item.getItemName());
        //        imageView.setImageResource(R.drawable.apple_pie);

        InputStream inputStream = null;
        try {
            String imageFile = item.getImage();
            inputStream = getContext().getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return convertView;
    }
}
