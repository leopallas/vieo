package com.vieo.dynamic.proxy.e4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/19/13
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class RequestInvocationHandler implements InvocationHandler {
    private Subject obj;

    private AopLogger logger;

    public RequestInvocationHandler(Subject obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // do some processing before the method invocation
        System.out.println("Before requesting real subject...");
        logger.logBefore();
        // TODO:
        // how to use this proxy object?

        // invoke the method
        Object result = method.invoke(obj, args);

        // do some processing after the method invocation
        logger.logAfter();
        System.out.println("After requesting real subject...");
        return result;
    }

    public AopLogger getLogger() {
        return logger;
    }

    public void setLogger(AopLogger logger) {
        this.logger = logger;
    }
}
