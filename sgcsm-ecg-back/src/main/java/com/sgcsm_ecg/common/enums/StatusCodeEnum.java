package com.sgcsm_ecg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    OK(200, "Successful operation"),
    CREATED(201, "Created"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Please, log in to continue"),
    NOT_FOUND(404, "Not Found"),
    UNSUPPORTED_MEDIA_TYPE(415, "Sent media type is not compatible"),
    SYSTEM_ERROR(500, "Internal Service Error");

    private int code;
    private String msg;
}
