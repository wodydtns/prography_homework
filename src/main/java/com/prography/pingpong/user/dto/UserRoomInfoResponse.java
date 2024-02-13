package com.prography.pingpong.user.dto;

import com.prography.pingpong.team.entity.Team;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRoomInfoResponse {

    private int id;

    private Integer roomId;

    private String roomType;

    private Integer host;

    @Enumerated(EnumType.STRING)
    private Team team;
}
