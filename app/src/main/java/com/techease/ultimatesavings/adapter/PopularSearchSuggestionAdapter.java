package com.techease.ultimatesavings.adapter;

/**
 * Copyright (C) 2015 Ari C.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.util.Util;
import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.models.searchShop.SearchShop;
import com.techease.ultimatesavings.models.updatedPopularSearch.Datum;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.utils.DialogBuilder;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;
import com.techease.ultimatesavings.views.SecondSearchActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularSearchSuggestionAdapter extends RecyclerView.Adapter<PopularSearchSuggestionAdapter.ViewHolder> {

    private List<Datum> mDataSet = new ArrayList<>();
    Context mContext;
    private int mLastAnimatedItemPosition = -1;

    public PopularSearchSuggestionAdapter(Context context, List<Datum> data){
        this.mContext = context;
        this.mDataSet = data;
    }

    public interface OnItemClickListener{
        void onClick(Datum colorWrapper);
    }

    private OnItemClickListener mItemsOnClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mPopSearchItem;
        TextView mTitle;
        public final View mTextContainer;

        public ViewHolder(View view) {
            super(view);
            mPopSearchItem = view.findViewById(R.id.color_name);
            mTitle = view.findViewById(R.id.tv_pop_title);
            mTextContainer = view.findViewById(R.id.text_container);
        }
    }

    public void swapData(List<Datum> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }

    public void setItemsOnClickListener(OnItemClickListener onClickListener){
        this.mItemsOnClickListener = onClickListener;
    }

    @Override
    public PopularSearchSuggestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popular_search_results_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopularSearchSuggestionAdapter.ViewHolder holder, final int position) {

        final Datum searchedData = mDataSet.get(position);
        holder.mPopSearchItem.setText(searchedData.getSearchQuery());
//        if (mDataSet.size() > 0){
//            if (position == 0){
//                holder.mTitle.setVisibility(View.VISIBLE);
//            }else{
//                holder.mTitle.setVisibility(View.GONE);
//            }
//        }
        holder.mPopSearchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zma click", searchedData.getLat());
                searchResults(searchedData.getLat(), searchedData.getLng(), searchedData.getSearchQuery(),
                        searchedData.getSize(), searchedData.getColor());
                AppRepository.mEditor(mContext).putString("title", searchedData.getSearchQuery()).commit();
                AppRepository.mEditor(mContext).putString("size", searchedData.getSize()).commit();
                AppRepository.mEditor(mContext).putString("color", searchedData.getColor()).commit();

                mContext.startActivity(new Intent(mContext, SecondSearchActivity.class));
            }
        });

        if(mLastAnimatedItemPosition < position){
            animateItem(holder.itemView);
            mLastAnimatedItemPosition = position;
        }

        if(mItemsOnClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemsOnClickListener.onClick(mDataSet.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void animateItem(View view) {
        view.setTranslationY(Util.getScreenHeight((Activity) view.getContext()));
        view.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .start();
    }

    private void searchResults(String lat, String lon, String title, String size, String color) {

        Call<SearchShop> searchShops = BaseNetworking.apiServices().searchShop(lat, lon, title, color, AppRepository.mUserID(mContext));
        searchShops.enqueue(new Callback<SearchShop>() {
            @Override
            public void onResponse(Call<SearchShop> call, Response<SearchShop> response) {
                if (response.body().getSuccess()) {
                    if (response.body().getData().size() > 0) {


                    }
                }else {
                    Toast.makeText(mContext, "No Shop found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchShop> call, Throwable t) {
                DialogBuilder.dialog.dismiss();
            }
        });
    }
}
