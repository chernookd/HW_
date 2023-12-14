package edu.hw10.Task1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Random;

public class RandomObjectGenerator {

    private static final Random RANDOM = new Random();
    private static final int RANDOM_STRING_LENGTH = 5;
    private static final int RANDOM_CHAR_BOUND_MIN = 65;
    private static final int RANDOM_CHAR_BOUND_MAX = 90;


    public <T> T nextObject(Class<T> clazz, String createName, Class<?>... parameterTypes) {
        try {
            Method method = clazz.getMethod(createName, parameterTypes);
            Object object = createObject(clazz, method);
            return (T) object;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public <T> T nextObject(Class<T> clazz, Class<?>... parameterTypes) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes);
            Object object = createObject(clazz, constructor);
            return (T) object;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public <T> T nextRecord(Class<T> clazz) {
        try {
            if (!clazz.isRecord()) {
                throw new IllegalArgumentException();
            }

            Constructor<T> constructor = (Constructor<T>) canonicalConstructorOfRecord(clazz);
            RecordComponent[] recordComponents = clazz.getRecordComponents();
            Object[] constructorParameters = new Object[recordComponents.length];
            constructorParameters = createRandomArgsToRecord(recordComponents);

            return constructor.newInstance(constructorParameters);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public <T> T createObject(Class<T> clazz, Executable createMethod)
        throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        try {
            Parameter[] parameters = createMethod.getParameters();
            Object[] constructorParameters = new Object[parameters.length];
            constructorParameters = createRandomArgs(parameters);
            if (createMethod instanceof Constructor) {
                Class<?>[] parameterTypes = Arrays.stream(parameters)
                    .map(Parameter::getType)
                    .toArray(Class<?>[]::new);
                return clazz.getDeclaredConstructor(parameterTypes).newInstance(constructorParameters);
            } else {
                Method method = (Method) createMethod;
                return (T) method.invoke(clazz, constructorParameters);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private Object[] createRandomArgs(Parameter[] parameters) {
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            args[i] = createRandomArg(parameters[i]);
        }
        return args;
    }

    private Object[] createRandomArgsToRecord(RecordComponent[] recordComponents) {
        Object[] args = new Object[recordComponents.length];
        for (int i = 0; i < recordComponents.length; i++) {
            args[i] = createRandomArgToRecord(recordComponents[i]);
        }
        return args;
    }

    @SuppressWarnings({"CyclomaticComplexity", "ReturnCount"})
    private Object createRandomArgToRecord(RecordComponent recordComponent) {

        Class<?> argType = recordComponent.getType();
        Min minAnnotation = recordComponent.getAnnotation(Min.class);
        Max maxAnnotation = recordComponent.getAnnotation(Max.class);
        NotNull notNullAnnotation = recordComponent.getAnnotation(NotNull.class);

        if (argType == int.class || argType == Integer.class) {
            int min = (minAnnotation != null) ? minAnnotation.value() : Integer.MIN_VALUE;
            int max = (maxAnnotation != null) ? maxAnnotation.value() : Integer.MAX_VALUE;
            return RANDOM.nextInt(min, max);
        } else if (argType == double.class || argType == Double.class) {
            return RANDOM.nextDouble();
        } else if (argType == long.class || argType == Long.class) {
            return RANDOM.nextLong();
        } else if (argType == short.class || argType == Short.class) {
            return (short) RANDOM.nextInt(Short.MAX_VALUE);
        } else if (argType == float.class || argType == Float.class) {
            return RANDOM.nextFloat();
        } else if (argType == boolean.class || argType == Boolean.class) {
            return RANDOM.nextBoolean();
        } else if (argType == char.class || argType == Character.class) {
            return (char) (RANDOM_CHAR_BOUND_MIN + RANDOM.nextInt(RANDOM_CHAR_BOUND_MAX - RANDOM_CHAR_BOUND_MIN));
        } else if (argType == String.class) {
            String randomString = createRandomString();
            return (notNullAnnotation != null && randomString == null) ? "" : randomString;
        } else {
            return null;
        }
    }

    @SuppressWarnings({"CyclomaticComplexity", "ReturnCount"})
    private Object createRandomArg(Parameter parameter) {
        Class<?> argType = parameter.getType();
        Min minAnnotation = parameter.getAnnotation(Min.class);
        Max maxAnnotation = parameter.getAnnotation(Max.class);
        NotNull notNullAnnotation = parameter.getAnnotation(NotNull.class);

        if (argType == int.class || argType == Integer.class) {
            int min = (minAnnotation != null) ? minAnnotation.value() : Integer.MIN_VALUE;
            int max = (maxAnnotation != null) ? maxAnnotation.value() : Integer.MAX_VALUE;
            return RANDOM.nextInt(min, max);
        } else if (argType == double.class || argType == Double.class) {
            return RANDOM.nextDouble();
        } else if (argType == long.class || argType == Long.class) {
            return RANDOM.nextLong();
        } else if (argType == short.class || argType == Short.class) {
            return (short) RANDOM.nextInt(Short.MAX_VALUE);
        } else if (argType == float.class || argType == Float.class) {
            return RANDOM.nextFloat();
        } else if (argType == boolean.class || argType == Boolean.class) {
            return RANDOM.nextBoolean();
        } else if (argType == char.class || argType == Character.class) {
            return (char) (RANDOM_CHAR_BOUND_MIN + RANDOM.nextInt(RANDOM_CHAR_BOUND_MAX - RANDOM_CHAR_BOUND_MIN));
        } else if (argType == String.class) {
            String randomString = createRandomString();
            return (notNullAnnotation != null && randomString == null) ? "" : randomString;
        } else {
            return null;
        }
    }

    private String createRandomString() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            char c = (char) (RANDOM.nextInt(RANDOM_CHAR_BOUND_MIN, RANDOM_CHAR_BOUND_MAX));
            randomString.append(c);
        }
        return randomString.toString();
    }

    private static <T extends Record> Constructor<T> canonicalConstructorOfRecord(Class<?> recordClass)
        throws NoSuchMethodException, SecurityException {
        Class<?>[] componentTypes = Arrays.stream(recordClass.getRecordComponents())
            .map(RecordComponent::getType)
            .toArray(Class<?>[]::new);
        return (Constructor<T>) recordClass.getDeclaredConstructor(componentTypes);
    }

}
