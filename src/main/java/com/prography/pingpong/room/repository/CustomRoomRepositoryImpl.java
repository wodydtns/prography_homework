package com.prography.pingpong.room.repository;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.room.dto.RoomDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.prography.pingpong.room.entity.QRoom.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomRoomRepositoryImpl implements CustomRoomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ApiResponse<Map<String, Object>> getAllRooms(int size, int page) {
        int adjustedPage = page - 1;

        if (adjustedPage < 0) {
            adjustedPage = 0;
        }
        Pageable pageable = PageRequest.of(adjustedPage, size);

        List<RoomDto> rooms = jpaQueryFactory.select(Projections.fields(RoomDto.class,
                room.id,
                room.title,
                room.host.as("hostId"),
                room.roomType,
                room.status
                )).from(room).orderBy(room.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = jpaQueryFactory.select(room.count()).from(room).fetchOne();

        // API 응답 구성
        Map<String, Object> result = new HashMap<>();
        result.put("totalElements", total);
        result.put("totalPages", (int) Math.ceil((double) total / size)); // 총 페이지 수 계산
        result.put("roomList", rooms);
        return new ApiResponse<>(200, "API 요청이 성공했습니다.", result);
    }
}
