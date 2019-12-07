package pers.hyx.metrics.metrics;

import pers.hyx.metrics.config.MetricsProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-23 17:07
 */
@Component
public class TagProduction {

    @Resource
    private MetricsProperties properties;

    public MonitorTag structureTag(String method, String data, String remark) {

        MonitorTag monitorTag = new MonitorTag();
        monitorTag.setIp(getHostAddress());
        monitorTag.setServer(properties.getServer());
        monitorTag.setMethod(method);
        monitorTag.setData(data);
        monitorTag.setRemark(remark);

        return monitorTag;
    }

    private String getHostAddress() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            return host.getHostAddress();
        } catch (UnknownHostException e) {
            return "";
        }
    }
}
