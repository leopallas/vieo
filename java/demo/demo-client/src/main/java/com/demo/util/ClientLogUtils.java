package com.demo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClientLogUtils {

    private static final String LOG_UTILS_CLASS_NAME  = "com.icommunity.common.crash.ExceptionLogUtils";
    private static final String LOG_UTILS_METHOD_NAME = "saveExceptionInfoToFile";

    public static void saveLogToFile(Throwable throwable) {
        try {
            Method m;
            Class<?> c = Class.forName(LOG_UTILS_CLASS_NAME);
            m = c.getMethod(LOG_UTILS_METHOD_NAME, new Class[] { Throwable.class });
            m.invoke(c, new Object[] { throwable });
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
