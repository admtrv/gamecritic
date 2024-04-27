package session;

import game.*;

/**
 * Singleton class for managing the currently selected game across the application.
 * This class ensures that single instance of game is maintained globally, allowing for
 * consistent access and updates to game state across different parts of the application.
 */
public class CurrentGame {
    private static Game game;
    private static CurrentGame instance;
    private CurrentGame() { }

    /**
     * Gets the single instance of current game, creating it if it does not yet exist.
     * @return singleton instance of current game
     */
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
