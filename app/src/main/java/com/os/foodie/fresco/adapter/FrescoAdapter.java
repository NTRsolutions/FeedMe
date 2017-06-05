package com.os.foodie.fresco.adapter;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.os.foodie.fresco.zoomable.ZoomableDraweeView;

import java.util.ArrayList;

public class FrescoAdapter extends PagerAdapter {

    private ArrayList<String> urlList = new ArrayList<>();

//    String[] items = new String[]{
//            "https://peach.blender.org/wp-content/uploads/bbb-splash.png",
//            "https://peach.blender.org/wp-content/uploads/rodents.png",
//            "https://peach.blender.org/wp-content/uploads/evil-frank.png",
//            "https://peach.blender.org/wp-content/uploads/bunny-bow.png",
//            "https://peach.blender.org/wp-content/uploads/rinkysplash.jpg",
//            "https://peach.blender.org/wp-content/uploads/its-a-trap.png"
//    };

    public FrescoAdapter(ArrayList<String> urlList) {
        this.urlList = urlList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ZoomableDraweeView view = new ZoomableDraweeView(container.getContext());

        view.setController(Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(urlList.get(position)))
//                .setUri(Uri.parse(items[position]))
                .build());

        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(container.getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setProgressBarImage(new ProgressBarDrawable())
                .build();

        view.setHierarchy(hierarchy);

        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
//        return item.length;
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}