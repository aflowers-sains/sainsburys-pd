package uk.co.sainsburys.clients.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerLoggingAspect {

    @Pointcut("execution(@(@org.springframework.web.bind.annotation.RequestMapping *) * *(..))")
    public void restControllerLoggingPointcut() {
    }

    @Around("restControllerLoggingPointcut()")
    public Object logMethodCall(ProceedingJoinPoint pjp) throws Throwable {
        Logger logger = LoggerFactory.getLogger(pjp.getSignature().getDeclaringType());

        if(logger.isInfoEnabled()) {
            logger.info("Entering {} {}", pjp.toLongString(), pjp.getArgs());
        }

        Object result = pjp.proceed();

        if(logger.isInfoEnabled()) {
            logger.info("Exiting {}", pjp.toLongString());
        }

        return result;
    }
}