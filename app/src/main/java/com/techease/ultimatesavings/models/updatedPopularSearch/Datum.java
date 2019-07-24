
package com.techease.ultimatesavings.models.updatedPopularSearch;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
@SuppressWarnings("unused")
public class Datum implements SearchSuggestion {

    public boolean getIsHistory() {
        return mIsHistory;
    }

    public void setIsHistory(boolean mIsHistory) {
        this.mIsHistory = mIsHistory;
    }

    private boolean mIsHistory = false;

    @SerializedName("color")
    private String mColor;
    @SerializedName("lat")
    private String mLat;
    @SerializedName("lng")
    private String mLng;
    @SerializedName("search_query")
    private String mSearchQuery;
    @SerializedName("size")
    private String mSize;
    @SerializedName("total_seraches")
    private String mTotalSeraches;

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
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

    public String getTotalSeraches() {
        return mTotalSeraches;
    }

    public void setTotalSeraches(String totalSeraches) {
        mTotalSeraches = totalSeraches;
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
