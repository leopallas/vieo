package com.demo.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PPMServiceManager {
    public static final String  EXTRA_INFO     = "";

    private static final String PACKAGE_PREFIX = PPMServiceManager.class.getPackage().getName() + ".impl";

    private static final String CLASS_NAME     = "PPMServiceImpl";

    @SuppressWarnings("unchecked")
    public static PPMService getService(String serverAddress, int serverPort) {
        String className = String.format("%s.%s", PACKAGE_PREFIX, CLASS_NAME);
        try {
            Class<? extends PPMService> clazz = (Class<? extends PPMService>) Class.forName(className);
            Constructor<? extends PPMService> constructor = clazz.getConstructor(String.class, int.class);
            return constructor.newInstance(serverAddress, serverPort);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static PPMService getService(String serverAddress, int serverPort, int serverSecretPort, boolean https) {
        String className = String.format("%s.%s", PACKAGE_PREFIX, CLASS_NAME);
        try {

            Class<? extends PPMService> clazz = (Class<? extends PPMService>) Class.forName(className);
            Constructor<? extends PPMService> constructor = clazz.getConstructor(String.class, int.class, int.class,
                    boolean.class);
            return constructor.newInstance(serverAddress, serverPort, serverSecretPort, https);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
