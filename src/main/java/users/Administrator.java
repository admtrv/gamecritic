package users;

import database.*;

import java.sql.SQLException;
import java.time.LocalDate;

public class Administrator extends User {
    public Administrator(int id, String username, String password) {
        super(id, username, password);
    }

    // Методы специфичные для администратора
    public void uploadGameAwardsThisYear() {
        try {
            // Получаем текущий год
            String currentYear = String.valueOf(LocalDate.now().getYear());
            DataBaseUtil.uploadGameAwardsByYear(currentYear);
            System.out.println("Successful uploading awards!");
        } catch (SQLException e) {
            System.err.println("Error uploading awards!");
            e.printStackTrace();
        }
    }
}
