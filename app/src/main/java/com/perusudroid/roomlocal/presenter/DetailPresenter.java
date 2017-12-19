package com.perusudroid.roomlocal.presenter;

import android.os.Bundle;
import android.util.Log;

import com.perusudroid.roomlocal.adapter.DishesAdapter;
import com.perusudroid.roomlocal.common.Constants;
import com.perusudroid.roomlocal.dao.UserDaoModel;
import com.perusudroid.roomlocal.dao.UserModel;
import com.perusudroid.roomlocal.model.ObservableUser;
import com.perusudroid.roomlocal.model.dto.response.Data;
import com.perusudroid.roomlocal.model.dto.response.Shop_location;
import com.perusudroid.roomlocal.presenter.ipresenter.IDetailPresenter;
import com.perusudroid.roomlocal.view.iview.IDetailView;
import com.perusudroid.roomlocal.view.iview.IView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import rx.schedulers.Schedulers;

/**
 * Created by perusu on 7/12/17.
 */

public class DetailPresenter extends BasePresenter implements IDetailPresenter{

    private IDetailView iDetailView;
    private UserDaoModel userDaoModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public DetailPresenter(IDetailView iDetailView, UserDaoModel userDaoModel) {
        super(iDetailView);
        this.iDetailView = iDetailView;
        this.userDaoModel = userDaoModel;
    }


    @Override
    public void onCreatePresenter(Bundle bundle) {

        if(bundle.getString(Constants.BundleKey.DISH_ID) != null){

            mDisposable.add(userDaoModel.getUsersById(bundle.getString(Constants.BundleKey.DISH_ID))
                    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModels -> {
                                    if(userModels.size() > 0){

                                        for (int i = 0; i < userModels.size(); i++) {
                                            Log.d(TAG, "onStartPresenter: " + userModels.get(i).getDish_name());

                                            iDetailView.setDishInfoData(userModels);

                                        }

                                    }else{
                                        Log.d(TAG, "onCreatePresenter: no data found!");
                                    }
                            }

                    )
                    );
        }

    }

    @Override
    public void onStopPresenter() {
        super.onStopPresenter();
        mDisposable.clear();
    }

    @Override
    public void onSuccessfulApi(String str, int i) {

    }

    @Override
    public void onFailureApi(Throwable t, int paramInt) {

    }
}
