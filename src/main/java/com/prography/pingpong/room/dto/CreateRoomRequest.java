package com.prography.pingpong.room.dto;

import lombok.Getter;

@Getter
public class CreateRoomRequest {

    private int userId;
    private String roomType;
    private String title;
}
