package com.prography.pingpong.room.repository;

import com.prography.pingpong.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    Room findById(int roomId);

    int countByHost(int userId);

    int countInRoomById(int roomId);

    int deleteByHost(int userId);
}
