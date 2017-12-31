package com.perusudroid.roomlocal.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.perusudroid.roomlocal.dao.IDishDAO;
import com.perusudroid.roomlocal.dao.DishModel;


/**
 * Created by perusu on 11/11/17.
 */
@Database(entities = {DishModel.class}, version = 6)
public abstract class DishDB extends RoomDatabase {
    private static volatile DishDB INSTANCE;

    public abstract IDishDAO iuserDao();

    public static DishDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DishDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DishDB.class, "Dish.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
