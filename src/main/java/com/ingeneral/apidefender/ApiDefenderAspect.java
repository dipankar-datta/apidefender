package com.ingeneral.apidefender;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

import static java.util.Optional.empty;

@Aspect
@Component
public class ApiDefenderAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DefenderService defenderService;

    @Around("@annotation(ApiDefender)")
    public Object defend(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ApiDefender apiDefender = method.getAnnotation(ApiDefender.class);

        Optional<Long> unlockTimeoutOptional =
                apiDefender.unlockTimeout() == 0 ? empty() :
                        Optional.of(Long.valueOf(apiDefender.unlockTimeout()));

        var lockKey = this.defenderService.lock(
                this.request.getSession().getId(),
                this.request.getRemoteAddr(),
                apiDefender.resourceName(),
                unlockTimeoutOptional);

        Object result = joinPoint.proceed();

        this.defenderService.releaseLock(lockKey);

        return result;
    }
}
