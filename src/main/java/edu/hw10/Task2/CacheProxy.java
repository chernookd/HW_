package edu.hw10.Task2;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class CacheProxy {
    private CacheProxy() {}

    public static <T> T create(T proxy, Class<T> clazz, File cacheFile) throws NoSuchMethodException, IOException {
        return (T) Proxy.newProxyInstance(
            clazz.getClassLoader(),
            new Class<?>[] {clazz},
            new CacheInvocationHandler(proxy, cacheFile));
    }
}
