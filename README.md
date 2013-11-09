## base
**公共调用**

jetty:9950/8030

## trade
**订单同步和计算**

jetty:9949/8029

## 安装rocketmq
    mvn install:install-file -Dfile=/Users/kevin/Dev/alibaba-rocketmq/lib/rocketmq-client-3.0.2.jar -DgroupId=com.alibaba.rocketmq  -DartifactId=rocketmq-client -Dversion=3.0.2 -Dpackaging=jar

    mvn install:install-file -Dfile=/Users/kevin/Dev/alibaba-rocketmq/lib/rocketmq-common-3.0.2.jar -DgroupId=com.alibaba.rocketmq  -DartifactId=rocketmq-common -Dversion=3.0.2 -Dpackaging=jar

##监控
1. zk monitor：https://github.com/alibaba/taokeeper