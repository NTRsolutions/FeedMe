package com.os.foodie.ui.discount.list;

import com.os.foodie.ui.base.MvpPresenter;

public interface DiscountListMvpPresenter<V extends DiscountListMvpView> extends MvpPresenter<V>
{
    void DiscountListing();

    void deleteDiscountList(String discount_id);

    void dispose();
}