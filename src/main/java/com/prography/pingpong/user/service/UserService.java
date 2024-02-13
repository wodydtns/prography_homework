package com.prography.pingpong.user.service;

import com.prography.pingpong.common.dto.ApiResponse;

import java.util.Map;

public interface UserService {

    ApiResponse<Map<String, Object>> getAllUsers(int size, int page);
}
