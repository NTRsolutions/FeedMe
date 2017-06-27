package com.os.foodie.ui.mybasket;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.cart.clear.ClearCartRequest;
import com.os.foodie.data.network.model.cart.clear.ClearCartResponse;
import com.os.foodie.data.network.model.cart.remove.RemoveFromCartRequest;
import com.os.foodie.data.network.model.cart.remove.RemoveFromCartResponse;
import com.os.foodie.data.network.model.cart.update.UpdateCartRequest;
import com.os.foodie.data.network.model.cart.update.UpdateCartResponse;
import com.os.foodie.data.network.model.cart.view.ViewCartRequest;
import com.os.foodie.data.network.model.cart.view.ViewCartResponse;
import com.os.foodie.data.network.model.checkout.CheckoutRequest;
import com.os.foodie.data.network.model.checkout.CheckoutResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

public class MyBasketPresenter<V extends MyBasketMvpView> extends BasePresenter<V> implements MyBasketMvpPresenter<V> {

//    private TextView tvScheduledTime;
//    private Calendar selectedCalendar;
//
//    private final String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
//
//    private DatePickerDialog datePickerDialog;
//    private TimePickerDialog timePickerDialog;

    public MyBasketPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getMyBasketDetails(String userId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getCartDetails(new ViewCartRequest(userId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ViewCartResponse>() {
                        @Override
                        public void accept(ViewCartResponse viewCartResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (viewCartResponse.getResponse().getStatus() == 1) {

                                getMvpView().setMyBasket(viewCartResponse);

                            } else if (viewCartResponse.getResponse().getStatus() == 2) {
                                getMvpView().onError(viewCartResponse.getResponse().getMessage());

//                                TODO Clear Basket
//                                getMvpView().askForClearBasket();

                            } else {
                                getMvpView().onError(viewCartResponse.getResponse().getMessage());
//                                getMvpView().onError("No Restaurant found");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            Log.d("Error", ">>ErrThorwed");
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void removeFromMyBasket(String userId, String itemId, String restaurantId, final int position) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            Log.d("itemId", ">>" + itemId);

            getCompositeDisposable().add(getDataManager()
                    .removeFromCart(new RemoveFromCartRequest(userId, itemId, restaurantId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<RemoveFromCartResponse>() {
                        @Override
                        public void accept(RemoveFromCartResponse removeFromCartResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (removeFromCartResponse.getResponse().getStatus() == 1) {

                                getMvpView().itemRemovedFromBasket(position);
//                                getMvpView().setMyBasket(viewCartResponse);

                            } else {
                                getMvpView().onError(removeFromCartResponse.getResponse().getMessage());
//                                getMvpView().onError("No Restaurant found");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            Log.d("Error", ">>ErrThorwed");
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void updateMyBasket(String userId, String itemId, String restaurantId, final String qty, String price, final int position) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            Log.d("itemId", ">>" + itemId);

            getCompositeDisposable().add(getDataManager()
                    .updateCart(new UpdateCartRequest(userId, itemId, restaurantId, qty, price))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<UpdateCartResponse>() {
                        @Override
                        public void accept(UpdateCartResponse updateCartResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (updateCartResponse.getResponse().getStatus() == 1) {

                                getMvpView().updateMyBasket(position, qty, updateCartResponse.getResponse().getData().getTotalCartQuantity(), updateCartResponse.getResponse().getData().getTotalCartAmount());
//                                getMvpView().setMyBasket(viewCartResponse);

                            } else {
                                getMvpView().onError(updateCartResponse.getResponse().getMessage());
//                                getMvpView().onError("No Restaurant found");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            Log.d("Error", ">>ErrThorwed");
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void clearBasket() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .clearCart(new ClearCartRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ClearCartResponse>() {
                        @Override
                        public void accept(ClearCartResponse clearCartResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (clearCartResponse.getResponse().getStatus() == 1) {

                                getDataManager().setCustomerRestaurantId("");
                                getMvpView().onBasketClear();
                                getMvpView().onError(clearCartResponse.getResponse().getMessage());

                            } else {
                                getMvpView().onError(clearCartResponse.getResponse().getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            Log.d("Error", ">>ErrThorwed");
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    public void clearBasketRestaurant() {
        getDataManager().setCustomerRestaurantId("");
    }

    @Override
    public void checkout(CheckoutRequest checkoutRequest) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .checkoout(checkoutRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CheckoutResponse>() {
                        @Override
                        public void accept(CheckoutResponse checkoutResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (checkoutResponse.getResponse().getStatus() == 1) {

                                getDataManager().setCustomerRestaurantId("");

                                Log.d("getResponse", ">>" + checkoutResponse.getResponse().getMessage());

//                                getMvpView().onError(checkoutResponse.getResponse().getMessage());
                                getMvpView().onCheckoutComplete(checkoutResponse.getResponse().getMessage());

                            } else {
                                getMvpView().onError(checkoutResponse.getResponse().getMessage());
//                                getMvpView().onError("No Restaurant found");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }

    @Override
    public void onError(@StringRes int resId) {
        getMvpView().onError(resId);
    }


//
//    @Override
//    public void dateTimePickerDialog(Context context, final ViewCartResponse viewCartResponse, TextView tvScheduledTime) {
//
//        this.tvScheduledTime = tvScheduledTime;
//
//        selectedCalendar = Calendar.getInstance();
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//
//        final Calendar timeFrom = Calendar.getInstance();
//        final Calendar timeTo = Calendar.getInstance();
//
//        try {
//            timeFrom.setTime(simpleDateFormat.parse(viewCartResponse.getResponse().getOpeningTime()));
//            timeTo.setTime(simpleDateFormat.parse(viewCartResponse.getResponse().getClosingTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Calendar fromCalendar = Calendar.getInstance();
//        Calendar toCalendar = Calendar.getInstance();
//
//        toCalendar.setTimeInMillis(fromCalendar.getTimeInMillis() + (24 * 60 * 60 * 1000 * 7));
//
//        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                setTimePickerDialogListenerAction(MyBasketPresenter.this.tvScheduledTime, selectedCalendar, timeFrom, timeTo, hourOfDay, minute);
//            }
//        };
//
//        timePickerDialog = new TimePickerDialog(context, onTimeSetListener, fromCalendar.get(Calendar.HOUR_OF_DAY), fromCalendar.get(Calendar.MINUTE), false);
//
//        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
//                context.getString(R.string.alert_dialog_bt_ok),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == DialogInterface.BUTTON_POSITIVE) {
////                            dialog.cancel();
//                            setTimePickerDialogListenerAction(MyBasketPresenter.this.tvScheduledTime, selectedCalendar, timeFrom, timeTo, selectedCalendar.get(Calendar.HOUR_OF_DAY), selectedCalendar.get(Calendar.MINUTE));
//                        }
//                    }
//                });
//
//
//        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                setDatePickerDialogListenerAction(viewCartResponse, selectedCalendar, year, month, dayOfMonth);
//            }
//        };
//
//        datePickerDialog = new DatePickerDialog(context, onDateSetListener, fromCalendar.get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH), fromCalendar.get(Calendar.DAY_OF_MONTH));
//
//        datePickerDialog.getDatePicker().setMinDate(fromCalendar.getTimeInMillis());
//        datePickerDialog.getDatePicker().setMaxDate(toCalendar.getTimeInMillis());
//
//        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
//                context.getString(R.string.alert_dialog_bt_ok),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == DialogInterface.BUTTON_POSITIVE) {
////                            dialog.cancel();
//                            setDatePickerDialogListenerAction(viewCartResponse, selectedCalendar, selectedCalendar.get(Calendar.YEAR), selectedCalendar.get(Calendar.MONTH), selectedCalendar.get(Calendar.DAY_OF_MONTH));
//                        }
//                    }
//                });
//
//        datePickerDialog.show();
//    }
//
//    public void setDatePickerDialogListenerAction(ViewCartResponse viewCartResponse, Calendar selectedCalendar, int year, int month, int dayOfMonth) {
//
//
//        Calendar tempCalendar = Calendar.getInstance();
//
//        tempCalendar.set(Calendar.YEAR, year);
//        tempCalendar.set(Calendar.MONTH, month);
//        tempCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//        boolean isOk = false;
//        String[] selectedWeekDays = viewCartResponse.getResponse().getWorkingDays().split(",");
//
//        for (int i = 0; i < weekDays.length; i++) {
//
//            if (weekDays[tempCalendar.get(Calendar.DAY_OF_WEEK) - 1].equalsIgnoreCase(selectedWeekDays[i])) {
//                isOk = true;
//                break;
//            }
//        }
//
//        if (isOk) {
//
//            selectedCalendar.set(Calendar.YEAR, year);
//            selectedCalendar.set(Calendar.MONTH, month);
//            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//            datePickerDialog.dismiss();
//            timePickerDialog.show();
//
//        } else {
//            Log.d("Problem", "Restaurant don't open on this week day");
//            getMvpView().onError("Restaurant don't open on this week day");
//        }
//    }
//
//    public void setTimePickerDialogListenerAction(TextView tvScheduledTime, Calendar selectedCalendar, Calendar timeFrom, Calendar timeTo, int hourOfDay, int minute) {
//
//        Log.d("hourOfDay", ">>" + hourOfDay);
//        Log.d("minute", ">>" + minute);
//        Log.d("timeFrom HOUR_OF_DAY", ">>" + timeFrom.get(Calendar.HOUR_OF_DAY));
//        Log.d("timeFrom MINUTE", ">>" + timeFrom.get(Calendar.MINUTE));
//        Log.d("timeTo HOUR_OF_DAY", ">>" + timeTo.get(Calendar.HOUR_OF_DAY));
//        Log.d("timeTo MINUTE", ">>" + timeTo.get(Calendar.MINUTE));
//
//        if (hourOfDay < timeFrom.get(Calendar.HOUR_OF_DAY) || hourOfDay > timeTo.get(Calendar.HOUR_OF_DAY)) {
//
//            Log.d("Problem", "select time under restaurant opening and closing hours");
//            getMvpView().onError("select time under restaurant opening and closing hours");
//
//        } else if (hourOfDay == timeFrom.get(Calendar.HOUR_OF_DAY) && minute < timeFrom.get(Calendar.MINUTE)) {
//
//            Log.d("Problem", "select time under restaurant opening and closing hours");
//            getMvpView().onError("select time under restaurant opening and closing hours");
//
//        } else if (hourOfDay == timeTo.get(Calendar.HOUR_OF_DAY) && minute > timeTo.get(Calendar.MINUTE)) {
//
//            Log.d("Problem", "select time under restaurant opening and closing hours");
//            getMvpView().onError("select time under restaurant opening and closing hours");
//
//        } else {
//
//            selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//            selectedCalendar.set(Calendar.MINUTE, minute);
//
//            SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd-MM-yyyy");
//            SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm a");
//
//            tvScheduledTime.setText(simpleDateFormatDate.format(selectedCalendar.getTime()) + " at " + simpleDateFormatTime.format(selectedCalendar.getTime()));
//
//            timePickerDialog.dismiss();
//        }
//    }
}