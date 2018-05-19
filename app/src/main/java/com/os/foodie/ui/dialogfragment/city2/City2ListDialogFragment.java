package com.os.foodie.ui.dialogfragment.city2;

import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineType;
import com.os.foodie.data.network.model.locationinfo.city.City;
import com.os.foodie.ui.adapter.recyclerview.City2ListAdapter;
import com.os.foodie.ui.adapter.recyclerview.CityListAdapter;
import com.os.foodie.ui.dialogfragment.city.CityOnClickListener;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

public class City2ListDialogFragment extends DialogFragment implements View.OnClickListener {

    private ImageView ivDone;
    private RecyclerView recyclerView;

    private ArrayList<City> cities;
    private City2ListAdapter city2ListAdapter;

    private City2OnClickListener city2OnClickListener;

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

        View rootView = inflater.inflate(R.layout.dialog_fragment_city2_list, container, false);

        cities = getArguments().getParcelableArrayList(AppConstants.CITY_LIST);
        city2OnClickListener = getArguments().getParcelable(AppConstants.CITY_LISTENER);

        ivDone = (ImageView) rootView.findViewById(R.id.dialog_fragment_city2_list_iv_done);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.dialog_fragment_city2_list_recyclerview);

        city2ListAdapter = new City2ListAdapter(getContext(), cities);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(city2ListAdapter);

        ivDone.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (ivDone.getId() == v.getId()) {

            Log.d("onClick", "ivDone");
            Log.d("cuisineTypesSize", ">>" + cities.size());

            ArrayList<City> citiesChecked = new ArrayList<City>();

            for (int i = 0; i < cities.size(); i++) {

                Log.d("isChecked", ">>" + cities.get(i).isChecked());

                if (cities.get(i).isChecked()) {

                    Log.d("Checked", ">>" + cities.get(i).getName());
                    citiesChecked.add(cities.get(i));
                }
            }

            city2OnClickListener.onClick(citiesChecked);

            dismiss();
        }
    }
}