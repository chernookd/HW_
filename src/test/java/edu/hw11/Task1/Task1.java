package edu.hw11.Task1;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1 {

    @Test
    void helloByteBuddyTest() throws InstantiationException, IllegalAccessException {
        try (DynamicType.Unloaded<Object> unloadedType = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.isToString())
            .intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()) {

            Class<?> dynamicType = unloadedType.load(getClass()
                    .getClassLoader())
                .getLoaded();

            assertThat(dynamicType.newInstance().toString()).isEqualTo("Hello, ByteBuddy!");
        }
    }
}
