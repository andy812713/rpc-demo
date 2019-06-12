package com.andy.rpc.server.v3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>spring配置类</p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/9 0009 17:30
 */
@Configuration
@ComponentScan(basePackages = "com.andy.rpc.server.v3")
public class SpringConfig {

    @Bean(name = "rpcServerBySpring")
    public RpcServerBySpring rpcServerBySpring(){
        //需要使用spring4.2以上版本，否则无法执行有参构造
        return new RpcServerBySpring(9999);
    }
}
