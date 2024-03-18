package session;

import users.*;

import java.io.*;

// Singleton класс
public class CurrentUser {
    private static User user;
    private static final CurrentUser instance = new CurrentUser();
    private CurrentUser() {}
    public static CurrentUser getInstance() {
        return instance;
    }
    public User getUser() {
        return user;
    }
    public void logIn(User newUser) {
        user = newUser;
    }
    public void logOut(){user = null;}

    // В классе CurrentUser
    public void saveCurrentUser() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("currentUser.ser"))) {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCurrentUser() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("currentUser.ser"))) {
            user = (User) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            user = null; // В случае ошибки или если файл не найден, пользователь не восстанавливается
        }
    }

    public void clearCurrentUser() throws IOException {
        File file = new File("currentUser.ser");
        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Failed to delete currentUser.ser file!");
            }
        }
    }

}
