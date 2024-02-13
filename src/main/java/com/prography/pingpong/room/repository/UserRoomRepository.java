package com.prography.pingpong.room.repository;

import com.prography.pingpong.room.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {

    UserRoom findByUserId(int userId);

    Optional<UserRoom> countInRoomByRoomId(int roomId);
}
