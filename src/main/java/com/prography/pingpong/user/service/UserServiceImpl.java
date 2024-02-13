package com.prography.pingpong.user.service;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.user.dto.UserDto;
import com.prography.pingpong.user.entity.User;
import com.prography.pingpong.user.repository.CustomUserRepository;
import com.prography.pingpong.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final CustomUserRepository customUserRepository;
    @Override
    public ApiResponse<Map<String, Object>> getAllUsers(int size, int page) {
        return customUserRepository.getAllUserList(size,page);
    }
}
