package com.vieo.dynamic.proxy.e2;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/17/13
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class ManagerImpl implements Manager {

    @Override
    public void modify() {
        System.out.println("*******modify()方法被调用");
    }
}
