package pers.hyx.metrics.operation;

import pers.hyx.metrics.metrics.MonitorType;
import pers.hyx.metrics.metrics.MonitorTag;
import pers.hyx.metrics.metrics.Metrics;
import pers.hyx.metrics.metrics.TagProduction;
import pers.hyx.metrics.util.SpringUtil;
import io.prometheus.client.Counter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-23 15:48
 */
@Aspect
@Component
public class MonitorInterceptor {

    @Pointcut("@annotation(pers.hyx.metrics.operation.MetricsMonitor)")
    private void monitorMethod() {

    }

    @Around("monitorMethod()")
    public Object trackInfo(ProceedingJoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        MetricsMonitor annotation = method.getAnnotation(MetricsMonitor.class);

        Class<? extends Throwable>[] monitorFor = annotation.monitorFor();
        Class<? extends Throwable>[] noMonitorFor = annotation.noMonitorFor();
        MonitorType type = annotation.type();
        Object proceed = null;
        try {
            proceed = jp.proceed();
        } catch (Throwable throwable) {

            for (Class<? extends Throwable> aClass : noMonitorFor) {
                if (throwable.getClass().isAssignableFrom(aClass)) {
                    return null;
                }
            }
            for (Class<? extends Throwable> aClass : monitorFor) {
                if (aClass.isAssignableFrom(throwable.getClass())) {
                    // add monitor
                    TagProduction production = SpringUtil.getBean(TagProduction.class);
                    Object[] args = jp.getArgs();
                    MonitorTag monitorTag = production.structureTag(method.getName(), Arrays.toString(args), annotation.remark());
                    Counter counter = Metrics.getIfPresent(type.getGraph());
                    assert counter != null;
                    counter.labels(monitorTag.toLabels()).inc(System.currentTimeMillis()/1000);
                    return null;
                }
            }
        }
        return proceed;
    }
}
