package com.os.foodie.ui.dialogfragment.dishlist;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.discount.dishlist.DishDatum;
import com.os.foodie.ui.adapter.recyclerview.DishListAdapter;
import com.os.foodie.ui.discount.add.DishNamesCallback;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

public class DishListDialogFragment extends DialogFragment implements View.OnClickListener {

    private ImageView ivDone;
    private RecyclerView recyclerView;
    private TextView title;

    //    private String[] weekDays;
    private ArrayList<DishDatum> dishData;
    private DishListAdapter dishListAdapter;
    DishNamesCallback dishNamesCallback;
    String dish_ids = "",dish_names="";

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
        title = (TextView) rootView.findViewById(R.id.title);

        title.setText(getString(R.string.add_discount_tv_select_dish_text));
//        initializeWorkingDays();
        dishData = getArguments().getParcelableArrayList(AppConstants.DISH_LIST_ARRAYLIST);
        dish_ids = getArguments().getString(AppConstants.EXIST_DISH_IDS);

        dishListAdapter = new DishListAdapter(getContext(), dishData);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dishListAdapter);

        ivDone.setOnClickListener(this);

        dishNamesCallback=(DishNamesCallback)getActivity();



        ArrayList<String> ids = new ArrayList<>();
        ids.clear();
        if (dish_ids.length() > 0) {
            if (dish_ids.contains(",")) {
                for (int i = 0; i < dish_ids.split(",").length; i++) {
                    ids.add(dish_ids.split(",")[i]);
                }
            } else {
                ids.add(dish_ids);
            }
        }

        for (int i = 0; i < dishData.size(); i++) {
            if (ids.contains(dishData.get(i).getDishId())) {
                dishData.get(i).setChecked(true);
            }

        }
        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (ivDone.getId() == v.getId()) {

            dish_ids="";
            dish_names="";
            for (int i = 0; i < dishData.size(); i++)
            {

                if (dishData.get(i).isChecked()) {
                    dish_ids = dish_ids + "," + dishData.get(i).getDishId();
                    dish_names = dish_names + "," + dishData.get(i).getName();
                }
            }

            if(dish_ids.length()>0)
            {
                dish_ids = dish_ids.substring(1);
                dish_names = dish_names.substring(1);
            }
            dishNamesCallback.getDishIds(dish_ids,dish_names);
            dismiss();

          /*  RestaurantMainActivity restaurantMainActivity = (RestaurantMainActivity) getActivity();
            Fragment fragment = restaurantMainActivity.getCurrentFragment();

            if (fragment instanceof SetupRestaurantProfileFragment)
            {
                ((SetupRestaurantProfileFragment) fragment).setWorkingDays(workingDaysChecked);

            }//            ((SetupRestaurantProfileFragment) getActivity()).setWorkingDays(workingDays);*/
        }
    }
}