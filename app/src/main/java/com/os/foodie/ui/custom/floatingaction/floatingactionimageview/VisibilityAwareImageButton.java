package com.os.foodie.ui.custom.floatingaction.floatingactionimageview;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

@CoordinatorLayout.DefaultBehavior(FloatingActionImageViewBehavior.class)
class VisibilityAwareImageButton extends AppCompatImageButton {

    private int mUserSetVisibility;

    public VisibilityAwareImageButton(Context context) {
        this(context, null);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
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