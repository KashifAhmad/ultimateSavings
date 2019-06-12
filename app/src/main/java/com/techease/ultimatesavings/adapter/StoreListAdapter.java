package com.techease.ultimatesavings.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techease.ultimatesavings.R;

import com.techease.ultimatesavings.models.allShopsModel.Datum;

import java.util.List;

/**
 * Created by kashif on 4/9/19.
 */

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.MyViewHolder> {

    private Context context;
    private List<Datum> allPropertiesDataList;

    public StoreListAdapter(List<Datum> allPropertiesDataList, Context context) {
        this.allPropertiesDataList = allPropertiesDataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_store_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Datum allStoreModel = allPropertiesDataList.get(position);
        holder.tvStoreName.setText(allStoreModel.getTitle());
        holder.tvStoreDistance.setText(allStoreModel.getDistance()+" away");
        Picasso.get().load(allStoreModel.getPicture()).into(holder.ivStoreImage);



    }

    @Override
    public int getItemCount() {
        return allPropertiesDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivStoreImage;
        TextView tvStoreName, tvStoreDistance;
        CardView cvStore;
        ProgressBar progressBar;
        MyViewHolder(View view) {
            super(view);
            ivStoreImage = view.findViewById(R.id.iv_store);
            tvStoreName = view.findViewById(R.id.tv_store_name);
            tvStoreDistance = view.findViewById(R.id.tv_store_distance);




        }
    }
}