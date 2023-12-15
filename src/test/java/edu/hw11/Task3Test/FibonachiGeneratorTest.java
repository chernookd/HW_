package edu.hw11.Task3Test;

import edu.hw11.Task3.FibonachiGenerator;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FibonachiGeneratorTest {

    @Test
    public void testFibonachiGenerator() throws Exception {
        FibonachiGenerator generator = new FibonachiGenerator();
        Class<?> clazz = generator.generateFibonachiClass();
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method fibMethod = clazz.getMethod("fib", int.class);

        assertEquals(0L, fibMethod.invoke(instance, 0));
        assertEquals(1L, fibMethod.invoke(instance, 1));
        assertEquals(2L, fibMethod.invoke(instance, 3));
        assertEquals(13L, fibMethod.invoke(instance, 7));
        assertEquals(55L, fibMethod.invoke(instance, 10));
        assertEquals(6765L, fibMethod.invoke(instance, 20));
    }
}
