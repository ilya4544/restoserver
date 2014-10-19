package domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Администратор on 18.10.2014.
 */
@Entity
@Table(name = "visits")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Integer rating;
    @Column
    private Double payment;
    @Column
    private Date date;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "waiter_id")
    private Long waiterId;
    @Column
    private String comment;

    public Visit() {
    }

    public Visit(Long userId, Long waiterId, Integer rating, String comment, Date date) {
        this.rating = rating;
        this.date = date;
        this.userId = userId;
        this.waiterId = waiterId;
        this.comment = comment;
        this.payment = 0.0;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Long waiterId) {
        this.waiterId = waiterId;
    }
}
