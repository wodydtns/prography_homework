package com.prography.pingpong.user.entity;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class User {

    private Integer id;

    private Integer fakerId;

    private String name;

    private String meail;

    private String status;

    private LocalDateTime created_at;

    private LocalDateTime updated_at ;
}
