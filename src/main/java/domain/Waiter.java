package domain;

import javax.persistence.*;

@Entity
@Table(name = "waiters")
public class Waiter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private Double balance;
    @Column
    private Integer rating;
    @Column
    private String organization;
    @Column
    private Integer countRating;

    public Waiter() {
    }

    public Integer getCountRating() {
        return countRating;
    }

    public void setCountRating(Integer countRating) {
        this.countRating = countRating;
    }

    public Waiter(String name, String organization, Integer rating) {
        this.name = name;
        this.organization = organization;
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
