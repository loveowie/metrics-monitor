//package pers.hyx.metrics;
//
//import Monitor;
//import io.prometheus.client.CollectorRegistry;
//import io.prometheus.client.Counter;
//import io.prometheus.client.exporter.PushGateway;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusPushGatewayManager;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
//import java.io.IOException;
//import java.time.Duration;
//
///**
// * @author hyx
// * @link loveowie@vip.qq.com
// * @since 2019-05-22 16:39
// */
//@SpringBootApplication
//@EnableScheduling
//public class MetricsMonitorApplication {
//
//    public static void main(String[] args) throws IOException {
//        SpringApplication.run(MetricsMonitorApplication.class, args);
////        demo2();
//        Monitor.build("method", "data", "remark").push();
//    }
//
//
//    private static void demo2() {
//
//        CollectorRegistry registry = new CollectorRegistry();
//
//
//        PushGateway prometheusPush = new PushGateway("localhost:9091");
//        PrometheusPushGatewayManager manager = new PrometheusPushGatewayManager(prometheusPush, registry,
//                Duration.ofMinutes(5), "job name", null, null);
//
//        Counter counterDemo = Counter.build()
//                .name("springtest")
//                .labelNames("data", "uri", "remark", "ip", "instance")
//                .help("this is demo")
//                .create();
//        Counter counterDemo2 = Counter.build()
//                .name("springtest2")
//                .labelNames("data", "uri", "remark", "ip", "instance")
//                .help("this is demo")
//                .create();
//
//        registry.register(counterDemo);
//        registry.register(counterDemo2);
//
//        counterDemo.labels("{5ggg:ggg,gg:gg}", "xxx:port/v1/xx", "this is test", "172.22.90.172:9091", "test").inc(6);
//        counterDemo2.labels("{ggg:ggg,gg:gg}", "xxx:port/v1/xx", "this is test", "172.22.90.172:9091", "test").inc(6);
//
//
//        manager.shutdown();
//    }
//
//
//    private static void demo1() {
//        Counter counterDemo = Counter.build()
//                .name("woshiceshide2")
//                .labelNames("data", "uri", "remark", "ip", "instance")
//                .help("this is demo")
//                .register();
//
//        PushGateway prometheusPush = new PushGateway("localhost:9091");
//        try {
//            counterDemo.labels("{zzzz:yyy,yy:yy}", "xxx:port/v1/xx", "this is test", "172.22.90.172:9091", "test");
//            prometheusPush.push(counterDemo, "sp-getway1");
//            counterDemo.labels("{ddd:hhh,hh:hh}", "xxx:port/v1/xx", "this is test", "172.22.90.172:9091", "test");
//            prometheusPush.push(counterDemo, "sp-getway1");
//            counterDemo.setChild(new Counter.Child() {
//                @Override
//                public double get() {
//                    return 1d;
//                }
//            }, "{gggg:ggg,gg:gg}", "xxx:port/v1/xx", "this is test", "172.22.90.172:9091", "test");
//            prometheusPush.push(counterDemo, "sp-getway1");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
