package com.andy.rpc.server.v3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/12 0012 19:03
 */
public class RpcServerTest {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        ((AnnotationConfigApplicationContext)context).start();
    }
}
