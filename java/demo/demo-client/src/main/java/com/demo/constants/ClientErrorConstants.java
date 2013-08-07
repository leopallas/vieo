package com.demo.constants;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClientErrorConstants {
    @Target({ ElementType.FIELD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface Description {
        String value() default "";

        int key() default 0;
    }

    @Description(value = "服务器维护中，请稍后 ", key = 0)
    public static int                   SERVER_MAINTENANCE           = 0;

    @Description(value = "客户端错误", key = 2)
    public static int                   CLIENT_GENERIC_ERROR         = 2;

    @Description(value = "连接超时", key = 3)
    public static int                   CONNECT_TIMEOUT_ERROR        = 3;

    @Description(value = "socket超时", key = 4)
    public static int                   SOCKET_TIMEOUT_ERROR         = 4;

    @Description(value = "不能连接到服务器", key = 5)
    public static int                   HOST_CONNECTION_ERROR        = 5;

    @Description(value = "访问资源不存在", key = 404)
    public static int                   REQUEST_RESOURCE_NOT_EXISTED = 404;

    private static Map<Integer, String> descriptionMap               = new HashMap<Integer, String>();

    static {
        Field[] fields = ClientErrorConstants.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Description.class)) {
                Description description = field.getAnnotation(Description.class);
                descriptionMap.put(Integer.valueOf(description.key()), description.value());
            }
        }
    }

    public static String getDescription(int errorCode) {
        return descriptionMap.get(errorCode);
    }

    public static void main(String[] args) {
        try {
            PrintStream stream = new PrintStream("server_error.txt");
            Iterator<Integer> it = descriptionMap.keySet().iterator();
            while (it.hasNext()) {
                Integer key = it.next();
                stream.println(String.format("Error Code:%d   Description:%s", key, descriptionMap.get(key)));
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
