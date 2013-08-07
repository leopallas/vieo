package com.vieo.dynamic.proxy.e3;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/19/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceImpl implements UserService {

    @Override
    public boolean add(String name) {
        System.out.println(String.format("添加一个名为:%s的用户", name));
        return true;
    }
}
