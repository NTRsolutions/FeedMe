package com.os.foodie.ui.slide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.os.foodie.utils.AppConstants;

public class SlidePagerAdapter extends FragmentStatePagerAdapter {

    public SlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.VIEWPAGER_POSITION, position);

        SlideFragment slideFragment = new SlideFragment();
        slideFragment.setArguments(bundle);

        return slideFragment;
    }

    @Override
    public int getCount() {
        return AppConstants.TOTAL_SLIDE;
    }
}