package session;

/**
 * Singleton class for managing currently selected year in the application. This class
 * ensures that single instance of year is maintained globally, allowing for
 * consistent access and updates to year state across different parts of the application.
 */
public class CurrentYear {
    private String year;
    private static CurrentYear instance;
    private CurrentYear() { }

    /**
     * Gets the single instance of current year, creating it if it does not yet exist.
     * @return singleton instance of current year
     */
    public static CurrentYear getInstance() {
        if (instance == null) {
            instance = new CurrentYear();
        }
        return instance;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void resetYear() {
        year = null;
    }

}
