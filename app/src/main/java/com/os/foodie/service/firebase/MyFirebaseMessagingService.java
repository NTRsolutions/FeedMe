package com.os.foodie.service.firebase;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.notification.NotificationFragments;
import com.os.foodie.ui.order.restaurant.detail.OrderHistoryDetailActivity;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListFragment;
import com.os.foodie.ui.setupprofile.restaurant.SetupRestaurantProfilePresenter;
import com.os.foodie.ui.splash.SplashActivity;
import com.os.foodie.utils.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    AppDataManager appDataManager;
    int id = (int) (System.currentTimeMillis() * (int) (Math.random() * 100));

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        initPresenter();

        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData().get("message"));
        checkNotificationType(remoteMessage);
    }

    public void checkNotificationType(RemoteMessage remoteMessage) {

        showPn(remoteMessage);
    }

    /////get activity name
    private String getCurrentTopActivity() {

        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
        return ar.topActivity.getClassName();
    }

    public void showPn(RemoteMessage remoteMessage) {
        String notiMsg = "";
        String notiTitle = "";

        notiMsg = remoteMessage.getData().get("message");
        notiTitle = getResources().getString(R.string.app_name);


        String notificationType = remoteMessage.getData().get("notification_type");
        String payload = remoteMessage.getData().get("payload");
        String orderId = "";
        String userType = "";

        try {
            JSONObject jsonObject = new JSONObject(payload);
            orderId = jsonObject.getString("order_id");
            userType = jsonObject.getString("user_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("orderId", ">>" + orderId);
        Log.d("userType", ">>" + userType);
        Log.d("notificationType", ">>" + notificationType);

        System.out.println("Notification msg is " + notiMsg);

        if (appDataManager.getCurrentUserType() != null && appDataManager.getCurrentUserType().equalsIgnoreCase(userType)) {

            if (AppConstants.NOTIFICATION_TYPE_ORDER_RECEIVED.equalsIgnoreCase(notificationType)) {

                if (RestaurantOrderListFragment.getInstance() != null && RestaurantOrderListFragment.getInstance().getActivity() != null) {
                    RestaurantOrderListFragment.getInstance().newOrder();
                }
            }

            if (NotificationFragments.getInstance() != null && NotificationFragments.getInstance().getActivity() != null) {
                Log.d("NotificationFragments", ">>newOrder");
                NotificationFragments.getInstance().newOrder();
            }

            long notificationTime = 0;
            notificationTime = System.currentTimeMillis();

            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder notification1 =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_profile)
                            .setLargeIcon(largeIcon)
                            .setColor(ContextCompat.getColor(this, R.color.orange))
                            .setContentTitle(notiTitle)
                            .setWhen(notificationTime)
                            .setDefaults(Notification.DEFAULT_ALL);

            Intent notificationIntent, parentIntent;

            if (userType.equalsIgnoreCase(AppConstants.CUSTOMER)) {
                parentIntent = new Intent(this, CustomerMainActivity.class);
            } else {
                parentIntent = new Intent(this, RestaurantMainActivity.class);
            }

            notificationIntent = new Intent(this, OrderHistoryDetailActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(OrderHistoryDetailActivity.class);

            Bundle notification_bundle = new Bundle();

            notification_bundle.putString("order_id", orderId);
            notification_bundle.putBoolean("showUpdateButton", setStatusButtonOnOrderHistory(userType, notificationType));

            notificationIntent.putExtras(notification_bundle);

            int id = (int) (System.currentTimeMillis() * (int) (Math.random() * 100));
            stackBuilder.addNextIntent(parentIntent);
            stackBuilder.addNextIntent(notificationIntent);
            //int requestID = (int) System.currentTimeMillis();
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);

            notification1
                    .setContentText(notiMsg)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(notiMsg))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .setContentTitle(notiTitle)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setVibrate(new long[]{100L, 100L, 200L, 500L});

            notificationManager.notify(id, notification1.build());
        }
    }

    boolean setStatusButtonOnOrderHistory(String userType, String notificationType) {

        boolean showStatus = false;

        if (userType.equalsIgnoreCase(AppConstants.CUSTOMER)) {
            showStatus = false;
        } else if (/*notificationType.equalsIgnoreCase("order_received") || */notificationType.equalsIgnoreCase("order_reject") || notificationType.equalsIgnoreCase("picked") || notificationType.equalsIgnoreCase("delivered")) {
            showStatus = false;
        } else {
            showStatus = true;
        }
        return showStatus;
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getBaseContext(), AppConstants.PREFERENCE_DEFAULT);

        appDataManager = new AppDataManager(getBaseContext(), appPreferencesHelper, appApiHelpter);
//        setupRestaurantProfileMvpPresenter = new SetupRestaurantProfilePresenter(appDataManager, compositeDisposable);
    }
}