package session;

import game.*;

// Singleton class
public class CurrentGame {
    private static Game game;
    private static CurrentGame instance;
    private CurrentGame() { }
    public static CurrentGame getInstance() {
        if (instance == null) {
            instance = new CurrentGame();
        }
        return instance;
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game newGame) {
        game = newGame;
    }
    public void resetGame() {
        game = null;
    }
}
