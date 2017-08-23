package com.fyp.gosearchphoto.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.database.MDataSource;
import com.fyp.gosearchphoto.model.DataImage;
import com.fyp.gosearchphoto.model.DataImageAdapter;
import com.fyp.gosearchphoto.model.DataStatus;
import com.fyp.gosearchphoto.services.CandyLoopService;
import com.fyp.gosearchphoto.services.ServiceHelper;
import com.fyp.gosearchphoto.utils.APIManager;
import com.fyp.gosearchphoto.utils.PreferencesConfig;
import com.fyp.gosearchphoto.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import ru.whalemare.sheetmenu.SheetMenu;


public class MyPhotosFragment extends Fragment implements View.OnClickListener {

    private EditText etMySearch;
    private ImageButton ibMyPhotoFilter;
    private FloatingActionButton fab;

    private MDataSource mDataSource;
    private DataImageAdapter adapter;
    private String getKeywordSearch;

    private RecyclerView mRecyclerView;

    private Context mContext;
    private String PAGE_NAME, getSearchBy, getSortBy;



    // Advanced search
    /*private EditText etUploadFromDate;
    private EditText etUploadToDate;
    private  String selectedButtonStr = "UploadFrom";
    private String getImageType;
    private Calendar myCalendarFrom;
    private Calendar myCalendarTo;
    private Spinner spinnerImageType;
    private Dialog adv_menu_dialog;
    private Button adv_menu_dialogButtonCancel;
    private Button adv_menu_dialogButtonOK ;*/

    public MyPhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myPhotoView = inflater.inflate(R.layout.fragment_my_photos, container, false);
        fab = (FloatingActionButton) myPhotoView.findViewById(R.id.fabMyPhoto);


        ibMyPhotoFilter = (ImageButton) myPhotoView.findViewById(R.id.ibMyPhotoFilter);
        etMySearch = (EditText) myPhotoView.findViewById(R.id.etMyPhotoSearch);

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etMySearch, InputMethodManager.SHOW_IMPLICIT);

        mContext = getContext();
        //registerBroadcast();
        getSearchBy = "img_name";
        getSortBy = "img_name";
        fab.setOnClickListener(this);
        ibMyPhotoFilter.setOnClickListener(this);
        etMySearch.setOnClickListener(this);
        registerForContextMenu(ibMyPhotoFilter);


        etMySearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getKeywordSearch = etMySearch.getText().toString().trim();
