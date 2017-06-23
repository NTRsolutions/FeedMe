package com.os.foodie.ui.filters;

import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

//import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
//import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineType;
import com.os.foodie.data.network.model.home.customer.Filters;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.dialogfragment.cuisine.list.CuisineTypeDialogFragment;
import com.os.foodie.utils.AppConstants;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FiltersActivity extends BaseActivity implements FiltersMvpView, View.OnClickListener {

    private RangeSeekBar seekbarMinimumOrderAmount, seekbarDistance;
    private CheckedTextView ctvOpen, ctvClose, ctvDeliver, ctvPickup, ctvOffer;
    private EditText etCuisineType;
    private RatingBar ratingBar;
    private Button btSearch;

    public ArrayList<CuisineType> cuisineTypesChecked;
    private CuisineTypeDialogFragment cuisineTypeDialogFragment;

    private FiltersMvpPresenter<FiltersMvpView> filtersMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        filtersMvpPresenter = new FiltersPresenter(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        filtersMvpPresenter.onAttach(this);

        cuisineTypesChecked = new ArrayList<>();

        seekbarMinimumOrderAmount = (RangeSeekBar) findViewById(R.id.activity_filters_seekbar_minimum_order_amount);
        seekbarDistance = (RangeSeekBar) findViewById(R.id.activity_filters_seekbar_distance);

        ctvOpen = (CheckedTextView) findViewById(R.id.activity_filters_ctv_open);
        ctvClose = (CheckedTextView) findViewById(R.id.activity_filters_ctv_close);
        ctvDeliver = (CheckedTextView) findViewById(R.id.activity_filters_ctv_deliver);
        ctvPickup = (CheckedTextView) findViewById(R.id.activity_filters_ctv_pickup);
        ctvOffer = (CheckedTextView) findViewById(R.id.activity_filters_ctv_offer);

        etCuisineType = (EditText) findViewById(R.id.activity_filters_et_cuisine_type);

        ratingBar = (RatingBar) findViewById(R.id.activity_filters_rb_rating);

        btSearch = (Button) findViewById(R.id.activity_filters_bt_search);

        setOnRatingChangedListener();

        ctvOpen.setOnClickListener(this);
        ctvClose.setOnClickListener(this);
        ctvDeliver.setOnClickListener(this);
        ctvPickup.setOnClickListener(this);
        ctvOffer.setOnClickListener(this);

        etCuisineType.setOnClickListener(this);
        btSearch.setOnClickListener(this);

        setUp();
    }

    public void clearFilter() {

        seekbarMinimumOrderAmount.setSelectedMinValue(0);
        seekbarMinimumOrderAmount.setSelectedMaxValue(5000);

        seekbarDistance.setSelectedMinValue(0);
        seekbarDistance.setSelectedMaxValue(10);

        etCuisineType.setText("");
        cuisineTypesChecked.clear();

        ctvOpen.setChecked(false);
        ctvClose.setChecked(false);

        ctvDeliver.setChecked(false);
        ctvPickup.setChecked(false);

        ratingBar.setRating(0);

        ctvOffer.setChecked(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filters, menu);
        return true;
    }

    public void setCuisineTypeList(ArrayList<CuisineType> cuisineTypesChecked) {

        this.cuisineTypesChecked = cuisineTypesChecked;

        String cuisionTypes = "";

        for (int i = 0; i < cuisineTypesChecked.size(); i++) {

            if (i == 0) {
                cuisionTypes = cuisineTypesChecked.get(i).getName();
            } else {
                cuisionTypes = cuisionTypes + "," + cuisineTypesChecked.get(i).getName();
            }
        }

        etCuisineType.setText(cuisionTypes);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_clear) {
            AppController.get(this).setFilters(new Filters());
            clearFilter();
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        if (etCuisineType.getId() == v.getId()) {

            filtersMvpPresenter.onCuisineTypeClick();

        } else if (ctvOpen.getId() == v.getId()) {

            if (ctvOpen.isChecked()) {
                ctvOpen.setChecked(false);
            } else {
                ctvOpen.setChecked(true);
            }

        } else if (ctvClose.getId() == v.getId()) {

            if (ctvClose.isChecked()) {
                ctvClose.setChecked(false);
            } else {
                ctvClose.setChecked(true);
            }

        } else if (ctvDeliver.getId() == v.getId()) {

            if (ctvDeliver.isChecked()) {
                ctvDeliver.setChecked(false);
            } else {
                ctvDeliver.setChecked(true);
            }

        } else if (ctvPickup.getId() == v.getId()) {

            if (ctvPickup.isChecked()) {
                ctvPickup.setChecked(false);
            } else {
                ctvPickup.setChecked(true);
            }

        } else if (ctvOffer.getId() == v.getId()) {

            if (ctvOffer.isChecked()) {
                ctvOffer.setChecked(false);
            } else {
                ctvOffer.setChecked(true);
            }
        } else if (btSearch.getId() == v.getId()) {

            setFilters();
            AppController.get(this).getFilters();
            finish();
//            filtersMvpPresenter;
        }
    }

    public void setFilters() {

        Filters filters = new Filters();

        filters.setMinOrderAmount(seekbarMinimumOrderAmount.getSelectedMinValue().toString());
        filters.setMaxOrderAmount(seekbarMinimumOrderAmount.getSelectedMaxValue().toString());

        filters.setMinDistance(seekbarDistance.getSelectedMinValue().toString());
        filters.setMaxDistance(seekbarDistance.getSelectedMaxValue().toString());

        filters.setClear(false);

        if (ctvPickup.isChecked() || ctvDeliver.isChecked()) {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            filters.setTime(simpleDateFormat.format(calendar.getTime()));
        }

        if (cuisineTypesChecked != null && cuisineTypesChecked.size() > 0) {

            String cuisineTypes = "";
            String cuisineNames = "";

            for (int i = 0; i < cuisineTypesChecked.size(); i++) {

                if (i == 0) {
                    cuisineTypes = cuisineTypesChecked.get(i).getId();
                    cuisineNames = cuisineTypesChecked.get(i).getName();
                } else {
                    cuisineTypes += "," + cuisineTypesChecked.get(i).getId();
                    cuisineNames += "," + cuisineTypesChecked.get(i).getName();
                }
            }

            filters.setCuisineTypes(cuisineTypes);
            filters.setCuisineNames(cuisineNames);
        }

        AppController.get(this).setFilters(filters);
    }

    @Override
    protected void setUp() {

        Filters filters = AppController.get(this).getFilters();

        if (!filters.isClear()) {

            Log.d("filters", ">>Set");

            if (filters.getMinOrderAmount() != null && !filters.getMinOrderAmount().isEmpty()) {
//                tvMinimumOrderMin.setText(filters.getMinOrderAmount());

                Log.d("getMinOrderAmount", ">>" + filters.getMinOrderAmount());
                seekbarMinimumOrderAmount.setSelectedMinValue(Integer.parseInt(filters.getMinOrderAmount()));
//                seekbarMinimumOrderAmount.setMinStartValue(Float.parseFloat(filters.getMinOrderAmount()));
            }

            if (filters.getMaxOrderAmount() != null && !filters.getMaxOrderAmount().isEmpty()) {
//                tvMinimumOrderMax.setText(filters.getMaxOrderAmount());

                Log.d("getMaxOrderAmount", ">>" + filters.getMaxOrderAmount());
                seekbarMinimumOrderAmount.setSelectedMaxValue(Integer.parseInt(filters.getMaxOrderAmount()));
//                seekbarMinimumOrderAmount.setMaxStartValue(Float.parseFloat(filters.getMaxOrderAmount()));
            }

            if (filters.getMinDistance() != null && !filters.getMinDistance().isEmpty()) {
//                tvDistanceMin.setText(filters.getMinDistance());
                seekbarDistance.setSelectedMinValue(Integer.parseInt(filters.getMinDistance()));
//                seekbarDistance.setMinValue(Float.parseFloat(filters.getMinDistance()));
            }

            if (filters.getMaxDistance() != null && !filters.getMaxDistance().isEmpty()) {
//                tvDistanceMax.setText(filters.getMaxDistance());
                seekbarDistance.setSelectedMaxValue(Integer.parseInt(filters.getMaxDistance()));
//                seekbarDistance.setMaxValue(Float.parseFloat(filters.getMaxDistance()));
            }

            if (filters.getCuisineTypes() != null && !filters.getCuisineTypes().isEmpty()) {

                String cuisineTypes[] = filters.getCuisineTypes().split(",");
                String cuisineNames[] = filters.getCuisineNames().split(",");

                etCuisineType.setText(filters.getCuisineNames());

                for (int i = 0; i < cuisineTypes.length; i++) {

                    CuisineType cuisineType = new CuisineType();

                    cuisineType.setId(cuisineTypes[i]);
                    cuisineType.setName(cuisineNames[i]);
                    cuisineType.setChecked(true);

                    cuisineTypesChecked.add(cuisineType);
                }
            }
        }
    }

    @Override
    public void onCuisineTypeListReceive(ArrayList<CuisineType> cuisineTypes) {

        for (int i = 0; i < cuisineTypesChecked.size(); i++) {

            for (int j = 0; j < cuisineTypes.size(); j++) {

                if (cuisineTypesChecked.get(i).getId().equals(cuisineTypes.get(j).getId())) {

                    cuisineTypes.get(j).setChecked(true);
                    break;
                }
            }
        }

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AppConstants.CUISINE_TYPES_ARRAYLIST, cuisineTypes);
        bundle.putBoolean(AppConstants.IS_FAB_NEEDED, false);

        cuisineTypeDialogFragment = new CuisineTypeDialogFragment();
        cuisineTypeDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
//        cuisineTypeDialogFragment.setTargetFragment(cuisineTypeDialogFragment, CUISINE_TYPES_REQUEST);
        cuisineTypeDialogFragment.setArguments(bundle);
        cuisineTypeDialogFragment.show(getSupportFragmentManager(), "CuisineTypeDialogFragment");
    }

    @Override
    protected void onDestroy() {
        filtersMvpPresenter.onDetach();
        super.onDestroy();
    }

    public void setOnRangeSeekbarChangeListenerMinimumAmount() {

//        seekbarMinimumOrderAmount.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
//            @Override
//            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
//
//            }
//        });

//        seekbarMinimumOrderAmount.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
//            @Override
//            public void valueChanged(Number minValue, Number maxValue) {
//                tvMinimumOrderMin.setText(String.valueOf(minValue));
//                tvMinimumOrderMax.setText(String.valueOf(maxValue));
//            }
//        });
    }

    public void setOnRangeSeekbarChangeListenerDistance() {

//        seekbarDistance.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
//            @Override
//            public void valueChanged(Number minValue, Number maxValue) {
//                tvDistanceMin.setText(String.valueOf(minValue));
//                tvDistanceMax.setText(String.valueOf(maxValue));
//            }
//        });
    }

    public void setOnRatingChangedListener() {

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.d("rating", ">>" + rating);
            }
        });
    }
}