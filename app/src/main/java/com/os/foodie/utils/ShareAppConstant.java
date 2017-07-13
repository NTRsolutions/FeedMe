package com.os.foodie.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by hemendrag on 1/20/2016.
 */
public class ShareAppConstant {

    /**
     * PlayStore fprwarding link URL
     */
    public  static String playStoreURL="market://details?id=";
    public  static String INTENT_TYPE="image/*";

    /**
     * Packages name of application at which the data to be shared
     */
    public static String PACKAGE_TWITTER="com.twitter.android";
    public static String PACKAGE_GMAIL="com.google.android.gm";






    /**
     * Setectting app is installed or not in the device
     * @param appPackageName
     * @param context
     * @return
     */

    public static boolean isAppInstalled(String appPackageName , Context context) {
          PackageManager pm = context.getPackageManager();
         boolean app_installed;
        try {
              pm.getPackageInfo(appPackageName, PackageManager.GET_ACTIVITIES);
                app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
             app_installed = false;
        }
         return app_installed;
    }



}
