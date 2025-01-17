package com.techease.ultimatesavings.models.recentSearches;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.gson.annotations.SerializedName;


@SuppressLint("ParcelCreator")
@SuppressWarnings("unused")
public class Datum implements SearchSuggestion {


    private boolean mIsHistory = false;

    @SerializedName("color")
    private String mColor;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private String mId;
    @SerializedName("lat")
    private String mLat;
    @SerializedName("lng")
    private String mLng;
    @SerializedName("search_query")
    private String mSearchQuery;
    @SerializedName("size")
    private String mSize;
    @SerializedName("user_id")
    private String mUserId;

    public boolean getIsHistory() {
        return mIsHistory;
    }

    public void setIsHistory(boolean mIsHistory) {
        this.mIsHistory = mIsHistory;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLat() {
        return mLat;
    }

    public void setLat(String lat) {
        mLat = lat;
    }

    public String getLng() {
        return mLng;
    }

    public void setLng(String lng) {
        mLng = lng;
    }

    public String getSearchQuery() {
        return mSearchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        mSearchQuery = searchQuery;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
