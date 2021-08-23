package com.js.ms.todo.global.config.exception;


import com.js.ms.todo.global.config.Response.Response;
import com.js.ms.todo.global.config.exception.business.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 비즈니스 요규사항 예외
     */
    @ExceptionHandler(BusinessException.class)
    protected  ResponseEntity<Response> handleBusinessException(BusinessException e) {
        log.error("BusinessException", e);
        final Response response = Response.of(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 비즈니스 요규사항이 아닌 예외
     * 스프링 및 라이브러리 등 자체적으로 발생하는 예외
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response> handleException(Exception e) {
        log.error("Exception!", e);

        eventPublisher.publishEvent(e);

        final Response response = Response.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
