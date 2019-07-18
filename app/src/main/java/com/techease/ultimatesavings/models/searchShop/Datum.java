package com.techease.ultimatesavings.models.searchShop;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Datum {

    @SerializedName("distance")
    private String mDistance;
    @SerializedName("latitude")
    private String mLatitude;
    @SerializedName("longitude")
    private String mLongitude;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("product_id")
    private String mProductId;
    @SerializedName("product_name")
    private String mProductName;
    @SerializedName("shop_id")
    private String mShopId;
    @SerializedName("shop_title")
    private String mShopTitle;
    @SerializedName("size")
    private String mSize;
    @SerializedName("color")
    private String mColor;

    public String getSize() {
        return mSize;
    }

    public void setSize(String mSize) {
        this.mSize = mSize;
    }

    public String getmColor() {
        return mColor;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
    }


    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getProductId() {
        return mProductId;
    }

    public void setProductId(String productId) {
        mProductId = productId;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getShopId() {
        return mShopId;
    }

    public void setShopId(String shopId) {
        mShopId = shopId;
    }

    public String getShopTitle() {
        return mShopTitle;
    }

    public void setShopTitle(String shopTitle) {
        mShopTitle = shopTitle;
    }

}
