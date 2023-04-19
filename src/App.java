public class App {
    public static void main(String[] args) throws Exception {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        RecommendationRunner recommender = new RecommendationRunner();

        recommender.printRecommendationsFor("500");
    }
}
