package com.perusudroid.roomlocal.dao;

import android.arch.lifecycle.ViewModel;

import com.perusudroid.roomlocal.datasource.IDishDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.internal.operators.completable.CompletableFromAction;

/**
 * Created by perusu on 11/11/17.
 */

public class DishDaoModel extends ViewModel {

    private final IDishDataSource mIDishDataSource;

    private DishModel mDishModel;

    public DishDaoModel(IDishDataSource mIDishDataSource) {
        this.mIDishDataSource = mIDishDataSource;
    }

/*
    */
/**
     * Get the user name of the user.
     *
     * @return a {@link Flowable} that will emit every time the user name has been updated.
     *//*

    public Flowable<String> getUserName() {
        return mIDishDataSource.getDish()
                // for every emission of the user, get the user name
                .map(userModel -> {
                    mDishModel = userModel;
                    return userModel.get_userName();
                });

    }
*/


    /**
     * Get all users from db
     *
     * @return a {@link Flowable} that will emit every time the user name has been updated.
     */
    public Flowable<List<DishModel>> getAllUsers() {

        return mIDishDataSource.getAllDish();
    }


    /**
     * Get users based on ID
     *
     * @return a {@link Flowable} that will emit every time the user name has been updated.
     */

    public Flowable<List<DishModel>> getUsersById(String dish_id) {

        return mIDishDataSource.getDishById(dish_id);
    }



    /**
     * @param user - Insert DishModel class directly
     *
     * @return a {@link Completable} that completes when the user name is updated
     */

    public Completable inserUser(final DishModel... user) {
        return new CompletableFromAction(() -> {

            mIDishDataSource.insertUsers(user);
        });
    }


    /**
     *
     * @return a {@link Completable}
     */


    public Completable deleteAll() {
        return new CompletableFromAction(() -> {

            mIDishDataSource.deleteAllDish();
        });
    }


}