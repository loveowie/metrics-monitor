package pers.hyx.metrics.util;

import org.springframework.context.ApplicationContext;

/**
 * @author yxhe4
 * @since 2019-02-21 9:29
 */
public class SpringUtil {
    private static ApplicationContext applicationContext;

    public static void init(ApplicationContext context) {
        checkNotNull(context);
        applicationContext = context;
    }

    private static <T> void checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
