package com.os.foodie.ui.discount.add;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.discount.add.AddDiscountRequest;
import com.os.foodie.data.network.model.discount.dishlist.DishDatum;
import com.os.foodie.data.network.model.discount.list.DiscountList;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.ui.dialogfragment.dishlist.DishListDialogFragment;
import com.os.foodie.utils.AppConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class DiscountAddActivity extends BaseActivity implements DiscountAddMvpView, View.OnClickListener, DishNamesCallback, DatePickerDialog.OnDateSetListener {

    private AppCompatRadioButton rbMinType;
    private AppCompatRadioButton rbCourseType;

    private TextView tvFoodType;

    private EditText etMinAmount;
    private EditText etMinPercent;
    private EditText etFoodPercent;

    public static TextView tvStartDate;
    public static TextView tvEndDate;

    private Button btAdd;

    private ArrayList<DishDatum> dishlist;

    private Bundle bundle;

    private String dishIds = "";
    private String discountId = "";

    static String dateTag = "";

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private DiscountList discountList;

    private DiscountAddMvpPresenter<DiscountAddMvpView> discountAddMvpPresenter;
    boolean isAddButtonEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discount);

        initPresenter();
        discountAddMvpPresenter.onAttach(DiscountAddActivity.this);

        discountList = new DiscountList();

        initView();
    }

    private void initView() {

        onDateSetListener = this;

        rbMinType = (AppCompatRadioButton) findViewById(R.id.activity_add_discount_rb_min_type);
        rbCourseType = (AppCompatRadioButton) findViewById(R.id.activity_add_discount_rb_course_type);

        etMinAmount = (EditText) findViewById(R.id.activity_add_discount_et_min_amount);
        etMinPercent = (EditText) findViewById(R.id.activity_add_discount_et_min_percent);
        etFoodPercent = (EditText) findViewById(R.id.activity_add_discount_et_food_percent);
        tvFoodType = (TextView) findViewById(R.id.activity_add_discount_tv_food_course_type);

        tvStartDate = (TextView) findViewById(R.id.activity_add_discount_tv_start_date);
        tvEndDate = (TextView) findViewById(R.id.activity_add_discount_tv_end_date);

        btAdd = (Button) findViewById(R.id.activity_add_discount_bt_add_discount);

        tvFoodType.setOnClickListener(this);
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);

        btAdd.setOnClickListener(this);

        onLoad();
    }

    private void onLoad() {

        try {

            bundle = getIntent().getExtras();

            if (bundle != null) {

                discountList = (DiscountList) bundle.getSerializable(AppConstants.DISCOUNT_EDIT_DATA);
                discountId = discountList.getDiscountId();

                if (discountList.getDiscountType().compareToIgnoreCase("min_order") == 0) {

                    dishIds = "";
                    rbMinType.setChecked(true);
                    rbCourseType.setChecked(false);
                    etMinAmount.setText(discountList.getMinOrderAmount());
                    etMinPercent.setText(discountList.getDiscountPercentage());

                    etMinAmount.setEnabled(true);
                    etMinPercent.setEnabled(true);

                    tvFoodType.setEnabled(false);
                    etFoodPercent.setEnabled(false);

                } else {

                    rbMinType.setChecked(false);
                    rbCourseType.setChecked(true);

                    etFoodPercent.setText(discountList.getDiscountPercentage());

                    String names = "";
                    dishIds = "";

                    for (int i = 0; i < discountList.getDishes().size(); i++) {
                        dishIds = dishIds + "," + discountList.getDishes().get(i).getId();
                        names = names + "," + discountList.getDishes().get(i).getName();
                    }

                    if (names.length() > 0) {
                        dishIds = dishIds.substring(1);
                        names = names.substring(1);
                    }

                    tvFoodType.setText(names);

                    etMinAmount.setEnabled(false);
                    etMinPercent.setEnabled(false);
                    tvFoodType.setEnabled(true);
                    etFoodPercent.setEnabled(true);
                }

                tvStartDate.setText(discountList.getStartDate());
                tvEndDate.setText(discountList.getEndDate());

            } else {

                discountId = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        rbMinType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    dishIds = "";

                    rbCourseType.setChecked(false);

                    tvFoodType.setEnabled(false);

                    etMinAmount.setEnabled(true);
                    etMinPercent.setEnabled(true);
                    etFoodPercent.setEnabled(false);

                } else {

                    rbCourseType.setChecked(true);

                    tvFoodType.setEnabled(true);

                    etMinAmount.setEnabled(false);
                    etMinPercent.setEnabled(false);
                    etFoodPercent.setEnabled(true);
                }
            }
        });

        rbCourseType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    rbMinType.setChecked(false);

                    tvFoodType.setEnabled(true);

                    etMinAmount.setEnabled(false);
                    etMinPercent.setEnabled(false);
                    etFoodPercent.setEnabled(true);

                } else {

                    rbMinType.setChecked(true);

                    tvFoodType.setEnabled(false);

                    etMinAmount.setEnabled(true);
                    etMinPercent.setEnabled(true);
                    etFoodPercent.setEnabled(false);
                }
            }
        });
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        discountAddMvpPresenter = new DiscountAddPresenter(appDataManager, compositeDisposable);
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (v.getId() == tvFoodType.getId()) {

            discountAddMvpPresenter.showDishList();

        } else if (v.getId() == tvStartDate.getId()) {

            dateTag = "start";
            DatepickerFragment datePickerFragment = DatepickerFragment.newInstance(onDateSetListener);
            datePickerFragment.show(getSupportFragmentManager(), "date");

        } else if (v.getId() == tvEndDate.getId()) {

            dateTag = "end";
            DatepickerFragment datePickerFragment = DatepickerFragment.newInstance(onDateSetListener);
            datePickerFragment.show(getSupportFragmentManager(), "date_end");

        } else if (v.getId() == btAdd.getId()) {

            if (isAddButtonEnabled) {

                isAddButtonEnabled = false;
                Log.d("isAddButtonEnabled", "false");

                if (rbMinType.isChecked()) {

                    if (etMinAmount.getText().toString().trim().length() == 0) {

                        Snackbar snackbar = Snackbar.make(btAdd, getString(R.string.empty_minimum_amount), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        setAddButtonEnable();
                        return;

                    } else if (etMinPercent.getText().toString().trim().length() == 0) {

                        Snackbar snackbar = Snackbar.make(btAdd, getString(R.string.empty_percentage), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        setAddButtonEnable();
                        return;

                    } else if (tvStartDate.getText().toString().trim().length() == 0) {

                        Snackbar snackbar = Snackbar.make(btAdd, getString(R.string.empty_start_date), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        setAddButtonEnable();
                        return;

                    } else if (tvEndDate.getText().toString().trim().length() == 0) {

                        Snackbar snackbar = Snackbar.make(btAdd, getString(R.string.empty_end_date), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        setAddButtonEnable();
                        return;

                    } else {

                        AddDiscountRequest addDiscountRequest = new AddDiscountRequest();

                        addDiscountRequest.setDiscountId(discountId);
                        addDiscountRequest.setDiscountPercentage(etMinPercent.getText().toString().trim());
                        addDiscountRequest.setDiscountType("min_order");
                        addDiscountRequest.setDishId(dishIds);
                        addDiscountRequest.setStartDate(tvStartDate.getText().toString().trim());
                        addDiscountRequest.setEndDate(tvEndDate.getText().toString().trim());
                        addDiscountRequest.setMinOrderAmount(etMinAmount.getText().toString().trim());

                        discountAddMvpPresenter.addDiscount(addDiscountRequest);
                    }

                } else {

                    if (dishIds.length() == 0) {

                        Snackbar snackbar = Snackbar
                                .make(btAdd, getString(R.string.select_dish_name), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        setAddButtonEnable();
                        return;

                    } else if (etFoodPercent.getText().toString().trim().length() == 0) {

                        Snackbar snackbar = Snackbar
                                .make(btAdd, getString(R.string.empty_percentage), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        setAddButtonEnable();
                        return;

                    } else if (tvStartDate.getText().toString().trim().length() == 0) {

                        Snackbar snackbar = Snackbar
                                .make(btAdd, getString(R.string.empty_start_date), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        setAddButtonEnable();
                        return;

                    } else if (tvEndDate.getText().toString().trim().length() == 0) {

                        Snackbar snackbar = Snackbar
                                .make(btAdd, getString(R.string.empty_end_date), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        setAddButtonEnable();
                        return;

                    } else {

                        AddDiscountRequest addDiscountRequest = new AddDiscountRequest();

                        addDiscountRequest.setDiscountId(discountId);
                        addDiscountRequest.setDiscountPercentage(etFoodPercent.getText().toString().trim());
                        addDiscountRequest.setDiscountType("course_type");
                        addDiscountRequest.setDishId(dishIds);
                        addDiscountRequest.setStartDate(tvStartDate.getText().toString().trim());
                        addDiscountRequest.setEndDate(tvEndDate.getText().toString().trim());
                        addDiscountRequest.setMinOrderAmount("");

                        discountAddMvpPresenter.addDiscount(addDiscountRequest);
                    }
                }
            }
        }
    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void onDestroy() {
        discountAddMvpPresenter.dispose();
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
    public void setAddButtonEnable() {
        Log.d("isAddButtonEnabled", "true");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isAddButtonEnabled = true;
            }
        }, 1000);
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
    public void getDishIds(String dishIds, String dishNames) {
        this.dishIds = dishIds;
        tvFoodType.setText(dishNames);
    }

    public void openDishListDialogFragment() {

        Bundle bundle = new Bundle();

        bundle.putString(AppConstants.EXIST_DISH_IDS, dishIds);
        bundle.putParcelableArrayList(AppConstants.DISH_LIST_ARRAYLIST, dishlist);

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

            Calendar calender = Calendar.getInstance();

            if (dateTag.equals("start")) {

                if (!tvStartDate.getText().toString().equals("")) {

                    try {
                        calender = Calendar.getInstance();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        calender.setTime(sdf.parse(tvStartDate.getText().toString()));

                    } catch (Exception e) {
                        Log.d("Exception", ">>" + e.getMessage());
                    }
                }

            } else {

                if (!tvEndDate.getText().toString().equals("")) {

                    try {
                        calender = Calendar.getInstance();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        calender.setTime(sdf.parse(tvEndDate.getText().toString()));

                    } catch (Exception e) {
                        Log.d("Exception", ">>" + e.getMessage());
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

        Calendar cal = Calendar.getInstance();

        cal.set(year, month, dayOfMonth, 0, 0, 0);

        if (dateTag.equals("start")) {
            setDate(cal);
        } else {
            setDateend(cal);
        }
    }

    public void setDate(Calendar selected) {

        Calendar current = Calendar.getInstance();

        current.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        selected.set(selected.get(Calendar.YEAR), selected.get(Calendar.MONTH), selected.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        current.set(Calendar.MILLISECOND, 0);
        selected.set(Calendar.MILLISECOND, 0);

        Log.d("current", ">>" + current.getTimeInMillis());
        Log.d("selected", ">>" + selected.getTimeInMillis());

        Log.d("current", "YEAR>>" + current.get(Calendar.YEAR));
        Log.d("selected", "YEAR>>" + selected.get(Calendar.YEAR));

        Log.d("current", "MONTH>>" + current.get(Calendar.MONTH));
        Log.d("selected", "MONTH>>" + selected.get(Calendar.MONTH));

        Log.d("current", "DAY_OF_MONTH>>" + current.get(Calendar.DAY_OF_MONTH));
        Log.d("selected", "DAY_OF_MONTH>>" + selected.get(Calendar.DAY_OF_MONTH));

        Log.d("current", "HOUR_OF_DAY>>" + current.get(Calendar.HOUR_OF_DAY));
        Log.d("selected", "HOUR_OF_DAY>>" + selected.get(Calendar.HOUR_OF_DAY));

        Log.d("current", "MINUTE>>" + current.get(Calendar.MINUTE));
        Log.d("selected", "MINUTE>>" + selected.get(Calendar.MINUTE));

        Log.d("current", "SECOND>>" + current.get(Calendar.SECOND));
        Log.d("selected", "SECOND>>" + selected.get(Calendar.SECOND));

        Log.d("current", "MILLISECOND>>" + current.get(Calendar.MILLISECOND));
        Log.d("selected", "MILLISECOND>>" + selected.get(Calendar.MILLISECOND));

        if (current.getTimeInMillis() > selected.getTimeInMillis()) {
//        if (diff1 > 0) {

            Snackbar snackbar = Snackbar.make(tvStartDate, getString(R.string.empty_valid_date), Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tvStartDate.setText(dateFormat.format(selected.getTime()));
        }
    }

    public void setDateend(Calendar selected) {

        Calendar current = Calendar.getInstance();

        current.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        selected.set(selected.get(Calendar.YEAR), selected.get(Calendar.MONTH), selected.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        current.set(Calendar.MILLISECOND, 0);
        selected.set(Calendar.MILLISECOND, 0);

//        Date current = calender.getTime();
//        int diff1 = new Date().compareTo(current);

        if (current.getTimeInMillis() > selected.getTimeInMillis()) {
//        if (diff1 > 0) {

            Snackbar snackbar = Snackbar.make(tvEndDate, getString(R.string.empty_valid_date), Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tvEndDate.setText(dateFormat.format(selected.getTime()));
        }
    }
}