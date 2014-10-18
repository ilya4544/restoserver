package servlet;

/**
 * Created by Администратор on 18.10.2014.
 */
public class User {
    private int id;
    private String name;
    private float balance;
    private String phone;

    public User(int id, String name, float balance, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
