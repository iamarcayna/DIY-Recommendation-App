
/**
 * 
 * @author R.N. Arcayna
 * @version 4.14.2023
 */

public class YearAfterFilter implements Filter {
	private int myYear;

	public YearAfterFilter(int year) {
		myYear = year;
	}

	@Override
	public boolean satisfies(String id) {
		return MovieDatabase.getYear(id) >= myYear;
	}

}
