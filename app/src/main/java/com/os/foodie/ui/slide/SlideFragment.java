package com.os.foodie.ui.slide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.utils.AppConstants;

public class SlideFragment extends Fragment {

    private ImageView ivIcon;
    private TextView tvTitle, tvSubtitle;

    private String title[], subtitle[];

    public SlideFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_slide, container, false);

        ivIcon = (ImageView) view.findViewById(R.id.fragment_slide_iv_icon);

        tvTitle = (TextView) view.findViewById(R.id.fragment_slide_tv_title);
        tvSubtitle = (TextView) view.findViewById(R.id.fragment_slide_tv_subtitle);

        title = getResources().getStringArray(R.array.slide_title_text);
        subtitle = getResources().getStringArray(R.array.slide_subtitle_text);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            int position = bundle.getInt(AppConstants.VIEWPAGER_POSITION, 1);
            setSlider(position);
        }

        return view;
    }

    public void setSlider(int position) {

        switch (position) {

            case 0:
                ivIcon.setImageResource(R.mipmap.img_slide_one);
                break;

            case 1:
                ivIcon.setImageResource(R.mipmap.img_slide_two);
                break;

            case 2:
                ivIcon.setImageResource(R.mipmap.img_slide_three);
                break;

            case 3:
                ivIcon.setImageResource(R.mipmap.img_slide_four);
                break;
        }

        tvTitle.setText(title[position]);
        tvSubtitle.setText(subtitle[position]);
    }
}