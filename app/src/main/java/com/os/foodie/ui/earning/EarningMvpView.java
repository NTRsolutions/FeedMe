package com.os.foodie.ui.earning;

import com.os.foodie.data.network.model.earning.GetEarningResponse;
import com.os.foodie.ui.base.MvpView;

public interface EarningMvpView extends MvpView {

    void setEarnings(GetEarningResponse getEarningResponse);
}