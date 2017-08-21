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
import com.fyp.gosearchphoto.activity.TabMUsersActivity;
import com.fyp.gosearchphoto.fragment.DeptUserFragment;
import com.fyp.gosearchphoto.fragment.GroupProfileFragment;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.List;

/**
 * Created by anamay on 7/21/17.
 */

public class DataUserAdapter extends RecyclerView.Adapter<DataUserAdapter.ViewHolder> {

    public static final String ITEM_USER_ID = "user_id";
    public static final String ITEM_USER_FULLNAME = "fullname";
    public static final String ITEM_USER_EMAIL = "email";
    public static final String ITEM_USER_TYPE = "type";
    public static final String ITEM_USER_COMPANYID = "company_id";
    public static final String ITEM_USER_DEPARTMENT = "departmentName";

    private GroupProfileFragment groupFragment;
    private DeptUserFragment deptUserFragment;

    private List<DataUser> mItems;
    private Context mContext;


    public DataUserAdapter(Context context, List<DataUser> items, GroupProfileFragment groupFragment, DeptUserFragment deptUserFragment) {
        this.mContext = context;
        this.mItems = items;
        this.groupFragment = groupFragment;
        this.deptUserFragment = deptUserFragment;
        Log.i("DataItemAdapter", items.toString());


    }
/*
    This method is called automatically by the adapter each time it needs a new visual
    representation of a data item. When you inflate the xml layout file, you get a view,
    and then you wrap that in an instance of your ViewHolder class and return that object.
* */

    @Override
    public DataUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("preferences", "onCreateViewHolder: ");


