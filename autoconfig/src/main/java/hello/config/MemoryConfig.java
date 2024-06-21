package hello.config;

import memory.MemoryController;
import memory.MemoryFinder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "memory", havingValue = "on")
/*
* → JVM system property에서 값을 읽어와 조건적으로 판단하는 간편한 어노테이션
*   의존 관계를 따라가보면 Condition의 구현 클래스인 OnPropertyCondition이 복잡한 로직으로 구현되어 있는 것 확인 가능
* - 이 외에도 여러 @ConditionalOnXxx 어노테이션들이 → 주로 Spring Boot 자동 구성에 활용
*   - https://docs.spring.io/spring-boot/reference/features/developing-auto-configuration.html
*   - cf. @Conditional과 Condition은 spring-context 라이브러리에 있으나, 그 확장인 @ConditionalOnXxx는 spring-boot-autoconfigure 라이브러리
* */
//@Conditional(ConditionOnMemory.class) // 명시된 Condition type의 matches()가 true를 반환할 때만 config가 등록
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
