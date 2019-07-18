
package com.techease.ultimatesavings.models.recentSearches;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("search_query")
    private String mSearchQuery;

    public String getSearchQuery() {
        return mSearchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        mSearchQuery = searchQuery;
    }

}
