package com.prography.pingpong.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    WAIT,
    ACTIVE,
    NON_ACTIVE
}
