package session;

import users.*;

import java.io.*;

// Singleton class
public class CurrentUser {
    private static User user;
    private static CurrentUser instance;
    private CurrentUser() { }
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


    // Serialization of the current user
    public void saveCurrentUser() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("serialization.ser"))) {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCurrentUser() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serialization.ser"))) {
            user = (User) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            user = null; // In case of an error or if the file is not found, the user is not recovered
        }
    }

    public void clearCurrentUser() throws IOException {
        File file = new File("serialization.ser");
        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Failed to delete serialization file!");
            }
        }
    }

}
