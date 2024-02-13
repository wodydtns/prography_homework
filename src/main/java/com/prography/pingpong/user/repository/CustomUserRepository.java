package com.prography.pingpong.user.repository;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.user.dto.UserRoomInfoResponse;
import com.prography.pingpong.user.dto.UserRoomStatusResponse;

import java.util.List;
import java.util.Map;

public interface CustomUserRepository {

    ApiResponse<Map<String, Object>> getAllUserList(int size, int page);

    List<UserRoomStatusResponse>  isInRoomByUserid(int userId);

    Long countJoinUser(int roomId);

    List<UserRoomInfoResponse> currentRoomInfo(int roomId);
}
