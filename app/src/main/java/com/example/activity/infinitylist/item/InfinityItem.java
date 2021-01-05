package com.example.activity.infinitylist.item;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class InfinityItem{
    @SerializedName("data")
    private List<Post> data;

    public List<Post> getData() {
        return data;
    }

    public void setData(List<Post> data) {
        this.data = data;
    }
}


