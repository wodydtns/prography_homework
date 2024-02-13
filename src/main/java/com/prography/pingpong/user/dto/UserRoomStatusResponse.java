package com.prography.pingpong.user.dto;

import com.prography.pingpong.user.entity.UserStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRoomStatusResponse {

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Integer roomId;
}
