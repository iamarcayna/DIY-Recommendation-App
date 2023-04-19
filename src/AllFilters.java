
/**
 * 
 * @author R.N. Arcayna
 * @version 4.14.2023
 */

import java.util.ArrayList;

public class AllFilters implements Filter {
    private ArrayList<Filter> AllFilters;

    public AllFilters() {
        AllFilters = new ArrayList<Filter>();
    }

    public void addFilter(Filter filter) {
        AllFilters.add(filter);
    }

    @Override
    public boolean satisfies(String id) {
        for (Filter filter : AllFilters) {
            if (!filter.satisfies(id)) {
                return false;
            }
        }
        return true;
    }

}
