package com.prography.pingpong.room.entity;

import jakarta.persistence.Entity;

@Entity
public class UserRoom {

    private Integer id;

    private Integer roomId;

    private Integer userId;

    private String team;
}

