
package com.techease.ultimatesavings.models.getCartItems;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CartDatum {

    @SerializedName("cart_id")
    private String mCartId;
    @SerializedName("distance")
    private String mDistance;
    @SerializedName("item_name")
    private Object mItemName;
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

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public Object getItemName() {
        return mItemName;
    }

    public void setItemName(Object itemName) {
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
