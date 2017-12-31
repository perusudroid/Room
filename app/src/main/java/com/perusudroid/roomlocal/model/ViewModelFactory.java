package com.perusudroid.roomlocal.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.perusudroid.roomlocal.dao.DishDaoModel;
import com.perusudroid.roomlocal.datasource.IDishDataSource;

/**
 * Created by perusu on 11/11/17.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final IDishDataSource mIDishDataSource;

    public ViewModelFactory(IDishDataSource mIDishDataSource) {
        this.mIDishDataSource = mIDishDataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DishDaoModel.class)) {
            return (T) new DishDaoModel(mIDishDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}