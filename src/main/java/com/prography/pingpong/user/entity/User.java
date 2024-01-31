package com.prography.pingpong.user.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    private Integer id;

    private Integer fakerId;

    private String name;

    private String meail;

    private String status;

    private LocalDateTime created_at;

    private LocalDateTime updated_at ;
}
