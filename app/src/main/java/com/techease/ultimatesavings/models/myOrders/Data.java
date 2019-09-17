
package com.techease.ultimatesavings.models.myOrders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("cart_data")
    private List<CartDatum> mCartData;

    public List<CartDatum> getCartData() {
        return mCartData;
    }

    public void setCartData(List<CartDatum> cartData) {
        mCartData = cartData;
    }

}
