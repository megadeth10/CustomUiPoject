package com.example.activity.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.network.response.appInfoResponse;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private ArrayList<?> mItemArray = new ArrayList<>();
    private View.OnClickListener mOnClickListener;

    public MenuAdapter(View.OnClickListener listener){
        this.mOnClickListener = listener;
    }

    public ArrayList<?> getItemArray() {
        return mItemArray;
    }

    public void setItemArray(ArrayList<?> itemArray) {
        this.mItemArray = itemArray;
        notifyDataSetChanged();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{
        protected TextView tv;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.title);
        }
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        MenuViewHolder viewHolder = new MenuViewHolder(view);
        view.setOnClickListener(this.mOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        UIList.MenuItem res = (UIList.MenuItem) this.mItemArray.get(position);
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
