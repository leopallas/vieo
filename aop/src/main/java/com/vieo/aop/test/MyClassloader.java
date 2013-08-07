/**
 * Create on 2011-10-16 ����10:20:25 by tengfei.fangtf
 *
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 *
 * All rights reserved.
 */
package com.vieo.aop.test;

import com.vieo.aop.model.Business;

/**
 * 使用自定义的类加载
 *
 * @author tengfei.fangtf
 */
public class MyClassloader extends ClassLoader {

    public static void main(String[] args) {
        try {
            MyClassloader myClassloader = new MyClassloader();
            Class b = myClassloader.loadClass("model.Business");
            ((Business) b.newInstance()).doSomeThing();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
