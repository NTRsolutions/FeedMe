package com.os.foodie.ui.menu.add;

import com.os.foodie.data.network.model.coursetype.list.Course;
import com.os.foodie.ui.base.MvpView;

import java.util.ArrayList;
import java.util.List;

public interface RestaurantMenuAddUpdateDishMvpView extends MvpView {

    void onCourseTypeReceived(ArrayList<Course> courseArrayList);

    void onMenuItemAdded();
}