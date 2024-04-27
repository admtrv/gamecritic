package session;

import users.*;

import java.io.*;

/**
 * Singleton class for managing current user session across the application. This class
 * ensures that single instance of user is maintained globally, allowing for
 * consistent access and updates to user state across different parts of the application.
 * This class also handles serialization and deserialization of user data ensure
 * persistence across sessions.
 */
public class CurrentUser {
    private static User user;
    private static CurrentUser instance;

    private String SERIALIZATION_FILE = "serialization.ser";
    private CurrentUser() { }

    /**
     * Gets the single instance of current user, creating it if it does not yet exist.
     * @return singleton instance of current user
     */
    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }
    public User getUser() {
        return user;
    }
    public void logIn(User newUser) {
        user = newUser;
    }
    public void logOut(){user = null;}


    /**
     * Serializes current user to serialization file. This method saves
     * current user session to allow persistence between application sessions.
     */
    public void saveCurrentUser() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_FILE))) {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes current user from serialization file. This method
     * attempts to restore user session from the last state saved.
     * If file is not found, the user will not be recovered.
     */
    public void loadCurrentUser() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIALIZATION_FILE))) {
            user = (User) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            user = null; // In case of an error or if the file is not found, the user is not recovered
        }
    }

    /**
     * Deletes serialization file to clear saved user session.
     * Method used to ensure no user session is retained after logging out.
     * @throws IOException if deletion fails
     */
    public void clearCurrentUser() throws IOException {
        File file = new File(SERIALIZATION_FILE);
        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Failed to delete serialization file!");
            }
        }
    }

}
