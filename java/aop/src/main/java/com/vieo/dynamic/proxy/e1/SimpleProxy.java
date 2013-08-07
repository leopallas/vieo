package com.vieo.dynamic.proxy.e1;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 7/18/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleProxy implements Greet {
    private Greet greet = null;

    SimpleProxy(Greet greet) {
        this.greet = greet;
    }

    public void sayHello(String name) {
        System.out.println("--before method sayHello");
        greet.sayHello(name);
        System.out.println("--after method sayHello");
    }

    public void goodBye() {
        System.out.println("--before method goodBye");
        greet.goodBye();
        System.out.println("--after method goodBye");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Greet greet = new SimpleProxy(new GreetImpl());//生成代理
        greet.sayHello("walter");
        greet.goodBye();
    }
}
