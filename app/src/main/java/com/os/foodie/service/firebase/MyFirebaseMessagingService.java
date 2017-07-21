package com.os.foodie.service.firebase;

/**
 * Created by abhinava on 9/12/2016.
 */

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.os.foodie.R;
import com.os.foodie.ui.order.restaurant.detail.OrderHistoryDetailActivity;
import com.os.foodie.ui.splash.SplashActivity;
import com.os.foodie.utils.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Belal on 5/27/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    int id = (int) (System.currentTimeMillis() * (int) (Math.random() * 100));

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

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

        System.out.println("Notification msg is " + notiMsg);

        int icon = 0;
        long notificationTime = 0;
        icon = R.mipmap.ic_launcher;
        notificationTime = System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notification1 =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(icon)
                        .setContentTitle(notiTitle)
                        .setWhen(notificationTime)
                        .setDefaults(Notification.DEFAULT_ALL);

       /* if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            notification1.setSmallIcon(R.mipmap.noti_logo);
        }*/

        String notificationType = remoteMessage.getData().get("notification_type");
        String payload = remoteMessage.getData().get("payload");
        String orderId="";

        String userType="";
        try {
            JSONObject jsonObject=new JSONObject(payload);
            orderId=jsonObject.getString("order_id");
            userType=jsonObject.getString("user_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent notificationIntent;

         notificationIntent = new Intent(this, OrderHistoryDetailActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(OrderHistoryDetailActivity.class);
        Bundle notification_bundle = new Bundle();

        notification_bundle.putString("order_id", orderId);
        notification_bundle.putBoolean("showUpdateButton", setStatusButtonOnOrderHistory(userType,notificationType));

        notificationIntent.putExtras(notification_bundle);


        int id = (int) (System.currentTimeMillis() * (int) (Math.random() * 100));
        stackBuilder.addNextIntent(notificationIntent);
        //int requestID = (int) System.currentTimeMillis();
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

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



    boolean setStatusButtonOnOrderHistory(String userType,String notificationType){
        boolean showStatus=false;
        if(userType.equalsIgnoreCase(AppConstants.CUSTOMER)){
            showStatus=false;
        }
        else if(notificationType.equalsIgnoreCase("order_received") || notificationType.equalsIgnoreCase("order_reject") || notificationType.equalsIgnoreCase("picked") || notificationType.equalsIgnoreCase("delivered")) {
            showStatus = false;
        }else{
            showStatus = true;
        }
        return  showStatus;
    }

}
