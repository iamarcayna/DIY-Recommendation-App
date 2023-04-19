
import java.util.ArrayList;
import java.util.HashMap;

public class MovieDatabase {
    private static HashMap<String, Movie> AllMovies;

    public static void initialize(String movieFile) {
        if (AllMovies == null) {
            AllMovies = new HashMap<>();
            loadMovies(movieFile);
        }
    }

    private static void initialize() {
        if (AllMovies == null) {
            AllMovies = new HashMap<>();
            loadMovies("ratedmoviesfull.csv");
        }
    }

    private static void loadMovies(String fileName) {
        FirstRatings fr = new FirstRatings();
        ArrayList<Movie> movieList = fr.loadMovies(fileName);
        for (Movie movie : movieList) {
            AllMovies.put(movie.getID(), movie);
        }
    }

    public static boolean containsID(String id) {
        initialize();
        return AllMovies.containsKey(id);
    }

    public static int getYear(String id) {
        initialize();
        return AllMovies.get(id).getYear();
    }

    public static String getGenres(String id) {
        initialize();
        return AllMovies.get(id).getGenres();
    }

    public static String getTitle(String id) {
        initialize();
        return AllMovies.get(id).getTitle();
    }

    public static Movie getMovie(String id) {
        initialize();
        return AllMovies.get(id);
    }

    public static String getPoster(String id) {
        initialize();
        return AllMovies.get(id).getPoster();
    }

    public static int getMinutes(String id) {
        initialize();
        return AllMovies.get(id).getMinutes();
    }

    public static String getCountry(String id) {
        initialize();
        return AllMovies.get(id).getCountry();
    }

    public static String getDirector(String id) {
        initialize();
        return AllMovies.get(id).getDirector();
    }

    public static int size() {
        return AllMovies.size();
    }

    public static ArrayList<String> filterBy(Filter filter) {
        initialize();
        ArrayList<String> list = new ArrayList<>();
        for (String id : AllMovies.keySet()) {
            if (filter.satisfies(id)) {
                list.add(id);
            }
        }

        return list;
    }

}
