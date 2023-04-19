
/**
 * 
 * @author R.N. Arcayna
 * @version 4.14.2023
 */

public class MinutesFilter implements Filter {
    private int min;
    private int max;

    public MinutesFilter(int minDuration, int maxDuration) {
        this.min = minDuration;
        this.max = maxDuration;
    }

    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getMinutes(id) >= min && MovieDatabase.getMinutes(id) <= max;
    }

}
