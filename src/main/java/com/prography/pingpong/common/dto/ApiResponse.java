package com.prography.pingpong.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private List<T> result;
}
