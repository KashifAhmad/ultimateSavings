
package com.techease.ultimatesavings.models.recentSearches;

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


    @SerializedName("search_query")
    private String mSearchQuery;

    public String getSearchQuery() {
        return mSearchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        mSearchQuery = searchQuery;
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
