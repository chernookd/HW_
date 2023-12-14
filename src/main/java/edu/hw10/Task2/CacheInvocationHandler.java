package edu.hw10.Task2;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class CacheInvocationHandler implements InvocationHandler {
    File cacheFile;
    Object currentInterface;
    private final Map<String, Object> cacheMemory = new HashMap<>();

    <T> CacheInvocationHandler(T proxy, File cacheFile) throws IOException {
        this.cacheFile = cacheFile;
        this.currentInterface = proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        String key = (method.getName() + " " + Arrays.toString(args));
        if (getCacheAnnotation(method.getAnnotations()).persist()) {
            if (cacheFile.exists() && cacheFile.length() > 0) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(cacheFile))) {
                    CacheEntry entry;
                    while ((entry = (CacheEntry) in.readObject()) != null) {
                        if (key.equals(entry.key)) {
                            return entry.value;
                        }
                    }
                } catch (EOFException ignored) {
                }
            }
            result = method.invoke(currentInterface, args);
            try (ObjectOutputStream out = cacheFile.length() == 0
                ? new ObjectOutputStream(new FileOutputStream(cacheFile))
                : new AppendingObjectOutputStream(new FileOutputStream(cacheFile, true))) {
                out.writeObject(new CacheEntry(key, result));
            }
        } else {
            if (cacheMemory.containsKey(key)) {
                return cacheMemory.get(key);
            } else {
                result = method.invoke(currentInterface, args);
                cacheMemory.put(key, result);
            }
        }
        return result;
    }

    private static Cache getCacheAnnotation(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Cache.class) {
                return (Cache) annotation;
            }
        }
        return null;
    }

    private static class CacheEntry implements Serializable {
        String key;
        Object value;

        CacheEntry(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class AppendingObjectOutputStream  extends ObjectOutputStream {

        AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }
}
