
package com.techease.ultimatesavings.models.myOrders;

import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class CartDatum {

    @SerializedName("cart_id")
    private String mCartId;
    @SerializedName("order_date")
    private List<OrderDate> mOrderDate;

    public String getCartId() {
        return mCartId;
    }

    public void setCartId(String cartId) {
        mCartId = cartId;
    }

    public List<OrderDate> getOrderDate() {
        return mOrderDate;
    }

    public void setOrderDate(List<OrderDate> orderDate) {
        mOrderDate = orderDate;
    }

}
