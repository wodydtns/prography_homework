package com.prography.pingpong.user.repository;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.user.dto.UserDto;
import com.prography.pingpong.user.dto.UserRoomInfoResponse;
import com.prography.pingpong.user.dto.UserRoomStatusResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import static com.prography.pingpong.user.entity.QUser.*;
import static com.prography.pingpong.room.entity.QUserRoom.*;
import static com.prography.pingpong.room.entity.QRoom.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public ApiResponse<Map<String, Object>> getAllUserList(int size, int page) {
        int adjustedPage = page - 1;

        if (adjustedPage < 0) {
            adjustedPage = 0;
        }
        Pageable pageable = PageRequest.of(adjustedPage, size);

        // 결과 리스트 조회
        List<UserDto> users = jpaQueryFactory.select(Projections.fields(UserDto.class,
                        user.id,
                        user.fakerId,
                        user.name,
                        user.email,
                        user.status,
                        user.createdAt.as("createdAt"),
                        user.updatedAt.as("updatedAt")
                )).from(user).orderBy(user.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 조회
        long total = jpaQueryFactory.select(user.count()).from(user).fetchOne();

        // API 응답 구성
        Map<String, Object> result = new HashMap<>();
        result.put("totalElements", total);
        result.put("totalPages", (int) Math.ceil((double) total / size)); // 총 페이지 수 계산
        result.put("userList", users);

        return new ApiResponse<>(200, "API 요청이 성공했습니다.", result);
    }

    @Override
    public List<UserRoomStatusResponse> isInRoomByUserid(int userId) {
        return jpaQueryFactory.select(Projections.fields(UserRoomStatusResponse.class,
                    user.status,
                    userRoom.id.as("roomId")
                ))
                .from(userRoom)
                .join(user)
                .where(user.id.eq(userId))
                .fetch();
    }

    @Override
    public Long countJoinUser(int roomId) {
        return jpaQueryFactory.select(userRoom.count()).from(user).join(userRoom).where(userRoom.id.eq(roomId)).fetchOne();
    }

    @Override
    public List<UserRoomInfoResponse> currentRoomInfo(int roomId) {
        return jpaQueryFactory.select(Projections.fields(UserRoomInfoResponse.class
                        ,user.id
                        ,room.id.as("roomId")
                        ,room.roomType
                        ,room.host
                        ,userRoom.team
                ))
                .from(userRoom).join(userRoom.user, user).join(userRoom.room, room)
                .where(userRoom.room.id.eq(roomId))
                .fetch();
    }
}
