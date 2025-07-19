package org.example.syntheticcorestarter.log;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class WatchingAspect {

    private final LogKafkaProducer logKafkaProducer;

    @Before("@annotation(org.example.syntheticcorestarter.log.WeylandWatchingYou)")
    public void beforeMethod(JoinPoint joinPoint) {
        var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var annotation = method.getAnnotation(WeylandWatchingYou.class);
        var message = ">>> Before method: " + joinPoint.getSignature();

        if (annotation.value() == WatchingLogType.KAFKA) {
            logKafkaProducer.sendLog(message);
        } else {
            log.info(message);
        }
    }
}
