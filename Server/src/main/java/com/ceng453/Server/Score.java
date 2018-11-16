package com.ceng453.Server;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable=false,name="id")
    private Integer score_id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable=false, name="timestamp")
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @Column(nullable=false,name="score")
    private Integer score;

    public Score() {}

    // Constructor for contructing a score instance from @Requestbody data
    public Score(User user, LocalDateTime createDateTime, Integer score) {
        this.user = user;
        this.createDateTime = createDateTime;
        this.score = score;
    }

    public Integer getScore_id() {
        return score_id;
    }

    public void setScore_id(Integer score_id) {
        this.score_id = score_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}