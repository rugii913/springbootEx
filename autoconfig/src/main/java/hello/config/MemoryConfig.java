package hello.config;

import memory.MemoryController;
import memory.MemoryFinder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(name = "memory", havingValue = "on")
/*
* → JVM system property에서 값을 읽어와 조건적으로 판단하는 간편한 어노테이션
*   의존 관계를 따라가보면 Condition의 구현 클래스인 OnPropertyCondition이 복잡한 로직으로 구현되어 있는 것 확인 가능
* - 이 외에도 여러 @ConditionalOnXxx 어노테이션들이 → 주로 Spring Boot 자동 구성에 활용
*   - https://docs.spring.io/spring-boot/reference/features/developing-auto-configuration.html
*   - cf. @Conditional과 Condition은 spring-context 라이브러리에 있으나, 그 확장인 @ConditionalOnXxx는 spring-boot-autoconfigure 라이브러리
* */
//@Conditional(ConditionOnMemory.class) // 명시된 Condition type의 matches()가 true를 반환할 때만 config가 등록
//@Configuration // @Conditional 작업을 라이브러리에서 auto configuration으로 해줄 것이라 config에서 제외시키기 위해 주석 처리
public class MemoryConfig {
    /*
    * auto configuration의 필요성
    * - 라이브러리를 사용하는 클라이언트 개발자 입장에서 보면
    *   - 라이브러리 내부의 어떤 bean을 등록해야 하는지 알아야 함
    *   - 알고난 뒤, 직접 config 파일을 작성하여 하나하나 bean으로 등록해줘야 함
    * - 라이브러리를 제공하는 개발자 입장에서도
    *   - 구체적인 내용을 알려주는 문서를 상세하게 작성해두어야 함
    *   - 물론 auto configuration을 이용하더라도 문서는 작성해야겠지만...
    * - 이런 부분은 자동으로 처리해서 라이브러리를 프로젝트에 포함시키기만 하면 자동으로 bean 등록 작업들을 처리해주는 것이 auto configuration
    * */

    @Bean
    public MemoryController memoryController() {
        return new MemoryController(memoryFinder()); // cf. @Configuration이 붙은 클래스에서는 CGLIB 이용하여 싱글톤 보장
    }

    @Bean
    public MemoryFinder memoryFinder() {
        return new MemoryFinder();
    }
}
