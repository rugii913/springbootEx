package hello.config;

import memory.ConditionOnMemory;
import memory.MemoryController;
import memory.MemoryFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Conditional(ConditionOnMemory.class) // 명시된 Condition type의 matches()가 true를 반환할 때만 config가 등록
@Configuration
public class MemoryConfig {

    @Bean
    public MemoryController memoryController() {
        return new MemoryController(memoryFinder()); // cf. @Configuration이 붙은 클래스에서는 CGLIB 이용하여 싱글톤 보장
    }

    @Bean
    public MemoryFinder memoryFinder() {
        return new MemoryFinder();
    }
}
