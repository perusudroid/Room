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
public interface IUserDAO {

    /**
     * Get the user from the table. Since for simplicity we only have one user in the database,
     * this query gets all users from the table, but limits the result to just the 1st user.
     *
     * @return the user from the table
     */
    @Query("SELECT * FROM Users LIMIT 1")
    Flowable<UserModel> getUser();



    @Query("SELECT * FROM Users")
    Flowable<List<UserModel>> getAllUser();


    @Query("SELECT * FROM Users WHERE dish_id = :dish_id LIMIT 1")
    Flowable<List<UserModel>> getUserById(String dish_id);

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateUser(UserModel user);


    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param users the user to be inserted.
     */

    @Insert
    void insertUser(UserModel... users);

    /**
     * Delete all users.
     */
    @Query("DELETE FROM Users")
    void deleteAllUsers();
}