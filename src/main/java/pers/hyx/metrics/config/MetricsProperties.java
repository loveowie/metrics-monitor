package pers.hyx.metrics.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-23 14:30
 */
@ConfigurationProperties(prefix = "metrics.config")
public class MetricsProperties {

    private final static Logger LOG = LoggerFactory.getLogger(MetricsProperties.class);

    private String pushGateway;
    private String server;
    private Long pushRate;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        if (StringUtils.isEmpty(server)) {
            LOG.error("load metrics properties error, find server empty.");
            throw new IllegalArgumentException();
        }
        this.server = server;
    }

    public String getPushGateway() {
        return pushGateway;
    }

    public void setPushGateway(String pushGateway) {
        if (StringUtils.isEmpty(pushGateway)) {
            LOG.error("load metrics properties error, find pushGateway empty.");
            throw new IllegalArgumentException();
        }
        this.pushGateway = pushGateway;
    }

    public long getPushRate() {
        return pushRate;
    }

    public void setPushRate(Long pushRate) {
        if (pushRate == null) {
            pushRate = 60 * 1000L;
        }
        this.pushRate = pushRate;
    }
}
