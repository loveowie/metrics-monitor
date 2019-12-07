package pers.hyx.metrics.metrics;

import pers.hyx.metrics.config.MetricsProperties;
import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusPushGatewayManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-24 10:20
 */
@Component(value = "MetricsManager")
public class MetricsManager {

    private final static Logger LOG = LoggerFactory.getLogger(MetricsManager.class);

    @Resource
    private MetricsProperties properties;
    private CollectorRegistry registry;

    @PostConstruct
    public void init() {
        LOG.info("init metrics manager start. pushGateway:{}", properties.getPushGateway());
        PushGateway pushGateway = new PushGateway(properties.getPushGateway());
        registry = new CollectorRegistry();
        new PrometheusPushGatewayManager(pushGateway, registry,
                Duration.ofMillis(5000), "exception_alert", null, PrometheusPushGatewayManager.ShutdownOperation.PUSH);
        LOG.info("init metrics manager end.");
    }

    public void registry(Collector collector) {
        registry.register(collector);
    }
}
