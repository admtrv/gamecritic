package users;

import java.io.Serializable;

/**
 * Represents a user within the system, encapsulating key user information and
 * functionalities. This class is fundamental for managing user identity and is
 * designed to be extended.
 */
public class User implements Serializable {
    private int id;
    private String username;
    private String password;

    /**
     * Constructs new user with specified id, username, and password.
     * @param id        unique identifier for the user
     * @param username  users username used
     * @param password  users password used
     */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public int getId(){
        return id;
    }

    public void setUsername(String newUsername) {
        username = newUsername;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }
}
