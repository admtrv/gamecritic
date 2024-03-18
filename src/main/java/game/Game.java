package game;

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
    private double criticsScore;
    private double usersScore;
    private String imagePath;

    // Конструктор
    public Game(int id, String title, String developer, String releaseDate, String platforms,
                String genre, boolean award, String storeLink, String description,
                double criticsScore, double usersScore, String imagePath) {
        this.id = id;
        this.title = title;
        this.developer = developer;
        this.releaseDate = releaseDate;
        this.platforms = platforms;
        this.genre = genre;
        this.award = award;
        this.storeLink = storeLink;
        this.description = description;
        this.criticsScore = criticsScore;
        this.usersScore = usersScore;
        this.imagePath = imagePath;
    }

    // Геттеры и сеттеры


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

    public double getCriticsScore() {
        return criticsScore;
    }

    public double getUsersScore() {
        return usersScore;
    }

    public String getImagePath() {
        return imagePath;
    }

    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //String dateString = sdf.format(game.getReleaseDate()); // Для сохранения в БД
    //Date date = sdf.parse("2024-01-01"); // Для чтения из строки
}
