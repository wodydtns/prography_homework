package com.prography.pingpong.room.service;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.room.dto.CreateRoomRequest;
import com.prography.pingpong.room.dto.RoomDetailResponse;
import com.prography.pingpong.room.entity.Room;
import com.prography.pingpong.room.entity.RoomStatus;
import com.prography.pingpong.room.entity.RoomType;
import com.prography.pingpong.room.entity.UserRoom;
import com.prography.pingpong.room.repository.CustomRoomRepository;
import com.prography.pingpong.room.repository.RoomRepository;
import com.prography.pingpong.room.repository.UserRoomRepository;
import com.prography.pingpong.team.entity.Team;
import com.prography.pingpong.user.dto.UserRoomInfoResponse;
import com.prography.pingpong.user.dto.UserRoomStatusResponse;
import com.prography.pingpong.user.entity.User;
import com.prography.pingpong.user.entity.UserStatus;
import com.prography.pingpong.user.repository.CustomUserRepository;
import com.prography.pingpong.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final UserRoomRepository userRoomRepository;

    private final CustomRoomRepository customRoomRepository;

    private final CustomUserRepository customUserRepository;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> endGameFuture;

    @Override
    @Transactional
    public ApiResponse<Map<String, Object>> createRoom(CreateRoomRequest createRoomRequest) throws Exception {
        try {
            User user = userRepository.findById(createRoomRequest.getUserId()).orElseThrow(() -> new Exception());
            if (user != null && !UserStatus.ACTIVE.equals(user.getStatus())) {
                return new ApiResponse<>(201, "불가능한 요청입니다.", null);
            }
            List<UserRoomStatusResponse>  response = customUserRepository.isInRoomByUserid(createRoomRequest.getUserId());
            int responseRoomId = 0;
            for (UserRoomStatusResponse userRoomStatusResponse: response) {
                    responseRoomId = userRoomStatusResponse.getRoomId();
            }
            if ( responseRoomId > 0 ) {
                return new ApiResponse<>(201, "불가능한 요청입니다.", null);
            }
            Room room = Room.from(createRoomRequest);
            UserRoom created = UserRoom.builder()
                    .room(room)
                    .user(user)
                    .build();

            if (created != null) {
                userRoomRepository.save(created);
            }

            return new ApiResponse<>(200, "API 요청이 성공했습니다.", null);
        }catch(Exception e){
            return new ApiResponse<>(500, "에러가 발생했습니다.", null);
        }

    }

    @Override
    public ApiResponse<Map<String, Object>> getAllRooms(int size, int page) {
        // 방을 삭제하고 getAllRooms하면 => 다른 방이 하나 생김
        return customRoomRepository.getAllRooms(size, page);
    }

    @Override
    public ApiResponse<RoomDetailResponse> getRoomDetail(int roomId) {
        try {
            Room roomDetail = roomRepository.findById(roomId);
            if (roomDetail == null) {
                return new ApiResponse<>(201, "불가능한 요청입니다.", null);
            }
            RoomDetailResponse roomDetailResponse = new RoomDetailResponse();
            roomDetailResponse.setId(roomDetail.getId());
            roomDetailResponse.setTitle(roomDetail.getTitle());
            roomDetailResponse.setHost(roomDetail.getHost());
            roomDetailResponse.setRoomType(roomDetail.getRoomType());
            roomDetailResponse.setStatus(roomDetail.getStatus());
            roomDetailResponse.setCreatedAt(roomDetail.getCreatedAt());
            roomDetailResponse.setUpdatedAt(roomDetail.getUpdatedAt());

            return new ApiResponse<>(200, "API 요청이 성공했습니다.", roomDetailResponse);
        }catch(Exception e){
            return new ApiResponse<>(500, "에러가 발생했습니다.", null);
        }
    }

    @Override
    @Transactional
    public ApiResponse<Object> joinTheRoom(int roomId, int userId) throws Exception {
        ApiResponse<Object> apiResponse = new ApiResponse<>(200,"API 요청이 성공했습니다.", null );
        try {
            Room room = roomRepository.findById(roomId);

            if( room == null){
                return new ApiResponse<>(500,"에러가 발생했습니다.\"", null );
            }
            if (!RoomStatus.WAIT.equals(room.getStatus())) {
                return new ApiResponse<>(201,"불가능한 요청입니다.", null );
            }
            List<UserRoomStatusResponse>  response = customUserRepository.isInRoomByUserid(userId);
            UserStatus userStatus = UserStatus.ACTIVE;
            int responseRoomId = 0;
            if(response != null){
                for (UserRoomStatusResponse userRoomStatusResponse: response){
                    userStatus = userRoomStatusResponse.getStatus();
                    responseRoomId = userRoomStatusResponse.getRoomId();
                    if ( !UserStatus.ACTIVE.equals(userStatus) && responseRoomId > 0) {
                        return new ApiResponse<>(201,"불가능한 요청입니다.", null );
                    }
                }
            }

            //
            // 참가하고자 하는 방의 정원 미달 or full인지 확인 코드 추가 필요
            List<UserRoomInfoResponse> userRoomInfos = customUserRepository.currentRoomInfo(roomId);

            String teamFlag = "RED";
            int countRedTeam = 0;
            int countBlueTeam = 0;
            for (UserRoomInfoResponse userRoomInfoResponse: userRoomInfos) {
                if(RoomType.SINGLE.toString().equals(room.getRoomType())) {
                    // User room의 team을 BLUE로 설정
                    teamFlag = "BLUE";
                }else if(RoomType.DOUBLE.toString().equals(room.getRoomType())){
                    // User room의 count를 가져와, RED가 두명이면 BULE로 설정 = 역도 마찬가지
                    String teamName =userRoomInfoResponse.getTeam().toString();
                    if("RED".equals(teamName)){
                        countRedTeam += 1;
                    }else if("BLUE".equals(teamName)){
                        countBlueTeam += 1;
                    }

                    if(countRedTeam > countBlueTeam){
                        teamFlag = "BLUE";
                    }else if(countRedTeam == countBlueTeam){
                        teamFlag = "RED";
                    }
                }
            }
            int roomFullCount = 2;
            if(RoomType.DOUBLE.toString().equals(room.getRoomType())){
                roomFullCount *= 2;
            }
            if(userRoomInfos.size() < roomFullCount){
                // userRoom 테이블에 값 추가 => 방 참가
                User user = userRepository.findById(userId).orElseThrow(() -> new Exception());
                UserRoom created = UserRoom.from(room, user, Team.RED);
                if("BLUE".equals(teamFlag)){
                    created = UserRoom.from(room, user, Team.BLUE);
                }

                if (created != null) {
                    userRoomRepository.save(created);
                }else{
                    return new ApiResponse<>(201,"불가능한 요청입니다.", null );
                }
            }else{
                return new ApiResponse<>(201,"불가능한 요청입니다.", null );
            }

        }catch(Exception e){
            apiResponse = new ApiResponse<>(500,"에러가 발생했습니다.\"", null );
            throw e;
        }
        return apiResponse;
    }

    @Override
    @Transactional
    public ApiResponse<Object> outTheRoom(int roomId, int userId) {

        ApiResponse<Object> apiResponse = new ApiResponse<>(200,"API 요청이 성공했습니다.", null );
        try {
            UserRoom userRoom = userRoomRepository.findByUserId(userId);
            Room playRoom = roomRepository.findById(roomId);
            if (userRoom == null) {
                return new ApiResponse<>(201, "불가능한 요청입니다.", null);
            }
            Optional<Room> room = roomRepository.findById(userRoom.getRoom().getId());

            if (room.isPresent() && !RoomStatus.WAIT.equals(room.get().getStatus())) {
                return new ApiResponse<>(201, "불가능한 요청입니다.", null);
            }

            int isHost = roomRepository.countByHost(userId);
            if (isHost > 0) {
                // user Room에서 모두 삭제
                userRoomRepository.deleteById(roomId);
                roomRepository.deleteByHost(userId);
                // 해당 방 status를 finish로 변경
                playRoom.setStatus(RoomStatus.FINISH);
                roomRepository.save(playRoom);
            } else {
                userRoomRepository.deleteById(userId);
            }
        }catch(Exception e){
            apiResponse = new ApiResponse<>(500,"에러가 발생했습니다.\"", null );
            throw e;
        }
        return apiResponse;
    }

    @Override
    @Async
    public ApiResponse<Object> startPingpongGame(int roomId, int userId) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(200,"API 요청이 성공했습니다.", null );
        try {
            int isHost = 0;
            isHost = roomRepository.countByHost(userId);
            if( isHost <= 0){
                return new ApiResponse<>(201, "불가능한 요청입니다.", null);
            }
            Room room = findRoomById(roomId);
            List<UserRoomInfoResponse> userRoomInfos = customUserRepository.currentRoomInfo(roomId);
            if (room != null) {
                int roomFullCount = 2;
                if (RoomType.DOUBLE.toString().equals(room.getRoomType())) {
                    roomFullCount *= 2;
                }

                if (!RoomStatus.WAIT.equals(room.getStatus())) {
                    return new ApiResponse<>(201, "불가능한 요청입니다.", null);
                }
                if (userRoomInfos.size() != roomFullCount) {
                    return new ApiResponse<>(201, "불가능한 요청입니다.", null);
                }
                // DOUBLE 이면 4명일 때, SINGLE이면 1명일 때 START 해야함
                int countRedTeam = 0;
                int countBlueTeam = 0;
                for (UserRoomInfoResponse userRoomInfoResponse: userRoomInfos) {
                    String teamName =userRoomInfoResponse.getTeam().toString();
                    if("RED".equals(teamName)){
                        countRedTeam += 1;
                    }else if("BLUE".equals(teamName)){
                        countBlueTeam += 1;
                    }
                }
                if (roomFullCount - ( countRedTeam + countBlueTeam ) == 0){
                    // status 변경
                    room.setStatus(RoomStatus.PROGRESS);
                    roomRepository.save(room);
                    // 1분 후 게임 종료 로직을 스케쥴링
//                    endGameFuture = scheduler.schedule(() -> endGame(roomId), 1, TimeUnit.MINUTES);
                    endGameFuture = scheduler.schedule(() -> endGame(roomId), 10, TimeUnit.SECONDS);
                }
            }
        }catch(Exception e){
            apiResponse = new ApiResponse<>(500,"에러가 발생했습니다.\"", null );
            throw e;
        }
        return apiResponse;
    }

    private void endGame(int roomId) {
        try {
            // 게임 종료 로직
            // 예: 방 상태를 'FINISH(완료)'로 변경
            Room room = findRoomById(roomId);
            if (room != null) {
                cancelEndGame();
                room.setStatus(RoomStatus.FINISH);
                updateRoom(room);
            }
        }catch(Exception e){
            throw e;
        }
    }


    private Room findRoomById(int roomId) {
        // 방 ID로 방 찾기 로직 구현
        // 예제 코드이므로 구체적인 구현은 생략
        return roomRepository.findById(roomId);
    }
    private void updateRoom(Room room) {
        roomRepository.save(room);
    }

    public void cancelEndGame() {
        if (endGameFuture != null && !endGameFuture.isDone()) {
            endGameFuture.cancel(false);
        }
    }
}
