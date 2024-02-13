package com.prography.pingpong.system.controller;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.common.dto.InitRequest;
import com.prography.pingpong.system.service.ServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name= "ServerController", description = "ServerController api 목록")
public class ServerController {

    private final ServerService serverService;

    @Operation(summary = "헬스체크 API", description = "서버 헬스 체크")
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Object>> getServerHealthCondition(){
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            response = new ApiResponse<>(200, "API 요청이 성공했습니다.", null);
        }catch (Exception e){
            response = new ApiResponse<>(500,"에러가 발생했습니다.\"", null );
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "초기화 API", description = "모든 초기화")
    @PostMapping("/init")
    @Transactional
    public ResponseEntity<ApiResponse<Object>> createUser(@RequestBody InitRequest initRequest){
        ApiResponse<Object> response = serverService.createUser(initRequest);

        return ResponseEntity.ok(response);
    }
}
