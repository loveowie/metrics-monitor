package pers.hyx.metrics.operation;

import pers.hyx.metrics.metrics.Metrics;
import pers.hyx.metrics.metrics.MonitorType;
import pers.hyx.metrics.metrics.MonitorTag;
import pers.hyx.metrics.metrics.TagProduction;
import pers.hyx.metrics.util.SpringUtil;
import io.prometheus.client.Counter;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-23 19:31
 */
public class MonitorBlock {

    public void block(Runnable runnable, MonitorType graph, String identification, String data, String remark) {
        try {
            runnable.run();
        } catch (Exception e) {
            TagProduction production = SpringUtil.getBean(TagProduction.class);
            MonitorTag monitorTag = production.structureTag(identification, data, remark);
            Counter counter = Metrics.getIfPresent(graph.getGraph());
            assert counter != null;
            counter.labels(monitorTag.toLabels()).inc(System.currentTimeMillis()/1000);
        }
    }

    public void block(Runnable runnable, String identification, String data, String remark) {
        this.block(runnable, MonitorType.SERVER, identification, data, remark);
    }

    public interface Runnable {
        void run();
    }
}
