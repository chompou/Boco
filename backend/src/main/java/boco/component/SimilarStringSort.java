package boco.component;

import boco.model.rental.Listing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimilarStringSort {
    private SimilarStringSort() {}

    /**
     * Searches listings for matches with searchString and sorts the listings
     * by most matching to least matching. Zero matching listings is thrown away.
     *
     * The higher the includePercentage the more listings is included.
     */
    public static List<Listing> searchListings(List<Listing> listings,
                                               String searchString,
                                               int includePercentage) {
        int n = listings.size();
        String[] searchWords = searchString.toLowerCase().split(" ");

        IntPair[] score = getDefaultScoreArray(n);

        for (int i = 0; i < n; i++) {
            String listingString = listings.get(i).getName().toLowerCase();
            for (int j = 0; j < searchWords.length; j++) {
                if (searchWords[j].isBlank()) continue;
                if (listingString.contains(searchWords[j])) {
                    score[i].incrementB();
                }
            }
        }
        return getSearchResult(score, listings, includePercentage);
    }

    private static IntPair[] getDefaultScoreArray(int n) {
        IntPair[] editDistances = new IntPair[n]; // a = listing index, b = edit distance
        for (int i = 0; i < n; i++) {
            editDistances[i] = new IntPair(i, 0);
        }
        return editDistances;
    }

    /**
     * @param listingScores List including score
     * @param listings Listings to search
     * @param includePercentage Minimum percentage of listings with score > 0 to include.
     *                          If one listing with score X is included in return list,
     *                          then every listing with score X is included.
     *
     * @return List of leases sorted by best score and without the least relevant scores
     * (according to explained rules)
     */
    private static List<Listing> getSearchResult(IntPair[] listingScores,
                                                 List<Listing> listings,
                                                 int includePercentage) {
        Arrays.sort(listingScores);
        int numberOfListingsWithAnyMatch = 0;
        for (int i = 0; i < listingScores.length; i++) {
            if (listingScores[i].getB() > 0) numberOfListingsWithAnyMatch++;
        }

        int minNumberOfListings = (numberOfListingsWithAnyMatch * includePercentage) / 100;
        int minScore = listingScores[Math.min(Math.max(minNumberOfListings-1, 0), listingScores.length-1)].getB();

        List<Listing> sortedListings = new ArrayList<>();
        for (int i = 0; i < listingScores.length; i++) {
            if (listingScores[i].getB() < minScore) break;
            sortedListings.add(listings.get(listingScores[i].getA()));
        }
        return sortedListings;

    }
}
