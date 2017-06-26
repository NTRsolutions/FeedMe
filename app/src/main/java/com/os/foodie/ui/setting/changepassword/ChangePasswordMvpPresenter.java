package com.os.foodie.ui.setting.changepassword;

import com.os.foodie.ui.base.MvpPresenter;

public interface ChangePasswordMvpPresenter<V extends ChangePasswordMvpView> extends MvpPresenter<V> {

    void changePassword(String currentPassword, String newPassword, String confirmNewPassword);

    void dispose();
}