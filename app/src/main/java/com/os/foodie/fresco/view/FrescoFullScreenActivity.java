package com.os.foodie.fresco.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.os.foodie.R;
import com.os.foodie.fresco.adapter.FrescoAdapter;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

public class FrescoFullScreenActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<String> urlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Fresco.initialize(this);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);

        urlList = new ArrayList<>();

        if (getIntent().hasExtra(AppConstants.FRESCO_URL_LIST)) {
            urlList = getIntent().getStringArrayListExtra(AppConstants.FRESCO_URL_LIST);
        }

        setContentView(R.layout.activity_fresco_full_screen);

        viewPager = (ViewPager) findViewById(R.id.activity_fresco_full_screen_viewpager);
        viewPager.setAdapter(new FrescoAdapter(urlList));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Fresco.shutDown();
    }
}