package hello;

import hello.config.MyDataSourceConfigV3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;

//@Import(MyDataSourceEnvConfig.class)
//@Import(MyDataSourceValueConfig.class)
//@Import(MyDataSourceConfigV1.class)
//@Import(MyDataSourceConfigV2.class)
@Import(MyDataSourceConfigV3.class)
@SpringBootApplication(scanBasePackages = {"hello.datasource"})
// 예제 진행을 위해 scanBasePackage는 hello.datasource로 두고 설정 정보 @Configuration클래스는 @Import로 가져옴
@ConfigurationPropertiesScan(basePackages = {"hello.datasource"})
/*
* @ConfigurationPropertiesScan는 @ConfigurationProperties 붙은 클래스를 마치 컴포넌트 스캔처럼 스캔할 수 있게 함
* → @EnableConfigurationProperties({MyDataSourcePropertiesV1.class})를 직접 어딘가에 붙일 필요가 없어짐
* - bean을 직접 등록하는지, component scan을 지용하여 자동 등록하는지 차이와 비슷하다고 생각하면 됨
* */
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
    /*
    * @Value를 이용한 값 주입
    * - 사용 방법
    *   - @Value{"${[외부 설정 key]}"}를 필드 혹은 메서드의 파라미터에 붙임
    *     - ex. @Value("${my.datasource.username}") private String username
    *     - Kotlin은 format string 문법과 표기 형태가 겹치므로 @Value{"\${[외부 설정 key]}"}로 사용
    *       - 즉 Kotlin은 \을 빼먹으면 동작하지 않음
    *       - ex. @Value("\${my.datasource.username}") private val username: String
    *   - (1) 필드 주입 방식
    *     - component의 field에 @Value를 붙임
    *     - 필드 주입이므로 private이어도 가능(CGLIB 변조)
    *     - 다만 final field에는 주입 불가능 - final 붙일 경우 컴파일 오류(final은 생성자에서 초기화 되어야 함)
    *   - (2) 파라미터 주입 방식
    *     - component의 method의 parameter에 @Value를 붙임
    * - 참고 사항
    *   - 외부 설정값은 기본적으로 String이지만, 주입되는 변수에 지정된 type에 따라 Spring Boot에서 적당히 type을 변환해줌
    *   - 기본값은 :[기본값]을 외부 설정 key 뒤에 붙여줌
    * - 장점
    *   - 개별값 하나하나를 주입받을 때는 Environment bean을 사용할 때보다 편함
    *   - MyDataSourceValueConfig에서는 @Configuration이 붙은 클래스에 대한 예시를 들었지만
    *     - @Component가 붙은 클래스에서도 직접 사용 가능
    *     - https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.typesafe-configuration-properties
    * - 단점
    *   - 각 값별로 하나하나 외부 설정 정보 key를 알려주고 주입받는 부분이 번거로움
    *   - 연관되어 있는 정보이더라도 개별 값들로 우선 가져온 후 사용해야 함
    * */
    /*
    * @ConfigurationProperties를 이용한 type-safe configuration properties(타입 안전한 설정 속성)
    * - 사용 방법
    *   - 값 주입 방식
    *     - 필드 주입 - Java bean property 방식으로 setter 사용
    *     - 생성자 주입(@ConstructorBinding) - setter 사용 방지, 설정값이 변경되지 않도록 함
     *   - bean 등록 방식
    *     - (1) @ConfigurationProperties 붙은 클래스를 직접 @Component로 bean 등록
    *     - (2) @ConfigurationProperties 붙은 클래스를 @Bean으로 bean 등록
    *     - (3) @ConfigurationProperties 붙은 클래스를 @EnableConfigurationProperties를 이용하여 bean으로 등록
    *     - (4) @ConfigurationProperties 붙은 클래스를 @ConfigurationPropertiesScan을 이용하여 bean으로 등록
    *   - Java bean이므로 Java bean validation 사용 가능
    *   - 위의 과정을 이용해 외부 설정값을 주입받은 설정 속성이 bean으로 등록됐으므로, 다른 bean을 사용하듯이  
    * - 활용 사례 → spring-boot-autoconfigure 라이브러리
    *   - org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration에서도
    *     - @EnableConfigurationProperties(DataSourceProperties.class)를 사용
    *     - org.springframework.boot.autoconfigure.jdbc.DataSourceProperties 클래스도 확인해보면
    *       - 사용하는 외부 설정의 key를 확인해볼 수 있음
    *       - ex. spring.datasource.classLoader, spring.datasource.url, spring.datasource.username, ...
    * - 장점
    *   - 외부 설정값들을 모아 Spring bean 객체 형태로 변환하여 사용 가능
    *   - 외부 설정값에서의 계층을 객체에서 표현 가능
    *   - 객체 변환 시 type에 대해 검증하므로 type-safety가 있음
    *   - Java bean이므로 bean validation 적용 가능
    * */
}
