package com.perusudroid.roomlocal.presenter;

import android.os.Bundle;
import android.util.Log;

import com.perusudroid.roomlocal.dao.DishDaoModel;
import com.perusudroid.roomlocal.dao.DishModel;
import com.perusudroid.roomlocal.model.ApiClient;
import com.perusudroid.roomlocal.model.ObservableUser;
import com.perusudroid.roomlocal.model.dto.response.Data;
import com.perusudroid.roomlocal.model.dto.response.DishInfoResponse;
import com.perusudroid.roomlocal.model.dto.response.Shop_location;
import com.perusudroid.roomlocal.presenter.ipresenter.IMainPresenter;
import com.perusudroid.roomlocal.view.iview.IMainView;
import com.perusudroid.rxretro.IResponseListener;
import com.perusudroid.rxretro.RXRetro;

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
    private DishDaoModel dishDaoModel;
    private List<Data> data = new ArrayList<>();
    private ArrayList<ObservableUser> observeList;

    private final CompositeDisposable mDisposable = new CompositeDisposable();


    public MainPresenter(IMainView iMainView, DishDaoModel dishDaoModel) {
        super(iMainView);
        this.iMainView = iMainView;
        this.dishDaoModel = dishDaoModel;
    }


    @Override
    public void onCreatePresenter(Bundle bundle) {

        /**
         *  if no internet connection, then get data from localDB else, get data from api
         */

        if (!iMainView.getCodeSnippet().hasNetworkConnection()) {
            doGetLocalDB();
            iMainView.showToast("No network - Showing local db");
        } else {
            iMainView.showToast("Has network - Api call");
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
        mDisposable.clear();
    }

    private void doNetworkOperations() {

        /**
         *  Enqueing api task with one line of code
         *  Refer https://github.com/Periyanayagam/RxRetro for more info on this
         */

        RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getDishes(), this, 1);
    }


    /**
     * Getting all data from SQLiteDB and setting in recyclerView
     */
    private void doGetLocalDB() {
        data.clear();
        mDisposable.add(dishDaoModel.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        UsersfromDb -> {

                            if (UsersfromDb.size() > 0) {
                                observeList = new ArrayList<>();
                                for (int i = 0; i < UsersfromDb.size(); i++) {
                                    observeList.add(new ObservableUser());
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
    public void doFetchApiData() {
        if (!iMainView.getCodeSnippet().hasNetworkConnection()) {
            doGetLocalDB();
        } else {
            doNetworkOperations();
        }
    }


    /*
     On SucessfulApi Response, Clearing SQLiteDB and storing new data fetched from API
     and then getting all data from DB and showing in recycler view
     */

    @Override
    public void onSuccess(String str, int requestId) {

        switch (requestId) {
            case 1:

                data.clear();

                DishInfoResponse dishes = iMainView.getGson().fromJson(str, DishInfoResponse.class);
                DishModel[] users = new DishModel[dishes.getData().size()];

                for (int i = 0; i < users.length; i++) {

                    DishModel user =
                            new DishModel(dishes.getData().get(i).getDish_id(),
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
                }


                mDisposable.add(dishDaoModel.deleteAll()
                        .andThen(dishDaoModel.inserUser(users))
                        .andThen(dishDaoModel.getAllUsers())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                UsersfromDb -> {
                                    Log.d(TAG, "onStart: " + UsersfromDb.size());
                                    if (UsersfromDb.size() > 0) {

                                        observeList = new ArrayList<>();
                                        for (int i = 0; i < UsersfromDb.size(); i++) {
                                            observeList.add(new ObservableUser());

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
                break;
        }

    }

    /*
      onFailure API response, if data exists in localDB then show it.
     */

    @Override
    public void onFailure(Throwable throwable, int i) {
        iMainView.showToast(throwable.getLocalizedMessage() + " Showing local DB");
        doGetLocalDB();
    }
}
