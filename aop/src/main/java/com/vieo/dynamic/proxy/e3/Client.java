package com.vieo.dynamic.proxy.e3;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/19/13
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        //用代理工具类生成一个UserService服务代理
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        DynamicProxyUtil dynamicProxy = new DynamicProxyUtil();
        UserService userServiceProxy = (UserService)dynamicProxy.generateProxyClass(userService);
        //通过生成的代理类来调用方法,而不是直接调用userService.add。这样我们就可以动态调用服务方法,
        //以便在代理工具类中动态加入其他业务逻辑代码,如系统日志记录等等
        //spring aop默认就是使用JdkDynamic实现代理bean的，spring aop还有一种就是cglib动态字节码生成类工具包
        boolean result = userServiceProxy.add("leo");
        System.out.println(result?"添加成功":"添加失败");
    }
}
