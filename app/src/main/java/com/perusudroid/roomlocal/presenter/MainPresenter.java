package com.perusudroid.roomlocal.presenter;

import android.os.Bundle;
import android.util.Log;

import com.perusudroid.roomlocal.dao.UserDaoModel;
import com.perusudroid.roomlocal.dao.UserModel;
import com.perusudroid.roomlocal.model.ApiClient;
import com.perusudroid.roomlocal.model.ObservableUser;
import com.perusudroid.roomlocal.model.dto.response.Data;
import com.perusudroid.roomlocal.model.dto.response.DishInfoResponse;
import com.perusudroid.roomlocal.model.dto.response.Shop_location;
import com.perusudroid.roomlocal.model.retrofit.IResponseListener;
import com.perusudroid.roomlocal.model.retrofit.RXRetro;
import com.perusudroid.roomlocal.presenter.ipresenter.IMainPresenter;
import com.perusudroid.roomlocal.view.iview.IMainView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by perusu on 21/11/17.
 */

public class MainPresenter extends BasePresenter implements IMainPresenter, IResponseListener {

    private IMainView iMainView;
    private UserDaoModel userDaoModel;
    private ArrayList<ObservableUser> observeList;

    private final CompositeDisposable mDisposable = new CompositeDisposable();


    public MainPresenter(IMainView iMainView, UserDaoModel userDaoModel) {
        super(iMainView);
        this.iMainView = iMainView;
        this.userDaoModel = userDaoModel;
    }


    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (!iMainView.getCodeSnippet().hasNetworkConnection()) {
            doGetLocalDB();
        } else {
            doNetworkOperations();
        }

    }


    @Override
    public void onStartPresenter() {
        super.onStartPresenter();
    }

    @Override
    public void onStopPresenter() {
        super.onStopPresenter();
        // clear all the subscriptions
        mDisposable.clear();
    }

    private void doNetworkOperations() {
        RXRetro.retrofitRxEnque(ApiClient.getInterface().getDishes(), this, 1);
    }


    private void doGetLocalDB() {
        mDisposable.add(userDaoModel.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        UsersfromDb -> {
                            Log.d(TAG, "onStart: " + UsersfromDb.size());
                            if (UsersfromDb.size() > 0) {

                                observeList = new ArrayList<>();
                                for (int i = 0; i < UsersfromDb.size(); i++) {
                                    observeList.add(new ObservableUser());
                                    Log.d(TAG, "onStartPresenter: " + UsersfromDb.get(i).getDish_name());

                                    List<Data> data = new ArrayList<>();

                                    Shop_location shop_location = new Shop_location(UsersfromDb.get(i).getLat(), UsersfromDb.get(i).getLng());

                                    data.add(new Data(UsersfromDb.get(i).getDish_id(), UsersfromDb.get(i).getDish_expiration(), shop_location, UsersfromDb.get(i).getDish_name(), UsersfromDb.get(i).getShop_name(), UsersfromDb.get(i).getShop_address(), UsersfromDb.get(i).getShop_detail_address(), UsersfromDb.get(i).getDish_pic()));

                                    iMainView.refreshRecyclerData(data);

                                }
                            }

                        }
                )
        );
    }


    @Override
    public void onFailureApi(Throwable t, int paramInt) {
        Log.
                d(TAG, "onFailureApi: ");
    }

    @Override
    public void onSuccessfulApi(String str, int type) {
        Log.d(TAG, "onSuccessfulApi: ");

        DishInfoResponse dishes = iMainView.getGson().fromJson(str, DishInfoResponse.class);
        UserModel[] users = new UserModel[dishes.getData().size()];

        for (int i = 0; i < users.length; i++) {


            UserModel user =
                    new UserModel(dishes.getData().get(i).getDish_id(),
                            dishes.getData().get(i).getDish_shop(),
                            dishes.getData().get(i).getShop_id(),
                            dishes.getData().get(i).getDish_type(),
                            dishes.getData().get(i).getDish_expiration(),
                            dishes.getData().get(i).getShop_location().getLat(),
                            dishes.getData().get(i).getShop_location().getLng(),
                            dishes.getData().get(i).getShop_name(),
                            dishes.getData().get(i).getShop_address(),
                            dishes.getData().get(i).getDish_name(),
                            dishes.getData().get(i).getShop_mobile().toString(),
                            dishes.getData().get(i).getShop_timing(),
                            dishes.getData().get(i).getShop_address(),
                            dishes.getData().get(i).getDish_description(),
                            dishes.getData().get(i).getDish_pic()
                    );
            users[i] = user;
            Log.d(TAG, "onSuccessfulApi: " + user);
        }

        for (int i = 0; i < users.length; i++) {
            Log.d(TAG, "onSuccessfulApi: data" + users[i].getDish_pic());
        }


        mDisposable.add(userDaoModel.deleteAll()
                .andThen(userDaoModel.inserUser(users))
                .andThen(userDaoModel.getAllUsers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        UsersfromDb -> {
                            Log.d(TAG, "onStart: " + UsersfromDb.size());
                            if (UsersfromDb.size() > 0) {

                                observeList = new ArrayList<>();
                                for (int i = 0; i < UsersfromDb.size(); i++) {
                                    observeList.add(new ObservableUser());
                                    Log.d(TAG, "onStartPresenter: " + UsersfromDb.get(i).getDish_name());

                                    List<Data> data = new ArrayList<>();

                                    Log.d(TAG, "UsersfromDb: " + UsersfromDb.get(i).getDish_pic());

                                    Shop_location shop_location = new Shop_location(UsersfromDb.get(i).getLat(), UsersfromDb.get(i).getLng());

                                    data.add(new Data(UsersfromDb.get(i).getDish_id(), UsersfromDb.get(i).getDish_expiration(), shop_location,
                                            UsersfromDb.get(i).getDish_name(), UsersfromDb.get(i).getShop_name(),
                                            UsersfromDb.get(i).getShop_address(), UsersfromDb.get(i).getShop_detail_address(),
                                            UsersfromDb.get(i).getDish_pic()));

                                    iMainView.refreshRecyclerData(data);

                                }
                            }

                        }
                )
        );

    }

    @Override
    public void doFetchApiData() {
        if (!iMainView.getCodeSnippet().hasNetworkConnection()) {
            doGetLocalDB();
        } else {
            doNetworkOperations();
        }
    }
}
