package com.example.test.aac.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserProfile {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String address;
    public String phone;
}
