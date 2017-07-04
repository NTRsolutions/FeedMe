package com.os.foodie.ui.setting.staticpages;

import com.os.foodie.ui.base.MvpPresenter;

public interface StaticPageMvpPresenter<V extends StaticPageMvpView> extends MvpPresenter<V> {

    void GetStaticData(String pageslug);
}