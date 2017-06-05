package com.os.foodie.ui.setting;

import com.os.foodie.data.DataManager;
import com.os.foodie.ui.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;

public class SettingsPresenter<V extends SettingsMvpView> extends BasePresenter<V> implements SettingsMvpPresenter<V> {

    public SettingsPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
