package com.os.foodie.fresco.zoomable;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.MotionEvent;

public interface ZoomableController {

    public interface Listener {

        void onTransformChanged(Matrix transform);
    }

    void setEnabled(boolean enabled);

    boolean isEnabled();

    void setListener(Listener listener);

    float getScaleFactor();

    Matrix getTransform();

    void setImageBounds(RectF imageBounds);

    void setViewBounds(RectF viewBounds);

    boolean onTouchEvent(MotionEvent event);
}