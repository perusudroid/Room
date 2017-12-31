package com.perusudroid.roomlocal.presenter;

import android.os.Bundle;
import android.util.Log;

import com.perusudroid.roomlocal.common.Constants;
import com.perusudroid.roomlocal.dao.DishDaoModel;
import com.perusudroid.roomlocal.presenter.ipresenter.IDetailPresenter;
import com.perusudroid.roomlocal.view.iview.IDetailView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by perusu on 7/12/17.
 */

public class DetailPresenter extends BasePresenter implements IDetailPresenter {

    private IDetailView iDetailView;
    private DishDaoModel dishDaoModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public DetailPresenter(IDetailView iDetailView, DishDaoModel dishDaoModel) {
        super(iDetailView);
        this.iDetailView = iDetailView;
        this.dishDaoModel = dishDaoModel;
    }


    @Override
    public void onCreatePresenter(Bundle bundle) {

        if (bundle.getString(Constants.BundleKey.DISH_ID) != null) {

            mDisposable.add(dishDaoModel.getUsersById(bundle.getString(Constants.BundleKey.DISH_ID))
                    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModels -> {
                                if (userModels.size() > 0) {

                                    for (int i = 0; i < userModels.size(); i++) {
                                        Log.d(TAG, "onStartPresenter: " + userModels.get(i).getDish_name());

                                        iDetailView.setDishInfoData(userModels);

                                    }

                                } else {
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

}
