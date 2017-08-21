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
import com.fyp.gosearchphoto.activity.TabMDepartmentActivity;
import com.fyp.gosearchphoto.fragment.DeptProfileFragment;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.List;

/**
 * Created by anamay on 8/6/17.
 */

public class DataDepartmentAdapter extends RecyclerView.Adapter<DataDepartmentAdapter.ViewHolder> {

    public static final String ITEM_DEPT_ID = "dept_id";
    public static final String ITEM_DEPT_NAME = "dept_name";
    public static final String ITEM_DEPT_COMPANYID = "dept_cID";
    public static final String ITEM_DEPT_DESC = "dept_desc";

    private DeptProfileFragment deptProfileFragment;

    private List<DataDepartment> mItems;
    private Context mContext;


    public DataDepartmentAdapter(Context context, List<DataDepartment> items, DeptProfileFragment deptProfileFragment) {
        this.mContext = context;
        this.mItems = items;
        this.deptProfileFragment = deptProfileFragment;
        Log.i("DataItemAdapter", items.toString());
    }
/*
    This method is called automatically by the adapter each time it needs a new visual
    representation of a data item. When you inflate the xml layout file, you get a view,
    and then you wrap that in an instance of your ViewHolder class and return that object.
* */

    @Override
    public DataDepartmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("preferences", "onCreateViewHolder: ");


        int layoutId = R.layout.list_department;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(layoutId, parent, false);
        DataDepartmentAdapter.ViewHolder viewHolder = new DataDepartmentAdapter.ViewHolder(itemView);
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
    public void onBindViewHolder(final DataDepartmentAdapter.ViewHolder holder, final int position) {
        final DataDepartment item = mItems.get(position);

        try {
            switch (item.getPage_data_type()) {
                case "ManageDepartment":
                    holder.ibDeptAdd.setVisibility(View.GONE);
                    break;

                case "GroupProfile":
                    holder.ibDeptAdd.setBackgroundResource(R.drawable.btndelete);
                    holder.ibDeptAdd.setVisibility(View.VISIBLE);

                    break;
                case "CreateGroupUserTop":
                    holder.ibDeptAdd.setBackgroundResource(R.drawable.btnadd);
                    holder.ibDeptAdd.setVisibility(View.VISIBLE);

                    break;
                case "CreateGroupUserBottom":
                    holder.ibDeptAdd.setBackgroundResource(R.drawable.btndelete);
                    holder.ibDeptAdd.setVisibility(View.VISIBLE);

                    break;
            }

            holder.tvExistingDept.setText(item.getDepartment_name());

            /*Log.i("Username"+position, item.getFullName());
            Log.i("USER ID"+position, String.valueOf(item.getUserId()));
            Log.i("C ID"+position, String.valueOf(item.getCompanyId()));
            Log.i("getDepartmentName"+position, item.getDepartmentName());
            Log.i("Email"+position, item.getEmail());*/
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.ibDeptAdd.setOnClickListener(new View.OnClickListener() {
            int getPos = position;

            @Override
            public void onClick(View v) {
                Utilities.displayToast(mContext, "Page Name: " + item.getPage_data_type()
                        + "User: " + item.getDepartment_name());


                switch (item.getPage_data_type()) {
                    case "GroupProfile":


                        break;
                }
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                      Toast.makeText(mContext, "You selected " + item.getDepartment_name(),
                        Toast.LENGTH_SHORT).show();


                // String itemId = item.getItemId();
                switch (item.getPage_data_type()) {
                    case "ManageDepartment":
                        Intent intent = new Intent(mContext, TabMDepartmentActivity.class);

                        intent.putExtra(ITEM_DEPT_ID, item.getDepartment_id());
                        intent.putExtra(ITEM_DEPT_NAME, item.getDepartment_name());
                        intent.putExtra(ITEM_DEPT_COMPANYID, item.getCompany_id());
                        intent.putExtra(ITEM_DEPT_DESC, item.getDescription());


                        mContext.startActivity(intent);
                        break;
                }


            }

        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              /*  Toast.makeText(mContext, "You long clicked " + item.getFullName(),
                        Toast.LENGTH_SHORT).show();
                        */
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

        public TextView tvExistingDept;
        public View mView;
        public ImageButton ibDeptAdd;


        public ViewHolder(View itemView) {
            super(itemView);

            tvExistingDept = (TextView) itemView.findViewById(R.id.tvExistingDept);
            ibDeptAdd = (ImageButton) itemView.findViewById(R.id.ibDeptAdd);
            itemView.setOnClickListener(this);
            ibDeptAdd.setOnClickListener(this);


            // That view will reference will be available to the rest of the adapter
            mView = itemView;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvExistingDept:

                    Log.i("Name clicked", tvExistingDept.getText().toString());
                    //startActivity(new Intent(MainActivity.this, LogInActivity.class));
                    break;

            }

        }


    }
}

