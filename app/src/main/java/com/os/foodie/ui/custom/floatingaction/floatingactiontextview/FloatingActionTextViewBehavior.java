package com.os.foodie.ui.custom.floatingaction.floatingactiontextview;

import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.os.foodie.R;
import com.os.foodie.ui.custom.floatingaction.ViewGroupUtils;

public class FloatingActionTextViewBehavior extends CoordinatorLayout.Behavior<FloatingActionTextView> {

    private Rect mTmpRect;
    private int adjustPadding;

    public FloatingActionTextViewBehavior(int adjustPadding) {
        super();
        this.adjustPadding = adjustPadding;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionTextView child, View dependency) {
        // check that our dependency is the AppBarLayout
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionTextView child,
                                          View dependency) {
        if (dependency instanceof AppBarLayout) {
            return updateCircleImageViewVisibility(parent, (AppBarLayout) dependency, child);
        }
        return false;
    }

    private boolean updateCircleImageViewVisibility(CoordinatorLayout parent,
                                                    AppBarLayout appBarLayout, FloatingActionTextView child) {
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

        int ah = parent.findViewById(R.id.app_bar).getMeasuredHeight();
        int fh = child.getMeasuredHeight();

        int s = (ah - fh)/* + adjustPadding*/;
        s /= 2;
        s += adjustPadding;

        if (rect.bottom <= s) {
//            This don't work
//        if (rect.bottom <= parent/*appBarLayout.getMinimumHeightForVisibleOverlappingContent()*/) {
            child.setVisibility(View.INVISIBLE);
        } else {
            child.setVisibility(View.VISIBLE);
        }
        return true;
    }
}