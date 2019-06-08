package com.andy.rpc.server.api;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * <p>请求实体类</p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/7 0007 20:34
 */
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = 7448722637619827640L;
    private String serverName;
    private String method;
    private Object[] args;
    private String[] types;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "serverName='" + serverName + '\'' +
                ", method='" + method + '\'' +
                ", args=" + Arrays.toString(args) +
                ", types=" + Arrays.toString(types) +
                '}';
    }
}
