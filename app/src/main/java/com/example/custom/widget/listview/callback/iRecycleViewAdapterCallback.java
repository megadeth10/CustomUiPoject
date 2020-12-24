package com.example.custom.widget.listview.callback;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface iRecycleViewAdapterCallback<T> {
    T onCreateViewHolder(@NonNull ViewGroup parent, int viewType, View .OnClickListener clickListener);
    void onBindViewHolder(@NonNull T holder, int position, ArrayList<?> list);
}
