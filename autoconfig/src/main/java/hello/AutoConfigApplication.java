package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Spring Boot auto configuration의 핵심
 * - (1) @Conditional
 *   - Condition 인터페이스에서 구현한 matches() return value에 따라 @Configuration이 붙은 클래스의 설정 정보 등록 여부 판단
 *   - @Conditional, Condition은 Spring에서도 사용할 수 있고, 이를 확장한 @ConditionalOnXxx는 Spring Boot 필요
 * - (2) @AutoConfiguration
 *   - 자동 구성할 config 클래스에 @AutoConfiguration 어노테이션을 붙임
 *   - 더불어 [프로젝트 루트]/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports라는 파일에
 *     - @AutoConfiguration을 붙인 클래스의 "패키지명.클래스명"이 명시되어야 함
 *   - 라이브러리를 빌드할 때 위 파일이 jar 파일의 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports로 그대로 들어감
 *     - Spring Boot는 라이브러리 jar 파일에 있는 해당 위치, 해당 파일을 모두 검사하고
 *     - 명시된 클래스를 자동 구성을 위한 클래스로 사용함
 * */
@SpringBootApplication
public class AutoConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoConfigApplication.class, args);
    }
}
