package game;

import utils.*;

public class Game {
    private int id;
    private String title;
    private String developer;
    private String releaseDate;
    private String platforms;
    private String genre;
    private boolean award;
    private String storeLink;
    private String description;
    private String imagePath;
    private int criticsCount;
    private int usersCount;
    private int criticsSum;
    private int usersSum;
    private double criticsScore;
    private double usersScore;
    private double averageScore;

    public Game(int id, String title, String developer, String releaseDate, String platforms,
                String genre, boolean award, String storeLink, String description, String imagePath,
                int criticsCount, int usersCount, int criticsSum, int usersSum,
                double criticsScore, double usersScore, double averageScore) {
        this.id = id;
        this.title = title;
        this.developer = developer;
        this.releaseDate = DateUtil.convertDate(releaseDate);
        this.platforms = platforms;
        this.genre = genre;
        this.award = award;
        this.storeLink = storeLink;
        this.description = description;
        this.imagePath = imagePath;
        this.criticsCount = criticsCount;
        this.usersCount = usersCount;
        this.criticsSum = criticsSum;
        this.usersSum = usersSum;
        this.criticsScore = criticsScore;
        this.usersScore = usersScore;
        this.averageScore = averageScore;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPlatforms() {
        return platforms;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAward() {
        return award;
    }

    public String getStoreLink() {
        return storeLink;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getCriticsCount() {
        return criticsCount;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public double getUsersScore() {
        return usersScore;
    }

    public double getCriticsScore() {
        return criticsScore;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public int getCriticsSum() {
        return criticsSum;
    }

    public int getUsersSum() {
        return usersSum;
    }


    public void setCriticsCount(int criticsCount) {
        this.criticsCount = criticsCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public void setCriticsSum(int criticsSum) {
        this.criticsSum = criticsSum;
    }

    public void setUsersSum(int usersSum) {
        this.usersSum = usersSum;
    }

    public void setCriticsScore(double criticsScore) {
        this.criticsScore = criticsScore;
    }

    public void setUsersScore(double usersScore) {
        this.usersScore = usersScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }
}
