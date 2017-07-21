package com.os.foodie.ui.notification;

import com.os.foodie.data.network.model.merchantdetails.get.GetMerchantDetailResponse;
import com.os.foodie.data.network.model.notification.NotificationListResponse;
import com.os.foodie.ui.base.MvpView;

public interface NotificationMvpView extends MvpView {

    void setNotificationList(NotificationListResponse notificationList);

}