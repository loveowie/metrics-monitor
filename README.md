#### Metrics-monitor是将埋点信息推送至prometheus的pushGateway组件的插件
> prometheus会定时从pushGateway组件中拉取信息，所以此处将埋点信息推送至PushGateway看起来类似于将埋点信息推送至prometheus

### 如何使用？
1. **引入插件**
```maven
<dependency>
	<groupId>pers.hyx</groupId>
	<artifactId>metrics-monitor</artifactId>
	<version>0.0.1-beta</version>
</dependency>
```

2. **配置metrics-monitor**
在application.properties中配置metrics-monitor相关配置
```
// pushGateway地址
metrics.config.push-gateway=172.22.90.172:9091
// server名称，标识具体服务，建议为服务名（或其他辨识度高的标识）
metrics.config.server=test
// 推送间隔，即产生埋点后多久进行推送，若不配置默认为1分钟
metrics.config.push-rate=5000
```

3. **启用metrics-monitor**
在启动类或者配置类中加载注解 **@EnableMetricsMonitor**
```java
@SpringBootApplication
@EnableMetricsMonitor
public class DemoApplication {
		public static void main(String[] args) {
			SpringApplication.run(DemoApplication.class, args);
		}
}
```

4. **注解使用metrics-monitor**
可在相关方法中加载注解**@MetricsMonitor**
```java
@Service
public class DemoService {

		@MetricsMonitor(type = MonitorType.OTHER, 
				remark = "test remark", 
				 monitorFor = {NullPointerException.class, ServerErrorException.class},
				 noMonitorFor = {IllegalArgumentException.class, NumberFormatException.class})
		public void demo() throws Exception {
			//do something
		}
}
```
**注解参数说明**
	- **type** : metrics类型，可选**RPC**、**SERVER**、**CUSTOM**、**OTHER**
	- **remark** :  备注标识（可选）
	- **monitorFor** : 需埋点异常类型（可选，可多选）
	- **noMonitorFor** : 不需要埋点异常类型（可选，可多选）

5. **代码块使用metrics-monitor**
使用**MonitorBlock**类进行生成局部代码块的方式进行埋点
```java
    public void block() {
        new MonitorBlock().block(new MonitorBlock.Runnable() {
            @Override
            public void run() {
                //do something
            }
        }, MonitorType.RPC, "method or identification", "params like json", "remark");
    }
```
<font color=red>**！! 此处代码块中抛出任意异常，均会被捕获并进行埋点**</font>

6. **手动添加metrics-monitor**
Monitor中**metrics类型**默认为**SERVER**
```java
    public void monitor() throws IOException {
        //method 1
        Monitor.build("method or identification", "params like json", "remark").push();

        //method 2
        TagProduction production = SpringUtil.getBean(TagProduction.class);
        Monitor.build().setTag(production.structureTag("method or identification", "params like json", "remark")).setGraph(MonitorType.RPC).push();

        //method 3
        MonitorTag monitorTag = new MonitorTag();
        monitorTag.setIp("host address");
        monitorTag.setServer("server information");
        monitorTag.setMethod("method or identification");
        monitorTag.setData("params like json");
        monitorTag.setRemark("remark");
        Monitor.build().setTag(monitorTag).setGraph(MonitorType.RPC).push();
    }
```

###如何查看？
- 埋点成功后可打开配置的**pushGateway地址**，找到**job-name = exception_alert**相关数据，打开即可看到埋点信息

