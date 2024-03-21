package session;

import game.*;

// Singleton класс
public class CurrentGame {
    private static Game game;
    private static final CurrentGame instance = new CurrentGame();
    private CurrentGame() {}
    public static CurrentGame getInstance() {
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
