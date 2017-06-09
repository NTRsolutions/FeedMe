package com.os.foodie.ui.base;


public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void clearRequests();

    void handleApiError(Error error);

    void setUserAsLoggedOut();
}
