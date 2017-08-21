package com.fyp.gosearchphoto.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.activity.TabMAlbumActivity;
import com.fyp.gosearchphoto.fragment.DeptAlbumFragment;
import com.fyp.gosearchphoto.fragment.GroupAlbumFragment;
import com.fyp.gosearchphoto.fragment.PublicFragment;
import com.fyp.gosearchphoto.fragment.UserAlbumFragment;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.List;

/**
 * Created by anamay on 7/23/17.
 */

public class DataAlbumAdapter extends RecyclerView.Adapter<DataAlbumAdapter.ViewHolder> {

    public static final String ITEM_ALBUM_ID = "album_id";
    public static final String ITEM_ALBUM_NAME = "album_name";
    public static final String ITEM_OWNER_ID = "owner_id";
    public static final String ITEM_OWNER_NAME = "owner_name";
    public static final String ITEM_PRIVACY_TYPE = "privacy_type";
    public static final String ITEM_DESCRIPTION = "description";

    private List<DataAlbum> mItems;
    private Context mContext;

    public static String pageName;
    public UserAlbumFragment fragment;
    public PublicFragment pfragment;
    public GroupAlbumFragment gafragment;
    public DeptAlbumFragment dafragment;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public DataAlbumAdapter(Context context, List<DataAlbum> items, UserAlbumFragment dataAlbumAdapter, PublicFragment pfragment, GroupAlbumFragment gafragment, DeptAlbumFragment daFragment) {
        this.mContext = context;
        this.pfragment = pfragment;
        this.mItems = items;
        this.fragment = dataAlbumAdapter;
        this.gafragment = gafragment;
        this.dafragment =daFragment;

        Log.i("DataItemAdapter", items.toString());
    }

/*
    This method is called automatically by the adapter each time it needs a new visual
    representation of a data item. When you inflate the xml layout file, you get a view,
    and then you wrap that in an instance of your ViewHolder class and return that object.
* */

    @Override
    public DataAlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("preferences", "onCreateViewHolder: ");


        int layoutId = R.layout.list_album;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(layoutId, parent, false);
        DataAlbumAdapter.ViewHolder viewHolder = new DataAlbumAdapter.ViewHolder(itemView);
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
    public void onBindViewHolder(final DataAlbumAdapter.ViewHolder holder, final int position) {
        final DataAlbum item = mItems.get(position);


        try {
            holder.tvUserExistingAlbum.setText(item.getAlbum_name());
           /* String imageFile = item.getImage();
             InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);*/


            if (item.getPage_data_type().equals("ManageUserAlbum")
                    || item.getPage_data_type().equals("DBExistingAlbumBottom")
                    || item.getPage_data_type().equals("ManageGroupAlbum")
                    || item.getPage_data_type().equals("GroupExistingAlbumBottom")
                    || item.getPage_data_type().equals("DAExistingAlbumBottom")) {
                holder.ibUserAlbumAdd.setBackgroundResource(R.drawable.btndelete);
            } else {
                holder.ibUserAlbumAdd.setBackgroundResource(R.drawable.btnadd);
            }
            if (item.getPage_data_type().equals("CREATE_ALBUM_POPUP") || item.getPage_data_type().equals("DepartmentAlbum")|| item.getPage_data_type().equals("ManageAlbum")) {
                holder.ibUserAlbumAdd.setVisibility(View.GONE);
            } else {
                holder.ibUserAlbumAdd.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (item.getPage_data_type()) {

                    case "CREATE_ALBUM_POPUP":
                        Utilities.displayToast(mContext, item.getAlbum_name() + " selected");
                        pfragment.selectedAlbum(item.getAlbum_name(), item.getAlbumId());
                        break;

                    case "ManageAlbum":
                        Intent intent2 = new Intent(mContext, TabMAlbumActivity.class);

                        intent2.putExtra(ITEM_ALBUM_ID, item.getAlbumId());
                        intent2.putExtra(ITEM_ALBUM_NAME, item.getAlbum_name());
                        intent2.putExtra(ITEM_OWNER_NAME, item.getOwner_name());
                        intent2.putExtra(ITEM_PRIVACY_TYPE, item.getPrivacy_type());
                        intent2.putExtra(ITEM_DESCRIPTION, item.getDescription());
                        intent2.putExtra(ITEM_OWNER_ID, item.getOwner_id());

                        mContext.startActivity(intent2);
                        break;
                }
            }

        });
        holder.ibUserAlbumAdd.setOnClickListener(new View.OnClickListener() {
            int getPos = position;

            @Override
            public void onClick(View v) {
                Utilities.displayToast(mContext, "Page Name: " + item.getPage_data_type()
                        + "Album: " + item.getAlbum_name());


                switch (item.getPage_data_type()) {
                    case "ManageUserAlbum":
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position, mItems.size());
                        break;
                    case "DBExistingAlbumTop":
                        DataAlbum ditem = new DataAlbum();

                        ditem.setAlbumId(item.getAlbumId());
                        ditem.setOwner_id(item.getOwner_id());
                        ditem.setAlbum_name(item.getAlbum_name());
                        ditem.setOwner_name(item.getOwner_name());
                        ditem.setDescription(item.getDescription());
                        ditem.setPrivacy_type(item.getPrivacy_type());

                        // SET what page or screen is the album displayed
                        ditem.setPage_data_type("DBExistingAlbumBottom");


                      /*  if(fragment.checkBottomItemExists(ditem.getAlbumId())){
                            Utilities.displayToast(mContext,"item already exist!");

                        }else{*/
                        Utilities.displayToast(mContext, "Nah it doest exist!");

                        fragment.addbottomItem(ditem);

                        // }
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position, mItems.size());

                        break;
                    case "DAExistingAlbumTop":
                        DataAlbum daitem = new DataAlbum();

                        daitem.setAlbumId(item.getAlbumId());
                        daitem.setOwner_id(item.getOwner_id());
                        daitem.setAlbum_name(item.getAlbum_name());
                        daitem.setOwner_name(item.getOwner_name());
                        daitem.setDescription(item.getDescription());
                        daitem.setPrivacy_type(item.getPrivacy_type());

                        // SET what page or screen is the album displayed
                        daitem.setPage_data_type("DAExistingAlbumBottom");


                      /*  if(fragment.checkBottomItemExists(ditem.getAlbumId())){
                            Utilities.displayToast(mContext,"item already exist!");

                        }else{*/
                        Utilities.displayToast(mContext, "Nah it doest exist!");

                        dafragment.addbottomItem(daitem);

                        // }
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position, mItems.size());

                        break;
                    case "DBExistingAlbumBottom":
                        DataAlbum ditem2 = new DataAlbum();
                        ditem2.setAlbumId(item.getAlbumId());
                        ditem2.setOwner_id(item.getOwner_id());
                        ditem2.setAlbum_name(item.getAlbum_name());
                        ditem2.setOwner_name(item.getOwner_name());
                        ditem2.setDescription(item.getDescription());
                        ditem2.setPrivacy_type(item.getPrivacy_type());

                        // SET what page or screen is the album displayed
                        ditem2.setPage_data_type("DBExistingAlbumTop");
                        fragment.addtopItem(ditem2);
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position, mItems.size());
                        break;
                    case "DAExistingAlbumBottom":
                        DataAlbum daitem2 = new DataAlbum();

