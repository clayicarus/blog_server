package io.blog.handler.exception;

import io.blog.ResponseResult;
import io.blog.enums.AppHttpCodeEnum;
import io.blog.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BlogException.class)
    public ResponseResult systemExceptionHandler(BlogException e) {
        log.error("Exception occur: {}", e.getMsg());
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }
    @ExceptionHandler(BlogException.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("Exception occur: {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
