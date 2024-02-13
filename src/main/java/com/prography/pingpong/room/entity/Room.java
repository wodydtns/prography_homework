package com.prography.pingpong.room.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.prography.pingpong.room.dto.CreateRoomRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String title;

    @Column
    private Integer host;

    @Column
    private String roomType;

    @Column
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt ;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserRoom> userRoom;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static Room from(CreateRoomRequest createRoomRequest){
        return new Room(createRoomRequest.getUserId(), createRoomRequest.getRoomType(), createRoomRequest.getTitle());
    }

    private Room(int userId, String roomType, String title){
        this.host = userId;
        this.roomType = roomType;
        this.title = title;
        this.status = RoomStatus.WAIT;
    }

    // 상태를 설정하는 메소드 추가
    public void setStatus(RoomStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now(); // 상태 변경 시 업데이트 시간도 갱신
    }
}
