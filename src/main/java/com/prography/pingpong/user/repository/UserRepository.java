package com.prography.pingpong.user.repository;

import com.prography.pingpong.room.entity.Room;
import com.prography.pingpong.user.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int userId);
}
