package com.techease.ultimatesavings.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.models.recentSearches.Datum;

import java.util.List;

/**
 * Created by kashif on 4/9/19.
 */

public class RecentSearchesAdapter extends RecyclerView.Adapter<RecentSearchesAdapter.MyViewHolder> {

    private Context context;
    private List<Datum> allPropertiesDataList;

    public RecentSearchesAdapter(List<Datum> allPropertiesDataList, Context context) {
        this.allPropertiesDataList = allPropertiesDataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_recent_searches, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Datum allStoreModel = allPropertiesDataList.get(position);
        holder.tvRecentSearchedTerm.setText(allStoreModel.getSearchQuery());
        holder.tvRecentSearchedTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zma search item", allStoreModel.getLat());
            }
        });



    }

    @Override
    public int getItemCount() {
        return allPropertiesDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvRecentSearchedTerm;
        CardView cvStore;
        ProgressBar progressBar;
        MyViewHolder(View view) {
            super(view);
           tvRecentSearchedTerm = view.findViewById(R.id.tv_searched_term);



        }
    }
}