package pers.hyx.metrics.metrics;

import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-23 11:23
 */
public class Metrics {

    private final static Logger LOG = LoggerFactory.getLogger(Metrics.class);

    private static ConcurrentMap<String, Counter> METRICS = new ConcurrentHashMap<>();

    static {
        MonitorType[] values = MonitorType.values();
        Field[] declaredFields = MonitorTag.class.getDeclaredFields();
        String[] labelNames = new String[declaredFields.length];
        for (int i=0; i<declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            labelNames[i] = declaredFields[i].getName();
        }
        for (MonitorType graph : values) {
            put(graph.getGraph(), graph.getGraph(), labelNames);
        }
        LOG.info("init metrics success.");
    }

    public static ConcurrentMap<String, Counter> asMap() {
        return METRICS;
    }

    public static Counter getIfPresent(String graph) {
        if (METRICS.containsKey(graph)) {
            return METRICS.get(graph);
        }
        return null;
    }

    private static void put(String graph, String help, String[] labelNames) {
        if (!METRICS.containsKey(graph)) {
            try {
                Counter counter = Counter.build()
                        .name(graph)
                        .labelNames(labelNames)
                        .help(help)
                        .create();
                METRICS.put(graph, counter);
            }catch (Exception ignore) {}
        }
    }
}
