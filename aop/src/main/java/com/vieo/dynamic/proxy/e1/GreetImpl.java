package com.vieo.dynamic.proxy.e1;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/18/13
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class GreetImpl implements Greet {
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }

    public void goodBye() {
        System.out.println("Good bye.");
    }
}
