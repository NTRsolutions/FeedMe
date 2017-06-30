package com.os.foodie.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.widget.TextView;

import com.os.foodie.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommonUtils {

    private static final String TAG = "CommonUtils";

    private CommonUtils() {
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.AlertDialog);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static ProgressDialog showLoadingDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.AlertDialog);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        ((TextView) progressDialog.findViewById(R.id.pb_message)).setText(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static String ConvertDate(String startdate,String enddate)
    {
        String convert_date="";
        try {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date_start = dt.parse(startdate);
            Date date_end = dt.parse(enddate);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM");
            convert_date=dt1.format(date_start)+" to "+dt1.format(date_end);

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return convert_date;
    }
}
