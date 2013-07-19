package com.vieo.dynamic.proxy.e1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/18/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class DebugProxy implements InvocationHandler {
    private Object obj;

    private DebugProxy(Object obj) {
        this.obj = obj; //Greet接口的实现：GreetImpl
    }

    public static Object newInstance(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new DebugProxy(obj));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            //自定義的處理
            System.out.println("--before method " + method.getName());
            //調用GreetImpl中方法
            result = method.invoke(obj, args);
        } catch(InvocationTargetException e) {
            throw e.getTargetException();
        } catch(Exception e) {
            throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
        } finally {
            System.out.println("--after method " + method.getName());
        }
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Greet tmp = new GreetImpl();

        Greet greet = (Greet) DebugProxy.newInstance(tmp);
        //生成的greet和tmp有相同的hashCode
        greet.sayHello("walter");
        greet.goodBye();
    }
}
