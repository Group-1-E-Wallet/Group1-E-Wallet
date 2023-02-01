package com.semicolon.ewallet.Exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
@Builder
@Data
@AllArgsConstructor
public class ApiResponse{
    private ZonedDateTime timeStamp;
    private boolean isSuccessful;
    private Object data;
    private int status;
    private String path;
}
