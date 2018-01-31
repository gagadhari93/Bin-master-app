package com.example.anjana.binmaster.HomePage;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anjana.binmaster.DogFood;
import com.example.anjana.binmaster.DogFood;
import com.example.anjana.binmaster.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDonation extends Fragment {


    public FoodDonation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View rootView= inflater.inflate(R.layout.fragment_daily_service, container, false);


        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.btnFloating);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootView) {
                // Click action
                switch (rootView.getId()){
                    case R.id.btnFloating:
                        getActivity().startActivity(new Intent(getActivity(),DogFood.class));
                        break;
                }
            }
        });
        return  rootView;
    }

}
