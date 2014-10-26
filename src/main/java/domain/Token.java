package domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column
    private String token;
    @Column
    private Date date;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Token() {
    }

    public Token(Long userId, String token, Date date) {
        this.userId = userId;
        this.token = token;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
