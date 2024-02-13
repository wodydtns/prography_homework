package com.prography.pingpong.room.repository;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.room.dto.RoomDto;

import java.util.Map;

public interface CustomRoomRepository {
    ApiResponse<Map<String, Object>> getAllRooms(int size, int page);


}
