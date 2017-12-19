package com.perusudroid.roomlocal.model.retrofit;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by perusu on 24/9/17.
 */

public class RXRetro {

    private static String TAG = RXRetro.class.getSimpleName();


    private RXRetro() {

    }


    public static String getStringFromByte(InputStream paramInputStream) {
        StringBuilder localStringBuilder = new StringBuilder();
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
        try {
            while (true) {
                String str = localBufferedReader.readLine();
                if (str == null)
                    break;
                localStringBuilder.append(str);
            }
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        return localStringBuilder.toString();
    }


    public static void retrofitRxEnque(Observable<Response<ResponseBody>> call, IResponseListener iResponseListener, int paramInt) {

        call
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                     //   Log.d(TAG, "onError: RxRetro");

                        if (iResponseListener != null) {
                            iResponseListener.onFailureApi(e, 0);
                        }
                    }

                    @Override
                    public void onNext(Response<ResponseBody> paramResponse) {


                        Log.e("RetroFitUtils", "response.code(): " + paramResponse.code());
                        Log.e("RetroFitUtils", "response.code(): " + paramResponse.raw().code());
                        Log.e("RetroFitUtils", "=" + paramResponse.raw());

                        Object localObject = paramResponse.body();
                        String str = null;
                        if (localObject != null) {
                            str = getStringFromByte(((ResponseBody) paramResponse.body()).byteStream());
                            Log.e("RetroFitUtils", "=" + str);
                        }

                        switch (paramResponse.code()) {
                            case 200:
                                //ok
                                iResponseListener.onSuccessfulApi(str, paramInt);
                                return;
                            case 201:
                                //created
                                iResponseListener.onSuccessfulApi(str, paramInt);
                                return;
                            case 400:
                                iResponseListener.showErrorDialog(getStringFromByte(paramResponse.errorBody().byteStream()), paramInt, 400);
                                return;
                            case 401:
                                iResponseListener.showErrorDialog(getStringFromByte(paramResponse.errorBody().byteStream()), paramInt, 401);
                                return;
                            case 403:
                                //forbitten
                                iResponseListener.showErrorDialog(paramResponse.raw().message(), paramInt, 403);
                                return;
                            case 404:
                                //not found
                                iResponseListener.showErrorDialog(paramResponse.raw().message(), paramInt, 404);
                                return;
                            case 500:
                                //server error
                                iResponseListener.showErrorDialog(getStringFromByte(paramResponse.errorBody().byteStream()), paramInt, 500);
                                return;
                            case 204:
                                return;
                        }

                    }

                });

    }
}
