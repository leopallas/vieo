package com.vieo.dynamic.proxy.e4;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/19/13
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class RealSubject implements Subject {

    @Override
    public void request() {
        System.out.println("Processing request in real subject...");
    }
}
