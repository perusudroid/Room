package com.perusudroid.roomlocal.common;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.perusudroid.roomlocal.R;


public class BaseProject extends Application {

    private static final String TAG = BaseProject.class.getSimpleName();
    private static BaseProject mAppController;

    public static BaseProject getInstance() {
        return mAppController;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppController = this;

    }


    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @NonNull
    public String getBaseUrl(){
        try {
            return getStringFromRes(R.string.base_url);
        }catch (Exception e){
            Log.e(TAG, "getBaseUrl: "+ e.getLocalizedMessage() );
        }
        return null;
    }

    public String getStringFromRes(int resId) throws Exception {
        return getString(resId);
    }
}
