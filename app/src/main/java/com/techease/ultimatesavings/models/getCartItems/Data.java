
package com.techease.ultimatesavings.models.getCartItems;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("cart_data")
    private List<CartDatum> mCartData;
    @SerializedName("total_amount")
    private String mTotalAmount;

    public String getmCartID() {
        return mCartID;
    }

    public void setmCartID(String mCartID) {
        this.mCartID = mCartID;
    }

    @SerializedName("main_cart_id")
    private String mCartID;

    public List<CartDatum> getCartData() {
        return mCartData;
    }

    public void setCartData(List<CartDatum> cartData) {
        mCartData = cartData;
    }

    public String getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        mTotalAmount = totalAmount;
    }

}
