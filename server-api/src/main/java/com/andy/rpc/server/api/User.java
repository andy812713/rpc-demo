package com.andy.rpc.server.api;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/7 0007 20:16
 */
public class User implements Serializable {

    private static final long serialVersionUID = 6367539080124267934L;
    private String name;
    private int age;

    public User(){}

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
