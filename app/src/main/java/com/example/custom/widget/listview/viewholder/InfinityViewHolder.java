package com.example.custom.widget.listview.viewholder;

import android.view.View;
import android.widget.TextView;

import com.example.test.myapplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InfinityViewHolder extends RecyclerView.ViewHolder {
    private TextView tv;

    public InfinityViewHolder(@NonNull View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.title);
    }

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }
}
