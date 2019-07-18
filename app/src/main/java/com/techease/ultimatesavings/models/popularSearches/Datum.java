
package com.techease.ultimatesavings.models.popularSearches;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Datum {
    @SerializedName("search_query")
    private String mSearchQuery;
    @SerializedName("total_seraches")
    private String mTotalSeraches;

    public String getSearchQuery() {
        return mSearchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        mSearchQuery = searchQuery;
    }

    public String getTotalSeraches() {
        return mTotalSeraches;
    }

    public void setTotalSeraches(String totalSeraches) {
        mTotalSeraches = totalSeraches;
    }

}
