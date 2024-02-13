package com.prography.pingpong.team.controller;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.team.service.TeamService;
import com.prography.pingpong.user.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
@Tag(name= "TeamController", description = "TeamController api 목록")
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "팀 변경 API", description = "사용자 팀 변경")
    @PutMapping("{roomId}")
    public ResponseEntity<ApiResponse<Object>> changeTeamByUser(@PathVariable int roomId,  @RequestBody UserRequest userRequest){
        ApiResponse<Object> apiResponse = teamService.changeTeamByUser(roomId, userRequest.getUserId());
        return ResponseEntity.ok(apiResponse);
    }
}
