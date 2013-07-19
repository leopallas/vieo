package com.vieo.dynamic.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/18/13
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) throws Throwable {
//        3，JVM动态生成代理类。
//        前提：给一个接口。会通过接口自动生成一个实现该接口的代理类。
//
//        Proxy类中的静态获得代理类字节码的方法。
//
//        static Class<?>
//        getProxyClass(ClassLoader loader, Class<?>... interfaces)
//        返回代理类的 java.lang.Class 对象，并向其提供类加载器和接口数组。
//        需要指定一个类加载器（通常使用后面接口的使用的类加载器），和实现的所有接口 作为参数。

        //4，JVM根据接口动态生成代理类，并对代理类进行基本的操作。
        // 动态获取实现collection接口的类的字节码。
        Class clazzProxy = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);

        System.out.println("-------begin constructors list------");
        Constructor[] constructors = clazzProxy.getConstructors();
        for (Constructor con : constructors) {
            String name = con.getName();
            // 在多线程的情况下用StringBuffer，在单线程用StringBuilder
            StringBuilder sBuilder = new StringBuilder(name);
            // 因为不考虑多线程的情况，所以使用StringBuilder来进行操作。这样效率会高很多，使用StringBuffer还要考虑线程安全，效率不高。
            sBuilder.append('(');
            Class[] clazzParas = con.getParameterTypes();
            for (Class para : clazzParas) {
                sBuilder.append(para.getName()).append(',');
            }
            if (clazzParas.length != 0) {
                sBuilder.deleteCharAt(sBuilder.length() - 1);
            }
            sBuilder.append(')');
            System.out.println(sBuilder.toString());
        }

        // 打印普通方法的名字和参数
        System.out.println("-------begin methods list------");
        Method[] methods = clazzProxy.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            // 在多线程的情况下用StringBuffer，在单线程用StringBuilder
            StringBuilder sBuilder = new StringBuilder(name);
            // 因为不考虑多线程的情况，所以使用StringBuilder来进行操作。这样效率会高很多，使用StringBuffer还要考虑线程安全，效率不高。
            sBuilder.append('(');
            Class[] clazzParas = method.getParameterTypes();
            for (Class para : clazzParas) {
                sBuilder.append(para.getName()).append(',');
            }
            if (clazzParas.length != 0) {
                sBuilder.deleteCharAt(sBuilder.length() - 1);
            }
            sBuilder.append(')');
            System.out.println(sBuilder.toString());
        }

        //5，创建动态类的实例对象。
        System.out.println("-------begin create instanceObject------");
        // 通过动态类的字节码获得构造方法。
        Constructor constructor = clazzProxy.getConstructor(InvocationHandler.class);
        class MyInvocationHandler implements InvocationHandler {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        }
        Collection proxy1 = (Collection) constructor.newInstance(new MyInvocationHandler());
        // 下面打印结果为null。思考：没有创建成功proxy1,还是proxy1的toString()方法返回是null。
        System.out.println(proxy1);
        // 可以用下面的方法来判断一个对象是否被正在的创建成功。
        // 如果创建成功的话，那么就返回null，如果没有创建成功包NullPointerException
        System.out.println(proxy1.toString());

        // 如果InvocationHandler中的invoke方法返回的是null。
        // 会出现空指针异常。
        // 原因： proxy1.size()返回类型是int ,invoke方法中的返回的值需要赋给各个方法的调用结果。也就是说把null强转成int，出现空指针异常。其他方法同上。
        // System.out.println(proxy1.size());

        // 直接一步到位，直接生成一个动态对象，和Class类中的newInstance方法很像，都是一步到位。
        Collection proxy3 = (Collection) Proxy.newProxyInstance(Collection.class.getClassLoader(),
                new Class[] { Collection.class }, new InvocationHandler() {
                    ArrayList target = new ArrayList();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        long start = System.currentTimeMillis();
                        Object retVal = method.invoke(target, args);
                        Thread.sleep(1000);
                        System.out.println(method.getName() + "方法，耗时" + (System.currentTimeMillis() - start) + "毫秒");
                        return retVal;
                    }
                });
        proxy3.add(1);
        proxy3.add(4);
        System.out.println(proxy3.size());
    }


//    private static Object getProxy(final Object target, final Advice advice) {
//        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(),
//                target.getClass().getInterfaces(),
//                new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object proxy, Method method,
//                                         Object[] args) throws Throwable {
//                        advice.beforeAdvice(method);
//                        Object retVal = method.invoke(target, args);
//                        advice.afterAdvice(method);
//                        return retVal;
//                    }
//                });
//        return proxy;
//    }
}
