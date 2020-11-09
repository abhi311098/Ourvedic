package com.abhi.ourvedic.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
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

    @Query("Update ProfileModel set pincode = :uppincode,number = :upnumber ," +
            " landmark = :uplandmark, street = :upstreet," +
            " house = :uphouse, name = :upname, email = :upemail where id = :pid")
    void updateprofiledata(String uppincode,String upnumber, String uplandmark,
                           String upstreet,String uphouse, String upemail,String upname,int pid);

    @Query("Delete from ProfileModel where id = :pid")
    void deleteprofiledata (int pid);
}
