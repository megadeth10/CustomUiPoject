package com.example.test.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.network.response.appInfoResponse;

import java.util.ArrayList;

public class apiResponseAdapter extends RecyclerView.Adapter<apiResponseAdapter.AppInfoViewHolder> {
    private ArrayList<appInfoResponse> dataList;
    private View.OnClickListener mClickListener;
    public apiResponseAdapter(View.OnClickListener listener){
        dataList = new ArrayList<>();
        mClickListener = listener;
    }

    public void setData(appInfoResponse res){
        dataList.add(res);
        notifyDataSetChanged();
    }

    public void removeData(int index){
        dataList.remove(index);
        notifyDataSetChanged();
    }

    public class AppInfoViewHolder extends RecyclerView.ViewHolder{
        protected TextView tv;
        protected TextView indexTv;
        public AppInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.text);
            indexTv = itemView.findViewById(R.id.index);
        }
    }

    @NonNull
    @Override
    public AppInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appinfo_item, parent, false);
        AppInfoViewHolder viewHolder = new AppInfoViewHolder(view);
        view.setOnClickListener(mClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppInfoViewHolder holder, int position) {
        appInfoResponse res = this.dataList.get(position);
        holder.tv.setText(res.getVersion().toString());
        holder.indexTv.setText(Integer.toString(position));
        holder.indexTv.setTag(position);
    }

    @Override
    public int getItemCount() {
        if(this.dataList == null){
            return 0;
        }
        return dataList.size();
    }
}
