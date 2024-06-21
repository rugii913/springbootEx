package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Spring Boot auto configuration의 핵심
 * - (1) @Conditional
 *   - Condition 인터페이스에서 구현한 matches() return value에 따라 @Configuration이 붙은 클래스의 설정 정보 등록 여부 판단
 *   - @Conditional, Condition은 Spring에서도 사용할 수 있고, 이를 확장한 @ConditionalOnXxx는 Spring Boot 필요
 * - (2) @AutoConfiguration
 * */
@SpringBootApplication
public class AutoConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoConfigApplication.class, args);
    }
}
