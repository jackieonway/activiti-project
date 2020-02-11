package com.github.jackieonway.activiti.handler;

import com.github.jackieonway.activiti.utils.ResponseUtils;
import com.github.jackieonway.activiti.utils.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Objects;
import java.util.Set;

/**
 * 全局异常处理
 *
 * @author dengyi
 */
@Slf4j
@RestControllerAdvice
@Primary
public class GlobalExceptionHandler {

    private static final String PARAM_ERROR_MSG = "参数出错：{}";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResultMsg badArgumentHandler(IllegalArgumentException e) {
        log.error(PARAM_ERROR_MSG, e.getMessage(), e);
        return ResponseUtils.fail(e.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResultMsg httpMethodArgumentValidExceptionHander(MethodArgumentNotValidException exception) {
        FieldError error = exception.getBindingResult().getFieldErrors().get(0);
        log.error("参数校验异常: {}", exception.getMessage(), exception);
        if (error != null) {
            if ("NotBlank,NotNull,NotEmpty".contains(Objects.requireNonNull(error.getCode()))) {
                return ResponseUtils.fail(error.getDefaultMessage() + " " +
                        error.getField() + "(" + error.getRejectedValue() + ")");
            } else {
                return ResponseUtils.fail(error.getDefaultMessage());
            }
        }
        return ResponseUtils.fail();
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResultMsg httpConstraintViolationExceptionHander(ConstraintViolationException exception) {
        log.error("参数校验异常: {}", exception.getMessage(), exception);
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            for (ConstraintViolation<?> next : constraintViolations) {
                return ResponseUtils.fail(next.getMessage());
            }
        }
        return ResponseUtils.fail();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResultMsg badArgumentHandler(MethodArgumentTypeMismatchException e) {
        log.error(PARAM_ERROR_MSG, e.getMessage(), e);
        return ResponseUtils.fail(e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultMsg badArgumentHandler(MissingServletRequestParameterException e) {
        log.error("请求出错：{}", e.getMessage(), e);
        return ResponseUtils.fail(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultMsg badArgumentHandler(HttpMessageNotReadableException e) {
        log.error(PARAM_ERROR_MSG, e.getMessage(), e);
        return ResponseUtils.fail(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResultMsg badArgumentHandler(ValidationException e) {
        log.error("校验错误{}", e.getMessage(), e);
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                String message = ((PathImpl) item.getPropertyPath()).getLeafNode().getName() + item.getMessage();
                log.error(PARAM_ERROR_MSG, message);
                if (StringUtils.isNotBlank(message)) {
                    return ResponseUtils.fail(e.getMessage());
                }
            }
        }
        return ResponseUtils.fail(e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResultMsg seriousHandler(BusinessException e) {
        log.error("业务操作异常：{}", e.getMessage(), e);
        return ResponseUtils.fail(e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public ResultMsg seriousHandler(ServiceException e) {
        log.error("服务操作异常：{}", e.getMessage(), e);
        return ResponseUtils.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultMsg seriousHandler(Exception e) {
        log.error("未知异常：{}", e.getMessage(), e);
        return ResponseUtils.fail();
    }
}
