package com.os.foodie.ui.custom.floatingaction.floatingactionimageview;

import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.os.foodie.R;
import com.os.foodie.ui.custom.floatingaction.*;

public class FloatingActionImageViewBehavior extends CoordinatorLayout.Behavior<FloatingActionImageView> {

    private Rect mTmpRect;
    private int adjustPadding;

    public FloatingActionImageViewBehavior(int adjustPadding) {
        super();
        this.adjustPadding = adjustPadding;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionImageView child, View dependency) {
        // check that our dependency is the AppBarLayout
        return dependency instanceof ViewPager;
//        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionImageView child,
                                          View dependency) {
        if (dependency instanceof ViewPager) {
            return updateCircleImageViewVisibility(parent, (ViewPager) dependency, child);
        }
        return false;
    }

    private boolean updateCircleImageViewVisibility(CoordinatorLayout parent,
                                                    ViewPager appBarLayout, FloatingActionImageView child) {
        final CoordinatorLayout.LayoutParams lp =
                (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (lp.getAnchorId() != appBarLayout.getId()) {
            return false;
        }

        if (mTmpRect == null) {
            mTmpRect = new Rect();
        }

        final Rect rect = mTmpRect;
        ViewGroupUtils.getDescendantRect(parent, appBarLayout, rect);

//        int i = ScreenUtils.getScreenHeight(parent.getContext()) - parent.getHeight();
//        int i = 50 + parent.getMeasuredHeight();
//
//
//        int appBarHeight = (int) (parent.getResources().getDimension(R.dimen.app_bar_height)+ parent.getResources().getDimension(R.dimen.app_bar_height));
//        int appBarHeight = (int) (parent.getResources().getDimension(R.dimen.app_bar_height) / parent.getResources().getDisplayMetrics().density);
//        int statusBarHeight = (int) (ScreenUtils.getStatusBarHeight(parent.getContext()) / parent.getResources().getDisplayMetrics().density);
//        int i = (appBarHeight * 2) + statusBarHeight;
//        int i = (appBarHeight) + statusBarHeight;
//
//        Log.d("JJTypedValue", ">>" + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 138, parent.getResources().getDisplayMetrics()));
//        Log.d("appBarHeight", ">>" + appBarHeight);
//        Log.d("statusBarHeight", ">>" + statusBarHeight);
//        Log.d("i", ">>" + i);

        int ah = parent.findViewById(R.id.app_bar).getMeasuredHeight();
        int fh = child.getMeasuredHeight();

        int s = (ah - fh)/* + adjustPadding*/;
        s /= 3;
        s += adjustPadding;

//        Log.d("ah", ">>" + ah);
//        Log.d("fh", ">>" + fh);
//        Log.d("s", ">>" + s);

        if (rect.bottom <= s) {
//        if (rect.bottom <= parent/*appBarLayout.getMinimumHeightForVisibleOverlappingContent()*/) {
            child.setVisibility(View.INVISIBLE);
        } else {
            child.setVisibility(View.VISIBLE);
        }
        return true;
    }

}