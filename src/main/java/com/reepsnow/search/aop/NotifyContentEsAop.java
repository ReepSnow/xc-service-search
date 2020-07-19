package com.reepsnow.search.aop;

import java.io.IOException;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.reepsnow.search.service.ContentEsService;
import com.reepsnow.search.service.info.CreateOrUpdateContentInfo;

/**
 * aop 方案
 *
 * @author 王鹏涛
 * @since 2019年1月23日
 */
@Aspect
@Configuration
public class NotifyContentEsAop {

    @Autowired
    public NotifyContentEsAop(ContentEsService contentEsService) {
        this.contentEsService = contentEsService;
    }

    private final ContentEsService contentEsService;

    /**
     * 切点声明
     * notifyContentEs是com.reepsnow.search.aop.NotifyContentEs的别名
     * 顺便再带上notifyContentEs注解的属性以及值
     * @param notifyContentEs
     */
    @Pointcut(value = "@annotation(notifyContentEs)",argNames = "notifyContentEs")
    public void methodPoint(NotifyContentEs notifyContentEs) {
    }
    /**
     * AspectJ使用org.aspectj.lang.JoinPoint接口表示目标类连接点对象，如果是环绕增强时，使用 org.aspectj.lang.ProceedingJoinPoint表示连接点对象，
     * 该类是JoinPoint的子接口。任何一个增强方法都可以通过将第一个入参声明为JoinPoint访问到连接点上下文的信息。
     * */
    @AfterReturning("methodPoint(notifyContentEs)")
    public void interceptor(JoinPoint pjp,NotifyContentEs notifyContentEs) throws IOException {
      MethodSignature signature = (MethodSignature) pjp.getSignature();
      Method method = signature.getMethod();
      NotifyContentEs methodAnnotation = method.getAnnotation(NotifyContentEs.class);
      if (0 == methodAnnotation.sleep()) {
      //根据业务判断是否需要休眠
      }
      Object[] objects = pjp.getArgs();
      for(Object obj : objects ){
        if(obj instanceof CreateOrUpdateContentInfo){
          contentEsService.esAddOrUpdate((CreateOrUpdateContentInfo) obj);
        }
      }

    }
}