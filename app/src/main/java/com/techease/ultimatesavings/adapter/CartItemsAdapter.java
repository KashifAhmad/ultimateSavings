package com.techease.ultimatesavings.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.models.getCartItems.CartDatum;

import java.util.List;

/**
 * Created by kashif on 4/9/19.
 */

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.MyViewHolder> {

    private Context context;
    private List<CartDatum> allPropertiesDataList;
    int itemCount;

    public CartItemsAdapter(List<CartDatum> allPropertiesDataList, Context context) {
        this.allPropertiesDataList = allPropertiesDataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_checkout_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CartDatum allStoreModel = allPropertiesDataList.get(position);
        holder.tvStoreName.setText("" + allStoreModel.getItemName());
        holder.tvItemPrice.setText("$ " + allStoreModel.getPrice());
        holder.tvStoreDistance.setText("" + allStoreModel.getDistance());
        holder.tvQuantity.setText("" + allStoreModel.getQuantity());
        itemCount = Integer.parseInt(allStoreModel.getQuantity());
        if (allStoreModel.getQuantity().equals("1")) {
            holder.ivRemoveItem.setImageResource(R.drawable.ic_delete_button);
        } else {
            holder.ivRemoveItem.setImageResource(R.drawable.ic_remove);
        }
        holder.ivRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemCount = Integer.parseInt(holder.tvQuantity.getText().toString());
                if (itemCount == 0){
                    holder.tvQuantity.setText("0");
                }else {
                    itemCount -= 1;
                    holder.tvQuantity.setText("" + itemCount);
                }


            }
        });
        holder.ivAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCount = Integer.parseInt(holder.tvQuantity.getText().toString());
                if (itemCount == 10){
                    holder.tvQuantity.setText("10");
                }else {
                    itemCount += 1;
                    holder.tvQuantity.setText("" + itemCount);

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return allPropertiesDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivStoreImage, ivAddItem, ivRemoveItem;
        TextView tvStoreName, tvStoreDistance, tvItemPrice, tvQuantity;

        MyViewHolder(View view) {
            super(view);
            ivStoreImage = view.findViewById(R.id.iv_store);
            tvStoreName = view.findViewById(R.id.tv_store_name);
            tvStoreDistance = view.findViewById(R.id.tv_store_distance);
            tvItemPrice = view.findViewById(R.id.tv_item_price);
            tvQuantity = view.findViewById(R.id.tv_quantity);
            ivAddItem = view.findViewById(R.id.iv_add);
            ivRemoveItem = view.findViewById(R.id.iv_remove);


        }
    }
}