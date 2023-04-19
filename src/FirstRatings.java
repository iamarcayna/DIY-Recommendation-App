
/**
 *  This class reads data from a file, uses CSVparser to store information
 *  about movies and ratings of movies by different raters.
 * 
 *  It have two public method: loadMovies() and loadRaters()
 *  which returns a Arraylist of Movie/Rater from the file.
 *  
 * @author R.N. Arcayna
 * @version 4.14.2023
 */
import edu.duke.*;
import java.util.ArrayList;
import org.apache.commons.csv.*;

public class FirstRatings {

    public ArrayList<Movie> loadMovies(String fileName) {
        FileResource file = new FileResource("./src/resources/" + fileName);
        CSVParser movieParser = file.getCSVParser();
        ArrayList<Movie> loadedMovies = new ArrayList<>();

        for (CSVRecord record : movieParser) {
            String id = record.get("id");
            String title = record.get("title");
            String year = record.get("year");
            String country = record.get("country");
            String genre = record.get("genre");
            String director = record.get("director");
            int minutes = Integer.parseInt(record.get("minutes"));
            String poster = record.get("poster");
            Movie curMovie = new Movie(id, title, year, genre, director, country, poster, minutes);
            loadedMovies.add(curMovie);
        }
        return loadedMovies;
    }

    public ArrayList<Rater> loadRaters(String fileName) {
        FileResource file = new FileResource("./src/resources/" + fileName);
        CSVParser raterParser = file.getCSVParser();
        ArrayList<Rater> loadedRaters = new ArrayList<>();
        String prevRaterID = "";
        int raterCount = 0;
        for (CSVRecord record : raterParser) {
            String raterID = record.get("rater_id");
            String movieID = record.get("movie_id");
            Double rating = Double.parseDouble(record.get("rating"));

            if (!raterID.equals(prevRaterID)) {
                // IRater rater = new PlainRater(raterID);
                Rater rater = new EfficientRater(raterID);
                rater.addRating(movieID, rating);
                loadedRaters.add(rater);
                prevRaterID = raterID;
                raterCount += 1;
            } else {
                Rater rater = loadedRaters.get(raterCount - 1);
                rater.addRating(movieID, rating);
            }

        }

        return loadedRaters;
    }

}
