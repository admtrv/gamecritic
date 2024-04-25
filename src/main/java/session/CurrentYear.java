package session;

public class CurrentYear {
    private String year;
    private static CurrentYear instance;
    private CurrentYear() { }
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
