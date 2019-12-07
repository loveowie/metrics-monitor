package pers.hyx.metrics.metrics;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-23 15:37
 */
public enum MonitorType {

    /**
     * rpc exception
     */
    RPC("exception_rpc"),
    /**
     * server exception
     */
    SERVER("exception_server"),
    /**
     * custom exception
     */
    CUSTOM("exception_custom"),
    /**
     * others exception
     */
    OTHER("exception_other")
    ;

    private String graph;

    MonitorType(String graph) {
        this.graph = graph;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }
}
