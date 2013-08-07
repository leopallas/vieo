package com.vieo.dynamic.proxy;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/19/13
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class Test2 {
    public static void main(String[] args) throws Throwable {
        Class clazzType = Class.forName("com.vieo.dynamic.proxy.e1.DebugProxy");
        Method[] methods = clazzType.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i].toString());
        }

        Class clazzType1 = Class.forName("com.vieo.dynamic.proxy.e1.SimpleProxy");
        Field[] fields = clazzType1.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].toString());
        }

        Class<?> clazzType2 = Class.forName("java.lang.String");
        Object array = Array.newInstance(clazzType2, 10);
        Array.set(array, 5, "hello");
        String s = (String) Array.get(array, 5);
        System.out.println(s);
    }
}
