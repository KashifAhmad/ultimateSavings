package com.techease.ultimatesavings.models;

import android.content.Context;
import android.widget.Filter;

import com.techease.ultimatesavings.models.updatedPopularSearch.Datum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PopularSearchesHelper {
    private static List<Datum> sRecentSearches = new ArrayList<>();
    public interface OnFindSuggestionsListener {
        void onResults(List<Datum> results);
    }
    public static List<Datum> getHistory(Context context, int count) {

        List<Datum> suggestionList = new ArrayList<>();
        Datum colorSuggestion;
        for (int i = 0; i < sRecentSearches.size(); i++) {
            colorSuggestion = sRecentSearches.get(i);
            colorSuggestion.setIsHistory(true);
            suggestionList.add(colorSuggestion);
            if (suggestionList.size() == count) {
                break;
            }
        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
        for (Datum colorSuggestion : sRecentSearches) {
            colorSuggestion.setIsHistory(false);
        }
    }

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                PopularSearchesHelper.resetSuggestionsHistory();
                List<Datum> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (Datum suggestion : sRecentSearches) {
                        if (suggestion.getSearchQuery().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                Collections.sort(suggestionList, new Comparator<Datum>() {
                    @Override
                    public int compare(Datum lhs, Datum rhs) {
                        return lhs.getIsHistory() ? -1 : 0;
                    }
                });
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<Datum>) results.values);
                }
            }
        }.filter(query);

    }

}
