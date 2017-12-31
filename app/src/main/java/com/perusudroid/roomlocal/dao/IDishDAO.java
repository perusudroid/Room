package com.perusudroid.roomlocal.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by perusu on 11/11/17.
 */

@Dao
public interface IDishDAO {

    /**
     * Get the dish from the table. Since for simplicity we only have one dish in the database,
     * this query gets all dish from the table, but limits the result to just the 1st dish.
     *
     * @return the dish from the table
     */
    @Query("SELECT * FROM dishes LIMIT 1")
    Flowable<DishModel> getDish();



    @Query("SELECT * FROM dishes")
    Flowable<List<DishModel>> getAllDish();


    @Query("SELECT * FROM dishes WHERE dish_id = :dish_id LIMIT 1")
    Flowable<List<DishModel>> getDishById(String dish_id);

    /**
     * Insert a dish in the database. If the dish already exists, replace it.
     *
     * @param dish the dish to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateDish(DishModel dish);


    /**
     * Insert a dish in the database. If the dish already exists, replace it.
     *
     * @param dish the dish to be inserted.
     */

    @Insert
    void insertDish(DishModel... dish);

    /**
     * Delete all dish.
     */
    @Query("DELETE FROM dishes")
    void deleteAllDish();
}