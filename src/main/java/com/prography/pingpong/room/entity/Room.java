package com.prography.pingpong.room.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Room {

    private Integer id;

    private String title;

    private Integer host;

    private String roomType;

    private String status;

    private LocalDateTime created_at;

    private LocalDateTime updated_at ;
}
