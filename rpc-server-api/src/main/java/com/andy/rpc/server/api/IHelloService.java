package com.andy.rpc.server.api;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/7 0007 20:20
 */
public interface IHelloService {

    public String sayHello(String name);

    public User getUser(String name);
}
