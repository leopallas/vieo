package com.vieo.dynamic.proxy.e4;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/19/13
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        // ***** save proxy class into file , 将生成的代理类字节码保存到本地文件中*******
        Field field = System.class.getDeclaredField("props");
        field.setAccessible(true);
        Properties props = (Properties) field.get(null);
        props.put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // ****************************************

//        Package pkg = Client.class.getPackage();
//        if (pkg != null) {
//            String packagePath = pkg.getName().replace(".", File.pathSeparator);
//            new File(packagePath).mkdirs();
//        }

        // TODO:
        // 怎样防止客户端绕过proxy直接调用RealSubject的方法？
        Subject origObj = new RealSubject();
        Subject proxy = (Subject) getProxy(origObj);

        proxy.request();
    }

    private static Object getProxy(Subject origObj) {
        RequestInvocationHandler handler = new RequestInvocationHandler(origObj);
        handler.setLogger(new AopLogger() {
            @Override
            public void logBefore() {
                System.out.println("AOP logger working before...");
            }

            @Override
            public void logAfter() {
                System.out.println("AOP logger working after...");
            }
        });

        // 这里会通过ProxyGenerator类生成代理类的字节码，并由origObj所在的ClassLoader加载进JVM，然后通过反射实例化出一个代理对象
        return Proxy.newProxyInstance(origObj.getClass().getClassLoader(), origObj.getClass()
                .getInterfaces(), handler);
    }
}
