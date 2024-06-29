package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplication.class, args);
    }

    /*
    * Spring Boot의 프로덕션 준비 기능(production-ready features)
    * - 공식 문서 https://docs.spring.io/spring-boot/reference/actuator/index.html
    * - 비기능적 요구사항(non-functional requirements)
    *   - 기획자가 직접 요구하는 경우는 드문, 기능 요구사항(비즈니스 요구사항) 외의 비기능적 요소들
    *   - production을 운영 환경에 배포할 때 준비해야하는 비기능적 요소들
    *   - 지표(metric), 추적(trace), 감사(auditing), 모니터링(monitoring)
    *     - ex. 애플리케이션이 현재 살아 있는지, 로그 정보는 정상 설정되어 있는지, 커넥션 풀은 얼마나 사용되고 있는지 등을 확인할 수 있어야 함
    * - Spring Boot는 spring-boot-actuator 라이브러리를 제공
    *   - 이 actuator 라이브러리를 통해 프로덕션 준비 기능을 편리하게 사용 가능
    *   - 이에 더해 Micrometer, Prometheus, Grafana 등 모니터링 시스템과 쉽게 연동 가능
    *   - cf. actuator는 시스템을 움직이거나 제어하는 기계 장치라는 의미
    * */
    /*
    * Spring Boot actuator의 endpoint 설정
    * - actuator endpoint를 사용(호출 가능하도록)하려면 (1) endpoint 활성화, (2) endpoint 노출 두 과정 필요
    *   - 대부분의 endpoint는 기본적으로 활성화되어 있음(shutdown 제외)
    *   - Spring Boot autoconfigure에서 actuator endpoint 노출 기본 설정은 endpoint ID 중 health만 노출
    * - (1) endpoint 활성화/비활성화
    *   - 해당 기능을 사용할지 말지 선택하는 것
    *   - 방법 (cf. 설정 데이터로 yml 파일 사용 시 적절하게 계층구조에 맞춰 작성)
    *     - 외부 설정값을 다음과 같이 부여 → management.endpoint.[endpoint ID].enabled=true
    *     - 비활성화시킬 경우 true 대신 false 부여
    * - (2) endpoint 노출/제외
    *   - 활성화된 endpoint를 HTTP로 노출할지, JMX로 노출할지 선택하는 것
    *     - HTTP, JMX 모두 노출할 수도 있음
    *   - 노출 방법 (cf. 설정 데이터로 yml 파일 사용 시 적절하게 계층구조에 맞춰 작성)
    *     - (HTTP 노출) 외부 설정값을 다음과 같이 부여 → management.endpoints.web.exposure.include="[노출할 endpoint ID]"
    *     - (JMX 노출) 외부 설정값을 다음과 같이 부여 → management.endpoints.jmx.exposure.include="[노출할 endpoint ID]"
    *     - [노출할 endpoint ID] 자리에 *을 줄 경우 활성화된 모든 endpoint 노출
    *   - 제외 방법 (cf. 설정 데이터로 yml 파일 사용 시 적절하게 계층구조에 맞춰 작성)
    *     - 외부 설정값을 다음과 같이 부여 → management.endpoints.[web 또는 jmx].exposure.exclude="[제외할 endpoint ID]"
    * */
    /*
    * 다양한 actuator endpoint
    * - 주요 endpoint ID 목록
    *   - beans: Spring container에 등록된 Spring bean 표시
    *   - conditions: condition을 통해서 bean을 등록할 때 평가 조건과 일치하거나 일치하지 않는 이유 표시
    *   - configprops: @ConfigurationProperties(type-safe configuration properties) 표시
    *   - env: 외부 설정값 Environment 정보 표시
    *   - health: 애플리케이션 health 정보 표시 → 뒤에서 자세히 설명
    *   - httpexchanges: HTTP 호출 응답 정보 표시, HttpExchangeRepository를 구현한 bean을 별도로 등록해야 함 → 뒤에서 자세히 설명
    *   - info: 애플리케이션 정보 표시 → 뒤에서 자세히 설명
    *   - loggers: 애플리케이션 로거 설정 표시, POST 메서드 활용 로그 레벨 실시간 변경 → 뒤에서 자세히 설명
    *   - merics: 애플리케이션 메트릭 정보 표시
    *   - mappings: @RequestMappint 정보 표시
    *   - threaddump: thread dump를 실행하여 표시
    *   - shutdown: 애플리케이션 종료, 기본적으로 비활성화 되어 있음
    * - 전체 endpoint 간단한 설명 공식 문서
    *   - https://docs.spring.io/spring-boot/reference/actuator/endpoints.html
    * */
    /*
    * health
    * - (사용 목적) 애플리케이션에 문제가 발생했을 때 문제를 인지하기 위함 - 문제 발생 시 추적 범위를 좁힐 수 있음
    * - 사용 방법
    *   - 단순히 애플리케이션이 동작 중인지를 판단하는 게 아니라 다양한 정보를 종합해서 health 정보를 판단
    *     - heatlh component 중 하나라도 문제가 있다면 전체 상태가 DOWN으로 표시
    *   - 자세한 정보를 확인하려면 다음 외부 설정값 지정 → management.endpoint.health.show-details=always
    *     - (1) DB 등 응답 여부 체크 / cf. JDBC spec상 검증하는 기능이 있음 → validationQuery 정보로 확인 가능
    *     - (2) 시스템 디스크 사용량 체크
    *     - (3) ping 체크
    *   - 덜 자세한 정보를 확인하려면 다음 외부 설정값 지정 → management.endpoint.health.show-components=always
    *     - 세 가지 항목에 대한 status만 확인
    *   - 자세한 health 기본 지원 기능 참고
    *     - https://docs.spring.io/spring-boot/reference/actuator/endpoints.html#actuator.endpoints.health.auto-configured-health-indicators
    *   - 원하는 경우 직접 health 기능 구현하여 추가 가능
    *     - https://docs.spring.io/spring-boot/reference/actuator/endpoints.html#actuator.endpoints.health.writing-custom-health-indicators
    * */
    /*
    * info
    * - (사용 목적) 애플리케이션의 기본 정보 확인
    * - 제공 정보
    *   - (cf.) java, os, env는 기본으로 비활성화 되어 있고, build, git은 별도 파일이 필요하므로, 다른 설정 없이 actuator/info를 호출하면 아무 데이터도 확인할 수 없음
    *   - java: Java runtime 정보
    *   - os: OS 정보
    *   - env: Environment에서 "info."으로 시작하는 정보 표시 여부
    *   - build: 빌드 정보 - 확인하려면 META-INF/build-info.properties 파일 필요
    *   - git: git 정보(branch, commit 정보 등) - 확인하려면 git.properties 파일 필요
    *     - 잘못된 branch 혹은 commit이 build 된 것을 확인할 때 유용
    * - 사용 방법
    *   - java, os, env 정보 활성화 방법
    *     - 다음 외부 설정값 지정 → management.info.[java 또는 os 또는 env].enabled=true
    *     - cf. management.endpoint.info가 아니라 management.info로 계층이 다름에 유의
    *   - build 정보를 표시하기 위한 META-INF/build-info.properties 파일 생성 방법(Gradle 활용)
    *     - build.gradle에 다음 추가 → springBoot { buildInfo() }
    *   - git 정보를 표시하기 위한 git.properties 파일 생성 방법(Gradle 활용)
    *     - build.gradle 플러그인 추기(Kotlin DSL 기준) → id("com.gorylenko.gradle-git-properties") version "[플러그인 버전]"
    *     - git 정보를 자세하게 확인하려면 다음 외부 설정값 지정 → management.info.git.mode="full"
    *   - info 관련 커스텀한 기능을 추가하려면 참고
    *     - https://docs.spring.io/spring-boot/reference/actuator/endpoints.html#actuator.endpoints.info.writing-custom-info-contributors
    * */
    /*
    * loggers
    * - (사용 목적) 로깅 관련 정보(설정된 로그 레벨 등) 확인, POST 메서드를 이용하여 로그 레벨 실시간 변경 가능
    * - 제공 정보
    *   - 로거가 있는 패키지, 클래스에 대한 로그 레벨
    * - 사용 방법
    *   - cf. 외부 설정값 지정을 통한 로그 레벨 설정 방법
    *     - Spring Boot root 로그 레벨 설정값 지정
    *       - 외부 설정값 logging.level.root=[OFF/ERROR/WARN/INFO/DEBUG/TRACE]로 로거가 있는 모든 패키지, 클래스에 대한 기본 로그 레벨 설정 가능
    *         - root의 기본 설정값은 INFO
    *       - 더 구체적으로 설정값을 명시한 패키지, 클래스에 대해서는 더 구체적인 설정값을 따름
    *     - 각 패키지, 클래스별 로그 레벨 설정값 지정
    *       - logging.level.[패키지 혹은 패키지.클래스 이름]
    *       - 특정 패키지에서 대해 로그 레벨을 명시할 경우 **하위의 모든** 패키지, 클래스에 대해 명시된 로그 레벨이 설정됨
    *   - 로거가 있는 모든 패키지, 클래스에 대한 로그 레벨 조회 방법
    *     - /actuator/loggers 호출
    *   - 특정 로거만 조회 - 특정 로거 이름 기준 조회
    *     - /actuator/loggers/[로거 이름] 호출
    *     - ex. 예제에서는 /actuator/logger/hello.controller 혹은 /actuator/logger/hello.controller.LogController
    *   - 실시간 로그 레벨 변경
    *     - (필요성) 운영 서버에서는 통상적으로 INFO 이상의 로그 레벨을 사용하는데, 급하게 DEBUG, TRACE 로그를 확인해야 하는 경우 사용
    *       - 애플리케이션을 재시작하기 않고 실시간으로 로그 레벨 변경 가능
    *     - HTTP POST 메서드로 /actuator/loggers/[로거 이름], body JSON 형식으로 { "configuredLevel": "[지정할 로그 레벨]" } 호출
    * */
}
