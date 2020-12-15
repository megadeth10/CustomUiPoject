package com.example.test.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.fragment.data.NormalItem;
import com.example.test.myapplication.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.ViewHolder> {
    private ArrayList<?> mItemArray = new ArrayList<>();
    private View.OnClickListener mOnClickListener;

    public NormalAdapter(View.OnClickListener listener){
        this.mOnClickListener = listener;
    }

    public ArrayList<?> getItemArray() {
        return mItemArray;
    }

    public void setItemArray(ArrayList<?> itemArray) {
        this.mItemArray = itemArray;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.title);
        }
    }

    @NonNull
    @Override
    public NormalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 400;
        view.setLayoutParams(params);
        NormalAdapter.ViewHolder viewHolder = new NormalAdapter.ViewHolder(view);
        view.setOnClickListener(this.mOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NormalAdapter.ViewHolder holder, int position) {
        NormalItem res = (NormalItem) this.mItemArray.get(position);
        holder.tv.setText(res.getTitle());
        holder.tv.setTag(res);
    }

    @Override
    public int getItemCount() {
        if(mItemArray == null){
            return 0;
        }
        return this.mItemArray.size();
    }
}
