package com.example.test.aac.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserProfileDao {
    @Insert
    void insert(UserProfile user);

    @Update
    void update(UserProfile user);

    @Delete
    void delete(UserProfile user);

    @Query("SELECT * FROM UserProfile")
    List<UserProfile> getAll();
}