package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExternalReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalReadApplication.class, args);
    }
    /*
    * Spring이 지원하는 다양한 외부 설정 조회 방법과 Environment interface
    * - Environment interface
    *   - Environment type bean을 주입 받아 외부 설정 key=value 값을 조회할 수 있음
    *     - 앞서 살펴보았듯 설정 데이터(application.properties), OS environment variables, Java system properties, commandline arguments
    *   - Spring은 Environment bean을 활용하여 더 쉽게 외부 설정값을 읽는 방법들을 제공함
    * - 다양한 외부 설정 조회 방법(Spring 지원)
    *   - (1) Environment 직접 이용
    *   - (2) @Value → 값 주입
    *   - (3) @ConfigurationProperties → 타입 안전한 설정 속성
    * */
}
