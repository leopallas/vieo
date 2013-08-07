/**
 * Create on 2011-10-12 ����02:03:12 by tengfei.fangtf
 *
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 *
 * All rights reserved.
 */
package com.vieo.aop.dynamicProxy;

import com.vieo.aop.dynamicProxy.DynamicProxyDemo.LogInvocationHandler;
import com.vieo.aop.model.Business;
import com.vieo.aop.model.IBusiness;
import com.vieo.aop.model.IBusiness2;

import java.lang.reflect.Method;

/**
 * 织入器生成的代理类
 *
 * @author tengfei.fangtf
 */
public class ProxyBusiness implements IBusiness, IBusiness2 {

    private LogInvocationHandler h;

    @Override
    public void doSomeThing2() {
        try {
            Method m = (h.target).getClass().getMethod("doSomeThing", null);
            h.invoke(this, m, null);
        } catch (Throwable e) {
            // 异常处理 1（略）
        }
    }

    @Override
    public boolean doSomeThing() {
        try {
            Method m = (h.target).getClass().getMethod("doSomeThing2", null);
            return (Boolean) h.invoke(this, m, null);
        } catch (Throwable e) {
            // 异常处理 1（略）
        }
        return false;
    }

    public ProxyBusiness(LogInvocationHandler h) {
        this.h = h;
    }

    //测试用
    public static void main(String[] args) {
        //构建AOP的Advice
        staticDynamic();
    }

    public static void staticDynamic() {
        LogInvocationHandler handler = new LogInvocationHandler(new Business());
        new ProxyBusiness(handler).doSomeThing();
        new ProxyBusiness(handler).doSomeThing2();
    }

}
