package com.sias.system.aspect;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sias.commons.annotation.Log;
import com.sias.commons.model.SysOperationLog;
import com.sias.commons.model.SysUser;
import com.sias.commons.utils.AddressUtils;
import com.sias.commons.utils.IpUtils;
import com.sias.commons.utils.ServletUtils;
import com.sias.commons.utils.StringUtils;
import com.sias.system.service.SysOperationLogService;
import com.sias.system.util.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-18 18:53:07
 */
@Aspect
@Component
public class LogAspect {

  @Resource
  SysOperationLogService sysOperationLogService;

  private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

  @Pointcut("@annotation(com.sias.commons.annotation.Log)")
  public void logPointCut() {
    // TODO document why this method is empty
  }

  @Around("logPointCut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    saveLog(joinPoint);
    return joinPoint.proceed();
  }

  void saveLog(ProceedingJoinPoint joinPoint) throws IOException {
    SysUser currentUser = SecurityUtils.getCurrentUser();
    SysOperationLog operLog = new SysOperationLog();
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    Log log = method.getAnnotation(Log.class);
    operLog.setOperation(log.value());
    operLog.setTitle(log.title());
    operLog.setCreateTime(new Date());
    String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
    operLog.setIp(ip);
    operLog.setUrl(StringUtils.substring(ServletUtils.getRequest().getRequestURI(), 0, 255));
    operLog.setUsername(currentUser.getUsername());
    String className = joinPoint.getTarget().getClass().getName();
    String methodName = joinPoint.getSignature().getName();
    operLog.setMethod(className + "." + methodName + "()");
    String address = AddressUtils.getRealAddressByIP(ip);
    if ("内网IP".equals(address)){
      operLog.setLocation(address);
    }else {
      JSONObject jsonObject = JSON.parseObject(address);
      operLog.setLocation((String) jsonObject.get("addr"));
    }
    sysOperationLogService.insertByFilter(operLog);
  }
}

