package users;

public class Critic extends User {
    private double balance; // Balance for benefit payments

    public Critic(int id, String username, String password, double balance) {
        super(id, username, password);
        this.balance = balance;
    }

    // Getters and setters
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
