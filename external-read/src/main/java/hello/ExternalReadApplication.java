package hello;

import hello.config.MyDataSourceEnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(MyDataSourceEnvConfig.class)
@SpringBootApplication(scanBasePackages = {"hello.datasource"})
// 예제 진행을 위해 scanBasePackage는 hello.datasource로 두고 설정 정보 @Configuration클래스는 @Import로 가져옴
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
    /*
    * Environment bean 직접 이용
    * - 사용 방법
    *   - Spring 구성으로 사용할 config 클래스 작성
    *   - Environment type bean을 자동 주입받도록 함
    *   - [Environment bean 객체].getProperty("[외부 설정 key]")로 외부 설정 key에 대한 value를 가져옴
    *     - getProperty("[외부 설정 key]", [타입 정보])를 통해 해당 타입으로 바로 받아올 수도 있음(Spring 내부의 타입 변환 로직 작동)
    *     - cf. Duration, Period, DataSize type에 대한 변환 공식 문서 참고
    *       - https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.typesafe-configuration-properties.conversion
    *   - 가져온 설정값을 저장할 객체의 클래스를 작성하고 bean으로 등록, bean으로 등록 시 생성자 등을 통해 설정값을 넣어둠
    * - 단점
    *   - Environment bean 객체에서 getProperty()로 외부 설정값을 가져오는 작업을 반복해야 함
    *     - 설정값이 추가되면 객체의 메서드를 호출하는 해당 코드도 추가해야 함
    *     - 설정값이 추가될 때마다 type 변환도 고려해줘야 함 
    * */
}
