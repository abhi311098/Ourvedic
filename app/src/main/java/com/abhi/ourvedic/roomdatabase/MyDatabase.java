package com.abhi.ourvedic.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.abhi.ourvedic.model.ProfileModel;

@Database(entities = {ProfileModel.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract DAO dao();
}
