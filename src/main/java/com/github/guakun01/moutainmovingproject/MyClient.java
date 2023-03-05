package com.github.guakun01.moutainmovingproject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatcher;

public class MyClient {
    static List<String> methodsAnnotatedWithGuatalk = Stream.of(MyService.class.getMethods())
                    .filter(MyClient::isAnnotatedWithGuaTalk)
                    .map(Method::getName)
                    .collect(Collectors.toList());
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        MyService service = enhanceByAnnotation();

        service.getById(100);
        service.greeting("瓜坤");
        service.withoutAnnotation();
    }

    private static MyService enhanceByAnnotation() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        MyService serviceWithGuatalkInject = new ByteBuddy()
                .subclass(MyService.class)
                .method(target -> methodsAnnotatedWithGuatalk.contains(target.getName()))
                .intercept(MethodDelegation.to(LoggerInterceptor.class))
                .make()
                .load(MyClient.class.getClassLoader())
                .getLoaded()
                .getConstructor()
                .newInstance();
        return serviceWithGuatalkInject;
    }

    private static boolean isAnnotatedWithGuaTalk(Method method) {
        return method.getAnnotation(GuaTalk.class) != null;
    }

    public static class LoggerInterceptor {
        public static void log(@SuperCall Callable<Void> zuper) throws Exception {
            System.out.println("开始方法调用了");
            try {
                zuper.call();
            } finally {
                System.out.println("结束方法调用了");
            }
        }
    }
}
