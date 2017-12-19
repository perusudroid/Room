package com.perusudroid.roomlocal.model.retrofit;

 abstract interface ResponseListener {

    void onFailure(Throwable paramThrowable, int paramInt);

    void onSuccess(String paramString, int paramInt);

    void showErrorDialog(String paramString, int anInt, int paramInt);
}
