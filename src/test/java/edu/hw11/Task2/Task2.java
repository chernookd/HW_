package edu.hw11.Task2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;
public class Task2 {

    public static Stream<Arguments> source() {
        return Stream.of(
            Arguments.of(1,2,2),
            Arguments.of(0,2,0),
            Arguments.of(1,10,10),
            Arguments.of(-1,2,-2),
            Arguments.of(-1,-2,2),
            Arguments.of(0,0,0)
            );
    }

    @MethodSource("source")
    @ParameterizedTest
    void Task2ChangeSumTest(int a, int b, int correctResult) {
        ByteBuddyAgent.install();
        new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(named("sum"))
            .intercept(MethodDelegation.to(Multiply.class))
            .make()
            .load(Task2.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        int result = ArithmeticUtils.sum(a,b);
        assertThat(result).isEqualTo(correctResult);
    }
}
