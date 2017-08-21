package com.fyp.gosearchphoto.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fyp.gosearchphoto.R;
import com.fyp.gosearchphoto.utils.Utilities;


public class AlbumUserFragment extends Fragment {

    private Spinner spinnerWhoCanView;
    String getSelectedItem;

    public AlbumUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vAlbumUser = inflater.inflate(R.layout.fragment_album_user, container, false);
        spinnerWhoCanView = (Spinner) vAlbumUser.findViewById(R.id.spinnerWhoCanView);

        initSpinnerWhoCanView();

        return vAlbumUser ;
    }

    private void initSpinnerWhoCanView() {
        getSelectedItem = "Users";
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.who_can_view_array,
                        android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerWhoCanView.setAdapter(staticAdapter);


        spinnerWhoCanView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                getSelectedItem=spinnerWhoCanView.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  Auto-generated method stub
            }
        });
        //Set default value for spinner;
        Utilities.selectSpinnerValue(spinnerWhoCanView, "Users");
    }
}