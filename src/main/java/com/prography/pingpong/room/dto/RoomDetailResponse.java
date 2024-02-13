package com.prography.pingpong.room.dto;

import com.prography.pingpong.room.entity.RoomStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RoomDetailResponse {

    private Integer id;

    private String title;

    private Integer host;

    private String roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt ;
}
