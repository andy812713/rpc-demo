package com.andy.rpc.server.v3;

import com.andy.rpc.server.api.IHelloService;
import com.andy.rpc.server.api.User;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/7 0007 20:24
 */
@RpcServerAnnotation(value = IHelloService.class, version = "2.0")
public class HelloServiceImplV4 implements IHelloService {

    @Override
    public String sayHello(String name) {
        return "[2.0]Hello :" + name;
    }

    @Override
    public User getUser(String name) {
        User user = new User("[2.0]"+name, 30);
        return user;
    }
}
