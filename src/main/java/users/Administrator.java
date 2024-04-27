package users;

import utils.*;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Represents administrator within the system, extending the {@link User} class.
 * Administrators have special privileges that allow them to perform system-wide
 * operations that regular users and critics cannot, such as uploading game awards.
 */
public class Administrator extends User {
    /**
     * Constructs new administrator with specified id, username, and password.
     * @param id        unique identifier for the administrator
     * @param username  administrator username used
     * @param password  administrator password used
     */
    public Administrator(int id, String username, String password) {
        super(id, username, password);
    }

    /**
     * Performs operation of uploading game awards for the current year.
     * This method accesses the database to update games with awards. One
     * of the fundamental methods needed to see results of vote.
     */
    public void uploadGameAwardsThisYear() {
        try {
            // Getting the current year
            String currentYear = String.valueOf(LocalDate.now().getYear());
            DataBaseUtil.uploadGameAwardsByYear(currentYear);

            System.out.println("Successful uploading awards!");
        } catch (SQLException e) {

            System.err.println("Error uploading awards!");
            e.printStackTrace();
        }
    }
}
