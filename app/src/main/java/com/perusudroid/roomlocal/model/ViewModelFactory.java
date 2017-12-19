package com.perusudroid.roomlocal.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.perusudroid.roomlocal.dao.UserDaoModel;
import com.perusudroid.roomlocal.datasource.IUserDataSource;

/**
 * Created by perusu on 11/11/17.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final IUserDataSource mIUserDataSource;

    public ViewModelFactory(IUserDataSource mIUserDataSource) {
        this.mIUserDataSource = mIUserDataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserDaoModel.class)) {
            return (T) new UserDaoModel(mIUserDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}