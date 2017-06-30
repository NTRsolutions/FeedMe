package com.os.foodie.ui.discount.add;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.discount.add.AddDiscountRequest;
import com.os.foodie.data.network.model.discount.dishlist.DishDatum;
import com.os.foodie.data.network.model.discount.list.DiscountList;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.dialogfragment.dishlist.DishListDialogFragment;
import com.os.foodie.utils.AppConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DiscountAddActivity extends BaseActivity implements DiscountAddMvpView, View.OnClickListener, DishNamesCallback, DatePickerDialog.OnDateSetListener {
    private DiscountAddMvpPresenter<DiscountAddMvpView> discountAddMvpPresenter;
    private AppCompatRadioButton minTypeRadioBt;
    private EditText minAmountEt;
    private EditText minPercentEt;
    private AppCompatRadioButton courseTypeRadioBt;
    private TextView foodTypeTv;
    private EditText foodPercentEt;
    public static TextView startDateTv;
    public static TextView endDateTv;
    private RippleAppCompatButton discountAddButton;
    ArrayList<DishDatum> dishlist;
    String dish_ids = "";
    DatePickerDialog.OnDateSetListener onDateSetListener;
    static String date_tag = "";
    DiscountList discountList = new DiscountList();
    Bundle bundle;
    String discount_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_discount_activity);

        initPresenter();
        discountAddMvpPresenter.onAttach(DiscountAddActivity.this);
        initView();
    }

    private void initView() {
        onDateSetListener = this;
        minTypeRadioBt = (AppCompatRadioButton) findViewById(R.id.min_type_radio_bt);
        minAmountEt = (EditText) findViewById(R.id.min_amount_et);
        minPercentEt = (EditText) findViewById(R.id.min_percent_et);
        courseTypeRadioBt = (AppCompatRadioButton) findViewById(R.id.course_type_radio_bt);
        foodTypeTv = (TextView) findViewById(R.id.food_type_tv);
        foodPercentEt = (EditText) findViewById(R.id.food_percent_et);
        startDateTv = (TextView) findViewById(R.id.start_date_tv);
        endDateTv = (TextView) findViewById(R.id.end_date_tv);
        discountAddButton = (RippleAppCompatButton) findViewById(R.id.discount_add_button);

        foodTypeTv.setOnClickListener(this);
        startDateTv.setOnClickListener(this);
        endDateTv.setOnClickListener(this);
        discountAddButton.setOnClickListener(this);
        onLoad();
    }

    private void onLoad() {
        try {


            bundle = getIntent().getExtras();
            if (bundle != null) {
                discountList = (DiscountList) bundle.getSerializable(AppConstants.DISCOUNT_EDIT_DATA);
                discount_id = discountList.getDiscountId();
                //min_order/course_type
                if (discountList.getDiscountType().compareToIgnoreCase("min_order") == 0) {
                    dish_ids = "";
                    minTypeRadioBt.setChecked(true);
                    courseTypeRadioBt.setChecked(false);
                    minAmountEt.setText(discountList.getMinOrderAmount());
                    minPercentEt.setText(discountList.getDiscountPercentage());

                    minAmountEt.setEnabled(true);
                    minPercentEt.setEnabled(true);

                    foodTypeTv.setEnabled(false);
                    foodPercentEt.setEnabled(false);
                } else {
                    minTypeRadioBt.setChecked(false);
                    courseTypeRadioBt.setChecked(true);
                    foodPercentEt.setText(discountList.getDiscountPercentage());
                    String names = "";
                    dish_ids = "";
                    for (int i = 0; i < discountList.getDishes().size(); i++) {
                        dish_ids = dish_ids + "," + discountList.getDishes().get(i).getId();
                        names = names + "," + discountList.getDishes().get(i).getName();
                    }

                    if (names.length() > 0) {
                        dish_ids = dish_ids.substring(1);
                        names = names.substring(1);
                    }
                    foodTypeTv.setText(names);

                    minAmountEt.setEnabled(false);
                    minPercentEt.setEnabled(false);

                    foodTypeTv.setEnabled(true);
                    foodPercentEt.setEnabled(true);

                }
                startDateTv.setText(discountList.getStartDate());
                endDateTv.setText(discountList.getEndDate());
            } else {
                discount_id = "";
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        minTypeRadioBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    dish_ids = "";
                    courseTypeRadioBt.setChecked(false);
                    minAmountEt.setEnabled(true);
                    minPercentEt.setEnabled(true);

                    foodTypeTv.setEnabled(false);
                    foodPercentEt.setEnabled(false);
                } else {
                    courseTypeRadioBt.setChecked(true);
                    minAmountEt.setEnabled(false);
                    minPercentEt.setEnabled(false);

                    foodTypeTv.setEnabled(true);
                    foodPercentEt.setEnabled(true);
                }
            }
        });

        courseTypeRadioBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    minTypeRadioBt.setChecked(false);
                    minAmountEt.setEnabled(false);
                    minPercentEt.setEnabled(false);

                    foodTypeTv.setEnabled(true);
                    foodPercentEt.setEnabled(true);
                } else {
                    minTypeRadioBt.setChecked(true);
                    minAmountEt.setEnabled(true);
                    minPercentEt.setEnabled(true);

                    foodTypeTv.setEnabled(false);
                    foodPercentEt.setEnabled(false);
                }
            }
        });
    }

    public void initPresenter() {

        discountAddMvpPresenter = new DiscountAddPresenter<>(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();
        if (v.getId() == foodTypeTv.getId()) {
            discountAddMvpPresenter.showDishList();
        }
        if (v.getId() == startDateTv.getId()) {
            date_tag = "start";
            DatepickerFragment datePickerFragment = DatepickerFragment.newInstance(onDateSetListener);
            datePickerFragment.show(getSupportFragmentManager(), "date");
        }
        if (v.getId() == endDateTv.getId()) {
            date_tag = "end";
            DatepickerFragment datePickerFragment = DatepickerFragment.newInstance(onDateSetListener);
            datePickerFragment.show(getSupportFragmentManager(), "date_end");
        }
        if (v.getId() == discountAddButton.getId()) {
            if (minTypeRadioBt.isChecked()) {
                if (minAmountEt.getText().toString().trim().length() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(discountAddButton, getString(R.string.enter_minimum_amount), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                } else if (minPercentEt.getText().toString().trim().length() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(discountAddButton, getString(R.string.enter_percentage), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                } else if (startDateTv.getText().toString().trim().length() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(discountAddButton, getString(R.string.enter_start_date), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                } else if (endDateTv.getText().toString().trim().length() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(discountAddButton, getString(R.string.enter_end_date), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                } else {
                    AddDiscountRequest addDiscountRequest = new AddDiscountRequest();
                    addDiscountRequest.setDiscountId(discount_id);
                    addDiscountRequest.setDiscountPercentage(minPercentEt.getText().toString().trim());
                    addDiscountRequest.setDiscountType("min_order");
                    addDiscountRequest.setDishId(dish_ids);
                    addDiscountRequest.setStartDate(startDateTv.getText().toString().trim());
                    addDiscountRequest.setEndDate(endDateTv.getText().toString().trim());
                    addDiscountRequest.setMinOrderAmount(minAmountEt.getText().toString().trim());
                    discountAddMvpPresenter.addDiscount(addDiscountRequest);
                }
            } else {
                if (dish_ids.length() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(discountAddButton, getString(R.string.select_dish_name), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                } else if (foodPercentEt.getText().toString().trim().length() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(discountAddButton, getString(R.string.enter_percentage), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                } else if (startDateTv.getText().toString().trim().length() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(discountAddButton, getString(R.string.enter_start_date), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                } else if (endDateTv.getText().toString().trim().length() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(discountAddButton, getString(R.string.enter_end_date), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                } else {
                    AddDiscountRequest addDiscountRequest = new AddDiscountRequest();
                    addDiscountRequest.setDiscountId(discount_id);
                    addDiscountRequest.setDiscountPercentage(foodPercentEt.getText().toString().trim());
                    addDiscountRequest.setDiscountType("course_type");
                    addDiscountRequest.setDishId(dish_ids);
                    addDiscountRequest.setStartDate(startDateTv.getText().toString().trim());
                    addDiscountRequest.setEndDate(endDateTv.getText().toString().trim());
                    addDiscountRequest.setMinOrderAmount("");
                    discountAddMvpPresenter.addDiscount(addDiscountRequest);
                }
            }
        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void onDestroy() {
        discountAddMvpPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_with_close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_close) {
            finish();
        }

        return true;
    }

    @Override
    public void onShowDishList(ArrayList<DishDatum> dishlist) {
        this.dishlist = dishlist;
        openDishListDialogFragment();

    }

    @Override
    public void onFinish() {
        finish();
    }

    @Override
    public void getDishIds(String dish_ids, String dish_names) {
        this.dish_ids = dish_ids;
        foodTypeTv.setText(dish_names);
    }

    public void openDishListDialogFragment() {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AppConstants.DISH_LIST_ARRAYLIST, dishlist);
        bundle.putString(AppConstants.EXIST_DISH_IDS, dish_ids);

        DishListDialogFragment dishListDialogFragment = new DishListDialogFragment();
        dishListDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
        dishListDialogFragment.setArguments(bundle);
        dishListDialogFragment.show(getSupportFragmentManager(), "DishListDialogFragment");
    }

    public static class DatepickerFragment extends DialogFragment {

        DatePickerDialog.OnDateSetListener onDateSetListener;

        public static DatepickerFragment newInstance(DatePickerDialog.OnDateSetListener onDateSetListener) {
            DatepickerFragment datepickerFragment = new DatepickerFragment();
            datepickerFragment.onDateSetListener = onDateSetListener;
            return datepickerFragment;
        }

        public Dialog onCreateDialog(Bundle savedInstance) {

            // create Calendar Instance from Calendar class

            Calendar calender = Calendar.getInstance();
            if (date_tag.equals("start"))
            {
                if (!startDateTv.getText().toString().equals(""))
                {
                    try
                    {
                        calender = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        calender.setTime(sdf.parse(startDateTv.getText().toString()));
                    } catch (Exception e) {

                    }
                }
            } else {
                if (!endDateTv.getText().toString().equals(""))
                {
                    try
                    {
                        calender = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        calender.setTime(sdf.parse(endDateTv.getText().toString()));
                    } catch (Exception e) {

                    }
                }

            }

            int year = calender.get(Calendar.YEAR);
            int month = calender.get(Calendar.MONTH);
            int day = calender.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);

        if (date_tag.equals("start")) {
            setDate(cal);
        } else {
            setDateend(cal);
        }
    }

    public void setDate(Calendar calender) {
        Date current = calender.getTime();
        int diff1 = new Date().compareTo(current);
        if (diff1 > 0) {
            Snackbar snackbar = Snackbar
                    .make(startDateTv, getString(R.string.enter_valid_date), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDateTv.setText(dateFormat.format(calender.getTime()));
        }
    }


    public void setDateend(Calendar calender) {
        Date current = calender.getTime();
        int diff1 = new Date().compareTo(current);
        if (diff1 > 0) {
            Snackbar snackbar = Snackbar
                    .make(endDateTv, getString(R.string.enter_valid_date), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            endDateTv.setText(dateFormat.format(calender.getTime()));
        }
    }
}