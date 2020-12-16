package com.example.custom.widget.listview.callback;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Infinity Adapter Interface Callback
 * View 관련 함수를 외부로 빼내서 override 효과
 */
public interface iInfinityListCallback {
    void onBind(@NonNull RecyclerView.ViewHolder holder, int position, List<?> data);
    RecyclerView.ViewHolder createView(@NonNull ViewGroup parent, int viewType, View.OnClickListener clickListener);
}
