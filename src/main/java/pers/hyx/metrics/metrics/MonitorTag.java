package pers.hyx.metrics.metrics;

import javax.annotation.Resource;

/**
 * @author hyx
 * @link loveowie@vip.qq.com
 * @since 2019-05-23 16:40
 */
public class MonitorTag {
    private String ip;
    private String server;
    private String method;
    private String remark;
    private String data;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String[] toLabels() {
        return new String[]{
                this.ip,
                this.server,
                this.method,
                this.remark,
                this.data
        };
    }
}
