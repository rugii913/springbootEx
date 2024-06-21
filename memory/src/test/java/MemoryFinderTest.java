import memory.Memory;
import memory.MemoryFinder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemoryFinderTest {

    @Test
    void get() {
        MemoryFinder memoryFinder = new MemoryFinder();
        Memory memory = memoryFinder.get();
        System.out.println("memory = " + memory);
        Assertions.assertThat(memory).isNotNull();
    }
}
