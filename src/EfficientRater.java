import java.util.ArrayList;
import java.util.HashMap;

public class EfficientRater implements Rater {
    private String Id;
    private HashMap<String, Rating> AllRatings;

    public EfficientRater(String id) {
        Id = id;
        AllRatings = new HashMap<>();
    }

    public void addRating(String item, double rating) {
        AllRatings.put(item, new Rating(item, rating));
    }

    public boolean hasRating(String item) {
        return AllRatings.containsKey(item);
    }

    public String getID() {
        return Id;
    }

    public double getRating(String item) {
        return hasRating(item) ? AllRatings.get(item).getValue() : -1;
    }

    public int numRatings() {
        return AllRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<>();

        for (String key : AllRatings.keySet()) {
            list.add(key);
        }
        return list;
    }
}
