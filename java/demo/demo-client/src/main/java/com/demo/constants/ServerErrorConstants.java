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

public class ServerErrorConstants {
    @Target({ ElementType.FIELD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface Description {
        String value() default "";

        int key() default 0;
    }

    public static final String          HEADER_ERROR_CODE             = "PPM_ERROR_CODE";

    public static final String          HEADER_ERROR_DESCRIPTION      = "PPM_ERROR_DESCRIPTION";

    @Description(value = "服务器维护中，请稍后 ", key = 0)
    public static int                   SERVER_MAINTENANCE            = 0;

    @Description(value = "服务器错误", key = 1)
    public static int                   SERVER_GENERIC_ERROR          = 1;

    @Description(value = "输入参数是空或是无效的", key = 2)
    public static int                   INVALID_REQUEST_PARAMETER     = 2;

    @Description(value = "用户当前的令牌是无效的", key = 3)
    public static int                   INVALID_SECRET_TOKEN          = 3;

    @Description(value = "当前用户已经在其它设备上登录", key = 4)
    public static int                   USER_LOGINED_ON_OTHER_DEVICE  = 4;

    @Description(value = "无效的注册码", key = 5)
    public static int                   INVALID_REGISTER_BARCODE      = 5;

    @Description(value = "不能使用未注册设备", key = 6)
    public static int                   INVALID_DEVICE                = 6;

    @Description(value = "用户未注册", key = 7)
    public static int                   INVALID_USER                  = 7;

    @Description(value = "密码错误", key = 8)
    public static int                   INVALID_PASSWORD              = 8;

    @Description(value = "无效的请求", key = 9)
    public static int                   INVALID_URL                   = 9;

    @Description(value = "终端没有登录", key = 10)
    public static int                   PDA_NOT_LOGIN                 = 10;

    @Description(value = "上传文件是空的", key = 12)
    public static int                   INVALID_UPLOAD_FILE           = 12;

    @Description(value = "上传文件到服务器端失败", key = 13)
    public static int                   UPLOAD_FILE_PERSISTENT_FAIL   = 13;

    @Description(value = "上传文件不存在", key = 14)
    public static int                   UPLOAD_FILE_NOT_EXIST_ERROR   = 14;

    @Description(value = "设备已经注册", key = 15)
    public static int                   DEVICE_ALREADY_REGISTER       = 15;

    @Description(value = "设备注册码已经被使用", key = 16)
    public static int                   REGISTER_BARCODE_USED         = 16;

    @Description(value = "用户已经在当前设备登录", key = 17)
    public static int                   USER_ALREADY_LOGIN            = 17;

    @Description(value = "上传文件过大", key = 18)
    public static int                   MAX_UPLOAD_FILE_SIZE_EXCEEDED = 18;

    @Description(value = "用户名不能为空", key = 19)
    public static int                   USER_NOT_EMPTY                = 19;

    @Description(value = "密码不能为空", key = 20)
    public static int                   USER_PWD_NOT_EMPTY            = 20;

    @Description(value = "没有设备序列号", key = 21)
    public static int                   DEVICE_NO_NOT_EMPTY           = 21;

    @Description(value = "访问资源不存在", key = 404)
    public static int                   REQUEST_RESOURCE_NOT_EXISTED  = 404;

    private static Map<Integer, String> descriptionMap                = new HashMap<Integer, String>();

    static {
        Field[] fields = ServerErrorConstants.class.getDeclaredFields();
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
