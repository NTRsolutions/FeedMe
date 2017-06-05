package com.os.foodie.ui.custom.floatingaction.floatingactionimageview;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import com.os.foodie.R;
import com.os.foodie.ui.custom.floatingaction.floatingactionimageview.VisibilityAwareImageButton;

@CoordinatorLayout.DefaultBehavior(FloatingActionImageViewBehavior.class)
public class FloatingActionImageView extends VisibilityAwareImageButton {

    public FloatingActionImageView(Context context) {
        this(context, null);
    }

    public FloatingActionImageView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.imageButtonStyle);
    }

    public FloatingActionImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}