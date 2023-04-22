
/**
 *  
 * @author R.N. Arcayna
 * @version 4.14.2023
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class RecommendationRunner implements Recommender {
    private FourthRatings fourthRatings;
    private ArrayList<Rating> averageMovieRatings;

    public RecommendationRunner() {
        this.fourthRatings = new FourthRatings();
        this.averageMovieRatings = fourthRatings.getAverageRatingsByFilter(5, new TrueFilter());
    }

    @Override
    public ArrayList<String> getItemsToRate() {
        Random random = new Random(System.currentTimeMillis());
        HashSet<Integer> indexUsed = new HashSet<>();
        AllFilters filter = new AllFilters();
        filter.addFilter(new MinutesFilter(60, 200));
        filter.addFilter(new YearAfterFilter(1990));
        ArrayList<String> moviesToRate = new ArrayList<>();
        ArrayList<Rating> selectFrom = fourthRatings.getAverageRatingsByFilter(30, filter);

        while (moviesToRate.size() < 10) {
            int index = random.nextInt(selectFrom.size());
            if (!indexUsed.contains(index)) {
                indexUsed.add(index);
                moviesToRate.add(selectFrom.get(index).getItem());
            }
        }

        return moviesToRate;
    }

    @Override
    public void printRecommendationsFor(String webRaterID) {
        StringBuilder htmlTable = new StringBuilder();
        ArrayList<Rating> topMovies = fourthRatings.getSimilarRatingsByFilter(webRaterID, 20, 5, new TrueFilter());
        boolean hasRecommendation = false;
        setUpTable(htmlTable);

        for (Rating movie : topMovies) {
            if (!RaterDatabase.getRater(webRaterID).hasRating(movie.getItem())) {
                addRow(htmlTable, movie.getItem());
                hasRecommendation = true;
            }
        }
        if (!hasRecommendation) {
            htmlTable.append(
                    "<tr><td><h2>Sorry! We can't find any movies to recommend. Please try again.</h2></td></tr>");
        }
        htmlTable.append("</table>");
        System.out.println(htmlTable.toString());
    }

    private void setUpTable(StringBuilder table) {
        table.append("<style>");
        table.append("body{background: #181818;}");
        table.append(".content {background: #181818 !important;}");
        table.append(
                ".movie-table{margin: 0 auto;background: #232323;border-radius: 20px;padding: 10px;}");
        table.append(".thumb-nail{width: 300px;height: 350px;border-radius: 10px;}");
        table.append(".movie-details {width: 200px;padding: 10px;}");
        table.append("td h2{color: #0098d9;}");
        table.append("td p{color: #d6d6d6;}");
        table.append("span{font-weight: 700;font-style: italic;}");
        table.append(
                "td a{text-decoration: none;width: 100px;height: 30px;background: #007c6a;border-radius: 10px;padding: 10px;display: flex;align-items: center;justify-content: center;font-size: 1.1rem;font-weight: 700;color: #d5effa;}");
        table.append("</style>");
        table.append("<table class=\"movie-table\">");
    }

    private void addRow(StringBuilder table, String movieID) {
        table.append("<tr>");
        table.append("<td><img class=\"thumb-nail\" src=\"" + MovieDatabase.getPoster(movieID) + "\" /></td>");
        table.append("<td class=\"movie-details\">");
        table.append("<h2>" + MovieDatabase.getTitle(movieID) + "</h2><hr/>");
        table.append("<p><span>Rating:</span> " + getRating(movieID) + " &#x2605;</p>");
        table.append("<p><span>Genre:</span> " + MovieDatabase.getGenres(movieID) + "</p>");
        table.append("<p><span>Year: </span> " + MovieDatabase.getYear(movieID) + "</p>");
        table.append("<p><span>Duration: </span> " + MovieDatabase.getMinutes(movieID) + " mins</p></td>");
        table.append("<td><a href=\"https://www.imdb.com/title/tt" + movieID + "/\">Watch</a></td>");
        table.append("</tr>");
    }

    private double getRating(String movieID) {
        for (Rating rating : averageMovieRatings) {
            if (rating.getItem().equals(movieID)) {
                return Math.floor(rating.getValue() * 10) / 10;
            }
        }
        return 0.0;
    }
}
