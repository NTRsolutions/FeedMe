package com.os.foodie.ui.dialogfragment.city;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.os.foodie.R;
import com.os.foodie.data.network.model.locationinfo.city.City;
import com.os.foodie.ui.adapter.recyclerview.CityListAdapter;
import com.os.foodie.ui.adapter.recyclerview.CuisineTypeAdapter;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

public class CityListDialogFragment extends DialogFragment {

    private RecyclerView recyclerView;

    private ArrayList<City> cities;
    private CityListAdapter cityListAdapter;

    private CityOnClickListener cityOnClickListener;

    @Override
    public void onStart() {
        super.onStart();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();

        lp.copyFrom(window.getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        window.setAttributes(lp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_fragment_city_list, container, false);

        cities = getArguments().getParcelableArrayList(AppConstants.CITY_LIST);
        cityOnClickListener = getArguments().getParcelable(AppConstants.CITY_LISTENER);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.dialog_fragment_city_list_recyclerview);

        cityListAdapter = new CityListAdapter(getContext(), cities, cityOnClickListener);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cityListAdapter);

        return rootView;
    }

}