package com.vieo.dynamic.proxy.e2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/17/13
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class BusinessHandler implements InvocationHandler {
    private Object object = null;

    public BusinessHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("do something before method");
        Object ret = method.invoke(this.object, args);
        System.out.println("do something after method");
        if (proxy instanceof Manager) {
            System.out.println("manager....");
        }
        return ret;
    }
}
