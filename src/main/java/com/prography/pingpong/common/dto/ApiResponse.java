package com.prography.pingpong.common.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T result;
}
