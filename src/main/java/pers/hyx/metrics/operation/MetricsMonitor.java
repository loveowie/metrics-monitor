package pers.hyx.metrics.operation;

import pers.hyx.metrics.metrics.MonitorType;

import java.lang.annotation.*;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-23 15:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface MetricsMonitor {

    MonitorType type() default MonitorType.SERVER;
    String remark() default "";
    Class<? extends Throwable>[] monitorFor() default {};
    Class<? extends Throwable>[] noMonitorFor() default {};
}
