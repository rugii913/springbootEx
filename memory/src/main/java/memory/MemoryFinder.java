package memory;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemoryFinder {

    public Memory get() {
        long max = Runtime.getRuntime().maxMemory(); // JVM이 사용할 수 있는 최대 메모리 → 이 이상이면 OOM 발생
        long total = Runtime.getRuntime().totalMemory(); // JVM이 확보한 전체 메모리(JVM은 처음부터 max를 확보해두지 않음)
        long free = Runtime.getRuntime().freeMemory(); // JVM이 확보한 메모리 중 사용하지 않은 메모리(total 중 사용하지 않은 것)
        long used = total - free; // → JVM이 사용 중인 메모리가 됨
        return new Memory(used, max);
    }

    @PostConstruct
    public void init() {
        log.info("init memoryFinder");
    }
}
