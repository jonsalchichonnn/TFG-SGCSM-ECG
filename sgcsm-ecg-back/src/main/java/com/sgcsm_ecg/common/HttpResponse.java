package com.sgcsm_ecg.common;

import lombok.Data;

import static com.sgcsm_ecg.common.enums.StatusCodeEnum.OK;
import static com.sgcsm_ecg.common.enums.StatusCodeEnum.SYSTEM_ERROR;
import static com.sgcsm_ecg.common.enums.StatusCodeEnum.CREATED;

@Data
public class HttpResponse<T> {
    private Integer code;
    private String msg;
    private T data;
    private Long total;

    public static <T> HttpResponse<T> created() {
        return buildResponse(CREATED.getCode(), CREATED.getMsg(), null, null);
    }


    public static <T> HttpResponse<T> success() {
        return buildResponse(OK.getCode(), OK.getMsg(), null, null);
    }

    public static <T> HttpResponse<T> success(T data) {
        return buildResponse(OK.getCode(), OK.getMsg(), data, 1L);
    }

    public static <T> HttpResponse<T> success(T data, Long total) {
        return buildResponse(OK.getCode(), OK.getMsg(), data, total);
    }


    public static <T> HttpResponse<T> fail(String message) {
        return buildResponse(SYSTEM_ERROR.getCode(), message, null, null);
    }

    public static <T> HttpResponse<T> fail(Integer code, String message) {
        return buildResponse(code, message, null, null);
    }

    private static <T> HttpResponse<T> buildResponse(Integer code, String message, T data, Long total) {
        HttpResponse<T> res = new HttpResponse<>();
        res.setCode(code);
        res.setMsg(message);
        res.setData(data);
        res.setTotal(total);
        return res;
    }
}
