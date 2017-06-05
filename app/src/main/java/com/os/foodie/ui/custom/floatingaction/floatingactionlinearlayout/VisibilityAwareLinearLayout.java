package com.os.foodie.ui.custom.floatingaction.floatingactionlinearlayout;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.widget.LinearLayout;

@CoordinatorLayout.DefaultBehavior(FloatingActionButton.Behavior.class)
class VisibilityAwareLinearLayout extends LinearLayout {

    private int mUserSetVisibility;

    public VisibilityAwareLinearLayout(Context context) {
        this(context, null);
    }

    public VisibilityAwareLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VisibilityAwareLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mUserSetVisibility = getVisibility();
    }

    @Override
    public void setVisibility(int visibility) {
        internalSetVisibility(visibility, true);
    }

    final void internalSetVisibility(int visibility, boolean fromUser) {
        super.setVisibility(visibility);
        if (fromUser) {
            mUserSetVisibility = visibility;
        }
    }

    final int getUserSetVisibility() {
        return mUserSetVisibility;
    }
}