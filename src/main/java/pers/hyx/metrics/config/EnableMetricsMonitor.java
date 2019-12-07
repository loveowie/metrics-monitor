package pers.hyx.metrics.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.lang.annotation.*;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-24 11:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@ComponentScans(@ComponentScan("pers.hyx.metrics"))
public @interface EnableMetricsMonitor {
}
