package com.perusudroid.roomlocal.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.perusudroid.roomlocal.model.retrofit.IResponseListener;
import com.perusudroid.roomlocal.presenter.ipresenter.IPresenter;
import com.perusudroid.roomlocal.view.iview.IView;

/**
 * Created by perusu on 21/11/17.
 */

abstract class BasePresenter implements IPresenter,IResponseListener{

    protected String TAG = getClass().getSimpleName();

    private IView iView;

    BasePresenter(IView iView) {
        this.iView = iView;
        iView.bindPresenter(this);
    }


    @Override
    public void onStartPresenter() {

    }

    @Override
    public void onStopPresenter() {

    }

    @Override
    public void onPausePresenter() {

    }

    @Override
    public void onResumePresenter() {

    }

    @Override
    public void onDestroyPresenter() {

    }

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void showErrorDialog(String stringFromByte, int paramInt, int i) {

    }
}
