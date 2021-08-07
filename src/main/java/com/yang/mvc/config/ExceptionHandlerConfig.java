package com.yang.mvc.config;

import com.yang.mvc.common.Result;
import com.yang.mvc.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerConfig {
    /**
     * 报错处理，返回统一的值
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(Exception e) {
        log.error("err:", e);
        return Result.error(ResultCode.FAILED);
    }
}
