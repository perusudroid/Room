package com.perusudroid.roomlocal.datasource;



import com.perusudroid.roomlocal.dao.IUserDAO;
import com.perusudroid.roomlocal.dao.UserModel;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by perusu on 11/11/17.
 */

public class UserDataSource implements IUserDataSource {

    private final IUserDAO mIUserDao;

    public UserDataSource(IUserDAO mIUserDao) {
        this.mIUserDao = mIUserDao;
    }

    @Override
    public Flowable<UserModel> getUser() {
        return mIUserDao.getUser();
    }

    @Override
    public Flowable<List<UserModel>> getAllUser() {
        return mIUserDao.getAllUser();
    }

    @Override
    public Flowable<List<UserModel>> getUserByDishId(String dish_id) {
        return mIUserDao.getUserById(dish_id);
    }

    @Override
    public void insertUsers(UserModel... user) {
        mIUserDao.insertUser(user);
    }


    @Override
    public void insertOrUpdateUser(UserModel user) {
        mIUserDao.insertOrUpdateUser(user);
    }

    @Override
    public void deleteAllUsers() {
        mIUserDao.deleteAllUsers();
    }

}
