package com.prography.pingpong.room.controller;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.room.dto.CreateRoomRequest;
import com.prography.pingpong.room.dto.RoomDetailResponse;
import com.prography.pingpong.room.service.RoomService;
import com.prography.pingpong.user.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/room")
@RestController
@RequiredArgsConstructor
@Tag(name= "RoomController", description = "Room Controller api 목록")
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "방 생성 API", description = "방 생성하기")
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> createRoom(@RequestBody CreateRoomRequest createRoomRequest) {
        ApiResponse<Map<String, Object>> created = null;
        try {
            created = roomService.createRoom(createRoomRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "방 전체 조회 API",description = "전체 모든 방 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllRoom(@RequestParam("size") int size, @RequestParam("page") int page){
        ApiResponse<Map<String, Object>> response = roomService.getAllRooms(size,page);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "방 상세 조회 API", description = "방 상세 조회")
    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<RoomDetailResponse>> getRoomDetail(@PathVariable int roomId){
        ApiResponse<RoomDetailResponse> roomDetail = roomService.getRoomDetail(roomId);
        return ResponseEntity.ok(roomDetail);
    }


    @Operation(summary = "방 참가 API", description = "방 참가")
    @PostMapping("/attention/{roomId}")
    public ResponseEntity<ApiResponse<Object>> joinTheRoom(@PathVariable int roomId , @RequestBody UserRequest userRequest) throws Exception {
        ApiResponse<Object> apiResponse = roomService.joinTheRoom(roomId, userRequest.getUserId());
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "방 나가기 API", description = "방 나가기")
    @PostMapping("/out/{roomId}")
    public ResponseEntity<ApiResponse<Object>> outTheRoom(@PathVariable int roomId,  @RequestBody UserRequest userRequest){
        ApiResponse<Object> apiResponse = roomService.outTheRoom(roomId,userRequest.getUserId());
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "게임시작 API", description = "게임 시작")
    @PutMapping("/start/{roomId}")
    public ResponseEntity<ApiResponse<Object>> startPingpongGame(@PathVariable int roomId, @RequestBody UserRequest userRequest){
        ApiResponse<Object> apiResponse = roomService.startPingpongGame(roomId,userRequest.getUserId());
        return ResponseEntity.ok(apiResponse);
    }
}
