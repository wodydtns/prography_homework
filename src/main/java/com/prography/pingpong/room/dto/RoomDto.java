package com.prography.pingpong.room.dto;

import com.prography.pingpong.room.entity.RoomStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomDto {

    private Integer id;

    private String title;

    private Integer hostId;

    private String roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;
}
