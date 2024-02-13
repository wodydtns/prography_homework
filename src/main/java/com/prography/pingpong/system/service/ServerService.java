package com.prography.pingpong.system.service;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.common.dto.CreateUserDto;
import com.prography.pingpong.common.dto.InitRequest;
import com.prography.pingpong.user.entity.User;

public interface ServerService {

    ApiResponse<Object> createUser(InitRequest initRequest);

}
