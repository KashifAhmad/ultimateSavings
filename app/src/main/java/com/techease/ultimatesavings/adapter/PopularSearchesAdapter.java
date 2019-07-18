package com.techease.ultimatesavings.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.models.popularSearches.Datum;

import java.util.List;

/**
 * Created by kashif on 4/9/19.
 */

public class PopularSearchesAdapter extends RecyclerView.Adapter<PopularSearchesAdapter.MyViewHolder> {

    private Context context;
    private List<Datum> allPropertiesDataList;

    public PopularSearchesAdapter(List<Datum> allPropertiesDataList, Context context) {
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