package com.techease.ultimatesavings.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.techease.ultimatesavings.models.searchShop.Datum;

import java.util.List;

/**
 * Created by kashif on 4/9/19.
 */

public class SearchedStoreListAdapter extends RecyclerView.Adapter<SearchedStoreListAdapter.MyViewHolder> {

    private Context context;
    private List<Datum> allPropertiesDataList;
    int mView;

    public SearchedStoreListAdapter(List<Datum> allPropertiesDataList, Context context, int view) {
        this.allPropertiesDataList = allPropertiesDataList;
        this.context = context;
        this.mView = view;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(mView, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Datum allStoreModel = allPropertiesDataList.get(position);
        holder.tvStoreName.setText(allStoreModel.getShopTitle());
        holder.tvStoreDistance.setText(allStoreModel.getDistance()+" away");
        Picasso.get().load(allStoreModel.getPicture()).into(holder.ivStoreImage);
        holder.tvItemPrice.setText("$"+allStoreModel.getPrice());



    }

    @Override
    public int getItemCount() {
        return allPropertiesDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivStoreImage;
        TextView tvStoreName, tvStoreDistance, tvItemPrice;
        CardView cvStore;
        ProgressBar progressBar;
        MyViewHolder(View view) {
            super(view);
            ivStoreImage = view.findViewById(R.id.iv_store);
            tvStoreName = view.findViewById(R.id.tv_store_name);
            tvStoreDistance = view.findViewById(R.id.tv_store_distance);
            tvItemPrice = view.findViewById(R.id.tv_item_price);




        }
    }
}