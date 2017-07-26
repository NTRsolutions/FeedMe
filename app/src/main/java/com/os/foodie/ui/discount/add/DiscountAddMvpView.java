package com.os.foodie.ui.discount.add;

import com.os.foodie.data.network.model.discount.dishlist.DishDatum;
import com.os.foodie.ui.base.MvpView;

import java.util.ArrayList;

public interface DiscountAddMvpView extends MvpView {

    void setAddButtonEnable();

    void onShowDishList(ArrayList<DishDatum> dishlist);

    void onFinish();
}