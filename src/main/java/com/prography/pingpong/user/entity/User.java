package com.prography.pingpong.user.entity;

import com.prography.pingpong.common.dto.CreateUserDto;
import com.prography.pingpong.room.entity.UserRoom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private int fakerId;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany
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

    public static User from(CreateUserDto createUserDto) {
        return new User(createUserDto.getId(), createUserDto.getUsername(), createUserDto.getEmail());
    }

    private User(int id, String username, String email){
        this.fakerId = id;
        this.name = username;
        this.email = email;
        if(id <= 30){
            this.status = UserStatus.ACTIVE;
        }else if(id >=61 ){
            this.status = UserStatus.NON_ACTIVE;
        }else{
            this.status = UserStatus.WAIT;
        }
    }
}
