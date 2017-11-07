package com.samcotec.ahlalquran;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PC07 on 10/8/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private Context context;
    private List<MyData> my_data;

    public CustomAdapter(Context context, List<MyData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    public Context getContext() {
        return context;
    }

    public List<MyData> getMy_data() {
        return my_data;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setMy_data(List<MyData> my_data) {
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.gName.setText(my_data.get(position).getName());
        holder.sDate.setText(my_data.get(position).getsDate());
        holder.eDate.setText(my_data.get(position).geteDate());
        holder.adminName.setText(my_data.get(position).getgAdminName());
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView gName;
        public TextView sDate;
        public TextView eDate;
        public TextView adminName;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            gName = (TextView) itemView.findViewById(R.id.gName);
            sDate = (TextView) itemView.findViewById(R.id.Sdate);
            eDate = (TextView) itemView.findViewById(R.id.Edate);
            adminName = (TextView) itemView.findViewById(R.id.adminName);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}