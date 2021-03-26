package com.huihe.eg.user.service.web.aop;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.util.result.ResultParam;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.huihe.eg.comm.aop.WebLog;
import com.huihe.eg.user.model.LogOperationHistoryEntity;
import com.huihe.eg.user.mybatis.dao.LogOperationHistoryMapper;
import com.huihe.eg.user.service.impl.pay.PayServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.*;
import java.lang.reflect.Method;

import static com.huihe.eg.comm.util.GetIp.getIpAddr2;


@Aspect
@Component
public class LogAspect {

//    private static ExecutorService pool = Executors.newFixedThreadPool(5);

    @Resource
    private LogOperationHistoryMapper logOperationHistoryMapper;

    /**
     * 添加业务逻辑方法切入点
     */
    @Around("execution(* com.huihe.eg.user.service.impl..*(..))" +
            "&& @annotation(com.huihe.eg.user.service.web.aop.WebLog)")
    public Object insertLog(ProceedingJoinPoint pjp) throws Throwable {

        // 先通过注解判断
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        // 获取被拦截的方法
        Method method = signature.getMethod();
        WebLog opertionBLog = method.getAnnotation(WebLog.class);
        Object result = pjp.proceed();

        if (opertionBLog == null) {
            return result;
        }

        try {
            LogOperationHistoryEntity log = new LogOperationHistoryEntity();
            log.setOperation(opertionBLog.description());
            crateBLog(log, pjp);
            if (result==null || ((ResultParam)result).getCode()!=0){
                log.setResult("失败");
            }else {
                log.setResult("成功");
            }

            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                    .setNameFormat("LogAspect-pool-%d").build();

            ExecutorService pool = new ThreadPoolExecutor(5, 200,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());


            pool.execute(new WBLog(log));
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private void crateBLog(LogOperationHistoryEntity log, ProceedingJoinPoint pjp) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();

        log.setUrl(request.getRequestURL().toString());
        log.setIp(getIpAddr2(request));
        log.setMethod(request.getMethod());
        log.setContent(request.getContentType());

        log.setParam(getRequestParams(request, pjp.getArgs()).toString());
    }

    private Object getRequestParams(HttpServletRequest request, Object[] args) {
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                continue;
            }
            arguments[i] = args[i];
        }
        args = arguments;

        StringBuilder sb = new StringBuilder();
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            if (request.getContentType() != null) {
                if (request.getContentType().contains("json")) {
                    for (Object object : args) {
                        sb.append(JSONObject.toJSONString(object));
                    }
                } else {
                    for (Object object : args) {
                        sb.append(object.toString());
                    }
                }
            }
        } else {
            sb.append(request.getQueryString());
        }
        return sb.toString();
    }

    class WBLog implements Runnable {
        private final LogOperationHistoryEntity log;

        public WBLog(LogOperationHistoryEntity log) {
            this.log = log;
        }

        @Override
        public void run() {
            if (log != null) {
                try {
                    logOperationHistoryMapper.insertSelective(log);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
