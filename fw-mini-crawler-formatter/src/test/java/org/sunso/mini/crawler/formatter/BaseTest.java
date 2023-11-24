package org.sunso.mini.crawler.formatter;

public abstract class BaseTest {

    protected void print(String result) {
        System.out.println("" + result);
    }

    protected void print(Object result) {
        System.out.println("" + result.toString());
    }
}
