package com.andy.rpc.server;

import com.andy.rpc.server.api.IHelloService;
import com.andy.rpc.server.api.User;
import com.andy.rpc.server.v3.RpcServerAnnotation;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/7 0007 20:24
 */
@RpcServerAnnotation(value = IHelloService.class)
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String name) {
        return "Hello :" + name;
    }

    @Override
    public User getUser(String name) {
        User user = new User(name, 30);
        return user;
    }
}
