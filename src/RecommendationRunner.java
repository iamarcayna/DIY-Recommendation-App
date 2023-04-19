
/**
 *  
 * @author R.N. Arcayna
 * @version 4.14.2023
 */

import java.util.ArrayList;
import java.util.Random;

public class RecommendationRunner implements Recommender {
    private Random _random;
    private AllFilters _filter;

    public RecommendationRunner() {
        this._random = new Random();
        this._filter = new AllFilters();
        _filter.addFilter(new MinutesFilter(60, 200));
        _filter.addFilter(new YearAfterFilter(1990));
    }

    @Override
    public ArrayList<String> getItemsToRate() {
        ArrayList<String> moviesToRate = new ArrayList<>();
        ArrayList<String> selectFrom = MovieDatabase.filterBy(_filter);
        int movieCount = 10;

        while (movieCount > 0) {
            moviesToRate.add(selectFrom.get(_random.nextInt(selectFrom.size())));
            movieCount -= 1;
        }

        return moviesToRate;
    }

    @Override
    public void printRecommendationsFor(String webRaterID) {
        StringBuilder htmlTable = new StringBuilder();
        FourthRatings fourthRatings = new FourthRatings();
        ArrayList<Rating> topMovies = fourthRatings.getSimilarRatingsByFilter(webRaterID, 20, 5, _filter);
        setUpTable(htmlTable);

        if (topMovies.size() == 0) {
            htmlTable.append(
                    "<tr><td><h2>Sorry! We can't find any movies to recommend. Please try again.</h2></td></tr>");

        } else {
            for (Rating movie : topMovies) {
                if (!RaterDatabase.getRater(webRaterID).hasRating(movie.getItem())) {
                    addRow(htmlTable, movie);
                }
            }
        }
        htmlTable.append("</table>");
        System.out.println(htmlTable.toString());
    }

    private void setUpTable(StringBuilder table) {
        table.append("<style>");
        table.append("body{background: #181818;}");
        table.append(
                ".movie-table{margin: 0 auto;background: #232323;border-radius: 20px;padding: 10px;}");
        table.append(".thumb-nail{width: 300px;height: 350px;border-radius: 10px;}");
        table.append(".movie-details {width: 200px;padding: 10px;}");
        table.append("h2{color: #0098d9;}");
        table.append("p{color: #d6d6d6;}");
        table.append("span{font-weight: 700;font-style: italic;}");
        table.append(
                "a{text-decoration: none;width: 100px;height: 30px;background: #007c6a;border-radius: 10px;padding: 10px;display: flex;align-items: center;justify-content: center;font-size: 1.1rem;font-weight: 700;color: #d5effa;}");
        table.append("</style>");
        table.append("<table class=\"movie-table\">");
    }

    private void addRow(StringBuilder table, Rating movie) {
        table.append("<tr>");
        table.append("<td><img class=\"thumb-nail\" src=\"" + MovieDatabase.getPoster(movie.getItem()) + "\" /></td>");
        table.append("<td class=\"movie-details\">");
        table.append("<h2>" + MovieDatabase.getTitle(movie.getItem()) + "</h2><hr/>");
        table.append("<p><span>Genre:</span> " + MovieDatabase.getGenres(movie.getItem()) + "</p>");
        table.append("<p><span>Year: </span> " + MovieDatabase.getYear(movie.getItem()) + "</p>");
        table.append("<p><span>Duration: </span> " + MovieDatabase.getMinutes(movie.getItem()) + " mins</p></td>");
        table.append("<td><a href=\"https://www.imdb.com/title/tt" + movie.getItem() + "/\">Watch</a></td>");
        table.append("</tr>");
    }
}
