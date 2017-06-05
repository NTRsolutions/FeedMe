package com.os.foodie.ui.custom.floatingaction.floatingactiontextview;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

@CoordinatorLayout.DefaultBehavior(FloatingActionButton.Behavior.class)
class VisibilityAwareTextView extends AppCompatTextView {

    private int mUserSetVisibility;

    public VisibilityAwareTextView(Context context) {
        super(context);
    }

    public VisibilityAwareTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VisibilityAwareTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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