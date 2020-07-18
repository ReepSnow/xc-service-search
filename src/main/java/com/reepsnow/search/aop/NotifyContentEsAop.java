package com.reepsnow.search.aop;

import java.io.IOException;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.reepsnow.search.ContentService;

/**
 * redis 方案
 *
 * @author 王鹏涛
 * @since 2019年1月23日
 */
@Aspect
@Configuration
public class NotifyContentEsAop {

    @Autowired
    public NotifyContentEsAop(ContentService contentService) {
        this.contentService = contentService;
    }

    private final ContentService contentService;

    /**
     * 切点声明
     * cacheLock是com.snow.cacheredislock.annotation.CacheLock的别名
     * 顺便再带上cacheLock注解的属性以及值
     * @param NotifyContentEs
     */
    @Pointcut(value = "@annotation(NotifyContentEs)",argNames = "NotifyContentEs")
    public void methodPoint(NotifyContentEs NotifyContentEs) {
    }
    /**
     * AspectJ使用org.aspectj.lang.JoinPoint接口表示目标类连接点对象，如果是环绕增强时，使用 org.aspectj.lang.ProceedingJoinPoint表示连接点对象，
     * 该类是JoinPoint的子接口。任何一个增强方法都可以通过将第一个入参声明为JoinPoint访问到连接点上下文的信息。
     * */
    @AfterReturning("methodPoint(NotifyContentEs)")
    public void interceptor(JoinPoint pjp,NotifyContentEs NotifyContentEs) {
      MethodSignature signature = (MethodSignature) pjp.getSignature();
      Method method = signature.getMethod();
      NotifyContentEs lock = method.getAnnotation(NotifyContentEs.class);
      if (0 == lock.sleep()) {
        throw new RuntimeException("lock key don't null...");
      }
      try {
        contentService.esAdd("dd");
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
}