package com.good.word.redis.exception;

import com.good.word.redis.common.entity.R;
import com.good.word.redis.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public R bizExceptionHandler(HttpServletRequest request, BizException e) {
        log.error("业务异常......code={} message={}",e.getErrCode(), e.getMessage());
        return R.error().code(e.getErrCode()).message(e.getMessage());
    }

    /**
     * JSR-303 validation
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R validationBodyException(MethodArgumentNotValidException e) {
        List<String> errorList = new ArrayList<>();
        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                log.error("Data check failure : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+
                        "},errorMessage{"+fieldError.getDefaultMessage()+"}");
                errorList.add(fieldError.getDefaultMessage());
            });
        }
        return R.error().code(HttpStatus.BAD_REQUEST.value())
                .message(StringUtils.collectionToDelimitedString(errorList, ","));
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public R exceptionHandler(HttpServletRequest req, NullPointerException e){
        log.error("发生空指针异常！原因是：",e);
        return R.error("服务器内部错误");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R exceptionHandler(HttpServletRequest req, ConstraintViolationException e) {
        log.error("数据校验异常！原因是：", e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<?> violation : constraintViolations) {
            message.append(violation.getMessage());
        }
        return R.error().code(HttpStatus.BAD_REQUEST.value()).message(message.toString());
    }


    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(HttpServletRequest req, Exception e){
        log.error("发生异常！原因是：",e);
        return R.error("服务器内部错误");
    }
}
