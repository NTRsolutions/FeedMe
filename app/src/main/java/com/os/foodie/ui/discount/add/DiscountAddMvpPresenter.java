package com.os.foodie.ui.discount.add;

import com.os.foodie.data.network.model.discount.add.AddDiscountRequest;
import com.os.foodie.ui.base.MvpPresenter;

public interface DiscountAddMvpPresenter<V extends DiscountAddMvpView> extends MvpPresenter<V>
{
    void addDiscount(AddDiscountRequest addDiscountRequest);

    void showDishList();

    void showStartDate();

    void showEndDate();
}