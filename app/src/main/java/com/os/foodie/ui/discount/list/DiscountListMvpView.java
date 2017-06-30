package com.os.foodie.ui.discount.list;

import com.os.foodie.data.network.model.discount.list.DiscountList;
import com.os.foodie.ui.base.MvpView;

import java.util.ArrayList;

public interface DiscountListMvpView extends MvpView
{
    void onShowDiscountList(ArrayList<DiscountList> discountLists);

    void onRefreshList();

}