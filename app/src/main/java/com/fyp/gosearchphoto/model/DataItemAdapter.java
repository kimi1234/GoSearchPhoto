package com.fyp.gosearchphoto.model;

/**
 * Created by anamay on 6/28/17.
 */


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.utils.Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    public static final String ITEM_ID_KEY = "item_id_key";
    public static final String ITEM_KEY = "item_key";

    private List<DataItem> mItems;
    private Context mContext;



    public DataItemAdapter(Context context, List<DataItem> items) {
        this.mContext = context;
        this.mItems = items;
        Log.i("DataItemAdapter", items.toString());

    }
/*
    This method is called automatically by the adapter each time it needs a new visual
    representation of a data item. When you inflate the xml layout file, you get a view,
    and then you wrap that in an instance of your ViewHolder class and return that object.
* */

    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("preferences", "onCreateViewHolder: ");


        int layoutId = R.layout.list_item;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    /*
    * OnBindViewHolder is called each time the adapter encounters a new data item that it needs to display.
    * It passes in the reference to the ViewHolder and the position of the data item in the collection.
    * The job of OnBindViewHolder is to take that data object and display it's values.
    * And again, the code here looks exactly the same, but now when I reference tvName, a text view,
    * and image view, which are views in the xml layout file,
    * I'm getting those from the ViewHolder object.
    *
    * Supplies the data that you want to display to the user and set up event handlers
    * */
    @Override
    public void onBindViewHolder(final DataItemAdapter.ViewHolder holder, int position) {
        final DataItem item = mItems.get(position);

        try {
            holder.tvName.setText(item.getItemName());
            String imageFile = item.getImage();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    /*  Toast.makeText(mContext, "You selected " + item.getItemName(),
                        Toast.LENGTH_SHORT).show();
                        */
                // String itemId = item.getItemId();
             /*   Intent intent = new Intent(mContext, DetailActivity.class);
                // intent.putExtra(ITEM_ID_KEY, itemId);


                intent.putExtra(ITEM_KEY, item);

                mContext.startActivity(intent);*/

            }

        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "You long clicked " + item.getItemName(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /*
    * It extends a class named RecyclerView.
    * ViewHolder and it's responsible for setting up the bindings
    * to the views in the xml layout file.  I'm getting references to my text views,
    * my image views and other visual widgets.
    * And then I'm saving those as public fields of the ViewHolder class.
    * */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName, tvUploadDate, tvUploadBy;
        public ImageView imageView;
        public View mView;
        public ImageButton iBDownload, iBInfo;
        public ImageButton iBFave;


        public ViewHolder(View itemView) {
            super(itemView);

            iBFave = (ImageButton) itemView.findViewById(R.id.imageButtonFave);
            iBDownload = (ImageButton) itemView.findViewById(R.id.imageButtonDownload);
            iBInfo = (ImageButton) itemView.findViewById(R.id.imageButtonInfo);

            tvName = (TextView) itemView.findViewById(R.id.itemNameText);
            tvUploadDate = (TextView) itemView.findViewById(R.id.tvUploadDate);
            tvUploadBy = (TextView) itemView.findViewById(R.id.tvUploadBy);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);


            iBFave.setOnClickListener(this);
            iBInfo.setOnClickListener(this);
            iBDownload.setOnClickListener(this);

            // That view will reference will be available to the rest of the adapter
            mView = itemView;
        }
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imageButtonFave:
              //  iBFave.setButtonDrawable(R.drawable.rate_star_pink);
                //iBFave.setBackgroundResource(R.drawable.rate_star_pink);
                   // iBFave.setImageResource(R.drawable.rate_star_pink);


                   Log.i("Favourite clicked", iBFave.getDrawable().toString());
                    //startActivity(new Intent(MainActivity.this, LogInActivity.class));

                    Object tag = iBFave.getTag();
                    int backgroundId = R.drawable.rate_star_pink;
                    if( tag != null && ((Integer)tag).intValue() == backgroundId) {
                        backgroundId = R.drawable.rate_star_darkgrey;
                    }
                    iBFave.setTag(backgroundId);
                    iBFave.setImageResource(backgroundId);

                    Utilities.displayToast(view.getContext(),"Favourites Selected");


                    break;

                case R.id.imageButtonDownload:
                    //startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                    ;
                    Utilities.displayToast(view.getContext(),"Download Selected");

                    break;
                case R.id.imageButtonInfo:
                    showImageInfo(view.getContext());
                    // startActivity(new Intent(MainActivity.this, TabActivity.class));

                    break;
            }
            Log.i("click", "button is clicked");

        }

        public void showImageInfo(Context context){
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.popup_info);
            dialog.setTitle("Image Information");

            // set the custom dialog components - text, image and button

            Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogbtn_cancel);
           final Button dialogButtonEdit = (Button) dialog.findViewById(R.id.dialogbtn_edit);
            // if button is clicked, close the custom dialog
            dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialogButtonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dialogButtonEdit.getText().equals("Edit")){
                        dialogButtonEdit.setText("Save");
                    }else {
                        dialog.dismiss();

                    }

                }
            });

            dialogButtonEdit.setText("Edit");

            dialog.show();
            //  Set dialog width to fill parent and height to wrap content
            dialog.getWindow()
                    .setAttributes(Utilities.setPopUpWidth(dialog));


        }


    }

}