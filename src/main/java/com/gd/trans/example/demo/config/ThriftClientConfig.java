package com.gd.trans.example.demo.config;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author joinly
 * @company: goodinvest
 * @date 2020-10-13 09:30
 * @desc: TODO
 */
@Configuration
public class ThriftClientConfig {
    @Value("${thrift.host}")
    private String host;

    @Value("${thrift.port}")
    private Integer port;

    @Bean
    public ThriftClientConnectPoolFactory ThriftClientPool() {
        //对象池的配置对象
        //这里测试就直接使用默认的配置 可以通过config 设置对应的参数
        GenericObjectPool.Config config = new GenericObjectPool.Config();
        //创建一个池工厂对象
        ThriftClientConnectPoolFactory thriftClientConnectPoolFactory = new ThriftClientConnectPoolFactory(config, host, port);
        //交由Spring管理
        return thriftClientConnectPoolFactory;
    }
}
