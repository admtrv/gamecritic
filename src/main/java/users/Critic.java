package users;

public class Critic extends User {
    private double balance; // Баланс для начисления вознаграждений

    public Critic(int id, String username, String password, double balance) {
        super(id, username, password);
        this.balance = balance;
    }

    // Геттеры и сеттеры
    public double getBalance() {
        return balance;
    }
}
