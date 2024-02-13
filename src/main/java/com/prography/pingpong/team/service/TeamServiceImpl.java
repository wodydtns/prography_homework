package com.prography.pingpong.team.service;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.room.entity.Room;
import com.prography.pingpong.room.entity.RoomStatus;
import com.prography.pingpong.room.entity.RoomType;
import com.prography.pingpong.room.entity.UserRoom;
import com.prography.pingpong.room.repository.RoomRepository;
import com.prography.pingpong.room.repository.UserRoomRepository;
import com.prography.pingpong.team.entity.Team;
import com.prography.pingpong.user.dto.UserRoomInfoResponse;
import com.prography.pingpong.user.repository.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements  TeamService{

    private final CustomUserRepository customUserRepository;

    private final RoomRepository roomRepository;

    private final UserRoomRepository userRoomRepository;
    @Override
    public ApiResponse<Object> changeTeamByUser(int roomId, int userId) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(200, "API 요청이 성공했습니다.", null);
        try {
            List<UserRoomInfoResponse> userRoomInfos = customUserRepository.currentRoomInfo(roomId);
            UserRoom userRoom = userRoomRepository.findByUserId(userId);
            Room room = roomRepository.findById(roomId);
            // 방 상태 check
            if (room != null && !RoomStatus.WAIT.equals(room.getStatus())) {
                return new ApiResponse<>(201, "불가능한 요청입니다.", null);
            }
            int roomFullCount = 2;
            if(RoomType.DOUBLE.toString().equals(room.getRoomType())){
                roomFullCount *= 2;
            }                                                                                                                                        ;
            int countRedTeam = 0;
            int countBlueTeam = 0;
            for (UserRoomInfoResponse userRoomInfo : userRoomInfos) {
                if (Team.RED.equals(userRoomInfo.getTeam())) {
                    countRedTeam += 1;
                } else {
                    countBlueTeam += 1;
                }
            }
            if( countRedTeam + countBlueTeam == roomFullCount) {
                return new ApiResponse<>(201, "불가능한 요청입니다.", null);
            }
            if (userId == userRoom.getUser().getId()) {
                if (RoomType.SINGLE.toString().equals(room.getRoomType()) && roomFullCount - (countBlueTeam + countRedTeam) > 0 ) {
                    userRoom.toggleTeam(userRoom.getTeam());
                }else{
                    // userRoom.getTeam() 사용자의 TEAM NAME
                    if(roomFullCount / countRedTeam == 2  && Team.BLUE.equals(userRoom.getTeam())){
                        return new ApiResponse<>(201, "불가능한 요청입니다.", null);
                    }else if(roomFullCount / countBlueTeam == 2 && Team.RED.equals(userRoom.getTeam())){
                        return new ApiResponse<>(201, "불가능한 요청입니다.", null);
                    }else{
                        userRoom.toggleTeam(userRoom.getTeam());
                    }
                }
            }
            userRoomRepository.save(userRoom);
        }catch(Exception e){
            apiResponse = new ApiResponse<>(500,"에러가 발생했습니다.\"", null );
            throw e;
        }

        return apiResponse;
    }
}