//                    performSearch();
                    mDataSource = new MDataSource(getContext());
                    mDataSource.open();

                    performSearch();
                    Utilities.hideFragKeyboardNow(getActivity());

                    return true;
                }

                return false;
            }
        });


        return myPhotoView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvMyPhotoItems);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ibMyPhotoFilter:

                Log.i("filter button", "ibMyPhotoFilter clicked");
                v.showContextMenu();
               // Utilities.displayToast(getContext(), "show context menu");


                break;
            case R.id.fabMyPhoto:
                showCreateAlbum(getContext());
                break;
        }


    }


    private void getMyPhotoImageNow() {
        PAGE_NAME = "MyPhotoPage";
        APIManager.getSearchMyPhotoAPI(mContext,
                PreferencesConfig.getCompanyIdPreference(mContext),
                PreferencesConfig.getUserIDPreference(mContext),
                getSearchBy,
                "all",
                getSortBy);
    }


    private void performSearch() {

        if(Utilities.checkIsNull(getKeywordSearch)==true){
            getKeywordSearch ="all";
        }
        APIManager.getSearchMyPhotoAPI(mContext,
                PreferencesConfig.getCompanyIdPreference(mContext),
                PreferencesConfig.getUserIDPreference(mContext),
                getSearchBy,
                getKeywordSearch,
                getSortBy);


    }


    public void setImageDataAdapter(List<DataImage> du){
        if(du.size()>0) {
            adapter = new DataImageAdapter(mContext, du);
            Log.i("get count", "" + adapter.getItemCount());
            adapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(adapter);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Toast.makeText(getActivity(), "onCreateContextMenu CALLED", Toast.LENGTH_SHORT)
                .show();
        if (v.getId() == R.id.ibMyPhotoFilter) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.search_option, menu);

            // custom dialog
        }
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_sort:

                SheetMenu.with(getContext())
                        .setTitle(R.string.title_sortby)
                        .setMenu(R.menu.menu_sortby)
                        .setAutoCancel(true)
                        .setClick(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getTitle().toString().trim()){
                                    case "Name":
                                        getSortBy = "img_name";
                                        break;
                                    case "Size":
                                        getSortBy = "width";
                                        break;
                                    case "Last Upload Date":
                                        getSortBy = "uploadDateTime";
                                        break;
                                }
                                return false;
                            }
                        }).show();

                return true;
            case R.id.menu_filter:
                SheetMenu.with(getContext())
                        .setTitle(R.string.title_searchby)
                        .setMenu(R.menu.menu_searchby)
                        .setAutoCancel(true)
                        .setClick(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getTitle().toString().trim()){
                                    case "Name":
                                        getSearchBy = "img_name";
                                        break;
                                    case "Size":
                                        getSearchBy = "img_width";
                                        break;
                                    case "Extension":
                                        getSearchBy = "extension";

                                        break;
                                    case "Last Upload Date":
                                        getSearchBy = "uploadDateTime";
                                        break;
                                    case "Album":
                                        getSearchBy = "img_name";

                                        break;
                                    case "Description":
                                        getSearchBy = "description";

                                        break;
                                    case "Tags":
                                        getSearchBy = "img_name";

                                        break;
                                }

                                return false;
                            }
                        }).show();
                return true;
            case R.id.menu_adv_search:
             //   showAdvancedMenu();
                Utilities ut = new Utilities();
                ut.showAdvanceSearch(getContext(), "MyPhotos");
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }




    public void showCreateAlbum(Context context) {
        final Dialog album_dialog = new Dialog(context);
        album_dialog.setContentView(R.layout.popup_createalbum);
        album_dialog.setTitle("Create Album");

        Button dialogButtonCancel = (Button) album_dialog.findViewById(R.id.dialogbtn_cancel);
        Button dialogButtonOK = (Button) album_dialog.findViewById(R.id.dialogbtn_ok);

        // set the custom dialog components - text, image and button
        // if button is clicked, close the custom dialog
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album_dialog.dismiss();
            }
        });
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album_dialog.dismiss();
            }
        });

        album_dialog.show();
        //  Set dialog width to fill parent and height to wrap content
        album_dialog.getWindow()
                .setAttributes(Utilities.setPopUpWidth(album_dialog));


    }
   /*
    *   Advanced Menu
    * */

   /* public void showAdvancedMenu(){


        adv_menu_dialog = new Dialog(getContext());
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

        etUploadedBy.setVisibility(View.GONE);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                if(selectedButtonStr.equals("UploadTo")) {

                    myCalendarTo.set(Calendar.YEAR, year);
                    myCalendarTo.set(Calendar.MONTH, monthOfYear);
                    myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                }else{
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
                new DatePickerDialog(getContext(), date, myCalendarFrom
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
                new DatePickerDialog(getContext(), date, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        // set the custom dialog components - text, image and button
        // if button is clicked, close the custom dialog
        adv_menu_dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adv_menu_dialog.dismiss();
                adv_menu_dialog = null;
            }
        });
        adv_menu_dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adv_menu_dialog.dismiss();
                adv_menu_dialog = null;
            }
        });

        adv_menu_dialog.show();
        //  Set dialog width to fill parent and height to wrap content
        adv_menu_dialog.getWindow()
                .setAttributes(Utilities.setPopUpWidth(adv_menu_dialog));



    }
    public void updateLabel(){
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        int catalog_outdated=0;
        if(selectedButtonStr.equals("UploadTo")){
            etUploadToDate.setText(sdf.format(myCalendarTo.getTime()));
        }else {
            etUploadFromDate.setText(sdf.format(myCalendarFrom.getTime()));
        }

        if (myCalendarFrom.after(myCalendarTo)) {
            catalog_outdated = 1;
        }
        Utilities.displayToast(getContext(),"outdated"+catalog_outdated);

    }

    private void initSpinnerImageType() {
        //TODO Set industry default when connected to db
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.photo_type_array,
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
                getImageType=spinnerImageType.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  Auto-generated method stub
            }
        });
//        Utilities.selectSpinnerValue(staticSpinner, "Others");
    }
*/
   private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
       @Override
       public void onReceive(Context context, Intent intent) {
           //   Log.i(LogInTAG, "Broadcast: "+CandyLoopService.MY_SERVICE_PAYLOAD);

           switch (CandyLoopService.MY_SERVICE_PAYLOAD) {
               case ServiceHelper.PAYLOAD_SEARCH_MY_PHOTO:

                   DataImage du = (DataImage) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                   if(du.getStatus().equals("no data")){
                       Utilities.displayToast(mContext, "No public photo found.");
                       Log.i("Public","No public data found");

                   }else if(du.getStatus().equals("success")){
                       List<DataImage> listFromServer = du.getImageinfo();
                       Log.i("to string ", listFromServer.toString());

                       // filter list and add pagename list
                       List<DataImage> dataItems = new ArrayList<>();

                       for (int i = 0; i < listFromServer.size(); i++) {
                           DataImage item = new DataImage();
                           item.setImage_id(listFromServer.get(i).getImage_id());
                           item.setImage_url(listFromServer.get(i).getImage_url());
                           item.setTitle(listFromServer.get(i).getTitle());
                           item.setSize(listFromServer.get(i).getSize());
                           item.setDescription(listFromServer.get(i).getDescription());
                           item.setAlbum(listFromServer.get(i).getDescription());
                           item.setAlbum_id(listFromServer.get(i).getAlbum_id());
                           item.setUploaded_by(listFromServer.get(i).getDescription());
                           item.setUploadDateTime(listFromServer.get(i).getUploadDateTime());
                           item.setTag(listFromServer.get(i).getTag());
                           item.setPage_data_type(PAGE_NAME);

                           //TODO: Should set if its a favourite or not
                           dataItems.add(item);

                       }


                       setImageDataAdapter(dataItems);

                   }else{
                       Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                   }

                   break;

               case ServiceHelper.PAYLOAD_ADD_FAVOURITE:
                   DataStatus duData1 = (DataStatus) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                   // Update Successful
                   if (duData1.getStatus().equals("success")) {
                       Utilities.displayToast(mContext, "Photo has been added to favourites");
                   } else {
                       Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                   }
                   break;

               case ServiceHelper.PAYLOAD_REMOVE_FAVOURITE:
                   DataStatus duData2 = (DataStatus) intent.getParcelableExtra(CandyLoopService.MY_SERVICE_PAYLOAD);

                   // Update Successful
                   if (duData2.getStatus().equals("success")) {
                       Utilities.displayToast(mContext, "Photo has been removed from favourites");
                   } else {
                       Utilities.displayToast(mContext, ServiceHelper.ERROR_MSG);
                   }
                   break;

           }
       }

   };

    public void registerBroadcast(){
        CandyLoopService.setMyServicePage(ServiceHelper.PAGE_MYPHOTO);
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(CandyLoopService.MY_SERVICE_PAGE));

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Do your Work
                registerBroadcast();
            getMyPhotoImageNow();


        } else {

        }
    }


}