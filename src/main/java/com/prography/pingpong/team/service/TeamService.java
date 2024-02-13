package com.prography.pingpong.team.service;

import com.prography.pingpong.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface TeamService {

    public ApiResponse<Object> changeTeamByUser(@PathVariable int roomId, @RequestBody int userId);
}
