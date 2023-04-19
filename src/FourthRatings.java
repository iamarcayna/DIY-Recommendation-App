
/**
 * 
 * @author R.N. Arcayna
 * @version 4.14.2023
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FourthRatings {

    private double getAverageByID(String movieID, int minRaters) {
        double average = 0.0;
        int raterCount = 0;

        for (Rater rater : RaterDatabase.getRaters()) {
            if (rater.hasRating(movieID)) {
                average += rater.getRating(movieID);
                raterCount += 1;
            }
        }
        return raterCount >= minRaters ? average / raterCount : 0.0;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minRaters, Filter filter) {
        ArrayList<Rating> averageRatings = new ArrayList<>();
        for (String movieID : MovieDatabase.filterBy(filter)) {
            double averageRating = getAverageByID(movieID, minRaters);
            if (averageRating > 0) {
                averageRatings.add(new Rating(movieID, averageRating));
            }
        }
        return averageRatings;
    }

    private double dotProduct(Rater me, Rater other) {
        double dotProduct = 0.0;
        for (String movie : me.getItemsRated()) {
            if (other.hasRating(movie)) {
                double meRating = me.getRating(movie) - 5;
                double otherRating = other.getRating(movie) - 5;
                dotProduct = dotProduct + meRating * otherRating;
            }
        }
        return dotProduct;
    }

    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> topSimilar = new ArrayList<>();
        Rater meRater = RaterDatabase.getRater(id);
        for (Rater rater : RaterDatabase.getRaters()) {
            if (!rater.getID().equals(id)) {
                double dotRating = dotProduct(meRater, rater);
                if (dotRating > 0) {
                    Rating rating = new Rating(rater.getID(), dotRating);
                    topSimilar.add(rating);
                }
            }
        }
        Collections.sort(topSimilar, Collections.reverseOrder());
        return topSimilar;
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilar, int minRaters, Filter filter) {
        ArrayList<Rating> moviesWeightedAverage = new ArrayList<>();
        ArrayList<Rating> similarities = getSimilarities(id);
        List<Rating> topSimilar = similarities.size() >= numSimilar ? similarities.subList(0, numSimilar)
                : similarities;

        for (String movieID : MovieDatabase.filterBy(filter)) {
            Double average = 0.0;
            int raterCount = 0;
            for (Rating rating : topSimilar) {
                Rater rater = RaterDatabase.getRater(rating.getItem());
                if (rater.hasRating(movieID)) {
                    average += rating.getValue() * rater.getRating(movieID);
                    raterCount += 1;
                }
            }
            if (average > 0 && raterCount >= minRaters) {
                moviesWeightedAverage.add(new Rating(movieID, average / raterCount));
            }
        }
        Collections.sort(moviesWeightedAverage, Collections.reverseOrder());
        return moviesWeightedAverage;
    }

}
