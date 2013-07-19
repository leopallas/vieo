package com.vieo.dynamic.proxy.e4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/19/13
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public final class $Proxy0 extends Proxy implements Subject {
    private static Method m1;

    private static Method m3;

    private static Method m0;

    private static Method m2;

    public $Proxy0(InvocationHandler paramInvocationHandler) {
        super(paramInvocationHandler);
    }

    public final boolean equals(Object paramObject) {
        try {
            return ((Boolean) this.h.invoke(this, m1, new Object[] { paramObject })).booleanValue();
        } catch (Error localError) {
            throw localError;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }

    }

    public final void request() {
        try {
            // 转发给InvocationHanlder对象处理
            this.h.invoke(this, m3, null);
            return;
        } catch (Error localError) {
            throw localError;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }

    }

    public final int hashCode() {
        try {
            return ((Integer) this.h.invoke(this, m0, null)).intValue();
        } catch (Error localError) {
            throw localError;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final String toString() {
        try {
            return (String) this.h.invoke(this, m2, null);
        } catch (Error localError) {
            throw localError;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[] { Class.forName("java.lang.Object") });
            m3 = Class.forName("com.leo.dynamic.proxy.e4.Subject").getMethod("request", new Class[0]);
            m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
            m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
        } catch (NoSuchMethodException localNoSuchMethodException) {
            throw new NoSuchMethodError(localNoSuchMethodException.getMessage());
        } catch (ClassNotFoundException localClassNotFoundException) {
            throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
        }
    }
}