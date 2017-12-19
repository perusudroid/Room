package com.perusudroid.roomlocal.model.retrofit;

/**
 * Created by perusu on 24/9/17.
 */

public interface IBaseResponseListener {

    void showErrorDialog(String stringFromByte, int paramInt, int i);

    void onFailureApi(Throwable t, int paramInt);
}
