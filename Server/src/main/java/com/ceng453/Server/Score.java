package com.ceng453.Server;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable=false,name="id")
    private Integer score_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name="timestamp")
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @Column(nullable=false,name="score")
    private Integer score; // password that will be stored as encrypted

}
