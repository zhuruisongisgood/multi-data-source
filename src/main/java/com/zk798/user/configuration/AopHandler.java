package com.zk798.user.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <br/>
 * Date: 2016/7/14<br/>
 *
 * @author ZRS
 * @since JDK7
 */
@Aspect
@Component
@Slf4j
public class AopHandler {


    /**
     * 打印Controller层日志
     * @param pjp 切点
     * @param requestMapping 注解类型
     * @return
     * @throws Throwable
     */
    @Around("@annotation(requestMapping)")
    public Object printMethodsExecutionTime(ProceedingJoinPoint pjp, RequestMapping requestMapping) throws Throwable {
        long start = System.currentTimeMillis();

        String clazz = pjp.getSignature().getDeclaringTypeName();
        String method = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        StringBuilder builder = new StringBuilder();
        for(Object o : args){
            if(o instanceof String){
                builder.append("参数String=");
            }
            else{
                builder.append("参数Object=");
            }
            builder.append(o);
            builder.append(",");
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String path = request.getRequestURL().toString();
        log.info(">>>>>> 开始请求:" + path + "|执行方法:" + clazz + "." + method + "|" + builder);

        Object result = pjp.proceed();

        String json = "";
        if(null!=result){
            json = JSONObject.valueToString(result);
        }
        long usedTime = System.currentTimeMillis() - start;
        log.info("<<<<<< 结束请求:" + path + "|执行方法:" + clazz + "." + method + "|响应:" + json + "|耗时:" + usedTime + "毫秒");

        return result;
    }

    /**
     * 打印Dao层日志
     * @param pjp 切点
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.zk798.user.dao..*.*(..))")
    public Object printMethodsExecutionTime0(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        String clazz = pjp.getSignature().getDeclaringTypeName();
        String method = pjp.getSignature().getName();
        Object result = pjp.proceed();
        long usedTime = System.currentTimeMillis() - start;
        log.info("DAO:" + clazz + "." + method + "|耗时:" + usedTime + "毫秒");
        return result;
    }




}