                        daitem2.setAlbumId(item.getAlbumId());
                        daitem2.setOwner_id(item.getOwner_id());
                        daitem2.setAlbum_name(item.getAlbum_name());
                        daitem2.setOwner_name(item.getOwner_name());
                        daitem2.setDescription(item.getDescription());
                        daitem2.setPrivacy_type(item.getPrivacy_type());

                        // SET what page or screen is the album displayed
                        daitem2.setPage_data_type("DAExistingAlbumTop");
                        dafragment.addtopItem(daitem2);
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position, mItems.size());
                        break;
                    case "ManageGroupAlbum":
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position, mItems.size());
                        break;
                    case "GroupExistingAlbumTop":
                        DataAlbum ditem3 = new DataAlbum();

                        ditem3.setAlbumId(item.getAlbumId());
                        ditem3.setOwner_id(item.getOwner_id());
                        ditem3.setAlbum_name(item.getAlbum_name());
                        ditem3.setOwner_name(item.getOwner_name());
                        ditem3.setDescription(item.getDescription());
                        ditem3.setPrivacy_type(item.getPrivacy_type());

                        // SET what page or screen is the album displayed
                        ditem3.setPage_data_type("GroupExistingAlbumBottom");


                      /*  if(fragment.checkBottomItemExists(ditem.getAlbumId())){
                            Utilities.displayToast(mContext,"item already exist!");

                        }else{*/
                        Utilities.displayToast(mContext, "Nah it doest exist!");

                        gafragment.addbottomItem(ditem3);

                        // }
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position, mItems.size());

                        break;
                    case "GroupExistingAlbumBottom":
                        DataAlbum ditem4 = new DataAlbum();

                        ditem4.setAlbumId(item.getAlbumId());
                        ditem4.setOwner_id(item.getOwner_id());
                        ditem4.setAlbum_name(item.getAlbum_name());
                        ditem4.setOwner_name(item.getOwner_name());
                        ditem4.setDescription(item.getDescription());
                        ditem4.setPrivacy_type(item.getPrivacy_type());

                        // SET what page or screen is the album displayed
                        ditem4.setPage_data_type("GroupExistingAlbumTop");
                        gafragment.addtopItem(ditem4);

                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position, mItems.size());
                        break;


                }

            }

        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "You long clicked " + item.getDescription(),
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

        public TextView tvUserExistingAlbum;
        public ImageButton ibUserAlbumAdd;

        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            ibUserAlbumAdd = (ImageButton) itemView.findViewById(R.id.ibUserAlbumAdd);
            tvUserExistingAlbum = (TextView) itemView.findViewById(R.id.tvUserExistingAlbum);

            itemView.setOnClickListener(this);
            ibUserAlbumAdd.setOnClickListener(this);

            // That view will reference will be available to the rest of the adapter
            mView = itemView;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.ibUserAlbumAdd:
                    //startActivity(new Intent(MainActivity.this, RegisterActivity.class));

                    break;

            }
            Log.i("click", "button is clicked");

        }


    }

}