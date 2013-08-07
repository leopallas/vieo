package com.vieo.dynamic.proxy.e3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/19/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 * <p/>
 * 代理工具类,用其他类提供代理
 */
public class DynamicProxyUtil implements InvocationHandler {

    /**
     * 被代理的对象
     */
    private Object proxyObject;

    /**
     * 将被代理对象传入,以便生产代理类
     */
    public Object generateProxyClass(Object proxyObject) {
        this.proxyObject = proxyObject;//将被代理对象引入该类
        //根据被代理对象的类信息生产代理类
        Class<?> clazz = proxyObject.getClass();
        /*Proxy.newProxyInstance方法实现原理:jvm会根据传入的接口及其实现类动态生成如:$Proxy.class字节码类,默认只在内存中,
        可使用System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true")将类文件保存在本地磁盘
        $Proxy0 实现要代理的对象接口及其托管对被代理对象所有方法调用
        public final class $Proxy0 extends java.lang.reflect.Proxy implements UserService
        */
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(String.format("方法%s调用前,传入参数%s", method.getName(), Arrays.toString(args)));
        Object result = method.invoke(this.proxyObject, args);
        System.out.println(String.format("方法%s调用前,返回值%s", method.getName(), result));
        return result;
    }
}
