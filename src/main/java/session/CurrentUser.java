package session;

import users.User;

// Singleton класс
public class CurrentUser {
    private static User user;
    private static final CurrentUser instance = new CurrentUser();

    private CurrentUser() {}

    public static CurrentUser getInstance() {
        return instance;
    }

    public void setUser(User newUser) {
        user = newUser;
    }

    public User getUser() {
        return user;
    }

}
