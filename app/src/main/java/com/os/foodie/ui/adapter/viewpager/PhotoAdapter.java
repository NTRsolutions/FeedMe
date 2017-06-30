package com.os.foodie.ui.adapter.viewpager;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.ui.details.restaurant.RestaurantDetailsActivity;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;

public class PhotoAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> urlList;

    public PhotoAdapter(Context context, ArrayList<String> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {

        ViewGroup layout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.viewpager_photo, collection, false);

        ImageView ivPhoto = (ImageView) layout.findViewById(R.id.viewpager_photo_iv_photo);

        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ImageViewer.Builder(context, urlList)
                        .setStartPosition(position)
                        .show();
            }
        });

        Glide.with(context)
                .load(urlList.get(position))
                .placeholder(ContextCompat.getDrawable(context, R.mipmap.img_placeholder))
                .error(ContextCompat.getDrawable(context, R.mipmap.img_placeholder))
                .into(ivPhoto);

        collection.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
