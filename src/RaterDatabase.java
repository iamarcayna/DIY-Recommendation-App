
import edu.duke.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.csv.*;

public class RaterDatabase {
    private static HashMap<String, Rater> AllRaters;

    private static void initialize() {
        // this method is only called from addRatings
        if (AllRaters == null) {
            AllRaters = new HashMap<>();
        }
    }

    public static void initialize(String filename) {
        if (AllRaters == null) {
            AllRaters = new HashMap<>();
            addRatings("./src/resources/" + filename);
        }
    }

    public static void addRatings(String filename) {
        initialize();
        FileResource fr = new FileResource(filename);
        CSVParser csvp = fr.getCSVParser();
        for (CSVRecord rec : csvp) {
            String id = rec.get("rater_id");
            String item = rec.get("movie_id");
            String rating = rec.get("rating");
            addRaterRating(id, item, Double.parseDouble(rating));
        }
    }

    public static void addRaterRating(String raterID, String movieID, double rating) {
        initialize();
        Rater rater = null;
        if (AllRaters.containsKey(raterID)) {
            rater = AllRaters.get(raterID);
        } else {
            rater = new EfficientRater(raterID);
            AllRaters.put(raterID, rater);
        }
        rater.addRating(movieID, rating);
    }

    public static Rater getRater(String id) {
        initialize();

        return AllRaters.get(id);
    }

    public static ArrayList<Rater> getRaters() {
        initialize();
        ArrayList<Rater> list = new ArrayList<>(AllRaters.values());

        return list;
    }

    public static int size() {
        return AllRaters.size();
    }

}