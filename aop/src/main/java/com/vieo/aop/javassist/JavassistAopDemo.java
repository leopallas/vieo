/**
 * Create on 2011-10-13 ����04:29:04 by tengfei.fangtf
 * 
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * 
 * All rights reserved.
 */
package com.vieo.aop.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;

import com.vieo.aop.model.Business;



/**
 * 使用Javassist演示Aop的Demo.
 * 
 * @author tengfei.fangtf
 */
public class JavassistAopDemo {

    public static void main(String[] args) throws Exception {
        aop();
    }

    public static void aop() throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        //获取存放CtClass的容器ClassPool
        ClassPool cp = ClassPool.getDefault();
//        //创建一个类加载器
//        Loader cl = new Loader();
//        //增加一个转换器，让类加载的时候
//        cl.addTranslator(cp, new MyTranslator());
//        //将类装载到JVM
//        try {
//            cl.run("jsvassist.JavassistAopDemo$MyTranslator", null);
//        } catch (Throwable e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        CtClass cc = cp.get("model.Business");
        //获得指定方法名的方法
        CtMethod m = cc.getDeclaredMethod("doSomeThing");
        //在方法执行前插入代码
        m.insertBefore("{ System.out.println(\"记录日志\"); }");
        ((Business)cc.toClass().newInstance()).doSomeThing();
        
    }

    public static class MyTranslator implements Translator {

        public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
        }

        /* *
         * 类装载到JVM前进行代码织入
         */
        public void onLoad(ClassPool pool, String classname) {
            if (!"model.Business".equals(classname)) {
                return;
            }
            //通过报名获取类文件
            try {
                CtClass cc = pool.get(classname);
                //获得指定方法名的方法
                CtMethod m = cc.getDeclaredMethod("doSomeThing");
                //在方法执行前插入代码
                m.insertBefore("{ System.out.println(\"记录日志\"); }");
            } catch (NotFoundException e) {
            } catch (CannotCompileException e) {
            }
        }

        public static void main(String[] args) {
            Business b = new Business();
            b.doSomeThing2();
            b.doSomeThing();
        }
    }

}
