package game;

import java.util.Date;

public class Game {
    private int id;
    private String title;
    private String developer;
    private Date releaseDate;
    private String platforms;
    private String genre;
    private boolean award;
    private String storeLink;
    private String description;
    private double criticsScore;
    private double usersScore;

    // Конструктор
    public Game(int id, String title, String developer, Date releaseDate, String platforms,
                String genre, boolean award, String storeLink, String description,
                double criticsScore, double usersScore) {
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
    }

    // Геттеры и сеттеры

    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //String dateString = sdf.format(game.getReleaseDate()); // Для сохранения в БД
    //Date date = sdf.parse("2024-01-01"); // Для чтения из строки
}
