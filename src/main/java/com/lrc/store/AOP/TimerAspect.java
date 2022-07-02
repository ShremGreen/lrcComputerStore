package com.lrc.store.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//业务层方法执行时间统计方法
@Component //将当前类的对象创建使用维护交给Spring容器
@Aspect    //将当前类标记为切面类
public class TimerAspect {
    @Around("execution(* com.lrc.store.service.impl.*.*(..))")//连接点表达式，连接控制层方法
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //开始时间
        long t1 = System.currentTimeMillis();
        //调用目标方法
        Object result = pjp.proceed();
        //结束时间
        long t2 = System.currentTimeMillis();
        System.out.println("耗时：" + (t2 - t1) + "ms");
        return result;
    }
}
