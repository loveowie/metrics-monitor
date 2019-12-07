package pers.hyx.metrics.operation;

import io.prometheus.client.Counter;
import pers.hyx.metrics.metrics.Metrics;
import pers.hyx.metrics.metrics.MonitorTag;
import pers.hyx.metrics.metrics.MonitorType;
import pers.hyx.metrics.metrics.TagProduction;
import pers.hyx.metrics.util.SpringUtil;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-23 17:43
 */
public class Monitor {

    private MonitorTag tag;
    private MonitorType graph = MonitorType.SERVER;

    private Monitor(MonitorTag tag, MonitorType graph) {
        this.tag = tag;
        this.graph = graph;
    }

    private Monitor(MonitorTag tag) {
        this.tag = tag;
    }

    /**
     * @param identification identification or method name
     * @param data deal params
     * @param remark record something
     */
    private Monitor(String identification, String data, String remark) {
        TagProduction production = SpringUtil.getBean(TagProduction.class);
        this.tag = production.structureTag(identification, data, remark);
    }

    private Monitor() {
    }

    public static Monitor build() {
        return new Monitor();
    }

    public static Monitor build(String identification, String data, String remark) {
        return new Monitor(identification, data, remark);
    }

    public MonitorTag getTag() {
        return tag;
    }

    public Monitor setTag(MonitorTag tag) {
        this.tag = tag;
        return this;
    }

    public MonitorType getGraph() {
        return graph;
    }

    public Monitor setGraph(MonitorType graph) {
        this.graph = graph;
        return this;
    }

    public void push() {
        if (tag == null) {
            throw new NullPointerException("Monitor tag is null.");
        }
        Counter counter = Metrics.getIfPresent(graph.getGraph());
        assert counter != null;
        counter.labels(tag.toLabels()).inc(System.currentTimeMillis()/1000);
    }
}
