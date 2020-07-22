####sentinel使用nacos做持久化配置
~~~json
{
        //资源名称
        "resource":"sentinel",
        //流控针对的调用来源，若为 default 则不区分调用来源
        "limitApp":"default",
        //限流阈值类型（QPS 或并发线程数）；0代表根据并发数量来限流，1代表根据QPS来进行流量控制
        "grade":1,
        //限流阈值
        "count":20,
        //流控模式 0-直接，1-关联，2-链路
        "strategy":0,
        //流控效果，0-快速失败，1-Warm up,2-排队等待
        "controlBehavior":0,
        //是否集群
        "clusterMode":false
    }
~~~
