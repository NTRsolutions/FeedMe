package com.os.foodie.ui.notification;

import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.merchantdetails.set.SetMerchantDetailRequest;
import com.os.foodie.ui.base.MvpPresenter;
import com.os.foodie.ui.merchantdetail.MerchantDetailMvpView;

public interface NotificationMvpPresenter<V extends NotificationMvpView> extends MvpPresenter<V> {


    void getNotificationList(String userId, String restaurantId);
    void readNotification(String notification_id);
    DataManager getDataManager();



}