        int layoutId = R.layout.list_usernames;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(layoutId, parent, false);
        DataUserAdapter.ViewHolder viewHolder = new DataUserAdapter.ViewHolder(itemView);
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
    public void onBindViewHolder(final DataUserAdapter.ViewHolder holder, final int position) {
        final DataUser item = mItems.get(position);

        try {
            switch (item.getPage_data_type()) {
                case "No Page Name":
                    holder.ibUserAdd.setVisibility(View.GONE);
                    break;
                case "GroupProfile":
                    holder.ibUserAdd.setBackgroundResource(R.drawable.btndelete);
                    holder.ibUserAdd.setVisibility(View.VISIBLE);

                    break;
                case "CreateGroupUserTop":
                    holder.ibUserAdd.setBackgroundResource(R.drawable.btnadd);
                    holder.ibUserAdd.setVisibility(View.VISIBLE);

                    break;
                case "CreateGroupUserBottom":
                    holder.ibUserAdd.setBackgroundResource(R.drawable.btndelete);
                    holder.ibUserAdd.setVisibility(View.VISIBLE);

                    break;
                case "DepartmentUser":
                    holder.ibUserAdd.setVisibility(View.GONE);
                    break;
                case "CreateDeptUserTop":
                    holder.ibUserAdd.setBackgroundResource(R.drawable.btnadd);
                    holder.ibUserAdd.setVisibility(View.VISIBLE);
                    break;
                case "CreateDeptUserBottom":
                    holder.ibUserAdd.setBackgroundResource(R.drawable.btndelete);
                    holder.ibUserAdd.setVisibility(View.VISIBLE);

                    break;

            }

            holder.tvUserName.setText(item.getFullName());

            /*Log.i("Username"+position, item.getFullName());
            Log.i("USER ID"+position, String.valueOf(item.getUserId()));
            Log.i("C ID"+position, String.valueOf(item.getCompanyId()));
            Log.i("getDepartmentName"+position, item.getDepartmentName());
            Log.i("Email"+position, item.getEmail());*/
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.ibUserAdd.setOnClickListener(new View.OnClickListener() {
            int getPos = position;

            @Override
            public void onClick(View v) {
                Utilities.displayToast(mContext, "Page Name: " + item.getPage_data_type()
                        + "User: " + item.getFullName());


                switch (item.getPage_data_type()) {
                    case "GroupProfile":
                        groupFragment.deleteGroupUser(item.getFullName(), item.getUserId());
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position, mItems.size());
                        break;

                    case  "CreateGroupUserTop":
                        DataUser uitem = new DataUser();

                        uitem.setUserId(item.getUserId());
                        uitem.setFullName(item.getFullName());
                        uitem.setCompanyId(item.getCompanyId());
                        uitem.setEmail(item.getEmail());
                        uitem.setType(item.getType());

                        // SET what page or screen is the album displayed
                        uitem.setPage_data_type("CreateGroupUserBottom");


                      /*  if(fragment.checkBottomItemExists(ditem.getAlbumId())){
                            Utilities.displayToast(mContext,"item already exist!");

                        }else{*/
                        Utilities.displayToast(mContext,"Nah it doest exist!");

                        groupFragment.addbottomItem(uitem);

                        // }
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position,mItems.size());

                        break;
                    case  "CreateGroupUserBottom":
                        DataUser uitem2 = new DataUser();

                        uitem2.setUserId(item.getUserId());
                        uitem2.setFullName(item.getFullName());
                        uitem2.setCompanyId(item.getCompanyId());
                        uitem2.setEmail(item.getEmail());
                        uitem2.setType(item.getType());

                        // SET what page or screen is the album displayed
                        uitem2.setPage_data_type("CreateGroupUserTop");


                      /*  if(fragment.checkBottomItemExists(ditem.getAlbumId())){
                            Utilities.displayToast(mContext,"item already exist!");

                        }else{*/
                       // Utilities.displayToast(mContext,"Nah it doest exist!");

                        groupFragment.addtopItem(uitem2);

                        // }
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position,mItems.size());

                        break;
                    case  "CreateDeptUserTop":
                        DataUser duitem = new DataUser();

                        duitem.setUserId(item.getUserId());
                        duitem.setFullName(item.getFullName());


                        // SET what page or screen is the album displayed
                        duitem.setPage_data_type("CreateDeptUserBottom");


                       // Utilities.displayToast(mContext,"Nah it doest exist!");

                        deptUserFragment.addbottomItem(duitem);

                        // }
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position,mItems.size());

                        break;
                    case  "CreateDeptUserBottom":
                        DataUser duitem2 = new DataUser();

                        duitem2.setUserId(item.getUserId());
                        duitem2.setFullName(item.getFullName());


                        // SET what page or screen is the album displayed
                        duitem2.setPage_data_type("CreateDeptUserTop");


                      /*  if(fragment.checkBottomItemExists(ditem.getAlbumId())){
                            Utilities.displayToast(mContext,"item already exist!");

                        }else{*/
                        // Utilities.displayToast(mContext,"Nah it doest exist!");

                        deptUserFragment.addtopItem(duitem2);

                        // }
                        mItems.remove(getPos);
                        notifyItemRemoved(getPos);
                        notifyItemRangeChanged(position,mItems.size());

                        break;

                }

            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    /*  Toast.makeText(mContext, "You selected " + item.getItemName(),
                        Toast.LENGTH_SHORT).show();
                        */

                // String itemId = item.getItemId();
                switch (item.getPage_data_type()) {
                    case "No Page Name":
                        Intent intent = new Intent(mContext, TabMUsersActivity.class);

                        intent.putExtra(ITEM_USER_ID, item.getUserId());
                        intent.putExtra(ITEM_USER_FULLNAME, item.getFullName());
                        intent.putExtra(ITEM_USER_EMAIL, item.getEmail());
                        intent.putExtra(ITEM_USER_TYPE, item.getType());
                        intent.putExtra(ITEM_USER_COMPANYID, item.getCompanyId());
                        intent.putExtra(ITEM_USER_DEPARTMENT, item.getDepartmentName());


                        Log.i("user_fullname", Utilities.checkValueIfNull(item.getFullName()));
                        Log.i("user_id", "USERID " + item.getUserId());
                        Log.i("user_email", Utilities.checkValueIfNull(item.getEmail()));
                        Log.i("user_type", Utilities.checkValueIfNull(item.getType()));
                        Log.i("user_dept", Utilities.checkValueIfNull(item.getDepartmentName()));
                        Log.i("user_cid", "COMPANYID" + item.getCompanyId());
                        mContext.startActivity(intent);
                        break;

                    case "ManageUsers":
                        Intent intent2 = new Intent(mContext, TabMUsersActivity.class);

                        intent2.putExtra(ITEM_USER_ID, item.getUserId());
                        intent2.putExtra(ITEM_USER_FULLNAME, item.getFullName());
                        intent2.putExtra(ITEM_USER_EMAIL, item.getEmail());
                        intent2.putExtra(ITEM_USER_TYPE, item.getType());
                        intent2.putExtra(ITEM_USER_COMPANYID, item.getCompanyId());
                        intent2.putExtra(ITEM_USER_DEPARTMENT, item.getDepartmentName());


                        Log.i("user_fullname", Utilities.checkValueIfNull(item.getFullName()));
                        Log.i("user_id", "USERID " + item.getUserId());
                        Log.i("user_email", Utilities.checkValueIfNull(item.getEmail()));
                        Log.i("user_type", Utilities.checkValueIfNull(item.getType()));
                        Log.i("user_dept", Utilities.checkValueIfNull(item.getDepartmentName()));
                        Log.i("user_cid", "COMPANYID" + item.getCompanyId());
                        mContext.startActivity(intent2);
                        break;
                }


            }

        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "You long clicked " + item.getFullName(),
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

        public TextView tvUserName;
        public View mView;
        public ImageButton ibUserAdd;


        public ViewHolder(View itemView) {
            super(itemView);

            tvUserName = (TextView) itemView.findViewById(R.id.tvUserNames);
            ibUserAdd = (ImageButton) itemView.findViewById(R.id.ibUserAdd);
            itemView.setOnClickListener(this);
            ibUserAdd.setOnClickListener(this);


            // That view will reference will be available to the rest of the adapter
            mView = itemView;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvUserNames:

                    Log.i("Name clicked", tvUserName.getText().toString());
                    //startActivity(new Intent(MainActivity.this, LogInActivity.class));


                    break;

            }

        }


    }
}

