package pers.hyx.metrics.initialization;

import pers.hyx.metrics.metrics.Metrics;
import pers.hyx.metrics.metrics.MetricsManager;
import pers.hyx.metrics.util.SpringUtil;
import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-23 16:32
 */
@Component
@DependsOn(value = "MetricsManager")
public class MetricsInitialization implements ApplicationContextAware {

    private final static Logger LOG = LoggerFactory.getLogger(MetricsInitialization.class);

    private final MetricsManager metricsManager;

    public MetricsInitialization(MetricsManager metricsManager) {
        this.metricsManager = metricsManager;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.init(applicationContext);
    }

    @PostConstruct
    public void run() {
        ConcurrentMap<String, Counter> stringCounterConcurrentMap = Metrics.asMap();
        Set<Map.Entry<String, Counter>> entries = stringCounterConcurrentMap.entrySet();
        for (Map.Entry<String, Counter> entry : entries) {
            metricsManager.registry(entry.getValue());
        }
        LOG.info("load metrics registry. find metrics num :{}", stringCounterConcurrentMap.size());
    }
}
