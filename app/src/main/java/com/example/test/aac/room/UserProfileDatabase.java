package com.example.test.aac.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserProfile.class}, version = 1)
public abstract class UserProfileDatabase extends RoomDatabase {
    public abstract UserProfileDao getUserProfileDao();
}
