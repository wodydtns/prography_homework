package com.prography.pingpong.room.service;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.room.dto.CreateRoomRequest;
import com.prography.pingpong.room.dto.RoomDetailResponse;
import com.prography.pingpong.room.dto.RoomDto;
import com.prography.pingpong.room.entity.Room;

import java.util.Map;
import java.util.Optional;

public interface RoomService {

    ApiResponse<Map<String, Object>> createRoom(CreateRoomRequest createRoomRequest) throws Exception;

    ApiResponse<Map<String, Object>> getAllRooms(int size, int page);

    ApiResponse<RoomDetailResponse> getRoomDetail(int roomId);

    ApiResponse<Object>  joinTheRoom(int roomId, int userId) throws Exception;

    ApiResponse<Object> outTheRoom(int roomId, int userId);

    ApiResponse<Object> startPingpongGame(int roomId, int userId);
}
