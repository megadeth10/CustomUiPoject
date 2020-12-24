package com.example.custom.widget.listview.callback;

import android.view.ViewGroup;

public interface iFoldRecycleViewAdapterCallback<T, V> {
    void onBindChildView(T holder, int position, ViewGroup parent, V item);
}
