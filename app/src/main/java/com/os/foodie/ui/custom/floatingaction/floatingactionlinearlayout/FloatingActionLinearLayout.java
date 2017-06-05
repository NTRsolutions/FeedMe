package com.os.foodie.ui.custom.floatingaction.floatingactionlinearlayout;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import com.os.foodie.R;

@CoordinatorLayout.DefaultBehavior(FloatingActionLinearLayoutBehavior.class)
public class FloatingActionLinearLayout extends VisibilityAwareLinearLayout {

    public FloatingActionLinearLayout(Context context) {
        this(context, null);
    }

    public FloatingActionLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.imageButtonStyle);
    }

    public FloatingActionLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}