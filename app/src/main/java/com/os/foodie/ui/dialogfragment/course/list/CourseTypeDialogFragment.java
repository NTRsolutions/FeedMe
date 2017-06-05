package com.os.foodie.ui.dialogfragment.course.list;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.os.foodie.R;
import com.os.foodie.data.network.model.coursetype.list.Course;
import com.os.foodie.ui.adapter.recyclerview.CourseTypeAdapter;
import com.os.foodie.ui.custom.RecyclerTouchListener;
import com.os.foodie.ui.dialogfragment.course.add.AddCourseTypeDialogFragment;
import com.os.foodie.ui.menu.add.RestaurantMenuAddUpdateDishActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;

import java.util.ArrayList;

public class CourseTypeDialogFragment extends DialogFragment implements View.OnClickListener {

    //    private ImageView ivDone;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;

    private ArrayList<Course> courses;
    private CourseTypeAdapter courseTypeAdapter;

    private ProgressDialog progressDialog;

    @Override
    public void onStart() {
        super.onStart();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();

        lp.copyFrom(window.getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        window.setAttributes(lp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_fragment_course_type, container, false);

//        ivDone = (ImageView) rootView.findViewById(R.id.dialog_fragment_add_course_type_bt_submit);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.dialog_fragment_course_type_recycler_view);
        fabAdd = (FloatingActionButton) rootView.findViewById(R.id.dialog_fragment_course_type_fab_add);

        courses = getArguments().getParcelableArrayList(AppConstants.COURSE_TYPES_ARRAYLIST);

        courseTypeAdapter = new CourseTypeAdapter(getContext(), courses);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(courseTypeAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
//                Toast.makeText(MainActivity.this, "Single Click on position        :"+position,
//                        Toast.LENGTH_SHORT).show();

                dismiss();
                ((RestaurantMenuAddUpdateDishActivity) getActivity()).setCourseType(courses.get(position));
            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(MainActivity.this, "Long press on position :" + position,
//                        Toast.LENGTH_LONG).show();
            }
        }));

//        ivDone.setOnClickListener(this);
        fabAdd.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (fabAdd.getId() == v.getId()) {

            AddCourseTypeDialogFragment addCourseTypeDialogFragment = new AddCourseTypeDialogFragment();
            addCourseTypeDialogFragment.show(getActivity().getSupportFragmentManager(), "AddCourseTypeDialogFragment");

        }

//        else if (ivDone.getId() == v.getId()) {
//
//            Log.d("onClick", "ivDone");
//            Log.d("cuisineTypesSize", ">>" + courseTypeAdapter.courses.size());
//
//            dismiss();
//
////            ((RestaurantMenuAddUpdateDishActivity) getActivity()).setCuisineTypeList(cuisineTypesChecked);
//        }
    }

    public void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(getContext());
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public void addNewCourseType(String courseId, String courseType) {

        Course course = new Course(courseId, courseType);

        courses.add(course);
        courseTypeAdapter.notifyDataSetChanged();
    }
}