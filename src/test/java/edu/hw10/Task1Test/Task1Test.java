package edu.hw10.Task1Test;

import edu.hw10.Task1.RandomObjectGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class Task1Test {

    private RandomObjectGenerator randomObjectGenerator;

    @BeforeEach
    public void setUp() {
        randomObjectGenerator = new RandomObjectGenerator();
    }

    @Test
    public void nextObjectWithMethodTest() {
        var testClass = randomObjectGenerator.nextObject(TestClass.class, "create", int.class, String.class);

        assertNotNull(testClass);

        assertThat(testClass.getAge()).isNotEqualTo(0);
        assertThat(testClass.getAge()).isNotEqualTo(null);
        assertThat(testClass.getName().length()).isEqualTo(5);
        assertThat(testClass.getName()).isNotEqualTo(null);
    }

    @Test
    public void testNextObjectWithConstructor() {
        var testRecord = randomObjectGenerator.nextRecord(TestRecord.class);

        assertNotNull(testRecord);

        assertThat(testRecord.integer()).isNotEqualTo(null);
        assertThat(testRecord.sh()).isNotEqualTo(null);
        assertThat(testRecord.list()).isEqualTo(null);
    }

    @Test
    public void testWithAnnotation() {
        for (int i = 0; i < 30; i++) {
            var testClassWithAnnotations = randomObjectGenerator.nextObject(TestClassWithAnnotations.class,
                int.class, String.class, int.class);

            assertNotNull(testClassWithAnnotations);
            assertTrue(testClassWithAnnotations.getAge() < 10);
            assertNotNull(testClassWithAnnotations.getName());
            assertTrue(testClassWithAnnotations.getWeight() > 25);
        }
    }

}
