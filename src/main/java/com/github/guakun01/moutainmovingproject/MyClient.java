package com.github.guakun01.moutainmovingproject;

public class MyClient {
    public static void main(String[] args) {
        MyService service = new MyService();

        service.getById(100);
        service.greeting("瓜坤");
        service.withoutAnnotation();
    }

     
}
