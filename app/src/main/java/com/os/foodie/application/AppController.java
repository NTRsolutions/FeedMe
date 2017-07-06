package com.os.foodie.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.home.customer.Filters;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.FontOverride;

import io.fabric.sdk.android.Fabric;
import io.reactivex.disposables.CompositeDisposable;
//import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AppController extends MultiDexApplication {

    private AppPreferencesHelper appPreferencesHelper;
    private AppApiHelpter appApiHelpter;
    private AppDataManager appDataManager;
    private CompositeDisposable compositeDisposable;

    private static GoogleAnalytics googleAnalytics;
    private static Tracker tracker;

    private Filters filters;

    public static AppController get(Activity activity) {
        return (AppController) activity.getApplication();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        AndroidNetworking.initialize(getApplicationContext());

        FontOverride.setDefaultFont(this, "DEFAULT", "fonts/dosis_book.ttf");
        FontOverride.setDefaultFont(this, "MONOSPACE", "fonts/dosis_semibold.ttf");
        FontOverride.setDefaultFont(this, "SERIF", "fonts/dosis_medium.ttf");
        FontOverride.setDefaultFont(this, "SANS_SERIF", "fonts/dosis_light.ttf");

//        Typekit.getInstance()
//                .addNormal(Typekit.createFromAsset(this, "fonts/dosis_book.ttf"))
//                .addCustom1(Typekit.createFromAsset(this, "fonts/dosis_semibold.ttf"));

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build());

        appApiHelpter = new AppApiHelpter();
        compositeDisposable = new CompositeDisposable();
        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);

        googleAnalytics = GoogleAnalytics.getInstance(this);
        filters = new Filters();
    }

    public AppPreferencesHelper getAppPreferencesHelper() {
        return appPreferencesHelper;
    }

    public AppApiHelpter getAppApiHelpter() {
        return appApiHelpter;
    }

    public AppDataManager getAppDataManager() {
        return appDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (tracker == null) {
            tracker = googleAnalytics.newTracker(R.xml.global_tracker);
            tracker.enableAutoActivityTracking(true);
//            tracker = googleAnalytics.newTracker(R.xml.global_tracker);
        }

        return tracker;
    }
}