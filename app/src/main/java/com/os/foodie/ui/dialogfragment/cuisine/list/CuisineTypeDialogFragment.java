package com.os.foodie.ui.dialogfragment.cuisine.list;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineType;
import com.os.foodie.ui.adapter.recyclerview.CuisineTypeAdapter;
import com.os.foodie.ui.dialogfragment.cuisine.add.AddCuisineTypeDialogFragment;
import com.os.foodie.ui.filters.FiltersActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.setupprofile.restaurant.SetupRestaurantProfileFragment;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;

import java.util.ArrayList;

public class CuisineTypeDialogFragment extends DialogFragment implements View.OnClickListener {

    private ImageView ivDone;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;

    private ArrayList<CuisineType> cuisineTypes;
    private CuisineTypeAdapter cuisineTypeAdapter;

    private ProgressDialog progressDialog;

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

        View rootView = inflater.inflate(R.layout.dialog_fragment_cuisine_type, container, false);

        ivDone = (ImageView) rootView.findViewById(R.id.dialog_fragment_cuisine_type_iv_done);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.dialog_fragment_cuisine_type_recycler_view);
        fabAdd = (FloatingActionButton) rootView.findViewById(R.id.dialog_fragment_cuisine_type_fab_add);

        cuisineTypes = getArguments().getParcelableArrayList(AppConstants.CUISINE_TYPES_ARRAYLIST);

        if (getArguments().getBoolean(AppConstants.IS_FAB_NEEDED) == false) {
            fabAdd.setVisibility(View.GONE);
        }

        cuisineTypeAdapter = new CuisineTypeAdapter(getContext(), cuisineTypes);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cuisineTypeAdapter);

        ivDone.setOnClickListener(this);
        fabAdd.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (fabAdd.getId() == v.getId()) {

            AddCuisineTypeDialogFragment addCuisineTypeDialogFragment = new AddCuisineTypeDialogFragment();
            addCuisineTypeDialogFragment.show(getActivity().getSupportFragmentManager(), "AddCuisineTypeDialogFragment");

        } else if (ivDone.getId() == v.getId()) {

            Log.d("onClick", "ivDone");
            Log.d("cuisineTypesSize", ">>" + cuisineTypeAdapter.cuisineTypes.size());

            ArrayList<CuisineType> cuisineTypesChecked = new ArrayList<CuisineType>();

            for (int i = 0; i < cuisineTypeAdapter.cuisineTypes.size(); i++) {

                Log.d("isChecked", ">>" + cuisineTypeAdapter.cuisineTypes.get(i).isChecked());

                if (cuisineTypeAdapter.cuisineTypes.get(i).isChecked()) {

                    Log.d("Checked", ">>" + cuisineTypeAdapter.cuisineTypes.get(i).getName());
                    cuisineTypesChecked.add(cuisineTypeAdapter.cuisineTypes.get(i));
                }
            }

            dismiss();

            if (getActivity() instanceof RestaurantMainActivity) {

                RestaurantMainActivity restaurantMainActivity = (RestaurantMainActivity) getActivity();
                Fragment fragment = restaurantMainActivity.getCurrentFragment();

                if (fragment instanceof SetupRestaurantProfileFragment) {
                    ((SetupRestaurantProfileFragment) fragment).setCuisineTypeList(cuisineTypesChecked);
                }

            } else if (getActivity() instanceof FiltersActivity) {

                FiltersActivity filtersActivity = (FiltersActivity) getActivity();
                filtersActivity.setCuisineTypeList(cuisineTypesChecked);
            }
        }
    }

    public void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(getContext());
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public void addNewCuisineType(String cuisineId, String cuisineType) {

        CuisineType cuisineTypeObject = new CuisineType(cuisineId, cuisineType);

        cuisineTypes.add(cuisineTypeObject);
        cuisineTypeAdapter.notifyDataSetChanged();
    }
}