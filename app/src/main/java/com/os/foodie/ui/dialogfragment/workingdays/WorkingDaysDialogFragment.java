package com.os.foodie.ui.dialogfragment.workingdays;

import android.os.Bundle;
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
import com.os.foodie.model.WorkingDay;
import com.os.foodie.ui.adapter.recyclerview.WorkingDaysAdapter;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.setupprofile.restaurant.SetupRestaurantProfileFragment;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

public class WorkingDaysDialogFragment extends DialogFragment implements View.OnClickListener {

    private ImageView ivDone;
    private RecyclerView recyclerView;

    //    private String[] weekDays;
    private ArrayList<WorkingDay> workingDays;
    private WorkingDaysAdapter workingDaysAdapter;

    @Override
    public void onStart() {
        super.onStart();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();

        lp.copyFrom(window.getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        window.setAttributes(lp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_fragment_working_days, container, false);

        ivDone = (ImageView) rootView.findViewById(R.id.dialog_fragment_working_days_iv_done);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.dialog_fragment_working_days_recycler_view);

//        initializeWorkingDays();
        workingDays = getArguments().getParcelableArrayList(AppConstants.WORKING_DAYS_ARRAYLIST);

        workingDaysAdapter = new WorkingDaysAdapter(getContext(), workingDays);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(workingDaysAdapter);

        ivDone.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (ivDone.getId() == v.getId()) {

            Log.d("onClick", "ivDone");
            Log.d("cuisineTypesSize", ">>" + workingDays.size());

            ArrayList<WorkingDay> workingDaysChecked = new ArrayList<WorkingDay>();
            workingDaysChecked.addAll(workingDays);
//
//            for (int i = 0; i < workingDays.size(); i++) {
//
//                Log.d("isChecked", ">>" + workingDays.get(i).isChecked());
//
//                if (workingDays.get(i).isChecked()) {
//
//                    Log.d("Checked", ">>" + workingDays.get(i).getDay());
//                    workingDaysChecked.add(workingDays.get(i));
//                }
//            }

            dismiss();

            RestaurantMainActivity restaurantMainActivity = (RestaurantMainActivity) getActivity();
            Fragment fragment = restaurantMainActivity.getCurrentFragment();

            if (fragment instanceof SetupRestaurantProfileFragment) {
                ((SetupRestaurantProfileFragment) fragment).setWorkingDays(workingDaysChecked);

            }//            ((SetupRestaurantProfileFragment) getActivity()).setWorkingDays(workingDays);
        }
    }
}