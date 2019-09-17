
package com.techease.ultimatesavings.models.myOrders;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class OrderDate {

    @SerializedName("cart_id")
    private String mCartId;
    @SerializedName("item_name")
    private String mItemName;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("quantity")
    private String mQuantity;

    public String getCartId() {
        return mCartId;
    }

    public void setCartId(String cartId) {
        mCartId = cartId;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

}
