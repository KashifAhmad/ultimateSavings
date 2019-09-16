package com.techease.ultimatesavings.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.models.addToCart.AddToCartResponse;
import com.techease.ultimatesavings.models.searchShop.Datum;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kashif on 4/9/19.
 */

public class SearchedStoreListAdapter extends RecyclerView.Adapter<SearchedStoreListAdapter.MyViewHolder> {

    private Context context;
    private List<Datum> allPropertiesDataList;
    int mView;
    HashMap<String, String> mMap= new HashMap<>();

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
        holder.ibCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ibCart.setImageResource(R.drawable.ic_cart_added);
                holder.ibCart.setBackgroundResource(R.drawable.round_button_green);
                mMap.put(allStoreModel.getProductId(), "added");
                addToCart(AppRepository.mUserID(context), allStoreModel.getProductId(), allStoreModel.getPrice(), 3);
            }
        });
       if (mMap != null && mMap.containsValue("added")){
           Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
       }


    }
    private void addToCart(int userID, String productID, String price, int quantity){
        Call<AddToCartResponse> cartAddition = BaseNetworking.apiServices().cartAddition(userID, productID, price, quantity);
        cartAddition.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return allPropertiesDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivStoreImage;
        TextView tvStoreName, tvStoreDistance, tvItemPrice;
        ImageButton ibCart;
        CardView cvStore;
        ProgressBar progressBar;
        MyViewHolder(View view) {
            super(view);
            ivStoreImage = view.findViewById(R.id.iv_store);
            tvStoreName = view.findViewById(R.id.tv_store_name);
            tvStoreDistance = view.findViewById(R.id.tv_store_distance);
            tvItemPrice = view.findViewById(R.id.tv_item_price);
            ibCart = view.findViewById(R.id.ib_cart);




        }
    }
}