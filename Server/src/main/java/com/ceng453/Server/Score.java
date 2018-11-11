package com.ceng453.Server;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable=false, name="timestamp")
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @Column(nullable=false,name="score")
    private Integer score; // password that will be stored as encrypted

    public Score() {}

    public Score(User user, LocalDateTime createDateTime, Integer score) {
        this.user = user;
        this.createDateTime = createDateTime;
        this.score = score;
    }
}
