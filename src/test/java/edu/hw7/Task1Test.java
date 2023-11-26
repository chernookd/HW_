package edu.hw7;

import edu.hw7.Task1.Counter;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {

    @Test
    public void counterInvalidArgTest() {
        Counter counter = new Counter();

        assertThrows(RuntimeException.class, () -> counter.multiThreadedIncrease(20, -10));
        assertThrows(RuntimeException.class, () -> counter.multiThreadedIncrease(20, 0));
    }

    @Test
    public void counterValidArgTest() {
        Counter counter = new Counter();

        assertThat(counter.multiThreadedIncrease(-10, 10)).isEqualTo(0);
        assertThat(counter.multiThreadedIncrease(-1000, 10)).isEqualTo(-990);
        assertThat(counter.multiThreadedIncrease(0, 1)).isEqualTo(1);
        assertThat(counter.multiThreadedIncrease(0, 10000)).isEqualTo(10000);
        assertThat(counter.multiThreadedIncrease(-100, 100)).isEqualTo(0);
    }

}
