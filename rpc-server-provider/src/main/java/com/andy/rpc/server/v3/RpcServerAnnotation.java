package com.andy.rpc.server.v3;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/9 0009 17:30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcServerAnnotation {

    Class<?> value();
    String version() default "";
}
