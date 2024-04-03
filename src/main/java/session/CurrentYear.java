package session;

public class CurrentYear {
    private String year;
    private static final CurrentYear instance = new CurrentYear();
    private CurrentYear() {}
    public static CurrentYear getInstance() {
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
