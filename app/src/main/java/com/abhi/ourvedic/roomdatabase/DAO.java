package com.abhi.ourvedic.roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.abhi.ourvedic.model.ProfileModel;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    public void insertion(ProfileModel profileModel);

    @Query("Select * from ProfileModel")
    List<ProfileModel> getProfile();

}
