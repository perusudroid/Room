package com.perusudroid.roomlocal.datasource;



import com.perusudroid.roomlocal.dao.DishModel;
import com.perusudroid.roomlocal.dao.IDishDAO;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by perusu on 11/11/17.
 */

public class DishDataSource implements IDishDataSource {

    private final IDishDAO mIDishDao;

    public DishDataSource(IDishDAO mIDishDao) {
        this.mIDishDao = mIDishDao;
    }

    @Override
    public Flowable<DishModel> getDish() {
        return mIDishDao.getDish();
    }

    @Override
    public Flowable<List<DishModel>> getAllDish() {
        return mIDishDao.getAllDish();
    }

    @Override
    public Flowable<List<DishModel>> getDishById(String dish_id) {
        return mIDishDao.getDishById(dish_id);
    }

    @Override
    public void insertUsers(DishModel... user) {
        mIDishDao.insertDish(user);
    }


    @Override
    public void insertOrUpdateDish(DishModel user) {
        mIDishDao.insertOrUpdateDish(user);
    }

    @Override
    public void deleteAllDish() {
        mIDishDao.deleteAllDish();
    }

}
