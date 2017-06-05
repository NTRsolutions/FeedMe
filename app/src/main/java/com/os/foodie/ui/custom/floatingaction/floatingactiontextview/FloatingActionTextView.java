package com.os.foodie.ui.custom.floatingaction.floatingactiontextview;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.os.foodie.R;

@CoordinatorLayout.DefaultBehavior(FloatingActionTextViewBehavior.class)
public class FloatingActionTextView extends VisibilityAwareTextView {

    public FloatingActionTextView(Context context) {
        super(context, null);
    }

    public FloatingActionTextView(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.imageButtonStyle);
    }

    public FloatingActionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}