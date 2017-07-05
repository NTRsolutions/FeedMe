package com.os.foodie.ui.earning;

import com.os.foodie.ui.base.MvpPresenter;

public interface EarningMvpPresenter<V extends EarningMvpView> extends MvpPresenter<V> {

    void getEarnings();

    void dispose();
}
