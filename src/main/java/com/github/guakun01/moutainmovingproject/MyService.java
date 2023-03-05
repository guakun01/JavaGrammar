package com.github.guakun01.moutainmovingproject;

public class MyService {

    @GuaTalk
    public void getById(int id) {
        System.out.println("Get element " + id);
    }

    @GuaTalk
    public void greeting(String name) {
        System.out.println("你好啊 " + name);
    }

    public void withoutAnnotation() {
        System.out.println("没有注解");
    }
}
