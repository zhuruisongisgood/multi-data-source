package com.zk798.user.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理<br/>
 * Date: 2016/7/14<br/>
 *
 * @author ZRS
 * @since JDK7
 */
@ControllerAdvice
@Component
@Slf4j
public class BizExceptionHandler {

    /**
     * 请求参数验证失败
     * @param e 异常堆栈信息
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value={
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String hanleValidateParameterException(Exception e) throws Exception {
    	log.error("请求参数错误:" + e.getMessage());
    	return "请求参数错误";
    }


    /**
     * 处理系统异常
     * @param e
     * @return
     * @throws Throwable
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String unknowException(Exception e) throws Throwable {
    	log.error("未知异常:",e);
    	return e.toString();
    }
    /**
     * 处理系统异常
     *
     * @param e
     * @return
     * @throws Throwable
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String throwableException(HttpServletResponse response, Throwable e) throws Throwable {
    	log.error("未知异常:",e);
    	return e.toString();
    }


    
}
