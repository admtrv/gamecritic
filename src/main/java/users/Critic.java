package users;

/**
 * Represents critic within the system, extending the {@link User} class with additional
 * attributes and functionalities specific to critics. This class includes management of
 * balance specific to activities performed by critics.
 */
public class Critic extends User {
    private double balance; // Balance for benefit payments

    /**
     * Constructs new critic with specified id, username, password, and balance.
     * @param id        unique identifier for the critic
     * @param username  critic username used
     * @param password  critic password used
     * @param balance   balance of the critic
     */
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
