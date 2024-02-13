package com.prography.pingpong.user.controller;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name= "TeamController", description = "TeamController api 목록")
public class UserController {

    private final UserService userService;
    @Operation(summary = "유저 전체 조회 API", description = "전체 유저 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllUserList(@RequestParam("size") int size, @RequestParam("page") int page){
        ApiResponse<Map<String, Object>> response = userService.getAllUsers(size,page);
        return ResponseEntity.ok(response);
    }

}
